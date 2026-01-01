package net.minecraft.src;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BanList
{
    private final LowerStringMap field_57350_a = new LowerStringMap();
    private final File field_57348_b;
    private boolean field_57349_c;

    public BanList(File par1File)
    {
        field_57349_c = true;
        field_57348_b = par1File;
    }

    public boolean func_57344_a()
    {
        return field_57349_c;
    }

    public void func_57343_a(boolean par1)
    {
        field_57349_c = par1;
    }

    public Map func_57340_b()
    {
        func_57345_c();
        return field_57350_a;
    }

    public boolean func_57339_a(String par1Str)
    {
        if (!func_57344_a())
        {
            return false;
        }
        else
        {
            func_57345_c();
            return field_57350_a.containsKey(par1Str);
        }
    }

    public void func_57346_a(BanEntry par1BanEntry)
    {
        field_57350_a.func_56878_a(par1BanEntry.func_57016_a(), par1BanEntry);
        func_57342_e();
    }

    public void func_57347_b(String par1Str)
    {
        field_57350_a.remove(par1Str);
        func_57342_e();
    }

    public void func_57345_c()
    {
        Iterator iterator = field_57350_a.values().iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            BanEntry banentry = (BanEntry)iterator.next();

            if (banentry.func_57014_e())
            {
                iterator.remove();
            }
        }
        while (true);
    }

    public void func_57341_d()
    {
        if (!field_57348_b.isFile())
        {
            return;
        }

        BufferedReader bufferedreader;

        try
        {
            bufferedreader = new BufferedReader(new FileReader(field_57348_b));
        }
        catch (FileNotFoundException filenotfoundexception)
        {
            throw new Error();
        }

        try
        {
            do
            {
                String s;

                if ((s = bufferedreader.readLine()) == null)
                {
                    break;
                }

                if (!s.startsWith("#"))
                {
                    BanEntry banentry = BanEntry.func_57013_c(s);

                    if (banentry != null)
                    {
                        field_57350_a.func_56878_a(banentry.func_57016_a(), banentry);
                    }
                }
            }
            while (true);
        }
        catch (IOException ioexception)
        {
            Logger.getLogger("Minecraft").log(Level.SEVERE, "Could not load ban list", ioexception);
        }
    }

    public void func_57342_e()
    {
        func_57338_b(true);
    }

    public void func_57338_b(boolean par1)
    {
        func_57345_c();

        try
        {
            PrintWriter printwriter = new PrintWriter(new FileWriter(field_57348_b, false));

            if (par1)
            {
                printwriter.println((new StringBuilder()).append("# Updated ").append((new SimpleDateFormat()).format(new Date())).append(" by Minecraft ").append("12w26a").toString());
                printwriter.println("# victim name | ban date | banned by | banned until | reason");
                printwriter.println();
            }

            BanEntry banentry;

            for (Iterator iterator = field_57350_a.values().iterator(); iterator.hasNext(); printwriter.println(banentry.func_57017_g()))
            {
                banentry = (BanEntry)iterator.next();
            }

            printwriter.close();
        }
        catch (IOException ioexception)
        {
            Logger.getLogger("Minecraft").log(Level.SEVERE, "Could not save ban list", ioexception);
        }
    }
}
