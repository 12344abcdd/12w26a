package net.minecraft.src;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class BanEntry
{
    public static final SimpleDateFormat field_57029_a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
    public static Logger field_57027_b = Logger.getLogger("Minecraft");
    private final String field_57028_c;
    private Date field_57025_d;
    private String field_57026_e;
    private Date field_57023_f;
    private String field_57024_g;

    public BanEntry(String par1Str)
    {
        field_57025_d = new Date();
        field_57026_e = "(Unknown)";
        field_57023_f = null;
        field_57024_g = "Banned by an operator.";
        field_57028_c = par1Str;
    }

    public String func_57016_a()
    {
        return field_57028_c;
    }

    public Date func_57015_b()
    {
        return field_57025_d;
    }

    public void func_57012_a(Date par1Date)
    {
        field_57025_d = par1Date == null ? new Date() : par1Date;
    }

    public String func_57021_c()
    {
        return field_57026_e;
    }

    public void func_57019_a(String par1Str)
    {
        field_57026_e = par1Str;
    }

    public Date func_57011_d()
    {
        return field_57023_f;
    }

    public void func_57022_b(Date par1Date)
    {
        field_57023_f = par1Date;
    }

    public boolean func_57014_e()
    {
        if (field_57023_f == null)
        {
            return false;
        }
        else
        {
            return field_57023_f.before(new Date());
        }
    }

    public String func_57018_f()
    {
        return field_57024_g;
    }

    public void func_57020_b(String par1Str)
    {
        field_57024_g = par1Str;
    }

    public String func_57017_g()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(func_57016_a());
        stringbuilder.append("|");
        stringbuilder.append(field_57029_a.format(func_57015_b()));
        stringbuilder.append("|");
        stringbuilder.append(func_57021_c());
        stringbuilder.append("|");
        stringbuilder.append(func_57011_d() != null ? field_57029_a.format(func_57011_d()) : "Forever");
        stringbuilder.append("|");
        stringbuilder.append(func_57018_f());
        return stringbuilder.toString();
    }

    public static BanEntry func_57013_c(String par0Str)
    {
        if (par0Str.trim().length() < 2)
        {
            return null;
        }

        String as[] = par0Str.trim().split(Pattern.quote("|"), 5);
        BanEntry banentry = new BanEntry(as[0].trim());
        int i = 0;

        if (as.length <= ++i)
        {
            return banentry;
        }

        try
        {
            banentry.func_57012_a(field_57029_a.parse(as[i].trim()));
        }
        catch (ParseException parseexception)
        {
            field_57027_b.log(Level.WARNING, (new StringBuilder()).append("Could not read creation date format for ban entry '").append(banentry.func_57016_a()).append("' (was: '").append(as[i]).append("')").toString(), parseexception);
        }

        if (as.length <= ++i)
        {
            return banentry;
        }

        banentry.func_57019_a(as[i].trim());

        if (as.length <= ++i)
        {
            return banentry;
        }

        try
        {
            String s = as[i].trim();

            if (!s.equalsIgnoreCase("Forever") && s.length() > 0)
            {
                banentry.func_57022_b(field_57029_a.parse(s));
            }
        }
        catch (ParseException parseexception1)
        {
            field_57027_b.log(Level.WARNING, (new StringBuilder()).append("Could not read expiry date format for ban entry '").append(banentry.func_57016_a()).append("' (was: '").append(as[i]).append("')").toString(), parseexception1);
        }

        if (as.length <= ++i)
        {
            return banentry;
        }
        else
        {
            banentry.func_57020_b(as[i].trim());
            return banentry;
        }
    }
}
