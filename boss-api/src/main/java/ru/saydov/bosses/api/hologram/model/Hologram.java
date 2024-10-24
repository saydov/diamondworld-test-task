package ru.saydov.bosses.api.hologram.model;

import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;
import ru.saydov.bosses.api.hologram.model.lines.HologramLine;

import java.util.List;
import java.util.Set;

/**
 * @author saydov
 */
public interface Hologram {

    @NotNull Location getLocation();

    @NotNull @UnmodifiableView List<HologramLine> getLines();

    void addLine(final @NonNull HologramLine hologramLine);

    void addLine(final int index, final @NonNull HologramLine hologramLine);

    void removeLine(final @NonNull HologramLine hologramLine);

    void removeLine(final int index);

    void clearLines();

    @NotNull @UnmodifiableView Set<Player> getViewers();

    void showTo(final @NonNull Player player);

    void hideFrom(final @NonNull Player player);

    void spawn();

    void remove();

}
