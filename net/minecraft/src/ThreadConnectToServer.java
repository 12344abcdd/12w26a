package net.minecraft.src;

import java.net.ConnectException;
import java.net.UnknownHostException;
import net.minecraft.client.Minecraft;

class ThreadConnectToServer extends Thread
{
    /** The IP address or domain used to connect. */
    final String ip;

    /** The port used to connect. */
    final int port;

    /** A reference to the GuiConnecting object. */
    final GuiConnecting connectingGui;

    ThreadConnectToServer(GuiConnecting par1GuiConnecting, String par2Str, int par3)
    {
        connectingGui = par1GuiConnecting;
        ip = par2Str;
        port = par3;
    }

    public void run()
    {
        try
        {
            GuiConnecting.setNetClientHandler(connectingGui, new NetClientHandler(GuiConnecting.func_56502_a(connectingGui), ip, port));

            if (GuiConnecting.func_56504_b(connectingGui))
            {
                return;
            }

            GuiConnecting.getNetClientHandler(connectingGui).addToSendQueue(new Packet2ClientProtocol(37, GuiConnecting.func_56508_c(connectingGui).session.username, ip, port));
        }
        catch (UnknownHostException unknownhostexception)
        {
            if (GuiConnecting.func_56504_b(connectingGui))
            {
                return;
            }

            GuiConnecting.func_56506_e(connectingGui).displayGuiScreen(new GuiDisconnected("connect.failed", "disconnect.genericReason", new Object[]
                    {
                        (new StringBuilder()).append("Unknown host '").append(ip).append("'").toString()
                    }));
        }
        catch (ConnectException connectexception)
        {
            if (GuiConnecting.func_56504_b(connectingGui))
            {
                return;
            }

            GuiConnecting.func_56503_f(connectingGui).displayGuiScreen(new GuiDisconnected("connect.failed", "disconnect.genericReason", new Object[]
                    {
                        connectexception.getMessage()
                    }));
        }
        catch (Exception exception)
        {
            if (GuiConnecting.func_56504_b(connectingGui))
            {
                return;
            }

            exception.printStackTrace();
            GuiConnecting.func_56501_g(connectingGui).displayGuiScreen(new GuiDisconnected("connect.failed", "disconnect.genericReason", new Object[]
                    {
                        exception.toString()
                    }));
        }
    }
}
