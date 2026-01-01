package net.minecraft.src;

import java.io.*;

public class Packet2ClientProtocol extends Packet
{
    private int field_55173_a;
    private String field_55172_b;
    private String field_56586_c;
    private int field_56585_d;

    public Packet2ClientProtocol()
    {
    }

    public Packet2ClientProtocol(int par1, String par2Str, String par3Str, int par4)
    {
        field_55173_a = par1;
        field_55172_b = par2Str;
        field_56586_c = par3Str;
        field_56585_d = par4;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        field_55173_a = par1DataInputStream.readByte();
        field_55172_b = readString(par1DataInputStream, 16);
        field_56586_c = readString(par1DataInputStream, 255);
        field_56585_d = par1DataInputStream.readInt();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeByte(field_55173_a);
        writeString(field_55172_b, par1DataOutputStream);
        writeString(field_56586_c, par1DataOutputStream);
        par1DataOutputStream.writeInt(field_56585_d);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.func_55320_a(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 3 + 2 * field_55172_b.length();
    }

    public int func_56583_b()
    {
        return field_55173_a;
    }

    public String func_56584_c()
    {
        return field_55172_b;
    }
}
