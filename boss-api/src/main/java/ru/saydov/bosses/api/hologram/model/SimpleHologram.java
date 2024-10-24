package ru.saydov.bosses.api.hologram.model;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;
import ru.saydov.bosses.api.hologram.model.lines.HologramLine;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author saydov
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SimpleHologram implements Hologram {

    @NotNull Location location;

    @NotNull List<HologramLine> hologramLines;

    @NotNull Set<Player> players;

    public static @NotNull SimpleHologram create(final @NonNull Location location) {
        return new SimpleHologram(location, new ObjectArrayList<>(), new ObjectOpenHashSet<>());
    }

    @Override
    public @NotNull @UnmodifiableView List<HologramLine> getLines() {
        return Collections.unmodifiableList(hologramLines);
    }

    @Override
    public void addLine(final @NonNull HologramLine hologramLine) {
        hologramLines.add(hologramLine);
    }

    @Override
    public void addLine(int index, @NonNull HologramLine hologramLine) {
        if (index < 0 || index > hologramLines.size()) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        hologramLines.add(index, hologramLine);
    }

    @Override
    public void removeLine(@NonNull HologramLine hologramLine) {
        hologramLine.remove();
        hologramLines.remove(hologramLine);
    }

    @Override
    public void removeLine(int index) {
        if (index < 0 || index >= hologramLines.size()) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        val hologramLine = hologramLines.get(index);
        hologramLine.remove();

        hologramLines.remove(index);
    }

    @Override
    public void clearLines() {
        hologramLines.forEach(HologramLine::remove);
        hologramLines.clear();
    }

    @Override
    public @NotNull @UnmodifiableView Set<Player> getViewers() {
        return Collections.unmodifiableSet(players);
    }

    @Override
    public void showTo(@NonNull Player player) {
        hologramLines.forEach(hologramLine -> hologramLine.showTo(player));
        players.add(player);
    }

    @Override
    public void hideFrom(@NonNull Player player) {
        hologramLines.forEach(hologramLine -> hologramLine.hideFrom(player));
        players.remove(player);
    }

    @Override
    public void spawn() {
        hologramLines.forEach(HologramLine::spawn);
    }

    @Override
    public void remove() {
        hologramLines.forEach(HologramLine::remove);
    }
}
