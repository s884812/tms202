package com.msemu.core.network.packets.outpacket.user;

import com.msemu.commons.enums.OutHeader;
import com.msemu.commons.network.packets.OutPacket;
import com.msemu.core.network.GameClient;
import com.msemu.world.client.character.AvatarLook;
import com.msemu.world.client.character.Character;
import com.msemu.world.client.character.CharacterStat;
import com.msemu.world.client.character.skill.CharacterTemporaryStat;
import com.msemu.world.client.character.skill.TSIndex;
import com.msemu.world.client.character.skill.TemporaryStatManager;
import com.msemu.world.client.guild.Guild;
import com.msemu.world.client.field.lifes.Pet;
import com.msemu.world.constants.MapleJob;

/**
 * Created by Weber on 2018/4/12.
 */
public class LP_UserEnterField extends OutPacket<GameClient> {

    public LP_UserEnterField(Character chr) {
        super(OutHeader.LP_UserEnterField);
        CharacterStat cs = chr.getAvatarData().getCharacterStat();
        AvatarLook look = chr.getAvatarData().getAvatarLook();
        TemporaryStatManager tsm = chr.getTemporaryStatManager();

        encodeInt(chr.getId());
        encodeByte(chr.getLevel());
        encodeString(chr.getName());
        // m_sParentName
        encodeString("m_sParentName");
        if (chr.getGuild() != null) {
            chr.getGuild().encodeForRemote(this);
        } else {
            Guild.defaultEncodeForRemote(this);
        }
        encodeByte(cs.getGender());
        encodeInt(cs.getPop());
        encodeInt(0); // nNameTagMark
        tsm.encodeForRemote(this);
        encodeShort(chr.getJob());
        encodeShort(cs.getSubJob());
        encodeInt(chr.getTotalChuc());
        encodeInt(0);
        look.encode(this);
        if (MapleJob.is神之子(chr.getJob())) {
            chr.getAvatarData().getZeroAvatarLook().encode(this);
        }
        encodeInt(chr.getDriverID());
        encodeInt(chr.getPassengerID()); // dwPassenserID

        // TODO
        encodeInt(0);
        encodeInt(0);
        int size = 0;
        encodeInt(size);
        for (int i = 0; i < size; i++) {
            encodeInt(0);
            encodeInt(0);
        }
        // TODO 
        encodeInt(chr.getChocoCount());
        encodeInt(chr.getActiveEffectItemID());
        encodeInt(chr.getMonkeyEffectItemID());
        encodeInt(chr.getActiveNickItemID());
        encodeByte(false);
        encodeInt(chr.getDamageSkin());
        encodeInt(0); // ptPos.x?
        encodeInt(look.getDemonWingID());
        encodeInt(look.getKaiserWingID());
        encodeInt(look.getKaiserTailID());
        encodeInt(chr.getCompletedSetItemID());
        encodeShort(chr.getFieldSeatID());
        encodeString("");
        encodeString("");
        encodeShort(-1);
        encodeShort(-1);
        encodeByte(false);
        encodeInt(chr.getPortableChairID());
        boolean hasPortableChairMsg = chr.getPortableChairMsg() != null;
        encodeInt(hasPortableChairMsg ? 1 : 0); // why is this an int
        if (hasPortableChairMsg) {
            encodeString(chr.getPortableChairMsg());
        }
        int towerIDSize = 0;
        encodeInt(towerIDSize);
        for (int i = 0; i < towerIDSize; i++) {
            encodeInt(0); // towerChairID
        }
        // sub_81F990
        encodeByte(false);
        encodeInt(0);
        encodeInt(0);
        encodePosition(chr.getPosition());
        encodeByte(chr.getAction());
        encodeShort(chr.getFoothold());
        encodeByte(0);
        for (Pet pet : chr.getPets()) {
            if (pet.getId() == 0) {
                continue;
            }
            encodeByte(1);
            encodeInt(pet.getIdx());
            pet.encode(this);
        }
        encodeByte(chr.getMechanicHue());
        encodeInt(chr.getTamingMobLevel());
        encodeInt(chr.getTamingMobExp());
        encodeInt(chr.getTamingMobFatigue());
        byte miniRoomType = chr.getMiniRoom() != null ? chr.getMiniRoom().getType() : 0;
        encodeByte(miniRoomType);
        if (miniRoomType > 0) {
            chr.getMiniRoom().encode(this);
        }
        encodeByte(chr.getADBoardRemoteMsg() != null);
        if (chr.getADBoardRemoteMsg() != null) {
            encodeString(chr.getADBoardRemoteMsg());
        }


        encodeByte(chr.isInCouple());
        if (chr.isInCouple()) {
            chr.getCouple().encodeForRemote(this);
        }
        encodeByte(chr.hasFriendshipItem());
        if (chr.hasFriendshipItem()) {
            chr.getFriendshipRingRecord().forEach(record -> record.encode(this));
        }
        encodeByte(chr.isMarried());
        if (chr.isMarried()) {
            chr.getMarriageRecord().encodeForRemote(this);
        }
        encodeByte(0); // some flag that shows uninteresting things for now
//        int m_nDelayedEffectFlag = chr.getStat().Berserk ? 1 : 0;
//        mplew.write(m_nDelayedEffectFlag);
//        if ((m_nDelayedEffectFlag & 1) != 0) {
//        }
//        if ((m_nDelayedEffectFlag & 2) != 0) {
//        }
//        if ((m_nDelayedEffectFlag & 8) != 0) {
//            mplew.writeInt(0); // m_tDelayedPvPEffectTime
//        } else if ((m_nDelayedEffectFlag & 0x10) != 0) {
//            mplew.writeInt(0); // m_tDelayedPvPEffectTime
//        }
//        if ((m_nDelayedEffectFlag & 0x20) != 0) {
//            mplew.writeInt(0); // m_tHitPeriodRemain_Revive
//        }
//        mplew.writeInt(chr.getMount().getItemId()); // m_nEvanDragonGlide_Riding 骑宠id
        encodeInt(chr.getEvanDragonGlide());
        if (MapleJob.is凱撒(chr.getJob())) {
            encodeInt(chr.getKaiserMorphRotateHueExtern());
            encodeInt(chr.getKaiserMorphPrimiumBlack());
            encodeByte(chr.getKaiserMorphRotateHueInnner());
        }
        encodeInt(chr.getMakingMeisterSkillEff());
        // chr.getFarmUserInfo().encode(this);
        for (int i = 0; i < 5; i++) {
            encodeByte(-1); // activeEventNameTag
        }

        encodeInt(chr.getCustomizeEffect());
        if (chr.getCustomizeEffect() > 0) {
            encodeString(chr.getCustomizeEffectMsg());
        }
        encodeByte(chr.getSoulEffect());
        if (tsm.hasStat(CharacterTemporaryStat.RideVehicle)) {
            int vehicleID = tsm.getTSBByTSIndex(TSIndex.RideVehicle).getNOption();
            if (vehicleID == 1932249) { // is_mix_vehicle
                size = 0;
                encodeInt(size); // ???
                for (int i = 0; i < size; i++) {
                    encodeInt(0);
                }
            }
        }
        encodeByte(false); // Flashfire
//        boolean v90 = false;
//        mplew.write(v90);
//        if (v90) {
//            boolean v91 = false;
//            mplew.write(v91);
//            if (v91) {
//                mplew.writeInt(0);
//                mplew.writeInt(0);
//                mplew.writeShort(0);
//                mplew.writeShort(0);
//            }
//        }
        encodeByte(false); // StarPlanetRank::Decode
        encodeInt(0); // for CUser::DecodeStarPlanetTrendShopLook
        encodeInt(0); // for CUser::DecodeTextEquipInfo
        chr.getFreezeHotEventInfo().encode(this);
        encodeInt(chr.getEventBestFriendAID()); // CUser::DecodeEventBestFriendInfo
        encodeByte(tsm.hasStat(CharacterTemporaryStat.KinesisPsychicEnergeShield));
        encodeByte(chr.isBeastFormWingOn());
        encodeInt(chr.getMesoChairCount());
        encodeInt(1051291);

        encodeInt(0);
        encodeInt(0);

        encodeInt(0); // for
        encodeInt(0); // for
        encodeInt(0); // for

        encodeInt(0);

    }
}