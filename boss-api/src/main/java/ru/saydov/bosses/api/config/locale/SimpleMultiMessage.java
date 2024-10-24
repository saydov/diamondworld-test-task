package ru.saydov.bosses.api.config.locale;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author saydov
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SimpleMultiMessage implements MultiMessage {

    @NotNull List<String> lines;

    public static @NotNull SimpleMultiMessage create(final @NonNull List<String> lines) {
        return new SimpleMultiMessage(lines);
    }

    @Override
    public @NotNull List<@NotNull String> getLines() {
        return lines;
    }

    @Override
    public @NotNull String getJoinedLines() {
        return String.join("\n", lines);
    }

    @Override
    public @NotNull String asSingleLine() {
        return String.join(" ", lines);
    }

    @Override
    public @NotNull Message format(final @NonNull Object... args) {
        List<String> cloned = new ArrayList<>(lines);

        for (int i = 0; i < cloned.size(); i++) {
            for (int j = 0; j < args.length; j += 2) {
                if (args.length <= j + 1) break;

                val value = args[j + 1].toString();
                cloned.set(i, cloned.get(i)
                        .replaceAll("%" + args[j] + "%", value));
            }
        }

        return SimpleMultiMessage.create(cloned);
    }

    @Override
    public void send(final @NonNull CommandSender sender) {
        lines.forEach(sender::sendMessage);
    }

}
