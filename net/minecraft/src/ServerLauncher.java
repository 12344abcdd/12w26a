package net.minecraft.src;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import net.minecraft.client.Minecraft;

public class ServerLauncher
{
    private File field_57435_a;
    private File field_57433_b;
    private JavaProcess field_57434_c;
    private String field_57432_d;

    public ServerLauncher()
    {
        this(new File(Minecraft.getMinecraftDir(), "server/minecraft_server.jar"), new File(Minecraft.getMinecraftDir(), "server/"));
    }

    public ServerLauncher(File par1File, File par2File)
    {
        field_57435_a = par1File;
        field_57433_b = par2File;
    }

    public boolean func_57429_a()
    {
        return field_57434_c != null && field_57434_c.func_56710_a();
    }

    public static int func_57431_b() throws IOException
    {
        ServerSocket serversocket = null;
        int i = -1;

        try
        {
            serversocket = new ServerSocket(0);
            i = serversocket.getLocalPort();
        }
        finally
        {
            try
            {
                if (serversocket != null)
                {
                    serversocket.close();
                }
            }
            catch (IOException ioexception) { }
        }

        return i;
    }

    public void func_57428_a(File par1File)
    {
        field_57435_a = par1File;
    }

    public void func_57430_a(String par1Str)
    {
        field_57432_d = par1Str;
    }
}
