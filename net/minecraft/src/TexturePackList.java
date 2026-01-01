package net.minecraft.src;

import java.io.File;
import java.util.*;
import net.minecraft.client.Minecraft;

public class TexturePackList
{
    private static final TexturePackBase field_55283_a = new TexturePackDefault();
    private final Minecraft field_57508_b;

    /** The directory the texture packs will be loaded from. */
    private final File texturePackDir;
    private final File field_57507_d;

    /** The list of the available texture packs. */
    private List availableTexturePacks;
    private Map field_55280_f;

    /** The TexturePack that will be used. */
    private TexturePackBase selectedTexturePack;
    private boolean field_57509_h;

    public TexturePackList(File par1File, Minecraft par2Minecraft)
    {
        availableTexturePacks = new ArrayList();
        field_55280_f = new HashMap();
        field_57508_b = par2Minecraft;
        texturePackDir = new File(par1File, "texturepacks");
        field_57507_d = new File(par1File, "texturepacks-mp-cache");
        func_55276_d();
        updateAvaliableTexturePacks();
    }

    private void func_55276_d()
    {
        if (!texturePackDir.isDirectory())
        {
            texturePackDir.delete();
            texturePackDir.mkdirs();
        }

        if (!field_57507_d.isDirectory())
        {
            field_57507_d.delete();
            field_57507_d.mkdirs();
        }
    }

    /**
     * Sets the new TexturePack to be used, returning true if it has actually changed, false if nothing changed.
     */
    public boolean setTexturePack(TexturePackBase par1TexturePackBase)
    {
        if (par1TexturePackBase == selectedTexturePack)
        {
            return false;
        }
        else
        {
            field_57509_h = false;
            selectedTexturePack = par1TexturePackBase;
            field_57508_b.gameSettings.skin = par1TexturePackBase.func_55180_b();
            field_57508_b.gameSettings.saveOptions();
            return true;
        }
    }

    public void func_57499_a(String par1Str)
    {
        String s = par1Str.substring(par1Str.lastIndexOf("/") + 1);

        if (s.contains("?"))
        {
            s = s.substring(0, s.indexOf("?"));
        }

        if (!s.endsWith(".zip"))
        {
            return;
        }
        else
        {
            File file = new File(field_57507_d, s);
            func_57500_a(par1Str, file);
            return;
        }
    }

    private void func_57500_a(String par1Str, File par2File)
    {
        HashMap hashmap = new HashMap();
        GuiProgress guiprogress = new GuiProgress();
        hashmap.put("X-Minecraft-Username", field_57508_b.session.username);
        hashmap.put("X-Minecraft-Version", "12w26a");
        hashmap.put("X-Minecraft-Supported-Resolutions", "16");
        field_57509_h = true;
        field_57508_b.displayGuiScreen(guiprogress);
        HttpUtil.func_57218_a(par2File, par1Str, new TexturePackDownloadSuccess(this), hashmap, 0x989680, guiprogress);
    }

    public boolean func_57498_a()
    {
        return field_57509_h;
    }

    public void func_57505_b()
    {
        field_57509_h = false;
        updateAvaliableTexturePacks();
        field_57508_b.func_56447_y();
    }

    /**
     * check the texture packs the client has installed
     */
    public void updateAvaliableTexturePacks()
    {
        ArrayList arraylist = new ArrayList();
        selectedTexturePack = field_55283_a;
        arraylist.add(field_55283_a);
        Iterator iterator = func_55279_e().iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            File file = (File)iterator.next();
            String s = func_55277_a(file);

            if (s != null)
            {
                Object obj = (TexturePackBase)field_55280_f.get(s);

                if (obj == null)
                {
                    obj = file.isDirectory() ? ((Object)(new TexturePackFolder(s, file))) : ((Object)(new TexturePackCustom(s, file)));
                    field_55280_f.put(s, obj);
                }

                if (((TexturePackBase)(obj)).func_55180_b().equals(field_57508_b.gameSettings.skin))
                {
                    selectedTexturePack = ((TexturePackBase)(obj));
                }

                arraylist.add(obj);
            }
        }
        while (true);

        availableTexturePacks.removeAll(arraylist);
        TexturePackBase texturepackbase;

        for (Iterator iterator1 = availableTexturePacks.iterator(); iterator1.hasNext(); field_55280_f.remove(texturepackbase.func_55178_a()))
        {
            texturepackbase = (TexturePackBase)iterator1.next();
            texturepackbase.func_55179_a(field_57508_b.renderEngine);
        }

        availableTexturePacks = arraylist;
    }

    private String func_55277_a(File par1File)
    {
        if (par1File.isFile() && par1File.getName().toLowerCase().endsWith(".zip"))
        {
            return (new StringBuilder()).append(par1File.getName()).append(":").append(par1File.length()).append(":").append(par1File.lastModified()).toString();
        }

        if (par1File.isDirectory() && (new File(par1File, "pack.txt")).exists())
        {
            return (new StringBuilder()).append(par1File.getName()).append(":folder:").append(par1File.lastModified()).toString();
        }
        else
        {
            return null;
        }
    }

    private List func_55279_e()
    {
        if (texturePackDir.exists() && texturePackDir.isDirectory())
        {
            return Arrays.asList(texturePackDir.listFiles());
        }
        else
        {
            return Collections.emptyList();
        }
    }

    /**
     * Returns a list of the available texture packs.
     */
    public List availableTexturePacks()
    {
        return Collections.unmodifiableList(availableTexturePacks);
    }

    public TexturePackBase func_55278_c()
    {
        return selectedTexturePack;
    }

    public boolean func_57502_f()
    {
        if (!field_57508_b.gameSettings.field_57427_s)
        {
            return false;
        }

        ServerData serverdata = field_57508_b.func_56446_z();

        if (serverdata == null)
        {
            return true;
        }
        else
        {
            return serverdata.func_57441_c();
        }
    }

    public boolean func_57501_g()
    {
        if (!field_57508_b.gameSettings.field_57427_s)
        {
            return false;
        }

        ServerData serverdata = field_57508_b.func_56446_z();

        if (serverdata == null)
        {
            return false;
        }
        else
        {
            return serverdata.func_57440_b();
        }
    }

    static boolean func_57504_a(TexturePackList par0TexturePackList)
    {
        return par0TexturePackList.field_57509_h;
    }

    static TexturePackBase func_57503_a(TexturePackList par0TexturePackList, TexturePackBase par1TexturePackBase)
    {
        return par0TexturePackList.selectedTexturePack = par1TexturePackBase;
    }

    static String func_57497_a(TexturePackList par0TexturePackList, File par1File)
    {
        return par0TexturePackList.func_55277_a(par1File);
    }

    static Minecraft func_57506_b(TexturePackList par0TexturePackList)
    {
        return par0TexturePackList.field_57508_b;
    }
}
