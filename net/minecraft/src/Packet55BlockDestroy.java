package net.minecraft.src;

import java.io.*;

public class Packet55BlockDestroy extends Packet
{
    private int field_56553_a;
    private int field_56551_b;
    private int field_56552_c;
    private int field_56549_d;
    private int field_56550_e;

    public Packet55BlockDestroy()
    {
    }

    public Packet55BlockDestroy(int par1, int par2, int par3, int par4, int par5)
    {
        field_56553_a = par1;
        field_56551_b = par2;
        field_56552_c = par3;
        field_56549_d = par4;
        field_56550_e = par5;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        field_56553_a = par1DataInputStream.readInt();
        field_56551_b = par1DataInputStream.readInt();
        field_56552_c = par1DataInputStream.readInt();
        field_56549_d = par1DataInputStream.readInt();
        field_56550_e = par1DataInputStream.read();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(field_56553_a);
        par1DataOutputStream.writeInt(field_56551_b);
        par1DataOutputStream.writeInt(field_56552_c);
        par1DataOutputStream.writeInt(field_56549_d);
        par1DataOutputStream.write(field_56550_e);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.func_56713_a(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 13;
    }

    public int func_56548_b()
    {
        return field_56553_a;
    }

    public int func_56547_c()
    {
        return field_56551_b;
    }

    public int func_56546_d()
    {
        return field_56552_c;
    }

    public int func_56545_e()
    {
        return field_56549_d;
    }

    public int func_56544_f()
    {
        return field_56550_e;
    }
}
