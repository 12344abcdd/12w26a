package net.minecraft.src;

import java.util.concurrent.Callable;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;

public class CallableModded implements Callable
{
    final Minecraft field_58130_a;

    public CallableModded(Minecraft par1Minecraft)
    {
        field_58130_a = par1Minecraft;
    }

    public String func_58129_a()
    {
        String s = ClientBrandRetriever.func_56462_getClientModName();

        if (!s.equals("vanilla"))
        {
            return (new StringBuilder()).append("Definitely; '").append(s).append("'").toString();
        }

        if ((net.minecraft.client.Minecraft.class).getClassLoader().getResource("META-INF/CODESIGN.RSA") == null)
        {
            return "Very likely";
        }
        else
        {
            return "Probably not";
        }
    }

    public Object call()
    {
        return func_58129_a();
    }
}
