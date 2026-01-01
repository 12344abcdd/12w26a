package net.minecraft.src;

import java.util.*;

public class PlayerManager
{
    private final WorldServer field_57620_a;
    private final List field_57618_b = new ArrayList();
    private final LongHashMap field_57619_c = new LongHashMap();
    private final List field_57616_d = new ArrayList();
    private final int field_57617_e;
    private final int field_57615_f[][] =
    {
        {
            1, 0
        }, {
            0, 1
        }, {
            -1, 0
        }, {
            0, -1
        }
    };

    public PlayerManager(WorldServer par1WorldServer, int par2)
    {
        if (par2 > 15)
        {
            throw new IllegalArgumentException("Too big view radius!");
        }

        if (par2 < 3)
        {
            throw new IllegalArgumentException("Too small view radius!");
        }
        else
        {
            field_57617_e = par2;
            field_57620_a = par1WorldServer;
            return;
        }
    }

    public WorldServer func_57606_a()
    {
        return field_57620_a;
    }

    public void func_57612_b()
    {
        PlayerInstance playerinstance;

        for (Iterator iterator = field_57616_d.iterator(); iterator.hasNext(); playerinstance.func_57154_a())
        {
            playerinstance = (PlayerInstance)iterator.next();
        }

        field_57616_d.clear();

        if (field_57618_b.isEmpty())
        {
            WorldProvider worldprovider = field_57620_a.worldProvider;

            if (!worldprovider.canRespawnHere())
            {
                field_57620_a.field_56871_I.func_56900_e();
            }
        }
    }

    private PlayerInstance func_57603_a(int par1, int par2, boolean par3)
    {
        long l = (long)par1 + 0x7fffffffL | (long)par2 + 0x7fffffffL << 32;
        PlayerInstance playerinstance = (PlayerInstance)field_57619_c.getValueByKey(l);

        if (playerinstance == null && par3)
        {
            playerinstance = new PlayerInstance(this, par1, par2);
            field_57619_c.add(l, playerinstance);
        }

        return playerinstance;
    }

    public void func_57608_a(int par1, int par2, int par3)
    {
        int i = par1 >> 4;
        int j = par3 >> 4;
        PlayerInstance playerinstance = func_57603_a(i, j, false);

        if (playerinstance != null)
        {
            playerinstance.func_57157_a(par1 & 0xf, par2, par3 & 0xf);
        }
    }

    public void func_57602_a(EntityPlayerMP par1EntityPlayerMP)
    {
        int i = (int)par1EntityPlayerMP.posX >> 4;
        int j = (int)par1EntityPlayerMP.posZ >> 4;
        par1EntityPlayerMP.field_56264_d = par1EntityPlayerMP.posX;
        par1EntityPlayerMP.field_56265_e = par1EntityPlayerMP.posZ;

        for (int k = i - field_57617_e; k <= i + field_57617_e; k++)
        {
            for (int l = j - field_57617_e; l <= j + field_57617_e; l++)
            {
                func_57603_a(k, l, true).func_57152_a(par1EntityPlayerMP);
            }
        }

        field_57618_b.add(par1EntityPlayerMP);
        func_57610_b(par1EntityPlayerMP);
    }

    public void func_57610_b(EntityPlayerMP par1EntityPlayerMP)
    {
        ArrayList arraylist = new ArrayList(par1EntityPlayerMP.field_56262_f);
        int i = 0;
        int j = field_57617_e;
        int k = (int)par1EntityPlayerMP.posX >> 4;
        int l = (int)par1EntityPlayerMP.posZ >> 4;
        int i1 = 0;
        int j1 = 0;
        ChunkCoordIntPair chunkcoordintpair = PlayerInstance.func_57153_a(func_57603_a(k, l, true));
        par1EntityPlayerMP.field_56262_f.clear();

        if (arraylist.contains(chunkcoordintpair))
        {
            par1EntityPlayerMP.field_56262_f.add(chunkcoordintpair);
        }

        for (int k1 = 1; k1 <= j * 2; k1++)
        {
            for (int i2 = 0; i2 < 2; i2++)
            {
                int ai[] = field_57615_f[i++ % 4];

                for (int j2 = 0; j2 < k1; j2++)
                {
                    i1 += ai[0];
                    j1 += ai[1];
                    ChunkCoordIntPair chunkcoordintpair1 = PlayerInstance.func_57153_a(func_57603_a(k + i1, l + j1, true));

                    if (arraylist.contains(chunkcoordintpair1))
                    {
                        par1EntityPlayerMP.field_56262_f.add(chunkcoordintpair1);
                    }
                }
            }
        }

        i %= 4;

        for (int l1 = 0; l1 < j * 2; l1++)
        {
            i1 += field_57615_f[i][0];
            j1 += field_57615_f[i][1];
            ChunkCoordIntPair chunkcoordintpair2 = PlayerInstance.func_57153_a(func_57603_a(k + i1, l + j1, true));

            if (arraylist.contains(chunkcoordintpair2))
            {
                par1EntityPlayerMP.field_56262_f.add(chunkcoordintpair2);
            }
        }
    }

