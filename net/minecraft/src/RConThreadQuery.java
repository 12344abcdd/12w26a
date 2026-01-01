package net.minecraft.src;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class RConThreadQuery extends RConThreadBase
{
    private long field_56686_g;
    private int field_56693_h;
    private int field_56694_i;
    private int field_56691_j;
    private String field_56692_k;
    private String field_56689_l;
    private DatagramSocket field_56690_m;
    private byte field_56687_n[];
    private DatagramPacket field_56688_o;
    private Map field_56701_p;
    private String field_56700_q;
    private String field_56699_r;
    private Map field_56698_s;
    private long field_56697_t;
    private RConOutputStream field_56696_u;
    private long field_56695_v;

    public RConThreadQuery(IServer par1IServer)
    {
        super(par1IServer);
        field_56690_m = null;
        field_56687_n = new byte[1460];
        field_56688_o = null;
        field_56693_h = par1IServer.func_56424_b("query.port", 0);
        field_56699_r = par1IServer.func_56288_r();
        field_56694_i = par1IServer.func_56299_s();
        field_56692_k = par1IServer.func_56367_t();
        field_56691_j = par1IServer.func_56308_w();
        field_56689_l = par1IServer.func_58029_J();
        field_56695_v = 0L;
        field_56700_q = "0.0.0.0";

        if (0 == field_56699_r.length() || field_56700_q.equals(field_56699_r))
        {
            field_56699_r = "0.0.0.0";

            try
            {
                InetAddress inetaddress = InetAddress.getLocalHost();
                field_56700_q = inetaddress.getHostAddress();
            }
            catch (UnknownHostException unknownhostexception)
            {
                func_56654_c((new StringBuilder()).append("Unable to determine local host IP, please set server-ip in '").append(par1IServer.func_56421_ah()).append("' : ").append(unknownhostexception.getMessage()).toString());
            }
        }
        else
        {
            field_56700_q = field_56699_r;
        }

        if (0 == field_56693_h)
        {
            field_56693_h = field_56694_i;
            func_56657_b((new StringBuilder()).append("Setting default query port to ").append(field_56693_h).toString());
            par1IServer.func_56423_a("query.port", Integer.valueOf(field_56693_h));
            par1IServer.func_56423_a("debug", Boolean.valueOf(false));
            par1IServer.func_56422_ag();
        }

        field_56701_p = new HashMap();
        field_56696_u = new RConOutputStream(1460);
        field_56698_s = new HashMap();
        field_56697_t = (new Date()).getTime();
    }

    private void func_56677_a(byte par1ArrayOfByte[], DatagramPacket par2DatagramPacket) throws IOException
    {
        field_56690_m.send(new DatagramPacket(par1ArrayOfByte, par1ArrayOfByte.length, par2DatagramPacket.getSocketAddress()));
    }

    private boolean func_56679_a(DatagramPacket par1DatagramPacket) throws IOException
    {
        byte abyte0[] = par1DatagramPacket.getData();
        int i = par1DatagramPacket.getLength();
        SocketAddress socketaddress = par1DatagramPacket.getSocketAddress();
        func_56655_a((new StringBuilder()).append("Packet len ").append(i).append(" [").append(socketaddress).append("]").toString());

        if (3 > i || -2 != abyte0[0] || -3 != abyte0[1])
        {
            func_56655_a((new StringBuilder()).append("Invalid packet [").append(socketaddress).append("]").toString());
            return false;
        }

        func_56655_a((new StringBuilder()).append("Packet '").append(RConUtils.func_57423_a(abyte0[2])).append("' [").append(socketaddress).append("]").toString());

        switch (abyte0[2])
        {
            case 9:
                func_56680_d(par1DatagramPacket);
                func_56655_a((new StringBuilder()).append("Challenge [").append(socketaddress).append("]").toString());
                return true;

            case 0:
                if (!func_56685_c(par1DatagramPacket).booleanValue())
                {
                    func_56655_a((new StringBuilder()).append("Invalid challenge [").append(socketaddress).append("]").toString());
                    return false;
                }

                if (15 == i)
                {
                    func_56677_a(func_56683_b(par1DatagramPacket), par1DatagramPacket);
                    func_56655_a((new StringBuilder()).append("Rules [").append(socketaddress).append("]").toString());
                }
                else
                {
                    RConOutputStream rconoutputstream = new RConOutputStream(1460);
                    rconoutputstream.func_56880_a(0);
                    rconoutputstream.func_56883_a(func_56684_a(par1DatagramPacket.getSocketAddress()));
                    rconoutputstream.func_56884_a(field_56692_k);
                    rconoutputstream.func_56884_a("SMP");
                    rconoutputstream.func_56884_a(field_56689_l);
                    rconoutputstream.func_56884_a(Integer.toString(func_56656_c()));
                    rconoutputstream.func_56884_a(Integer.toString(field_56691_j));
                    rconoutputstream.func_56881_a((short)field_56694_i);
                    rconoutputstream.func_56884_a(field_56700_q);
                    func_56677_a(rconoutputstream.func_56885_a(), par1DatagramPacket);
                    func_56655_a((new StringBuilder()).append("Status [").append(socketaddress).append("]").toString());
                }

                break;
        }

        return true;
    }

    private byte[] func_56683_b(DatagramPacket par1DatagramPacket) throws IOException
    {
        long l = System.currentTimeMillis();

        if (l < field_56695_v + 5000L)
        {
            byte abyte0[] = field_56696_u.func_56885_a();
            byte abyte1[] = func_56684_a(par1DatagramPacket.getSocketAddress());
            abyte0[1] = abyte1[0];
            abyte0[2] = abyte1[1];
            abyte0[3] = abyte1[2];
            abyte0[4] = abyte1[3];
            return abyte0;
        }

        field_56695_v = l;
        field_56696_u.func_56882_b();
        field_56696_u.func_56880_a(0);
        field_56696_u.func_56883_a(func_56684_a(par1DatagramPacket.getSocketAddress()));
        field_56696_u.func_56884_a("splitnum");
        field_56696_u.func_56880_a(128);
        field_56696_u.func_56880_a(0);
        field_56696_u.func_56884_a("hostname");
        field_56696_u.func_56884_a(field_56692_k);
        field_56696_u.func_56884_a("gametype");
        field_56696_u.func_56884_a("SMP");
        field_56696_u.func_56884_a("game_id");
        field_56696_u.func_56884_a("MINECRAFT");
        field_56696_u.func_56884_a("version");
        field_56696_u.func_56884_a(field_56666_b.func_56293_u());
        field_56696_u.func_56884_a("plugins");
        field_56696_u.func_56884_a(field_56666_b.func_56317_z());
        field_56696_u.func_56884_a("map");
        field_56696_u.func_56884_a(field_56689_l);
        field_56696_u.func_56884_a("numplayers");
        field_56696_u.func_56884_a((new StringBuilder()).append("").append(func_56656_c()).toString());
        field_56696_u.func_56884_a("maxplayers");
        field_56696_u.func_56884_a((new StringBuilder()).append("").append(field_56691_j).toString());
        field_56696_u.func_56884_a("hostport");
        field_56696_u.func_56884_a((new StringBuilder()).append("").append(field_56694_i).toString());
        field_56696_u.func_56884_a("hostip");
        field_56696_u.func_56884_a(field_56700_q);
        field_56696_u.func_56880_a(0);
        field_56696_u.func_56880_a(1);
        field_56696_u.func_56884_a("player_");
        field_56696_u.func_56880_a(0);
        String as[] = field_56666_b.func_56332_x();
        byte byte0 = (byte)as.length;

        for (byte byte1 = (byte)(byte0 - 1); byte1 >= 0; byte1--)
        {
            field_56696_u.func_56884_a(as[byte1]);
        }

        field_56696_u.func_56880_a(0);
        return field_56696_u.func_56885_a();
    }

    private byte[] func_56684_a(SocketAddress par1SocketAddress)
    {
        return ((RConThreadQueryAuth)field_56698_s.get(par1SocketAddress)).func_56631_c();
    }

    private Boolean func_56685_c(DatagramPacket par1DatagramPacket)
    {
        SocketAddress socketaddress = par1DatagramPacket.getSocketAddress();

        if (!field_56698_s.containsKey(socketaddress))
        {
            return Boolean.valueOf(false);
        }

        byte abyte0[] = par1DatagramPacket.getData();

        if (((RConThreadQueryAuth)field_56698_s.get(socketaddress)).func_56632_a() != RConUtils.func_57424_c(abyte0, 7, par1DatagramPacket.getLength()))
        {
            return Boolean.valueOf(false);
        }
        else
        {
            return Boolean.valueOf(true);
        }
    }

    private void func_56680_d(DatagramPacket par1DatagramPacket) throws IOException
    {
        RConThreadQueryAuth rconthreadqueryauth = new RConThreadQueryAuth(this, par1DatagramPacket);
        field_56698_s.put(par1DatagramPacket.getSocketAddress(), rconthreadqueryauth);
        func_56677_a(rconthreadqueryauth.func_56634_b(), par1DatagramPacket);
    }

    private void func_56681_e()
    {
        if (!field_56668_a)
        {
            return;
        }

        long l = System.currentTimeMillis();

        if (l < field_56686_g + 30000L)
        {
            return;
        }

        field_56686_g = l;
        Iterator iterator = field_56698_s.entrySet().iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();

            if (((RConThreadQueryAuth)entry.getValue()).func_56633_a(l).booleanValue())
            {
                iterator.remove();
            }
        }
        while (true);
    }

    public void run()
    {
        func_56657_b((new StringBuilder()).append("Query running on ").append(field_56699_r).append(":").append(field_56693_h).toString());
        field_56686_g = System.currentTimeMillis();
        field_56688_o = new DatagramPacket(field_56687_n, field_56687_n.length);

        try
        {
            while (field_56668_a)
            {
                try
                {
                    field_56690_m.receive(field_56688_o);
                    func_56681_e();
                    func_56679_a(field_56688_o);
                }
                catch (SocketTimeoutException sockettimeoutexception)
                {
                    func_56681_e();
                }
                catch (PortUnreachableException portunreachableexception) { }
                catch (IOException ioexception)
                {
                    func_56682_a(ioexception);
                }
            }
        }
        finally
        {
            func_56659_d();
        }
    }

    public void func_56651_a()
    {
        if (field_56668_a)
        {
            return;
        }

        if (0 >= field_56693_h || 65535 < field_56693_h)
        {
            func_56654_c((new StringBuilder()).append("Invalid query port ").append(field_56693_h).append(" found in '").append(field_56666_b.func_56421_ah()).append("' (queries disabled)").toString());
            return;
        }

        if (func_56678_f())
        {
            super.func_56651_a();
        }
    }

    private void func_56682_a(Exception par1Exception)
    {
        if (!field_56668_a)
        {
            return;
        }

        func_56654_c((new StringBuilder()).append("Unexpected exception, buggy JRE? (").append(par1Exception.toString()).append(")").toString());

        if (!func_56678_f())
        {
            func_56658_d("Failed to recover from buggy JRE, shutting down!");
            field_56668_a = false;
        }
    }

    private boolean func_56678_f()
    {
        try
        {
            field_56690_m = new DatagramSocket(field_56693_h, InetAddress.getByName(field_56699_r));
            func_56650_a(field_56690_m);
            field_56690_m.setSoTimeout(500);
            return true;
        }
        catch (SocketException socketexception)
        {
            func_56654_c((new StringBuilder()).append("Unable to initialise query system on ").append(field_56699_r).append(":").append(field_56693_h).append(" (Socket): ").append(socketexception.getMessage()).toString());
        }
        catch (UnknownHostException unknownhostexception)
        {
            func_56654_c((new StringBuilder()).append("Unable to initialise query system on ").append(field_56699_r).append(":").append(field_56693_h).append(" (Unknown Host): ").append(unknownhostexception.getMessage()).toString());
        }
        catch (Exception exception)
        {
            func_56654_c((new StringBuilder()).append("Unable to initialise query system on ").append(field_56699_r).append(":").append(field_56693_h).append(" (E): ").append(exception.getMessage()).toString());
        }

        return false;
    }
}
