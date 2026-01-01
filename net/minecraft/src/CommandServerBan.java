package net.minecraft.src;

import java.util.List;

public class CommandServerBan extends CommandBase
{
    public CommandServerBan()
    {
    }

    public String func_55223_a()
    {
        return "ban";
    }

    public String func_55224_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55085_a("commands.ban.usage", new Object[0]);
    }

    public boolean func_55222_c(ICommandSender par1ICommandSender)
    {
        return MinecraftServer.func_56300_C().func_56339_Z().func_57110_e().func_57344_a() && super.func_55222_c(par1ICommandSender);
    }

    public void func_55225_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length >= 1 && par2ArrayOfStr[0].length() > 0)
        {
            EntityPlayerMP entityplayermp = MinecraftServer.func_56300_C().func_56339_Z().func_57094_f(par2ArrayOfStr[0]);
            BanEntry banentry = new BanEntry(par2ArrayOfStr[0]);
            banentry.func_57019_a(par1ICommandSender.func_55088_aJ());

            if (par2ArrayOfStr.length >= 2)
            {
                banentry.func_57020_b(func_56588_a(par2ArrayOfStr, 1));
            }

            MinecraftServer.func_56300_C().func_56339_Z().func_57110_e().func_57346_a(banentry);

            if (entityplayermp != null)
            {
                entityplayermp.field_56268_a.func_56716_a("You are banned from this server.");
            }

            func_55227_a(par1ICommandSender, "commands.ban.success", new Object[]
                    {
                        par2ArrayOfStr[0]
                    });
            return;
        }
        else
        {
            throw new WrongUsageException("commands.ban.usage", new Object[0]);
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
