package net.minecraft.src;

import java.util.*;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class GuiNewChat extends Gui
{
    private final Minecraft field_55112_a;
    private final List field_55110_b = new ArrayList();
    private final List field_55111_c = new ArrayList();
    private int field_55108_d;
    private boolean field_55109_e;

    public GuiNewChat(Minecraft par1Minecraft)
    {
        field_55108_d = 0;
        field_55109_e = false;
        field_55112_a = par1Minecraft;
    }

    public void func_55102_a(int par1)
    {
        if (field_55112_a.gameSettings.field_55382_n == 2)
        {
            return;
        }

        byte byte0 = 10;
        boolean flag = false;
        int i = 0;
        int j = field_55111_c.size();
        float f = field_55112_a.gameSettings.field_55384_r * 0.9F + 0.1F;

        if (j <= 0)
        {
            return;
        }

        if (func_55100_d())
        {
            byte0 = 20;
            flag = true;
        }

        for (int k = 0; k + field_55108_d < field_55111_c.size() && k < byte0; k++)
        {
            ChatLine chatline = (ChatLine)field_55111_c.get(k + field_55108_d);
            int j1 = par1 - chatline.func_55313_b();

            if (j1 >= 200 && !flag)
            {
                continue;
            }

            double d = (double)j1 / 200D;
            d = 1.0D - d;
            d *= 10D;

            if (d < 0.0D)
            {
                d = 0.0D;
            }

            if (d > 1.0D)
            {
                d = 1.0D;
            }

            d *= d;
            int j2 = (int)(255D * d);

            if (flag)
            {
                j2 = 255;
            }

            j2 = (int)((float)j2 * f);
            i++;

            if (j2 <= 3)
            {
                continue;
            }

            byte byte1 = 3;
            int l2 = -k * 9;
            drawRect(byte1, l2 - 1, byte1 + 320 + 4, l2 + 8, j2 / 2 << 24);
            GL11.glEnable(GL11.GL_BLEND);
            String s = chatline.func_55311_a();

            if (!field_55112_a.gameSettings.field_55383_o)
            {
                s = StringUtils.func_55394_a(s);
            }

            field_55112_a.fontRenderer.drawStringWithShadow(s, byte1, l2, 0xffffff + (j2 << 24));
        }

        if (flag)
        {
            int l = field_55112_a.fontRenderer.FONT_HEIGHT;
            GL11.glTranslatef(0.0F, l, 0.0F);
            int i1 = j * l + j;
            int k1 = i * l + i;
            int l1 = (field_55108_d * k1) / j;
            int i2 = (k1 * k1) / i1;

            if (i1 != k1)
            {
                char c = l1 <= 0 ? '`' : '\252';
                int k2 = field_55109_e ? 0xcc3333 : 0x3333aa;
                drawRect(0, -l1, 2, -l1 - i2, k2 + (c << 24));
                drawRect(2, -l1, 1, -l1 - i2, 0xcccccc + (c << 24));
            }
        }
    }

    public void func_55101_a()
    {
        field_55111_c.clear();
        field_55110_b.clear();
    }

    public void func_55106_a(String par1Str)
    {
        func_55104_a(par1Str, 0);
    }

    public void func_55104_a(String par1Str, int par2)
    {
        boolean flag = func_55100_d();
        boolean flag1 = true;

        if (par2 != 0)
        {
            func_55099_c(par2);
        }

        String s;

        for (Iterator iterator = field_55112_a.fontRenderer.listFormattedStringToWidth(par1Str, 320).iterator(); iterator.hasNext(); field_55111_c.add(0, new ChatLine(field_55112_a.ingameGUI.func_55093_c(), s, par2)))
        {
            s = (String)iterator.next();

            if (flag && field_55108_d > 0)
            {
                field_55109_e = true;
                func_55098_b(1);
            }

            if (!flag1)
            {
                s = (new StringBuilder()).append(" ").append(s).toString();
            }

            flag1 = false;
        }

        for (; field_55111_c.size() > 100; field_55111_c.remove(field_55111_c.size() - 1)) { }
    }

    public List func_55096_b()
    {
        return field_55110_b;
    }

    public void func_55107_b(String par1Str)
    {
        if (field_55110_b.isEmpty() || !((String)field_55110_b.get(field_55110_b.size() - 1)).equals(par1Str))
        {
            field_55110_b.add(par1Str);
        }
    }

    public void func_55105_c()
    {
        field_55108_d = 0;
        field_55109_e = false;
    }

    public void func_55098_b(int par1)
    {
        field_55108_d += par1;
        int i = field_55111_c.size();

        if (field_55108_d > i - 20)
        {
            field_55108_d = i - 20;
        }

        if (field_55108_d <= 0)
        {
            field_55108_d = 0;
            field_55109_e = false;
        }
    }

    public ChatClickData func_55097_a(int par1, int par2)
    {
        if (!func_55100_d())
        {
            return null;
        }

        ScaledResolution scaledresolution = new ScaledResolution(field_55112_a.gameSettings, field_55112_a.displayWidth, field_55112_a.displayHeight);
        int i = scaledresolution.func_57187_e();
        int j = par1 / i - 3;
        int k = par2 / i - 40;

        if (j < 0 || k < 0)
        {
            return null;
        }

        int l = Math.min(20, field_55111_c.size());

        if (j <= 320 && k < field_55112_a.fontRenderer.FONT_HEIGHT * l + l)
        {
            int i1 = k / (field_55112_a.fontRenderer.FONT_HEIGHT + 1) + field_55108_d;
            return new ChatClickData(field_55112_a.fontRenderer, (ChatLine)field_55111_c.get(i1), j, (k - (i1 - field_55108_d) * field_55112_a.fontRenderer.FONT_HEIGHT) + i1);
        }
        else
        {
            return null;
        }
    }

    public void func_56468_a(String par1Str, Object par2ArrayOfObj[])
    {
        func_55106_a(StringTranslate.getInstance().translateKeyFormat(par1Str, par2ArrayOfObj));
    }

    public boolean func_55100_d()
    {
        return field_55112_a.currentScreen instanceof GuiChat;
    }

    public void func_55099_c(int par1)
    {
        for (Iterator iterator = field_55111_c.iterator(); iterator.hasNext();)
        {
            ChatLine chatline = (ChatLine)iterator.next();

            if (chatline.func_55312_c() == par1)
            {
                iterator.remove();
                return;
            }
        }
    }
}
