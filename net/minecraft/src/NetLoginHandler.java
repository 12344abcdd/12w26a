package net.minecraft.src;

import java.io.IOException;
import java.net.Socket;
import java.security.KeyPair;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Logger;
import javax.crypto.SecretKey;

public class NetLoginHandler extends NetHandler
{
    private byte field_58094_d[];
    public static Logger field_56756_a = Logger.getLogger("Minecraft");
    private static Random field_56752_d = new Random();
    public TcpConnection field_56754_b;
    public boolean field_56755_c;
    private MinecraftServer field_56753_e;
    private int field_56750_f;
    private String field_56751_g;
    private volatile boolean field_56758_h;
    private String field_56759_i;
    private SecretKey field_56757_j;

    public NetLoginHandler(MinecraftServer par1MinecraftServer, Socket par2Socket, String par3Str) throws IOException
    {
        field_56755_c = false;
        field_56750_f = 0;
        field_56751_g = null;
        field_56758_h = false;
        field_56759_i = "";
        field_56757_j = null;
        field_56753_e = par1MinecraftServer;
        field_56754_b = new TcpConnection(par2Socket, par3Str, this, par1MinecraftServer.func_56355_F().getPrivate());
        field_56754_b.field_57295_e = 0;
    }

    public void func_56747_b()
    {
        if (field_56758_h)
        {
            func_56748_c();
        }

        if (field_56750_f++ == 600)
        {
            func_56744_a("Took too long to log in");
        }
        else
        {
            field_56754_b.processReadPackets();
        }
    }

    public void func_56744_a(String par1Str)
    {
        try
        {
            field_56756_a.info((new StringBuilder()).append("Disconnecting ").append(func_56741_d()).append(": ").append(par1Str).toString());
            field_56754_b.addToSendQueue(new Packet255KickDisconnect(par1Str));
            field_56754_b.serverShutdown();
            field_56755_c = true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void func_55320_a(Packet2ClientProtocol par1Packet2ClientProtocol)
    {
        field_56751_g = par1Packet2ClientProtocol.func_56584_c();

        if (!field_56751_g.equals(StringUtils.func_55394_a(field_56751_g)))
        {
            func_56744_a("Invalid username!");
            return;
        }

        java.security.PublicKey publickey = field_56753_e.func_56355_F().getPublic();

        if (par1Packet2ClientProtocol.func_56583_b() != 37)
        {
            if (par1Packet2ClientProtocol.func_56583_b() > 37)
            {
                func_56744_a("Outdated server!");
            }
            else
            {
                func_56744_a("Outdated client!");
            }

            return;
        }
        else
        {
            field_56759_i = field_56753_e.func_56338_R() ? Long.toString(field_56752_d.nextLong(), 16) : "-";
            field_58094_d = new byte[4];
            field_56752_d.nextBytes(field_58094_d);
            field_56754_b.addToSendQueue(new Packet253ServerAuthData(field_56759_i, publickey, field_58094_d));
            return;
        }
    }

    public void func_55317_a(Packet252SharedKey par1Packet252SharedKey)
    {
        java.security.PrivateKey privatekey = field_56753_e.func_56355_F().getPrivate();
        field_56757_j = par1Packet252SharedKey.func_55163_a(privatekey);

        if (!Arrays.equals(field_58094_d, par1Packet252SharedKey.func_58069_b(privatekey)))
        {
            func_56744_a("Invalid client reply");
        }

        field_56754_b.addToSendQueue(new Packet252SharedKey());
    }

    public void func_56715_a(Packet205ClientCommand par1Packet205ClientCommand)
    {
        if (par1Packet205ClientCommand.field_56587_a == 0)
        {
            if (field_56753_e.func_56338_R())
            {
                (new ThreadLoginVerifier(this)).start();
            }
            else
            {
                field_56758_h = true;
            }
        }
    }

    public void handleLogin(Packet1Login packet1login)
    {
    }

    public void func_56748_c()
    {
        String s = field_56753_e.func_56339_Z().func_57128_a(field_56754_b.func_57272_c(), field_56751_g);

        if (s != null)
        {
            func_56744_a(s);
        }
        else
        {
            EntityPlayerMP entityplayermp = field_56753_e.func_56339_Z().func_57123_a(field_56751_g);

            if (entityplayermp != null)
            {
                field_56753_e.func_56339_Z().func_57113_a(field_56754_b, entityplayermp);
            }
        }

        field_56755_c = true;
    }

    public void handleErrorMessage(String par1Str, Object par2ArrayOfObj[])
    {
        field_56756_a.info((new StringBuilder()).append(func_56741_d()).append(" lost connection").toString());
        field_56755_c = true;
    }

    /**
     * Handle a server ping packet.
     */
    public void handleServerPing(Packet254ServerPing par1Packet254ServerPing)
    {
        try
        {
            String s = (new StringBuilder()).append(field_56753_e.func_56318_W()).append("\247").append(field_56753_e.func_56339_Z().func_57121_k()).append("\247").append(field_56753_e.func_56339_Z().func_57086_l()).toString();
            java.net.InetAddress inetaddress = null;

            if (field_56754_b.func_57284_g() != null)
            {
                inetaddress = field_56754_b.func_57284_g().getInetAddress();
            }

            field_56754_b.addToSendQueue(new Packet255KickDisconnect(s));
            field_56754_b.serverShutdown();

            if (inetaddress != null && (field_56753_e.func_56290_aa() instanceof DedicatedServerListenThread))
            {
                ((DedicatedServerListenThread)field_56753_e.func_56290_aa()).func_58079_a(inetaddress);
            }

            field_56755_c = true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void registerPacket(Packet par1Packet)
    {
        func_56744_a("Protocol error");
    }

    public String func_56741_d()
    {
        if (field_56751_g != null)
        {
            return (new StringBuilder()).append(field_56751_g).append(" [").append(field_56754_b.func_57272_c().toString()).append("]").toString();
        }
        else
        {
            return field_56754_b.func_57272_c().toString();
        }
    }

    /**
     * determine if it is a server handler
     */
    public boolean isServerHandler()
    {
        return true;
    }

    static String func_56749_a(NetLoginHandler par0NetLoginHandler)
    {
        return par0NetLoginHandler.field_56759_i;
    }

    static MinecraftServer func_56743_b(NetLoginHandler par0NetLoginHandler)
    {
        return par0NetLoginHandler.field_56753_e;
    }

    static SecretKey func_56746_c(NetLoginHandler par0NetLoginHandler)
    {
        return par0NetLoginHandler.field_56757_j;
    }

    static String func_56745_d(NetLoginHandler par0NetLoginHandler)
    {
        return par0NetLoginHandler.field_56751_g;
    }

    static boolean func_56742_a(NetLoginHandler par0NetLoginHandler, boolean par1)
    {
        return par0NetLoginHandler.field_56758_h = par1;
    }
}
