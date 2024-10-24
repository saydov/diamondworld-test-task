package ru.saydov.bosses.api.entity.impl;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import ru.saydov.bosses.api.entity.interfaces.PacketEntity;
import ru.saydov.bosses.api.entity.packet.WrapperPlayServerEntityDestroy;
import ru.saydov.bosses.api.entity.packet.WrapperPlayServerEntityMetadata;
import ru.saydov.bosses.api.entity.packet.WrapperPlayServerEntityTeleport;
import ru.saydov.bosses.api.entity.packet.WrapperPlayServerSpawnEntityLiving;
import ru.saydov.bosses.api.utils.nms.NmsUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

/**
 * @author saydov
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class PacketEntityBaseImpl implements PacketEntity {

    @NonFinal @NotNull Location location;

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
        dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(0,
                WrappedDataWatcher.Registry.get(Byte.class)), (byte) 0);

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

        val packet = new WrapperPlayServerEntityTeleport();
        packet.setEntityID(entityId);

        packet.setX(location.getX());
        packet.setY(location.getY());
        packet.setZ(location.getZ());
        packet.setOnGround(false);

        packet.broadcastPacket();
    }

    @Nullable @NonFinal String customName;

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
    public void spawn() {
        val packet = new WrapperPlayServerSpawnEntityLiving();

        packet.setEntityID(entityId);
        packet.setType(entityType);
        packet.setUniqueId(entityUUID);

        packet.setX(location.getX());
        packet.setY(location.getY());
        packet.setZ(location.getZ());

        packet.broadcastPacket();
        addViewers(new ObjectOpenHashSet<>(Bukkit.getOnlinePlayers()));
    }

    @Override
    public void remove() {
        val packet = new WrapperPlayServerEntityDestroy();
        packet.setEntityIds(new int[] { getEntityId() });
        packet.broadcastPacket();
    }

    protected void sendMetadata() {
        val packet = new WrapperPlayServerEntityMetadata();
        packet.setEntityID(entityId);

        val objects = dataWatcher.getWatchableObjects();
        packet.setMetadata(objects);

        packet.broadcastPacket();
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
