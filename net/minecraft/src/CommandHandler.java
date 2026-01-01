package net.minecraft.src;

import java.util.*;

public class CommandHandler implements ICommandManager
{
    private final Map field_55361_a = new HashMap();
    private final Set field_55360_b = new HashSet();

    public CommandHandler()
    {
    }

    public void func_55355_a(ICommandSender par1ICommandSender, String par2Str)
    {
        if (par2Str.startsWith("/"))
        {
            par2Str = par2Str.substring(1);
        }

        String as[] = par2Str.split(" ");
        String s = as[0];
        as = func_55358_a(as);
        ICommand icommand = (ICommand)field_55361_a.get(s);

        try
        {
            if (icommand == null)
            {
                throw new CommandNotFoundException();
            }

            if (icommand.func_55222_c(par1ICommandSender))
            {
                icommand.func_55225_a(par1ICommandSender, as);
            }
            else
            {
                par1ICommandSender.func_55086_a("\247cYou do not have permission to use this command.");
            }
        }
        catch (WrongUsageException wrongusageexception)
        {
            par1ICommandSender.func_55086_a((new StringBuilder()).append("\247c").append(par1ICommandSender.func_55085_a("commands.generic.usage", new Object[]
                    {
                        par1ICommandSender.func_55085_a(wrongusageexception.getMessage(), wrongusageexception.func_55288_a())
                    })).toString());
        }
        catch (CommandException commandexception)
        {
            par1ICommandSender.func_55086_a((new StringBuilder()).append("\247c").append(par1ICommandSender.func_55085_a(commandexception.getMessage(), commandexception.func_55288_a())).toString());
        }
        catch (Throwable throwable)
        {
            par1ICommandSender.func_55086_a((new StringBuilder()).append("\247c").append(par1ICommandSender.func_55085_a("commands.generic.exception", new Object[0])).toString());
            throwable.printStackTrace();
        }
    }

    public ICommand func_55359_a(ICommand par1ICommand)
    {
        List list = par1ICommand.func_55221_b();
        field_55361_a.put(par1ICommand.func_55223_a(), par1ICommand);
        field_55360_b.add(par1ICommand);

        if (list != null)
        {
            Iterator iterator = list.iterator();

            do
            {
                if (!iterator.hasNext())
                {
                    break;
                }

                String s = (String)iterator.next();
                ICommand icommand = (ICommand)field_55361_a.get(s);

                if (icommand == null || !icommand.func_55223_a().equals(s))
                {
                    field_55361_a.put(s, par1ICommand);
                }
            }
            while (true);
        }

        return par1ICommand;
    }

    private static String[] func_55358_a(String par0ArrayOfStr[])
    {
        String as[] = new String[par0ArrayOfStr.length - 1];

        for (int i = 1; i < par0ArrayOfStr.length; i++)
        {
            as[i - 1] = par0ArrayOfStr[i];
        }

        return as;
    }

    public List func_55356_b(ICommandSender par1ICommandSender, String par2Str)
    {
        String as[] = par2Str.split(" ", -1);
        String s = as[0];

        if (as.length == 1)
        {
            ArrayList arraylist = new ArrayList();
            Iterator iterator = field_55361_a.entrySet().iterator();

            do
            {
                if (!iterator.hasNext())
                {
                    break;
                }

                java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();

                if (CommandBase.func_55231_a(s, (String)entry.getKey()) && ((ICommand)entry.getValue()).func_55222_c(par1ICommandSender))
                {
                    arraylist.add(entry.getKey());
                }
            }
            while (true);

            return arraylist;
        }

        if (as.length > 1)
        {
            ICommand icommand = (ICommand)field_55361_a.get(s);

            if (icommand != null)
            {
                return icommand.func_55226_b(par1ICommandSender, func_55358_a(as));
            }
        }

        return null;
    }

    public List func_55357_a(ICommandSender par1ICommandSender)
    {
        ArrayList arraylist = new ArrayList();
        Iterator iterator = field_55360_b.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            ICommand icommand = (ICommand)iterator.next();

            if (icommand.func_55222_c(par1ICommandSender))
            {
                arraylist.add(icommand);
            }
        }
        while (true);

        return arraylist;
    }

    public Map func_57260_a()
    {
        return field_55361_a;
    }
}
