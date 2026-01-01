package net.minecraft.src;

public final class WorldSettings
{
    /** The seed for the map. */
    private final long seed;
    private final EnumGameType field_57459_b;

    /**
     * Switch for the map features. 'true' for enabled, 'false' for disabled.
     */
    private final boolean mapFeaturesEnabled;

    /** True if hardcore mode is enabled */
    private final boolean hardcoreEnabled;
    private final WorldType terrainType;
    private boolean field_55252_f;
    private boolean field_55253_g;

    public WorldSettings(long par1, EnumGameType par3EnumGameType, boolean par4, boolean par5, WorldType par6WorldType)
    {
        seed = par1;
        field_57459_b = par3EnumGameType;
        mapFeaturesEnabled = par4;
        hardcoreEnabled = par5;
        terrainType = par6WorldType;
    }

    public WorldSettings(WorldInfo par1WorldInfo)
    {
        this(par1WorldInfo.getSeed(), par1WorldInfo.func_56915_q(), par1WorldInfo.isMapFeaturesEnabled(), par1WorldInfo.isHardcoreModeEnabled(), par1WorldInfo.getTerrainType());
    }

    public WorldSettings func_55250_a()
    {
        field_55253_g = true;
        return this;
    }

    public WorldSettings func_55248_b()
    {
        field_55252_f = true;
        return this;
    }

    public boolean func_55251_c()
    {
        return field_55253_g;
    }

    /**
     * Returns the seed for the world.
     */
    public long getSeed()
    {
        return seed;
    }

    public EnumGameType func_57458_e()
    {
        return field_57459_b;
    }

    /**
     * Returns true if hardcore mode is enabled, otherwise false
     */
    public boolean getHardcoreEnabled()
    {
        return hardcoreEnabled;
    }

    /**
     * Get whether the map features (e.g. strongholds) generation is enabled or disabled.
     */
    public boolean isMapFeaturesEnabled()
    {
        return mapFeaturesEnabled;
    }

    public WorldType getTerrainType()
    {
        return terrainType;
    }

    public boolean func_55249_i()
    {
        return field_55252_f;
    }

    public static EnumGameType func_57457_a(int par0)
    {
        return EnumGameType.func_57536_a(par0);
    }
}
