package net.minecraft.src;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class NetworkListenThread
{
    public static Logger field_56965_a = Logger.getLogger("Minecraft");
    private final MinecraftServer field_56964_c;
    private final List field_56962_d = Collections.synchronizedList(new ArrayList());
    public volatile boolean field_56963_b;

    public NetworkListenThread(MinecraftServer par1MinecraftServer) throws IOException
    {
        field_56963_b = false;
        field_56964_c = par1MinecraftServer;
        field_56963_b = true;
    }

    public void func_56961_a(NetServerHandler par1NetServerHandler)
    {
        field_56962_d.add(par1NetServerHandler);
    }

    public void func_56958_a()
    {
        field_56963_b = false;
    }

    public void func_56960_b()
    {
        for (int i = 0; i < field_56962_d.size(); i++)
        {
            NetServerHandler netserverhandler = (NetServerHandler)field_56962_d.get(i);

            try
            {
                netserverhandler.func_56718_b();
            }
            catch (Exception exception)
            {
                field_56965_a.log(Level.WARNING, (new StringBuilder()).append("Failed to handle packet: ").append(exception).toString(), exception);
                netserverhandler.func_56716_a("Internal server error");
            }

            if (netserverhandler.field_56727_c)
            {
                field_56962_d.remove(i--);
            }

            netserverhandler.field_56726_b.func_55345_a();
        }
    }

    public MinecraftServer func_56959_c()
    {
        return field_56964_c;
    }
}
