package ru.saydov.bosses.api.boss;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;
import ru.saydov.bosses.api.boss.model.Boss;
import ru.saydov.bosses.api.utils.Manager;

import java.util.Collections;
import java.util.Set;

/**
 * @author saydov
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class SimpleBossManager implements Manager<Boss> {

    @NotNull Set<Boss> bosses;

    public static @NotNull Manager<Boss> create() {
        return new SimpleBossManager(new ObjectOpenHashSet<>());
    }

    @Override
    public @NotNull @UnmodifiableView Set<Boss> getEntities() {
        return Collections.unmodifiableSet(bosses);
    }

    @Override
    public void registerEntity(@NonNull Boss entity) {
        bosses.add(entity);
    }

    @Override
    public void removeEntity(@NonNull Boss entity) {
        bosses.remove(entity);
    }

    @Override
    public void removeAll() {
        bosses.forEach(this::removeEntity);
        bosses.clear();
    }
}
