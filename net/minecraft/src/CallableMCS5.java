package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableMCS5 implements Callable
{
    final MinecraftServer field_56927_a;

    CallableMCS5(MinecraftServer par1MinecraftServer)
    {
        field_56927_a = par1MinecraftServer;
    }

    public String func_56926_a()
    {
        String s = field_56927_a.func_56347_B();

        if (!s.equals("vanilla"))
        {
            return (new StringBuilder()).append("Definitely; '").append(s).append("'").toString();
        }
        else
        {
            return "Unknown (can't tell)";
        }
    }

    public Object call()
    {
        return func_56926_a();
    }
}
