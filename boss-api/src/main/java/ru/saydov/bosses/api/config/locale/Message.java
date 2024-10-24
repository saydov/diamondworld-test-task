package ru.saydov.bosses.api.config.locale;

import lombok.NonNull;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * @author saydov
 */
public interface Message {

    List<@NonNull String> getLines();

    @NonNull String getJoinedLines();

    @NonNull String asSingleLine();

    @NonNull Message format(final @NonNull Object... args);

    void send(final @NonNull CommandSender sender);

}
