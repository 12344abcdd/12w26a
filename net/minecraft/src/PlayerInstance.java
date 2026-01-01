package net.minecraft.src;

import java.util.*;

class PlayerInstance
{
    private final List field_57161_b = new ArrayList();
    private final ChunkCoordIntPair field_57162_c;
    private short field_57159_d[];
    private int field_57160_e;
    private int field_57158_f;
    final PlayerManager field_57163_a;

    public PlayerInstance(PlayerManager par1PlayerManager, int par2, int par3)
    {
        field_57163_a = par1PlayerManager;
        field_57159_d = new short[64];
        field_57160_e = 0;
        field_57162_c = new ChunkCoordIntPair(par2, par3);
        par1PlayerManager.func_57606_a().field_56871_I.loadChunk(par2, par3);
    }

    public void func_57152_a(EntityPlayerMP par1EntityPlayerMP)
    {
        if (field_57161_b.contains(par1EntityPlayerMP))
        {
            throw new IllegalStateException((new StringBuilder()).append("Failed to add player. ").append(par1EntityPlayerMP).append(" already is in chunk ").append(field_57162_c.chunkXPos).append(", ").append(field_57162_c.chunkZPosition).toString());
        }
        else
        {
            field_57161_b.add(par1EntityPlayerMP);
            par1EntityPlayerMP.field_56262_f.add(field_57162_c);
            return;
        }
    }

    public void func_57151_b(EntityPlayerMP par1EntityPlayerMP)
    {
        if (!field_57161_b.contains(par1EntityPlayerMP))
        {
            return;
        }

        par1EntityPlayerMP.field_56268_a.func_56717_b(new Packet51MapChunk(PlayerManager.func_57611_a(field_57163_a).getChunkFromChunkCoords(field_57162_c.chunkXPos, field_57162_c.chunkZPosition), true, 0));
        field_57161_b.remove(par1EntityPlayerMP);
        par1EntityPlayerMP.field_56262_f.remove(field_57162_c);

        if (field_57161_b.isEmpty())
        {
            long l = (long)field_57162_c.chunkXPos + 0x7fffffffL | (long)field_57162_c.chunkZPosition + 0x7fffffffL << 32;
            PlayerManager.func_57607_b(field_57163_a).remove(l);

            if (field_57160_e > 0)
            {
                PlayerManager.func_57609_c(field_57163_a).remove(this);
            }

            field_57163_a.func_57606_a().field_56871_I.func_56898_d(field_57162_c.chunkXPos, field_57162_c.chunkZPosition);
        }
    }

    public void func_57157_a(int par1, int par2, int par3)
    {
        if (field_57160_e == 0)
        {
            PlayerManager.func_57609_c(field_57163_a).add(this);
        }

        field_57158_f |= 1 << (par2 >> 4);

        if (field_57160_e < 64)
        {
            short word0 = (short)(par1 << 12 | par3 << 8 | par2);

            for (int i = 0; i < field_57160_e; i++)
            {
                if (field_57159_d[i] == word0)
                {
                    return;
                }
            }

            field_57159_d[field_57160_e++] = word0;
        }
    }

    public void func_57155_a(Packet par1Packet)
    {
        Iterator iterator = field_57161_b.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();

            if (!entityplayermp.field_56262_f.contains(field_57162_c))
            {
                entityplayermp.field_56268_a.func_56717_b(par1Packet);
            }
        }
        while (true);
    }

    public void func_57154_a()
    {
        if (field_57160_e == 0)
        {
            return;
        }

        if (field_57160_e == 1)
        {
            int i = field_57162_c.chunkXPos * 16 + (field_57159_d[0] >> 12 & 0xf);
            int l = field_57159_d[0] & 0xff;
            int k1 = field_57162_c.chunkZPosition * 16 + (field_57159_d[0] >> 8 & 0xf);
            func_57155_a(new Packet53BlockChange(i, l, k1, PlayerManager.func_57611_a(field_57163_a)));

            if (PlayerManager.func_57611_a(field_57163_a).func_56838_k(i, l, k1))
            {
                func_57156_a(PlayerManager.func_57611_a(field_57163_a).getBlockTileEntity(i, l, k1));
            }
        }
        else if (field_57160_e == 64)
        {
            int j = field_57162_c.chunkXPos * 16;
            int i1 = field_57162_c.chunkZPosition * 16;
            func_57155_a(new Packet51MapChunk(PlayerManager.func_57611_a(field_57163_a).getChunkFromChunkCoords(field_57162_c.chunkXPos, field_57162_c.chunkZPosition), false, field_57158_f));

            for (int l1 = 0; l1 < 16; l1++)
            {
                if ((field_57158_f & 1 << l1) != 0)
                {
                    int j2 = l1 << 4;
                    List list = PlayerManager.func_57611_a(field_57163_a).func_56857_a(j, j2, i1, j + 16, j2 + 16, i1 + 16);
                    TileEntity tileentity;

                    for (Iterator iterator = list.iterator(); iterator.hasNext(); func_57156_a(tileentity))
                    {
                        tileentity = (TileEntity)iterator.next();
                    }
                }
            }
        }
        else
        {
            func_57155_a(new Packet52MultiBlockChange(field_57162_c.chunkXPos, field_57162_c.chunkZPosition, field_57159_d, field_57160_e, PlayerManager.func_57611_a(field_57163_a)));

            for (int k = 0; k < field_57160_e; k++)
            {
                int j1 = field_57162_c.chunkXPos * 16 + (field_57159_d[k] >> 12 & 0xf);
                int i2 = field_57159_d[k] & 0xff;
                int k2 = field_57162_c.chunkZPosition * 16 + (field_57159_d[k] >> 8 & 0xf);

                if (PlayerManager.func_57611_a(field_57163_a).func_56838_k(j1, i2, k2))
                {
                    func_57156_a(PlayerManager.func_57611_a(field_57163_a).getBlockTileEntity(j1, i2, k2));
                }
            }
        }

        field_57160_e = 0;
        field_57158_f = 0;
    }

    private void func_57156_a(TileEntity par1TileEntity)
    {
        if (par1TileEntity != null)
        {
            Packet packet = par1TileEntity.func_56203_d();

            if (packet != null)
            {
                func_57155_a(packet);
            }
        }
    }

    static ChunkCoordIntPair func_57153_a(PlayerInstance par0PlayerInstance)
    {
        return par0PlayerInstance.field_57162_c;
    }
}
