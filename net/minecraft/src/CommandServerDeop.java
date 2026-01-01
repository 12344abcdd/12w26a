package net.minecraft.src;

import java.util.List;

public class CommandServerDeop extends CommandBase
{
    public CommandServerDeop()
    {
    }

    public String func_55223_a()
    {
        return "deop";
    }

    public String func_55224_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55085_a("commands.deop.usage", new Object[0]);
    }

    public void func_55225_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1 && par2ArrayOfStr[0].length() > 0)
        {
            MinecraftServer.func_56300_C().func_56339_Z().func_57091_c(par2ArrayOfStr[0]);
            func_55227_a(par1ICommandSender, "commands.deop.success", new Object[]
                    {
                        par2ArrayOfStr[0]
                    });
            return;
        }
        else
        {
            throw new WrongUsageException("commands.deop.usage", new Object[0]);
        }
    }

    public List func_55226_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1)
        {
            return func_56591_a(par2ArrayOfStr, MinecraftServer.func_56300_C().func_56339_Z().func_57108_i());
        }
        else
        {
            return null;
        }
    }
}
