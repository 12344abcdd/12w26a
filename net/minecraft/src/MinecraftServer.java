package net.minecraft.src;

import java.io.File;
import java.io.IOException;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class MinecraftServer implements ICommandSender, IPlayerUsage, Runnable
{
    public static Logger field_56394_a = Logger.getLogger("Minecraft");
    private static MinecraftServer field_56400_k = null;
    private final ISaveFormat field_56397_l;
    private final PlayerUsageSnooper field_56398_m = new PlayerUsageSnooper("server", this);
    private final File field_56395_n;
    private final List field_56396_o = new ArrayList();
    private final ICommandManager field_56410_p;
    private String field_56409_q;
    private int field_56408_r;
    public WorldServer field_56392_b[];
    private ServerConfigurationManager field_56407_s;
    private boolean field_56406_t;
    private boolean field_56405_u;
    private int field_56404_v;
    public String field_56393_c;
    public int field_56390_d;
    private boolean field_56403_w;
    private boolean field_56413_x;
    private boolean field_56412_y;
    private boolean field_56411_z;
    private boolean field_56377_A;
    private String field_56378_B;
    private int field_56379_C;
    private long field_56373_D;
    private long field_56374_E;
    private long field_56375_F;
    private long field_56376_G;
    public final long field_56391_e[] = new long[100];
    public final long field_56388_f[] = new long[100];
    public final long field_56389_g[] = new long[100];
    public final long field_56401_h[] = new long[100];
    public final long field_56402_i[] = new long[100];
    public long field_56399_j[][];
    private KeyPair field_56384_H;
    private String field_56385_I;
    private String field_58035_J;
    private String field_56386_J;
    private boolean field_56387_K;
    private boolean field_56380_L;
    private boolean field_56381_M;
    private String field_56382_N;
    private boolean field_56383_O;
    private long field_58036_Q;
    private String field_58037_R;

    public MinecraftServer(File par1File)
    {
        field_56408_r = -1;
        field_56406_t = true;
        field_56405_u = false;
        field_56404_v = 0;
        field_56382_N = "";
        field_56383_O = false;
        field_56400_k = this;
        field_56395_n = par1File;
        field_56397_l = new AnvilSaveConverter(par1File);
        field_56410_p = new ServerCommandManager();
    }

    protected abstract boolean func_56341_a() throws IOException;

    protected void func_56294_a(String par1Str)
    {
        if (func_56328_M().isOldMapFormat(par1Str))
        {
            field_56394_a.info("Converting map!");
            func_58027_d("menu.convertingLevel");
            func_56328_M().convertMapFormat(par1Str, new ConvertProgressUpdater(this));
        }
    }

    protected synchronized void func_58027_d(String par1Str)
    {
        field_58037_R = par1Str;
    }

    public synchronized String func_58030_b()
    {
        return field_58037_R;
    }

    protected void func_58028_a(String par1Str, String par2Str, long par3, WorldType par5WorldType)
    {
        func_56294_a(par1Str);
        func_58027_d("menu.loadingLevel");
        field_56392_b = new WorldServer[3];
        field_56399_j = new long[field_56392_b.length][100];
        ISaveHandler isavehandler = field_56397_l.getSaveLoader(par1Str, true);
        WorldInfo worldinfo = isavehandler.loadWorldInfo();
        WorldSettings worldsettings;

        if (worldinfo == null)
        {
            worldsettings = new WorldSettings(par3, func_56366_d(), func_56333_c(), func_56363_f(), par5WorldType);
        }
        else
        {
            worldsettings = new WorldSettings(worldinfo);
        }

        if (field_56380_L)
        {
            worldsettings.func_55250_a();
        }

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
                    field_56392_b[i] = new WorldServer(this, isavehandler, par2Str, byte0, worldsettings);
                }
            }
            else
            {
                field_56392_b[i] = new WorldServerMulti(this, isavehandler, par2Str, byte0, worldsettings, field_56392_b[0]);
            }

            field_56392_b[i].addWorldAccess(new WorldManager(this, field_56392_b[i]));

            if (!func_56307_I())
            {
                field_56392_b[i].getWorldInfo().func_56911_a(func_56366_d());
            }

            field_56407_s.func_57096_a(field_56392_b);
        }

        func_56364_c(func_56356_e());
        func_56312_b();
    }

    protected void func_56312_b()
    {
        char c = '\304';
        long l = System.currentTimeMillis();
        func_58027_d("menu.generatingTerrain");

        for (int i = 0; i < 1; i++)
        {
            field_56394_a.info((new StringBuilder()).append("Preparing start region for level ").append(i).toString());
            WorldServer worldserver = field_56392_b[i];
            ChunkCoordinates chunkcoordinates = worldserver.getSpawnPoint();

            for (int j = -c; j <= c && func_56349_j(); j += 16)
            {
                for (int k = -c; k <= c && func_56349_j(); k += 16)
                {
                    long l1 = System.currentTimeMillis();

                    if (l1 < l)
                    {
                        l = l1;
                    }

                    if (l1 > l + 1000L)
                    {
                        int i1 = (c * 2 + 1) * (c * 2 + 1);
                        int j1 = (j + c) * (c * 2 + 1) + (k + 1);
                        func_56313_a("Preparing spawn area", (j1 * 100) / i1);
                        l = l1;
                    }

                    worldserver.field_56871_I.loadChunk(chunkcoordinates.posX + j >> 4, chunkcoordinates.posZ + k >> 4);

                    while (worldserver.updatingLighting() && func_56349_j()) ;
                }
            }
        }

        func_56361_g();
    }

    public abstract boolean func_56333_c();

    public abstract EnumGameType func_56366_d();

    public abstract int func_56356_e();

    public abstract boolean func_56363_f();

    protected void func_56313_a(String par1Str, int par2)
    {
        field_56393_c = par1Str;
        field_56390_d = par2;
        field_56394_a.info((new StringBuilder()).append(par1Str).append(": ").append(par2).append("%").toString());
    }

    protected void func_56361_g()
    {
        field_56393_c = null;
        field_56390_d = 0;
    }

    protected void func_58034_a(boolean par1)
    {
        if (field_56381_M)
        {
            return;
        }

        WorldServer aworldserver[] = field_56392_b;
        int i = aworldserver.length;

        for (int j = 0; j < i; j++)
        {
            WorldServer worldserver = aworldserver[j];

            if (worldserver == null)
            {
                continue;
            }

            if (!par1)
            {
                field_56394_a.info((new StringBuilder()).append("Saving chunks for level '").append(worldserver.getWorldInfo().getWorldName()).append("'/").append(worldserver.worldProvider).toString());
            }

            try
            {
                worldserver.func_56849_a(true, null);
            }
            catch (MinecraftException minecraftexception)
            {
                field_56394_a.warning(minecraftexception.getMessage());
            }
        }
    }

    protected void func_56368_ae()
    {
        if (field_56381_M)
        {
            return;
        }

        field_56394_a.info("Stopping server");

        if (func_56290_aa() != null)
        {
            func_56290_aa().func_56958_a();
        }

        if (field_56407_s != null)
        {
            field_56394_a.info("Saving players");
            field_56407_s.func_57117_g();
            field_56407_s.func_58124_r();
        }

        field_56394_a.info("Saving worlds");
        func_58034_a(false);
        WorldServer aworldserver[] = field_56392_b;
        int i = aworldserver.length;

        for (int j = 0; j < i; j++)
        {
            WorldServer worldserver = aworldserver[j];
            worldserver.func_56850_I();
        }

        if (field_56398_m != null && field_56398_m.func_57210_c())
        {
            field_56398_m.func_57203_d();
        }
    }

    public String func_56295_i()
    {
        return field_56409_q;
    }

    public void func_56301_d(String par1Str)
    {
        field_56409_q = par1Str;
    }

    public boolean func_56349_j()
    {
        return field_56406_t;
    }

    public void func_56286_k()
    {
        field_56406_t = false;
    }

    public void run()
    {
        try
        {
            if (func_56341_a())
            {
                long l = System.currentTimeMillis();
                long l1 = 0L;

                while (field_56406_t)
                {
                    long l2 = System.currentTimeMillis();
                    long l3 = l2 - l;

                    if (l3 > 2000L && l - field_58036_Q >= 15000L)
                    {
                        field_56394_a.warning("Can't keep up! Did the system time change, or is the server overloaded?");
                        l3 = 2000L;
                        field_58036_Q = l;
                    }

                    if (l3 < 0L)
                    {
                        field_56394_a.warning("Time ran backwards! Did the system time change?");
                        l3 = 0L;
                    }

                    l1 += l3;
                    l = l2;

                    if (field_56392_b[0].func_56855_E())
                    {
                        func_56322_n();
                        l1 = 0L;
                    }
                    else
                    {
                        while (l1 > 50L)
                        {
                            l1 -= 50L;
                            func_56322_n();
                        }
                    }

                    Thread.sleep(1L);
                    field_56383_O = true;
                }
            }
            else
            {
                func_56370_a(null);
            }
        }
        catch (Throwable throwable1)
        {
            throwable1.printStackTrace();
            field_56394_a.log(Level.SEVERE, (new StringBuilder()).append("Encountered an unexpected exception ").append(throwable1.getClass().getSimpleName()).toString(), throwable1);
            CrashReport crashreport = func_56327_b(new CrashReport("Exception in server tick loop", throwable1));
            File file = new File(new File(func_56358_l(), "crash-reports"), (new StringBuilder()).append("crash-").append((new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date())).append("-server.txt").toString());

            if (crashreport.func_55370_a(file))
            {
                field_56394_a.severe((new StringBuilder()).append("This crash report has been saved to: ").append(file.getAbsolutePath()).toString());
            }
            else
            {
                field_56394_a.severe("We were unable to save this crash report to disk.");
            }

            func_56370_a(crashreport);
        }
        finally
        {
            try
            {
                func_56368_ae();
                field_56405_u = true;
            }
            catch (Throwable throwable2)
            {
                throwable2.printStackTrace();
            }
            finally
            {
                func_56346_m();
            }
        }
    }

    protected File func_56358_l()
    {
        return new File(".");
    }

    protected void func_56370_a(CrashReport crashreport)
    {
    }

    protected void func_56346_m()
    {
    }

    protected void func_56322_n()
    {
        long l = System.nanoTime();
        AxisAlignedBB.func_58144_a().func_58151_a();
        Vec3.func_58128_a().func_58135_a();
        field_56404_v++;
        func_56292_o();

        if (field_56404_v % 100 == 0)
        {
            Profiler.endStartSection("save");
            field_56407_s.func_57117_g();
            func_58034_a(true);
        }

        field_56402_i[field_56404_v % 100] = System.nanoTime() - l;
        field_56391_e[field_56404_v % 100] = Packet.field_48157_o - field_56373_D;
        field_56373_D = Packet.field_48157_o;
        field_56388_f[field_56404_v % 100] = Packet.field_48155_p - field_56374_E;
        field_56374_E = Packet.field_48155_p;
        field_56389_g[field_56404_v % 100] = Packet.field_48158_m - field_56375_F;
        field_56375_F = Packet.field_48158_m;
        field_56401_h[field_56404_v % 100] = Packet.field_48156_n - field_56376_G;
        field_56376_G = Packet.field_48156_n;

        if (!field_56398_m.func_57210_c() && field_56404_v > 100)
        {
            field_56398_m.func_57205_a();
        }

        if (field_56404_v % 6000 == 0)
        {
            field_56398_m.func_57207_b();
        }
    }

    protected void func_56292_o()
    {
        for (int i = 0; i < field_56392_b.length; i++)
        {
            long l = System.nanoTime();

            if (i == 0 || func_56354_p())
            {
                WorldServer worldserver = field_56392_b[i];

                if (field_56404_v % 20 == 0)
                {
                    field_56407_s.func_57105_a(new Packet4UpdateTime(worldserver.getWorldTime()), worldserver.worldProvider.worldType);
                }

                worldserver.tick();

                while (worldserver.updatingLighting()) ;

                worldserver.updateEntities();
                worldserver.func_56859_K().func_57592_a();
            }

            field_56399_j[i][field_56404_v % 100] = System.nanoTime() - l;
        }

        func_56290_aa().func_56960_b();
        field_56407_s.func_57104_b();
        IUpdatePlayerListBox iupdateplayerlistbox;

        for (Iterator iterator = field_56396_o.iterator(); iterator.hasNext(); iupdateplayerlistbox.func_56877_a())
        {
            iupdateplayerlistbox = (IUpdatePlayerListBox)iterator.next();
        }
    }

    public boolean func_56354_p()
    {
        return true;
    }

    public void func_56291_q()
    {
        (new ThreadServerApplication(this, "Server thread")).start();
    }

    public File func_56340_e(String par1Str)
    {
        return new File(par1Str);
    }

    public void func_56337_f(String par1Str)
    {
        field_56394_a.info(par1Str);
    }

    public void func_56311_g(String par1Str)
    {
        field_56394_a.warning(par1Str);
    }

    public WorldServer func_56325_a(int par1)
    {
        if (par1 == -1)
        {
            return field_56392_b[1];
        }

        if (par1 == 1)
        {
            return field_56392_b[2];
        }
        else
        {
            return field_56392_b[0];
        }
    }

    public String func_56288_r()
    {
        return field_56409_q;
    }

    public int func_56299_s()
    {
        return field_56408_r;
    }

    public String func_56367_t()
    {
        return field_56378_B;
    }

    public String func_56293_u()
    {
        return "12w26a";
    }

    public int func_56360_v()
    {
        return field_56407_s.func_57121_k();
    }

    public int func_56308_w()
    {
        return field_56407_s.func_57086_l();
    }

    public String[] func_56332_x()
    {
        return field_56407_s.func_57097_d();
    }

    public String func_56317_z()
    {
        return "";
    }

    public String func_56321_h(String par1Str)
    {
        RConConsoleSource.field_56285_a.func_56282_a();
        field_56410_p.func_55355_a(RConConsoleSource.field_56285_a, par1Str);
        return RConConsoleSource.field_56285_a.func_56283_b();
    }

    public boolean func_56350_A()
    {
        return false;
    }

    public void func_56320_i(String par1Str)
    {
        field_56394_a.log(Level.SEVERE, par1Str);
    }

    public void func_56324_j(String par1Str)
    {
        if (func_56350_A())
        {
            field_56394_a.log(Level.INFO, par1Str);
        }
    }

    public String func_56347_B()
    {
        return "vanilla";
    }

    protected CrashReport func_56327_b(CrashReport par1CrashReport)
    {
        par1CrashReport.func_55366_a("Is Modded", new CallableMCS5(this));

        if (field_56407_s != null)
        {
            par1CrashReport.func_55366_a("Player Count", new CallablePlayers(this));
        }

        if (field_56392_b != null)
        {
            WorldServer aworldserver[] = field_56392_b;
            int i = aworldserver.length;

            for (int j = 0; j < i; j++)
            {
                WorldServer worldserver = aworldserver[j];

                if (worldserver != null)
                {
                    worldserver.func_55266_a(par1CrashReport);
                }
            }
        }

        return par1CrashReport;
    }

    public List func_56315_a(ICommandSender par1ICommandSender, String par2Str)
    {
        ArrayList arraylist = new ArrayList();

        if (par2Str.startsWith("/"))
        {
            par2Str = par2Str.substring(1);
            boolean flag = !par2Str.contains(" ");
            List list = field_56410_p.func_55356_b(par1ICommandSender, par2Str);

            if (list != null)
            {
                for (Iterator iterator = list.iterator(); iterator.hasNext();)
                {
                    String s1 = (String)iterator.next();

                    if (flag)
                    {
                        arraylist.add((new StringBuilder()).append("/").append(s1).toString());
                    }
                    else
                    {
                        arraylist.add(s1);
                    }
                }
            }

            return arraylist;
        }

        String as[] = par2Str.split(" ", -1);
        String s = as[as.length - 1];
        String as1[] = field_56407_s.func_57097_d();
        int i = as1.length;

        for (int j = 0; j < i; j++)
        {
            String s2 = as1[j];

            if (CommandBase.func_55231_a(s, s2))
            {
                arraylist.add(s2);
            }
        }

        return arraylist;
    }

    public static MinecraftServer func_56300_C()
    {
        return field_56400_k;
    }

    public String func_55088_aJ()
    {
        return "Server";
    }

    public void func_55086_a(String par1Str)
    {
        field_56394_a.info(StringUtils.func_55394_a(par1Str));
    }

    public boolean func_55084_b(String par1Str)
    {
        return true;
    }

    public String func_55085_a(String par1Str, Object par2ArrayOfObj[])
    {
        return StringTranslate.getInstance().translateKeyFormat(par1Str, par2ArrayOfObj);
    }

    public ICommandManager func_56344_E()
    {
        return field_56410_p;
    }

    public KeyPair func_56355_F()
    {
        return field_56384_H;
    }

    public int func_56331_G()
    {
        return field_56408_r;
    }

    public void func_56336_b(int par1)
    {
        field_56408_r = par1;
    }

    public String func_56296_H()
    {
        return field_56385_I;
    }

    public void func_56316_k(String par1Str)
    {
        field_56385_I = par1Str;
    }

    public boolean func_56307_I()
    {
        return field_56385_I != null;
    }

    public String func_58029_J()
    {
        return field_58035_J;
    }

    public void func_58033_m(String par1Str)
    {
        field_58035_J = par1Str;
    }

    public void func_56362_l(String par1Str)
    {
        field_56386_J = par1Str;
    }

    public String func_56359_J()
    {
        return field_56386_J;
    }

    public void func_56330_a(KeyPair par1KeyPair)
    {
        field_56384_H = par1KeyPair;
    }

    public void func_56364_c(int par1)
    {
        for (int i = 0; i < field_56392_b.length; i++)
        {
            WorldServer worldserver = field_56392_b[i];

            if (worldserver == null)
            {
                continue;
            }

            if (worldserver.getWorldInfo().isHardcoreModeEnabled())
            {
                worldserver.difficultySetting = 3;
                worldserver.setAllowedSpawnTypes(true, true);
                continue;
            }

            if (func_56307_I())
            {
                worldserver.difficultySetting = par1;
                worldserver.setAllowedSpawnTypes(((World)(worldserver)).difficultySetting > 0, true);
            }
            else
            {
                worldserver.difficultySetting = par1;
                worldserver.setAllowedSpawnTypes(func_56303_K(), field_56413_x);
            }
        }
    }

    protected boolean func_56303_K()
    {
        return true;
    }

    public boolean func_56298_L()
    {
        return field_56387_K;
    }

    public void func_56352_a(boolean par1)
    {
        field_56387_K = par1;
    }

    public void func_56326_b(boolean par1)
    {
        field_56380_L = par1;
    }

    public ISaveFormat func_56328_M()
    {
        return field_56397_l;
    }

    public void func_56319_N()
    {
        field_56381_M = true;
        func_56328_M().flushCache();

        for (int i = 0; i < field_56392_b.length; i++)
        {
            WorldServer worldserver = field_56392_b[i];

            if (worldserver != null)
            {
                field_56392_b[0].func_56850_I();
            }
        }

        func_56328_M().deleteWorldDirectory(field_56392_b[0].getSaveHandler().getSaveDirectoryName());
        func_56286_k();
    }

    public String func_56310_O()
    {
        return field_56382_N;
    }

    public void func_56343_m(String par1Str)
    {
        field_56382_N = par1Str;
    }

    public void func_56176_a(PlayerUsageSnooper par1PlayerUsageSnooper)
    {
        par1PlayerUsageSnooper.func_52022_a("whitelist_enabled", Boolean.valueOf(false));
        par1PlayerUsageSnooper.func_52022_a("whitelist_count", Integer.valueOf(0));
        par1PlayerUsageSnooper.func_52022_a("players_current", Integer.valueOf(func_56360_v()));
        par1PlayerUsageSnooper.func_52022_a("players_max", Integer.valueOf(func_56308_w()));
        par1PlayerUsageSnooper.func_52022_a("players_seen", Integer.valueOf(field_56407_s.func_57102_m().length));
        par1PlayerUsageSnooper.func_52022_a("uses_auth", Boolean.valueOf(field_56403_w));
        par1PlayerUsageSnooper.func_52022_a("gui_state", func_56372_ac() ? "enabled" : "disabled");
        par1PlayerUsageSnooper.func_52022_a("avg_tick_ms", Integer.valueOf((int)(MathHelper.func_57510_a(field_56402_i) * 9.9999999999999995E-007D)));
        par1PlayerUsageSnooper.func_52022_a("avg_sent_packet_count", Integer.valueOf((int)MathHelper.func_57510_a(field_56391_e)));
        par1PlayerUsageSnooper.func_52022_a("avg_sent_packet_size", Integer.valueOf((int)MathHelper.func_57510_a(field_56388_f)));
        par1PlayerUsageSnooper.func_52022_a("avg_rec_packet_count", Integer.valueOf((int)MathHelper.func_57510_a(field_56389_g)));
        par1PlayerUsageSnooper.func_52022_a("avg_rec_packet_size", Integer.valueOf((int)MathHelper.func_57510_a(field_56401_h)));
        int i = 0;

        for (int j = 0; j < field_56392_b.length; j++)
        {
            if (field_56392_b[j] != null)
            {
                WorldServer worldserver = field_56392_b[j];
                WorldInfo worldinfo = worldserver.getWorldInfo();
                par1PlayerUsageSnooper.func_52022_a((new StringBuilder()).append("world[").append(i).append("][dimension]").toString(), Integer.valueOf(worldserver.worldProvider.worldType));
                par1PlayerUsageSnooper.func_52022_a((new StringBuilder()).append("world[").append(i).append("][mode]").toString(), worldinfo.func_56915_q());
                par1PlayerUsageSnooper.func_52022_a((new StringBuilder()).append("world[").append(i).append("][difficulty]").toString(), Integer.valueOf(worldserver.difficultySetting));
                par1PlayerUsageSnooper.func_52022_a((new StringBuilder()).append("world[").append(i).append("][hardcore]").toString(), Boolean.valueOf(worldinfo.isHardcoreModeEnabled()));
                par1PlayerUsageSnooper.func_52022_a((new StringBuilder()).append("world[").append(i).append("][generator_name]").toString(), worldinfo.getTerrainType().func_48628_a());
                par1PlayerUsageSnooper.func_52022_a((new StringBuilder()).append("world[").append(i).append("][generator_version]").toString(), Integer.valueOf(worldinfo.getTerrainType().getGeneratorVersion()));
                par1PlayerUsageSnooper.func_52022_a((new StringBuilder()).append("world[").append(i).append("][height]").toString(), Integer.valueOf(field_56379_C));
                par1PlayerUsageSnooper.func_52022_a((new StringBuilder()).append("world[").append(i).append("][chunks_loaded]").toString(), Integer.valueOf(worldserver.getChunkProvider().func_56896_d()));
                i++;
            }
        }

        par1PlayerUsageSnooper.func_52022_a("worlds", Integer.valueOf(i));
    }

    public void func_56177_b(PlayerUsageSnooper par1PlayerUsageSnooper)
    {
        par1PlayerUsageSnooper.func_52022_a("singleplayer", Boolean.valueOf(func_56307_I()));
        par1PlayerUsageSnooper.func_52022_a("server_brand", func_56347_B());
        par1PlayerUsageSnooper.func_52022_a("gui_supported", java.awt.GraphicsEnvironment.isHeadless() ? "headless" : "supported");
        par1PlayerUsageSnooper.func_52022_a("dedicated", Boolean.valueOf(func_56371_Q()));
    }

    public int func_56305_P()
    {
        return 16;
    }

    public abstract boolean func_56371_Q();

    public boolean func_56338_R()
    {
        return field_56403_w;
    }

    public void func_56306_c(boolean par1)
    {
        field_56403_w = par1;
    }

    public boolean func_56342_S()
    {
        return field_56413_x;
    }

    public void func_56369_d(boolean par1)
    {
        field_56413_x = par1;
    }

    public boolean func_56351_T()
    {
        return field_56412_y;
    }

    public void func_56323_e(boolean par1)
    {
        field_56412_y = par1;
    }

    public boolean func_56353_U()
    {
        return field_56411_z;
    }

    public void func_56335_f(boolean par1)
    {
        field_56411_z = par1;
    }

    public boolean func_56357_V()
    {
        return field_56377_A;
    }

    public void func_56289_g(boolean par1)
    {
        field_56377_A = par1;
    }

    public String func_56318_W()
    {
        return field_56378_B;
    }

    public void func_56314_n(String par1Str)
    {
        field_56378_B = par1Str;
    }

    public int func_56348_X()
    {
        return field_56379_C;
    }

    public void func_56297_d(int par1)
    {
        field_56379_C = par1;
    }

    public boolean func_56304_Y()
    {
        return field_56405_u;
    }

    public ServerConfigurationManager func_56339_Z()
    {
        return field_56407_s;
    }

    public void func_56309_a(ServerConfigurationManager par1ServerConfigurationManager)
    {
        field_56407_s = par1ServerConfigurationManager;
    }

    public void func_58031_a(EnumGameType par1EnumGameType)
    {
        for (int i = 0; i < field_56392_b.length; i++)
        {
            func_56300_C().field_56392_b[i].getWorldInfo().func_56911_a(par1EnumGameType);
        }
    }

    public abstract NetworkListenThread func_56290_aa();

    public boolean func_56365_ab()
    {
        return field_56383_O;
    }

    public boolean func_56372_ac()
    {
        return false;
    }

    public abstract String func_58032_a(EnumGameType enumgametype, boolean flag);

    static ServerConfigurationManager func_56345_a(MinecraftServer par0MinecraftServer)
    {
        return par0MinecraftServer.field_56407_s;
    }
}
