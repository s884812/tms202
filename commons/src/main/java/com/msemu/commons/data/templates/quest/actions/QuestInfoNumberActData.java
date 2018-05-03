package com.msemu.commons.data.templates.quest.actions;

import com.msemu.commons.data.enums.QuestActDataType;
import com.msemu.commons.data.loader.dat.DatSerializable;
import lombok.Getter;
import lombok.Setter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Weber on 2018/4/22.
 */
public class QuestInfoNumberActData extends QuestActData {

    @Getter
    @Setter
    private int infoNumber;

    @Override
    public QuestActDataType getType() {
        return QuestActDataType.infoNumber;
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        dos.writeInt(infoNumber);
    }

    @Override
    public DatSerializable load(DataInputStream dis) throws IOException {
        setInfoNumber(dis.readInt());
        return this;
    }
}