package net.minecraft.src;

import java.io.*;

class DedicatedServerCommandThread extends Thread
{
    final DedicatedServer field_57331_a;

    DedicatedServerCommandThread(DedicatedServer par1DedicatedServer)
    {
        field_57331_a = par1DedicatedServer;
    }

    public void run()
    {
        BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(System.in));

        try
        {
            String s;

            for (; !field_57331_a.func_56304_Y() && field_57331_a.func_56349_j() && (s = bufferedreader.readLine()) != null; field_57331_a.func_56428_a(s, field_57331_a)) { }
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }
}
