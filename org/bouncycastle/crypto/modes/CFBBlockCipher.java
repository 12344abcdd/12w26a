package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.*;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class CFBBlockCipher implements BlockCipher
{
    private byte field_57258_a[];
    private byte field_57256_b[];
    private byte field_57257_c[];
    private int field_57254_d;
    private BlockCipher field_57255_e;
    private boolean field_57253_f;

    public CFBBlockCipher(BlockCipher par1BlockCipher, int par2)
    {
        field_57255_e = null;
        field_57255_e = par1BlockCipher;
        field_57254_d = par2 / 8;
        field_57258_a = new byte[par1BlockCipher.func_57222_b()];
        field_57256_b = new byte[par1BlockCipher.func_57222_b()];
        field_57257_c = new byte[par1BlockCipher.func_57222_b()];
    }

    public void func_57221_a(boolean par1, CipherParameters par2CipherParameters) throws IllegalArgumentException
    {
        field_57253_f = par1;

        if (par2CipherParameters instanceof ParametersWithIV)
        {
            ParametersWithIV parameterswithiv = (ParametersWithIV)par2CipherParameters;
            byte abyte0[] = parameterswithiv.func_57454_a();

            if (abyte0.length < field_57258_a.length)
            {
                System.arraycopy(abyte0, 0, field_57258_a, field_57258_a.length - abyte0.length, abyte0.length);

                for (int i = 0; i < field_57258_a.length - abyte0.length; i++)
                {
                    field_57258_a[i] = 0;
                }
            }
            else
            {
                System.arraycopy(abyte0, 0, field_57258_a, 0, field_57258_a.length);
            }

            func_57220_c();

            if (parameterswithiv.func_57453_b() != null)
            {
                field_57255_e.func_57221_a(true, parameterswithiv.func_57453_b());
            }
        }
        else
        {
            func_57220_c();
            field_57255_e.func_57221_a(true, par2CipherParameters);
        }
    }

    public String func_57219_a()
    {
        return (new StringBuilder()).append(field_57255_e.func_57219_a()).append("/CFB").append(field_57254_d * 8).toString();
    }

    public int func_57222_b()
    {
        return field_57254_d;
    }

    public int func_57223_a(byte par1ArrayOfByte[], int par2, byte par3ArrayOfByte[], int par4) throws DataLengthException, IllegalStateException
    {
        return field_57253_f ? func_57251_b(par1ArrayOfByte, par2, par3ArrayOfByte, par4) : func_57252_c(par1ArrayOfByte, par2, par3ArrayOfByte, par4);
    }

    public int func_57251_b(byte par1ArrayOfByte[], int par2, byte par3ArrayOfByte[], int par4) throws DataLengthException, IllegalStateException
    {
        if (par2 + field_57254_d > par1ArrayOfByte.length)
        {
            throw new DataLengthException("input buffer too short");
        }

        if (par4 + field_57254_d > par3ArrayOfByte.length)
        {
            throw new DataLengthException("output buffer too short");
        }

        field_57255_e.func_57223_a(field_57256_b, 0, field_57257_c, 0);

        for (int i = 0; i < field_57254_d; i++)
        {
            par3ArrayOfByte[par4 + i] = (byte)(field_57257_c[i] ^ par1ArrayOfByte[par2 + i]);
        }

        System.arraycopy(field_57256_b, field_57254_d, field_57256_b, 0, field_57256_b.length - field_57254_d);
        System.arraycopy(par3ArrayOfByte, par4, field_57256_b, field_57256_b.length - field_57254_d, field_57254_d);
        return field_57254_d;
    }

    public int func_57252_c(byte par1ArrayOfByte[], int par2, byte par3ArrayOfByte[], int par4) throws DataLengthException, IllegalStateException
    {
        if (par2 + field_57254_d > par1ArrayOfByte.length)
        {
            throw new DataLengthException("input buffer too short");
        }

        if (par4 + field_57254_d > par3ArrayOfByte.length)
        {
            throw new DataLengthException("output buffer too short");
        }

        field_57255_e.func_57223_a(field_57256_b, 0, field_57257_c, 0);
        System.arraycopy(field_57256_b, field_57254_d, field_57256_b, 0, field_57256_b.length - field_57254_d);
        System.arraycopy(par1ArrayOfByte, par2, field_57256_b, field_57256_b.length - field_57254_d, field_57254_d);

        for (int i = 0; i < field_57254_d; i++)
        {
            par3ArrayOfByte[par4 + i] = (byte)(field_57257_c[i] ^ par1ArrayOfByte[par2 + i]);
        }

        return field_57254_d;
    }

    public void func_57220_c()
    {
        System.arraycopy(field_57258_a, 0, field_57256_b, 0, field_57258_a.length);
        field_57255_e.func_57220_c();
    }
}
