package net.minecraft.src;

import java.io.File;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

public abstract class ServerConfigurationManager
{
    private static final SimpleDateFormat field_57132_e = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    public static final Logger field_57135_a = Logger.getLogger("Minecraft");
    private final MinecraftServer field_57129_f;
    public final List field_57133_b = new ArrayList();
    private final BanList field_57130_g = new BanList(new File("banned-players.txt"));
    private final BanList field_57140_h = new BanList(new File("banned-ips.txt"));
    private Set field_57141_i;
    private Set field_57138_j;
    private IPlayerFileData field_57139_k;
    private boolean field_57136_l;
    protected int field_57134_c;
    protected int field_57131_d;
    private EnumGameType field_58126_m;
    private boolean field_58125_n;
    private int field_57137_m;

    public ServerConfigurationManager(MinecraftServer par1MinecraftServer)
    {
        field_57141_i = new HashSet();
        field_57138_j = new HashSet();
        field_57137_m = 0;
        field_57129_f = par1MinecraftServer;
        field_57130_g.func_57343_a(false);
        field_57140_h.func_57343_a(false);
        field_57134_c = 8;
    }

    public void func_57113_a(NetworkManager par1NetworkManager, EntityPlayerMP par2EntityPlayerMP)
    {
        func_57088_a(par2EntityPlayerMP);
        par2EntityPlayerMP.setWorld(field_57129_f.func_56325_a(par2EntityPlayerMP.dimension));
        par2EntityPlayerMP.field_56267_c.func_57360_a((WorldServer)par2EntityPlayerMP.worldObj);
        field_57135_a.info((new StringBuilder()).append(par2EntityPlayerMP.username).append(" logged in with entity id ").append(par2EntityPlayerMP.entityId).append(" at (").append(par2EntityPlayerMP.posX).append(", ").append(par2EntityPlayerMP.posY).append(", ").append(par2EntityPlayerMP.posZ).append(")").toString());
        WorldServer worldserver = field_57129_f.func_56325_a(par2EntityPlayerMP.dimension);
        ChunkCoordinates chunkcoordinates = worldserver.getSpawnPoint();
        func_58121_a(par2EntityPlayerMP, null, worldserver);
        NetServerHandler netserverhandler = new NetServerHandler(field_57129_f, par1NetworkManager, par2EntityPlayerMP);
        netserverhandler.func_56717_b(new Packet1Login(par2EntityPlayerMP.entityId, worldserver.getWorldInfo().getTerrainType(), par2EntityPlayerMP.field_56267_c.func_57362_b(), worldserver.getWorldInfo().isHardcoreModeEnabled(), worldserver.worldProvider.worldType, worldserver.difficultySetting, worldserver.getHeight(), func_57086_l()));
        netserverhandler.func_56717_b(new Packet6SpawnPosition(chunkcoordinates.posX, chunkcoordinates.posY, chunkcoordinates.posZ));
        netserverhandler.func_56717_b(new Packet202PlayerAbilities(par2EntityPlayerMP.capabilities));
        func_57120_b(par2EntityPlayerMP, worldserver);
        func_57114_a(new Packet3Chat((new StringBuilder()).append("\247e").append(par2EntityPlayerMP.username).append(" joined the game.").toString()));
        func_57126_c(par2EntityPlayerMP);
        netserverhandler.func_56719_a(par2EntityPlayerMP.posX, par2EntityPlayerMP.posY, par2EntityPlayerMP.posZ, par2EntityPlayerMP.rotationYaw, par2EntityPlayerMP.rotationPitch);
        field_57129_f.func_56290_aa().func_56961_a(netserverhandler);
        netserverhandler.func_56717_b(new Packet4UpdateTime(worldserver.getWorldTime()));

        if (field_57129_f.func_56310_O().length() > 0)
        {
            par2EntityPlayerMP.func_56250_a(field_57129_f.func_56310_O(), field_57129_f.func_56305_P());
        }

        PotionEffect potioneffect;

        for (Iterator iterator = par2EntityPlayerMP.getActivePotionEffects().iterator(); iterator.hasNext(); netserverhandler.func_56717_b(new Packet41EntityEffect(par2EntityPlayerMP.entityId, potioneffect)))
        {
            potioneffect = (PotionEffect)iterator.next();
        }

        par2EntityPlayerMP.func_56256_h();
    }