    public void func_57614_c(EntityPlayerMP par1EntityPlayerMP)
    {
        int i = (int)par1EntityPlayerMP.field_56264_d >> 4;
        int j = (int)par1EntityPlayerMP.field_56265_e >> 4;

        for (int k = i - field_57617_e; k <= i + field_57617_e; k++)
        {
            for (int l = j - field_57617_e; l <= j + field_57617_e; l++)
            {
                PlayerInstance playerinstance = func_57603_a(k, l, false);

                if (playerinstance != null)
                {
                    playerinstance.func_57151_b(par1EntityPlayerMP);
                }
            }
        }

        field_57618_b.remove(par1EntityPlayerMP);
    }

    private boolean func_57604_a(int par1, int par2, int par3, int par4, int par5)
    {
        int i = par1 - par3;
        int j = par2 - par4;

        if (i < -par5 || i > par5)
        {
            return false;
        }

        return j >= -par5 && j <= par5;
    }

    public void func_57613_d(EntityPlayerMP par1EntityPlayerMP)
    {
        int i = (int)par1EntityPlayerMP.posX >> 4;
        int j = (int)par1EntityPlayerMP.posZ >> 4;
        double d = par1EntityPlayerMP.field_56264_d - par1EntityPlayerMP.posX;
        double d1 = par1EntityPlayerMP.field_56265_e - par1EntityPlayerMP.posZ;
        double d2 = d * d + d1 * d1;

        if (d2 < 64D)
        {
            return;
        }

        int k = (int)par1EntityPlayerMP.field_56264_d >> 4;
        int l = (int)par1EntityPlayerMP.field_56265_e >> 4;
        int i1 = field_57617_e;
        int j1 = i - k;
        int k1 = j - l;

        if (j1 == 0 && k1 == 0)
        {
            return;
        }

        for (int l1 = i - i1; l1 <= i + i1; l1++)
        {
            for (int i2 = j - i1; i2 <= j + i1; i2++)
            {
                if (!func_57604_a(l1, i2, k, l, i1))
                {
                    func_57603_a(l1, i2, true).func_57152_a(par1EntityPlayerMP);
                }

                if (func_57604_a(l1 - j1, i2 - k1, i, j, i1))
                {
                    continue;
                }

                PlayerInstance playerinstance = func_57603_a(l1 - j1, i2 - k1, false);

                if (playerinstance != null)
                {
                    playerinstance.func_57151_b(par1EntityPlayerMP);
                }
            }
        }

        func_57610_b(par1EntityPlayerMP);
        par1EntityPlayerMP.field_56264_d = par1EntityPlayerMP.posX;
        par1EntityPlayerMP.field_56265_e = par1EntityPlayerMP.posZ;
    }

    public static int func_57605_a(int par0)
    {
        return par0 * 16 - 16;
    }

    static WorldServer func_57611_a(PlayerManager par0PlayerManager)
    {
        return par0PlayerManager.field_57620_a;
    }

    static LongHashMap func_57607_b(PlayerManager par0PlayerManager)
    {
        return par0PlayerManager.field_57619_c;
    }

    static List func_57609_c(PlayerManager par0PlayerManager)
    {
        return par0PlayerManager.field_57616_d;
    }
}
