package ru.saydov.bosses.plugin;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import ru.saydov.bosses.api.BossApi;
import ru.saydov.bosses.plugin.commands.TestCommand;

/**
 * @author saydov
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class BossesPlugin extends JavaPlugin {

    BossApi bossApi;

    LiteCommands<CommandSender> builder;

    @Override
    public void onEnable() {
        bossApi = BossApi.create(this);
        bossApi.load();

        builder = LiteBukkitFactory.builder("diamond-world-in-the-heart")
                .commands(TestCommand.create(bossApi))
                .build();
        builder.register();
    }

    @Override
    public void onDisable() {
        bossApi.unload();
        builder.unregister();
    }
}
