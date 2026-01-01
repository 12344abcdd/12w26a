package net.minecraft.src;

public class DestroyBlockProgress
{
    private final int field_57561_a;
    private final int field_57559_b;
    private final int field_57560_c;
    private final int field_57557_d;
    private int field_57558_e;

    public DestroyBlockProgress(int par1, int par2, int par3, int par4)
    {
        field_57561_a = par1;
        field_57559_b = par2;
        field_57560_c = par3;
        field_57557_d = par4;
    }

    public int func_57554_a()
    {
        return field_57559_b;
    }

    public int func_57556_b()
    {
        return field_57560_c;
    }

    public int func_57555_c()
    {
        return field_57557_d;
    }

    public void func_57553_a(int par1)
    {
        if (par1 > 10)
        {
            par1 = 10;
        }

        field_57558_e = par1;
    }

    public int func_57552_d()
    {
        return field_57558_e;
    }
}
