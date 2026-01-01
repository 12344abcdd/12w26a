package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

class GuiSlotServer extends GuiSlot
{
    /** Instance to the GUI this list is on. */
    final GuiMultiplayer parentGui;

    public GuiSlotServer(GuiMultiplayer par1GuiMultiplayer)
    {
        super(par1GuiMultiplayer.mc, par1GuiMultiplayer.width, par1GuiMultiplayer.height, 32, par1GuiMultiplayer.height - 64, 36);
        parentGui = par1GuiMultiplayer;
    }

    /**
     * Gets the size of the current slot list.
     */
    protected int getSize()
    {
        return GuiMultiplayer.func_56474_a(parentGui).func_57178_c() + GuiMultiplayer.func_58051_b(parentGui).size() + 1;
    }

    /**
     * the element in the slot that was clicked, boolean for wether it was double clicked or not
     */
    protected void elementClicked(int par1, boolean par2)
    {
        if (par1 >= GuiMultiplayer.func_56474_a(parentGui).func_57178_c() + GuiMultiplayer.func_58051_b(parentGui).size())
        {
            return;
        }

        int i = GuiMultiplayer.func_58045_c(parentGui);
        GuiMultiplayer.func_58044_a(parentGui, par1);
        boolean flag = GuiMultiplayer.func_58045_c(parentGui) >= 0 && GuiMultiplayer.func_58045_c(parentGui) < getSize();
        boolean flag1 = GuiMultiplayer.func_58045_c(parentGui) < GuiMultiplayer.func_56474_a(parentGui).func_57178_c();
        GuiMultiplayer.getButtonEdit(parentGui).enabled = flag;
        GuiMultiplayer.getButtonDelete(parentGui).enabled = flag1;
        GuiMultiplayer.func_58042_f(parentGui).enabled = flag1;

        if (par2 && flag)
        {
            GuiMultiplayer.func_58041_b(parentGui, par1);
        }
        else if (flag1 && GuiScreen.func_50049_m() && i >= 0 && i < GuiMultiplayer.func_56474_a(parentGui).func_57178_c())
        {
            GuiMultiplayer.func_56474_a(parentGui).func_57179_a(i, GuiMultiplayer.func_58045_c(parentGui));
        }
    }

    /**
     * returns true if the element passed in is currently selected
     */
    protected boolean isSelected(int par1)
    {
        return par1 == GuiMultiplayer.func_58045_c(parentGui);
    }

    /**
     * return the height of the content being scrolled
     */
    protected int getContentHeight()
    {
        return getSize() * 36;
    }

    protected void drawBackground()
    {
        parentGui.drawDefaultBackground();
    }

    protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
    {
        if (par1 < GuiMultiplayer.func_56474_a(parentGui).func_57178_c())
        {
            func_58097_d(par1, par2, par3, par4, par5Tessellator);
        }
        else if (par1 < GuiMultiplayer.func_56474_a(parentGui).func_57178_c() + GuiMultiplayer.func_58051_b(parentGui).size())
        {
            func_58098_b(par1, par2, par3, par4, par5Tessellator);
        }
        else
        {
            func_58096_c(par1, par2, par3, par4, par5Tessellator);
        }
    }

    private void func_58098_b(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
    {
        LanServer lanserver = (LanServer)GuiMultiplayer.func_58051_b(parentGui).get(par1 - GuiMultiplayer.func_56474_a(parentGui).func_57178_c());
        parentGui.drawString(parentGui.fontRenderer, StatCollector.translateToLocal("lanServer.title"), par2 + 2, par3 + 1, 0xffffff);
        parentGui.drawString(parentGui.fontRenderer, lanserver.func_58160_a(), par2 + 2, par3 + 12, 0x808080);
        parentGui.drawString(parentGui.fontRenderer, lanserver.func_58161_b(), par2 + 2, par3 + 12 + 11, 0x303030);
    }

    private void func_58096_c(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
    {
        parentGui.drawCenteredString(parentGui.fontRenderer, StatCollector.translateToLocal("lanServer.scanning"), parentGui.width / 2, par3 + 1, 0xffffff);
        String s;

        switch ((GuiMultiplayer.func_58047_g(parentGui) / 3) % 4)
        {
            case 0:
            default:
                s = "O o o";
                break;

            case 1:
            case 3:
                s = "o O o";
                break;

            case 2:
                s = "o o O";
                break;
        }

        parentGui.drawCenteredString(parentGui.fontRenderer, s, parentGui.width / 2, par3 + 12, 0x808080);
    }

    private void func_58097_d(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
    {
        ServerData serverdata = GuiMultiplayer.func_56474_a(parentGui).func_57180_a(par1);

        synchronized (GuiMultiplayer.func_58048_h())
        {
            if (GuiMultiplayer.func_58046_n() < 5 && !serverdata.field_57443_f)
            {
                serverdata.field_57443_f = true;
                serverdata.field_57446_e = -2L;
                serverdata.field_57445_d = "";
                serverdata.field_57448_c = "";
                GuiMultiplayer.func_58052_o();
                (new ThreadPollServers(this, serverdata)).start();
            }
        }

        parentGui.drawString(parentGui.fontRenderer, serverdata.field_57449_a, par2 + 2, par3 + 1, 0xffffff);
        parentGui.drawString(parentGui.fontRenderer, serverdata.field_57445_d, par2 + 2, par3 + 12, 0x808080);
        parentGui.drawString(parentGui.fontRenderer, serverdata.field_57448_c, (par2 + 215) - parentGui.fontRenderer.getStringWidth(serverdata.field_57448_c), par3 + 12, 0x808080);
        parentGui.drawString(parentGui.fontRenderer, serverdata.field_57447_b, par2 + 2, par3 + 12 + 11, 0x303030);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        parentGui.mc.renderEngine.bindTexture(parentGui.mc.renderEngine.getTexture("/gui/icons.png"));
        String s = "";
        int i;
        int j;

        if (serverdata.field_57443_f && serverdata.field_57446_e != -2L)
        {
            i = 0;
            j = 0;

            if (serverdata.field_57446_e < 0L)
            {
                j = 5;
            }
            else if (serverdata.field_57446_e < 150L)
            {
                j = 0;
            }
            else if (serverdata.field_57446_e < 300L)
            {
                j = 1;
            }
            else if (serverdata.field_57446_e < 600L)
            {
                j = 2;
            }
            else if (serverdata.field_57446_e < 1000L)
            {
                j = 3;
            }
            else
            {
                j = 4;
            }

            if (serverdata.field_57446_e < 0L)
            {
                s = "(no connection)";
            }
            else
            {
                s = (new StringBuilder()).append(serverdata.field_57446_e).append("ms").toString();
            }
        }
        else
        {
            i = 1;
            j = (int)(System.currentTimeMillis() / 100L + (long)(par1 * 2) & 7L);

            if (j > 4)
            {
                j = 8 - j;
            }

            s = "Polling..";
        }

        parentGui.drawTexturedModalRect(par2 + 205, par3, 0 + i * 10, 176 + j * 8, 10, 8);
        byte byte0 = 4;

        if (mouseX >= (par2 + 205) - byte0 && mouseY >= par3 - byte0 && mouseX <= par2 + 205 + 10 + byte0 && mouseY <= par3 + 8 + byte0)
        {
            GuiMultiplayer.func_58043_a(parentGui, s);
        }
    }
}
