package ru.saydov.bosses.api.config.settings.time;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

/**
 * @author saydov
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BossTimeSettings {

    Setting setting;

    public static @Nullable BossTimeSettings create(final @NonNull JavaPlugin javaPlugin,
                                                   final @Nullable ConfigurationSection section) {
        if (section == null) return null;

        val zone = section.getString("zone");

        val timeZone = Optional.ofNullable(TimeZone.getTimeZone(zone))
                .orElseGet(() -> {
                    javaPlugin.getSLF4JLogger().warn("Invalid time zone: " + zone);
                    return TimeZone.getDefault();
                });

        val value = section.getStringList("values");
        return new BossTimeSettings(Setting.create(timeZone, value));
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static class Setting {

        @NotNull TimeZone timeZone;
        @NotNull List<String> values;

        public static @NotNull Setting create(final @NonNull TimeZone timeZone,
                                              final @NonNull List<String> values) {
            return new Setting(timeZone, values);
        }

    }

}
