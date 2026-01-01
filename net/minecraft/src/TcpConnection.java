package net.minecraft.src;

import java.io.*;
import java.net.*;
import java.security.PrivateKey;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import javax.crypto.SecretKey;

public class TcpConnection implements NetworkManager
{
    public static AtomicInteger field_57298_a = new AtomicInteger();
    public static AtomicInteger field_57296_b = new AtomicInteger();
    private Object field_57305_h;
    private Socket field_57306_i;
    private final SocketAddress field_57303_j;
    private volatile DataInputStream field_57304_k;
    private volatile DataOutputStream field_57301_l;
    private volatile boolean field_57302_m;
    private volatile boolean field_57299_n;
    private List field_57300_o;
    private List field_57314_p;
    private List field_57313_q;
    private NetHandler field_57312_r;
    private boolean field_57311_s;
    private Thread field_57310_t;
    private Thread field_57309_u;
    private String field_57308_v;
    private Object field_57307_w[];
    private int field_57317_x;
    private int field_57316_y;
    public static int field_57297_c[] = new int[256];
    public static int field_57294_d[] = new int[256];
    public int field_57295_e;
    boolean field_57292_f;
    boolean field_57293_g;
    private SecretKey field_57315_z;
    private PrivateKey field_57290_A;
    private int field_57291_B;

    public TcpConnection(Socket par1Socket, String par2Str, NetHandler par3NetHandler) throws IOException
    {
        this(par1Socket, par2Str, par3NetHandler, null);
    }

    public TcpConnection(Socket par1Socket, String par2Str, NetHandler par3NetHandler, PrivateKey par4PrivateKey) throws IOException
    {
        field_57305_h = new Object();
        field_57302_m = true;
        field_57299_n = false;
        field_57300_o = Collections.synchronizedList(new ArrayList());
        field_57314_p = Collections.synchronizedList(new ArrayList());
        field_57313_q = Collections.synchronizedList(new ArrayList());
        field_57311_s = false;
        field_57308_v = "";
        field_57317_x = 0;
        field_57316_y = 0;
        field_57295_e = 0;
        field_57292_f = false;
        field_57293_g = false;
        field_57315_z = null;
        field_57290_A = null;
        field_57291_B = 50;
        field_57290_A = par4PrivateKey;
        field_57306_i = par1Socket;
        field_57303_j = par1Socket.getRemoteSocketAddress();
        field_57312_r = par3NetHandler;

        try
        {
            par1Socket.setSoTimeout(30000);
            par1Socket.setTrafficClass(24);
        }
        catch (SocketException socketexception)
        {
            System.err.println(socketexception.getMessage());
        }

        field_57304_k = new DataInputStream(par1Socket.getInputStream());
        field_57301_l = new DataOutputStream(new BufferedOutputStream(par1Socket.getOutputStream(), 5120));
        field_57309_u = new TcpReaderThread(this, (new StringBuilder()).append(par2Str).append(" read thread").toString());
        field_57310_t = new TcpWriterThread(this, (new StringBuilder()).append(par2Str).append(" write thread").toString());
        field_57309_u.start();
        field_57310_t.start();
    }

    public void func_57274_f()
    {
        func_55345_a();
        field_57310_t = null;
        field_57309_u = null;
    }

    public void func_57273_a(NetHandler par1NetHandler)
    {
        field_57312_r = par1NetHandler;
    }

    /**
     * Adds the packet to the correct send queue (chunk data packets go to a separate queue).
     */
    public void addToSendQueue(Packet par1Packet)
    {
        if (field_57311_s)
        {
            return;
        }

        synchronized (field_57305_h)
        {
            field_57316_y += par1Packet.getPacketSize() + 1;

            if (par1Packet.isChunkDataPacket)
            {
                field_57313_q.add(par1Packet);
            }
            else
            {
                field_57314_p.add(par1Packet);
            }
        }
    }

    private boolean func_57276_h()
    {
        boolean flag = false;

        try
        {
            if (!field_57314_p.isEmpty() && (field_57295_e == 0 || System.currentTimeMillis() - ((Packet)field_57314_p.get(0)).creationTimeMillis >= (long)field_57295_e))
            {
                Packet packet;

                synchronized (field_57305_h)
                {
                    packet = (Packet)field_57314_p.remove(0);
                    field_57316_y -= packet.getPacketSize() + 1;
                }

                Packet.writePacket(packet, field_57301_l);

                if ((packet instanceof Packet252SharedKey) && !field_57293_g)
                {
                    if (!field_57312_r.isServerHandler())
                    {
                        field_57315_z = ((Packet252SharedKey)packet).func_55162_b();
                    }

                    func_57275_k();
                }

                field_57294_d[packet.getPacketId()] += packet.getPacketSize() + 1;
                flag = true;
            }

            if (field_57291_B-- <= 0 && !field_57313_q.isEmpty() && (field_57295_e == 0 || System.currentTimeMillis() - ((Packet)field_57313_q.get(0)).creationTimeMillis >= (long)field_57295_e))
            {
                Packet packet1;

                synchronized (field_57305_h)
                {
                    packet1 = (Packet)field_57313_q.remove(0);
                    field_57316_y -= packet1.getPacketSize() + 1;
                }

                Packet.writePacket(packet1, field_57301_l);
                field_57294_d[packet1.getPacketId()] += packet1.getPacketSize() + 1;
                field_57291_B = 0;
                flag = true;
            }
        }
        catch (Exception exception)
        {
            if (!field_57299_n)
            {
                func_57288_a(exception);
            }

            return false;
        }

        return flag;
    }

