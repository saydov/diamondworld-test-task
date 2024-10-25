package ru.saydov.bosses.api.utils.protocol;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import lombok.experimental.UtilityClass;

/**
 * @author saydov
 */
@UtilityClass
public class ProtocolLibUtil {

    public static final ProtocolManager PROTOCOL_MANAGER;

    static {
        PROTOCOL_MANAGER = ProtocolLibrary.getProtocolManager();
    }

}
