package ru.saydov.bosses.api.hologram.model.lines;

import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.saydov.bosses.api.entity.interfaces.PacketEntity;
import ru.saydov.bosses.api.hologram.model.Hologram;

/**
 * @author saydov
 */
public interface HologramLine {

    @NonNull PacketEntity getEntity();
    @NonNull Hologram getHologram();
    @NonNull Location getLocation();

    double getLineModifier();

    void showTo(final Player player);
    void hideFrom(final Player player);

    void spawn();
    void remove();

}
