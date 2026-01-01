package org.bouncycastle.crypto;

public class BufferedBlockCipher
{
    protected byte field_57396_a[];
    protected int field_57394_b;
    protected boolean field_57395_c;
    protected BlockCipher field_57392_d;
    protected boolean field_57393_e;
    protected boolean field_57391_f;

    protected BufferedBlockCipher()
    {
    }

    public BufferedBlockCipher(BlockCipher par1BlockCipher)
    {
        field_57392_d = par1BlockCipher;
        field_57396_a = new byte[par1BlockCipher.func_57222_b()];
        field_57394_b = 0;
        String s = par1BlockCipher.func_57219_a();
        int i = s.indexOf('/') + 1;
        field_57391_f = i > 0 && s.startsWith("PGP", i);

        if (field_57391_f)
        {
            field_57393_e = true;
        }
        else
        {
            field_57393_e = i > 0 && (s.startsWith("CFB", i) || s.startsWith("OFB", i) || s.startsWith("OpenPGP", i) || s.startsWith("SIC", i) || s.startsWith("GCTR", i));
        }
    }

    public void func_57390_a(boolean par1, CipherParameters par2CipherParameters) throws IllegalArgumentException
    {
        field_57395_c = par1;
        func_57389_b();
        field_57392_d.func_57221_a(par1, par2CipherParameters);
    }

    public int func_57387_a()
    {
        return field_57392_d.func_57222_b();
    }

    public int func_57388_a(int par1)
    {
        int i = par1 + field_57394_b;
        int j;

        if (field_57391_f)
        {
            j = i % field_57396_a.length - (field_57392_d.func_57222_b() + 2);
        }
        else
        {
            j = i % field_57396_a.length;
        }

        return i - j;
    }

    public int func_57384_b(int par1)
    {
        return par1 + field_57394_b;
    }

    public int func_57386_a(byte par1ArrayOfByte[], int par2, int par3, byte par4ArrayOfByte[], int par5) throws DataLengthException, IllegalStateException
    {
        if (par3 < 0)
        {
            throw new IllegalArgumentException("Can't have a negative input length!");
        }

        int i = func_57387_a();
        int j = func_57388_a(par3);

        if (j > 0 && par5 + j > par4ArrayOfByte.length)
        {
            throw new DataLengthException("output buffer too short");
        }

        int k = 0;
        int l = field_57396_a.length - field_57394_b;

        if (par3 > l)
        {
            System.arraycopy(par1ArrayOfByte, par2, field_57396_a, field_57394_b, l);
            k += field_57392_d.func_57223_a(field_57396_a, 0, par4ArrayOfByte, par5);
            field_57394_b = 0;
            par3 -= l;

            for (par2 += l; par3 > field_57396_a.length; par2 += i)
            {
                k += field_57392_d.func_57223_a(par1ArrayOfByte, par2, par4ArrayOfByte, par5 + k);
                par3 -= i;
            }
        }

        System.arraycopy(par1ArrayOfByte, par2, field_57396_a, field_57394_b, par3);
        field_57394_b += par3;

        if (field_57394_b == field_57396_a.length)
        {
            k += field_57392_d.func_57223_a(field_57396_a, 0, par4ArrayOfByte, par5 + k);
            field_57394_b = 0;
        }

        return k;
    }

    public int func_57385_a(byte par1ArrayOfByte[], int par2) throws DataLengthException, IllegalStateException, InvalidCipherTextException
    {
        try
        {
            int i = 0;

            if (par2 + field_57394_b > par1ArrayOfByte.length)
            {
                throw new DataLengthException("output buffer too short for doFinal()");
            }

            if (field_57394_b != 0)
            {
                if (!field_57393_e)
                {
                    throw new DataLengthException("data not block size aligned");
                }

                field_57392_d.func_57223_a(field_57396_a, 0, field_57396_a, 0);
                i = field_57394_b;
                field_57394_b = 0;
                System.arraycopy(field_57396_a, 0, par1ArrayOfByte, par2, i);
            }

            int j = i;
            return j;
        }
        finally
        {
            func_57389_b();
        }
    }

    public void func_57389_b()
    {
        for (int i = 0; i < field_57396_a.length; i++)
        {
            field_57396_a[i] = 0;
        }

        field_57394_b = 0;
        field_57392_d.func_57220_c();
    }
}
