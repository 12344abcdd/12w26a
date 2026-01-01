package net.minecraft.src;

import java.io.IOException;
import java.net.InetAddress;

public class DedicatedServerListenThread extends NetworkListenThread
{
    private final ServerListenThread field_56977_c;

    public DedicatedServerListenThread(MinecraftServer par1MinecraftServer, InetAddress par2InetAddress, int par3) throws IOException
    {
        super(par1MinecraftServer);
        field_56977_c = new ServerListenThread(this, par2InetAddress, par3);
        field_56977_c.start();
    }

    public void func_56958_a()
    {
        super.func_56958_a();
        field_56977_c.func_57001_b();
        field_56977_c.interrupt();
    }

    public void func_56960_b()
    {
        field_56977_c.func_56998_a();
        super.func_56960_b();
    }

    public DedicatedServer func_56975_d()
    {
        return (DedicatedServer)super.func_56959_c();
    }

    public void func_58079_a(InetAddress par1InetAddress)
    {
        field_56977_c.func_58116_a(par1InetAddress);
    }

    public MinecraftServer func_56959_c()
    {
        return func_56975_d();
    }
}
