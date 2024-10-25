package ru.saydov.bosses.api.ability;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;
import ru.saydov.bosses.api.utils.Manager;

import java.util.Set;

/**
 * @author saydov
 */
public final class SimpleAbilityManager implements Manager<Ability> {
    @Override
    public @NotNull @UnmodifiableView Set<Ability> getEntities() {
        return Set.of();
    }

    @Override
    public void registerEntity(@NonNull Ability entity) {

    }

    @Override
    public void removeEntity(@NonNull Ability entity) {

    }

    @Override
    public void removeAll() {

    }
}
