package net.minecraft.src;

class TcpMonitorThread extends Thread
{
    final TcpConnection field_57564_a;

    TcpMonitorThread(TcpConnection par1TcpConnection)
    {
        field_57564_a = par1TcpConnection;
    }

    public void run()
    {
        try
        {
            Thread.sleep(2000L);

            if (TcpConnection.func_57280_a(field_57564_a))
            {
                TcpConnection.func_57289_h(field_57564_a).interrupt();
                field_57564_a.networkShutdown("disconnect.closed", new Object[0]);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
