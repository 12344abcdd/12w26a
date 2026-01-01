package net.minecraft.src;

import java.util.Set;
import java.util.concurrent.Callable;

class CallableMPL2 implements Callable
{
    final WorldClient field_55285_a;

    CallableMPL2(WorldClient par1WorldClient)
    {
        field_55285_a = par1WorldClient;
    }

    public String func_55284_a()
    {
        return (new StringBuilder()).append(WorldClient.func_55272_b(field_55285_a).size()).append(" total; ").append(WorldClient.func_55272_b(field_55285_a).toString()).toString();
    }

    public Object call()
    {
        return func_55284_a();
    }
}
