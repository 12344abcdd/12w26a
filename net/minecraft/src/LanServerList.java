package net.minecraft.src;

import java.net.InetAddress;
import java.util.*;

public class LanServerList
{
    private ArrayList field_58149_b;
    boolean field_58150_a;

    public LanServerList()
    {
        field_58149_b = new ArrayList();
    }

    public synchronized boolean func_58147_a()
    {
        return field_58150_a;
    }

    public synchronized void func_58146_b()
    {
        field_58150_a = false;
    }

    public synchronized List func_58148_c()
    {
        return Collections.unmodifiableList(field_58149_b);
    }

    public synchronized void func_58145_a(String par1Str, InetAddress par2InetAddress)
    {
        String s = ThreadLanServerPing.func_58087_a(par1Str);
        String s1 = ThreadLanServerPing.func_58086_b(par1Str);

        if (s1 == null)
        {
            return;
        }

        boolean flag = false;
        Iterator iterator = field_58149_b.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            LanServer lanserver = (LanServer)iterator.next();

            if (!lanserver.func_58161_b().equals(s1))
            {
                continue;
            }

            lanserver.func_58162_c();
            flag = true;
            break;
        }
        while (true);

        if (!flag)
        {
            field_58149_b.add(new LanServer(s, s1));
            field_58150_a = true;
        }
    }
}
