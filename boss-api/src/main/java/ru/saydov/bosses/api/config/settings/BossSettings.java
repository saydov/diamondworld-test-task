package ru.saydov.bosses.api.config.settings;

import com.comphenix.protocol.wrappers.EnumWrappers;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.saydov.bosses.api.utils.builder.ItemBuilder;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author saydov
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class BossSettings {

    Set<Setting> settings;

    public static @NotNull BossSettings create(final @NotNull JavaPlugin javaPlugin,
                                               final @NotNull ConfigurationSection section) {
        val settings = new ObjectOpenHashSet<Setting>();

        for (val key : section.getKeys(false)) {
            val entry = section.getConfigurationSection(key);
            if (entry == null) continue;

            EntityType entityType;

            try {
                entityType = EntityType.valueOf(key.toUpperCase());
            } catch (final IllegalArgumentException e) {
                javaPlugin.getLogger().warning("Invalid entity type: " + key);
                continue;
            }

            val customName = entry.getString("custom-name");

            val locations = new ObjectOpenHashSet<Location>();

            if (entry.contains("locations")) {
                locations.addAll(entry.getStringList("locations")
                        .stream().map(l -> l.split(", "))
                        .filter(l -> l.length >= 3)
                        .map(l -> {
                            val world = Bukkit.getWorld(l[0]);

                            val x = Double.parseDouble(l[1]);
                            val y = Double.parseDouble(l[2]);
                            val z = Double.parseDouble(l[3]);

                            return new Location(world, x, y, z);
                        }).collect(Collectors.toSet()));
            } else {
                javaPlugin.getSLF4JLogger().warn("Failed to load locations!");

            }

            val attributes = new Object2ObjectOpenHashMap<Attribute, Integer>();

            if (entry.contains("attributes")) {
                val attributeSection = entry.getConfigurationSection("attributes");

                if (attributeSection != null) {
                    for (val attributeKey : attributeSection.getKeys(false)) {
                        val attribute = Attribute.valueOf(attributeKey.toUpperCase());
                        val value = attributeSection.getInt(attributeKey);
                        attributes.put(attribute, value);
                    }
                }
            }

            val holoLines = new ObjectArrayList<String>();

            if (entry.contains("holo-lines")) {
                holoLines.addAll(entry.getStringList("holo-lines"));
            }

            val equip = new Object2ObjectOpenHashMap<EnumWrappers.ItemSlot, ItemStack>();

            if (entry.contains("equipment")) {
                val equipment = entry.getConfigurationSection("equipment");

                if (equipment != null) {
                    for (val slot : equipment.getKeys(false)) {
                        val itemSection = equipment.getConfigurationSection(slot);
                        if (itemSection == null) continue;

                        val builder = ItemBuilder.fromConfig(javaPlugin, itemSection);
                        if (builder == null) continue;

                        try {
                            val itemSlot = EnumWrappers.ItemSlot.valueOf(slot.toUpperCase());
                            equip.put(itemSlot, builder.build());
                        } catch (final IllegalArgumentException e) {
                            javaPlugin.getLogger().warning("Invalid item slot: " + slot);
                        }
                    }
                }
            }

            settings.add(Setting.create(
                            customName, entityType, locations, attributes, holoLines, equip
                    )
            );
        }

        return new BossSettings(settings);
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static class Setting {

        @Nullable String customName;

        @NonNull
        EntityType entityType;

        @NotNull Set<Location> locations;

        @NotNull Map<Attribute, Integer> attributes;

        @Nullable List<String> holoLines;

        @Nullable Map<EnumWrappers.ItemSlot, ItemStack> equipment;

        public static @NotNull Setting create(
                // мне кажется, это конечно пи3да
                final @Nullable String customName,
                final @NonNull EntityType entityType,
                final @NonNull Set<Location> locations,
                final @NonNull Map<Attribute, Integer> attributes,
                final @Nullable List<String> holoLines,
                final @Nullable Map<EnumWrappers.ItemSlot, ItemStack> equipment
        ) {
            return new Setting(
                    customName, entityType, locations, attributes, holoLines, equipment
            );
        }

    }

}
