package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class GuiProgress extends GuiScreen implements IProgressUpdate
{
    private String field_56515_a;
    private String field_56513_b;
    private int field_56514_c;
    private boolean field_56512_d;

    public GuiProgress()
    {
        field_56515_a = "";
        field_56513_b = "";
        field_56514_c = 0;
    }

    /**
     * Shows the 'Saving level' string.
     */
    public void displaySavingString(String par1Str)
    {
        printText(par1Str);
    }

    public void printText(String par1Str)
    {
        field_56515_a = par1Str;
        displayLoadingString("Working...");
    }

    /**
     * Displays a string on the loading screen supposed to indicate what is being done currently.
     */
    public void displayLoadingString(String par1Str)
    {
        field_56513_b = par1Str;
        setLoadingProgress(0);
    }

    /**
     * Updates the progress bar on the loading screen to the specified amount. Args: loadProgress
     */
    public void setLoadingProgress(int par1)
    {
        field_56514_c = par1;
    }

    public void func_56509_a()
    {
        field_56512_d = true;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        if (field_56512_d)
        {
            mc.displayGuiScreen(null);
            return;
        }
        else
        {
            drawDefaultBackground();
            drawCenteredString(fontRenderer, field_56515_a, width / 2, 70, 0xffffff);
            drawCenteredString(fontRenderer, (new StringBuilder()).append(field_56513_b).append(" ").append(field_56514_c).append("%").toString(), width / 2, 90, 0xffffff);
            super.drawScreen(par1, par2, par3);
            return;
        }
    }
}
