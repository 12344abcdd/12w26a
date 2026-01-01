package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class ServerCommandManager extends CommandHandler implements IAdminCommand
{
    public ServerCommandManager()
    {
        func_55359_a(new CommandTime());
        func_55359_a(new CommandGameMode());
        func_55359_a(new CommandDefaultGameMode());
        func_55359_a(new CommandKill());
        func_55359_a(new CommandToggleDownfall());
        func_55359_a(new CommandXP());
        func_55359_a(new CommandServerTp());
        func_55359_a(new CommandGive());
        func_55359_a(new CommandServerEmote());
        func_55359_a(new CommandShowSeed());
        func_55359_a(new CommandHelp());

        if (MinecraftServer.func_56300_C().func_56371_Q())
        {
            func_55359_a(new CommandServerOp());
            func_55359_a(new CommandServerDeop());
            func_55359_a(new CommandServerStop());
            func_55359_a(new CommandServerSaveAll());
            func_55359_a(new CommandServerSaveOff());
            func_55359_a(new CommandServerSaveOn());
            func_55359_a(new CommandServerBanIp());
            func_55359_a(new CommandServerPardonIp());
            func_55359_a(new CommandServerBan());
            func_55359_a(new CommandServerBanlist());
            func_55359_a(new CommandServerPardon());
            func_55359_a(new CommandServerKick());
            func_55359_a(new CommandServerList());
            func_55359_a(new CommandServerSay());
            func_55359_a(new CommandServerWhitelist());
        }
        else
        {
            func_55359_a(new CommandServerPublishLocal());
        }

        CommandBase.func_55230_a(this);
    }

    public void func_55354_a(ICommandSender par1ICommandSender, String par2Str, Object par3ArrayOfObj[])
    {
        Iterator iterator = MinecraftServer.func_56300_C().func_56339_Z().field_57133_b.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();

            if (entityplayermp != par1ICommandSender && MinecraftServer.func_56300_C().func_56339_Z().func_57087_e(((EntityPlayer)(entityplayermp)).username))
            {
                entityplayermp.func_55086_a((new StringBuilder()).append("\2477\247o[").append(par1ICommandSender.func_55088_aJ()).append(": ").append(entityplayermp.func_55085_a(par2Str, par3ArrayOfObj)).append("]").toString());
            }
        }
        while (true);

        if (par1ICommandSender != MinecraftServer.func_56300_C())
        {
            MinecraftServer.field_56394_a.info((new StringBuilder()).append("[").append(par1ICommandSender.func_55088_aJ()).append(": ").append(MinecraftServer.func_56300_C().func_55085_a(par2Str, par3ArrayOfObj)).append("]").toString());
        }

        par1ICommandSender.func_55086_a(par1ICommandSender.func_55085_a(par2Str, par3ArrayOfObj));
    }
}
