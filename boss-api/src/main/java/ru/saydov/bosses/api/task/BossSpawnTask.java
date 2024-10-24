package ru.saydov.bosses.api.task;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.scheduler.BukkitRunnable;
import ru.saydov.bosses.api.BossApi;

/**
 * @author saydov
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class BossSpawnTask extends BukkitRunnable {

    @NonNull BossApi bossApi;

    public static @NonNull BossSpawnTask create(final @NonNull BossApi bossApi) {
        return new BossSpawnTask(bossApi);
    }

    @Override
    public void run() {

    }
}
