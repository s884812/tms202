/*
 * MIT License
 *
 * Copyright (c) 2018 msemu
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.msemu.world.client.character.stats;

import com.msemu.commons.data.enums.SkillStat;
import com.msemu.commons.data.templates.skill.SkillInfo;
import com.msemu.world.data.SkillData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Weber on 2018/4/11.
 */
public class Option {
    public int nOption = 0;
    public int rOption = 0;
    public int tOption = 0;
    public int xOption = 0;
    public int mOption = 0;
    public int wOption = 0;
    public int uOption = 0;
    public int zOption = 0;
    public int bOption = 0;
    public int sOption = 0;
    public int ssOption = 0;
    public int cOption = 0;
    public int yOption = 0;
    public int nReason = 0;
    public int nValue = 0;
    public int nKey = Integer.MAX_VALUE;
    public int tStart = 0;
    public int tTerm = 0;
    public int pOption = 0;
    public int slv = 0;
    public List<Option> extraOpts = new ArrayList<>();

    public Option(int skillID) {
        this.nReason = skillID;
        this.rOption = skillID;
    }

    public Option(int itemID, long duration) {
        // hack to have a constructorfor itemTemplates
        this.tTerm = (int) duration;
        this.nReason = itemID;
        this.rOption = itemID;
        this.tOption = (int) duration;
    }

    public Option(int skillID, byte slv) {
        SkillInfo si = SkillData.getInstance().getSkillInfoById(skillID);
        rOption = skillID;
        tOption = si.getValue(SkillStat.time, slv);
    }

    public Option() {
    }

    public Option deepCopy() {
        Option copy = new Option();
        copy.nOption = nOption;
        copy.rOption = rOption;
        copy.tOption = tOption;
        copy.xOption = xOption;
        copy.mOption = mOption;
        copy.wOption = wOption;
        copy.uOption = uOption;
        copy.zOption = zOption;
        copy.bOption = bOption;
        copy.sOption = sOption;
        copy.ssOption = ssOption;
        copy.cOption = cOption;
        copy.pOption = pOption;
        copy.yOption = yOption;
        copy.nReason = nReason;
        copy.nValue = nValue;
        copy.nKey = nKey;
        copy.tStart = tStart;
        copy.tTerm = tTerm;
        copy.slv = slv;
        extraOpts.forEach(o -> extraOpts.add(o.deepCopy()));
        return copy;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Option &&
                ((((Option) obj).rOption == rOption && ((Option) obj).rOption > 0) ||
                        ((((Option) obj).nReason == nReason && ((Option) obj).nReason > 0)));
    }

    @Override
    public String toString() {
        if (nReason == 0) {
            return "Indie: false, skill: " + rOption + ", val: " + nOption + ", time: " + tOption;
        } else {
            return "Indie: true, skill: " + nReason + ", val: " + nValue + ", time: " + tTerm;
        }
    }
}

