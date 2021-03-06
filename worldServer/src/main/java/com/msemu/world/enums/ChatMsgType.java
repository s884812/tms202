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

package com.msemu.world.enums;

/**
 * Created by Weber on 2018/4/13.
 */
public enum ChatMsgType {
    // 普聊 (白)
    NORMAL(0),
    // 密語 (綠)
    WHISPER(1),
    // 隊伍聊天 (粉紅)
    GROUP_PARTY(2),
    GROUP_FRIEND(3),
    GROUP_GUILD(4),
    GROUP_ALLIANCE(5),
    // 灰色
    GAME_DESC(6),
    // 黃色
    TIP(7),
    NOTICE(8),
    // 淡藍色
    NOTICE_2(9),
    // 白底黑字
    ADMIN_CHAT(10),
    // 粉紅
    SYSTEM(11),
    // 淡藍底深藍字
    SPEAKER_CHANNEL(12),
    // 粉紅底，有頻道
    SMEGA(13),
    SMEGA_2(14),
    ITEM_SMEGA(15),
    PURPLE(16),
    DARK_BLUE(16),
    ITEM_NOTICE(17),
    MEGA(18),
    // 黃色
    MOB(19),
    // 藍綠色
    CYAN(20),
    ITEM_NO_ITEM_SMEGA(21),
    SWEDEN(22),
    LOTTERY_ITEM_SPEAKER(23),
    LOTTERY_ITEM_SPEAKER_WORLD(24),
    AVATAR_MEGAPHONE(25),
    PICKUP_SPEAKER_WORLD(26),
    WORLD_NAME(27),
    BOSS_ARENA_NOTICE(28),
    CLAIM(29),
    DARK_RED(30),
    GACHA_REWARD(31),
    GACHA_RED(32),
    GACHA_RED_2(33), // same as GACHA_RED(32)
    DARK_BLUE_2(34), // same as DARK_BLUE(16)
    ITEM_NO_ITEM_SMEGA_DARK_TEXT(35),
    WHITE_ON_GREEN(36),
    CAKE_MEGAPHONE(37),
    PIE_MEGAPHONE(38),
    BLACK_ON_WHITE(39),;
    private short value;

    ChatMsgType(short value) {
        this.value = value;
    }

    ChatMsgType(int i) {
        this((short) i);
    }

    public short getValue() {
        return value;
    }
}
