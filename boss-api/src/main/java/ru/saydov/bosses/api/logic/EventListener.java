package ru.saydov.bosses.api.logic;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.event.Listener;
import ru.saydov.bosses.api.BossApi;

/**
 * @author saydov
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class EventListener implements Listener {

    BossApi bossApi;

}
