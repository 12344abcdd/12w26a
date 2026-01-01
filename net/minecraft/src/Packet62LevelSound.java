package net.minecraft.src;

import java.io.*;

public class Packet62LevelSound extends Packet
{
    private String field_56582_a;
    private int field_56580_b;
    private int field_56581_c;
    private int field_56578_d;
    private float field_58071_e;
    private int field_56577_f;

    public Packet62LevelSound()
    {
        field_56581_c = 0x7fffffff;
    }

    public Packet62LevelSound(String par1Str, double par2, double par4, double par6, float par8, float par9)
    {
        field_56581_c = 0x7fffffff;
        field_56582_a = par1Str;
        field_56580_b = (int)(par2 * 8D);
        field_56581_c = (int)(par4 * 8D);
        field_56578_d = (int)(par6 * 8D);
        field_58071_e = par8;
        field_56577_f = (int)(par9 * 63F);

        if (field_56577_f < 0)
        {
            field_56577_f = 0;
        }

        if (field_56577_f > 255)
        {
            field_56577_f = 255;
        }
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        field_56582_a = readString(par1DataInputStream, 32);
        field_56580_b = par1DataInputStream.readInt();
        field_56581_c = par1DataInputStream.readInt();
        field_56578_d = par1DataInputStream.readInt();
        field_58071_e = par1DataInputStream.readFloat();
        field_56577_f = par1DataInputStream.readUnsignedByte();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        writeString(field_56582_a, par1DataOutputStream);
        par1DataOutputStream.writeInt(field_56580_b);
        par1DataOutputStream.writeInt(field_56581_c);
        par1DataOutputStream.writeInt(field_56578_d);
        par1DataOutputStream.writeFloat(field_58071_e);
        par1DataOutputStream.writeByte(field_56577_f);
    }

    public String func_56574_b()
    {
        return field_56582_a;
    }

    public double func_56575_c()
    {
        return (double)((float)field_56580_b / 8F);
    }

    public double func_56571_d()
    {
        return (double)((float)field_56581_c / 8F);
    }

    public double func_56576_e()
    {
        return (double)((float)field_56578_d / 8F);
    }

    public float func_56573_f()
    {
        return field_58071_e;
    }

    public float func_56572_g()
    {
        return (float)field_56577_f / 63F;
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.func_56714_a(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 24;
    }
}
