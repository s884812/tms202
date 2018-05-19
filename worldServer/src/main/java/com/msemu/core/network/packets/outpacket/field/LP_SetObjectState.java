package com.msemu.core.network.packets.outpacket.field;

import com.msemu.commons.enums.OutHeader;
import com.msemu.commons.network.packets.OutPacket;
import com.msemu.core.network.GameClient;

/**
 * Created by Weber on 2018/5/12.
 */
public class LP_SetObjectState extends OutPacket<GameClient> {

    public LP_SetObjectState(String objectName, int state) {
        super(OutHeader.LP_SetObjectState);
        encodeString(objectName);
        encodeInt(state);
    }
}