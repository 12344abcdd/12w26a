package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class CommandServerOp extends CommandBase
{
    public CommandServerOp()
    {
    }

    public String func_55223_a()
    {
        return "op";
    }

    public String func_55224_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55085_a("commands.op.usage", new Object[0]);
    }

    public void func_55225_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1 && par2ArrayOfStr[0].length() > 0)
        {
            MinecraftServer.func_56300_C().func_56339_Z().func_57115_b(par2ArrayOfStr[0]);
            func_55227_a(par1ICommandSender, "commands.op.success", new Object[]
                    {
                        par2ArrayOfStr[0]
                    });
            return;
        }
        else
        {
            throw new WrongUsageException("commands.op.usage", new Object[0]);
        }
    }

    public List func_55226_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1)
        {
            String s = par2ArrayOfStr[par2ArrayOfStr.length - 1];
            ArrayList arraylist = new ArrayList();
            String as[] = MinecraftServer.func_56300_C().func_56332_x();
            int i = as.length;

            for (int j = 0; j < i; j++)
            {
                String s1 = as[j];

                if (!MinecraftServer.func_56300_C().func_56339_Z().func_57087_e(s1) && func_55231_a(s, s1))
                {
                    arraylist.add(s1);
                }
            }

            return arraylist;
        }
        else
        {
            return null;
        }
    }
}
