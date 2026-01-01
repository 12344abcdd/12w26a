package net.minecraft.src;

public class CommandDefaultGameMode extends CommandGameMode
{
    public CommandDefaultGameMode()
    {
    }

    public String func_55223_a()
    {
        return "defaultgamemode";
    }

    public String func_55224_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55085_a("commands.defaultgamemode.usage", new Object[0]);
    }

    public void func_55225_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length > 0)
        {
            EnumGameType enumgametype = func_56593_b(par1ICommandSender, par2ArrayOfStr[0]);
            func_56594_a(enumgametype);
            String s = StatCollector.translateToLocal((new StringBuilder()).append("gameMode.").append(enumgametype.func_57539_b()).toString());
            func_55227_a(par1ICommandSender, "commands.defaultgamemode.success", new Object[]
                    {
                        s
                    });
            return;
        }
        else
        {
            throw new WrongUsageException("commands.defaultgamemode.usage", new Object[0]);
        }
    }

    protected void func_56594_a(EnumGameType par1EnumGameType)
    {
        MinecraftServer.func_56300_C().func_58031_a(par1EnumGameType);
    }
}
