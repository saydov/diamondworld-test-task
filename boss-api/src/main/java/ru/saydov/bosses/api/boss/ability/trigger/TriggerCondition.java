package ru.saydov.bosses.api.boss.ability.trigger;

import lombok.NonNull;
import org.bukkit.entity.LivingEntity;

/**
 * @author saydov
 */
@FunctionalInterface
public interface TriggerCondition {

    boolean checkCondition(final @NonNull LivingEntity entity);

}
