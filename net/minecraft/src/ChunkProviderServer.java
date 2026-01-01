package net.minecraft.src;

import java.io.IOException;
import java.util.*;

public class ChunkProviderServer implements IChunkProvider
{
    private Set field_56907_b;
    private Chunk field_56908_c;
    private IChunkProvider field_56905_d;
    private IChunkLoader field_56906_e;
    public boolean field_56909_a;
    private LongHashMap field_56903_f;
    private List field_56904_g;
    private WorldServer field_56910_h;

    public ChunkProviderServer(WorldServer par1WorldServer, IChunkLoader par2IChunkLoader, IChunkProvider par3IChunkProvider)
    {
        field_56907_b = new HashSet();
        field_56909_a = true;
        field_56903_f = new LongHashMap();
        field_56904_g = new ArrayList();
        field_56908_c = new EmptyChunk(par1WorldServer, 0, 0);
        field_56910_h = par1WorldServer;
        field_56906_e = par2IChunkLoader;
        field_56905_d = par3IChunkProvider;
    }

    /**
     * Checks to see if a chunk exists at x, y
     */
    public boolean chunkExists(int par1, int par2)
    {
        return field_56903_f.containsItem(ChunkCoordIntPair.chunkXZ2Int(par1, par2));
    }

    public void func_56898_d(int par1, int par2)
    {
        if (field_56910_h.worldProvider.canRespawnHere())
        {
            ChunkCoordinates chunkcoordinates = field_56910_h.getSpawnPoint();
            int i = (par1 * 16 + 8) - chunkcoordinates.posX;
            int j = (par2 * 16 + 8) - chunkcoordinates.posZ;
            char c = '\200';

            if (i < -c || i > c || j < -c || j > c)
            {
                field_56907_b.add(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(par1, par2)));
            }
        }
        else
        {
            field_56907_b.add(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(par1, par2)));
        }
    }

    public void func_56900_e()
    {
        Chunk chunk;

        for (Iterator iterator = field_56904_g.iterator(); iterator.hasNext(); func_56898_d(chunk.xPosition, chunk.zPosition))
        {
            chunk = (Chunk)iterator.next();
        }
    }

    /**
     * loads or generates the chunk at the chunk location specified
     */
    public Chunk loadChunk(int par1, int par2)
    {
        long l = ChunkCoordIntPair.chunkXZ2Int(par1, par2);
        field_56907_b.remove(Long.valueOf(l));
        Chunk chunk = (Chunk)field_56903_f.getValueByKey(l);

        if (chunk == null)
        {
            chunk = func_56901_e(par1, par2);

            if (chunk == null)
            {
                if (field_56905_d == null)
                {
                    chunk = field_56908_c;
                }
                else
                {
                    chunk = field_56905_d.provideChunk(par1, par2);
                }
            }

            field_56903_f.add(l, chunk);
            field_56904_g.add(chunk);

            if (chunk != null)
            {
                chunk.onChunkLoad();
            }

            chunk.populateChunk(this, this, par1, par2);
        }

        return chunk;
    }

    /**
     * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
     * specified chunk from the map seed and chunk seed
     */
    public Chunk provideChunk(int par1, int par2)
    {
        Chunk chunk = (Chunk)field_56903_f.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(par1, par2));

        if (chunk == null)
        {
            if (field_56910_h.findingSpawnPoint || field_56909_a)
            {
                return loadChunk(par1, par2);
            }
            else
            {
                return field_56908_c;
            }
        }
        else
        {
            return chunk;
        }
    }

    private Chunk func_56901_e(int par1, int par2)
    {
        if (field_56906_e == null)
        {
            return null;
        }

        try
        {
            Chunk chunk = field_56906_e.loadChunk(field_56910_h, par1, par2);

            if (chunk != null)
            {
                chunk.lastSaveTime = field_56910_h.getWorldTime();
            }

            return chunk;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return null;
    }

    private void func_56902_a(Chunk par1Chunk)
    {
        if (field_56906_e == null)
        {
            return;
        }

        try
        {
            field_56906_e.saveExtraChunkData(field_56910_h, par1Chunk);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private void func_56899_b(Chunk par1Chunk)
    {
        if (field_56906_e == null)
        {
            return;
        }

        try
        {
            par1Chunk.lastSaveTime = field_56910_h.getWorldTime();
            field_56906_e.saveChunk(field_56910_h, par1Chunk);
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }
        catch (MinecraftException minecraftexception)
        {
            minecraftexception.printStackTrace();
        }
    }

    /**
     * Populates chunk with ores etc etc
     */
    public void populate(IChunkProvider par1IChunkProvider, int par2, int par3)
    {
        Chunk chunk = provideChunk(par2, par3);

        if (!chunk.isTerrainPopulated)
        {
            chunk.isTerrainPopulated = true;

            if (field_56905_d != null)
            {
                field_56905_d.populate(par1IChunkProvider, par2, par3);
                chunk.setChunkModified();
            }
        }
    }

    /**
     * Two modes of operation: if passed true, save all Chunks in one go.  If passed false, save up to two chunks.
     * Return true if all chunks have been saved.
     */
    public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate)
    {
        int i = 0;

        for (Iterator iterator = field_56904_g.iterator(); iterator.hasNext();)
        {
            Chunk chunk = (Chunk)iterator.next();

            if (par1)
            {
                func_56902_a(chunk);
            }

            if (chunk.needsSaving(par1))
            {
                func_56899_b(chunk);
                chunk.isModified = false;

                if (++i == 24 && !par1)
                {
                    return false;
                }
            }
        }

        if (par1)
        {
            if (field_56906_e == null)
            {
                return true;
            }

            field_56906_e.saveExtraData();
        }

        return true;
    }

    /**
     * Unloads the 100 oldest chunks from memory, due to a bug with chunkSet.add() never being called it thinks the list
     * is always empty and will not remove any chunks.
     */
    public boolean unload100OldestChunks()
    {
        if (!field_56910_h.field_56873_K)
        {
            for (int i = 0; i < 100; i++)
            {
                if (!field_56907_b.isEmpty())
                {
                    Long long1 = (Long)field_56907_b.iterator().next();
                    Chunk chunk = (Chunk)field_56903_f.getValueByKey(long1.longValue());
                    chunk.onChunkUnload();
                    func_56899_b(chunk);
                    func_56902_a(chunk);
                    field_56907_b.remove(long1);
                    field_56903_f.remove(long1.longValue());
                    field_56904_g.remove(chunk);
                }
            }

            if (field_56906_e != null)
            {
                field_56906_e.chunkTick();
            }
        }

        return field_56905_d.unload100OldestChunks();
    }

    /**
     * Returns if the IChunkProvider supports saving.
     */
    public boolean canSave()
    {
        return !field_56910_h.field_56873_K;
    }

    /**
     * Converts the instance data to a readable string.
     */
    public String makeString()
    {
        return (new StringBuilder()).append("ServerChunkCache: ").append(field_56903_f.getNumHashElements()).append(" Drop: ").append(field_56907_b.size()).toString();
    }

    /**
     * Returns a list of creatures of the specified type that can spawn at the given location.
     */
    public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
    {
        return field_56905_d.getPossibleCreatures(par1EnumCreatureType, par2, par3, par4);
    }

    /**
     * Returns the location of the closest structure of the specified type. If not found returns null.
     */
    public ChunkPosition findClosestStructure(World par1World, String par2Str, int par3, int par4, int par5)
    {
        return field_56905_d.findClosestStructure(par1World, par2Str, par3, par4, par5);
    }

    public int func_56896_d()
    {
        return field_56903_f.getNumHashElements();
    }
}
