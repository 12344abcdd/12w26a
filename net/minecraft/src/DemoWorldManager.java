package net.minecraft.src;

public class DemoWorldManager extends ItemInWorldManager
{
    private boolean field_57383_c;
    private boolean field_57381_d;
    private int field_57382_e;
    private int field_57380_f;

    public DemoWorldManager(World par1World)
    {
        super(par1World);
        field_57383_c = false;
        field_57381_d = false;
        field_57382_e = 0;
        field_57380_f = 0;
    }

    public void func_57353_a()
    {
        super.func_57353_a();
        field_57380_f++;
        long l = field_57370_a.getWorldTime();
        long l1 = l / 24000L + 1L;

        if (!field_57383_c && field_57380_f > 20)
        {
            field_57383_c = true;
            field_57368_b.field_56268_a.func_56717_b(new Packet70GameEvent(5, 0));
        }

        field_57381_d = l > 0x1d6b4L;

        if (field_57381_d)
        {
            field_57382_e++;
        }

        if (l % 24000L == 500L)
        {
            if (l1 <= 6L)
            {
                field_57368_b.func_55086_a(field_57368_b.func_55085_a((new StringBuilder()).append("demo.day.").append(l1).toString(), new Object[0]));
            }
        }
        else if (l1 == 1L)
        {
            if (l == 100L)
            {
                field_57368_b.field_56268_a.func_56717_b(new Packet70GameEvent(5, 101));
            }
            else if (l == 175L)
            {
                field_57368_b.field_56268_a.func_56717_b(new Packet70GameEvent(5, 102));
            }
            else if (l == 250L)
            {
                field_57368_b.field_56268_a.func_56717_b(new Packet70GameEvent(5, 103));
            }
        }
        else if (l1 == 5L && l % 24000L == 22000L)
        {
            field_57368_b.func_55086_a(field_57368_b.func_55085_a("demo.day.warning", new Object[0]));
        }
    }

    private void func_57379_d()
    {
        if (field_57382_e > 100)
        {
            field_57368_b.func_55086_a(field_57368_b.func_55085_a("demo.reminder", new Object[0]));
            field_57382_e = 0;
        }
    }

    public void func_57352_a(int par1, int par2, int par3, int par4)
    {
        if (field_57381_d)
        {
            func_57379_d();
            return;
        }
        else
        {
            super.func_57352_a(par1, par2, par3, par4);
            return;
        }
    }

    public void func_57358_a(int par1, int par2, int par3)
    {
        if (field_57381_d)
        {
            return;
        }
        else
        {
            super.func_57358_a(par1, par2, par3);
            return;
        }
    }

    public boolean func_57361_b(int par1, int par2, int par3)
    {
        if (field_57381_d)
        {
            return false;
        }
        else
        {
            return super.func_57361_b(par1, par2, par3);
        }
    }

    public boolean func_57357_a(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack)
    {
        if (field_57381_d)
        {
            func_57379_d();
            return false;
        }
        else
        {
            return super.func_57357_a(par1EntityPlayer, par2World, par3ItemStack);
        }
    }

    public boolean func_57355_a(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (field_57381_d)
        {
            func_57379_d();
            return false;
        }
        else
        {
            return super.func_57355_a(par1EntityPlayer, par2World, par3ItemStack, par4, par5, par6, par7, par8, par9, par10);
        }
    }
}
