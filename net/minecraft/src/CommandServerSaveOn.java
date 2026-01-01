package net.minecraft.src;

public class CommandServerSaveOn extends CommandBase
{
    public CommandServerSaveOn()
    {
    }

    public String func_55223_a()
    {
        return "save-on";
    }

    public void func_55225_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        MinecraftServer minecraftserver = MinecraftServer.func_56300_C();

        for (int i = 0; i < minecraftserver.field_56392_b.length; i++)
        {
            if (minecraftserver.field_56392_b[i] != null)
            {
                WorldServer worldserver = minecraftserver.field_56392_b[i];
                worldserver.field_56873_K = false;
            }
        }

        func_55227_a(par1ICommandSender, "commands.save.enabled", new Object[0]);
    }
}
