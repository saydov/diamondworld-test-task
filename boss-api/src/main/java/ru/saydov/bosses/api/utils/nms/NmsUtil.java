package ru.saydov.bosses.api.utils.nms;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author saydov
 */
@UtilityClass
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NmsUtil {

    static String SERVER_VERSION;

    static AtomicInteger ENTITY_ID;

    public void init(final @NonNull JavaPlugin javaPlugin) {
        SERVER_VERSION = javaPlugin.getServer().getClass().getName().split("\\.")[3];

        try {
            ENTITY_ID = (AtomicInteger) getClazz("Entity")
                    .getDeclaredField("entityCount").get(null);
        } catch (final IllegalAccessException | NoSuchFieldException e) {
            javaPlugin.getSLF4JLogger().error("Failed to initialize NMSUtil", e);
        }
    }

    public static int getEntityId() {
        return ENTITY_ID.incrementAndGet();
    }

    @SneakyThrows
    public @NotNull Class<?> getClazz(final @NonNull String name) {
        return Class.forName("net.minecraft.server." + SERVER_VERSION + "." + name);
    }



}
