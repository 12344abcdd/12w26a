package net.minecraft.src;

import java.util.List;

public class CommandGive extends CommandBase
{
    public CommandGive()
    {
    }

    public String func_55223_a()
    {
        return "give";
    }

    public String func_55224_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55085_a("commands.give.usage", new Object[0]);
    }

    public void func_55225_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length >= 2)
        {
            EntityPlayer entityplayer = func_55241_a(par2ArrayOfStr[0]);
            int i = func_55229_a(par1ICommandSender, par2ArrayOfStr[1], 1);
            int j = 1;
            int k = 0;

            if (Item.itemsList[i] == null)
            {
                throw new NumberInvalidException("commands.give.notFound", new Object[]
                        {
                            Integer.valueOf(i)
                        });
            }

            if (par2ArrayOfStr.length >= 3)
            {
                j = func_55234_a(par1ICommandSender, par2ArrayOfStr[2], 1, 64);
            }

            if (par2ArrayOfStr.length >= 4)
            {
                k = func_55228_a(par1ICommandSender, par2ArrayOfStr[3]);
            }

            ItemStack itemstack = new ItemStack(i, j, k);
            entityplayer.dropPlayerItem(itemstack);
            func_55227_a(par1ICommandSender, "commands.give.success", new Object[]
                    {
                        Item.itemsList[i].func_56817_j(itemstack), Integer.valueOf(i), Integer.valueOf(j), entityplayer.func_55080_n()
                    });
            return;
        }
        else
        {
            throw new WrongUsageException("commands.give.usage", new Object[0]);
        }
    }

    public List func_55226_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1)
        {
            return func_55232_a(par2ArrayOfStr, func_55240_c());
        }
        else
        {
            return null;
        }
    }

    protected EntityPlayer func_55241_a(String par1Str)
    {
        EntityPlayerMP entityplayermp = MinecraftServer.func_56300_C().func_56339_Z().func_57094_f(par1Str);

        if (entityplayermp == null)
        {
            throw new PlayerNotFoundException();
        }
        else
        {
            return entityplayermp;
        }
    }

    protected String[] func_55240_c()
    {
        return MinecraftServer.func_56300_C().func_56332_x();
    }
}
