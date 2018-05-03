package com.msemu.commons.data.templates;


import com.msemu.commons.data.enums.ItemOptionStat;
import com.msemu.commons.data.loader.dat.DatSerializable;
import lombok.Getter;
import lombok.Setter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Weber on 2018/4/13.
 */
@Getter
@Setter
public class ItemOption implements DatSerializable {

    private int optionType;
    private String string = "";
    private int id;
    private int reqLevel;
    private String face = "";

    private Map<ItemOptionStat, Integer> otherData = new HashMap<>();

    public void setLevelData(ItemOptionStat type, Integer value) {
        otherData.put(type, value);
    }

    @Override
    public String toString() {
        return "id: " + getId() + ", optionType: " + getOptionType() + " : " + getString();
    }

    public static ItemOptionStat[] getTypes() {
        return ItemOptionStat.values();
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        dos.writeInt(this.optionType);
        dos.writeUTF(this.string);
        dos.writeInt(this.id);
        dos.writeInt(this.reqLevel);
        dos.writeUTF(this.face);
        dos.writeInt(otherData.size());
        for (Map.Entry<ItemOptionStat, Integer> entry : this.otherData.entrySet()) {
            dos.writeUTF(entry.getKey().name());
            dos.writeInt(entry.getValue());
        }
    }

    @Override
    public DatSerializable load(DataInputStream dis) throws IOException {
        this.setOptionType(dis.readInt());
        this.setString(dis.readUTF());
        this.setId(dis.readInt());
        this.setReqLevel(dis.readInt());
        this.setFace(dis.readUTF());
        int otherDataSize = dis.readInt();
        for (int i = 0; i < otherDataSize; i++) {
            String name = dis.readUTF();
            Integer value = dis.readInt();
            ItemOptionStat stat = ItemOptionStat.valueOf(name);
            this.getOtherData().put(stat, value);
        }
        return this;
    }
}