    public void func_57096_a(WorldServer par1ArrayOfWorldServer[])
    {
        field_57139_k = par1ArrayOfWorldServer[0].getSaveHandler().func_56804_d();
    }

    public void func_57107_a(EntityPlayerMP par1EntityPlayerMP, WorldServer par2WorldServer)
    {
        WorldServer worldserver = par1EntityPlayerMP.func_56260_H();

        if (par2WorldServer != null)
        {
            par2WorldServer.func_56858_L().func_57614_c(par1EntityPlayerMP);
        }

        worldserver.func_56858_L().func_57602_a(par1EntityPlayerMP);
        worldserver.field_56871_I.loadChunk((int)par1EntityPlayerMP.posX >> 4, (int)par1EntityPlayerMP.posZ >> 4);
    }

    public int func_57100_a()
    {
        return PlayerManager.func_57605_a(func_57122_o());
    }

    public void func_57088_a(EntityPlayerMP par1EntityPlayerMP)
    {
        NBTTagCompound nbttagcompound = field_57129_f.field_56392_b[0].getWorldInfo().getPlayerNBTTagCompound();

        if (par1EntityPlayerMP.func_55088_aJ().equals(field_57129_f.func_56296_H()) && nbttagcompound != null)
        {
            par1EntityPlayerMP.readFromNBT(nbttagcompound);
        }
        else
        {
            field_57139_k.func_56807_b(par1EntityPlayerMP);
        }
    }

    protected void func_57118_b(EntityPlayerMP par1EntityPlayerMP)
    {
        field_57139_k.func_56809_a(par1EntityPlayerMP);
    }

    public void func_57126_c(EntityPlayerMP par1EntityPlayerMP)
    {
        func_57114_a(new Packet201PlayerInfo(par1EntityPlayerMP.username, true, 1000));
        field_57133_b.add(par1EntityPlayerMP);
        WorldServer worldserver;

        for (worldserver = field_57129_f.func_56325_a(par1EntityPlayerMP.dimension); !worldserver.getCollidingBoundingBoxes(par1EntityPlayerMP, par1EntityPlayerMP.boundingBox).isEmpty(); par1EntityPlayerMP.setPosition(par1EntityPlayerMP.posX, par1EntityPlayerMP.posY + 1.0D, par1EntityPlayerMP.posZ)) { }

        worldserver.spawnEntityInWorld(par1EntityPlayerMP);
        func_57107_a(par1EntityPlayerMP, null);
        EntityPlayerMP entityplayermp;

        for (Iterator iterator = field_57133_b.iterator(); iterator.hasNext(); par1EntityPlayerMP.field_56268_a.func_56717_b(new Packet201PlayerInfo(entityplayermp.username, true, entityplayermp.field_56273_h)))
        {
            entityplayermp = (EntityPlayerMP)iterator.next();
        }
    }

    public void func_57106_d(EntityPlayerMP par1EntityPlayerMP)
    {
        par1EntityPlayerMP.func_56260_H().func_56858_L().func_57613_d(par1EntityPlayerMP);
    }

    public void func_57093_e(EntityPlayerMP par1EntityPlayerMP)
    {
        func_57118_b(par1EntityPlayerMP);
        WorldServer worldserver = par1EntityPlayerMP.func_56260_H();
        worldserver.setEntityDead(par1EntityPlayerMP);
        worldserver.func_56858_L().func_57614_c(par1EntityPlayerMP);
        field_57133_b.remove(par1EntityPlayerMP);
        func_57114_a(new Packet201PlayerInfo(par1EntityPlayerMP.username, false, 9999));
    }

