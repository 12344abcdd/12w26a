package net.minecraft.src;

import java.io.IOException;
import java.io.PrintStream;
import java.net.*;

public class ThreadLanServerFind extends Thread
{
    private final LanServerList field_58101_a;
    private final InetAddress field_58099_b = InetAddress.getByName("224.0.2.60");
    private final MulticastSocket field_58100_c = new MulticastSocket(4445);

    public ThreadLanServerFind(LanServerList par1LanServerList) throws IOException
    {
        super("LanServerDetector");
        field_58101_a = par1LanServerList;
        setDaemon(true);
        field_58100_c.joinGroup(field_58099_b);
    }

    public void run()
    {
        byte abyte0[] = new byte[1024];

        do
        {
            if (isInterrupted())
            {
                break;
            }

            DatagramPacket datagrampacket = new DatagramPacket(abyte0, abyte0.length);

            try
            {
                field_58100_c.receive(datagrampacket);
            }
            catch (IOException ioexception)
            {
                ioexception.printStackTrace();
                break;
            }

            String s = new String(datagrampacket.getData(), datagrampacket.getOffset(), datagrampacket.getLength());
            System.out.println((new StringBuilder()).append(datagrampacket.getAddress()).append(": ").append(s).toString());
            field_58101_a.func_58145_a(s, datagrampacket.getAddress());
        }
        while (true);

        try
        {
            field_58100_c.leaveGroup(field_58099_b);
        }
        catch (IOException ioexception1) { }

        field_58100_c.close();
    }
}
