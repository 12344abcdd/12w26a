package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableLvl3 implements Callable
{
    final World field_55405_a;

    CallableLvl3(World par1World)
    {
        field_55405_a = par1World;
    }

    public String func_55404_a()
    {
        return field_55405_a.chunkProvider.makeString();
    }

    public Object call()
    {
        return func_55404_a();
    }
}
