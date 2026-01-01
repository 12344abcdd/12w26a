package net.minecraft.src;

import java.io.*;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

public class DedicatedPlayerList extends ServerConfigurationManager
{
    private File field_57148_e;
    private File field_57147_f;

    public DedicatedPlayerList(DedicatedServer par1DedicatedServer)
    {
        super(par1DedicatedServer);
        field_57148_e = par1DedicatedServer.func_56340_e("ops.txt");
        field_57147_f = par1DedicatedServer.func_56340_e("white-list.txt");
        field_57131_d = par1DedicatedServer.func_56424_b("view-distance", 10);
        field_57134_c = par1DedicatedServer.func_56424_b("max-players", 20);
        func_57099_a(par1DedicatedServer.func_56427_a("white-list", false));

        if (!par1DedicatedServer.func_56307_I())
        {
            func_57110_e().func_57343_a(true);
            func_57101_f().func_57343_a(true);
        }

        func_57110_e().func_57341_d();
        func_57110_e().func_57342_e();
        func_57101_f().func_57341_d();
        func_57101_f().func_57342_e();
        func_57146_s();
        func_57145_u();
        func_57142_t();
        func_57143_v();
    }

    public void func_57099_a(boolean par1)
    {
        super.func_57099_a(par1);
        func_57144_r().func_56423_a("white-list", Boolean.valueOf(par1));
        func_57144_r().func_56422_ag();
    }

    public void func_57115_b(String par1Str)
    {
        super.func_57115_b(par1Str);
        func_57142_t();
    }

    public void func_57091_c(String par1Str)
    {
        super.func_57091_c(par1Str);
        func_57142_t();
    }

    public void func_57090_h(String par1Str)
    {
        super.func_57090_h(par1Str);
        func_57143_v();
    }

    public void func_57103_g(String par1Str)
    {
        super.func_57103_g(par1Str);
        func_57143_v();
    }

    public void func_57092_j()
    {
        func_57145_u();
    }

    private void func_57146_s()
    {
        try
        {
            func_57108_i().clear();
            BufferedReader bufferedreader = new BufferedReader(new FileReader(field_57148_e));

            for (String s = ""; (s = bufferedreader.readLine()) != null;)
            {
                func_57108_i().add(s.trim().toLowerCase());
            }

            bufferedreader.close();
        }
        catch (Exception exception)
        {
            field_57135_a.warning((new StringBuilder()).append("Failed to load operators list: ").append(exception).toString());
        }
    }

    private void func_57142_t()
    {
        try
        {
            PrintWriter printwriter = new PrintWriter(new FileWriter(field_57148_e, false));
            String s;

            for (Iterator iterator = func_57108_i().iterator(); iterator.hasNext(); printwriter.println(s))
            {
                s = (String)iterator.next();
            }

            printwriter.close();
        }
        catch (Exception exception)
        {
            field_57135_a.warning((new StringBuilder()).append("Failed to save operators list: ").append(exception).toString());
        }
    }

    private void func_57145_u()
    {
        try
        {
            func_57116_h().clear();
            BufferedReader bufferedreader = new BufferedReader(new FileReader(field_57147_f));

            for (String s = ""; (s = bufferedreader.readLine()) != null;)
            {
                func_57116_h().add(s.trim().toLowerCase());
            }

            bufferedreader.close();
        }
        catch (Exception exception)
        {
            field_57135_a.warning((new StringBuilder()).append("Failed to load white-list: ").append(exception).toString());
        }
    }

    private void func_57143_v()
    {
        try
        {
            PrintWriter printwriter = new PrintWriter(new FileWriter(field_57147_f, false));
            String s;

            for (Iterator iterator = func_57116_h().iterator(); iterator.hasNext(); printwriter.println(s))
            {
                s = (String)iterator.next();
            }

            printwriter.close();
        }
        catch (Exception exception)
        {
            field_57135_a.warning((new StringBuilder()).append("Failed to save white-list: ").append(exception).toString());
        }
    }

    public boolean func_57098_d(String par1Str)
    {
        par1Str = par1Str.trim().toLowerCase();
        return !func_57109_n() || func_57087_e(par1Str) || func_57116_h().contains(par1Str);
    }

    public DedicatedServer func_57144_r()
    {
        return (DedicatedServer)super.func_57085_p();
    }

    public MinecraftServer func_57085_p()
    {
        return func_57144_r();
    }
}
