package net.minecraft.src;

public class CommandShowSeed extends CommandBase
{
    public CommandShowSeed()
    {
    }

    public String func_55223_a()
    {
        return "seed";
    }

    public void func_55225_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        EntityPlayer entityplayer = func_55233_d(par1ICommandSender);
        par1ICommandSender.func_55086_a((new StringBuilder()).append("Seed: ").append(entityplayer.worldObj.getSeed()).toString());
    }
}
