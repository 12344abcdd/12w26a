package net.minecraft.src;

import java.util.concurrent.atomic.AtomicInteger;

class TcpReaderThread extends Thread
{
    final TcpConnection field_57565_a;

    TcpReaderThread(TcpConnection par1TcpConnection, String par2Str)
    {
        super(par2Str);
        field_57565_a = par1TcpConnection;
    }

    public void run()
    {
        TcpConnection.field_57298_a.getAndIncrement();

        try
        {
            while (TcpConnection.func_57280_a(field_57565_a) && !TcpConnection.func_57281_b(field_57565_a))
            {
                while (TcpConnection.func_57286_c(field_57565_a)) ;

                try
                {
                    sleep(2L);
                }
                catch (InterruptedException interruptedexception) { }
            }
        }
        finally
        {
            TcpConnection.field_57298_a.getAndDecrement();
        }
    }
}
