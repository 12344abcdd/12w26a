package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import org.lwjgl.opengl.GL11;

public abstract class TexturePackImplementation implements TexturePackBase
{
    private final String field_55189_e;
    private final String field_55186_f;
    protected final File field_55192_a;
    protected String field_55190_b;
    protected String field_55191_c;
    protected BufferedImage field_55188_d;
    private int field_55187_g;

    protected TexturePackImplementation(String par1Str, String par2Str)
    {
        this(par1Str, null, par2Str);
    }

    protected TexturePackImplementation(String par1Str, File par2File, String par3Str)
    {
        field_55187_g = -1;
        field_55189_e = par1Str;
        field_55186_f = par3Str;
        field_55192_a = par2File;
        func_55185_f();
        func_55183_e();
    }

    private static String func_55184_b(String par0Str)
    {
        if (par0Str != null && par0Str.length() > 34)
        {
            par0Str = par0Str.substring(0, 34);
        }

        return par0Str;
    }

    private void func_55185_f()
    {
        InputStream inputstream = null;

        try
        {
            inputstream = getResourceAsStream("/pack.png");
            field_55188_d = ImageIO.read(inputstream);
        }
        catch (IOException ioexception) { }
        finally
        {
            try
            {
                inputstream.close();
            }
            catch (IOException ioexception1) { }
        }
    }

    protected void func_55183_e()
    {
        InputStream inputstream = null;
        BufferedReader bufferedreader = null;

        try
        {
            inputstream = getResourceAsStream("/pack.txt");
            bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
            field_55190_b = func_55184_b(bufferedreader.readLine());
            field_55191_c = func_55184_b(bufferedreader.readLine());
        }
        catch (IOException ioexception) { }
        finally
        {
            try
            {
                bufferedreader.close();
                inputstream.close();
            }
            catch (IOException ioexception1) { }
        }
    }

    public void func_55179_a(RenderEngine par1RenderEngine)
    {
        if (field_55188_d != null && field_55187_g != -1)
        {
            par1RenderEngine.deleteTexture(field_55187_g);
        }
    }

    public void func_55181_b(RenderEngine par1RenderEngine)
    {
        if (field_55188_d != null)
        {
            if (field_55187_g == -1)
            {
                field_55187_g = par1RenderEngine.allocateAndSetupTexture(field_55188_d);
            }

            par1RenderEngine.bindTexture(field_55187_g);
        }
        else
        {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, par1RenderEngine.getTexture("/gui/unknown_pack.png"));
        }
    }

    /**
     * Gives a texture resource as InputStream.
     */
    public InputStream getResourceAsStream(String par1Str)
    {
        return (net.minecraft.src.TexturePackBase.class).getResourceAsStream(par1Str);
    }

    public String func_55178_a()
    {
        return field_55189_e;
    }

    public String func_55180_b()
    {
        return field_55186_f;
    }

    public String func_55182_c()
    {
        return field_55190_b;
    }

    public String func_55177_d()
    {
        return field_55191_c;
    }

    public int func_56598_e()
    {
        return 16;
    }
}
