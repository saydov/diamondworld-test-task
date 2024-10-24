package ru.saydov.bosses.api.boss.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

/**
 * @author saydov
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SimpleBoss implements Boss {

    @NonNull Location location;

    @NonNull EntityType entityType;

    @Nullable String customName;

    double maxHealth, damage;

    @NonFinal @Nullable LivingEntity entity;

    @Override
    public @NonNull Optional<LivingEntity> getEntity() {
        return Optional.ofNullable(entity);
    }

    @Override
    public void delete() {
        if (entity == null) {
            return;
        }

        entity.remove();
    }

    @Override
    public void spawn() {
        if (entity != null) {
            return;
        }

        entity = (LivingEntity) location.getWorld().spawnEntity(location, entityType);

        if (customName != null) {
            entity.setCustomNameVisible(true);
            entity.customName(Component.text(customName));
        }

        entity.setRemoveWhenFarAway(false);
        entity.setAI(false);
        entity.setCanPickupItems(false);

        setHealth0(maxHealth); // todo : add / remove attributes options
    }

    private void setHealth0(final double health) {
        if (entity == null) {
            return;
        }

        val attribute = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (attribute != null) attribute.setBaseValue(health);

        entity.setHealth(health);
    }

    @NonFinal Map<EquipmentSlot, ItemStack> equipment;

    @Override
    public boolean setEquipment(@NonNull Map<EquipmentSlot, ItemStack> equip) {
        return false;
    }

}
