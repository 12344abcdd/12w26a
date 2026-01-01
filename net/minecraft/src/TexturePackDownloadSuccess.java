package net.minecraft.src;

import java.io.File;
import net.minecraft.client.Minecraft;

class TexturePackDownloadSuccess implements IDownloadSuccess
{
    final TexturePackList field_56642_a;

    TexturePackDownloadSuccess(TexturePackList par1TexturePackList)
    {
        field_56642_a = par1TexturePackList;
    }

    public void func_56641_a(File par1File)
    {
        if (!TexturePackList.func_57504_a(field_56642_a))
        {
            return;
        }
        else
        {
            TexturePackList.func_57503_a(field_56642_a, new TexturePackCustom(TexturePackList.func_57497_a(field_56642_a, par1File), par1File));
            TexturePackList.func_57506_b(field_56642_a).func_56447_y();
            return;
        }
    }
}
