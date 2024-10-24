package ru.saydov.bosses.api.entity.interfaces;

/**
 * @author saydov
 */
public interface PacketArmorStand {

    void isSmall();

    void setSmall(final boolean small);

    boolean isInvisible();

    void setInvisible(final boolean invisible);

    void setArms(final boolean arms);

    boolean hasArms();

}
