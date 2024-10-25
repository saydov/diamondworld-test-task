package ru.saydov.bosses.api.entity.impl;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import ru.saydov.bosses.api.entity.interfaces.PacketEntity;
import ru.saydov.bosses.api.utils.nms.NmsUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import static ru.saydov.bosses.api.utils.protocol.ProtocolLibUtil.PROTOCOL_MANAGER;

/**
 * @author saydov
 */
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class PacketEntityBaseImpl implements PacketEntity {

    @EqualsAndHashCode.Include
    @NonFinal
    @NotNull Location location;

    int entityId;

    @NotNull UUID entityUUID;

    @NotNull WrappedDataWatcher dataWatcher;

    @NotNull Set<Player> players;

    protected PacketEntityBaseImpl(final @NonNull Location location) {
        this.location = location;

        this.entityId = NmsUtil.getEntityId();
        this.entityUUID = UUID.randomUUID();
        this.entityType = getEntityType();

        this.players = new ObjectOpenHashSet<>();
        this.dataWatcher = new WrappedDataWatcher();
        // https://wiki.vg/index.php?title=Protocol&oldid=18242#Spawn_Player
       /* dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(0,
                WrappedDataWatcher.Registry.get(Byte.class)), (byte) 0); */

        registerWatcher();
    }

    protected void registerWatcher() {
        // Для других классов (например, в ArmorStand нужно
        // будет переопределить этот метод)
    }

    @NotNull EntityType entityType;

    public abstract @NotNull EntityType getEntityType();

    @Override
    public void teleport(@NonNull Location location) {
        this.location = location;

        val packet = new PacketContainer(PacketType.Play.Server.ENTITY_TELEPORT);
        packet.getIntegers().write(0, entityId);

        //location
        packet.getDoubles().write(0, location.getX());
        packet.getDoubles().write(1, location.getY());
        packet.getDoubles().write(2, location.getZ());

        //on ground
        packet.getBooleans().write(0, false);

        PROTOCOL_MANAGER.broadcastServerPacket(packet);
    }

    @Nullable
    @NonFinal
    String customName;

    @Override
    public void setCustomName(@Nullable String name) {
        if (customName != null && customName.equals(name))
            return;

        customName = name;
        sendMetadata();
    }

    @NonFinal boolean customNameVisible;

    @Override
    public boolean isCustomNameVisible() {
        return customNameVisible;
    }

    @Override
    public void setCustomNameVisible(boolean visible) {
        if (customNameVisible == visible)
            return;

        customNameVisible = visible;
        sendMetadata();
    }

    @Override
    public void spawn(final @NonNull Player player) {
        val packet = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY);

        //base
        packet.getIntegers().write(0, entityId);
        packet.getUUIDs().write(0, entityUUID);

        val key = new ResourceLocation(entityType.getKey().toString());
        val registry = BuiltInRegistries.ENTITY_TYPE;

        packet.getIntegers().write(1, registry.getId(registry.get(key)));

        //location
        packet.getDoubles().write(0, location.getX());
        packet.getDoubles().write(1, location.getY());
        packet.getDoubles().write(2, location.getZ());

        val yaw = (byte) (location.getYaw() * 256.0F / 360.0F);
        val pitch = (byte) (location.getPitch() * 256.0F / 360.0F);

        packet.getBytes().write(0, yaw);
        packet.getBytes().write(1, pitch);
        packet.getBytes().write(2, yaw);

        packet.getIntegers().write(2, 0); //velocity x
        packet.getIntegers().write(3, 0); //velocity y
        packet.getIntegers().write(4, 0); //velocity z

        PROTOCOL_MANAGER.sendServerPacket(player, packet);
        addViewer(player);
    }

    @Override
    public void remove(final @NonNull Player player) {
        val packet = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
        packet.getIntegerArrays().write(0, new int[]{entityId});

        PROTOCOL_MANAGER.sendServerPacket(player, packet);
    }

    protected void sendMetadata() {
        val packet = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
        packet.getIntegers().write(0, entityId);

        val objects = dataWatcher.getWatchableObjects();
        packet.getWatchableCollectionModifier().write(0, objects);

        PROTOCOL_MANAGER.broadcastServerPacket(packet);
    }

    @Override
    public @NotNull @UnmodifiableView Set<Player> getViewers() {
        return Collections.unmodifiableSet(players);
    }

    @Override
    public void addViewers(@NonNull Collection<Player> players) {
        players.forEach(this::addViewer);
    }

    @Override
    public void addViewer(@NonNull Player player) {
        players.add(player);
    }

    @Override
    public void removeViewer(@NonNull Player player) {
        players.remove(player);
    }
}
