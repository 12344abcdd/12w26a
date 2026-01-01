package net.minecraft.src;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.*;

public class MemoryConnection implements NetworkManager
{
    private static final SocketAddress field_57329_a = new InetSocketAddress("127.0.0.1", 0);
    private final List field_57327_b = Collections.synchronizedList(new ArrayList());
    private MemoryConnection field_57328_c;
    private NetHandler field_57325_d;
    private boolean field_57326_e;
    private String field_57323_f;
    private Object field_57324_g[];
    private boolean field_57330_h;

    public MemoryConnection(NetHandler par1NetHandler) throws IOException
    {
        field_57326_e = false;
        field_57323_f = "";
        field_57330_h = false;
        field_57325_d = par1NetHandler;
    }

    public void func_57273_a(NetHandler par1NetHandler)
    {
        field_57325_d = par1NetHandler;
    }

    /**
     * Adds the packet to the correct send queue (chunk data packets go to a separate queue).
     */
    public void addToSendQueue(Packet par1Packet)
    {
        if (field_57326_e)
        {
            return;
        }
        else
        {
            field_57328_c.field_57327_b.add(par1Packet);
            return;
        }
    }

    public void func_57274_f()
    {
        field_57328_c = null;
        field_57325_d = null;
    }

    public boolean func_57320_g()
    {
        return !field_57326_e && field_57328_c != null;
    }

    public void func_55345_a()
    {
    }

    /**
     * Checks timeouts and processes all pending read packets.
     */
    public void processReadPackets()
    {
        Packet packet;

        for (int i = 1000; !field_57327_b.isEmpty() && i-- >= 0; packet.processPacket(field_57325_d))
        {
            packet = (Packet)field_57327_b.remove(0);
        }

        if (field_57326_e && field_57327_b.isEmpty())
        {
            field_57325_d.handleErrorMessage(field_57323_f, field_57324_g);
        }
    }

    public SocketAddress func_57272_c()
    {
        return field_57329_a;
    }

    /**
     * Shuts down the server. (Only actually used on the server)
     */
    public void serverShutdown()
    {
        field_57326_e = true;
    }

    /**
     * Shuts down the network with the specified reason. Closes all streams and sockets, spawns NetworkMasterThread to
     * stop reading and writing threads.
     */
    public void networkShutdown(String par1Str, Object par2ArrayOfObj[])
    {
        field_57326_e = true;
        field_57323_f = par1Str;
        field_57324_g = par2ArrayOfObj;
    }

    public int func_57271_e()
    {
        return 0;
    }

    public void func_57319_a(MemoryConnection par1MemoryConnection)
    {
        field_57328_c = par1MemoryConnection;
        par1MemoryConnection.field_57328_c = this;
    }

    public boolean func_57318_h()
    {
        return field_57330_h;
    }

    public void func_57322_a(boolean par1)
    {
        field_57330_h = par1;
    }

    public MemoryConnection func_57321_i()
    {
        return field_57328_c;
    }
}
