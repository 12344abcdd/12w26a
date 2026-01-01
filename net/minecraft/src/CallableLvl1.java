package net.minecraft.src;

import java.util.List;
import java.util.concurrent.Callable;

class CallableLvl1 implements Callable
{
    final World field_55407_a;

    CallableLvl1(World par1World)
    {
        field_55407_a = par1World;
    }

    public String func_55406_a()
    {
        return (new StringBuilder()).append(field_55407_a.loadedEntityList.size()).append(" total; ").append(field_55407_a.loadedEntityList.toString()).toString();
    }

    public Object call()
    {
        return func_55406_a();
    }
}
