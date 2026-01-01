package net.minecraft.src;

import java.util.*;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiContainerCreative extends GuiContainer
{
    private static InventoryBasic inventory = new InventoryBasic("tmp", 45);
    private static int field_56499_i;

    /** Amount scrolled in Creative mode inventory (0 = top, 1 = bottom) */
    private float currentScroll;

    /** True if the scrollbar is being dragged */
    private boolean isScrolling;

    /**
     * True if the left mouse button was held down last time drawScreen was called.
     */
    private boolean wasClicking;
    private GuiTextField field_56498_m;
    private List field_56496_n;
    private Slot field_56497_o;

    public GuiContainerCreative(EntityPlayer par1EntityPlayer)
    {
        super(new ContainerCreative(par1EntityPlayer));
        currentScroll = 0.0F;
        isScrolling = false;
        field_56497_o = null;
        par1EntityPlayer.craftingInventory = inventorySlots;
        allowUserInput = true;
        par1EntityPlayer.addStat(AchievementList.openInventory, 1);
        ySize = 136;
        xSize = 195;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        if (!mc.field_56453_c.isInCreativeMode())
        {
            mc.displayGuiScreen(new GuiInventory(mc.field_56455_h));
        }
    }

    protected void handleMouseClick(Slot par1Slot, int par2, int par3, boolean par4)
    {
        if (par1Slot != null)
        {
            if (par1Slot == field_56497_o && par4)
            {
                for (int i = 0; i < mc.field_56455_h.inventorySlots.func_56980_a().size(); i++)
                {
                    mc.field_56453_c.sendSlotPacket(null, i);
                }
            }
            else if (field_56499_i == CreativeTabs.field_57056_m.func_57038_b())
            {
                if (par1Slot == field_56497_o)
                {
                    mc.field_56455_h.inventory.setItemStack(null);
                }
                else
                {
                    int j = SlotCreativeInventory.func_57195_a((SlotCreativeInventory)par1Slot).slotNumber;

                    if (par4)
                    {
                        mc.field_56453_c.sendSlotPacket(null, j);
                    }
                    else
                    {
                        mc.field_56455_h.inventorySlots.slotClick(j, par3, par4, mc.field_56455_h);
                        ItemStack itemstack1 = mc.field_56455_h.inventorySlots.getSlot(j).getStack();
                        mc.field_56453_c.sendSlotPacket(itemstack1, j);
                    }
                }
            }
            else if (par1Slot.inventory == inventory)
            {
                InventoryPlayer inventoryplayer = mc.field_56455_h.inventory;
                ItemStack itemstack2 = inventoryplayer.getItemStack();
                ItemStack itemstack5 = par1Slot.getStack();

                if (itemstack2 != null && itemstack5 != null && itemstack2.isItemEqual(itemstack5))
                {
                    if (par3 == 0)
                    {
                        if (par4)
                        {
                            itemstack2.stackSize = itemstack2.getMaxStackSize();
                        }
                        else if (itemstack2.stackSize < itemstack2.getMaxStackSize())
                        {
                            itemstack2.stackSize++;
                        }
                    }
                    else if (itemstack2.stackSize <= 1)
                    {
                        inventoryplayer.setItemStack(null);
                    }
                    else
                    {
                        itemstack2.stackSize--;
                    }
                }
                else if (itemstack5 == null || itemstack2 != null)
                {
                    inventoryplayer.setItemStack(null);
                }
                else
                {
                    boolean flag = false;

                    if (!flag)
                    {
                        inventoryplayer.setItemStack(ItemStack.copyItemStack(itemstack5));
                        ItemStack itemstack3 = inventoryplayer.getItemStack();

                        if (par4)
                        {
                            itemstack3.stackSize = itemstack3.getMaxStackSize();
                        }
                    }
                }
            }
            else
            {
                inventorySlots.slotClick(par1Slot.slotNumber, par3, par4, mc.field_56455_h);
                ItemStack itemstack = inventorySlots.getSlot(par1Slot.slotNumber).getStack();
                mc.field_56453_c.sendSlotPacket(itemstack, (par1Slot.slotNumber - inventorySlots.inventorySlots.size()) + 9 + 36);
            }
        }
        else
        {
            InventoryPlayer inventoryplayer1 = mc.field_56455_h.inventory;

            if (inventoryplayer1.getItemStack() != null)
            {
                if (par3 == 0)
                {
                    mc.field_56455_h.dropPlayerItem(inventoryplayer1.getItemStack());
                    mc.field_56453_c.func_35639_a(inventoryplayer1.getItemStack());
                    inventoryplayer1.setItemStack(null);
                }

                if (par3 == 1)
                {
                    ItemStack itemstack4 = inventoryplayer1.getItemStack().splitStack(1);
                    mc.field_56455_h.dropPlayerItem(itemstack4);
                    mc.field_56453_c.func_35639_a(itemstack4);

                    if (inventoryplayer1.getItemStack().stackSize == 0)
                    {
                        inventoryplayer1.setItemStack(null);
                    }
                }
            }
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        if (mc.field_56453_c.isInCreativeMode())
        {
            super.initGui();
            controlList.clear();
            Keyboard.enableRepeatEvents(true);
            field_56498_m = new GuiTextField(fontRenderer, guiLeft + 82, guiTop + 6, 89, fontRenderer.FONT_HEIGHT);
            field_56498_m.setMaxStringLength(15);
            field_56498_m.setEnableBackgroundDrawing(false);
            field_56498_m.func_56466_d(false);
            field_56498_m.func_56465_g(0xffffff);
            int i = field_56499_i;
            field_56499_i = -1;
            func_56495_b(CreativeTabs.field_57052_a[i]);
        }
        else
        {
            mc.displayGuiScreen(new GuiInventory(mc.field_56455_h));
        }
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        if (field_56499_i != CreativeTabs.field_57047_g.func_57038_b() && Keyboard.isKeyDown(mc.gameSettings.keyBindChat.keyCode))
        {
            func_56495_b(CreativeTabs.field_57047_g);
            return;
        }

        if (field_56498_m.textboxKeyTyped(par1, par2))
        {
            func_56491_m();
        }
        else
        {
            super.keyTyped(par1, par2);
        }
    }

    private void func_56491_m()
    {
        ContainerCreative containercreative = (ContainerCreative)inventorySlots;
        containercreative.itemList.clear();
        Item aitem[] = Item.itemsList;
        int i = aitem.length;

        for (int j = 0; j < i; j++)
        {
            Item item = aitem[j];

            if (item != null && item.func_56811_f() != null)
            {
                item.func_56814_a(item.shiftedIndex, null, containercreative.itemList);
            }
        }

        Iterator iterator = containercreative.itemList.iterator();
        String s = field_56498_m.getText().toLowerCase();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            ItemStack itemstack = (ItemStack)iterator.next();
            boolean flag = false;
            Iterator iterator1 = itemstack.getItemNameandInformation().iterator();

            do
            {
                if (!iterator1.hasNext())
                {
                    break;
                }

                String s1 = (String)iterator1.next();

                if (!s1.toLowerCase().contains(s))
                {
                    continue;
                }

                flag = true;
                break;
            }
            while (true);

            if (!flag)
            {
                iterator.remove();
            }
        }
        while (true);

        currentScroll = 0.0F;
        containercreative.scrollTo(0.0F);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everythin in front of the items)
     */
    protected void drawGuiContainerForegroundLayer()
    {
        CreativeTabs creativetabs = CreativeTabs.field_57052_a[field_56499_i];

        if (creativetabs.func_57037_g())
        {
            fontRenderer.drawString(creativetabs.func_57032_d(), 8, 6, 0x404040);
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        if (par3 == 0)
        {
            int i = par1 - guiLeft;
            int j = par2 - guiTop;
            CreativeTabs acreativetabs[] = CreativeTabs.field_57052_a;
            int k = acreativetabs.length;

            for (int l = 0; l < k; l++)
            {
                CreativeTabs creativetabs = acreativetabs[l];

                if (func_56493_a(creativetabs, i, j))
                {
                    func_56495_b(creativetabs);
                    return;
                }
            }
        }

        super.mouseClicked(par1, par2, par3);
    }

    private boolean func_56490_n()
    {
        return field_56499_i != CreativeTabs.field_57056_m.func_57038_b() && CreativeTabs.field_57052_a[field_56499_i].func_57035_i() && ((ContainerCreative)inventorySlots).func_56988_c();
    }

    private void func_56495_b(CreativeTabs par1CreativeTabs)
    {
        int i = field_56499_i;
        field_56499_i = par1CreativeTabs.func_57038_b();
        ContainerCreative containercreative = (ContainerCreative)inventorySlots;
        containercreative.itemList.clear();
        par1CreativeTabs.func_57036_a(containercreative.itemList);

        if (par1CreativeTabs == CreativeTabs.field_57056_m)
        {
            Container container = mc.field_56455_h.inventorySlots;

            if (field_56496_n == null)
            {
                field_56496_n = containercreative.inventorySlots;
            }

            containercreative.inventorySlots = new ArrayList();

            for (int j = 0; j < container.inventorySlots.size(); j++)
            {
                SlotCreativeInventory slotcreativeinventory = new SlotCreativeInventory(this, (Slot)container.inventorySlots.get(j), j);
                containercreative.inventorySlots.add(slotcreativeinventory);

                if (j >= 1 && j < 5)
                {
                    int k = j - 1;
                    int j1 = k % 2;
                    int i2 = k / 2;
                    slotcreativeinventory.xDisplayPosition = 117 + j1 * 18;
                    slotcreativeinventory.yDisplayPosition = 10 + i2 * 18;
                    continue;
                }

                if (j == 0)
                {
                    slotcreativeinventory.xDisplayPosition = 173;
                    slotcreativeinventory.yDisplayPosition = 20;
                    continue;
                }

                if (j >= 5 && j < 9)
                {
                    int l = j - 5;
                    int k1 = l / 2;
                    int j2 = l % 2;
                    slotcreativeinventory.xDisplayPosition = 9 + k1 * 54;
                    slotcreativeinventory.yDisplayPosition = 6 + j2 * 27;
                    continue;
                }

                if (j >= container.inventorySlots.size())
                {
                    continue;
                }

                int i1 = j - 9;
                int l1 = i1 % 9;
                int k2 = i1 / 9;
                slotcreativeinventory.xDisplayPosition = 9 + l1 * 18;

                if (j >= 36)
                {
                    slotcreativeinventory.yDisplayPosition = 112;
                }
                else
                {
                    slotcreativeinventory.yDisplayPosition = 54 + k2 * 18;
                }
            }

            field_56497_o = new Slot(inventory, 0, 173, 112);
            containercreative.inventorySlots.add(field_56497_o);
        }
        else if (i == CreativeTabs.field_57056_m.func_57038_b())
        {
            containercreative.inventorySlots = field_56496_n;
            field_56496_n = null;
        }

        if (field_56498_m != null)
        {
            if (par1CreativeTabs == CreativeTabs.field_57047_g)
            {
                field_56498_m.func_56466_d(true);
                field_56498_m.func_50026_c(false);
                field_56498_m.setFocused(true);
                field_56498_m.setText("");
                func_56491_m();
            }
            else
            {
                field_56498_m.func_56466_d(false);
                field_56498_m.func_50026_c(true);
                field_56498_m.setFocused(false);
            }
        }

        currentScroll = 0.0F;
        containercreative.scrollTo(0.0F);
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput()
    {
        super.handleMouseInput();
        int i = Mouse.getEventDWheel();

        if (i != 0 && func_56490_n())
        {
            int j = (((ContainerCreative)inventorySlots).itemList.size() / 9 - 5) + 1;

            if (i > 0)
            {
                i = 1;
            }

            if (i < 0)
            {
                i = -1;
            }

            currentScroll -= (double)i / (double)j;

            if (currentScroll < 0.0F)
            {
                currentScroll = 0.0F;
            }

            if (currentScroll > 1.0F)
            {
                currentScroll = 1.0F;
            }

            ((ContainerCreative)inventorySlots).scrollTo(currentScroll);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        boolean flag = Mouse.isButtonDown(0);
        int i = guiLeft;
        int j = guiTop;
        int k = i + 175;
        int l = j + 18;
        int i1 = k + 14;
        int j1 = l + 112;

        if (!wasClicking && flag && par1 >= k && par2 >= l && par1 < i1 && par2 < j1)
        {
            isScrolling = func_56490_n();
        }

        if (!flag)
        {
            isScrolling = false;
        }

        wasClicking = flag;

        if (isScrolling)
        {
            currentScroll = ((float)(par2 - l) - 7.5F) / ((float)(j1 - l) - 15F);

            if (currentScroll < 0.0F)
            {
                currentScroll = 0.0F;
            }

            if (currentScroll > 1.0F)
            {
                currentScroll = 1.0F;
            }

            ((ContainerCreative)inventorySlots).scrollTo(currentScroll);
        }

        super.drawScreen(par1, par2, par3);
        CreativeTabs acreativetabs[] = CreativeTabs.field_57052_a;
        int k1 = acreativetabs.length;
        int l1 = 0;

        do
        {
            if (l1 >= k1)
            {
                break;
            }

            CreativeTabs creativetabs = acreativetabs[l1];

            if (func_56494_b(creativetabs, par1, par2))
            {
                break;
            }

            l1++;
        }
        while (true);

        if (field_56497_o != null && field_56499_i == CreativeTabs.field_57056_m.func_57038_b() && func_56484_a(field_56497_o.xDisplayPosition, field_56497_o.yDisplayPosition, 16, 16, par1, par2))
        {
            func_56483_a(StringTranslate.getInstance().translateKey("inventory.binSlot"), par1, par2);
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_LIGHTING);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderHelper.enableGUIStandardItemLighting();
        int i = mc.renderEngine.getTexture("/gui/allitems.png");
        CreativeTabs creativetabs = CreativeTabs.field_57052_a[field_56499_i];
        int j = mc.renderEngine.getTexture((new StringBuilder()).append("/gui/creative_inv/").append(creativetabs.func_57034_f()).toString());
        CreativeTabs acreativetabs[] = CreativeTabs.field_57052_a;
        int l = acreativetabs.length;

        for (int i1 = 0; i1 < l; i1++)
        {
            CreativeTabs creativetabs1 = acreativetabs[i1];
            mc.renderEngine.bindTexture(i);

            if (creativetabs1.func_57038_b() != field_56499_i)
            {
                func_56492_a(creativetabs1);
            }
        }

        mc.renderEngine.bindTexture(j);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        field_56498_m.drawTextBox();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int k = guiLeft + 175;
        l = guiTop + 18;
        int j1 = l + 112;
        mc.renderEngine.bindTexture(i);

        if (creativetabs.func_57035_i())
        {
            drawTexturedModalRect(k, l + (int)((float)(j1 - l - 17) * currentScroll), 232 + (func_56490_n() ? 0 : 12), 0, 12, 15);
        }

        func_56492_a(creativetabs);

        if (creativetabs == CreativeTabs.field_57056_m)
        {
            GuiInventory.func_56500_a(mc, guiLeft + 43, guiTop + 45, 20, (guiLeft + 43) - par2, (guiTop + 45) - 30 - par3);
        }
    }

    protected boolean func_56493_a(CreativeTabs par1CreativeTabs, int par2, int par3)
    {
        int i = par1CreativeTabs.func_57041_k();
        int j = 28 * i;
        int k = 0;

        if (i == 5)
        {
            j = (xSize - 28) + 2;
        }
        else if (i > 0)
        {
            j += i;
        }

        if (par1CreativeTabs.func_57044_l())
        {
            k -= 32;
        }
        else
        {
            k += ySize;
        }

        return par2 >= j && par2 <= j + 28 && par3 >= k && par3 <= k + 32;
    }

    protected boolean func_56494_b(CreativeTabs par1CreativeTabs, int par2, int par3)
    {
        int i = par1CreativeTabs.func_57041_k();
        int j = 28 * i;
        int k = 0;

        if (i == 5)
        {
            j = (xSize - 28) + 2;
        }
        else if (i > 0)
        {
            j += i;
        }

        if (par1CreativeTabs.func_57044_l())
        {
            k -= 32;
        }
        else
        {
            k += ySize;
        }

        if (func_56484_a(j + 3, k + 3, 23, 27, par2, par3))
        {
            func_56483_a(par1CreativeTabs.func_57032_d(), par2, par3);
            return true;
        }
        else
        {
            return false;
        }
    }

    protected void func_56492_a(CreativeTabs par1CreativeTabs)
    {
        boolean flag = par1CreativeTabs.func_57038_b() == field_56499_i;
        boolean flag1 = par1CreativeTabs.func_57044_l();
        int i = par1CreativeTabs.func_57041_k();
        int j = i * 28;
        int k = 0;
        int l = guiLeft + 28 * i;
        int i1 = guiTop;
        byte byte0 = 32;

        if (flag)
        {
            k += 32;
        }

        if (i == 5)
        {
            l = (guiLeft + xSize) - 28;
        }
        else if (i > 0)
        {
            l += i;
        }

        if (flag1)
        {
            i1 -= 28;
        }
        else
        {
            k += 64;
            i1 += ySize - 4;
        }

        GL11.glDisable(GL11.GL_LIGHTING);
        drawTexturedModalRect(l, i1, j, k, 28, byte0);
        zLevel = 100F;
        itemRenderer.zLevel = 100F;
        l += 6;
        i1 += 8 + (flag1 ? 1 : -1);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        ItemStack itemstack = new ItemStack(par1CreativeTabs.func_57039_e());
        itemRenderer.renderItemIntoGUI(fontRenderer, mc.renderEngine, itemstack, l, i1);
        itemRenderer.renderItemOverlayIntoGUI(fontRenderer, mc.renderEngine, itemstack, l, i1);
        GL11.glDisable(GL11.GL_LIGHTING);
        itemRenderer.zLevel = 0.0F;
        zLevel = 0.0F;
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

    public int func_58065_g()
    {
        return field_56499_i;
    }

    /**
     * Returns the creative inventory
     */
    static InventoryBasic getInventory()
    {
        return inventory;
    }

    static
    {
        field_56499_i = CreativeTabs.field_57050_b.func_57038_b();
    }
}
