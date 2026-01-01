package net.minecraft.src;

public class ScaledResolution
{
    private int scaledWidth;
    private int scaledHeight;
    private double scaledWidthD;
    private double scaledHeightD;
    private int scaleFactor;

    public ScaledResolution(GameSettings par1GameSettings, int par2, int par3)
    {
        scaledWidth = par2;
        scaledHeight = par3;
        scaleFactor = 1;
        int i = par1GameSettings.guiScale;

        if (i == 0)
        {
            i = 1000;
        }

        for (; scaleFactor < i && scaledWidth / (scaleFactor + 1) >= 320 && scaledHeight / (scaleFactor + 1) >= 240; scaleFactor++) { }

        scaledWidthD = (double)scaledWidth / (double)scaleFactor;
        scaledHeightD = (double)scaledHeight / (double)scaleFactor;
        scaledWidth = (int)Math.ceil(scaledWidthD);
        scaledHeight = (int)Math.ceil(scaledHeightD);
    }

    public int getScaledWidth()
    {
        return scaledWidth;
    }

    public int getScaledHeight()
    {
        return scaledHeight;
    }

    public double func_57188_c()
    {
        return scaledWidthD;
    }

    public double func_57186_d()
    {
        return scaledHeightD;
    }

    public int func_57187_e()
    {
        return scaleFactor;
    }
}
