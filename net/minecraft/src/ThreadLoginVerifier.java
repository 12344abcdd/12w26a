package net.minecraft.src;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyPair;

class ThreadLoginVerifier extends Thread
{
    final NetLoginHandler field_57515_a;

    ThreadLoginVerifier(NetLoginHandler par1NetLoginHandler)
    {
        field_57515_a = par1NetLoginHandler;
    }

    public void run()
    {
        try
        {
            String s = (new BigInteger(CryptManager.func_55335_a(NetLoginHandler.func_56749_a(field_57515_a), NetLoginHandler.func_56743_b(field_57515_a).func_56355_F().getPublic(), NetLoginHandler.func_56746_c(field_57515_a)))).toString(16);
            URL url = new URL((new StringBuilder()).append("http://session.minecraft.net/game/checkserver.jsp?user=").append(URLEncoder.encode(NetLoginHandler.func_56745_d(field_57515_a), "UTF-8")).append("&serverId=").append(URLEncoder.encode(s, "UTF-8")).toString());
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(url.openStream()));
            String s1 = bufferedreader.readLine();
            bufferedreader.close();

            if (!"YES".equals(s1))
            {
                field_57515_a.func_56744_a("Failed to verify username!");
                return;
            }

            NetLoginHandler.func_56742_a(field_57515_a, true);
        }
        catch (Exception exception)
        {
            field_57515_a.func_56744_a((new StringBuilder()).append("Failed to verify username! [internal error ").append(exception).append("]").toString());
            exception.printStackTrace();
        }
    }
}
