package net.minecraft.src;

public class CommandServerStop extends CommandBase
{
    public CommandServerStop()
    {
    }

    public String func_55223_a()
    {
        return "stop";
    }

    public void func_55225_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        func_55227_a(par1ICommandSender, "commands.stop.start", new Object[0]);
        MinecraftServer.func_56300_C().func_56286_k();
    }
}
