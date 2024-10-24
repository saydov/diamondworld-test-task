package ru.saydov.bosses.api.config;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import ru.saydov.bosses.api.config.locale.Message;
import ru.saydov.bosses.api.config.locale.SimpleMultiMessage;
import ru.saydov.bosses.api.config.locale.SimpleSingleMessage;
import ru.saydov.bosses.api.config.settings.BossSettings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author saydov
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @NonNull)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class SimpleGeneralConfig implements GeneralConfig {

    JavaPlugin javaPlugin;

    @NonFinal Map<String, Message> messages;

    @NonFinal FileConfiguration configuration;

    @NonFinal String databaseFileName;

    @NonFinal BossSettings bossSettings;

    public static @NotNull SimpleGeneralConfig create(final @NonNull JavaPlugin plugin) {
        return new SimpleGeneralConfig(plugin);
    }

    @Override
    public @Unmodifiable @NotNull Map<String, Message> getMessages() {
        return messages;
    }

    private void loadMessages0(final @Nullable ConfigurationSection section) {
        if (section == null) return;

        val messages = new HashMap<String, Message>();

        for (val key : section.getKeys(false)) {
            Object obj = section.get(key);
            if (obj == null) continue;

            messages.put(key, loadMessage0(obj));
        }

        this.messages = messages;
    }

    @Override
    public @NotNull Optional<Message> findMessage(@NonNull String key) {
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
    public @NotNull Message getMessage(@NonNull String key) {
        return messages.getOrDefault(key, SimpleSingleMessage.create("Message with key " + key + " not found"));
    }

    @SuppressWarnings("unchecked")
    private @NotNull Message loadMessage0(final @NonNull Object obj) {
        if (obj instanceof String) {
            return SimpleSingleMessage.create((String) obj);
        }

        if (obj instanceof List) {
            return SimpleMultiMessage.create((List<String>) obj);
        }

        throw new IllegalArgumentException("Unknown message type");
    }

    private void loadDatabase0(final @Nullable ConfigurationSection section) {
        if (section == null) return;

        databaseFileName = section.getString("file");
    }

    private void loadBosses(final @Nullable ConfigurationSection section) {
        if (section == null) return;

        bossSettings = BossSettings.create(javaPlugin, section);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void load() {
        javaPlugin.saveDefaultConfig();
        configuration = javaPlugin.getConfig();

        loadMessages0(configuration.getConfigurationSection("messages"));
        loadDatabase0(configuration.getConfigurationSection("database"));
        loadBosses(configuration.getConfigurationSection("bosses"));
    }

    @Override
    public void unload() {
        messages.clear();
    }

}
