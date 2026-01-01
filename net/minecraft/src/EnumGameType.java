package net.minecraft.src;

public enum EnumGameType
{
    NOT_SET(-1, ""),
    SURVIVAL(0, "survival"),
    CREATIVE(1, "creative"),
    ADVENTURE(2, "adventure");

    int field_57544_e;
    String field_57541_f;

    private EnumGameType(int par3, String par4Str)
    {
        field_57544_e = par3;
        field_57541_f = par4Str;
    }

    public int func_57538_a()
    {
        return field_57544_e;
    }

    public String func_57539_b()
    {
        return field_57541_f;
    }

    public void func_57532_a(PlayerCapabilities par1PlayerCapabilities)
    {
        if (this == CREATIVE)
        {
            par1PlayerCapabilities.allowFlying = true;
            par1PlayerCapabilities.isCreativeMode = true;
            par1PlayerCapabilities.disableDamage = true;
        }
        else
        {
            par1PlayerCapabilities.allowFlying = false;
            par1PlayerCapabilities.isCreativeMode = false;
            par1PlayerCapabilities.disableDamage = false;
            par1PlayerCapabilities.isFlying = false;
        }

        par1PlayerCapabilities.field_57531_e = !func_57540_c();
    }

    public boolean func_57540_c()
    {
        return this == ADVENTURE;
    }

    public boolean func_57534_d()
    {
        return this == CREATIVE;
    }

    public boolean func_57533_e()
    {
        return this == SURVIVAL || this == ADVENTURE;
    }

    public static EnumGameType func_57536_a(int par0)
    {
        EnumGameType aenumgametype[] = values();
        int i = aenumgametype.length;

        for (int j = 0; j < i; j++)
        {
            EnumGameType enumgametype = aenumgametype[j];

            if (enumgametype.field_57544_e == par0)
            {
                return enumgametype;
            }
        }

        return SURVIVAL;
    }

    public static EnumGameType func_57535_a(String par0Str)
    {
        EnumGameType aenumgametype[] = values();
        int i = aenumgametype.length;

        for (int j = 0; j < i; j++)
        {
            EnumGameType enumgametype = aenumgametype[j];

            if (enumgametype.field_57541_f.equals(par0Str))
            {
                return enumgametype;
            }
        }

        return SURVIVAL;
    }
}
