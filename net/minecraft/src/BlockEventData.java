package net.minecraft.src;

public class BlockEventData
{
    private int field_58113_a;
    private int field_58111_b;
    private int field_58112_c;
    private int field_58109_d;
    private int field_58110_e;

    public BlockEventData(int par1, int par2, int par3, int par4, int par5)
    {
        field_58113_a = par1;
        field_58111_b = par2;
        field_58112_c = par3;
        field_58109_d = par4;
        field_58110_e = par5;
    }

    public int func_58106_a()
    {
        return field_58113_a;
    }

    public int func_58108_b()
    {
        return field_58111_b;
    }

    public int func_58107_c()
    {
        return field_58112_c;
    }

    public int func_58105_d()
    {
        return field_58109_d;
    }

    public int func_58104_e()
    {
        return field_58110_e;
    }
}
