package net.minecraft.src;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class PlayerUsageSnooper
{
    private Map field_52025_a;
    private final String field_57216_b = UUID.randomUUID().toString();
    private final URL field_52024_b;
    private final IPlayerUsage field_57214_d;
    private final java.util.Timer field_57215_e = new java.util.Timer("Snooper Timer", true);
    private final Object field_57212_f = new Object();
    private boolean field_57213_g;
    private int field_57217_h;

    public PlayerUsageSnooper(String par1Str, IPlayerUsage par2IPlayerUsage)
    {
        field_52025_a = new HashMap();
        field_57213_g = false;
        field_57217_h = 0;

        try
        {
            field_52024_b = new URL((new StringBuilder()).append("http://snoop.minecraft.net/").append(par1Str).append("?version=").append(1).toString());
        }
        catch (MalformedURLException malformedurlexception)
        {
            throw new IllegalArgumentException();
        }

        field_57214_d = par2IPlayerUsage;
    }

    public void func_57205_a()
    {
        if (field_57213_g)
        {
            return;
        }
        else
        {
            field_57213_g = true;
            func_57206_e();
            field_57215_e.schedule(new PlayerUsageSnooperThread(this), 0L, 0xdbba0L);
            return;
        }
    }

    private void func_57206_e()
    {
        func_57211_f();
        func_52022_a("snooper_token", field_57216_b);
        func_52022_a("os_name", System.getProperty("os.name"));
        func_52022_a("os_version", System.getProperty("os.version"));
        func_52022_a("os_architecture", System.getProperty("os.arch"));
        func_52022_a("java_version", System.getProperty("java.version"));
        func_52022_a("version", "12w26a");
        field_57214_d.func_56177_b(this);
    }

    private void func_57211_f()
    {
        RuntimeMXBean runtimemxbean = ManagementFactory.getRuntimeMXBean();
        List list = runtimemxbean.getInputArguments();
        int i = 0;
        String s;

        for (Iterator iterator = list.iterator(); iterator.hasNext(); func_52022_a((new StringBuilder()).append("jvm_arg[").append(i++).append("]").toString(), s))
        {
            s = (String)iterator.next();
        }

        func_52022_a("jvm_args", Integer.valueOf(i));
    }

    public void func_57207_b()
    {
        func_52022_a("memory_total", Long.valueOf(Runtime.getRuntime().totalMemory()));
        func_52022_a("memory_max", Long.valueOf(Runtime.getRuntime().maxMemory()));
        func_52022_a("memory_free", Long.valueOf(Runtime.getRuntime().freeMemory()));
        func_52022_a("cpu_cores", Integer.valueOf(Runtime.getRuntime().availableProcessors()));
        field_57214_d.func_56176_a(this);
    }

    public void func_52022_a(String par1Str, Object par2Obj)
    {
        synchronized (field_57212_f)
        {
            field_52025_a.put(par1Str, par2Obj);
        }
    }

    public boolean func_57210_c()
    {
        return field_57213_g;
    }

    public void func_57203_d()
    {
        field_57215_e.cancel();
    }

    static Object func_57204_a(PlayerUsageSnooper par0PlayerUsageSnooper)
    {
        return par0PlayerUsageSnooper.field_57212_f;
    }

    static Map func_52020_b(PlayerUsageSnooper par0PlayerUsageSnooper)
    {
        return par0PlayerUsageSnooper.field_52025_a;
    }

    static int func_57208_c(PlayerUsageSnooper par0PlayerUsageSnooper)
    {
        return par0PlayerUsageSnooper.field_57217_h++;
    }

    static URL func_57209_d(PlayerUsageSnooper par0PlayerUsageSnooper)
    {
        return par0PlayerUsageSnooper.field_52024_b;
    }
}
