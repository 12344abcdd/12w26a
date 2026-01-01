package net.minecraft.src;

import java.io.PrintStream;
import java.util.*;

public class WorldServer extends World
{
    private final MinecraftServer field_56866_a;
    private final EntityTracker field_56867_L;
    private final PlayerManager field_56868_M;
    private Set field_56869_N;
    private TreeSet field_56870_O;
    public ChunkProviderServer field_56871_I;
    public boolean field_56872_J;
    public boolean field_56873_K;
    private boolean field_56864_P;
    private ServerBlockEventList field_58085_P[] =
    {
        new ServerBlockEventList(null), new ServerBlockEventList(null)
    };
    private int field_58084_Q;
    private static final WeightedRandomChestContent field_56863_Q[];
    private IntHashMap field_56865_R;

    public WorldServer(MinecraftServer par1MinecraftServer, ISaveHandler par2ISaveHandler, String par3Str, int par4, WorldSettings par5WorldSettings)
    {
        super(par2ISaveHandler, par3Str, par5WorldSettings, WorldProvider.getProviderForDimension(par4));
        field_56872_J = false;
        field_58084_Q = 0;
        field_56866_a = par1MinecraftServer;
        field_56867_L = new EntityTracker(this);
        field_56868_M = new PlayerManager(this, par1MinecraftServer.func_56339_Z().func_57122_o());

        if (field_56865_R == null)
        {
            field_56865_R = new IntHashMap();
        }

        if (field_56869_N == null)
        {
            field_56869_N = new HashSet();
        }

        if (field_56870_O == null)
        {
            field_56870_O = new TreeSet();
        }
    }

    /**
     * Runs a single tick for the world
     */
    public void tick()
    {
        super.tick();

        if (getWorldInfo().isHardcoreModeEnabled() && difficultySetting < 3)
        {
            difficultySetting = 3;
        }

        worldProvider.worldChunkMgr.cleanupCache();

        if (func_56855_E())
        {
            boolean flag = false;

            if (spawnHostileMobs)
            {
                if (difficultySetting < 1);
            }

            if (!flag)
            {
                long l = worldInfo.getWorldTime() + 24000L;
                worldInfo.setWorldTime(l - l % 24000L);
                func_56862_h();
            }
        }

        Profiler.startSection("mobSpawner");
        SpawnerAnimals.func_57332_a(this, spawnHostileMobs, spawnPeacefulMobs && worldInfo.getWorldTime() % 400L == 0L);
        Profiler.endStartSection("chunkSource");
        chunkProvider.unload100OldestChunks();
        int i = calculateSkylightSubtracted(1.0F);

        if (i != skylightSubtracted)
        {
            skylightSubtracted = i;
        }

        func_58082_N();
        worldInfo.setWorldTime(worldInfo.getWorldTime() + 1L);
        Profiler.endStartSection("tickPending");
        tickUpdates(false);
        Profiler.endStartSection("tickTiles");
        tickBlocksAndAmbiance();
        Profiler.endStartSection("chunkMap");
        field_56868_M.func_57612_b();
        Profiler.endStartSection("village");
        villageCollectionObj.tick();
        villageSiegeObj.tick();
        Profiler.endSection();
        func_58082_N();
    }

    public SpawnListEntry func_56851_a(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
    {
        List list = getChunkProvider().getPossibleCreatures(par1EnumCreatureType, par2, par3, par4);

        if (list == null || list.isEmpty())
        {
            return null;
        }
        else
        {
            return (SpawnListEntry)WeightedRandom.getRandomItem(rand, list);
        }
    }

    /**
     * Updates the flag that indicates whether or not all players in the world are sleeping.
     */
    public void updateAllPlayersSleepingFlag()
    {
        field_56864_P = !playerEntities.isEmpty();
        Iterator iterator = playerEntities.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            EntityPlayer entityplayer = (EntityPlayer)iterator.next();

            if (entityplayer.isPlayerSleeping())
            {
                continue;
            }

            field_56864_P = false;
            break;
        }
        while (true);
    }

