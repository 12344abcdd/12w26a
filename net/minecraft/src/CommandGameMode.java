package net.minecraft.src;

import java.util.List;

public class CommandGameMode extends CommandBase
{
    public CommandGameMode()
    {
    }

    public String func_55223_a()
    {
        return "gamemode";
    }

    public String func_55224_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55085_a("commands.gamemode.usage", new Object[0]);
    }

    public void func_55225_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length > 0)
        {
            EnumGameType enumgametype = func_56593_b(par1ICommandSender, par2ArrayOfStr[0]);
            EntityPlayer entityplayer = par2ArrayOfStr.length < 2 ? func_55233_d(par1ICommandSender) : func_55243_a(par2ArrayOfStr[1]);
            entityplayer.func_56240_a(enumgametype);
            String s = StatCollector.translateToLocal((new StringBuilder()).append("gameMode.").append(enumgametype.func_57539_b()).toString());

            if (entityplayer != par1ICommandSender)
            {
                func_55227_a(par1ICommandSender, "commands.gamemode.success.other", new Object[]
                        {
                            entityplayer.func_55080_n(), s
                        });
            }
            else
            {
                func_55227_a(par1ICommandSender, "commands.gamemode.success.self", new Object[]
                        {
                            s
                        });
            }

            return;
        }
        else
        {
            throw new WrongUsageException("commands.gamemode.usage", new Object[0]);
        }
    }

    protected EnumGameType func_56593_b(ICommandSender par1ICommandSender, String par2Str)
    {
        if (par2Str.equalsIgnoreCase(EnumGameType.SURVIVAL.func_57539_b()) || par2Str.equalsIgnoreCase("s"))
        {
            return EnumGameType.SURVIVAL;
        }

        if (par2Str.equalsIgnoreCase(EnumGameType.CREATIVE.func_57539_b()) || par2Str.equalsIgnoreCase("c"))
        {
            return EnumGameType.CREATIVE;
        }

        if (par2Str.equalsIgnoreCase(EnumGameType.ADVENTURE.func_57539_b()) || par2Str.equalsIgnoreCase("a"))
        {
            return EnumGameType.ADVENTURE;
        }
        else
        {
            return WorldSettings.func_57457_a(func_55228_a(par1ICommandSender, par2Str));
        }
    }

    public List func_55226_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1)
        {
            return func_55232_a(par2ArrayOfStr, new String[]
                    {
                        "survival", "creative", "adventure"
                    });
        }

        if (par2ArrayOfStr.length == 2)
        {
            return func_55232_a(par2ArrayOfStr, func_55242_c());
        }
        else
        {
            return null;
        }
    }

    protected EntityPlayer func_55243_a(String par1Str)
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

    protected String[] func_55242_c()
    {
        return MinecraftServer.func_56300_C().func_56332_x();
    }
}
