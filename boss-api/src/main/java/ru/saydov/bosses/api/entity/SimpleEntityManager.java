package ru.saydov.bosses.api.entity;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import ru.saydov.bosses.api.entity.interfaces.PacketEntity;
import ru.saydov.bosses.api.utils.Manager;

import java.util.Collections;
import java.util.Set;

/**
 * @author saydov
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class SimpleEntityManager implements Manager<PacketEntity> {

    @NotNull Set<PacketEntity> entities;

    public static @NotNull Manager<PacketEntity> create() {
        return new SimpleEntityManager(new ObjectOpenHashSet<>());
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
        entities.forEach(PacketEntity::remove);
        entities.clear();
    }

}
