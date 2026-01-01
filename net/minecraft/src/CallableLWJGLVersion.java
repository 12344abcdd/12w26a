package net.minecraft.src;

import java.util.concurrent.Callable;
import net.minecraft.client.Minecraft;
import org.lwjgl.Sys;

public class CallableLWJGLVersion implements Callable
{
    final Minecraft field_58133_a;

    public CallableLWJGLVersion(Minecraft par1Minecraft)
    {
        field_58133_a = par1Minecraft;
    }

    public String func_58132_a()
    {
        return Sys.getVersion();
    }

    public Object call()
    {
        return func_58132_a();
    }
}
