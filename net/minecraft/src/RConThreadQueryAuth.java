package net.minecraft.src;

import java.net.DatagramPacket;
import java.util.Date;
import java.util.Random;

class RConThreadQueryAuth
{
    private long field_56638_b;
    private int field_56639_c;
    private byte field_56636_d[];
    private byte field_56637_e[];
    private String field_56635_f;
    final RConThreadQuery field_56640_a;

    public RConThreadQueryAuth(RConThreadQuery par1RConThreadQuery, DatagramPacket par2DatagramPacket)
    {
        field_56640_a = par1RConThreadQuery;
        field_56638_b = (new Date()).getTime();
        byte abyte0[] = par2DatagramPacket.getData();
        field_56636_d = new byte[4];
        field_56636_d[0] = abyte0[3];
        field_56636_d[1] = abyte0[4];
        field_56636_d[2] = abyte0[5];
        field_56636_d[3] = abyte0[6];
        field_56635_f = new String(field_56636_d);
        field_56639_c = (new Random()).nextInt(0x1000000);
        field_56637_e = String.format("\t%s%d\0", new Object[]
                {
                    field_56635_f, Integer.valueOf(field_56639_c)
                }).getBytes();
    }

    public Boolean func_56633_a(long par1)
    {
        return Boolean.valueOf(field_56638_b < par1);
    }

    public int func_56632_a()
    {
        return field_56639_c;
    }

    public byte[] func_56634_b()
    {
        return field_56637_e;
    }

    public byte[] func_56631_c()
    {
        return field_56636_d;
    }
}
