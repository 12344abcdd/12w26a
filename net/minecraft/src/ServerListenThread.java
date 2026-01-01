package net.minecraft.src;

import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerListenThread extends Thread
{
    private static Logger field_57009_a = Logger.getLogger("Minecraft");
    private final List field_57007_b = Collections.synchronizedList(new ArrayList());
    private final HashMap field_57008_c = new HashMap();
    private int field_57005_d;
    private final ServerSocket field_57006_e;
    private NetworkListenThread field_57003_f;
    private final InetAddress field_57004_g;
    private final int field_57010_h;

    public ServerListenThread(NetworkListenThread par1NetworkListenThread, InetAddress par2InetAddress, int par3) throws IOException
    {
        super("Listen thread");
        field_57005_d = 0;
        field_57003_f = par1NetworkListenThread;
        field_57004_g = par2InetAddress;
        field_57010_h = par3;
        field_57006_e = new ServerSocket(par3, 0, par2InetAddress);
        field_57006_e.setPerformancePreferences(0, 2, 1);
    }

    public void func_56998_a()
    {
        synchronized (field_57007_b)
        {
            for (int i = 0; i < field_57007_b.size(); i++)
            {
                NetLoginHandler netloginhandler = (NetLoginHandler)field_57007_b.get(i);

                try
                {
                    netloginhandler.func_56747_b();
                }
                catch (Exception exception)
                {
                    netloginhandler.func_56744_a("Internal server error");
                    field_57009_a.log(Level.WARNING, (new StringBuilder()).append("Failed to handle packet: ").append(exception).toString(), exception);
                }

                if (netloginhandler.field_56755_c)
                {
                    field_57007_b.remove(i--);
                }

                netloginhandler.field_56754_b.func_55345_a();
            }
        }
    }

    public void run()
    {
        do
        {
            if (!field_57003_f.field_56963_b)
            {
                break;
            }

            try
            {
                Socket socket = field_57006_e.accept();
                InetAddress inetaddress = socket.getInetAddress();
                long l = System.currentTimeMillis();

                synchronized (field_57008_c)
                {
                    if (field_57008_c.containsKey(inetaddress) && !func_57002_a(inetaddress) && l - ((Long)field_57008_c.get(inetaddress)).longValue() < 4000L)
                    {
                        field_57008_c.put(inetaddress, Long.valueOf(l));
                        socket.close();
                        continue;
                    }

                    field_57008_c.put(inetaddress, Long.valueOf(l));
                }

                NetLoginHandler netloginhandler = new NetLoginHandler(field_57003_f.func_56959_c(), socket, (new StringBuilder()).append("Connection #").append(field_57005_d++).toString());
                func_56997_a(netloginhandler);
            }
            catch (IOException ioexception)
            {
                ioexception.printStackTrace();
            }
        }
        while (true);

        System.out.println("Closing listening thread");
    }

    private void func_56997_a(NetLoginHandler par1NetLoginHandler)
    {
        if (par1NetLoginHandler == null)
        {
            throw new IllegalArgumentException("Got null pendingconnection!");
        }

        synchronized (field_57007_b)
        {
            field_57007_b.add(par1NetLoginHandler);
        }
    }

    private static boolean func_57002_a(InetAddress par0InetAddress)
    {
        return "127.0.0.1".equals(par0InetAddress.getHostAddress());
    }

    public void func_58116_a(InetAddress par1InetAddress)
    {
        if (par1InetAddress != null)
        {
            synchronized (field_57008_c)
            {
                field_57008_c.remove(par1InetAddress);
            }
        }
    }

    public void func_57001_b()
    {
        try
        {
            field_57006_e.close();
        }
        catch (Throwable throwable) { }
    }

    public InetAddress func_56999_c()
    {
        return field_57004_g;
    }

    public int func_56996_d()
    {
        return field_57010_h;
    }
}
