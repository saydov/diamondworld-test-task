package ru.saydov.bosses.api.config;

import lombok.NonNull;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import ru.saydov.bosses.api.config.locale.Message;
import ru.saydov.bosses.api.config.settings.BossSettings;
import ru.saydov.bosses.api.utils.Loadable;

import java.util.Map;
import java.util.Optional;

/**
 * @author saydov
 */
public interface GeneralConfig extends Loadable {

    @Unmodifiable @NotNull Map<String, Message> getMessages();

    @NotNull Optional<Message> findMessage(final @NonNull String key);

    @NotNull Message getMessage(final @NonNull String key);

    @NotNull FileConfiguration getConfiguration();

    @NotNull String getDatabaseFileName();

    @NotNull BossSettings getBossSettings();

}
