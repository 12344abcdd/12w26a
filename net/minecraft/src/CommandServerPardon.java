package net.minecraft.src;

import java.util.List;
import java.util.Map;

public class CommandServerPardon extends CommandBase
{
    public CommandServerPardon()
    {
    }

    public String func_55223_a()
    {
        return "pardon";
    }

    public String func_55224_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55085_a("commands.unban.usage", new Object[0]);
    }

    public boolean func_55222_c(ICommandSender par1ICommandSender)
    {
        return MinecraftServer.func_56300_C().func_56339_Z().func_57110_e().func_57344_a() && super.func_55222_c(par1ICommandSender);
    }

    public void func_55225_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1 && par2ArrayOfStr[0].length() > 0)
        {
            MinecraftServer.func_56300_C().func_56339_Z().func_57110_e().func_57347_b(par2ArrayOfStr[0]);
            func_55227_a(par1ICommandSender, "commands.unban.success", new Object[]
                    {
                        par2ArrayOfStr[0]
                    });
            return;
        }
        else
        {
            throw new WrongUsageException("commands.unban.usage", new Object[0]);
        }
    }

    public List func_55226_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1)
        {
            return func_56591_a(par2ArrayOfStr, MinecraftServer.func_56300_C().func_56339_Z().func_57110_e().func_57340_b().keySet());
        }
        else
        {
            return null;
        }
    }
}
