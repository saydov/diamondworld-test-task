package ru.saydov.bosses.plugin.commands;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.saydov.bosses.api.BossApi;
import ru.saydov.bosses.api.boss.model.SimpleBoss;

/**
 * @author saydov
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Command(name = "spawnboss", aliases = "sbboss")
public class SpawnBossCommand {

    BossApi bossApi;

    public static @NotNull SpawnBossCommand create(final @NonNull BossApi bossApi) {
        return new SpawnBossCommand(bossApi);
    }

    @Execute
    void execute(@Context CommandSender sender) {
        if (!(sender instanceof Player player))
            return;

//        bossApi.getBossManager().registerEntity();
    }
}
