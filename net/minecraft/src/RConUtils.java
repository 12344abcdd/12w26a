package net.minecraft.src;

public class RConUtils
{
    public static char field_57426_a[] =
    {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f'
    };

    public RConUtils()
    {
    }

    public static String func_57422_a(byte par0ArrayOfByte[], int par1, int par2)
    {
        int i = par2 - 1;
        int j;

        for (j = par1 <= i ? par1 : i; 0 != par0ArrayOfByte[j] && j < i; j++) { }

        return new String(par0ArrayOfByte, par1, j - par1);
    }

    public static int func_57421_a(byte par0ArrayOfByte[], int par1)
    {
        return func_57425_b(par0ArrayOfByte, par1, par0ArrayOfByte.length);
    }

    public static int func_57425_b(byte par0ArrayOfByte[], int par1, int par2)
    {
        if (0 > par2 - par1 - 4)
        {
            return 0;
        }
        else
        {
            return par0ArrayOfByte[par1 + 3] << 24 | (par0ArrayOfByte[par1 + 2] & 0xff) << 16 | (par0ArrayOfByte[par1 + 1] & 0xff) << 8 | par0ArrayOfByte[par1] & 0xff;
        }
    }

    public static int func_57424_c(byte par0ArrayOfByte[], int par1, int par2)
    {
        if (0 > par2 - par1 - 4)
        {
            return 0;
        }
        else
        {
            return par0ArrayOfByte[par1] << 24 | (par0ArrayOfByte[par1 + 1] & 0xff) << 16 | (par0ArrayOfByte[par1 + 2] & 0xff) << 8 | par0ArrayOfByte[par1 + 3] & 0xff;
        }
    }

    public static String func_57423_a(byte par0)
    {
        return (new StringBuilder()).append("").append(field_57426_a[(par0 & 0xf0) >>> 4]).append(field_57426_a[par0 & 0xf]).toString();
    }
}
