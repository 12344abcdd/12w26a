package net.minecraft.src;

import java.io.*;
import java.security.PublicKey;

public class Packet253ServerAuthData extends Packet
{
    private String field_55161_a;
    private PublicKey field_55160_b;
    private byte field_58067_c[];

    public Packet253ServerAuthData()
    {
        field_58067_c = new byte[0];
    }

    public Packet253ServerAuthData(String par1Str, PublicKey par2PublicKey, byte par3ArrayOfByte[])
    {
        field_58067_c = new byte[0];
        field_55161_a = par1Str;
        field_55160_b = par2PublicKey;
        field_58067_c = par3ArrayOfByte;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        field_55161_a = readString(par1DataInputStream, 20);
        field_55160_b = CryptManager.func_55334_a(func_55157_b(par1DataInputStream));
        field_58067_c = func_55157_b(par1DataInputStream);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        writeString(field_55161_a, par1DataOutputStream);
        func_55156_a(par1DataOutputStream, field_55160_b.getEncoded());
        func_55156_a(par1DataOutputStream, field_58067_c);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.func_55318_a(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 2 + field_55161_a.length() * 2 + 2 + field_55160_b.getEncoded().length + 2 + field_58067_c.length;
    }

    public String func_55159_b()
    {
        return field_55161_a;
    }

    public PublicKey func_55158_c()
    {
        return field_55160_b;
    }

    public byte[] func_58066_d()
    {
        return field_58067_c;
    }
}
