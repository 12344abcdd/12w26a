package net.minecraft.src;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils
{
    private static final Pattern field_55395_a = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");

    public StringUtils()
    {
    }

    public static String func_55393_a(int par0)
    {
        int i = par0 / 20;
        int j = i / 60;
        i %= 60;

        if (i < 10)
        {
            return (new StringBuilder()).append(j).append(":0").append(i).toString();
        }
        else
        {
            return (new StringBuilder()).append(j).append(":").append(i).toString();
        }
    }

    public static String func_55394_a(String par0Str)
    {
        return field_55395_a.matcher(par0Str).replaceAll("");
    }
}
