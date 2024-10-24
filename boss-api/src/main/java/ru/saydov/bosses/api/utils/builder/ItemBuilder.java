package ru.saydov.bosses.api.utils.builder;

import com.comphenix.protocol.wrappers.EnumWrappers;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * @author saydov
 */
@Builder(builderMethodName = "create", buildMethodName = "item")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemBuilder {

    @NotNull Material material;
    @Nullable Map<EnumWrappers.ItemSlot, Pair<Enchantment, Integer>> enchantments;

    int amount, durability;

    public static @Nullable ItemBuilder fromConfig(final @NonNull JavaPlugin javaPlugin,
                                                   final @NonNull ConfigurationSection section) {
        Material material;

        try {
            material = Material.valueOf(section.getString("material"));
        } catch (IllegalArgumentException e) {
            javaPlugin.getLogger().warning("Invalid material: " + section.getString("material"));
            return null;
        }

        val builder = ItemBuilder.create();
        builder.material(material);

        if (section.contains("amount")) {
            builder.amount(section.getInt("amount"));
        }

        if (section.contains("durability")) {
            builder.durability(section.getInt("durability"));
        }

        if (section.contains("enchantments")) {
            val enchantments = section.getConfigurationSection("enchantments");
            if (enchantments != null) {
                val enchantmentMap = enchantments.getValues(false);
                val enchantmentBuilder = builder.enchantments(Map.of());

                enchantmentMap.forEach((key, value) -> {
                    @SuppressWarnings("deprecation")
                    val enchantment = Enchantment.getByName(key);

                    if (enchantment != null) {
                        val enchantmentValue = (int) value;
                        enchantmentBuilder.enchantments(Map.of(EnumWrappers.ItemSlot.MAINHAND,
                                Pair.of(enchantment, enchantmentValue)));
                    } else {
                        javaPlugin.getLogger().warning("Invalid enchantment: " + key);
                    }
                });
            }
        }

        return builder.item();
    }

    @SuppressWarnings("deprecation")
    public @NotNull ItemStack build() {
        val item = new ItemStack(material, amount);

        if (durability > 0) {
            item.setDurability((short) durability);
        }

        val meta = item.getItemMeta();
        if (meta != null) {
            if (enchantments != null) {
                enchantments.forEach((slot, enchantment) ->
                        meta.addEnchant(enchantment.getKey(), enchantment.getValue(), true));
            }

            item.setItemMeta(meta);
        }

        return item;
    }

}
