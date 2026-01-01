package net.minecraft.src;

public class RConConsoleSource implements ICommandSender
{
    public static final RConConsoleSource field_56285_a = new RConConsoleSource();
    private StringBuffer field_56284_b;

    public RConConsoleSource()
    {
        field_56284_b = new StringBuffer();
    }

    public void func_56282_a()
    {
        field_56284_b.setLength(0);
    }

    public String func_56283_b()
    {
        return field_56284_b.toString();
    }

    public String func_55088_aJ()
    {
        return "Rcon";
    }

    public void func_55086_a(String par1Str)
    {
        field_56284_b.append(par1Str);
    }

    public boolean func_55084_b(String par1Str)
    {
        return true;
    }

    public String func_55085_a(String par1Str, Object par2ArrayOfObj[])
    {
        return StringTranslate.getInstance().translateKeyFormat(par1Str, par2ArrayOfObj);
    }
}
