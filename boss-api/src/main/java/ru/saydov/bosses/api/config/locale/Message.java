package ru.saydov.bosses.api.config.locale;

import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author saydov
 */
public interface Message {

    List<@NotNull String> getLines();

    @NotNull String getJoinedLines();

    @NotNull String asSingleLine();

    @NotNull Message format(final @NonNull Object... args);

    void send(final @NonNull CommandSender sender);

}
