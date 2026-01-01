package net.minecraft.src;

import java.util.logging.Logger;

class ConvertProgressUpdater implements IProgressUpdate
{
    private long field_56510_b;
    final MinecraftServer field_56511_a;

    ConvertProgressUpdater(MinecraftServer par1MinecraftServer)
    {
        field_56511_a = par1MinecraftServer;
        field_56510_b = System.currentTimeMillis();
    }

    /**
     * Shows the 'Saving level' string.
     */
    public void displaySavingString(String s)
    {
    }

    public void printText(String s)
    {
    }

    /**
     * Updates the progress bar on the loading screen to the specified amount. Args: loadProgress
     */
    public void setLoadingProgress(int par1)
    {
        if (System.currentTimeMillis() - field_56510_b >= 1000L)
        {
            field_56510_b = System.currentTimeMillis();
            MinecraftServer.field_56394_a.info((new StringBuilder()).append("Converting... ").append(par1).append("%").toString());
        }
    }

    public void func_56509_a()
    {
    }

    /**
     * Displays a string on the loading screen supposed to indicate what is being done currently.
     */
    public void displayLoadingString(String s)
    {
    }
}
