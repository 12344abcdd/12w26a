package net.minecraft.src;

import java.io.PrintStream;
import java.util.List;
import net.minecraft.client.Minecraft;

public class GuiConnecting extends GuiScreen
{
    /** A reference to the NetClientHandler. */
    private NetClientHandler clientHandler;

    /** True if the connection attempt has been cancelled. */
    private boolean cancelled;

    public GuiConnecting(Minecraft par1Minecraft, ServerData par2ServerData)
    {
        cancelled = false;
        mc = par1Minecraft;
        String s = par2ServerData.field_57447_b;
        String as[] = s.split(":");

        if (s.startsWith("["))
        {
            int i = s.indexOf("]");

            if (i > 0)
            {
                String s1 = s.substring(1, i);
                String s2 = s.substring(i + 1).trim();

                if (s2.startsWith(":") && s2.length() > 0)
                {
                    s2 = s2.substring(1);
                    as = new String[2];
                    as[0] = s1;
                    as[1] = s2;
                }
                else
                {
                    as = new String[1];
                    as[0] = s1;
                }
            }
        }

        if (as.length > 2)
        {
            as = new String[1];
            as[0] = s;
        }

        par1Minecraft.func_56445_a(null);
        par1Minecraft.func_56449_a(par2ServerData);
        func_56505_a(as[0], as.length <= 1 ? 25565 : func_56507_b(as[1], 25565));
    }

    public GuiConnecting(Minecraft par1Minecraft, String par2Str, int par3)
    {
        cancelled = false;
        mc = par1Minecraft;
        par1Minecraft.func_56445_a(null);
        func_56505_a(par2Str, par3);
    }

    private void func_56505_a(String par1Str, int par2)
    {
        System.out.println((new StringBuilder()).append("Connecting to ").append(par1Str).append(", ").append(par2).toString());
        (new ThreadConnectToServer(this, par1Str, par2)).start();
    }

    private int func_56507_b(String par1Str, int par2)
    {
        try
        {
            return Integer.parseInt(par1Str.trim());
        }
        catch (Exception exception)
        {
            return par2;
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        if (clientHandler != null)
        {
            clientHandler.processReadPackets();
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char c, int i)
    {
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        controlList.clear();
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 120 + 12, stringtranslate.translateKey("gui.cancel")));
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.id == 0)
        {
            cancelled = true;

            if (clientHandler != null)
            {
                clientHandler.disconnect();
            }

            mc.displayGuiScreen(new GuiMainMenu());
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        drawDefaultBackground();
        StringTranslate stringtranslate = StringTranslate.getInstance();

        if (clientHandler == null)
        {
            drawCenteredString(fontRenderer, stringtranslate.translateKey("connect.connecting"), width / 2, height / 2 - 50, 0xffffff);
            drawCenteredString(fontRenderer, "", width / 2, height / 2 - 10, 0xffffff);
        }
        else
        {
            drawCenteredString(fontRenderer, stringtranslate.translateKey("connect.authorizing"), width / 2, height / 2 - 50, 0xffffff);
            drawCenteredString(fontRenderer, clientHandler.field_1209_a, width / 2, height / 2 - 10, 0xffffff);
        }

        super.drawScreen(par1, par2, par3);
    }

    /**
     * Sets the NetClientHandler.
     */
    static NetClientHandler setNetClientHandler(GuiConnecting par0GuiConnecting, NetClientHandler par1NetClientHandler)
    {
        return par0GuiConnecting.clientHandler = par1NetClientHandler;
    }

    static Minecraft func_56502_a(GuiConnecting par0GuiConnecting)
    {
        return par0GuiConnecting.mc;
    }

    static boolean func_56504_b(GuiConnecting par0GuiConnecting)
    {
        return par0GuiConnecting.cancelled;
    }

    static Minecraft func_56508_c(GuiConnecting par0GuiConnecting)
    {
        return par0GuiConnecting.mc;
    }

    /**
     * Gets the NetClientHandler.
     */
    static NetClientHandler getNetClientHandler(GuiConnecting par0GuiConnecting)
    {
        return par0GuiConnecting.clientHandler;
    }

    static Minecraft func_56506_e(GuiConnecting par0GuiConnecting)
    {
        return par0GuiConnecting.mc;
    }

    static Minecraft func_56503_f(GuiConnecting par0GuiConnecting)
    {
        return par0GuiConnecting.mc;
    }

    static Minecraft func_56501_g(GuiConnecting par0GuiConnecting)
    {
        return par0GuiConnecting.mc;
    }
}
