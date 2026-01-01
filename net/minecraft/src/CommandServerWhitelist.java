package net.minecraft.src;

import java.util.*;

public class CommandServerWhitelist extends CommandBase
{
    public CommandServerWhitelist()
    {
    }

    public String func_55223_a()
    {
        return "whitelist";
    }

    public String func_55224_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55085_a("commands.whitelist.usage", new Object[0]);
    }

    public void func_55225_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length >= 1)
        {
            if (par2ArrayOfStr[0].equals("on"))
            {
                MinecraftServer.func_56300_C().func_56339_Z().func_57099_a(true);
                func_55227_a(par1ICommandSender, "commands.whitelist.enabled", new Object[0]);
                return;
            }

            if (par2ArrayOfStr[0].equals("off"))
            {
                MinecraftServer.func_56300_C().func_56339_Z().func_57099_a(false);
                func_55227_a(par1ICommandSender, "commands.whitelist.disabled", new Object[0]);
                return;
            }

            if (par2ArrayOfStr[0].equals("list"))
            {
                par1ICommandSender.func_55086_a(par1ICommandSender.func_55085_a("commands.whitelist.list", new Object[]
                        {
                            Integer.valueOf(MinecraftServer.func_56300_C().func_56339_Z().func_57116_h().size()), Integer.valueOf(MinecraftServer.func_56300_C().func_56339_Z().func_57102_m().length)
                        }));
                par1ICommandSender.func_55086_a(func_56589_a(MinecraftServer.func_56300_C().func_56339_Z().func_57116_h().toArray(new String[0])));
                return;
            }

            if (par2ArrayOfStr[0].equals("add"))
            {
                if (par2ArrayOfStr.length < 2)
                {
                    throw new WrongUsageException("commands.whitelist.add.usage", new Object[0]);
                }
                else
                {
                    MinecraftServer.func_56300_C().func_56339_Z().func_57103_g(par2ArrayOfStr[1]);
                    func_55227_a(par1ICommandSender, "commands.whitelist.add.success", new Object[]
                            {
                                par2ArrayOfStr[1]
                            });
                    return;
                }
            }

            if (par2ArrayOfStr[0].equals("remove"))
            {
                if (par2ArrayOfStr.length < 2)
                {
                    throw new WrongUsageException("commands.whitelist.remove.usage", new Object[0]);
                }
                else
                {
                    MinecraftServer.func_56300_C().func_56339_Z().func_57090_h(par2ArrayOfStr[1]);
                    func_55227_a(par1ICommandSender, "commands.whitelist.remove.success", new Object[]
                            {
                                par2ArrayOfStr[1]
                            });
                    return;
                }
            }

            if (par2ArrayOfStr[0].equals("reload"))
            {
                MinecraftServer.func_56300_C().func_56339_Z().func_57092_j();
                func_55227_a(par1ICommandSender, "commands.whitelist.reloaded", new Object[0]);
                return;
            }
        }

        throw new WrongUsageException("commands.whitelist.usage", new Object[0]);
    }

    public List func_55226_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1)
        {
            return func_55232_a(par2ArrayOfStr, new String[]
                    {
                        "on", "off", "list", "add", "remove", "reload"
                    });
        }

        if (par2ArrayOfStr.length == 2)
        {
            if (par2ArrayOfStr[0].equals("add"))
            {
                String as[] = MinecraftServer.func_56300_C().func_56339_Z().func_57102_m();
                ArrayList arraylist = new ArrayList();
                String s = par2ArrayOfStr[par2ArrayOfStr.length - 1];
                String as1[] = as;
                int i = as1.length;

                for (int j = 0; j < i; j++)
                {
                    String s1 = as1[j];

                    if (func_55231_a(s, s1) && !MinecraftServer.func_56300_C().func_56339_Z().func_57116_h().contains(s1))
                    {
                        arraylist.add(s1);
                    }
                }

                return arraylist;
            }

            if (par2ArrayOfStr[0].equals("remove"))
            {
                return func_56591_a(par2ArrayOfStr, MinecraftServer.func_56300_C().func_56339_Z().func_57116_h());
            }
        }

        return null;
    }
}
