package ru.saydov.bosses.api.hologram;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.UnmodifiableView;
import ru.saydov.bosses.api.hologram.model.Hologram;
import ru.saydov.bosses.api.utils.Manager;

import java.util.Collections;
import java.util.Set;

/**
 * @author saydov
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class SimpleHologramManager implements Manager<Hologram> {

    @NonNull
    Set<Hologram> holograms;

    public static @NonNull Manager<Hologram> create() {
        return new SimpleHologramManager(new ObjectOpenHashSet<>());
    }

    @Override
    public @NonNull @UnmodifiableView Set<Hologram> getEntities() {
        return Collections.unmodifiableSet(holograms);
    }

    @Override
    public void registerEntity(@NonNull Hologram entity) {
        holograms.add(entity);
    }

    @Override
    public void removeEntity(@NonNull Hologram entity) {
        entity.remove();
        holograms.remove(entity);
    }

    @Override
    public void removeAll() {
        holograms.forEach(this::removeEntity);
        holograms.clear();
    }

}
