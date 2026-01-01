package net.minecraft.src;

import java.util.*;

public class CommandHelp extends CommandBase
{
    public CommandHelp()
    {
    }

    public String func_55223_a()
    {
        return "help";
    }

    public String func_55224_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55085_a("commands.help.usage", new Object[0]);
    }

    public List func_55221_b()
    {
        return Arrays.asList(new String[]
                {
                    "?"
                });
    }

    public void func_55225_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        List list = func_55237_b(par1ICommandSender);
        byte byte0 = 7;
        int i = list.size() / byte0;
        int j = 0;

        try
        {
            j = par2ArrayOfStr.length != 0 ? func_55234_a(par1ICommandSender, par2ArrayOfStr[0], 1, i + 1) - 1 : 0;
        }
        catch (NumberInvalidException numberinvalidexception)
        {
            Map map = func_56592_c();
            ICommand icommand = (ICommand)map.get(par2ArrayOfStr[0]);

            if (icommand != null)
            {
                throw new WrongUsageException(icommand.func_55224_a(par1ICommandSender), new Object[0]);
            }
            else
            {
                throw new CommandNotFoundException();
            }
        }

        int k = Math.min((j + 1) * byte0, list.size());
        par1ICommandSender.func_55086_a((new StringBuilder()).append("\2472").append(par1ICommandSender.func_55085_a("commands.help.header", new Object[]
                {
                    Integer.valueOf(j + 1), Integer.valueOf(i + 1)
                })).toString());

        for (int l = j * byte0; l < k; l++)
        {
            ICommand icommand1 = (ICommand)list.get(l);
            par1ICommandSender.func_55086_a(icommand1.func_55224_a(par1ICommandSender));
        }

        if (j == 0)
        {
            par1ICommandSender.func_55086_a((new StringBuilder()).append("\247a").append(par1ICommandSender.func_55085_a("commands.help.footer", new Object[0])).toString());
        }
    }

    protected List func_55237_b(ICommandSender par1ICommandSender)
    {
        List list = MinecraftServer.func_56300_C().func_56344_E().func_55357_a(par1ICommandSender);
        Collections.sort(list);
        return list;
    }

    protected Map func_56592_c()
    {
        return MinecraftServer.func_56300_C().func_56344_E().func_57260_a();
    }
}
