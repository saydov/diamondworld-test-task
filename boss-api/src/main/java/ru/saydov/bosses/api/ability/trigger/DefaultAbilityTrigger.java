package ru.saydov.bosses.api.ability.trigger;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.bukkit.entity.LivingEntity;

    /**
     * @author saydov
     */
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum DefaultAbilityTrigger {

    TIME_ELAPSED(entity -> true),
    LOW_HEALTH(entity -> true),
    DAMAGE_RECEIVED(entity -> true)

    ;

    TriggerCondition condition;

    public boolean shouldActivate(final @NonNull LivingEntity entity) {
        return condition.checkCondition(entity);
    }

}
