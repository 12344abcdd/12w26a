package net.minecraft.src;

public class EnumOSHelper
{
    public static final int field_58131_a[];

    static
    {
        field_58131_a = new int[EnumOS.values().length];

        try
        {
            field_58131_a[EnumOS.LINUX.ordinal()] = 1;
        }
        catch (NoSuchFieldError nosuchfielderror) { }

        try
        {
            field_58131_a[EnumOS.SOLARIS.ordinal()] = 2;
        }
        catch (NoSuchFieldError nosuchfielderror1) { }

        try
        {
            field_58131_a[EnumOS.WINDOWS.ordinal()] = 3;
        }
        catch (NoSuchFieldError nosuchfielderror2) { }

        try
        {
            field_58131_a[EnumOS.MACOS.ordinal()] = 4;
        }
        catch (NoSuchFieldError nosuchfielderror3) { }
    }
}
