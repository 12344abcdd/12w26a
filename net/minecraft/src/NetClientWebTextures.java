package net.minecraft.src;

import net.minecraft.client.Minecraft;

class NetClientWebTextures extends GuiScreen
{
    final String field_56479_a;
    final NetClientHandler field_56478_b;

    NetClientWebTextures(NetClientHandler par1NetClientHandler, String par2Str)
    {
        field_56478_b = par1NetClientHandler;
        field_56479_a = par2Str;
    }

    public void confirmClicked(boolean par1, int par2)
    {
        mc = Minecraft.func_55068_z();

        if (mc.func_56446_z() != null)
        {
            mc.func_56446_z().func_57438_a(par1);
            ServerList.func_57172_b(mc.func_56446_z());
        }

        if (par1)
        {
            mc.texturePackList.func_57499_a(field_56479_a);
        }

        mc.displayGuiScreen(null);
    }
}
