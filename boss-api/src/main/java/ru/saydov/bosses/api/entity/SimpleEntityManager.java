package ru.saydov.bosses.api.entity;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import ru.saydov.bosses.api.entity.interfaces.PacketEntity;
import ru.saydov.bosses.api.entity.listener.EntityListener;
import ru.saydov.bosses.api.utils.Manager;

import java.util.Collections;
import java.util.Set;

/**
 * @author saydov
 */
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class SimpleEntityManager implements Manager<PacketEntity> {

    @NotNull JavaPlugin javaPlugin;

    @NotNull Set<PacketEntity> entities;

    public static @NotNull Manager<PacketEntity> create(
            final @NonNull JavaPlugin javaPlugin
    ) {
        val instance = new SimpleEntityManager(javaPlugin, new ObjectOpenHashSet<>());

        javaPlugin.getServer().getPluginManager()
                .registerEvents(EntityListener.create(javaPlugin, instance), javaPlugin);
        return instance;
    }

    @Override
    public @NotNull Set<PacketEntity> getEntities() {
        return Collections.unmodifiableSet(entities);
    }

    @Override
    public void registerEntity(@NonNull PacketEntity entity) {
        entities.add(entity);
    }

    @Override
    public void removeEntity(@NonNull PacketEntity entity) {
        entities.remove(entity);
    }

    @Override
    public void removeAll() {
        Bukkit.getOnlinePlayers().forEach(player ->
                entities.forEach(entity -> entity.remove(player)));

        entities.clear();
    }

}
