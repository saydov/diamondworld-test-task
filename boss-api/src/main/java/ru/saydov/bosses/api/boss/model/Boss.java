package ru.saydov.bosses.api.boss.model;

import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

/**
 * @author saydov
 */
public interface Boss {

    @NotNull Location getLocation();

    @NotNull EntityType getEntityType();

    @Nullable String getCustomName();

    double getDamage();

    double getMaxHealth();

    /*void setMaxHealth(final double health); */

    void spawn();

    void delete();

    @NotNull Map<EquipmentSlot, ItemStack> getEquipment();

    boolean setEquipment(final @NonNull Map<EquipmentSlot, ItemStack> equip);

    @NotNull Optional<LivingEntity> getEntity();



}
