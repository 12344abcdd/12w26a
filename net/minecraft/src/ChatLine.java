package net.minecraft.src;

public class ChatLine
{
    private final int field_55316_a;
    private final String field_55314_b;
    private final int field_55315_c;

    public ChatLine(int par1, String par2Str, int par3)
    {
        field_55314_b = par2Str;
        field_55316_a = par1;
        field_55315_c = par3;
    }

    public String func_55311_a()
    {
        return field_55314_b;
    }

    public int func_55313_b()
    {
        return field_55316_a;
    }

    public int func_55312_c()
    {
        return field_55315_c;
    }
}
