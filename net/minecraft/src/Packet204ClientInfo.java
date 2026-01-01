package net.minecraft.src;

import java.io.*;

public class Packet204ClientInfo extends Packet
{
    private String field_55170_a;
    private int field_55168_b;
    private int field_55169_c;
    private boolean field_55167_d;
    private int field_56543_e;

    public Packet204ClientInfo()
    {
    }

    public Packet204ClientInfo(String par1Str, int par2, int par3, boolean par4, int par5)
    {
        field_55170_a = par1Str;
        field_55168_b = par2;
        field_55169_c = par3;
        field_55167_d = par4;
        field_56543_e = par5;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        field_55170_a = readString(par1DataInputStream, 7);
        field_55168_b = par1DataInputStream.readByte();
        byte byte0 = par1DataInputStream.readByte();
        field_55169_c = byte0 & 7;
        field_55167_d = (byte0 & 8) == 8;
        field_56543_e = par1DataInputStream.readByte();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        writeString(field_55170_a, par1DataOutputStream);
        par1DataOutputStream.writeByte(field_55168_b);
        par1DataOutputStream.writeByte(field_55169_c | (field_55167_d ? 1 : 0) << 3);
        par1DataOutputStream.writeByte(field_56543_e);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.func_55319_a(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 0;
    }

    public String func_56541_b()
    {
        return field_55170_a;
    }

    public int func_56542_c()
    {
        return field_55168_b;
    }

    public int func_56540_d()
    {
        return field_55169_c;
    }

    public boolean func_56539_e()
    {
        return field_55167_d;
    }

    public int func_56538_f()
    {
        return field_56543_e;
    }
}
