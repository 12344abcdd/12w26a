package net.minecraft.src;

public class CommandServerSaveOff extends CommandBase
{
    public CommandServerSaveOff()
    {
    }

    public String func_55223_a()
    {
        return "save-off";
    }

    public void func_55225_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        MinecraftServer minecraftserver = MinecraftServer.func_56300_C();

        for (int i = 0; i < minecraftserver.field_56392_b.length; i++)
        {
            if (minecraftserver.field_56392_b[i] != null)
            {
                WorldServer worldserver = minecraftserver.field_56392_b[i];
                worldserver.field_56873_K = true;
            }
        }

        func_55227_a(par1ICommandSender, "commands.save.disabled", new Object[0]);
    }
}
