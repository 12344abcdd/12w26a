package net.minecraft.src;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;

public class IntegratedServerListenThread extends NetworkListenThread
{
    private final MemoryConnection field_56974_c = new MemoryConnection(null);
    private MemoryConnection field_56972_d;
    private String field_56973_e;
    private boolean field_56970_f;
    private ServerListenThread field_56971_g;

    public IntegratedServerListenThread(IntegratedServer par1IntegratedServer) throws IOException
    {
        super(par1IntegratedServer);
        field_56970_f = false;
    }

    public void func_56969_a(MemoryConnection par1MemoryConnection, String par2Str)
    {
        field_56972_d = par1MemoryConnection;
        field_56973_e = par2Str;
    }

    public String func_56966_d() throws IOException
    {
        if (field_56971_g == null)
        {
            int i = -1;

            try
            {
                i = ServerLauncher.func_57431_b();
            }
            catch (IOException ioexception) { }

            if (i <= 0)
            {
                i = 25564;
            }

            try
            {
                field_56971_g = new ServerListenThread(this, InetAddress.getLocalHost(), i);
                field_56971_g.start();
            }
            catch (IOException ioexception1)
            {
                throw ioexception1;
            }
        }

        return (new StringBuilder()).append(field_56971_g.func_56999_c().getHostName()).append(":").append(field_56971_g.func_56996_d()).toString();
    }

    public void func_56958_a()
    {
        super.func_56958_a();

        if (field_56971_g != null)
        {
            System.out.println("Stopping server connection");
            field_56971_g.func_57001_b();
            field_56971_g.interrupt();
            field_56971_g = null;
        }
    }

    public void func_56960_b()
    {
        if (field_56972_d != null)
        {
            EntityPlayerMP entityplayermp = func_56968_e().func_56339_Z().func_57123_a(field_56973_e);

            if (entityplayermp != null)
            {
                field_56974_c.func_57319_a(field_56972_d);
                field_56970_f = true;
                func_56968_e().func_56339_Z().func_57113_a(field_56974_c, entityplayermp);
            }

            field_56972_d = null;
            field_56973_e = null;
        }

        if (field_56971_g != null)
        {
            field_56971_g.func_56998_a();
        }

        super.func_56960_b();
    }

    public IntegratedServer func_56968_e()
    {
        return (IntegratedServer)super.func_56959_c();
    }

    public boolean func_56967_f()
    {
        return field_56970_f && field_56974_c.func_57321_i().func_57320_g() && field_56974_c.func_57321_i().func_57318_h();
    }

    public MinecraftServer func_56959_c()
    {
        return func_56968_e();
    }
}
