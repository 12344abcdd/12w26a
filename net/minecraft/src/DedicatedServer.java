package net.minecraft.src;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DedicatedServer extends MinecraftServer implements IServer
{
    private final List field_56437_k = Collections.synchronizedList(new ArrayList());
    private RConThreadQuery field_56435_l;
    private RConThreadMain field_56436_m;
    private PropertyManager field_56433_n;
    private boolean field_56434_o;
    private EnumGameType field_56432_p;
    private NetworkListenThread field_56431_q;
    private boolean field_56430_r;

    public DedicatedServer(File par1File)
    {
        super(par1File);
        field_56430_r = false;
        new DedicatedServerSleepThread(this);
    }

    protected boolean func_56341_a() throws IOException
    {
        DedicatedServerCommandThread dedicatedservercommandthread = new DedicatedServerCommandThread(this);
        dedicatedservercommandthread.setDaemon(true);
        dedicatedservercommandthread.start();
        ConsoleLogManager.func_57436_a();
        field_56394_a.info("Starting minecraft server version 12w26a");

        if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L)
        {
            field_56394_a.warning("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
        }

        field_56394_a.info("Loading properties");
        field_56433_n = new PropertyManager(new File("server.properties"));

        if (func_56307_I())
        {
            func_56301_d("127.0.0.1");
        }
        else
        {
            func_56306_c(field_56433_n.func_57080_a("online-mode", true));
            func_56301_d(field_56433_n.func_57081_a("server-ip", ""));
        }

        func_56369_d(field_56433_n.func_57080_a("spawn-animals", true));
        func_56323_e(field_56433_n.func_57080_a("spawn-npcs", true));
        func_56335_f(field_56433_n.func_57080_a("pvp", true));
        func_56289_g(field_56433_n.func_57080_a("allow-flight", false));
        func_56343_m(field_56433_n.func_57081_a("texture-pack", ""));
        func_56314_n(field_56433_n.func_57081_a("motd", "A Minecraft Server"));
        field_56434_o = field_56433_n.func_57080_a("generate-structures", true);
        int i = field_56433_n.func_57079_a("gamemode", EnumGameType.SURVIVAL.func_57538_a());
        field_56432_p = WorldSettings.func_57457_a(i);
        field_56394_a.info((new StringBuilder()).append("Default game type: ").append(field_56432_p).toString());
        InetAddress inetaddress = null;

        if (func_56295_i().length() > 0)
        {
            inetaddress = InetAddress.getByName(func_56295_i());
        }

        if (func_56331_G() < 0)
        {
            func_56336_b(field_56433_n.func_57079_a("server-port", 25565));
        }

        field_56394_a.info("Generating keypair");
        func_56330_a(CryptManager.func_57567_b());
        field_56394_a.info((new StringBuilder()).append("Starting Minecraft server on ").append(func_56295_i().length() != 0 ? func_56295_i() : "*").append(":").append(func_56331_G()).toString());

        try
        {
            field_56431_q = new DedicatedServerListenThread(this, inetaddress, func_56331_G());
        }
        catch (IOException ioexception)
        {
            field_56394_a.warning("**** FAILED TO BIND TO PORT!");
            field_56394_a.log(Level.WARNING, (new StringBuilder()).append("The exception was: ").append(ioexception.toString()).toString());
            field_56394_a.warning("Perhaps a server is already running on that port?");
            return false;
        }

        if (!func_56338_R())
        {
            field_56394_a.warning("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
            field_56394_a.warning("The server will make no attempt to authenticate usernames. Beware.");
            field_56394_a.warning("While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose.");
            field_56394_a.warning("To change this, set \"online-mode\" to \"true\" in the server.properties file.");
        }

        func_56309_a(new DedicatedPlayerList(this));
        long l = System.nanoTime();

        if (func_58029_J() == null)
        {
            func_58033_m(field_56433_n.func_57081_a("level-name", "world"));
        }

        String s = field_56433_n.func_57081_a("level-seed", "");
        String s1 = field_56433_n.func_57081_a("level-type", "DEFAULT");
        long l1 = (new Random()).nextLong();

        if (s.length() > 0)
        {
            try
            {
                long l2 = Long.parseLong(s);

                if (l2 != 0L)
                {
                    l1 = l2;
                }
            }
            catch (NumberFormatException numberformatexception)
            {
                l1 = s.hashCode();
            }
        }

        WorldType worldtype = WorldType.parseWorldType(s1);

        if (worldtype == null)
        {
            worldtype = WorldType.DEFAULT;
        }

        func_56297_d(field_56433_n.func_57079_a("max-build-height", 256));
        func_56297_d(((func_56348_X() + 8) / 16) * 16);
        func_56297_d(MathHelper.clamp_int(func_56348_X(), 64, 256));
        field_56433_n.func_57077_a("max-build-height", Integer.valueOf(func_56348_X()));
        field_56394_a.info((new StringBuilder()).append("Preparing level \"").append(func_58029_J()).append("\"").toString());
        func_58028_a(func_58029_J(), func_58029_J(), l1, worldtype);
        long l3 = System.nanoTime() - l;
        String s2 = String.format("%.3fs", new Object[]
                {
                    Double.valueOf((double)l3 / 1000000000D)
                });
        field_56394_a.info((new StringBuilder()).append("Done (").append(s2).append(")! For help, type \"help\" or \"?\"").toString());

        if (field_56433_n.func_57080_a("enable-query", false))
        {
            field_56394_a.info("Starting GS4 status listener");
            field_56435_l = new RConThreadQuery(this);
            field_56435_l.func_56651_a();
        }

        if (field_56433_n.func_57080_a("enable-rcon", false))
        {
            field_56394_a.info("Starting remote control listener");
            field_56436_m = new RConThreadMain(this);
            field_56436_m.func_56651_a();
        }

        return true;
    }

    public boolean func_56333_c()
    {
        return field_56434_o;
    }

    public EnumGameType func_56366_d()
    {
        return field_56432_p;
    }

    public int func_56356_e()
    {
        return field_56433_n.func_57079_a("difficulty", 1);
    }

    public boolean func_56363_f()
    {
        return field_56433_n.func_57080_a("hardcore", false);
    }

    protected void func_56370_a(CrashReport par1CrashReport)
    {
        while (func_56349_j())
        {
            func_56429_ae();

            try
            {
                Thread.sleep(10L);
            }
            catch (InterruptedException interruptedexception)
            {
                interruptedexception.printStackTrace();
            }
        }
    }

    protected void func_56346_m()
    {
        System.exit(0);
    }

    protected void func_56292_o()
    {
        super.func_56292_o();
        func_56429_ae();
    }

    public boolean func_56354_p()
    {
        return field_56433_n.func_57080_a("allow-nether", true);
    }

    public boolean func_56303_K()
    {
        return field_56433_n.func_57080_a("spawn-monsters", true);
    }

    public void func_56176_a(PlayerUsageSnooper par1PlayerUsageSnooper)
    {
        par1PlayerUsageSnooper.func_52022_a("whitelist_enabled", Boolean.valueOf(func_56426_af().func_57109_n()));
        par1PlayerUsageSnooper.func_52022_a("whitelist_count", Integer.valueOf(func_56426_af().func_57116_h().size()));
        super.func_56176_a(par1PlayerUsageSnooper);
    }

    public void func_56428_a(String par1Str, ICommandSender par2ICommandSender)
    {
        field_56437_k.add(new ServerCommand(par1Str, par2ICommandSender));
    }

    public void func_56429_ae()
    {
        ServerCommand servercommand;

        for (; !field_56437_k.isEmpty(); func_56344_E().func_55355_a(servercommand.field_57763_b, servercommand.field_57764_a))
        {
            servercommand = (ServerCommand)field_56437_k.remove(0);
        }
    }

    public boolean func_56371_Q()
    {
        return true;
    }

    public DedicatedPlayerList func_56426_af()
    {
        return (DedicatedPlayerList)super.func_56339_Z();
    }

    public NetworkListenThread func_56290_aa()
    {
        return field_56431_q;
    }

    public int func_56424_b(String par1Str, int par2)
    {
        return field_56433_n.func_57079_a(par1Str, par2);
    }

    public String func_56425_a(String par1Str, String par2Str)
    {
        return field_56433_n.func_57081_a(par1Str, par2Str);
    }

    public boolean func_56427_a(String par1Str, boolean par2)
    {
        return field_56433_n.func_57080_a(par1Str, par2);
    }

    public void func_56423_a(String par1Str, Object par2Obj)
    {
        field_56433_n.func_57077_a(par1Str, par2Obj);
    }

    public void func_56422_ag()
    {
        field_56433_n.func_57078_b();
    }

    public String func_56421_ah()
    {
        File file = field_56433_n.func_57075_c();

        if (file != null)
        {
            return file.getAbsolutePath();
        }
        else
        {
            return "No settings file";
        }
    }

    public boolean func_56372_ac()
    {
        return field_56430_r;
    }

    public String func_58032_a(EnumGameType par1EnumGameType, boolean par2)
    {
        return "";
    }

    public ServerConfigurationManager func_56339_Z()
    {
        return func_56426_af();
    }
}