    public String func_57128_a(SocketAddress par1SocketAddress, String par2Str)
    {
        if (field_57130_g.func_57339_a(par2Str))
        {
            BanEntry banentry = (BanEntry)field_57130_g.func_57340_b().get(par2Str);
            String s1 = (new StringBuilder()).append("You are banned from this server!\nReason: ").append(banentry.func_57018_f()).toString();

            if (banentry.func_57011_d() != null)
            {
                s1 = (new StringBuilder()).append(s1).append("\nYour ban will be removed on ").append(field_57132_e.format(banentry.func_57011_d())).toString();
            }

            return s1;
        }

        if (!func_57098_d(par2Str))
        {
            return "You are not white-listed on this server!";
        }

        String s = par1SocketAddress.toString();
        s = s.substring(s.indexOf("/") + 1);
        s = s.substring(0, s.indexOf(":"));

        if (field_57140_h.func_57339_a(s))
        {
            BanEntry banentry1 = (BanEntry)field_57140_h.func_57340_b().get(s);
            String s2 = (new StringBuilder()).append("Your IP address is banned from this server!\nReason: ").append(banentry1.func_57018_f()).toString();

            if (banentry1.func_57011_d() != null)
            {
                s2 = (new StringBuilder()).append(s2).append("\nYour ban will be removed on ").append(field_57132_e.format(banentry1.func_57011_d())).toString();
            }

            return s2;
        }

        if (field_57133_b.size() >= field_57134_c)
        {
            return "The server is full!";
        }
        else
        {
            return null;
        }
    }

    public EntityPlayerMP func_57123_a(String par1Str)
    {
        ArrayList arraylist = new ArrayList();
        Object obj = field_57133_b.iterator();

        do
        {
            if (!((Iterator)(obj)).hasNext())
            {
                break;
            }

            EntityPlayerMP entityplayermp = (EntityPlayerMP)((Iterator)(obj)).next();

            if (entityplayermp.username.equalsIgnoreCase(par1Str))
            {
                arraylist.add(entityplayermp);
            }
        }
        while (true);

        EntityPlayerMP entityplayermp1;

        for (obj = arraylist.iterator(); ((Iterator)(obj)).hasNext(); entityplayermp1.field_56268_a.func_56716_a("You logged in from another location"))
        {
            entityplayermp1 = (EntityPlayerMP)((Iterator)(obj)).next();
        }

        if (field_57129_f.func_56298_L())
        {
            obj = new DemoWorldManager(field_57129_f.func_56325_a(0));
        }
        else
        {
            obj = new ItemInWorldManager(field_57129_f.func_56325_a(0));
        }

        return new EntityPlayerMP(field_57129_f, field_57129_f.func_56325_a(0), par1Str, ((ItemInWorldManager)(obj)));
    }

    public EntityPlayerMP func_57112_a(EntityPlayerMP par1EntityPlayerMP, int par2, boolean par3)
    {
        par1EntityPlayerMP.func_56260_H().func_56859_K().func_57590_a(par1EntityPlayerMP);
        par1EntityPlayerMP.func_56260_H().func_56859_K().func_57594_b(par1EntityPlayerMP);
        par1EntityPlayerMP.func_56260_H().func_56858_L().func_57614_c(par1EntityPlayerMP);
        field_57133_b.remove(par1EntityPlayerMP);
        field_57129_f.func_56325_a(par1EntityPlayerMP.dimension).func_56839_f(par1EntityPlayerMP);
        ChunkCoordinates chunkcoordinates = par1EntityPlayerMP.getSpawnChunk();
        par1EntityPlayerMP.dimension = par2;
        Object obj;

        if (field_57129_f.func_56298_L())
        {
            obj = new DemoWorldManager(field_57129_f.func_56325_a(par1EntityPlayerMP.dimension));
        }
        else
        {
            obj = new ItemInWorldManager(field_57129_f.func_56325_a(par1EntityPlayerMP.dimension));
        }

        EntityPlayerMP entityplayermp = new EntityPlayerMP(field_57129_f, field_57129_f.func_56325_a(par1EntityPlayerMP.dimension), par1EntityPlayerMP.username, ((ItemInWorldManager)(obj)));
        entityplayermp.func_58026_a(par1EntityPlayerMP, par3);
        entityplayermp.entityId = par1EntityPlayerMP.entityId;
        entityplayermp.field_56268_a = par1EntityPlayerMP.field_56268_a;
        WorldServer worldserver = field_57129_f.func_56325_a(par1EntityPlayerMP.dimension);
        func_58121_a(entityplayermp, par1EntityPlayerMP, worldserver);

        if (chunkcoordinates != null)
        {
            ChunkCoordinates chunkcoordinates1 = EntityPlayer.verifyRespawnCoordinates(field_57129_f.func_56325_a(par1EntityPlayerMP.dimension), chunkcoordinates);

            if (chunkcoordinates1 != null)
            {
                entityplayermp.setLocationAndAngles((float)chunkcoordinates1.posX + 0.5F, (float)chunkcoordinates1.posY + 0.1F, (float)chunkcoordinates1.posZ + 0.5F, 0.0F, 0.0F);
                entityplayermp.setSpawnChunk(chunkcoordinates);
            }
            else
            {
                entityplayermp.field_56268_a.func_56717_b(new Packet70GameEvent(0, 0));
            }
        }

        worldserver.field_56871_I.loadChunk((int)entityplayermp.posX >> 4, (int)entityplayermp.posZ >> 4);

        for (; !worldserver.getCollidingBoundingBoxes(entityplayermp, entityplayermp.boundingBox).isEmpty(); entityplayermp.setPosition(entityplayermp.posX, entityplayermp.posY + 1.0D, entityplayermp.posZ)) { }

        entityplayermp.field_56268_a.func_56717_b(new Packet9Respawn(entityplayermp.dimension, (byte)entityplayermp.worldObj.difficultySetting, entityplayermp.worldObj.getWorldInfo().getTerrainType(), entityplayermp.worldObj.getHeight(), entityplayermp.field_56267_c.func_57362_b()));
        entityplayermp.field_56268_a.func_56719_a(entityplayermp.posX, entityplayermp.posY, entityplayermp.posZ, entityplayermp.rotationYaw, entityplayermp.rotationPitch);
        func_57120_b(entityplayermp, worldserver);
        worldserver.func_56858_L().func_57602_a(entityplayermp);
        worldserver.spawnEntityInWorld(entityplayermp);
        field_57133_b.add(entityplayermp);
        entityplayermp.func_56256_h();
        return entityplayermp;
    }

