package net.minecraft.src;

class TcpMasterThread extends Thread
{
    final TcpConnection field_57563_a;

    TcpMasterThread(TcpConnection par1TcpConnection)
    {
        field_57563_a = par1TcpConnection;
    }

    public void run()
    {
        try
        {
            Thread.sleep(5000L);

            if (TcpConnection.func_57282_g(field_57563_a).isAlive())
            {
                try
                {
                    TcpConnection.func_57282_g(field_57563_a).stop();
                }
                catch (Throwable throwable) { }
            }

            if (TcpConnection.func_57289_h(field_57563_a).isAlive())
            {
                try
                {
                    TcpConnection.func_57289_h(field_57563_a).stop();
                }
                catch (Throwable throwable1) { }
            }
        }
        catch (InterruptedException interruptedexception)
        {
            interruptedexception.printStackTrace();
        }
    }
}
