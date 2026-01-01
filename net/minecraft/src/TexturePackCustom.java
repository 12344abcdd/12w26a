package net.minecraft.src;

import java.io.*;
import java.util.zip.ZipFile;

public class TexturePackCustom extends TexturePackImplementation
{
    private ZipFile field_55194_e;

    public TexturePackCustom(String par1Str, File par2File)
    {
        super(par1Str, par2File, par2File.getName());
    }

    public void func_55179_a(RenderEngine par1RenderEngine)
    {
        super.func_55179_a(par1RenderEngine);

        try
        {
            field_55194_e.close();
        }
        catch (IOException ioexception) { }

        field_55194_e = null;
    }

    /**
     * Gives a texture resource as InputStream.
     */
    public InputStream getResourceAsStream(String par1Str)
    {
        func_55193_f();

        try
        {
            java.util.zip.ZipEntry zipentry = field_55194_e.getEntry(par1Str.substring(1));

            if (zipentry != null)
            {
                return field_55194_e.getInputStream(zipentry);
            }
        }
        catch (Exception exception) { }

        return super.getResourceAsStream(par1Str);
    }

    private void func_55193_f()
    {
        if (field_55194_e != null)
        {
            return;
        }

        try
        {
            field_55194_e = new ZipFile(field_55192_a);
        }
        catch (IOException ioexception) { }
    }
}
