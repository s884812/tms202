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

package com.msemu.commons.data.enums;

/**
 * Created by Weber on 2018/4/25.
 */
public enum Element {

    NEUTRAL(0),
    PHYSICAL(1),
    FIRE(2, true),
    ICE(3, true),
    LIGHTING(4),
    POISON(5),
    HOLY(6, true),
    DARKNESS(7);

    private final int value;
    private boolean special = false;

    private Element(final int v) {
        this.value = v;
    }

    private Element(final int v, final boolean special) {
        this.value = v;
        this.special = special;
    }

    public static Element getFromChar(final String elemAttr) {
        switch (Character.toUpperCase(elemAttr.charAt(0))) {
            case 'F':
                return FIRE;
            case 'I':
                return ICE;
            case 'L':
                return LIGHTING;
            case 'S':
                return POISON;
            case 'H':
                return HOLY;
            case 'P':
                return PHYSICAL;
            case 'D': // Added on v.92 MSEA
                return DARKNESS;
        }
        throw new IllegalArgumentException("unknown elemnt char " + elemAttr);
    }

    public static Element getFromId(final int value) {
        for (Element e : Element.values()) {
            if (e.value == value) {
                return e;
            }
        }
        throw new IllegalArgumentException("unknown elemnt id " + value);
    }

    public int getValue() {
        return this.value;
    }

    public boolean isSpecial() {
        return special;
    }
}
