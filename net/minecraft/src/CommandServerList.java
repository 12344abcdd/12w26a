package net.minecraft.src;

public class CommandServerList extends CommandBase
{
    public CommandServerList()
    {
    }

    public String func_55223_a()
    {
        return "list";
    }

    public void func_55225_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        par1ICommandSender.func_55086_a(par1ICommandSender.func_55085_a("commands.players.list", new Object[]
                {
                    Integer.valueOf(MinecraftServer.func_56300_C().func_56360_v()), Integer.valueOf(MinecraftServer.func_56300_C().func_56308_w())
                }));
        par1ICommandSender.func_55086_a(MinecraftServer.func_56300_C().func_56339_Z().func_57127_c());
    }
}
