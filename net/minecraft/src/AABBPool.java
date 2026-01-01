package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class AABBPool
{
    private final int field_58159_a;
    private final int field_58157_b;
    private final List field_58158_c = new ArrayList();
    private int field_58155_d;
    private int field_58156_e;
    private int field_58154_f;

    public AABBPool(int par1, int par2)
    {
        field_58155_d = 0;
        field_58156_e = 0;
        field_58154_f = 0;
        field_58159_a = par1;
        field_58157_b = par2;
    }

    public AxisAlignedBB func_58153_a(double par1, double par3, double par5, double par7, double par9, double par11)
    {
        AxisAlignedBB axisalignedbb;

        if (field_58155_d >= field_58158_c.size())
        {
            axisalignedbb = new AxisAlignedBB(par1, par3, par5, par7, par9, par11);
            field_58158_c.add(axisalignedbb);
        }
        else
        {
            axisalignedbb = (AxisAlignedBB)field_58158_c.get(field_58155_d);
            axisalignedbb.setBounds(par1, par3, par5, par7, par9, par11);
        }

        field_58155_d++;
        return axisalignedbb;
    }

    public void func_58151_a()
    {
        if (field_58155_d > field_58156_e)
        {
            field_58156_e = field_58155_d;
        }

        if (field_58154_f++ == field_58159_a)
        {
            for (int i = Math.max(field_58156_e, field_58158_c.size() - field_58157_b); field_58158_c.size() > i; field_58158_c.remove(i)) { }

            field_58156_e = 0;
            field_58154_f = 0;
        }

        field_58155_d = 0;
    }

    public void func_58152_b()
    {
        field_58155_d = 0;
        field_58158_c.clear();
    }
}
