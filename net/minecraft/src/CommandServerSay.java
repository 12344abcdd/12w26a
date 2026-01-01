package net.minecraft.src;

import java.util.List;

public class CommandServerSay extends CommandBase
{
    public CommandServerSay()
    {
    }

    public String func_55223_a()
    {
        return "say";
    }

    public String func_55224_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55085_a("commands.say.usage", new Object[0]);
    }

    public void func_55225_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length > 0 && par2ArrayOfStr[0].length() > 1)
        {
            String s = func_56588_a(par2ArrayOfStr, 0);
            MinecraftServer.func_56300_C().func_56339_Z().func_57114_a(new Packet3Chat(String.format("[%s] %s", new Object[]
                    {
                        par1ICommandSender.func_55088_aJ(), s
                    })));
            return;
        }
        else
        {
            throw new WrongUsageException("commands.say.usage", new Object[0]);
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