    public void func_57125_a(EntityPlayerMP par1EntityPlayerMP, int par2)
    {
        int i = par1EntityPlayerMP.dimension;
        WorldServer worldserver = field_57129_f.func_56325_a(par1EntityPlayerMP.dimension);
        par1EntityPlayerMP.dimension = par2;
        WorldServer worldserver1 = field_57129_f.func_56325_a(par1EntityPlayerMP.dimension);
        par1EntityPlayerMP.field_56268_a.func_56717_b(new Packet9Respawn(par1EntityPlayerMP.dimension, (byte)par1EntityPlayerMP.worldObj.difficultySetting, worldserver1.getWorldInfo().getTerrainType(), worldserver1.getHeight(), par1EntityPlayerMP.field_56267_c.func_57362_b()));
        worldserver.func_56839_f(par1EntityPlayerMP);
        par1EntityPlayerMP.isDead = false;
        double d = par1EntityPlayerMP.posX;
        double d1 = par1EntityPlayerMP.posZ;
        double d2 = 8D;

        if (par1EntityPlayerMP.dimension == -1)
        {
            d /= d2;
            d1 /= d2;
            par1EntityPlayerMP.setLocationAndAngles(d, par1EntityPlayerMP.posY, d1, par1EntityPlayerMP.rotationYaw, par1EntityPlayerMP.rotationPitch);

            if (par1EntityPlayerMP.isEntityAlive())
            {
                worldserver.updateEntityWithOptionalForce(par1EntityPlayerMP, false);
            }
        }
        else if (par1EntityPlayerMP.dimension == 0)
        {
            d *= d2;
            d1 *= d2;
            par1EntityPlayerMP.setLocationAndAngles(d, par1EntityPlayerMP.posY, d1, par1EntityPlayerMP.rotationYaw, par1EntityPlayerMP.rotationPitch);

            if (par1EntityPlayerMP.isEntityAlive())
            {
                worldserver.updateEntityWithOptionalForce(par1EntityPlayerMP, false);
            }
        }
        else
        {
            ChunkCoordinates chunkcoordinates = worldserver1.func_56847_G();
            d = chunkcoordinates.posX;
            par1EntityPlayerMP.posY = chunkcoordinates.posY;
            d1 = chunkcoordinates.posZ;
            par1EntityPlayerMP.setLocationAndAngles(d, par1EntityPlayerMP.posY, d1, 90F, 0.0F);

            if (par1EntityPlayerMP.isEntityAlive())
            {
                worldserver.updateEntityWithOptionalForce(par1EntityPlayerMP, false);
            }
        }

        if (i != 1 && par1EntityPlayerMP.isEntityAlive())
        {
            worldserver1.spawnEntityInWorld(par1EntityPlayerMP);
            par1EntityPlayerMP.setLocationAndAngles(d, par1EntityPlayerMP.posY, d1, par1EntityPlayerMP.rotationYaw, par1EntityPlayerMP.rotationPitch);
            worldserver1.updateEntityWithOptionalForce(par1EntityPlayerMP, false);
            (new Teleporter()).placeInPortal(worldserver1, par1EntityPlayerMP);
        }

        par1EntityPlayerMP.setWorld(worldserver1);
        func_57107_a(par1EntityPlayerMP, worldserver);
        par1EntityPlayerMP.field_56268_a.func_56719_a(par1EntityPlayerMP.posX, par1EntityPlayerMP.posY, par1EntityPlayerMP.posZ, par1EntityPlayerMP.rotationYaw, par1EntityPlayerMP.rotationPitch);
        par1EntityPlayerMP.field_56267_c.func_57360_a(worldserver1);
        func_57120_b(par1EntityPlayerMP, worldserver1);
        func_57124_f(par1EntityPlayerMP);
    }

