package net.minecraft.src;

import java.io.*;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.SecretKey;

public class Packet252SharedKey extends Packet
{
    private byte field_55165_a[];
    private byte field_58070_b[];
    private SecretKey field_55164_b;

    public Packet252SharedKey()
    {
        field_55165_a = new byte[0];
        field_58070_b = new byte[0];
    }

    public Packet252SharedKey(SecretKey par1SecretKey, PublicKey par2PublicKey, byte par3ArrayOfByte[])
    {
        field_55165_a = new byte[0];
        field_58070_b = new byte[0];
        field_55164_b = par1SecretKey;
        field_55165_a = CryptManager.func_55328_a(par2PublicKey, par1SecretKey.getEncoded());
        field_58070_b = CryptManager.func_55328_a(par2PublicKey, par3ArrayOfByte);
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        field_55165_a = func_55157_b(par1DataInputStream);
        field_58070_b = func_55157_b(par1DataInputStream);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        func_55156_a(par1DataOutputStream, field_55165_a);
        func_55156_a(par1DataOutputStream, field_58070_b);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.func_55317_a(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 2 + field_55165_a.length + 2 + field_58070_b.length;
    }

    public SecretKey func_55163_a(PrivateKey par1PrivateKey)
    {
        if (par1PrivateKey == null)
        {
            return field_55164_b;
        }
        else
        {
            return field_55164_b = CryptManager.func_55329_a(par1PrivateKey, field_55165_a);
        }
    }

    public SecretKey func_55162_b()
    {
        return func_55163_a(null);
    }

    public byte[] func_58069_b(PrivateKey par1PrivateKey)
    {
        if (par1PrivateKey == null)
        {
            return field_58070_b;
        }
        else
        {
            return CryptManager.func_55331_b(par1PrivateKey, field_58070_b);
        }
    }
}
