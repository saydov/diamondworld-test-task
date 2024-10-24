package ru.saydov.bosses.api.utils;

import lombok.NonNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Set;

/**
 * @author saydov
 */
public interface Manager<E> {

    @NonNull @UnmodifiableView Set<E> getEntities();

    void registerEntity(final @NonNull E entity);

    void removeEntity(final @NonNull E entity);

    void removeAll();

}
