package net.minecraft.src;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

final class HttpUtilRunnable implements Runnable
{
    final IProgressUpdate field_57630_a;
    final String field_57628_b;
    final Map field_57629_c;
    final File field_57626_d;
    final IDownloadSuccess field_57627_e;
    final int field_57625_f;

    HttpUtilRunnable(IProgressUpdate par1IProgressUpdate, String par2Str, Map par3Map, File par4File, IDownloadSuccess par5IDownloadSuccess, int par6)
    {
        field_57630_a = par1IProgressUpdate;
        field_57628_b = par2Str;
        field_57629_c = par3Map;
        field_57626_d = par4File;
        field_57627_e = par5IDownloadSuccess;
        field_57625_f = par6;
    }

    public void run()
    {
        Object obj = null;
        InputStream inputstream = null;
        DataOutputStream dataoutputstream = null;

        if (field_57630_a != null)
        {
            field_57630_a.printText("Downloading Texture Pack");
            field_57630_a.displayLoadingString("Making Request...");
        }

        try
        {
            byte abyte0[] = new byte[4096];
            URL url = new URL(field_57628_b);
            URLConnection urlconnection = url.openConnection();
            float f = 0.0F;
            float f1 = field_57629_c.entrySet().size();
            Iterator iterator = field_57629_c.entrySet().iterator();

            do
            {
                if (!iterator.hasNext())
                {
                    break;
                }

                java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
                urlconnection.setRequestProperty((String)entry.getKey(), (String)entry.getValue());

                if (field_57630_a != null)
                {
                    field_57630_a.setLoadingProgress((int)((++f / f1) * 100F));
                }
            }
            while (true);

            inputstream = urlconnection.getInputStream();
            f1 = urlconnection.getContentLength();
            int i = urlconnection.getContentLength();

            if (field_57630_a != null)
            {
                field_57630_a.displayLoadingString(String.format("Downloading file (%.2f MB)...", new Object[]
                        {
                            Float.valueOf(f1 / 1000F / 1000F)
                        }));
            }

            if (field_57626_d.exists())
            {
                long l = field_57626_d.length();

                if (l == (long)i)
                {
                    field_57627_e.func_56641_a(field_57626_d);

                    if (field_57630_a != null)
                    {
                        field_57630_a.func_56509_a();
                    }

                    return;
                }

                System.out.println((new StringBuilder()).append("Deleting ").append(field_57626_d).append(" as it does not match what we currently have (").append(i).append(" vs our ").append(l).append(").").toString());
                field_57626_d.delete();
            }

            dataoutputstream = new DataOutputStream(new FileOutputStream(field_57626_d));

            if (field_57625_f > 0 && f1 > (float)field_57625_f)
            {
                if (field_57630_a != null)
                {
                    field_57630_a.func_56509_a();
                }

                throw new IOException((new StringBuilder()).append("Filesize is bigger than maximum allowed (file is ").append(f).append(", limit is ").append(field_57625_f).append(")").toString());
            }

            for (int j = 0; (j = inputstream.read(abyte0)) >= 0;)
            {
                f += j;

                if (field_57630_a != null)
                {
                    field_57630_a.setLoadingProgress((int)((f / f1) * 100F));
                }

                if (field_57625_f > 0 && f > (float)field_57625_f)
                {
                    if (field_57630_a != null)
                    {
                        field_57630_a.func_56509_a();
                    }

                    throw new IOException((new StringBuilder()).append("Filesize was bigger than maximum allowed (got >= ").append(f).append(", limit was ").append(field_57625_f).append(")").toString());
                }

                dataoutputstream.write(abyte0, 0, j);
            }

            field_57627_e.func_56641_a(field_57626_d);

            if (field_57630_a != null)
            {
                field_57630_a.func_56509_a();
            }
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
        }
        finally
        {
            try
            {
                if (inputstream != null)
                {
                    inputstream.close();
                }
            }
            catch (IOException ioexception) { }

            try
            {
                if (dataoutputstream != null)
                {
                    dataoutputstream.close();
                }
            }
            catch (IOException ioexception1) { }
        }
    }
}
