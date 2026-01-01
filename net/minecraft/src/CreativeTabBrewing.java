package net.minecraft.src;

final class CreativeTabBrewing extends CreativeTabs
{
    CreativeTabBrewing(int par1, String par2Str)
    {
        super(par1, par2Str);
    }

    public int func_57042_a()
    {
        return Item.potion.shiftedIndex;
    }
}
