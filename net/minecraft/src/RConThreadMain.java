package net.minecraft.src;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class RConThreadMain extends RConThreadBase
{
    private int field_56671_g;
    private int field_56675_h;
    private String field_56676_i;
    private ServerSocket field_56673_j;
    private String field_56674_k;
    private Map field_56672_l;

    public RConThreadMain(IServer par1IServer)
    {
        super(par1IServer);
        field_56673_j = null;
        field_56671_g = par1IServer.func_56424_b("rcon.port", 0);
        field_56674_k = par1IServer.func_56425_a("rcon.password", "");
        field_56676_i = par1IServer.func_56288_r();
        field_56675_h = par1IServer.func_56299_s();

        if (0 == field_56671_g)
        {
            field_56671_g = field_56675_h + 10;
            func_56657_b((new StringBuilder()).append("Setting default rcon port to ").append(field_56671_g).toString());
            par1IServer.func_56423_a("rcon.port", Integer.valueOf(field_56671_g));

            if (0 == field_56674_k.length())
            {
                par1IServer.func_56423_a("rcon.password", "");
            }

            par1IServer.func_56422_ag();
        }

        if (0 == field_56676_i.length())
        {
            field_56676_i = "0.0.0.0";
        }

        func_56669_e();
        field_56673_j = null;
    }

    private void func_56669_e()
    {
        field_56672_l = new HashMap();
    }

    private void func_56670_f()
    {
        Iterator iterator = field_56672_l.entrySet().iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();

            if (!((RConThreadClient)entry.getValue()).func_56662_b())
            {
                iterator.remove();
            }
        }
        while (true);
    }

    public void run()
    {
        func_56657_b((new StringBuilder()).append("RCON running on ").append(field_56676_i).append(":").append(field_56671_g).toString());

        try
        {
            do
            {
                if (!field_56668_a)
                {
                    break;
                }

                try
                {
                    Socket socket = field_56673_j.accept();
                    socket.setSoTimeout(500);
                    RConThreadClient rconthreadclient = new RConThreadClient(field_56666_b, socket);
                    rconthreadclient.func_56651_a();
                    field_56672_l.put(socket.getRemoteSocketAddress(), rconthreadclient);
                    func_56670_f();
                }
                catch (SocketTimeoutException sockettimeoutexception)
                {
                    func_56670_f();
                }
                catch (IOException ioexception)
                {
                    if (field_56668_a)
                    {
                        func_56657_b((new StringBuilder()).append("IO: ").append(ioexception.getMessage()).toString());
                    }
                }
            }
            while (true);
        }
        finally
        {
            func_56660_a(field_56673_j);
        }
    }

    public void func_56651_a()
    {
        if (0 == field_56674_k.length())
        {
            func_56654_c((new StringBuilder()).append("No rcon password set in '").append(field_56666_b.func_56421_ah()).append("', rcon disabled!").toString());
            return;
        }

        if (0 >= field_56671_g || 65535 < field_56671_g)
        {
            func_56654_c((new StringBuilder()).append("Invalid rcon port ").append(field_56671_g).append(" found in '").append(field_56666_b.func_56421_ah()).append("', rcon disabled!").toString());
            return;
        }

        if (field_56668_a)
        {
            return;
        }

        try
        {
            field_56673_j = new ServerSocket(field_56671_g, 0, InetAddress.getByName(field_56676_i));
            field_56673_j.setSoTimeout(500);
            super.func_56651_a();
        }
        catch (IOException ioexception)
        {
            func_56654_c((new StringBuilder()).append("Unable to initialise rcon on ").append(field_56676_i).append(":").append(field_56671_g).append(" : ").append(ioexception.getMessage()).toString());
        }
    }
}
