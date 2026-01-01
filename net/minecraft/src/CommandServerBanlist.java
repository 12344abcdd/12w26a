package net.minecraft.src;

import java.util.*;

public class CommandServerBanlist extends CommandBase
{
    public CommandServerBanlist()
    {
    }

    public String func_55223_a()
    {
        return "banlist";
    }

    public boolean func_55222_c(ICommandSender par1ICommandSender)
    {
        return (MinecraftServer.func_56300_C().func_56339_Z().func_57101_f().func_57344_a() || MinecraftServer.func_56300_C().func_56339_Z().func_57110_e().func_57344_a()) && super.func_55222_c(par1ICommandSender);
    }

    public String func_55224_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55085_a("commands.banlist.usage", new Object[0]);
    }

    public void func_55225_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length >= 1 && par2ArrayOfStr[0].equalsIgnoreCase("ips"))
        {
            par1ICommandSender.func_55086_a(par1ICommandSender.func_55085_a("commands.banlist.ips", new Object[]
                    {
                        Integer.valueOf(MinecraftServer.func_56300_C().func_56339_Z().func_57101_f().func_57340_b().size())
                    }));
            par1ICommandSender.func_55086_a(func_56589_a(MinecraftServer.func_56300_C().func_56339_Z().func_57101_f().func_57340_b().keySet().toArray()));
        }
        else
        {
            par1ICommandSender.func_55086_a(par1ICommandSender.func_55085_a("commands.banlist.players", new Object[]
                    {
                        Integer.valueOf(MinecraftServer.func_56300_C().func_56339_Z().func_57110_e().func_57340_b().size())
                    }));
            par1ICommandSender.func_55086_a(func_56589_a(MinecraftServer.func_56300_C().func_56339_Z().func_57110_e().func_57340_b().keySet().toArray()));
        }
    }

    public List func_55226_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1)
        {
            return func_55232_a(par2ArrayOfStr, new String[]
                    {
                        "players", "ips"
                    });
        }
        else
        {
            return null;
        }
    }
}
