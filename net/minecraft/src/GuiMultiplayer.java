package net.minecraft.src;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class GuiMultiplayer extends GuiScreen
{
    /** Number of outstanding ThreadPollServers threads */
    private static int threadsPending = 0;

    /** Lock object for use with synchronized() */
    private static Object lock = new Object();

    /**
     * A reference to the screen object that created this. Used for navigating between screens.
     */
    private GuiScreen parentScreen;

    /** Slot container for the server list */
    private GuiSlotServer serverSlotContainer;
    private ServerList field_56476_e;

    /** Index of the currently selected server */
    private int selectedServer;

    /** The 'Edit' button */
    private GuiButton buttonEdit;

    /** The 'Join Server' button */
    private GuiButton buttonSelect;

    /** The 'Delete' button */
    private GuiButton buttonDelete;

    /** The 'Delete' button was clicked */
    private boolean deleteClicked;

    /** The 'Add server' button was clicked */
    private boolean addClicked;

    /** The 'Edit' button was clicked */
    private boolean editClicked;

    /** The 'Direct Connect' button was clicked */
    private boolean directClicked;

    /** This GUI's lag tooltip text or null if no lag icon is being hovered. */
    private String lagTooltip;
    private ServerData field_56477_w;
    private LanServerList field_58057_x;
    private ThreadLanServerFind field_58056_y;
    private int field_58055_z;
    private boolean field_58053_A;
    private List field_58054_B;

    public GuiMultiplayer(GuiScreen par1GuiScreen)
    {
        selectedServer = -1;
        deleteClicked = false;
        addClicked = false;
        editClicked = false;
        directClicked = false;
        lagTooltip = null;
        field_56477_w = null;
        field_58054_B = Collections.emptyList();
        parentScreen = par1GuiScreen;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        controlList.clear();

        if (!field_58053_A)
        {
            field_58053_A = true;
            field_56476_e = new ServerList(mc);
            field_56476_e.func_57174_a();
            field_58057_x = new LanServerList();

            try
            {
                field_58056_y = new ThreadLanServerFind(field_58057_x);
                field_58056_y.start();
            }
            catch (Exception exception)
            {
                System.out.println((new StringBuilder()).append("Unable to start LAN server detection: ").append(exception.getMessage()).toString());
            }

            serverSlotContainer = new GuiSlotServer(this);
        }
        else
        {
            serverSlotContainer.func_58095_a(width, height, 32, height - 64);
        }

        initGuiControls();
    }

    /**
     * Populate the GuiScreen controlList
     */
    public void initGuiControls()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        controlList.add(buttonEdit = new GuiButton(7, width / 2 - 154, height - 28, 70, 20, stringtranslate.translateKey("selectServer.edit")));
        controlList.add(buttonDelete = new GuiButton(2, width / 2 - 74, height - 28, 70, 20, stringtranslate.translateKey("selectServer.delete")));
        controlList.add(buttonSelect = new GuiButton(1, width / 2 - 154, height - 52, 100, 20, stringtranslate.translateKey("selectServer.select")));
        controlList.add(new GuiButton(4, width / 2 - 50, height - 52, 100, 20, stringtranslate.translateKey("selectServer.direct")));
        controlList.add(new GuiButton(3, width / 2 + 4 + 50, height - 52, 100, 20, stringtranslate.translateKey("selectServer.add")));
        controlList.add(new GuiButton(8, width / 2 + 4, height - 28, 70, 20, stringtranslate.translateKey("selectServer.refresh")));
        controlList.add(new GuiButton(0, width / 2 + 4 + 76, height - 28, 75, 20, stringtranslate.translateKey("gui.cancel")));
        boolean flag = selectedServer >= 0 && selectedServer < serverSlotContainer.getSize();
        buttonSelect.enabled = flag;
        buttonEdit.enabled = flag;
        buttonDelete.enabled = flag;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        field_58055_z++;

        if (field_58057_x.func_58147_a())
        {
            field_58054_B = field_58057_x.func_58148_c();
            field_58057_x.func_58146_b();
        }
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);

        if (field_58056_y != null)
        {
            field_58056_y.interrupt();
            field_58056_y = null;
        }
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (!par1GuiButton.enabled)
        {
            return;
        }

        if (par1GuiButton.id == 2)
        {
            String s = field_56476_e.func_57180_a(selectedServer).field_57449_a;

            if (s != null)
            {
                deleteClicked = true;
                StringTranslate stringtranslate = StringTranslate.getInstance();
                String s1 = stringtranslate.translateKey("selectServer.deleteQuestion");
                String s2 = (new StringBuilder()).append("'").append(s).append("' ").append(stringtranslate.translateKey("selectServer.deleteWarning")).toString();
                String s3 = stringtranslate.translateKey("selectServer.deleteButton");
                String s4 = stringtranslate.translateKey("gui.cancel");
                GuiYesNo guiyesno = new GuiYesNo(this, s1, s2, s3, s4, selectedServer);
                mc.displayGuiScreen(guiyesno);
            }
        }
        else if (par1GuiButton.id == 1)
        {
            joinServer(selectedServer);
        }
        else if (par1GuiButton.id == 4)
        {
            directClicked = true;
            mc.displayGuiScreen(new GuiScreenServerList(this, field_56477_w = new ServerData(StatCollector.translateToLocal("selectServer.defaultName"), "")));
        }
        else if (par1GuiButton.id == 3)
        {
            addClicked = true;
            mc.displayGuiScreen(new GuiScreenAddServer(this, field_56477_w = new ServerData(StatCollector.translateToLocal("selectServer.defaultName"), "")));
        }
        else if (par1GuiButton.id == 7)
        {
            editClicked = true;
            ServerData serverdata = field_56476_e.func_57180_a(selectedServer);
            mc.displayGuiScreen(new GuiScreenAddServer(this, field_56477_w = new ServerData(serverdata.field_57449_a, serverdata.field_57447_b)));
        }
        else if (par1GuiButton.id == 0)
        {
            mc.displayGuiScreen(parentScreen);
        }
        else if (par1GuiButton.id == 8)
        {
            mc.displayGuiScreen(new GuiMultiplayer(parentScreen));
        }
        else
        {
            serverSlotContainer.actionPerformed(par1GuiButton);
        }
    }

    public void confirmClicked(boolean par1, int par2)
    {
        if (deleteClicked)
        {
            deleteClicked = false;

            if (par1)
            {
                field_56476_e.func_57173_b(par2);
                field_56476_e.func_57176_b();
                selectedServer = -1;
            }

            mc.displayGuiScreen(this);
        }
        else if (directClicked)
        {
            directClicked = false;

            if (par1)
            {
                func_56475_a(field_56477_w);
            }
            else
            {
                mc.displayGuiScreen(this);
            }
        }
        else if (addClicked)
        {
            addClicked = false;

            if (par1)
            {
                field_56476_e.func_57175_a(field_56477_w);
                field_56476_e.func_57176_b();
                selectedServer = -1;
            }

            mc.displayGuiScreen(this);
        }
        else if (editClicked)
        {
            editClicked = false;

            if (par1)
            {
                ServerData serverdata = field_56476_e.func_57180_a(selectedServer);
                serverdata.field_57449_a = field_56477_w.field_57449_a;
                serverdata.field_57447_b = field_56477_w.field_57447_b;
                field_56476_e.func_57176_b();
            }

            mc.displayGuiScreen(this);
        }
    }

    private int parseIntWithDefault(String par1Str, int par2)
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
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        int i = selectedServer;

        if (func_50049_m() && par2 == 200)
        {
            if (i > 0)
            {
                field_56476_e.func_57179_a(i, i - 1);
                selectedServer--;

                if (i < field_56476_e.func_57178_c() - 1)
                {
                    serverSlotContainer.func_56922_d(-serverSlotContainer.slotHeight);
                }
            }
        }
        else if (func_50049_m() && par2 == 208)
        {
            if (i < field_56476_e.func_57178_c() - 1)
            {
                field_56476_e.func_57179_a(i, i + 1);
                selectedServer++;

                if (i > 0)
                {
                    serverSlotContainer.func_56922_d(serverSlotContainer.slotHeight);
                }
            }
        }
        else if (par1 == '\r')
        {
            actionPerformed((GuiButton)controlList.get(2));
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        lagTooltip = null;
        StringTranslate stringtranslate = StringTranslate.getInstance();
        drawDefaultBackground();
        serverSlotContainer.drawScreen(par1, par2, par3);
        drawCenteredString(fontRenderer, stringtranslate.translateKey("multiplayer.title"), width / 2, 20, 0xffffff);
        super.drawScreen(par1, par2, par3);

        if (lagTooltip != null)
        {
            func_35325_a(lagTooltip, par1, par2);
        }
    }

    /**
     * Join server by slot index
     */
    private void joinServer(int par1)
    {
        if (par1 < field_56476_e.func_57178_c())
        {
            func_56475_a(field_56476_e.func_57180_a(par1));
            return;
        }

        par1 -= field_56476_e.func_57178_c();

        if (par1 < field_58054_B.size())
        {
            LanServer lanserver = (LanServer)field_58054_B.get(par1);
            func_56475_a(new ServerData(lanserver.func_58160_a(), lanserver.func_58161_b()));
        }
    }

    private void func_56475_a(ServerData par1ServerData)
    {
        mc.displayGuiScreen(new GuiConnecting(mc, par1ServerData));
    }

    private void func_56473_b(ServerData par1ServerData) throws IOException
    {
        String s = par1ServerData.field_57447_b;
        String as[] = s.split(":");

        if (s.startsWith("["))
        {
            int i = s.indexOf("]");

            if (i > 0)
            {
                String s2 = s.substring(1, i);
                String s3 = s.substring(i + 1).trim();

                if (s3.startsWith(":") && s3.length() > 0)
                {
                    s3 = s3.substring(1);
                    as = new String[2];
                    as[0] = s2;
                    as[1] = s3;
                }
                else
                {
                    as = new String[1];
                    as[0] = s2;
                }
            }
        }

        if (as.length > 2)
        {
            as = new String[1];
            as[0] = s;
        }

        String s1 = as[0];
        int j = as.length <= 1 ? 25565 : parseIntWithDefault(as[1], 25565);
        Socket socket = null;
        DataInputStream datainputstream = null;
        DataOutputStream dataoutputstream = null;

        try
        {
            socket = new Socket();
            socket.setSoTimeout(3000);
            socket.setTcpNoDelay(true);
            socket.setTrafficClass(18);
            socket.connect(new InetSocketAddress(s1, j), 3000);
            datainputstream = new DataInputStream(socket.getInputStream());
            dataoutputstream = new DataOutputStream(socket.getOutputStream());
            dataoutputstream.write(254);

            if (datainputstream.read() != 255)
            {
                throw new IOException("Bad message");
            }

            String s4 = Packet.readString(datainputstream, 256);
            char ac[] = s4.toCharArray();

            for (int k = 0; k < ac.length; k++)
            {
                if (ac[k] != '\247' && ChatAllowedCharacters.allowedCharacters.indexOf(ac[k]) < 0)
                {
                    ac[k] = '?';
                }
            }

            s4 = new String(ac);
            String as1[] = s4.split("\247");
            s4 = as1[0];
            int l = -1;
            int i1 = -1;

            try
            {
                l = Integer.parseInt(as1[1]);
                i1 = Integer.parseInt(as1[2]);
            }
            catch (Exception exception) { }

            par1ServerData.field_57445_d = (new StringBuilder()).append("\2477").append(s4).toString();

            if (l >= 0 && i1 > 0)
            {
                par1ServerData.field_57448_c = (new StringBuilder()).append("\2477").append(l).append("\2478/\2477").append(i1).toString();
            }
            else
            {
                par1ServerData.field_57448_c = "\2478???";
            }
        }
        finally
        {
            try
            {
                if (datainputstream != null)
                {
                    datainputstream.close();
                }
            }
            catch (Throwable throwable) { }

            try
            {
                if (dataoutputstream != null)
                {
                    dataoutputstream.close();
                }
            }
            catch (Throwable throwable1) { }

            try
            {
                if (socket != null)
                {
                    socket.close();
                }
            }
            catch (Throwable throwable2) { }
        }
    }

    protected void func_35325_a(String par1Str, int par2, int par3)
    {
        if (par1Str == null)
        {
            return;
        }
        else
        {
            int i = par2 + 12;
            int j = par3 - 12;
            int k = fontRenderer.getStringWidth(par1Str);
            drawGradientRect(i - 3, j - 3, i + k + 3, j + 8 + 3, 0xc0000000, 0xc0000000);
            fontRenderer.drawStringWithShadow(par1Str, i, j, -1);
            return;
        }
    }

    static ServerList func_56474_a(GuiMultiplayer par0GuiMultiplayer)
    {
        return par0GuiMultiplayer.field_56476_e;
    }

    static List func_58051_b(GuiMultiplayer par0GuiMultiplayer)
    {
        return par0GuiMultiplayer.field_58054_B;
    }

    static int func_58045_c(GuiMultiplayer par0GuiMultiplayer)
    {
        return par0GuiMultiplayer.selectedServer;
    }

    static int func_58044_a(GuiMultiplayer par0GuiMultiplayer, int par1)
    {
        return par0GuiMultiplayer.selectedServer = par1;
    }

    /**
     * Return buttonEdit GuiButton
     */
    static GuiButton getButtonEdit(GuiMultiplayer par0GuiMultiplayer)
    {
        return par0GuiMultiplayer.buttonSelect;
    }

    /**
     * Return buttonDelete GuiButton
     */
    static GuiButton getButtonDelete(GuiMultiplayer par0GuiMultiplayer)
    {
        return par0GuiMultiplayer.buttonEdit;
    }

    static GuiButton func_58042_f(GuiMultiplayer par0GuiMultiplayer)
    {
        return par0GuiMultiplayer.buttonDelete;
    }

    static void func_58041_b(GuiMultiplayer par0GuiMultiplayer, int par1)
    {
        par0GuiMultiplayer.joinServer(par1);
    }

    static int func_58047_g(GuiMultiplayer par0GuiMultiplayer)
    {
        return par0GuiMultiplayer.field_58055_z;
    }

    static Object func_58048_h()
    {
        return lock;
    }

    static int func_58046_n()
    {
        return threadsPending;
    }

    static int func_58052_o()
    {
        return threadsPending++;
    }

    static void func_58049_a(GuiMultiplayer par0GuiMultiplayer, ServerData par1ServerData) throws IOException
    {
        par0GuiMultiplayer.func_56473_b(par1ServerData);
    }

    static int func_58050_p()
    {
        return threadsPending--;
    }

    static String func_58043_a(GuiMultiplayer par0GuiMultiplayer, String par1Str)
    {
        return par0GuiMultiplayer.lagTooltip = par1Str;
    }
}
