package net.minecraft.src;

class DedicatedServerSleepThread extends Thread
{
    final DedicatedServer field_57631_a;

    DedicatedServerSleepThread(DedicatedServer par1DedicatedServer)
    {
        field_57631_a = par1DedicatedServer;
        setDaemon(true);
        start();
    }

    public void run()
    {
        do
        {
            try
            {
                Thread.sleep(0x7fffffffL);
            }
            catch (InterruptedException interruptedexception) { }
        }
        while (true);
    }
}
