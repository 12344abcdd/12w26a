package net.minecraft.src;

public class DemoWorldServer extends WorldServer
{
    private static final long field_56875_L;
    public static final WorldSettings field_56874_a;

    public DemoWorldServer(MinecraftServer par1MinecraftServer, ISaveHandler par2ISaveHandler, String par3Str, int par4)
    {
        super(par1MinecraftServer, par2ISaveHandler, par3Str, par4, field_56874_a);
    }

    static
    {
        field_56875_L = "North Carolina".hashCode();
        field_56874_a = (new WorldSettings(field_56875_L, EnumGameType.SURVIVAL, true, false, WorldType.DEFAULT)).func_55250_a();
    }
}
