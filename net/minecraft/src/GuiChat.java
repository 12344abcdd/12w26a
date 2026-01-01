package net.minecraft.src;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.*;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class GuiChat extends GuiScreen
{
    private String field_50062_b;
    private int field_50063_c;
    private boolean field_50060_d;
    private boolean field_55152_e;
    private int field_50067_h;
    private List field_50068_i;
    private URI field_50065_j;

    /** Chat entry field */
    protected GuiTextField inputField;
    private String field_50066_k;

    public GuiChat()
    {
        field_50062_b = "";
        field_50063_c = -1;
        field_50060_d = false;
        field_55152_e = false;
        field_50067_h = 0;
        field_50068_i = new ArrayList();
        field_50065_j = null;
        field_50066_k = "";
    }

    public GuiChat(String par1Str)
    {
        field_50062_b = "";
        field_50063_c = -1;
        field_50060_d = false;
        field_55152_e = false;
        field_50067_h = 0;
        field_50068_i = new ArrayList();
        field_50065_j = null;
        field_50066_k = "";
        field_50066_k = par1Str;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        field_50063_c = mc.ingameGUI.func_55092_b().func_55096_b().size();
        inputField = new GuiTextField(fontRenderer, 4, height - 12, width - 4, 12);
        inputField.setMaxStringLength(100);
        inputField.setEnableBackgroundDrawing(false);
        inputField.setFocused(true);
        inputField.setText(field_50066_k);
        inputField.func_50026_c(false);
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
        mc.ingameGUI.func_55092_b().func_55105_c();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        inputField.updateCursorCounter();
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        field_55152_e = false;

        if (par2 == 15)
        {
            completePlayerName();
        }
        else
        {
            field_50060_d = false;
        }

        if (par2 == 1)
        {
            mc.displayGuiScreen(null);
        }
        else if (par2 == 28)
        {
            String s = inputField.getText().trim();

            if (s.length() > 0)
            {
                mc.ingameGUI.func_55092_b().func_55107_b(s);

                if (!mc.lineIsCommand(s))
                {
                    mc.field_56455_h.sendChatMessage(s);
                }
            }

            mc.displayGuiScreen(null);
        }
        else if (par2 == 200)
        {
            func_50058_a(-1);
        }
        else if (par2 == 208)
        {
            func_50058_a(1);
        }
        else if (par2 == 201)
        {
            mc.ingameGUI.func_55092_b().func_55098_b(19);
        }
        else if (par2 == 209)
        {
            mc.ingameGUI.func_55092_b().func_55098_b(-19);
        }
        else
        {
            inputField.textboxKeyTyped(par1, par2);
        }
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput()
    {
        super.handleMouseInput();
        int i = Mouse.getEventDWheel();

        if (i != 0)
        {
            if (i > 1)
            {
                i = 1;
            }

            if (i < -1)
            {
                i = -1;
            }

            if (!func_50049_m())
            {
                i *= 7;
            }

            mc.ingameGUI.func_55092_b().func_55098_b(i);
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        if (par3 == 0 && mc.gameSettings.field_55386_p)
        {
            ChatClickData chatclickdata = mc.ingameGUI.func_55092_b().func_55097_a(Mouse.getX(), Mouse.getY());

            if (chatclickdata != null)
            {
                URI uri = chatclickdata.func_50089_b();

                if (uri != null)
                {
                    if (mc.gameSettings.field_55385_q)
                    {
                        field_50065_j = uri;
                        mc.displayGuiScreen(new GuiChatConfirmLink(this, this, chatclickdata.func_50088_a(), 0, chatclickdata));
                    }
                    else
                    {
                        func_55151_a(uri);
                    }

                    return;
                }
            }
        }

        inputField.mouseClicked(par1, par2, par3);
        super.mouseClicked(par1, par2, par3);
    }

    public void confirmClicked(boolean par1, int par2)
    {
        if (par2 == 0)
        {
            if (par1)
            {
                func_55151_a(field_50065_j);
            }

            field_50065_j = null;
            mc.displayGuiScreen(this);
        }
    }

    private void func_55151_a(URI par1URI)
    {
        try
        {
            Class class1 = Class.forName("java.awt.Desktop");
            Object obj = class1.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
            class1.getMethod("browse", new Class[]
                    {
                        java.net.URI.class
                    }).invoke(obj, new Object[]
                            {
                                par1URI
                            });
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
        }
    }

    /**
     * Autocompletes player name
     */
    public void completePlayerName()
    {
        if (field_50060_d)
        {
            inputField.func_50020_b(inputField.func_55091_a(-1, inputField.func_50035_h(), false) - inputField.func_50035_h());

            if (field_50067_h >= field_50068_i.size())
            {
                field_50067_h = 0;
            }
        }
        else
        {
            int i = inputField.func_55091_a(-1, inputField.func_50035_h(), false);
            field_50068_i.clear();
            field_50067_h = 0;
            String s = inputField.getText().substring(i).toLowerCase();
            String s1 = inputField.getText().substring(0, inputField.func_50035_h());
            func_55149_a(s1, s);

            if (field_50068_i.isEmpty())
            {
                return;
            }

            field_50060_d = true;
            inputField.func_50020_b(i - inputField.func_50035_h());
        }

        if (field_50068_i.size() > 1)
        {
            StringBuilder stringbuilder = new StringBuilder();
            String s2;

            for (Iterator iterator = field_50068_i.iterator(); iterator.hasNext(); stringbuilder.append(s2))
            {
                s2 = (String)iterator.next();

                if (stringbuilder.length() > 0)
                {
                    stringbuilder.append(", ");
                }
            }

            mc.ingameGUI.func_55092_b().func_55104_a(stringbuilder.toString(), 1);
        }

        inputField.func_50031_b((String)field_50068_i.get(field_50067_h++));
    }

    private void func_55149_a(String par1Str, String par2Str)
    {
        if (par1Str.length() < 1)
        {
            return;
        }
        else
        {
            mc.field_56455_h.sendQueue.addToSendQueue(new Packet203AutoComplete(par1Str));
            field_55152_e = true;
            return;
        }
    }

    public void func_50058_a(int par1)
    {
        int i = field_50063_c + par1;
        int j = mc.ingameGUI.func_55092_b().func_55096_b().size();

        if (i < 0)
        {
            i = 0;
        }

        if (i > j)
        {
            i = j;
        }

        if (i == field_50063_c)
        {
            return;
        }

        if (i == j)
        {
            field_50063_c = j;
            inputField.setText(field_50062_b);
            return;
        }

        if (field_50063_c == j)
        {
            field_50062_b = inputField.getText();
        }

        inputField.setText((String)mc.ingameGUI.func_55092_b().func_55096_b().get(i));
        field_50063_c = i;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        drawRect(2, height - 14, width - 2, height - 2, 0x80000000);
        inputField.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }

    public void func_55150_a(String par1ArrayOfStr[])
    {
        if (field_55152_e)
        {
            field_50068_i.clear();
            String as[] = par1ArrayOfStr;
            int i = as.length;

            for (int j = 0; j < i; j++)
            {
                String s = as[j];

                if (s.length() > 0)
                {
                    field_50068_i.add(s);
                }
            }

            if (field_50068_i.size() > 0)
            {
                field_50060_d = true;
                completePlayerName();
            }
        }
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
