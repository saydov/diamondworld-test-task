package ru.saydov.bosses.api.hologram.model.lines;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.saydov.bosses.api.entity.impl.PacketArmorStandImpl;
import ru.saydov.bosses.api.entity.interfaces.PacketEntity;
import ru.saydov.bosses.api.hologram.model.Hologram;

/**
 * @author saydov
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TextHologramLine implements HologramLine {

    @NotNull String text;

    @NotNull Location location;

    @NotNull Hologram hologram;

    @NotNull PacketEntity entity;

    private TextHologramLine(final @NonNull String text,
                             final @NonNull Location location,
                             final @NonNull Hologram hologram) {
        this.text = text;
        this.location = location;
        this.hologram = hologram;

        this.entity = PacketArmorStandImpl.create(location);

        entity.setCustomNameVisible(true);
        entity.setCustomName(text);
    }

    @Override
    public double getLineModifier() {
        return 0.25;
    }

    @Override
    public void showTo(Player player) {
        entity.spawn(player);
    }

    @Override
    public void hideFrom(Player player) {
        entity.remove(player);
    }

}
