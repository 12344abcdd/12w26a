package net.minecraft.src;

import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadLanServerPing extends Thread
{
    private static Logger field_58093_a = Logger.getLogger("Minecraft");
    private final String field_58091_b;
    private final DatagramSocket field_58092_c = new DatagramSocket();
    private boolean field_58089_d;
    private final String field_58090_e;

    public ThreadLanServerPing(String par1Str, String par2Str) throws IOException
    {
        super("LanServerPinger");
        field_58089_d = true;
        field_58091_b = par1Str;
        field_58090_e = par2Str;
        setDaemon(true);
    }

    public void run()
    {
        String s = func_58088_a(field_58091_b, field_58090_e);
        byte abyte0[] = s.getBytes();

        do
        {
            if (isInterrupted() || !field_58089_d)
            {
                break;
            }

            try
            {
                InetAddress inetaddress = InetAddress.getByName("224.0.2.60");
                DatagramPacket datagrampacket = new DatagramPacket(abyte0, abyte0.length, inetaddress, 4445);
                field_58092_c.send(datagrampacket);
            }
            catch (IOException ioexception)
            {
                field_58093_a.log(Level.WARNING, (new StringBuilder()).append("LanServerPinger: ").append(ioexception.getMessage()).toString());
                break;
            }

            try
            {
                sleep(1500L);
            }
            catch (InterruptedException interruptedexception) { }
        }
        while (true);
    }

    public void interrupt()
    {
        super.interrupt();
        field_58089_d = false;
    }

    public static String func_58088_a(String par0Str, String par1Str)
    {
        return (new StringBuilder()).append("[MOTD]").append(par0Str).append("[/MOTD][AD]").append(par1Str).append("[/AD]").toString();
    }

    public static String func_58087_a(String par0Str)
    {
        int i = par0Str.indexOf("[MOTD]");

        if (i < 0)
        {
            return "missing no";
        }

        int j = par0Str.indexOf("[/MOTD]", i + "[MOTD]".length());

        if (j < i)
        {
            return "missing no";
        }
        else
        {
            return par0Str.substring(i + "[MOTD]".length(), j);
        }
    }

    public static String func_58086_b(String par0Str)
    {
        int i = par0Str.indexOf("[/MOTD]");

        if (i < 0)
        {
            return null;
        }

        int j = par0Str.indexOf("[/MOTD]", i + "[/MOTD]".length());

        if (j >= 0)
        {
            return null;
        }

        int k = par0Str.indexOf("[AD]", i + "[/MOTD]".length());

        if (k < 0)
        {
            return null;
        }

        int l = par0Str.indexOf("[/AD]", k + "[AD]".length());

        if (l < k)
        {
            return null;
        }
        else
        {
            return par0Str.substring(k + "[AD]".length(), l);
        }
    }
}
