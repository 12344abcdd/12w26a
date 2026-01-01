package net.minecraft.src;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

class TcpWriterThread extends Thread
{
    final TcpConnection field_57562_a;

    TcpWriterThread(TcpConnection par1TcpConnection, String par2Str)
    {
        super(par2Str);
        field_57562_a = par1TcpConnection;
    }

    public void run()
    {
        TcpConnection.field_57296_b.getAndIncrement();

        try
        {
            while (TcpConnection.func_57280_a(field_57562_a))
            {
                boolean flag;

                for (flag = false; TcpConnection.func_57287_d(field_57562_a); flag = true) { }

                try
                {
                    if (flag && TcpConnection.func_57283_e(field_57562_a) != null)
                    {
                        TcpConnection.func_57283_e(field_57562_a).flush();
                    }
                }
                catch (IOException ioexception)
                {
                    if (!TcpConnection.func_57285_f(field_57562_a))
                    {
                        TcpConnection.func_57277_a(field_57562_a, ioexception);
                    }

                    ioexception.printStackTrace();
                }

                try
                {
                    sleep(2L);
                }
                catch (InterruptedException interruptedexception) { }
            }
        }
        finally
        {
            TcpConnection.field_57296_b.getAndDecrement();
        }
    }
}
