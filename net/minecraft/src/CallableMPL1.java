package net.minecraft.src;

import java.util.Set;
import java.util.concurrent.Callable;

class CallableMPL1 implements Callable
{
    final WorldClient field_55287_a;

    CallableMPL1(WorldClient par1WorldClient)
    {
        field_55287_a = par1WorldClient;
    }

    public String func_55286_a()
    {
        return (new StringBuilder()).append(WorldClient.func_55273_a(field_55287_a).size()).append(" total; ").append(WorldClient.func_55273_a(field_55287_a).toString()).toString();
    }

    public Object call()
    {
        return func_55286_a();
    }
}