    protected void func_56862_h()
    {
        field_56864_P = false;
        Iterator iterator = playerEntities.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            EntityPlayer entityplayer = (EntityPlayer)iterator.next();

            if (entityplayer.isPlayerSleeping())
            {
                entityplayer.wakeUpPlayer(false, false, true);
            }
        }
        while (true);

        func_56860_M();
    }

    private void func_56860_M()
    {
        worldInfo.setRainTime(0);
        worldInfo.setRaining(false);
        worldInfo.setThunderTime(0);
        worldInfo.setThundering(false);
    }

    public boolean func_56855_E()
    {
        if (field_56864_P && !isRemote)
        {
            for (Iterator iterator = playerEntities.iterator(); iterator.hasNext();)
            {
                EntityPlayer entityplayer = (EntityPlayer)iterator.next();

                if (!entityplayer.isPlayerFullyAsleep())
                {
                    return false;
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Sets a new spawn location by finding an uncovered block at a random (x,z) location in the chunk.
     */
    public void setSpawnLocation()
    {
        if (worldInfo.getSpawnY() <= 0)
        {
            worldInfo.setSpawnY(64);
        }

        int i = worldInfo.getSpawnX();
        int j = worldInfo.getSpawnZ();
        int k = 0;

        do
        {
            if (getFirstUncoveredBlock(i, j) != 0)
            {
                break;
            }

            i += rand.nextInt(8) - rand.nextInt(8);
            j += rand.nextInt(8) - rand.nextInt(8);
        }
        while (++k != 10000);

        worldInfo.setSpawnX(i);
        worldInfo.setSpawnZ(j);
    }

    /**
     * plays random cave ambient sounds and runs updateTick on random blocks within each chunk in the vacinity of a
     * player
     */
    protected void tickBlocksAndAmbiance()
    {
        super.tickBlocksAndAmbiance();
        int i = 0;
        int j = 0;

        for (Iterator iterator = activeChunkSet.iterator(); iterator.hasNext(); Profiler.endSection())
        {
            ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair)iterator.next();
            int k = chunkcoordintpair.chunkXPos * 16;
            int l = chunkcoordintpair.chunkZPosition * 16;
            Profiler.startSection("getChunk");
            Chunk chunk = getChunkFromChunkCoords(chunkcoordintpair.chunkXPos, chunkcoordintpair.chunkZPosition);
            func_48458_a(k, l, chunk);
            Profiler.endStartSection("tickChunk");
            chunk.updateSkylight();
            Profiler.endStartSection("thunder");

            if (rand.nextInt(0x186a0) == 0 && isRaining() && isThundering())
            {
                updateLCG = updateLCG * 3 + 0x3c6ef35f;
                int i1 = updateLCG >> 2;
                int k1 = k + (i1 & 0xf);
                int j2 = l + (i1 >> 8 & 0xf);
                int i3 = getPrecipitationHeight(k1, j2);

                if (canLightningStrikeAt(k1, i3, j2))
                {
                    addWeatherEffect(new EntityLightningBolt(this, k1, i3, j2));
                    lastLightningBolt = 2;
                }
            }

            Profiler.endStartSection("iceandsnow");

            if (rand.nextInt(16) == 0)
            {
                updateLCG = updateLCG * 3 + 0x3c6ef35f;
                int j1 = updateLCG >> 2;
                int l1 = j1 & 0xf;
                int k2 = j1 >> 8 & 0xf;
                int j3 = getPrecipitationHeight(l1 + k, k2 + l);

                if (isBlockFreezableNaturally(l1 + k, j3 - 1, k2 + l))
                {
                    setBlockWithNotify(l1 + k, j3 - 1, k2 + l, Block.ice.blockID);
                }

                if (isRaining() && canSnowAt(l1 + k, j3, k2 + l))
                {
                    setBlockWithNotify(l1 + k, j3, k2 + l, Block.snow.blockID);
                }

                if (isRaining())
                {
                    BiomeGenBase biomegenbase = getBiomeGenForCoords(l1 + k, k2 + l);

                    if (biomegenbase.canSpawnLightningBolt())
                    {
                        int l3 = getBlockId(l1 + k, j3 - 1, k2 + l);

                        if (l3 != 0)
                        {
                            Block.blocksList[l3].func_56772_i(this, l1 + k, j3 - 1, k2 + l);
                        }
                    }
                }
            }

            Profiler.endStartSection("tickTiles");
            ExtendedBlockStorage aextendedblockstorage[] = chunk.getBlockStorageArray();
            int i2 = aextendedblockstorage.length;

            for (int l2 = 0; l2 < i2; l2++)
            {
                ExtendedBlockStorage extendedblockstorage = aextendedblockstorage[l2];

                if (extendedblockstorage == null || !extendedblockstorage.getNeedsRandomTick())
                {
                    continue;
                }

                for (int k3 = 0; k3 < 3; k3++)
                {
                    updateLCG = updateLCG * 3 + 0x3c6ef35f;
                    int i4 = updateLCG >> 2;
                    int j4 = i4 & 0xf;
                    int k4 = i4 >> 8 & 0xf;
                    int l4 = i4 >> 16 & 0xf;
                    int i5 = extendedblockstorage.getExtBlockID(j4, l4, k4);
                    j++;
                    Block block = Block.blocksList[i5];

                    if (block != null && block.getTickRandomly())
                    {
                        i++;
                        block.updateTick(this, j4 + k, l4 + extendedblockstorage.getYLocation(), k4 + l, rand);
                    }
                }
            }
        }
    }

    /**
     * Schedules a tick to a block with a delay (Most commonly the tick rate)
     */
    public void scheduleBlockUpdate(int par1, int par2, int par3, int par4, int par5)
    {
        NextTickListEntry nextticklistentry = new NextTickListEntry(par1, par2, par3, par4);
        byte byte0 = 8;

        if (scheduledUpdatesAreImmediate)
        {
            if (checkChunksExist(nextticklistentry.xCoord - byte0, nextticklistentry.yCoord - byte0, nextticklistentry.zCoord - byte0, nextticklistentry.xCoord + byte0, nextticklistentry.yCoord + byte0, nextticklistentry.zCoord + byte0))
            {
                int i = getBlockId(nextticklistentry.xCoord, nextticklistentry.yCoord, nextticklistentry.zCoord);

                if (i == nextticklistentry.blockID && i > 0)
                {
                    Block.blocksList[i].updateTick(this, nextticklistentry.xCoord, nextticklistentry.yCoord, nextticklistentry.zCoord, rand);
                }
            }

            return;
        }

        if (checkChunksExist(par1 - byte0, par2 - byte0, par3 - byte0, par1 + byte0, par2 + byte0, par3 + byte0))
        {
            if (par4 > 0)
            {
                nextticklistentry.setScheduledTime((long)par5 + worldInfo.getWorldTime());
            }

            if (!field_56869_N.contains(nextticklistentry))
            {
                field_56869_N.add(nextticklistentry);
                field_56870_O.add(nextticklistentry);
            }
        }
    }

    /**
     * Schedules a block update from the saved information in a chunk. Called when the chunk is loaded.
     */
    public void scheduleBlockUpdateFromLoad(int par1, int par2, int par3, int par4, int par5)
    {
        NextTickListEntry nextticklistentry = new NextTickListEntry(par1, par2, par3, par4);

        if (par4 > 0)
        {
            nextticklistentry.setScheduledTime((long)par5 + worldInfo.getWorldTime());
        }

        if (!field_56869_N.contains(nextticklistentry))
        {
            field_56869_N.add(nextticklistentry);
            field_56870_O.add(nextticklistentry);
        }
    }

    /**
     * Runs through the list of updates to run and ticks them
     */
    public boolean tickUpdates(boolean par1)
    {
        int i = field_56870_O.size();

        if (i != field_56869_N.size())
        {
            throw new IllegalStateException("TickNextTick list out of synch");
        }

        if (i > 1000)
        {
            i = 1000;
        }

        for (int j = 0; j < i; j++)
        {
            NextTickListEntry nextticklistentry = (NextTickListEntry)field_56870_O.first();

            if (!par1 && nextticklistentry.scheduledTime > worldInfo.getWorldTime())
            {
                break;
            }

            field_56870_O.remove(nextticklistentry);
            field_56869_N.remove(nextticklistentry);
            byte byte0 = 8;

            if (!checkChunksExist(nextticklistentry.xCoord - byte0, nextticklistentry.yCoord - byte0, nextticklistentry.zCoord - byte0, nextticklistentry.xCoord + byte0, nextticklistentry.yCoord + byte0, nextticklistentry.zCoord + byte0))
            {
                continue;
            }

            int k = getBlockId(nextticklistentry.xCoord, nextticklistentry.yCoord, nextticklistentry.zCoord);

            if (k == nextticklistentry.blockID && k > 0)
            {
                Block.blocksList[k].updateTick(this, nextticklistentry.xCoord, nextticklistentry.yCoord, nextticklistentry.zCoord, rand);
            }
        }

        return !field_56870_O.isEmpty();
    }

    public List getPendingBlockUpdates(Chunk par1Chunk, boolean par2)
    {
        ArrayList arraylist = null;
        ChunkCoordIntPair chunkcoordintpair = par1Chunk.getChunkCoordIntPair();
        int i = chunkcoordintpair.chunkXPos << 4;
        int j = i + 16;
        int k = chunkcoordintpair.chunkZPosition << 4;
        int l = k + 16;
        Iterator iterator = field_56870_O.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            NextTickListEntry nextticklistentry = (NextTickListEntry)iterator.next();

            if (nextticklistentry.xCoord >= i && nextticklistentry.xCoord < j && nextticklistentry.zCoord >= k && nextticklistentry.zCoord < l)
            {
                if (par2)
                {
                    field_56869_N.remove(nextticklistentry);
                    iterator.remove();
                }

                if (arraylist == null)
                {
                    arraylist = new ArrayList();
                }

                arraylist.add(nextticklistentry);
            }
        }
        while (true);

        return arraylist;
    }

    /**
     * Will update the entity in the world if the chunk the entity is in is currently loaded or its forced to update.
     * Args: entity, forceUpdate
     */
    public void updateEntityWithOptionalForce(Entity par1Entity, boolean par2)
    {
        if (!field_56866_a.func_56342_S() && ((par1Entity instanceof EntityAnimal) || (par1Entity instanceof EntityWaterMob)))
        {
            par1Entity.setDead();
        }

        if (!field_56866_a.func_56351_T() && (par1Entity instanceof INpc))
        {
            par1Entity.setDead();
        }

        if (!(par1Entity.riddenByEntity instanceof EntityPlayer))
        {
            super.updateEntityWithOptionalForce(par1Entity, par2);
        }
    }

    public void func_56853_b(Entity par1Entity, boolean par2)
    {
        super.updateEntityWithOptionalForce(par1Entity, par2);
    }

    /**
     * Creates the chunk provider for this world. Called in the constructor. Retrieves provider from worldProvider?
     */
    protected IChunkProvider createChunkProvider()
    {
        IChunkLoader ichunkloader = saveHandler.getChunkLoader(worldProvider);
        field_56871_I = new ChunkProviderServer(this, ichunkloader, worldProvider.getChunkProvider());
        return field_56871_I;
    }

    public List func_56857_a(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        ArrayList arraylist = new ArrayList();
        Iterator iterator = loadedTileEntityList.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            TileEntity tileentity = (TileEntity)iterator.next();

            if (tileentity.xCoord >= par1 && tileentity.yCoord >= par2 && tileentity.zCoord >= par3 && tileentity.xCoord < par4 && tileentity.yCoord < par5 && tileentity.zCoord < par6)
            {
                arraylist.add(tileentity);
            }
        }
        while (true);

        return arraylist;
    }

    /**
     * Called when checking if a certain block can be mined or not. The 'spawn safe zone' check is located here.
     */
    public boolean canMineBlock(EntityPlayer par1EntityPlayer, int par2, int par3, int par4)
    {
        int i = MathHelper.func_57511_a(par2 - worldInfo.getSpawnX());
        int j = MathHelper.func_57511_a(par4 - worldInfo.getSpawnZ());

        if (i > j)
        {
            j = i;
        }

        return j > 16 || field_56866_a.func_56339_Z().func_57087_e(par1EntityPlayer.username) || field_56866_a.func_56307_I();
    }

    protected void func_56842_a(WorldSettings par1WorldSettings)
    {
        if (field_56865_R == null)
        {
            field_56865_R = new IntHashMap();
        }

        if (field_56869_N == null)
        {
            field_56869_N = new HashSet();
        }

        if (field_56870_O == null)
        {
            field_56870_O = new TreeSet();
        }

        func_56848_b(par1WorldSettings);
        super.func_56842_a(par1WorldSettings);
    }

    protected void func_56848_b(WorldSettings par1WorldSettings)
    {
        if (!worldProvider.canRespawnHere())
        {
            worldInfo.setSpawnPosition(0, worldProvider.getAverageGroundLevel(), 0);
            return;
        }

        findingSpawnPoint = true;
        WorldChunkManager worldchunkmanager = worldProvider.worldChunkMgr;
        List list = worldchunkmanager.getBiomesToSpawnIn();
        Random random = new Random(getSeed());
        ChunkPosition chunkposition = worldchunkmanager.findBiomePosition(0, 0, 256, list, random);
        int i = 0;
        int j = worldProvider.getAverageGroundLevel();
        int k = 0;

        if (chunkposition != null)
        {
            i = chunkposition.x;
            k = chunkposition.z;
        }
        else
        {
            System.out.println("Unable to find spawn biome");
        }

        int l = 0;

        do
        {
            if (worldProvider.canCoordinateBeSpawn(i, k))
            {
                break;
            }

            i += random.nextInt(64) - random.nextInt(64);
            k += random.nextInt(64) - random.nextInt(64);
        }
        while (++l != 1000);

        worldInfo.setSpawnPosition(i, j, k);
        findingSpawnPoint = false;

        if (par1WorldSettings.func_55251_c())
        {
            func_56846_F();
        }
    }

    protected void func_56846_F()
    {
        WorldGeneratorBonusChest worldgeneratorbonuschest = new WorldGeneratorBonusChest(field_56863_Q, 10);
        int i = 0;

        do
        {
            if (i >= 10)
            {
                break;
            }

            int j = (worldInfo.getSpawnX() + rand.nextInt(6)) - rand.nextInt(6);
            int k = (worldInfo.getSpawnZ() + rand.nextInt(6)) - rand.nextInt(6);
            int l = getTopSolidOrLiquidBlock(j, k) + 1;

            if (worldgeneratorbonuschest.generate(this, rand, j, l, k))
            {
                break;
            }

            i++;
        }
        while (true);
    }

    public ChunkCoordinates func_56847_G()
    {
        return worldProvider.getEntrancePortalLocation();
    }

    public void func_56849_a(boolean par1, IProgressUpdate par2IProgressUpdate) throws MinecraftException
    {
        if (!chunkProvider.canSave())
        {
            return;
        }

        if (par2IProgressUpdate != null)
        {
            par2IProgressUpdate.displaySavingString("Saving level");
        }

        func_56854_H();

        if (par2IProgressUpdate != null)
        {
            par2IProgressUpdate.displayLoadingString("Saving chunks");
        }

        chunkProvider.saveChunks(par1, par2IProgressUpdate);
    }

    protected void func_56854_H() throws MinecraftException
    {
        checkSessionLock();
        saveHandler.func_56805_a(worldInfo, field_56866_a.func_56339_Z().func_57119_q());
        mapStorage.saveAllData();
    }

    /**
     * Start the skin for this entity downloading, if necessary, and increment its reference counter
     */
    protected void obtainEntitySkin(Entity par1Entity)
    {
        super.obtainEntitySkin(par1Entity);
        field_56865_R.addKey(par1Entity.entityId, par1Entity);
        Entity aentity[] = par1Entity.getParts();

        if (aentity != null)
        {
            Entity aentity1[] = aentity;
            int i = aentity1.length;

            for (int j = 0; j < i; j++)
            {
                Entity entity = aentity1[j];
                field_56865_R.addKey(entity.entityId, entity);
            }
        }
    }

    /**
     * Decrement the reference counter for this entity's skin image data
     */
    protected void releaseEntitySkin(Entity par1Entity)
    {
        super.releaseEntitySkin(par1Entity);
        field_56865_R.removeObject(par1Entity.entityId);
        Entity aentity[] = par1Entity.getParts();

        if (aentity != null)
        {
            Entity aentity1[] = aentity;
            int i = aentity1.length;

            for (int j = 0; j < i; j++)
            {
                Entity entity = aentity1[j];
                field_56865_R.removeObject(entity.entityId);
            }
        }
    }

    public Entity func_56861_a(int par1)
    {
        return (Entity)field_56865_R.lookup(par1);
    }

    /**
     * adds a lightning bolt to the list of lightning bolts in this world.
     */
    public boolean addWeatherEffect(Entity par1Entity)
    {
        if (super.addWeatherEffect(par1Entity))
        {
            field_56866_a.func_56339_Z().func_57095_a(par1Entity.posX, par1Entity.posY, par1Entity.posZ, 512D, worldProvider.worldType, new Packet71Weather(par1Entity));
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * sends a Packet 38 (Entity Status) to all tracked players of that entity
     */
    public void setEntityState(Entity par1Entity, byte par2)
    {
        Packet38EntityStatus packet38entitystatus = new Packet38EntityStatus(par1Entity.entityId, par2);
        func_56859_K().func_57596_b(par1Entity, packet38entitystatus);
    }

    /**
     * returns a new explosion. Does initiation (at time of writing Explosion is not finished)
     */
    public Explosion newExplosion(Entity par1Entity, double par2, double par4, double par6, float par8, boolean par9)
    {
        Explosion explosion = new Explosion(this, par1Entity, par2, par4, par6, par8);
        explosion.isFlaming = par9;
        explosion.doExplosionA();
        explosion.doExplosionB(false);
        Iterator iterator = playerEntities.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            EntityPlayer entityplayer = (EntityPlayer)iterator.next();

            if (entityplayer.getDistance(par2, par4, par6) < 64D)
            {
                ((EntityPlayerMP)entityplayer).field_56268_a.func_56717_b(new Packet60Explosion(par2, par4, par6, par8, explosion.field_58127_g, (Vec3)explosion.func_57169_b().get(entityplayer)));
            }
        }
        while (true);

        return explosion;
    }

    /**
     * plays a given note at x, y, z. args: x, y, z, instrument, note
     */
    public void playNoteAt(int par1, int par2, int par3, int par4, int par5)
    {
        field_58085_P[field_58084_Q].add(new BlockEventData(par1, par2, par3, par4, par5));
    }

    private void func_58082_N()
    {
        int i;
        label0:

        for (; !field_58085_P[field_58084_Q].isEmpty(); field_58085_P[i].clear())
        {
            i = field_58084_Q;
            field_58084_Q ^= 1;
            Iterator iterator = field_58085_P[i].iterator();

            do
            {
                if (!iterator.hasNext())
                {
                    continue label0;
                }

                BlockEventData blockeventdata = (BlockEventData)iterator.next();

                if (func_58083_a(blockeventdata))
                {
                    field_56866_a.func_56339_Z().func_57095_a(blockeventdata.func_58106_a(), blockeventdata.func_58108_b(), blockeventdata.func_58107_c(), 64D, worldProvider.worldType, new Packet54PlayNoteBlock(blockeventdata.func_58106_a(), blockeventdata.func_58108_b(), blockeventdata.func_58107_c(), blockeventdata.func_58105_d(), blockeventdata.func_58104_e()));
                }
            }
            while (true);
        }
    }

    private boolean func_58083_a(BlockEventData par1BlockEventData)
    {
        int i = getBlockId(par1BlockEventData.func_58106_a(), par1BlockEventData.func_58108_b(), par1BlockEventData.func_58107_c());

        if (i > 0)
        {
            Block.blocksList[i].powerBlock(this, par1BlockEventData.func_58106_a(), par1BlockEventData.func_58108_b(), par1BlockEventData.func_58107_c(), par1BlockEventData.func_58105_d(), par1BlockEventData.func_58104_e());
            return true;
        }
        else
        {
            return false;
        }
    }

    public void func_56850_I()
    {
        saveHandler.func_56806_f();
    }

    /**
     * Updates all weather states.
     */
    protected void updateWeather()
    {
        boolean flag = isRaining();
        super.updateWeather();

        if (flag != isRaining())
        {
            if (flag)
            {
                field_56866_a.func_56339_Z().func_57114_a(new Packet70GameEvent(2, 0));
            }
            else
            {
                field_56866_a.func_56339_Z().func_57114_a(new Packet70GameEvent(1, 0));
            }
        }
    }

    public MinecraftServer func_56852_J()
    {
        return field_56866_a;
    }

    public EntityTracker func_56859_K()
    {
        return field_56867_L;
    }

    public void func_56856_b(long par1)
    {
        long l = par1 - worldInfo.getWorldTime();

        for (Iterator iterator = field_56869_N.iterator(); iterator.hasNext();)
        {
            NextTickListEntry nextticklistentry = (NextTickListEntry)iterator.next();
            nextticklistentry.scheduledTime += l;
        }

        Block ablock[] = Block.blocksList;
        int i = ablock.length;

        for (int j = 0; j < i; j++)
        {
            Block block = ablock[j];

            if (block != null)
            {
                block.func_56768_a(this, l, par1);
            }
        }

        setWorldTime(par1);
    }

    public PlayerManager func_56858_L()
    {
        return field_56868_M;
    }

    static
    {
        field_56863_Q = (new WeightedRandomChestContent[]
                {
                    new WeightedRandomChestContent(Item.stick.shiftedIndex, 0, 1, 3, 10), new WeightedRandomChestContent(Block.planks.blockID, 0, 1, 3, 10), new WeightedRandomChestContent(Block.wood.blockID, 0, 1, 3, 10), new WeightedRandomChestContent(Item.axeStone.shiftedIndex, 0, 1, 1, 3), new WeightedRandomChestContent(Item.axeWood.shiftedIndex, 0, 1, 1, 5), new WeightedRandomChestContent(Item.pickaxeStone.shiftedIndex, 0, 1, 1, 3), new WeightedRandomChestContent(Item.pickaxeWood.shiftedIndex, 0, 1, 1, 5), new WeightedRandomChestContent(Item.appleRed.shiftedIndex, 0, 2, 3, 5), new WeightedRandomChestContent(Item.bread.shiftedIndex, 0, 2, 3, 3)
                });
    }
}
