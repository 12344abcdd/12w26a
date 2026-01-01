package net.minecraft.src;

public class CommandToggleDownfall extends CommandBase
{
    public CommandToggleDownfall()
    {
    }

    public String func_55223_a()
    {
        return "toggledownfall";
    }

    public void func_55225_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        func_55236_c();
        func_55227_a(par1ICommandSender, "commands.downfall.success", new Object[0]);
    }

    protected void func_55236_c()
    {
        MinecraftServer.func_56300_C().field_56392_b[0].func_55265_q();
        MinecraftServer.func_56300_C().field_56392_b[0].getWorldInfo().setThundering(true);
    }
}
