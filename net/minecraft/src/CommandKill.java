package net.minecraft.src;

public class CommandKill extends CommandBase
{
    public CommandKill()
    {
    }

    public String func_55223_a()
    {
        return "kill";
    }

    public void func_55225_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        EntityPlayer entityplayer = func_55233_d(par1ICommandSender);
        par1ICommandSender.func_55086_a("Ouch. That look like it hurt.");
    }
}
