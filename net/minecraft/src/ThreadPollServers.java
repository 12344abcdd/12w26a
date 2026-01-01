package net.minecraft.src;

import java.io.IOException;
import java.net.*;

class ThreadPollServers extends Thread
{
    final ServerData field_57164_a;

    /** Slot container for the server list */
    final GuiSlotServer serverSlotContainer;

    ThreadPollServers(GuiSlotServer par1GuiSlotServer, ServerData par2ServerData)
    {
        serverSlotContainer = par1GuiSlotServer;
        field_57164_a = par2ServerData;
    }

    public void run()
    {
        try
        {
            field_57164_a.field_57445_d = "\2478Polling..";
            long l = System.nanoTime();
            GuiMultiplayer.func_58049_a(serverSlotContainer.parentGui, field_57164_a);
            long l1 = System.nanoTime();
            field_57164_a.field_57446_e = (l1 - l) / 0xf4240L;
        }
        catch (UnknownHostException unknownhostexception)
        {
            field_57164_a.field_57446_e = -1L;
            field_57164_a.field_57445_d = "\2474Can't resolve hostname";
        }
        catch (SocketTimeoutException sockettimeoutexception)
        {
            field_57164_a.field_57446_e = -1L;
            field_57164_a.field_57445_d = "\2474Can't reach server";
        }
        catch (ConnectException connectexception)
        {
            field_57164_a.field_57446_e = -1L;
            field_57164_a.field_57445_d = "\2474Can't reach server";
        }
        catch (IOException ioexception)
        {
            field_57164_a.field_57446_e = -1L;
            field_57164_a.field_57445_d = "\2474Communication error";
        }
        catch (Exception exception)
        {
            field_57164_a.field_57446_e = -1L;
            field_57164_a.field_57445_d = (new StringBuilder()).append("ERROR: ").append(exception.getClass()).toString();
        }
        finally
        {
            synchronized (GuiMultiplayer.func_58048_h())
            {
                GuiMultiplayer.func_58050_p();
            }
        }
    }
}
