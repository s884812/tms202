package com.msemu.world.client.character.party.operations;

import com.msemu.commons.network.packets.OutPacket;
import com.msemu.core.network.GameClient;
import com.msemu.world.enums.PartyOperation;

/**
 * Created by Weber on 2018/5/4.
 */
public class ChangePartyBossNotSameChannelResponse implements IPartyResult {
    @Override
    public PartyOperation getType() {
        return PartyOperation.PartyRes_ChangePartyBoss_NotSameChannel;
    }

    @Override
    public void encode(OutPacket<GameClient> outPacket) {

    }
}
