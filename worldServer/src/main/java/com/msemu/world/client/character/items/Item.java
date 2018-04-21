package com.msemu.world.client.character.items;

import com.msemu.commons.network.packets.OutPacket;
import com.msemu.commons.utils.types.FileTime;
import com.msemu.world.constants.ItemConstants;
import com.msemu.world.enums.InvType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Weber on 2018/4/11.
 */
@Entity
@Table(name = "itemTemplates")
@Inheritance(strategy = InheritanceType.JOINED)
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "inventoryId")
    protected int inventoryId;
    @Column(name = "itemId")
    protected int itemId;
    @Column(name = "bagIndex")
    protected int bagIndex;
    @Column(name = "cashItemSerialNumber")
    protected long cashItemSerialNumber;
    @JoinColumn(name = "dateExpire")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    protected FileTime dateExpire = FileTime.getFileTimeFromType(FileTime.Type.PERMANENT);
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "invType")
    protected InvType invType;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    protected Type type;
    @Column(name = "isCash")
    protected boolean isCash;
    @Column(name = "quantity")
    protected int quantity;
    @Column(name = "owner")
    private String owner = "";

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void drop() {
        setBagIndex(0);
    }

    public void addQuantity(int amount) {
        if (amount > 0 && amount + getQuantity() > 0) {
            setQuantity(getQuantity() + amount);
        }
    }

    public void removeQuantity(int amount) {
        if (amount > 0) {
            setQuantity(Math.max(0, getQuantity() - amount));
        }
    }

    public enum Type {
        EQUIP(1),
        ITEM(2),
        PET(3);

        private byte val;

        Type(byte val) {
            this.val = val;
        }

        Type(int val) {
            this((byte) val);
        }

        public byte getValue() {
            return val;
        }

        public static Type getTypeById(int id) {
            return Arrays.stream(Type.values()).filter(type -> type.getValue() == id).findFirst().orElse(null);
        }
    }

    public Item() {
    }

    public Item(int itemId, int bagIndex, long cashItemSerialNumber, FileTime dateExpire, InvType invType,
                boolean isCash, Type type) {
        this.itemId = itemId;
        this.bagIndex = bagIndex;
        this.cashItemSerialNumber = cashItemSerialNumber;
        this.dateExpire = dateExpire;
        this.invType = invType;
        this.isCash = isCash;
        this.type = type;
    }

    public int getItemId() {
        return itemId;
    }

    public int getBagIndex() {
        return bagIndex;
    }

    public void setBagIndex(int bagIndex) {
        this.bagIndex = Math.abs(bagIndex);
    }

    public long getCashItemSerialNumber() {
        return cashItemSerialNumber;
    }

    public FileTime getDateExpire() {
        return dateExpire;
    }

    public InvType getInvType() {
        return invType;
    }

    public Type getType() {
        return type;
    }

    public boolean isCash() {
        return isCash;
    }


    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setCashItemSerialNumber(long cashItemSerialNumber) {
        this.cashItemSerialNumber = cashItemSerialNumber;
    }

    public void setDateExpire(FileTime dateExpire) {
        this.dateExpire = dateExpire;
    }

    public void setInvType(InvType invType) {
        this.invType = invType;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setCash(boolean cash) {
        isCash = cash;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Id: " + getId() + ", ItemId: " + getItemId() + ", Qty: " + getQuantity() + ", InvType: " + getInvType()
                + ", BagIndex: " + getBagIndex();
    }

    public void encode(OutPacket outPacket) {
        outPacket.encodeByte(getType().getValue());
        // GW_ItemSlotBase
        outPacket.encodeInt(getItemId());
        boolean hasSN = getCashItemSerialNumber() > 0;
        outPacket.encodeByte(hasSN);
        if (hasSN) {
            outPacket.encodeLong(getId());
        }
        getDateExpire().encode(outPacket);
        outPacket.encodeInt(getBagIndex());
        if (getType() == Type.ITEM) {
            outPacket.encodeShort(getQuantity()); // nQuantity
            outPacket.encodeString(getOwner()); // sOwner
            outPacket.encodeShort(0); // flag
            if (ItemConstants.類型.可充值道具(getItemId()) || ItemConstants.類型.isFamiliar(getItemId()) ||
                    getItemId() / 10000 == 302) {
                outPacket.encodeLong(getInventoryId());
            }
            // TODO 萌寵
            outPacket.encodeInt(0); // familiarId
            outPacket.encodeShort(0); // level
            outPacket.encodeShort(0); // level
            outPacket.encodeShort(0); // level
            outPacket.encodeShort(0); // option 1
            outPacket.encodeShort(0); // option 2
            outPacket.encodeShort(0); // option 3
            outPacket.encodeByte(0); // grade
        } else if (getType() == Type.PET) {
            // TODO 寵物
            outPacket.encodeString("", 13); // name
            outPacket.encodeByte(0); // level
            outPacket.encodeShort(0); // 親密度
            outPacket.encodeByte(0); // 飢餓
            outPacket.encodeFT(new FileTime(-1)); // 期限
            outPacket.encodeShort(0); // nPetAttribute
            outPacket.encodeShort(0); // uPetSkill flag
            outPacket.encodeInt(0); // nRemainLife
            outPacket.encodeByte(0); // nActiveStat
            outPacket.encodeInt(0); // nAutoBuffSkill buff技能
            // nPetHue
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            outPacket.encodeInt(-1);
            //nGiantRate
            outPacket.encodeShort(100);
        }
    }

}
