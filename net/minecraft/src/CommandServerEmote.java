package net.minecraft.src;

import java.util.List;

public class CommandServerEmote extends CommandBase
{
    public CommandServerEmote()
    {
    }

    public String func_55223_a()
    {
        return "me";
    }

    public String func_55224_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55085_a("commands.me.usage", new Object[0]);
    }

    public void func_55225_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length > 0)
        {
            String s = func_56588_a(par2ArrayOfStr, 0);
            MinecraftServer.func_56300_C().func_56339_Z().func_57114_a(new Packet3Chat((new StringBuilder()).append("* ").append(par1ICommandSender.func_55088_aJ()).append(" ").append(s).toString()));
            return;
        }
        else
        {
            throw new WrongUsageException("commands.me.usage", new Object[0]);
        }
    }

    public List func_55226_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        return func_55232_a(par2ArrayOfStr, MinecraftServer.func_56300_C().func_56332_x());
    }
}
