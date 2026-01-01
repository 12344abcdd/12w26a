package net.minecraft.src;

import java.util.*;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiInventory extends GuiContainer
{
    /**
     * x size of the inventory window in pixels. Defined as float, passed as int
     */
    private float xSize_lo;

    /**
     * y size of the inventory window in pixels. Defined as float, passed as int.
     */
    private float ySize_lo;

    public GuiInventory(EntityPlayer par1EntityPlayer)
    {
        super(par1EntityPlayer.inventorySlots);
        allowUserInput = true;
        par1EntityPlayer.addStat(AchievementList.openInventory, 1);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        if (mc.field_56453_c.isInCreativeMode())
        {
            mc.displayGuiScreen(new GuiContainerCreative(mc.field_56455_h));
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        controlList.clear();

        if (mc.field_56453_c.isInCreativeMode())
        {
            mc.displayGuiScreen(new GuiContainerCreative(mc.field_56455_h));
        }
        else
        {
            super.initGui();

            if (!mc.field_56455_h.getActivePotionEffects().isEmpty())
            {
                guiLeft = 160 + (width - xSize - 200) / 2;
            }
        }
    }

    /**
     * Draw the foreground layer for the GuiContainer (everythin in front of the items)
     */
    protected void drawGuiContainerForegroundLayer()
    {
        fontRenderer.drawString(StatCollector.translateToLocal("container.crafting"), 86, 16, 0x404040);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        super.drawScreen(par1, par2, par3);
        xSize_lo = par1;
        ySize_lo = par2;
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        int i = mc.renderEngine.getTexture("/gui/inventory.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = guiLeft;
        int k = guiTop;
        drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
        displayDebuffEffects();
        func_56500_a(mc, j + 51, k + 75, 30, (float)(j + 51) - xSize_lo, (float)((k + 75) - 50) - ySize_lo);
    }

    public static void func_56500_a(Minecraft par0Minecraft, int par1, int par2, int par3, float par4, float par5)
    {
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glPushMatrix();
        GL11.glTranslatef(par1, par2, 50F);
        GL11.glScalef(-par3, par3, par3);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        float f = par0Minecraft.field_56455_h.renderYawOffset;
        float f1 = par0Minecraft.field_56455_h.rotationYaw;
        float f2 = par0Minecraft.field_56455_h.rotationPitch;
        GL11.glRotatef(135F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-(float)Math.atan(par5 / 40F) * 20F, 1.0F, 0.0F, 0.0F);
        par0Minecraft.field_56455_h.renderYawOffset = (float)Math.atan(par4 / 40F) * 20F;
        par0Minecraft.field_56455_h.rotationYaw = (float)Math.atan(par4 / 40F) * 40F;
        par0Minecraft.field_56455_h.rotationPitch = -(float)Math.atan(par5 / 40F) * 20F;
        par0Minecraft.field_56455_h.rotationYawHead = par0Minecraft.field_56455_h.rotationYaw;
        GL11.glTranslatef(0.0F, par0Minecraft.field_56455_h.yOffset, 0.0F);
        RenderManager.instance.playerViewY = 180F;
        RenderManager.instance.renderEntityWithPosYaw(par0Minecraft.field_56455_h, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        par0Minecraft.field_56455_h.renderYawOffset = f;
        par0Minecraft.field_56455_h.rotationYaw = f1;
        par0Minecraft.field_56455_h.rotationPitch = f2;
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.id == 0)
        {
            mc.displayGuiScreen(new GuiAchievements(mc.statFileWriter));
        }

        if (par1GuiButton.id == 1)
        {
            mc.displayGuiScreen(new GuiStats(this, mc.statFileWriter));
        }
    }

    /**
     * Displays debuff/potion effects that are currently being applied to the player
     */
    private void displayDebuffEffects()
    {
        int i = guiLeft - 124;
        int j = guiTop;
        int k = mc.renderEngine.getTexture("/gui/inventory.png");
        Collection collection = mc.field_56455_h.getActivePotionEffects();

        if (collection.isEmpty())
        {
            return;
        }

        int l = 33;

        if (collection.size() > 5)
        {
            l = 132 / (collection.size() - 1);
        }

        for (Iterator iterator = mc.field_56455_h.getActivePotionEffects().iterator(); iterator.hasNext();)
        {
            PotionEffect potioneffect = (PotionEffect)iterator.next();
            Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            mc.renderEngine.bindTexture(k);
            drawTexturedModalRect(i, j, 0, ySize, 140, 32);

            if (potion.hasStatusIcon())
            {
                int i1 = potion.getStatusIconIndex();
                drawTexturedModalRect(i + 6, j + 7, 0 + (i1 % 8) * 18, ySize + 32 + (i1 / 8) * 18, 18, 18);
            }

            String s = StatCollector.translateToLocal(potion.getName());

            if (potioneffect.getAmplifier() == 1)
            {
                s = (new StringBuilder()).append(s).append(" II").toString();
            }
            else if (potioneffect.getAmplifier() == 2)
            {
                s = (new StringBuilder()).append(s).append(" III").toString();
            }
            else if (potioneffect.getAmplifier() == 3)
            {
                s = (new StringBuilder()).append(s).append(" IV").toString();
            }

            fontRenderer.drawStringWithShadow(s, i + 10 + 18, j + 6, 0xffffff);
            String s1 = Potion.getDurationString(potioneffect);
            fontRenderer.drawStringWithShadow(s1, i + 10 + 18, j + 6 + 10, 0x7f7f7f);
            j += l;
        }
    }
}
