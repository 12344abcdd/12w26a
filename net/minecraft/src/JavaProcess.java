package net.minecraft.src;

import java.util.List;

public class JavaProcess
{
    private final List field_56712_a = null;
    private final Process field_56711_b = null;

    public boolean func_56710_a()
    {
        try
        {
            field_56711_b.exitValue();
        }
        catch (IllegalThreadStateException illegalthreadstateexception)
        {
            return true;
        }

        return false;
    }

    public String toString()
    {
        return (new StringBuilder()).append("JavaProcess[commands=").append(field_56712_a).append(", isRunning=").append(func_56710_a()).append("]").toString();
    }
}
