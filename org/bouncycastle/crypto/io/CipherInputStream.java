package org.bouncycastle.crypto.io;

import java.io.*;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.StreamCipher;

public class CipherInputStream extends FilterInputStream
{
    private BufferedBlockCipher field_56957_a;
    private StreamCipher field_56955_b;
    private byte field_56956_c[];
    private byte field_56953_d[];
    private int field_56954_e;
    private int field_56951_f;
    private boolean field_56952_g;

    public CipherInputStream(InputStream par1InputStream, BufferedBlockCipher par2BufferedBlockCipher)
    {
        super(par1InputStream);
        field_56957_a = par2BufferedBlockCipher;
        field_56956_c = new byte[par2BufferedBlockCipher.func_57384_b(2048)];
        field_56953_d = new byte[2048];
    }

    private int func_56950_a() throws IOException
    {
        int i = super.available();

        if (i <= 0)
        {
            i = 1;
        }

        if (i > field_56953_d.length)
        {
            i = super.read(field_56953_d, 0, field_56953_d.length);
        }
        else
        {
            i = super.read(field_56953_d, 0, i);
        }

        if (i < 0)
        {
            if (field_56952_g)
            {
                return -1;
            }

            try
            {
                if (field_56957_a != null)
                {
                    field_56951_f = field_56957_a.func_57385_a(field_56956_c, 0);
                }
                else
                {
                    field_56951_f = 0;
                }
            }
            catch (Exception exception)
            {
                throw new IOException((new StringBuilder()).append("error processing stream: ").append(exception.toString()).toString());
            }

            field_56954_e = 0;
            field_56952_g = true;

            if (field_56954_e == field_56951_f)
            {
                return -1;
            }
        }
        else
        {
            field_56954_e = 0;

            try
            {
                if (field_56957_a != null)
                {
                    field_56951_f = field_56957_a.func_57386_a(field_56953_d, 0, i, field_56956_c, 0);
                }
                else
                {
                    field_56955_b.func_57632_a(field_56953_d, 0, i, field_56956_c, 0);
                    field_56951_f = i;
                }
            }
            catch (Exception exception1)
            {
                throw new IOException((new StringBuilder()).append("error processing stream: ").append(exception1.toString()).toString());
            }

            if (field_56951_f == 0)
            {
                return func_56950_a();
            }
        }

        return field_56951_f;
    }

    public int read() throws IOException
    {
        if (field_56954_e == field_56951_f && func_56950_a() < 0)
        {
            return -1;
        }
        else
        {
            return field_56956_c[field_56954_e++] & 0xff;
        }
    }

    public int read(byte par1ArrayOfByte[]) throws IOException
    {
        return read(par1ArrayOfByte, 0, par1ArrayOfByte.length);
    }

    public int read(byte par1ArrayOfByte[], int par2, int par3) throws IOException
    {
        if (field_56954_e == field_56951_f && func_56950_a() < 0)
        {
            return -1;
        }

        int i = field_56951_f - field_56954_e;

        if (par3 > i)
        {
            System.arraycopy(field_56956_c, field_56954_e, par1ArrayOfByte, par2, i);
            field_56954_e = field_56951_f;
            return i;
        }
        else
        {
            System.arraycopy(field_56956_c, field_56954_e, par1ArrayOfByte, par2, par3);
            field_56954_e += par3;
            return par3;
        }
    }

    public long skip(long par1) throws IOException
    {
        if (par1 <= 0L)
        {
            return 0L;
        }

        int i = field_56951_f - field_56954_e;

        if (par1 > (long)i)
        {
            field_56954_e = field_56951_f;
            return (long)i;
        }
        else
        {
            field_56954_e += (int)par1;
            return (long)(int)par1;
        }
    }

    public int available() throws IOException
    {
        return field_56951_f - field_56954_e;
    }

    public void close() throws IOException
    {
        super.close();
    }

    public boolean markSupported()
    {
        return false;
    }
}
