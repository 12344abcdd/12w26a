package net.minecraft.src;

class ThreadServerApplication extends Thread
{
    final MinecraftServer field_56928_a;

    ThreadServerApplication(MinecraftServer par1MinecraftServer, String par2Str)
    {
        super(par2Str);
        field_56928_a = par1MinecraftServer;
    }

    public void run()
    {
        field_56928_a.run();
    }
}
