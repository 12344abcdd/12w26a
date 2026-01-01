package org.bouncycastle.crypto.io;

import java.io.*;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.StreamCipher;

public class CipherOutputStream extends FilterOutputStream
{
    private BufferedBlockCipher field_57168_a;
    private StreamCipher field_57166_b;
    private byte field_57167_c[];
    private byte field_57165_d[];

    public CipherOutputStream(OutputStream par1OutputStream, BufferedBlockCipher par2BufferedBlockCipher)
    {
        super(par1OutputStream);
        field_57167_c = new byte[1];
        field_57168_a = par2BufferedBlockCipher;
        field_57165_d = new byte[par2BufferedBlockCipher.func_57387_a()];
    }

    public void write(int par1ArrayOfByte) throws IOException
    {
        field_57167_c[0] = (byte)par1ArrayOfByte;

        if (field_57168_a != null)
        {
            int i = field_57168_a.func_57386_a(field_57167_c, 0, 1, field_57165_d, 0);

            if (i != 0)
            {
                out.write(field_57165_d, 0, i);
            }
        }
        else
        {
            out.write(field_57166_b.func_57633_a((byte)par1ArrayOfByte));
        }
    }

    public void write(byte par1ArrayOfByte[]) throws IOException
    {
        write(par1ArrayOfByte, 0, par1ArrayOfByte.length);
    }

    public void write(byte par1ArrayOfByte[], int par2, int par3) throws IOException
    {
        if (field_57168_a != null)
        {
            byte abyte0[] = new byte[field_57168_a.func_57384_b(par3)];
            int i = field_57168_a.func_57386_a(par1ArrayOfByte, par2, par3, abyte0, 0);

            if (i != 0)
            {
                out.write(abyte0, 0, i);
            }
        }
        else
        {
            byte abyte1[] = new byte[par3];
            field_57166_b.func_57632_a(par1ArrayOfByte, par2, par3, abyte1, 0);
            out.write(abyte1, 0, par3);
        }
    }

    public void flush() throws IOException
    {
        super.flush();
    }

    public void close() throws IOException
    {
        try
        {
            if (field_57168_a != null)
            {
                byte abyte0[] = new byte[field_57168_a.func_57384_b(0)];
                int i = field_57168_a.func_57385_a(abyte0, 0);

                if (i != 0)
                {
                    out.write(abyte0, 0, i);
                }
            }
        }
        catch (Exception exception)
        {
            throw new IOException((new StringBuilder()).append("Error closing stream: ").append(exception.toString()).toString());
        }

        flush();
        super.close();
    }
}
