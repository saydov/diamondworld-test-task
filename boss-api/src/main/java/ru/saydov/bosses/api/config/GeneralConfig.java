package ru.saydov.bosses.api.config;

import lombok.NonNull;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.Unmodifiable;
import ru.saydov.bosses.api.config.locale.Message;
import ru.saydov.bosses.api.utils.Loadable;

import java.util.Map;
import java.util.Optional;

/**
 * @author saydov
 */
public interface GeneralConfig extends Loadable {

    @Unmodifiable @NonNull Map<String, Message> getMessages();

    @NonNull Optional<Message> findMessage(final @NonNull String key);

    @NonNull Message getMessage(final @NonNull String key);

    @NonNull FileConfiguration getConfiguration();

}
