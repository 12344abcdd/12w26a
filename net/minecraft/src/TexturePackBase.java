package net.minecraft.src;

import java.io.InputStream;

public interface TexturePackBase
{
    public abstract void func_55179_a(RenderEngine renderengine);

    public abstract void func_55181_b(RenderEngine renderengine);

    /**
     * Gives a texture resource as InputStream.
     */
    public abstract InputStream getResourceAsStream(String s);

    public abstract String func_55178_a();

    public abstract String func_55180_b();

    public abstract String func_55182_c();

    public abstract String func_55177_d();

    public abstract int func_56598_e();
}
