package net.minecraft.src;

import java.util.List;

public class ItemCoal extends Item
{
    public ItemCoal(int par1)
    {
        super(par1);
        setHasSubtypes(true);
        setMaxDamage(0);
        func_56813_a(CreativeTabs.field_57055_l);
    }

    public String getItemNameIS(ItemStack par1ItemStack)
    {
        if (par1ItemStack.getItemDamage() == 1)
        {
            return "item.charcoal";
        }
        else
        {
            return "item.coal";
        }
    }

    public void func_56814_a(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
    }
}
