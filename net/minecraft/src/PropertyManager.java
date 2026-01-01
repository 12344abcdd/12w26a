package net.minecraft.src;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertyManager
{
    public static Logger field_57084_a = Logger.getLogger("Minecraft");
    private Properties field_57082_b;
    private File field_57083_c;

    public PropertyManager(File par1File)
    {
        field_57082_b = new Properties();
        field_57083_c = par1File;

        if (par1File.exists())
        {
            FileInputStream fileinputstream = null;

            try
            {
                fileinputstream = new FileInputStream(par1File);
                field_57082_b.load(fileinputstream);
            }
            catch (Exception exception)
            {
                field_57084_a.log(Level.WARNING, (new StringBuilder()).append("Failed to load ").append(par1File).toString(), exception);
                func_57076_a();
            }
            finally
            {
                if (fileinputstream != null)
                {
                    try
                    {
                        fileinputstream.close();
                    }
                    catch (IOException ioexception) { }
                }
            }
        }
        else
        {
            field_57084_a.log(Level.WARNING, (new StringBuilder()).append(par1File).append(" does not exist").toString());
            func_57076_a();
        }
    }

    public void func_57076_a()
    {
        field_57084_a.log(Level.INFO, "Generating new properties file");
        func_57078_b();
    }

    public void func_57078_b()
    {
        FileOutputStream fileoutputstream = null;

        try
        {
            fileoutputstream = new FileOutputStream(field_57083_c);
            field_57082_b.store(fileoutputstream, "Minecraft server properties");
        }
        catch (Exception exception)
        {
            field_57084_a.log(Level.WARNING, (new StringBuilder()).append("Failed to save ").append(field_57083_c).toString(), exception);
            func_57076_a();
        }
        finally
        {
            if (fileoutputstream != null)
            {
                try
                {
                    fileoutputstream.close();
                }
                catch (IOException ioexception) { }
            }
        }
    }

    public File func_57075_c()
    {
        return field_57083_c;
    }

    public String func_57081_a(String par1Str, String par2Str)
    {
        if (!field_57082_b.containsKey(par1Str))
        {
            field_57082_b.setProperty(par1Str, par2Str);
            func_57078_b();
        }

        return field_57082_b.getProperty(par1Str, par2Str);
    }

    public int func_57079_a(String par1Str, int par2)
    {
        try
        {
            return Integer.parseInt(func_57081_a(par1Str, (new StringBuilder()).append("").append(par2).toString()));
        }
        catch (Exception exception)
        {
            field_57082_b.setProperty(par1Str, (new StringBuilder()).append("").append(par2).toString());
        }

        return par2;
    }

    public boolean func_57080_a(String par1Str, boolean par2)
    {
        try
        {
            return Boolean.parseBoolean(func_57081_a(par1Str, (new StringBuilder()).append("").append(par2).toString()));
        }
        catch (Exception exception)
        {
            field_57082_b.setProperty(par1Str, (new StringBuilder()).append("").append(par2).toString());
        }

        return par2;
    }

    public void func_57077_a(String par1Str, Object par2Obj)
    {
        field_57082_b.setProperty(par1Str, (new StringBuilder()).append("").append(par2Obj).toString());
    }
}
