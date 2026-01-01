package net.minecraft.src;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

class GuiButtonNextPage extends GuiButton
{
    private final boolean field_55095_j;

    public GuiButtonNextPage(int par1, int par2, int par3, boolean par4)
    {
        super(par1, par2, par3, 23, 13, "");
        field_55095_j = par4;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        if (!drawButton)
        {
            return;
        }

        boolean flag = par2 >= xPosition && par3 >= yPosition && par2 < xPosition + field_52008_a && par3 < yPosition + field_52007_b;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        par1Minecraft.renderEngine.bindTexture(par1Minecraft.renderEngine.getTexture("/gui/book.png"));
        int i = 0;
        int j = 192;

        if (flag)
        {
            i += 23;
        }

        if (!field_55095_j)
        {
            j += 13;
        }

        drawTexturedModalRect(xPosition, yPosition, i, j, 23, 13);
    }
}
