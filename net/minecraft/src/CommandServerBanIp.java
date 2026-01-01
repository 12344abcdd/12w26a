package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandServerBanIp extends CommandBase
{
    public static final Pattern field_56596_a = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    public CommandServerBanIp()
    {
    }

    public String func_55223_a()
    {
        return "ban-ip";
    }

    public boolean func_55222_c(ICommandSender par1ICommandSender)
    {
        return MinecraftServer.func_56300_C().func_56339_Z().func_57101_f().func_57344_a() && super.func_55222_c(par1ICommandSender);
    }

    public String func_55224_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55085_a("commands.banip.usage", new Object[0]);
    }

    public void func_55225_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length >= 1 && par2ArrayOfStr[0].length() > 1)
        {
            Matcher matcher = field_56596_a.matcher(par2ArrayOfStr[0]);
            String s = null;

            if (par2ArrayOfStr.length >= 2)
            {
                s = func_56588_a(par2ArrayOfStr, 1);
            }

            if (matcher.matches())
            {
                func_56595_a(par1ICommandSender, par2ArrayOfStr[0], s);
            }
            else
            {
                EntityPlayerMP entityplayermp = MinecraftServer.func_56300_C().func_56339_Z().func_57094_f(par2ArrayOfStr[0]);

                if (entityplayermp == null)
                {
                    throw new PlayerNotFoundException("commands.banip.invalid", new Object[0]);
                }

                func_56595_a(par1ICommandSender, entityplayermp.func_56258_I(), s);
            }

            return;
        }
        else
        {
            throw new WrongUsageException("commands.banip.usage", new Object[0]);
        }
    }

    public List func_55226_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1)
        {
            return func_55232_a(par2ArrayOfStr, MinecraftServer.func_56300_C().func_56332_x());
        }
        else
        {
            return null;
        }
    }

    protected void func_56595_a(ICommandSender par1ICommandSender, String par2Str, String par3Str)
    {
        BanEntry banentry = new BanEntry(par2Str);
        banentry.func_57019_a(par1ICommandSender.func_55088_aJ());

        if (par3Str != null)
        {
            banentry.func_57020_b(par3Str);
        }

        MinecraftServer.func_56300_C().func_56339_Z().func_57101_f().func_57346_a(banentry);
        List list = MinecraftServer.func_56300_C().func_56339_Z().func_57089_i(par2Str);
        String as[] = new String[list.size()];
        int i = 0;

        for (Iterator iterator = list.iterator(); iterator.hasNext();)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();
            entityplayermp.field_56268_a.func_56716_a("You have been IP banned.");
            as[i++] = entityplayermp.func_55080_n();
        }

        if (list.isEmpty())
        {
            func_55227_a(par1ICommandSender, "commands.banip.success", new Object[]
                    {
                        par2Str
                    });
        }
        else
        {
            func_55227_a(par1ICommandSender, "commands.banip.success.players", new Object[]
                    {
                        par2Str, func_56589_a(as)
                    });
        }
    }
}
