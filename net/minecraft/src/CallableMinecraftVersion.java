package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableMinecraftVersion implements Callable
{
    final CrashReport field_55338_a;

    CallableMinecraftVersion(CrashReport par1CrashReport)
    {
        field_55338_a = par1CrashReport;
    }

    public String func_55337_a()
    {
        return "12w26a";
    }

    public Object call()
    {
        return func_55337_a();
    }
}
