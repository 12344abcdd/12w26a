package net.minecraft.src;

import java.util.List;

public class CommandServerKick extends CommandBase
{
    public CommandServerKick()
    {
    }

    public String func_55223_a()
    {
        return "kick";
    }

    public String func_55224_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55085_a("commands.kick.usage", new Object[0]);
    }

    public void func_55225_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length > 0 && par2ArrayOfStr[0].length() > 1)
        {
            EntityPlayerMP entityplayermp = MinecraftServer.func_56300_C().func_56339_Z().func_57094_f(par2ArrayOfStr[0]);
            String s = "Kicked by an operator.";
            boolean flag = false;

            if (entityplayermp == null)
            {
                throw new PlayerNotFoundException();
            }

            if (par2ArrayOfStr.length >= 2)
            {
                s = func_56588_a(par2ArrayOfStr, 1);
                flag = true;
            }

            entityplayermp.field_56268_a.func_56716_a(s);

            if (flag)
            {
                func_55227_a(par1ICommandSender, "commands.kick.success.reason", new Object[]
                        {
                            entityplayermp.func_55080_n(), s
                        });
            }
            else
            {
                func_55227_a(par1ICommandSender, "commands.kick.success", new Object[]
                        {
                            entityplayermp.func_55080_n()
                        });
            }

            return;
        }
        else
        {
            throw new WrongUsageException("commands.kick.usage", new Object[0]);
        }
    }

    public List func_55226_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length >= 1)
        {
            return func_55232_a(par2ArrayOfStr, MinecraftServer.func_56300_C().func_56332_x());
        }
        else
        {
            return null;
        }
    }
}
