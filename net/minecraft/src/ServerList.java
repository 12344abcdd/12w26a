package net.minecraft.src;

import java.io.File;
import java.util.*;
import net.minecraft.client.Minecraft;

public class ServerList
{
    private final Minecraft field_57182_a;
    private final List field_57181_b = new ArrayList();

    public ServerList(Minecraft par1Minecraft)
    {
        field_57182_a = par1Minecraft;
        func_57174_a();
    }

    public void func_57174_a()
    {
        try
        {
            NBTTagCompound nbttagcompound = CompressedStreamTools.read(new File(field_57182_a.mcDataDir, "servers.dat"));
            NBTTagList nbttaglist = nbttagcompound.getTagList("servers");
            field_57181_b.clear();

            for (int i = 0; i < nbttaglist.tagCount(); i++)
            {
                field_57181_b.add(ServerData.func_57439_a((NBTTagCompound)nbttaglist.tagAt(i)));
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void func_57176_b()
    {
        try
        {
            NBTTagList nbttaglist = new NBTTagList();
            ServerData serverdata;

            for (Iterator iterator = field_57181_b.iterator(); iterator.hasNext(); nbttaglist.appendTag(serverdata.func_57442_a()))
            {
                serverdata = (ServerData)iterator.next();
            }

            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setTag("servers", nbttaglist);
            CompressedStreamTools.safeWrite(nbttagcompound, new File(field_57182_a.mcDataDir, "servers.dat"));
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public ServerData func_57180_a(int par1)
    {
        return (ServerData)field_57181_b.get(par1);
    }

    public void func_57173_b(int par1)
    {
        field_57181_b.remove(par1);
    }

    public void func_57175_a(ServerData par1ServerData)
    {
        field_57181_b.add(par1ServerData);
    }

    public int func_57178_c()
    {
        return field_57181_b.size();
    }

    public void func_57179_a(int par1, int par2)
    {
        ServerData serverdata = func_57180_a(par1);
        field_57181_b.set(par1, func_57180_a(par2));
        field_57181_b.set(par2, serverdata);
    }

    public void func_57177_a(int par1, ServerData par2ServerData)
    {
        field_57181_b.set(par1, par2ServerData);
    }

    public static void func_57172_b(ServerData par0ServerData)
    {
        ServerList serverlist = new ServerList(Minecraft.func_55068_z());
        serverlist.func_57174_a();
        int i = 0;

        do
        {
            if (i >= serverlist.func_57178_c())
            {
                break;
            }

            ServerData serverdata = serverlist.func_57180_a(i);

            if (serverdata.field_57449_a.equals(par0ServerData.field_57449_a) && serverdata.field_57447_b.equals(par0ServerData.field_57447_b))
            {
                serverlist.func_57177_a(i, par0ServerData);
                break;
            }

            i++;
        }
        while (true);

        serverlist.func_57176_b();
    }
}
