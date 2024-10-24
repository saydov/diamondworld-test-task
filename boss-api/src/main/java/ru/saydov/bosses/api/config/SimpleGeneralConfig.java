package ru.saydov.bosses.api.config;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Unmodifiable;
import ru.saydov.bosses.api.config.locale.Message;
import ru.saydov.bosses.api.config.locale.SimpleMultiMessage;
import ru.saydov.bosses.api.config.locale.SimpleSingleMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author saydov
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class SimpleGeneralConfig implements GeneralConfig {

    JavaPlugin plugin;

    public static @NonNull SimpleGeneralConfig create(
            final @NonNull JavaPlugin plugin
    ) {
        return new SimpleGeneralConfig(plugin);
    }

    @NonFinal
    Map<String, Message> messages;

    @Override
    @Unmodifiable
    public @NonNull Map<String, Message> getMessages() {
        return messages;
    }

    @SuppressWarnings("ConstantConditions")
    private void loadMessages0(final @NonNull ConfigurationSection section) {
        val messages = new HashMap<String, Message>();

        for (val key : section.getKeys(false)) {
            messages.put(key, _loadMessage(section.get(key)));
        }

        this.messages = messages;
    }

    @Override
    public @NonNull Optional<Message> findMessage(@NonNull String key) {
        val message = messages.get(key);
        if (message == null) return Optional.of(SimpleSingleMessage.create("Message with key " + key + " not found"));

        if (message instanceof SimpleSingleMessage single) {
            return Optional.of(SimpleSingleMessage.create(single.getJoinedLines()));
        } else {
            val multi = (SimpleMultiMessage) message;
            return Optional.of(SimpleMultiMessage.create(multi.getLines()));
        }
    }

    @Override
    public @NonNull Message getMessage(@NonNull String key) {
        return messages.getOrDefault(key, SimpleSingleMessage.create("Message with key " + key + " not found"));
    }

    @SuppressWarnings("unchecked")
    private @NonNull Message _loadMessage(final @NonNull Object obj) {
        if (obj instanceof String) {
            return SimpleSingleMessage.create((String) obj);
        }

        if (obj instanceof List) {
            return SimpleMultiMessage.create((List<String>) obj);
        }

        throw new IllegalArgumentException("Unknown message type");
    }

    @NonFinal
    FileConfiguration configuration;

    @Override
    @SuppressWarnings("ConstantConditions")
    public void load() {
        plugin.saveDefaultConfig();
        configuration = plugin.getConfig();

        loadMessages0(configuration.getConfigurationSection("messages"));
    }

    @Override
    public void unload() {

    }

}
