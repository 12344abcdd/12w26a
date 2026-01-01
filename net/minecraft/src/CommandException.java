package net.minecraft.src;

public class CommandException extends RuntimeException
{
    private Object field_55289_a[];

    public CommandException(String par1Str, Object par2ArrayOfObj[])
    {
        super(par1Str);
        field_55289_a = par2ArrayOfObj;
    }

    public Object[] func_55288_a()
    {
        return field_55289_a;
    }
}
