package net.minecraft.src;

public class CommandServerPublishLocal extends CommandBase
{
    public CommandServerPublishLocal()
    {
    }

    public String func_55223_a()
    {
        return "publish";
    }

    public void func_55225_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        String s = MinecraftServer.func_56300_C().func_58032_a(EnumGameType.SURVIVAL, false);

        if (s != null)
        {
            func_55227_a(par1ICommandSender, "commands.publish.started", new Object[]
                    {
                        s
                    });
        }
        else
        {
            func_55227_a(par1ICommandSender, "commands.publish.failed", new Object[0]);
        }
    }
}