    public void func_57104_b()
    {
        if (++field_57137_m > 200)
        {
            field_57137_m = 0;
        }

        if (field_57137_m < field_57133_b.size())
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)field_57133_b.get(field_57137_m);
            func_57114_a(new Packet201PlayerInfo(entityplayermp.username, true, entityplayermp.field_56273_h));
        }
    }

    public void func_57114_a(Packet par1Packet)
    {
        EntityPlayerMP entityplayermp;

        for (Iterator iterator = field_57133_b.iterator(); iterator.hasNext(); entityplayermp.field_56268_a.func_56717_b(par1Packet))
        {
            entityplayermp = (EntityPlayerMP)iterator.next();
        }
    }

    public void func_57105_a(Packet par1Packet, int par2)
    {
        Iterator iterator = field_57133_b.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();

            if (entityplayermp.dimension == par2)
            {
                entityplayermp.field_56268_a.func_56717_b(par1Packet);
            }
        }
        while (true);
    }

    public String func_57127_c()
    {
        String s = "";

        for (int i = 0; i < field_57133_b.size(); i++)
        {
            if (i > 0)
            {
                s = (new StringBuilder()).append(s).append(", ").toString();
            }

            s = (new StringBuilder()).append(s).append(((EntityPlayerMP)field_57133_b.get(i)).username).toString();
        }

        return s;
    }

    public String[] func_57097_d()
    {
        String as[] = new String[field_57133_b.size()];

        for (int i = 0; i < field_57133_b.size(); i++)
        {
            as[i] = ((EntityPlayerMP)field_57133_b.get(i)).username;
        }

        return as;
    }

    public BanList func_57110_e()
    {
        return field_57130_g;
    }

    public BanList func_57101_f()
    {
        return field_57140_h;
    }

    public void func_57115_b(String par1Str)
    {
        field_57141_i.add(par1Str.toLowerCase());
    }

    public void func_57091_c(String par1Str)
    {
        field_57141_i.remove(par1Str.toLowerCase());
    }

    public boolean func_57098_d(String par1Str)
    {
        par1Str = par1Str.trim().toLowerCase();
        return !field_57136_l || field_57141_i.contains(par1Str) || field_57138_j.contains(par1Str);
    }

    public boolean func_57087_e(String par1Str)
    {
        return field_57141_i.contains(par1Str.trim().toLowerCase()) || field_57129_f.func_56307_I() && field_57129_f.field_56392_b[0].getWorldInfo().func_55400_u() && field_57129_f.func_56296_H().equalsIgnoreCase(par1Str) || field_58125_n;
    }

    public EntityPlayerMP func_57094_f(String par1Str)
    {
        for (Iterator iterator = field_57133_b.iterator(); iterator.hasNext();)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();

            if (entityplayermp.username.equalsIgnoreCase(par1Str))
            {
                return entityplayermp;
            }
        }

        return null;
    }

    public void func_57095_a(double par1, double par3, double par5, double par7, int par9, Packet par10Packet)
    {
        func_57111_a(null, par1, par3, par5, par7, par9, par10Packet);
    }

    public void func_57111_a(EntityPlayer par1EntityPlayer, double par2, double par4, double par6, double par8, int par10, Packet par11Packet)
    {
        Iterator iterator = field_57133_b.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();

            if (entityplayermp != par1EntityPlayer && entityplayermp.dimension == par10)
            {
                double d = par2 - entityplayermp.posX;
                double d1 = par4 - entityplayermp.posY;
                double d2 = par6 - entityplayermp.posZ;

                if (d * d + d1 * d1 + d2 * d2 < par8 * par8)
                {
                    entityplayermp.field_56268_a.func_56717_b(par11Packet);
                }
            }
        }
        while (true);
    }

    public void func_57117_g()
    {
        EntityPlayerMP entityplayermp;

        for (Iterator iterator = field_57133_b.iterator(); iterator.hasNext(); func_57118_b(entityplayermp))
        {
            entityplayermp = (EntityPlayerMP)iterator.next();
        }
    }

    public void func_57103_g(String par1Str)
    {
        field_57138_j.add(par1Str);
    }

    public void func_57090_h(String par1Str)
    {
        field_57138_j.remove(par1Str);
    }

    public Set func_57116_h()
    {
        return field_57138_j;
    }

    public Set func_57108_i()
    {
        return field_57141_i;
    }

    public void func_57092_j()
    {
    }

    public void func_57120_b(EntityPlayerMP par1EntityPlayerMP, WorldServer par2WorldServer)
    {
        par1EntityPlayerMP.field_56268_a.func_56717_b(new Packet4UpdateTime(par2WorldServer.getWorldTime()));

        if (par2WorldServer.isRaining())
        {
            par1EntityPlayerMP.field_56268_a.func_56717_b(new Packet70GameEvent(1, 0));
        }
    }

    public void func_57124_f(EntityPlayerMP par1EntityPlayerMP)
    {
        par1EntityPlayerMP.func_56259_a(par1EntityPlayerMP.inventorySlots);
        par1EntityPlayerMP.func_56254_C_();
    }

    public int func_57121_k()
    {
        return field_57133_b.size();
    }

    public int func_57086_l()
    {
        return field_57134_c;
    }

    public String[] func_57102_m()
    {
        return field_57129_f.field_56392_b[0].getSaveHandler().func_56804_d().func_56808_e();
    }

    public boolean func_57109_n()
    {
        return field_57136_l;
    }

    public void func_57099_a(boolean par1)
    {
        field_57136_l = par1;
    }

    public List func_57089_i(String par1Str)
    {
        ArrayList arraylist = new ArrayList();
        Iterator iterator = field_57133_b.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();

            if (entityplayermp.func_56258_I().equals(par1Str))
            {
                arraylist.add(entityplayermp);
            }
        }
        while (true);

        return arraylist;
    }

    public int func_57122_o()
    {
        return field_57131_d;
    }

    public MinecraftServer func_57085_p()
    {
        return field_57129_f;
    }

    public NBTTagCompound func_57119_q()
    {
        return null;
    }

    public void func_58122_a(EnumGameType par1EnumGameType)
    {
        field_58126_m = par1EnumGameType;
    }

    private void func_58121_a(EntityPlayerMP par1EntityPlayerMP, EntityPlayerMP par2EntityPlayerMP, World par3World)
    {
        if (par2EntityPlayerMP != null)
        {
            par1EntityPlayerMP.field_56267_c.func_57359_a(par2EntityPlayerMP.field_56267_c.func_57362_b());
        }
        else if (field_58126_m != null)
        {
            par1EntityPlayerMP.field_56267_c.func_57359_a(field_58126_m);
        }

        par1EntityPlayerMP.field_56267_c.func_57354_b(par3World.getWorldInfo().func_56915_q());
    }

    public void func_58123_b(boolean par1)
    {
        field_58125_n = par1;
    }

    public void func_58124_r()
    {
        for (; !field_57133_b.isEmpty(); ((EntityPlayerMP)field_57133_b.get(0)).field_56268_a.func_56716_a("Server closed")) { }
    }
}
