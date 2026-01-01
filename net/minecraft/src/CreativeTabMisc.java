package net.minecraft.src;

final class CreativeTabMisc extends CreativeTabs
{
    CreativeTabMisc(int par1, String par2Str)
    {
        super(par1, par2Str);
    }

    public int func_57042_a()
    {
        return Item.bucketLava.shiftedIndex;
    }
}
