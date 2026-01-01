package net.minecraft.src;

import java.util.List;

public class CreativeTabs
{
    public static final CreativeTabs field_57052_a[] = new CreativeTabs[12];
    public static final CreativeTabs field_57050_b = new CreativeTabBlock(0, "buildingBlocks");
    public static final CreativeTabs field_57051_c = new CreativeTabDeco(1, "decorations");
    public static final CreativeTabs field_57048_d = new CreativeTabRedstone(2, "redstone");
    public static final CreativeTabs field_57049_e = new CreativeTabTransport(3, "transportation");
    public static final CreativeTabs field_57046_f = new CreativeTabMisc(4, "misc");
    public static final CreativeTabs field_57047_g = (new CreativeTabSearch(5, "search")).func_57033_a("search.png");
    public static final CreativeTabs field_57059_h = new CreativeTabFood(6, "food");
    public static final CreativeTabs field_57060_i = new CreativeTabTools(7, "tools");
    public static final CreativeTabs field_57057_j = new CreativeTabCombat(8, "combat");
    public static final CreativeTabs field_57058_k = new CreativeTabBrewing(9, "brewing");
    public static final CreativeTabs field_57055_l = new CreativeTabMaterial(10, "materials");
    public static final CreativeTabs field_57056_m = (new CreativeTabInventory(11, "inventory")).func_57033_a("survival_inv.png").func_57040_j().func_57043_h();
    private final int field_57053_n;
    private final String field_57054_o;
    private String field_57063_p;
    private boolean field_57062_q;
    private boolean field_57061_r;

    public CreativeTabs(int par1, String par2Str)
    {
        field_57063_p = "list_items.png";
        field_57062_q = true;
        field_57061_r = true;
        field_57053_n = par1;
        field_57054_o = par2Str;
        field_57052_a[par1] = this;
    }

    public int func_57038_b()
    {
        return field_57053_n;
    }

    public String func_57045_c()
    {
        return field_57054_o;
    }

    public String func_57032_d()
    {
        return StringTranslate.getInstance().translateKey((new StringBuilder()).append("itemGroup.").append(func_57045_c()).toString());
    }

    public Item func_57039_e()
    {
        return Item.itemsList[func_57042_a()];
    }

    public int func_57042_a()
    {
        return 1;
    }

    public String func_57034_f()
    {
        return field_57063_p;
    }

    public CreativeTabs func_57033_a(String par1Str)
    {
        field_57063_p = par1Str;
        return this;
    }

    public boolean func_57037_g()
    {
        return field_57061_r;
    }

    public CreativeTabs func_57043_h()
    {
        field_57061_r = false;
        return this;
    }

    public boolean func_57035_i()
    {
        return field_57062_q;
    }

    public CreativeTabs func_57040_j()
    {
        field_57062_q = false;
        return this;
    }

    public int func_57041_k()
    {
        return field_57053_n % 6;
    }

    public boolean func_57044_l()
    {
        return field_57053_n < 6;
    }

    public void func_57036_a(List par1List)
    {
        Item aitem[] = Item.itemsList;
        int i = aitem.length;

        for (int j = 0; j < i; j++)
        {
            Item item = aitem[j];

            if (item != null && item.func_56811_f() == this)
            {
                item.func_56814_a(item.shiftedIndex, this, par1List);
            }
        }
    }
}
