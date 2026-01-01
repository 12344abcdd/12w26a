package net.minecraft.src;

import java.io.*;

public class RConOutputStream
{
    private ByteArrayOutputStream field_56887_a;
    private DataOutputStream field_56886_b;

    public RConOutputStream(int par1)
    {
        field_56887_a = new ByteArrayOutputStream(par1);
        field_56886_b = new DataOutputStream(field_56887_a);
    }

    public void func_56883_a(byte par1ArrayOfByte[]) throws IOException
    {
        field_56886_b.write(par1ArrayOfByte, 0, par1ArrayOfByte.length);
    }

    public void func_56884_a(String par1Str) throws IOException
    {
        field_56886_b.writeBytes(par1Str);
        field_56886_b.write(0);
    }

    public void func_56880_a(int par1) throws IOException
    {
        field_56886_b.write(par1);
    }

    public void func_56881_a(short par1) throws IOException
    {
        field_56886_b.writeShort(Short.reverseBytes(par1));
    }

    public byte[] func_56885_a()
    {
        return field_56887_a.toByteArray();
    }

    public void func_56882_b()
    {
        field_56887_a.reset();
    }
}
