package net.minecraft.src;

class SlotCreativeInventory extends Slot
{
    private final Slot field_57196_f;
    final GuiContainerCreative field_57197_a;

    public SlotCreativeInventory(GuiContainerCreative par1GuiContainerCreative, Slot par2Slot, int par3)
    {
        super(par2Slot.inventory, par3, 0, 0);
        field_57197_a = par1GuiContainerCreative;
        field_57196_f = par2Slot;
    }

    /**
     * Called when the player picks up an item from an inventory slot
     */
    public void onPickupFromSlot(ItemStack par1ItemStack)
    {
        field_57196_f.onPickupFromSlot(par1ItemStack);
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        return field_57196_f.isItemValid(par1ItemStack);
    }

    /**
     * Helper fnct to get the stack in the slot.
     */
    public ItemStack getStack()
    {
        return field_57196_f.getStack();
    }

    /**
     * Returns if this slot contains a stack.
     */
    public boolean getHasStack()
    {
        return field_57196_f.getHasStack();
    }

    /**
     * Helper method to put a stack in the slot.
     */
    public void putStack(ItemStack par1ItemStack)
    {
        field_57196_f.putStack(par1ItemStack);
    }

    /**
     * Called when the stack in a Slot changes
     */
    public void onSlotChanged()
    {
        field_57196_f.onSlotChanged();
    }

    /**
     * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the case
     * of armor slots)
     */
    public int getSlotStackLimit()
    {
        return field_57196_f.getSlotStackLimit();
    }

    /**
     * Returns the icon index on items.png that is used as background image of the slot.
     */
    public int getBackgroundIconIndex()
    {
        return field_57196_f.getBackgroundIconIndex();
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    public ItemStack decrStackSize(int par1)
    {
        return field_57196_f.decrStackSize(par1);
    }

    public boolean func_57189_a(IInventory par1IInventory, int par2)
    {
        return field_57196_f.func_57189_a(par1IInventory, par2);
    }

    static Slot func_57195_a(SlotCreativeInventory par0SlotCreativeInventory)
    {
        return par0SlotCreativeInventory.field_57196_f;
    }
}
