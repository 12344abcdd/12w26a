package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class Vec3Pool
{
    private final int field_58142_a;
    private final int field_58140_b;
    private final List field_58141_c = new ArrayList();
    private int field_58138_d;
    private int field_58139_e;
    private int field_58137_f;

    public Vec3Pool(int par1, int par2)
    {
        field_58138_d = 0;
        field_58139_e = 0;
        field_58137_f = 0;
        field_58142_a = par1;
        field_58140_b = par2;
    }

    public Vec3 func_58134_a(double par1, double par3, double par5)
    {
        Vec3 vec3;

        if (field_58138_d >= field_58141_c.size())
        {
            vec3 = new Vec3(par1, par3, par5);
            field_58141_c.add(vec3);
        }
        else
        {
            vec3 = (Vec3)field_58141_c.get(field_58138_d);
            vec3.setComponents(par1, par3, par5);
        }

        field_58138_d++;
        return vec3;
    }

    public void func_58135_a()
    {
        if (field_58138_d > field_58139_e)
        {
            field_58139_e = field_58138_d;
        }

        if (field_58137_f++ == field_58142_a)
        {
            for (int i = Math.max(field_58139_e, field_58141_c.size() - field_58140_b); field_58141_c.size() > i; field_58141_c.remove(i)) { }

            field_58139_e = 0;
            field_58137_f = 0;
        }

        field_58138_d = 0;
    }

    public void func_58136_b()
    {
        field_58138_d = 0;
        field_58141_c.clear();
    }
}
