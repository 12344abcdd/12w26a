package net.minecraft.src;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CrashReport
{
    private final String field_55376_a;
    private final Throwable field_55374_b;
    private final Map field_55375_c = new LinkedHashMap();
    private File field_57262_d;

    public CrashReport(String par1Str, Throwable par2Throwable)
    {
        field_57262_d = null;
        field_55376_a = par1Str;
        field_55374_b = par2Throwable;
        func_55373_f();
    }

    private void func_55373_f()
    {
        func_55366_a("Minecraft Version", new CallableMinecraftVersion(this));
        func_55366_a("Operating System", new CallableOSInfo(this));
        func_55366_a("Java Version", new CallableJavaInfo(this));
        func_55366_a("Java VM Version", new CallableJavaInfo2(this));
        func_55366_a("Memory", new CallableMemoryInfo(this));
    }

    public void func_55366_a(String par1Str, Callable par2Callable)
    {
        try
        {
            func_55369_a(par1Str, par2Callable.call());
        }
        catch (Throwable throwable)
        {
            func_55364_a(par1Str, throwable);
        }
    }

    public void func_55369_a(String par1Str, Object par2Obj)
    {
        field_55375_c.put(par1Str, par2Obj != null ? ((Object)(par2Obj.toString())) : "null");
    }

    public void func_55364_a(String par1Str, Throwable par2Throwable)
    {
        func_55369_a(par1Str, (new StringBuilder()).append("~ERROR~ ").append(par2Throwable.getClass().getSimpleName()).append(": ").append(par2Throwable.getMessage()).toString());
    }

    public String func_55365_a()
    {
        return field_55376_a;
    }

    public Throwable func_55363_b()
    {
        return field_55374_b;
    }

    public String func_55372_c()
    {
        StringBuilder stringbuilder = new StringBuilder();
        func_55368_a(stringbuilder);
        return stringbuilder.toString();
    }

    public void func_55368_a(StringBuilder par1StringBuilder)
    {
        boolean flag = true;

        for (Iterator iterator = field_55375_c.entrySet().iterator(); iterator.hasNext();)
        {
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();

            if (!flag)
            {
                par1StringBuilder.append("\n");
            }

            par1StringBuilder.append("- ");
            par1StringBuilder.append((String)entry.getKey());
            par1StringBuilder.append(": ");
            par1StringBuilder.append((String)entry.getValue());
            flag = false;
        }
    }

    public String func_55362_d()
    {
        StringWriter stringwriter = null;
        PrintWriter printwriter = null;
        String s = field_55374_b.toString();

        try
        {
            stringwriter = new StringWriter();
            printwriter = new PrintWriter(stringwriter);
            field_55374_b.printStackTrace(printwriter);
            s = stringwriter.toString();
        }
        finally
        {
            try
            {
                if (stringwriter != null)
                {
                    stringwriter.close();
                }

                if (printwriter != null)
                {
                    printwriter.close();
                }
            }
            catch (IOException ioexception) { }
        }

        return s;
    }

    public String func_55371_e()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("---- Minecraft Crash Report ----\n");
        stringbuilder.append("// ");
        stringbuilder.append(func_55367_g());
        stringbuilder.append("\n\n");
        stringbuilder.append("Time: ");
        stringbuilder.append((new SimpleDateFormat()).format(new Date()));
        stringbuilder.append("\n");
        stringbuilder.append("Description: ");
        stringbuilder.append(field_55376_a);
        stringbuilder.append("\n\n");
        stringbuilder.append(func_55362_d());
        stringbuilder.append("\n");
        stringbuilder.append("Relevant Details:");
        stringbuilder.append("\n");
        func_55368_a(stringbuilder);
        return stringbuilder.toString();
    }

    public File func_57261_f()
    {
        return field_57262_d;
    }

    public boolean func_55370_a(File par1File)
    {
        if (field_57262_d != null)
        {
            return false;
        }

        if (par1File.getParentFile() != null)
        {
            par1File.getParentFile().mkdirs();
        }

        try
        {
            FileWriter filewriter = new FileWriter(par1File);
            filewriter.write(func_55371_e());
            filewriter.close();
            field_57262_d = par1File;
            return true;
        }
        catch (Throwable throwable)
        {
            Logger.getLogger("Minecraft").log(Level.SEVERE, (new StringBuilder()).append("Could not save crash report to ").append(par1File).toString(), throwable);
        }

        return false;
    }

    private static String func_55367_g()
    {
        String as[] =
        {
            "Who set us up the TNT?", "Everything's going to plan. No, really, that was supposed to happen.", "Uh... Did I do that?", "Oops.", "Why did you do that?", "I feel sad now :(", "My bad.", "I'm sorry, Dave.", "I let you down. Sorry :(", "On the bright side, I bought you a teddy bear!",
            "Daisy, daisy...", "Oh - I know what I did wrong!", "Hey, that tickles! Hehehe!", "I blame Dinnerbone.", "You should try our sister game, Minceraft!", "Don't be sad. I'll do better next time, I promise!", "Don't be sad, have a hug! <3", "I just don't know what went wrong :(", "Shall we play a game?", "Quite honestly, I wouldn't worry myself about that.",
            "I bet Cylons wouldn't have this problem.", "Sorry :(", "Surprise! Haha. Well, this is awkward.", "Would you like a cupcake?", "Hi. I'm Minecraft, and I'm a crashaholic.", "Ooh. Shiny."
        };

        try
        {
            return as[(int)(System.nanoTime() % (long)as.length)];
        }
        catch (Throwable throwable)
        {
            return "Witty comment unavailable :(";
        }
    }
}