    public void func_55345_a()
    {
        if (field_57309_u != null)
        {
            field_57309_u.interrupt();
        }

        if (field_57310_t != null)
        {
            field_57310_t.interrupt();
        }
    }

    private boolean func_57278_i()
    {
        boolean flag = false;

        try
        {
            Packet packet = Packet.readPacket(field_57304_k, field_57312_r.isServerHandler());

            if (packet != null)
            {
                if ((packet instanceof Packet252SharedKey) && !field_57292_f)
                {
                    if (field_57312_r.isServerHandler())
                    {
                        field_57315_z = ((Packet252SharedKey)packet).func_55163_a(field_57290_A);
                    }

                    func_57279_j();
                }

                field_57297_c[packet.getPacketId()] += packet.getPacketSize() + 1;

                if (!field_57311_s)
                {
                    field_57300_o.add(packet);
                }

                flag = true;
            }
            else
            {
                networkShutdown("disconnect.endOfStream", new Object[0]);
            }
        }
        catch (Exception exception)
        {
            if (!field_57299_n)
            {
                func_57288_a(exception);
            }

            return false;
        }

        return flag;
    }

    private void func_57288_a(Exception par1Exception)
    {
        par1Exception.printStackTrace();
        networkShutdown("disconnect.genericReason", new Object[]
                {
                    (new StringBuilder()).append("Internal exception: ").append(par1Exception.toString()).toString()
                });
    }

    /**
     * Shuts down the network with the specified reason. Closes all streams and sockets, spawns NetworkMasterThread to
     * stop reading and writing threads.
     */
    public void networkShutdown(String par1Str, Object par2ArrayOfObj[])
    {
        if (!field_57302_m)
        {
            return;
        }

        field_57299_n = true;
        field_57308_v = par1Str;
        field_57307_w = par2ArrayOfObj;
        field_57302_m = false;
        (new TcpMasterThread(this)).start();

        try
        {
            field_57304_k.close();
            field_57304_k = null;
            field_57301_l.close();
            field_57301_l = null;
            field_57306_i.close();
            field_57306_i = null;
        }
        catch (Throwable throwable) { }
    }

    /**
     * Checks timeouts and processes all pending read packets.
     */
    public void processReadPackets()
    {
        if (field_57316_y > 0x100000)
        {
            networkShutdown("disconnect.overflow", new Object[0]);
        }

        if (field_57300_o.isEmpty())
        {
            if (field_57317_x++ == 1200)
            {
                networkShutdown("disconnect.timeout", new Object[0]);
            }
        }
        else
        {
            field_57317_x = 0;
        }

        Packet packet;

        for (int i = 1000; !field_57300_o.isEmpty() && i-- >= 0; packet.processPacket(field_57312_r))
        {
            packet = (Packet)field_57300_o.remove(0);
        }

        func_55345_a();

        if (field_57299_n && field_57300_o.isEmpty())
        {
            field_57312_r.handleErrorMessage(field_57308_v, field_57307_w);
        }
    }

    public SocketAddress func_57272_c()
    {
        return field_57303_j;
    }

    /**
     * Shuts down the server. (Only actually used on the server)
     */
    public void serverShutdown()
    {
        if (field_57311_s)
        {
            return;
        }
        else
        {
            func_55345_a();
            field_57311_s = true;
            field_57309_u.interrupt();
            (new TcpMonitorThread(this)).start();
            return;
        }
    }

    private void func_57279_j() throws IOException
    {
        field_57292_f = true;
        field_57304_k = new DataInputStream(CryptManager.func_55330_a(field_57315_z, field_57306_i.getInputStream()));
    }

    private void func_57275_k() throws IOException
    {
        field_57301_l.flush();
        field_57293_g = true;
        field_57301_l = new DataOutputStream(new BufferedOutputStream(CryptManager.func_55333_a(field_57315_z, field_57306_i.getOutputStream()), 5120));
    }

    public int func_57271_e()
    {
        return field_57313_q.size();
    }

    public Socket func_57284_g()
    {
        return field_57306_i;
    }

    static boolean func_57280_a(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.field_57302_m;
    }

    static boolean func_57281_b(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.field_57311_s;
    }

    static boolean func_57286_c(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.func_57278_i();
    }

    static boolean func_57287_d(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.func_57276_h();
    }

    static DataOutputStream func_57283_e(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.field_57301_l;
    }

    static boolean func_57285_f(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.field_57299_n;
    }

    static void func_57277_a(TcpConnection par0TcpConnection, Exception par1Exception)
    {
        par0TcpConnection.func_57288_a(par1Exception);
    }

    static Thread func_57282_g(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.field_57309_u;
    }

    static Thread func_57289_h(TcpConnection par0TcpConnection)
    {
        return par0TcpConnection.field_57310_t;
    }
}
