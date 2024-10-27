package ru.saydov.bosses.api.hologram.model.lines;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.saydov.bosses.api.entity.interfaces.PacketEntity;
import ru.saydov.bosses.api.hologram.model.Hologram;

/**
 * @author saydov
 */
public interface HologramLine {

    @NotNull PacketEntity getEntity();
    @NotNull Hologram getHologram();
    @NotNull Location getLocation();

    double getLineModifier();

    void showTo(final Player player);
    void hideFrom(final Player player);

    void remove();

}
