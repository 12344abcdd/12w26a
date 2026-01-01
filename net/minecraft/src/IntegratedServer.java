package net.minecraft.src;

import java.io.*;
import java.util.logging.Logger;
import net.minecraft.client.Minecraft;

public class IntegratedServer extends MinecraftServer
{
    private final Minecraft field_56420_k;
    private final WorldSettings field_56418_l;
    private IntegratedServerListenThread field_56419_m;
    private boolean field_56416_n;
    private boolean field_56417_o;
    private ThreadLanServerPing field_58038_p;

    public IntegratedServer(Minecraft par1Minecraft, String par2Str, String par3Str, WorldSettings par4WorldSettings)
    {
        super(new File(Minecraft.getMinecraftDir(), "saves"));
        field_56416_n = false;
        func_56316_k(par1Minecraft.session.username);
        func_58033_m(par2Str);
        func_56362_l(par3Str);
        func_56352_a(par1Minecraft.func_55063_q());
        func_56326_b(par4WorldSettings.func_55251_c());
        func_56297_d(256);
        func_56309_a(new IntegratedPlayerList(this));
        field_56420_k = par1Minecraft;
        field_56418_l = par4WorldSettings;

        try
        {
            field_56419_m = new IntegratedServerListenThread(this);
        }
        catch (IOException ioexception)
        {
            throw new Error();
        }
    }

    protected void func_58028_a(String par1Str, String par2Str, long par3, WorldType par5WorldType)
    {
        func_56294_a(par1Str);
        field_56392_b = new WorldServer[3];
        field_56399_j = new long[field_56392_b.length][100];
        ISaveHandler isavehandler = func_56328_M().getSaveLoader(par1Str, true);

        for (int i = 0; i < field_56392_b.length; i++)
        {
            byte byte0 = 0;

            if (i == 1)
            {
                byte0 = -1;
            }

            if (i == 2)
            {
                byte0 = 1;
            }

            if (i == 0)
            {
                if (func_56298_L())
                {
                    field_56392_b[i] = new DemoWorldServer(this, isavehandler, par2Str, byte0);
                }
                else
                {
                    field_56392_b[i] = new WorldServer(this, isavehandler, par2Str, byte0, field_56418_l);
                }
            }
            else
            {
                field_56392_b[i] = new WorldServerMulti(this, isavehandler, par2Str, byte0, field_56418_l, field_56392_b[0]);
            }

            field_56392_b[i].addWorldAccess(new WorldManager(this, field_56392_b[i]));
            func_56339_Z().func_57096_a(field_56392_b);
        }

        func_56364_c(func_56356_e());
        func_56312_b();
    }

    protected boolean func_56341_a() throws IOException
    {
        field_56394_a.info("Starting integrated minecraft server version 12w26a");
        func_56306_c(false);
        func_56369_d(true);
        func_56323_e(true);
        func_56335_f(true);
        func_56289_g(true);
        field_56394_a.info("Generating keypair");
        func_56330_a(CryptManager.func_57567_b());
        func_58028_a(func_58029_J(), func_56359_J(), field_56418_l.getSeed(), field_56418_l.getTerrainType());
        func_56314_n((new StringBuilder()).append(func_56296_H()).append(" - ").append(field_56392_b[0].getWorldInfo().getWorldName()).toString());
        return true;
    }

    protected void func_56322_n()
    {
        boolean flag = field_56416_n;
        field_56416_n = field_56419_m.func_56967_f();

        if (!flag && field_56416_n)
        {
            field_56394_a.info("Saving and pausing game...");
            func_56339_Z().func_57117_g();
            func_58034_a(false);
        }

        if (!field_56416_n)
        {
            super.func_56322_n();
        }
    }

    public boolean func_56333_c()
    {
        return false;
    }

    public EnumGameType func_56366_d()
    {
        return field_56418_l.func_57458_e();
    }

    public int func_56356_e()
    {
        return field_56420_k.gameSettings.difficulty;
    }

    public boolean func_56363_f()
    {
        return field_56418_l.getHardcoreEnabled();
    }

    protected File func_56358_l()
    {
        return field_56420_k.mcDataDir;
    }

    public boolean func_56371_Q()
    {
        return false;
    }

    public IntegratedServerListenThread func_56414_ae()
    {
        return field_56419_m;
    }

    protected void func_56370_a(CrashReport par1CrashReport)
    {
        field_56420_k.func_56448_b(par1CrashReport);
    }

    public String func_58032_a(EnumGameType par1EnumGameType, boolean par2)
    {
        try
        {
            String s = field_56419_m.func_56966_d();
            System.out.println((new StringBuilder()).append("Started on ").append(s).toString());
            field_56417_o = true;
            field_58038_p = new ThreadLanServerPing(func_56318_W(), s);
            field_58038_p.start();
            func_56339_Z().func_58122_a(par1EnumGameType);
            func_56339_Z().func_58123_b(par2);
            return s;
        }
        catch (IOException ioexception)
        {
            return null;
        }
    }

    protected void func_56368_ae()
    {
        super.func_56368_ae();

        if (field_58038_p != null)
        {
            field_58038_p.interrupt();
            field_58038_p = null;
        }
    }

    public boolean func_56415_af()
    {
        return field_56417_o;
    }

    public void func_58031_a(EnumGameType par1EnumGameType)
    {
        func_56339_Z().func_58122_a(par1EnumGameType);
    }

    public NetworkListenThread func_56290_aa()
    {
        return func_56414_ae();
    }
}
