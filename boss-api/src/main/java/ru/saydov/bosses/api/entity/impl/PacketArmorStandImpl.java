package ru.saydov.bosses.api.entity.impl;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import ru.saydov.bosses.api.entity.interfaces.PacketArmorStand;
import ru.saydov.bosses.api.entity.interfaces.PacketEntity;

/**
 * @author saydov
 */
@Getter
public class PacketArmorStandImpl extends PacketEntityBaseImpl implements PacketArmorStand {

    private PacketArmorStandImpl(@NonNull Location location) {
        super(location);
    }

    public static @NonNull PacketEntity create(final @NonNull Location location) {
        return new PacketArmorStandImpl(location);
    }

    @Override
    public @NonNull EntityType getEntityType() {
        return EntityType.ARMOR_STAND;
    }

    boolean small = false;

    @Override
    public void setSmall(boolean small) {
        if (this.small != small) {
            this.small = small;
            sendMetadata();
        }
    }

    boolean invisible = true;

    @Override
    public boolean isInvisible() {
        return invisible;
    }

    @Override
    public void setInvisible(boolean invisible) {
        if (this.invisible != invisible) {
            this.invisible = invisible;
            sendMetadata();
        }
    }

    boolean arms = false;

    @Override
    public boolean hasArms() {
        return arms;
    }

    @Override
    public void setArms(boolean arms) {
        if (this.arms != arms) {
            this.arms = arms;
            sendMetadata();
        }
    }

}
