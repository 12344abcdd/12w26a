package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableOSInfo implements Callable
{
    final CrashReport field_55344_a;

    CallableOSInfo(CrashReport par1CrashReport)
    {
        field_55344_a = par1CrashReport;
    }

    public String func_55343_a()
    {
        return (new StringBuilder()).append(System.getProperty("os.name")).append(" (").append(System.getProperty("os.arch")).append(") version ").append(System.getProperty("os.version")).toString();
    }

    public Object call()
    {
        return func_55343_a();
    }
}
