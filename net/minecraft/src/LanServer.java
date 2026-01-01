package net.minecraft.src;

public class LanServer
{
    private String field_58165_a;
    private String field_58163_b;
    private long field_58164_c;

    public LanServer(String par1Str, String par2Str)
    {
        field_58165_a = par1Str;
        field_58163_b = par2Str;
        field_58164_c = System.currentTimeMillis();
    }

    public String func_58160_a()
    {
        return field_58165_a;
    }

    public String func_58161_b()
    {
        return field_58163_b;
    }

    public void func_58162_c()
    {
        field_58164_c = System.currentTimeMillis();
    }
}
