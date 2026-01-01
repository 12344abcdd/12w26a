package net.minecraft.src;

import java.util.List;

public class CommandServerTp extends CommandBase
{
    public CommandServerTp()
    {
    }

    public String func_55223_a()
    {
        return "tp";
    }

    public String func_55224_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55085_a("commands.tp.usage", new Object[0]);
    }

    public void func_55225_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length >= 1)
        {
            MinecraftServer minecraftserver = MinecraftServer.func_56300_C();
            EntityPlayerMP entityplayermp;

            if (par2ArrayOfStr.length == 2 || par2ArrayOfStr.length == 4)
            {
                entityplayermp = minecraftserver.func_56339_Z().func_57094_f(par2ArrayOfStr[0]);

                if (entityplayermp == null)
                {
                    throw new PlayerNotFoundException();
                }
            }
            else
            {
                entityplayermp = (EntityPlayerMP)func_55233_d(par1ICommandSender);
            }

            if (par2ArrayOfStr.length == 3 || par2ArrayOfStr.length == 4)
            {
                if (entityplayermp.worldObj != null)
                {
                    int i = par2ArrayOfStr.length - 3;
                    int j = 0x1c9c380;
                    int k = func_55234_a(par1ICommandSender, par2ArrayOfStr[i++], -j, j);
                    int l = func_55234_a(par1ICommandSender, par2ArrayOfStr[i++], 0, 256);
                    int i1 = func_55234_a(par1ICommandSender, par2ArrayOfStr[i++], -j, j);
                    entityplayermp.setPositionAndUpdate(k, l, i1);
                    func_55227_a(par1ICommandSender, "commands.tp.coordinates", new Object[]
                            {
                                entityplayermp.func_55080_n(), Integer.valueOf(k), Integer.valueOf(l), Integer.valueOf(i1)
                            });
                }
            }
            else if (par2ArrayOfStr.length == 1 || par2ArrayOfStr.length == 2)
            {
                EntityPlayerMP entityplayermp1 = minecraftserver.func_56339_Z().func_57094_f(par2ArrayOfStr[par2ArrayOfStr.length - 1]);

                if (entityplayermp1 == null)
                {
                    throw new PlayerNotFoundException();
                }

                entityplayermp.field_56268_a.func_56719_a(entityplayermp1.posX, entityplayermp1.posY, entityplayermp1.posZ, entityplayermp1.rotationYaw, entityplayermp1.rotationPitch);
                func_55227_a(par1ICommandSender, "commands.tp.success", new Object[]
                        {
                            entityplayermp.func_55080_n(), entityplayermp1.func_55080_n()
                        });
            }

            return;
        }
        else
        {
            throw new WrongUsageException("commands.tp.usage", new Object[0]);
        }
    }

    public List func_55226_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1 || par2ArrayOfStr.length == 2)
        {
            return func_55232_a(par2ArrayOfStr, MinecraftServer.func_56300_C().func_56332_x());
        }
        else
        {
            return null;
        }
    }
}
