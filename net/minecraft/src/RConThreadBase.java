package net.minecraft.src;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.*;

public abstract class RConThreadBase implements Runnable
{
    protected boolean field_56668_a;
    protected IServer field_56666_b;
    protected Thread field_56667_c;
    protected int field_56664_d;
    protected List field_56665_e;
    protected List field_56663_f;

    RConThreadBase(IServer par1IServer)
    {
        field_56668_a = false;
        field_56664_d = 5;
        field_56665_e = new ArrayList();
        field_56663_f = new ArrayList();
        field_56666_b = par1IServer;

        if (field_56666_b.func_56350_A())
        {
            func_56654_c("Debugging is enabled, performance maybe reduced!");
        }
    }

    public synchronized void func_56651_a()
    {
        field_56667_c = new Thread(this);
        field_56667_c.start();
        field_56668_a = true;
    }

    public boolean func_56662_b()
    {
        return field_56668_a;
    }

    protected void func_56655_a(String par1Str)
    {
        field_56666_b.func_56324_j(par1Str);
    }

    protected void func_56657_b(String par1Str)
    {
        field_56666_b.func_56337_f(par1Str);
    }

    protected void func_56654_c(String par1Str)
    {
        field_56666_b.func_56311_g(par1Str);
    }

    protected void func_56658_d(String par1Str)
    {
        field_56666_b.func_56320_i(par1Str);
    }

    protected int func_56656_c()
    {
        return field_56666_b.func_56360_v();
    }

    protected void func_56650_a(DatagramSocket par1DatagramSocket)
    {
        func_56655_a((new StringBuilder()).append("registerSocket: ").append(par1DatagramSocket).toString());
        field_56665_e.add(par1DatagramSocket);
    }

    protected boolean func_56652_a(DatagramSocket par1DatagramSocket, boolean par2)
    {
        func_56655_a((new StringBuilder()).append("closeSocket: ").append(par1DatagramSocket).toString());

        if (null == par1DatagramSocket)
        {
            return false;
        }

        boolean flag = false;

        if (!par1DatagramSocket.isClosed())
        {
            par1DatagramSocket.close();
            flag = true;
        }

        if (par2)
        {
            field_56665_e.remove(par1DatagramSocket);
        }

        return flag;
    }

    protected boolean func_56660_a(ServerSocket par1ServerSocket)
    {
        return func_56653_a(par1ServerSocket, true);
    }

    protected boolean func_56653_a(ServerSocket par1ServerSocket, boolean par2)
    {
        func_56655_a((new StringBuilder()).append("closeSocket: ").append(par1ServerSocket).toString());

        if (null == par1ServerSocket)
        {
            return false;
        }

        boolean flag = false;

        try
        {
            if (!par1ServerSocket.isClosed())
            {
                par1ServerSocket.close();
                flag = true;
            }
        }
        catch (IOException ioexception)
        {
            func_56654_c((new StringBuilder()).append("IO: ").append(ioexception.getMessage()).toString());
        }

        if (par2)
        {
            field_56663_f.remove(par1ServerSocket);
        }

        return flag;
    }

    protected void func_56659_d()
    {
        func_56661_a(false);
    }

    protected void func_56661_a(boolean par1)
    {
        int i = 0;
        Iterator iterator = field_56665_e.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            DatagramSocket datagramsocket = (DatagramSocket)iterator.next();

            if (func_56652_a(datagramsocket, false))
            {
                i++;
            }
        }
        while (true);

        field_56665_e.clear();
        iterator = field_56663_f.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            ServerSocket serversocket = (ServerSocket)iterator.next();

            if (func_56653_a(serversocket, false))
            {
                i++;
            }
        }
        while (true);

        field_56663_f.clear();

        if (par1 && 0 < i)
        {
            func_56654_c((new StringBuilder()).append("Force closed ").append(i).append(" sockets").toString());
        }
    }
}
