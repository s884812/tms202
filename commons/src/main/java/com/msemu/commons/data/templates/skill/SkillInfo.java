package com.msemu.commons.data.templates.skill;

import com.msemu.commons.data.enums.Element;
import com.msemu.commons.data.enums.SkillStat;
import com.msemu.commons.data.loader.dat.DatSerializable;
import com.msemu.commons.utils.StringUtils;
import com.msemu.commons.utils.types.Rect;
import lombok.Getter;
import lombok.Setter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Weber on 2018/4/23.
 */
@Getter
@Setter
public class SkillInfo implements DatSerializable {

    private String name = "", desc = "";
    private int rootId, skillId;
    private int maxLevel, currentLevel, masterLevel, fixLevel;
    private int skillType = -1, eventTamingMob = 0, vehicleID = 0, hyper = 0, hyperStat = 0, reqLev = 0, setItemReason = 0, setItemPartsCount = 0;
    private boolean massSpell, invisible = false, notRemoved = false, timeLimited = false, combatOrders = false, psd = false, vSkill = false, petPassive = false;
    // info prop
    private boolean pvp = true, magicDamage, casterMove, pushTarget, pullTarget;
    private boolean keyDownSkill, summonSkill;
    private List<ReqSkill> reqSkill = new ArrayList<>();
    private Element elemAttr = Element.NEUTRAL;
    private List<Integer> psdSkills = new ArrayList<>();
    private List<Rect> rects = new ArrayList<>();
    private Map<SkillStat, String> skillStatInfo = new HashMap<>();

    public void addSkillStatInfo(SkillStat sc, String value) {
        getSkillStatInfo().put(sc, value);
    }

    public int getValue(SkillStat skillStat, int slv) {
        int result = 0;
        String value = getSkillStatInfo().get(skillStat);
        if (value == null) {
            return 0;
        }
        if (StringUtils.isNumber(value)) {
            result = Integer.parseInt(value);
        } else {
            result = evaluateValue(value, slv);
        }
        return result;
    }

    private int evaluateValue(String expression, int slv) {
        int result = 0;
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
        try {
            expression = expression.replace("u", "Math.ceil");
            expression = expression.replace("d", "Math.floor");
            Object res = engine.eval(expression.replace("x", slv + ""));
            if (res instanceof Integer) {
                result = (Integer) res;
            } else if (res instanceof Double) {
                result = ((Double) res).intValue();
            }
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void addRect(Rect rect) {
        getRects().add(rect);
    }

    public Rect getLastRect() {
        return rects != null && rects.size() > 0 ? rects.get(rects.size() - 1) : null;
    }

    public Rect getFirstRect() {
        return rects != null && rects.size() > 0 ? rects.get(0) : null;
    }

    public void addPsdSkill(int skillID) {
        getPsdSkills().add(skillID);
    }

    public void addReqSkill(ReqSkill req) {
        getReqSkill().add(req);
    }

    @Override
    public String toString() {
        return String.format("[技能] %s(%d) - %s", name, skillId, desc);
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        dos.writeUTF(this.name);
        dos.writeUTF(this.desc);
        dos.writeInt(this.rootId);
        dos.writeInt(this.skillId);
        dos.writeInt(this.maxLevel);
        dos.writeInt(this.currentLevel);
        dos.writeInt(this.masterLevel);
        dos.writeInt(this.fixLevel);
        dos.writeInt(this.skillType);
        dos.writeInt(this.eventTamingMob);
        dos.writeInt(this.vehicleID);
        dos.writeInt(this.hyper);
        dos.writeInt(this.hyperStat);
        dos.writeInt(this.reqLev);
        dos.writeInt(this.setItemReason);
        dos.writeInt(this.setItemPartsCount);
        dos.writeBoolean(this.massSpell);
        dos.writeBoolean(this.invisible);
        dos.writeBoolean(this.notRemoved);
        dos.writeBoolean(this.timeLimited);
        dos.writeBoolean(this.combatOrders);
        dos.writeBoolean(this.psd);
        dos.writeBoolean(this.vSkill);
        dos.writeBoolean(this.petPassive);
        dos.writeBoolean(this.pvp);
        dos.writeBoolean(this.magicDamage);
        dos.writeBoolean(this.casterMove);
        dos.writeBoolean(this.pushTarget);
        dos.writeBoolean(this.pullTarget);
        dos.writeBoolean(this.keyDownSkill);
        dos.writeBoolean(this.summonSkill);
        dos.writeInt(reqSkill.size());
        for (ReqSkill req : reqSkill) {
            req.write(dos);
        }
        dos.writeUTF(this.elemAttr.name());
        dos.writeInt(psdSkills.size());
        for (Integer value : psdSkills) {
            dos.writeInt(value);
        }
        dos.writeInt(rects.size());
        for (Rect rect : rects) {
            dos.writeInt(rect.getLeft());
            dos.writeInt(rect.getTop());
            dos.writeInt(rect.getRight());
            dos.writeInt(rect.getBottom());
        }
        dos.writeInt(skillStatInfo.size());
        for (Map.Entry<SkillStat, String> entry : skillStatInfo.entrySet()) {
            dos.writeUTF(entry.getKey().name());
            dos.writeUTF(entry.getValue());
        }
    }

    @Override
    public DatSerializable load(DataInputStream dis) throws IOException {
        setName(dis.readUTF());
        setDesc(dis.readUTF());
        setRootId(dis.readInt());
        setSkillId(dis.readInt());
        setMaxLevel(dis.readInt());
        setCurrentLevel(dis.readInt());
        setMasterLevel(dis.readInt());
        setFixLevel(dis.readInt());
        setSkillType(dis.readInt());
        setEventTamingMob(dis.readInt());
        setVehicleID(dis.readInt());
        setHyper(dis.readInt());
        setHyperStat(dis.readInt());
        setReqLev(dis.readInt());
        setSetItemReason(dis.readInt());
        setSetItemPartsCount(dis.readInt());
        setMassSpell(dis.readBoolean());
        setInvisible(dis.readBoolean());
        setNotRemoved(dis.readBoolean());
        setTimeLimited(dis.readBoolean());
        setCombatOrders(dis.readBoolean());
        setPsd(dis.readBoolean());
        setVSkill(dis.readBoolean());
        setPetPassive(dis.readBoolean());
        setPvp(dis.readBoolean());
        setMagicDamage(dis.readBoolean());
        setCasterMove(dis.readBoolean());
        setPushTarget(dis.readBoolean());
        setPullTarget(dis.readBoolean());
        setKeyDownSkill(dis.readBoolean());
        setSummonSkill(dis.readBoolean());
        int reqSkilSize = dis.readInt();
        for (int i = 0; i < reqSkilSize; i++) {
            getReqSkill().add((ReqSkill) new ReqSkill().load(dis));
        }
        setElemAttr(Element.valueOf(dis.readUTF()));
        int psdSkillSize = dis.readInt();
        for (int i = 0; i < psdSkillSize; i++) {
            getPsdSkills().add(dis.readInt());
        }
        int rectsSize = dis.readInt();
        for (int i = 0; i < rectsSize; i++) {
            getRects().add(new Rect(dis.readInt(), dis.readInt(), dis.readInt(), dis.readInt()));
        }
        int skillStatSize = dis.readInt();
        for (int i = 0; i < skillStatSize; i++) {
            getSkillStatInfo().put(SkillStat.valueOf(dis.readUTF()), dis.readUTF());
        }
        return this;
    }
}