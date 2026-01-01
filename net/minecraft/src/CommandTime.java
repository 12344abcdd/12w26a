package net.minecraft.src;

import java.util.List;

public class CommandTime extends CommandBase
{
    public CommandTime()
    {
    }

    public String func_55223_a()
    {
        return "time";
    }

    public String func_55224_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55085_a("commands.time.usage", new Object[0]);
    }

    public void func_55225_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length > 1)
        {
            if (par2ArrayOfStr[0].equals("set"))
            {
                int i;

                if (par2ArrayOfStr[1].equals("day"))
                {
                    i = 0;
                }
                else if (par2ArrayOfStr[1].equals("night"))
                {
                    i = 12500;
                }
                else
                {
                    i = func_55229_a(par1ICommandSender, par2ArrayOfStr[1], 0);
                }

                func_55239_a(par1ICommandSender, i);
                func_55227_a(par1ICommandSender, "commands.time.set", new Object[]
                        {
                            Integer.valueOf(i)
                        });
                return;
            }

            if (par2ArrayOfStr[0].equals("add"))
            {
                int j = func_55229_a(par1ICommandSender, par2ArrayOfStr[1], 0);
                func_55238_b(par1ICommandSender, j);
                func_55227_a(par1ICommandSender, "commands.time.added", new Object[]
                        {
                            Integer.valueOf(j)
                        });
                return;
            }
        }

        throw new WrongUsageException("commands.time.usage", new Object[0]);
    }

    public List func_55226_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1)
        {
            return func_55232_a(par2ArrayOfStr, new String[]
                    {
                        "set", "add"
                    });
        }

        if (par2ArrayOfStr.length == 2 && par2ArrayOfStr[0].equals("set"))
        {
            return func_55232_a(par2ArrayOfStr, new String[]
                    {
                        "day", "night"
                    });
        }
        else
        {
            return null;
        }
    }

    protected void func_55239_a(ICommandSender par1ICommandSender, int par2)
    {
        for (int i = 0; i < MinecraftServer.func_56300_C().field_56392_b.length; i++)
        {
            MinecraftServer.func_56300_C().field_56392_b[i].func_56856_b(par2);
        }
    }

    protected void func_55238_b(ICommandSender par1ICommandSender, int par2)
    {
        for (int i = 0; i < MinecraftServer.func_56300_C().field_56392_b.length; i++)
        {
            WorldServer worldserver = MinecraftServer.func_56300_C().field_56392_b[i];
            worldserver.func_56856_b(worldserver.getWorldTime() + (long)par2);
        }
    }
}
