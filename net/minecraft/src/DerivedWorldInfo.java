package net.minecraft.src;

public class DerivedWorldInfo extends WorldInfo
{
    private final WorldInfo field_56918_a;

    public DerivedWorldInfo(WorldInfo par1WorldInfo)
    {
        field_56918_a = par1WorldInfo;
    }

    /**
     * Gets the NBTTagCompound for the worldInfo
     */
    public NBTTagCompound getNBTTagCompound()
    {
        return field_56918_a.getNBTTagCompound();
    }

    public NBTTagCompound func_56912_a(NBTTagCompound par1NBTTagCompound)
    {
        return field_56918_a.func_56912_a(par1NBTTagCompound);
    }

    /**
     * Returns the seed of current world.
     */
    public long getSeed()
    {
        return field_56918_a.getSeed();
    }

    /**
     * Returns the x spawn position
     */
    public int getSpawnX()
    {
        return field_56918_a.getSpawnX();
    }

    /**
     * Return the Y axis spawning point of the player.
     */
    public int getSpawnY()
    {
        return field_56918_a.getSpawnY();
    }

    /**
     * Returns the z spawn position
     */
    public int getSpawnZ()
    {
        return field_56918_a.getSpawnZ();
    }

    /**
     * Get current world time
     */
    public long getWorldTime()
    {
        return field_56918_a.getWorldTime();
    }

    public long getSizeOnDisk()
    {
        return field_56918_a.getSizeOnDisk();
    }

    /**
     * Returns the player's NBTTagCompound to be loaded
     */
    public NBTTagCompound getPlayerNBTTagCompound()
    {
        return field_56918_a.getPlayerNBTTagCompound();
    }

    public int getDimension()
    {
        return field_56918_a.getDimension();
    }

    /**
     * Get current world name
     */
    public String getWorldName()
    {
        return field_56918_a.getWorldName();
    }

    /**
     * Returns the save version of this world
     */
    public int getSaveVersion()
    {
        return field_56918_a.getSaveVersion();
    }

    /**
     * Return the last time the player was in this world.
     */
    public long getLastTimePlayed()
    {
        return field_56918_a.getLastTimePlayed();
    }

    /**
     * Returns true if it is thundering, false otherwise.
     */
    public boolean isThundering()
    {
        return field_56918_a.isThundering();
    }

    /**
     * Returns the number of ticks until next thunderbolt.
     */
    public int getThunderTime()
    {
        return field_56918_a.getThunderTime();
    }

    /**
     * Returns true if it is raining, false otherwise.
     */
    public boolean isRaining()
    {
        return field_56918_a.isRaining();
    }

    /**
     * Return the number of ticks until rain.
     */
    public int getRainTime()
    {
        return field_56918_a.getRainTime();
    }

    public EnumGameType func_56915_q()
    {
        return field_56918_a.func_56915_q();
    }

    /**
     * Set the x spawn position to the passed in value
     */
    public void setSpawnX(int i)
    {
    }

    /**
     * Sets the y spawn position
     */
    public void setSpawnY(int i)
    {
    }

    /**
     * Set the z spawn position to the passed in value
     */
    public void setSpawnZ(int i)
    {
    }

    /**
     * Set current world time
     */
    public void setWorldTime(long l)
    {
    }

    /**
     * Sets the spawn zone position. Args: x, y, z
     */
    public void setSpawnPosition(int i, int j, int k)
    {
    }

    public void setWorldName(String s)
    {
    }

    /**
     * Sets the save version of the world
     */
    public void setSaveVersion(int i)
    {
    }

    /**
     * Sets whether it is thundering or not.
     */
    public void setThundering(boolean flag)
    {
    }

    /**
     * Defines the number of ticks until next thunderbolt.
     */
    public void setThunderTime(int i)
    {
    }

    /**
     * Sets whether it is raining or not.
     */
    public void setRaining(boolean flag)
    {
    }

    /**
     * Sets the number of ticks until rain.
     */
    public void setRainTime(int i)
    {
    }

    /**
     * Get whether the map features (e.g. strongholds) generation is enabled or disabled.
     */
    public boolean isMapFeaturesEnabled()
    {
        return field_56918_a.isMapFeaturesEnabled();
    }

    /**
     * Returns true if hardcore mode is enabled, otherwise false
     */
    public boolean isHardcoreModeEnabled()
    {
        return field_56918_a.isHardcoreModeEnabled();
    }

    public WorldType getTerrainType()
    {
        return field_56918_a.getTerrainType();
    }

    public void setTerrainType(WorldType worldtype)
    {
    }

    public boolean func_55400_u()
    {
        return field_56918_a.func_55400_u();
    }

    public boolean func_56914_v()
    {
        return field_56918_a.func_56914_v();
    }

    public void func_56913_c(boolean flag)
    {
    }
}
