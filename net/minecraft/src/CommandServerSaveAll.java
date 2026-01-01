package net.minecraft.src;

public class CommandServerSaveAll extends CommandBase
{
    public CommandServerSaveAll()
    {
    }

    public String func_55223_a()
    {
        return "save-all";
    }

    public void func_55225_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        MinecraftServer minecraftserver = MinecraftServer.func_56300_C();
        par1ICommandSender.func_55086_a(par1ICommandSender.func_55085_a("commands.save.start", new Object[0]));

        if (minecraftserver.func_56339_Z() != null)
        {
            minecraftserver.func_56339_Z().func_57117_g();
        }

        try
        {
            for (int i = 0; i < minecraftserver.field_56392_b.length; i++)
            {
                if (minecraftserver.field_56392_b[i] != null)
                {
                    WorldServer worldserver = minecraftserver.field_56392_b[i];
                    boolean flag = worldserver.field_56873_K;
                    worldserver.field_56873_K = false;
                    worldserver.func_56849_a(true, null);
                    worldserver.field_56873_K = flag;
                }
            }
        }
        catch (MinecraftException minecraftexception)
        {
            func_55227_a(par1ICommandSender, "commands.save.failed", new Object[]
                    {
                        minecraftexception.getMessage()
                    });
            return;
        }

        func_55227_a(par1ICommandSender, "commands.save.success", new Object[0]);
    }
}
