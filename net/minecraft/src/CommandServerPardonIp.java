package net.minecraft.src;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandServerPardonIp extends CommandBase
{
    public CommandServerPardonIp()
    {
    }

    public String func_55223_a()
    {
        return "pardon-ip";
    }

    public boolean func_55222_c(ICommandSender par1ICommandSender)
    {
        return MinecraftServer.func_56300_C().func_56339_Z().func_57101_f().func_57344_a() && super.func_55222_c(par1ICommandSender);
    }

    public String func_55224_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55085_a("commands.unbanip.usage", new Object[0]);
    }

    public void func_55225_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1 && par2ArrayOfStr[0].length() > 1)
        {
            Matcher matcher = CommandServerBanIp.field_56596_a.matcher(par2ArrayOfStr[0]);

            if (matcher.matches())
            {
                MinecraftServer.func_56300_C().func_56339_Z().func_57101_f().func_57347_b(par2ArrayOfStr[0]);
                func_55227_a(par1ICommandSender, "commands.unbanip.success", new Object[]
                        {
                            par2ArrayOfStr[0]
                        });
                return;
            }
            else
            {
                throw new SyntaxErrorException("commands.unbanip.invalid", new Object[0]);
            }
        }
        else
        {
            throw new WrongUsageException("commands.unbanip.usage", new Object[0]);
        }
    }

    public List func_55226_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1)
        {
            return func_56591_a(par2ArrayOfStr, MinecraftServer.func_56300_C().func_56339_Z().func_57101_f().func_57340_b().keySet());
        }
        else
        {
            return null;
        }
    }
}
