package net.minecraft.src;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class RConThreadClient extends RConThreadBase
{
    private boolean field_56706_g;
    private Socket field_56708_h;
    private byte field_56709_i[];
    private String field_56707_j;

    RConThreadClient(IServer par1IServer, Socket par2Socket)
    {
        super(par1IServer);
        field_56706_g = false;
        field_56709_i = new byte[1460];
        field_56708_h = par2Socket;

        try
        {
            field_56708_h.setSoTimeout(0);
        }
        catch (Exception exception)
        {
            field_56668_a = false;
        }

        field_56707_j = par1IServer.func_56425_a("rcon.password", "");
        func_56657_b((new StringBuilder()).append("Rcon connection from: ").append(par2Socket.getInetAddress()).toString());
    }

    public void run()
    {
        try
        {
            while (true)
            {
                if (!field_56668_a)
                {
                    break;
                }

                BufferedInputStream bufferedinputstream = new BufferedInputStream(field_56708_h.getInputStream());
                int i = bufferedinputstream.read(field_56709_i, 0, 1460);

                if (10 > i)
                {
                    return;
                }

                int j = 0;
                int k = RConUtils.func_57425_b(field_56709_i, 0, i);

                if (k != i - 4)
                {
                    return;
                }

                j += 4;
                int l = RConUtils.func_57425_b(field_56709_i, j, i);
                j += 4;
                int i1 = RConUtils.func_57421_a(field_56709_i, j);
                j += 4;

                switch (i1)
                {
                    case 3:
                        String s = RConUtils.func_57422_a(field_56709_i, j, i);
                        j += s.length();

                        if (0 != s.length() && s.equals(field_56707_j))
                        {
                            field_56706_g = true;
                            func_56702_a(l, 2, "");
                        }
                        else
                        {
                            field_56706_g = false;
                            func_56703_e();
                        }

                        break;

                    case 2:
                        if (field_56706_g)
                        {
                            String s1 = RConUtils.func_57422_a(field_56709_i, j, i);

                            try
                            {
                                func_56704_a(l, field_56666_b.func_56321_h(s1));
                            }
                            catch (Exception exception1)
                            {
                                func_56704_a(l, (new StringBuilder()).append("Error executing: ").append(s1).append(" (").append(exception1.getMessage()).append(")").toString());
                            }
                        }
                        else
                        {
                            func_56703_e();
                        }

                        break;

                    default:
                        func_56704_a(l, String.format("Unknown request %s", new Object[]
                                {
                                    Integer.toHexString(i1)
                            }));
                        break;
                }
            }
        }
        catch (SocketTimeoutException sockettimeoutexception) { }
        catch (IOException ioexception) { }
        catch (Exception exception)
        {
            System.out.println(exception);
        }
        finally
        {
            func_56705_f();
        }
    }

    private void func_56702_a(int par1, int par2, String par3Str) throws IOException
    {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(1248);
        DataOutputStream dataoutputstream = new DataOutputStream(bytearrayoutputstream);
        dataoutputstream.writeInt(Integer.reverseBytes(par3Str.length() + 10));
        dataoutputstream.writeInt(Integer.reverseBytes(par1));
        dataoutputstream.writeInt(Integer.reverseBytes(par2));
        dataoutputstream.writeBytes(par3Str);
        dataoutputstream.write(0);
        dataoutputstream.write(0);
        field_56708_h.getOutputStream().write(bytearrayoutputstream.toByteArray());
    }

    private void func_56703_e() throws IOException
    {
        func_56702_a(-1, 2, "");
    }

    private void func_56704_a(int par1, String par2Str) throws IOException
    {
        int i = par2Str.length();

        do
        {
            int j = 4096 > i ? i : 4096;
            func_56702_a(par1, 0, par2Str.substring(0, j));
            par2Str = par2Str.substring(j);
            i = par2Str.length();
        }
        while (0 != i);
    }

    private void func_56705_f()
    {
        if (null == field_56708_h)
        {
            return;
        }

        try
        {
            field_56708_h.close();
        }
        catch (IOException ioexception)
        {
            func_56654_c((new StringBuilder()).append("IO: ").append(ioexception.getMessage()).toString());
        }

        field_56708_h = null;
    }
}
