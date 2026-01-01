package net.minecraft.src;

import java.util.concurrent.Callable;

class CallablePlayers implements Callable
{
    final MinecraftServer field_58103_a;

    CallablePlayers(MinecraftServer par1MinecraftServer)
    {
        field_58103_a = par1MinecraftServer;
    }

    public String func_58102_a()
    {
        return (new StringBuilder()).append(MinecraftServer.func_56345_a(field_58103_a).func_57121_k()).append(" / ").append(MinecraftServer.func_56345_a(field_58103_a).func_57086_l()).append("; ").append(MinecraftServer.func_56345_a(field_58103_a).field_57133_b).toString();
    }

    public Object call()
    {
        return func_58102_a();
    }
}
