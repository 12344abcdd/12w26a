package net.minecraft.src;

import java.io.PrintStream;
import java.util.*;

public class EntityTrackerEntry
{
    public Entity field_57476_a;
    public int field_57474_b;
    public int field_57475_c;
    public int field_57472_d;
    public int field_57473_e;
    public int field_57470_f;
    public int field_57471_g;
    public int field_57483_h;
    public int field_57484_i;
    public double field_57481_j;
    public double field_57482_k;
    public double field_57479_l;
    public int field_57480_m;
    private double field_57490_p;
    private double field_57489_q;
    private double field_57488_r;
    private boolean field_57487_s;
    private boolean field_57486_t;
    private int field_57485_u;
    public boolean field_57477_n;
    public Set field_57478_o;

    public EntityTrackerEntry(Entity par1Entity, int par2, int par3, boolean par4)
    {
        field_57480_m = 0;
        field_57487_s = false;
        field_57485_u = 0;
        field_57477_n = false;
        field_57478_o = new HashSet();
        field_57476_a = par1Entity;
        field_57474_b = par2;
        field_57475_c = par3;
        field_57486_t = par4;
        field_57472_d = MathHelper.floor_double(par1Entity.posX * 32D);
        field_57473_e = MathHelper.floor_double(par1Entity.posY * 32D);
        field_57470_f = MathHelper.floor_double(par1Entity.posZ * 32D);
        field_57471_g = MathHelper.floor_float((par1Entity.rotationYaw * 256F) / 360F);
        field_57483_h = MathHelper.floor_float((par1Entity.rotationPitch * 256F) / 360F);
        field_57484_i = MathHelper.floor_float((par1Entity.func_56180_an() * 256F) / 360F);
    }

    public boolean equals(Object par1Obj)
    {
        if (par1Obj instanceof EntityTrackerEntry)
        {
            return ((EntityTrackerEntry)par1Obj).field_57476_a.entityId == field_57476_a.entityId;
        }
        else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return field_57476_a.entityId;
    }

    public void func_57466_a(List par1List)
    {
        field_57477_n = false;

        if (!field_57487_s || field_57476_a.getDistanceSq(field_57490_p, field_57489_q, field_57488_r) > 16D)
        {
            field_57490_p = field_57476_a.posX;
            field_57489_q = field_57476_a.posY;
            field_57488_r = field_57476_a.posZ;
            field_57487_s = true;
            field_57477_n = true;
            func_57469_b(par1List);
        }

        field_57485_u++;

        if (field_57480_m++ % field_57475_c == 0 || field_57476_a.isAirBorne)
        {
            int i = field_57476_a.field_55082_am.func_55257_a(field_57476_a.posX);
            int j = MathHelper.floor_double(field_57476_a.posY * 32D);
            int k = field_57476_a.field_55082_am.func_55257_a(field_57476_a.posZ);
            int l = MathHelper.floor_float((field_57476_a.rotationYaw * 256F) / 360F);
            int i1 = MathHelper.floor_float((field_57476_a.rotationPitch * 256F) / 360F);
            int j1 = i - field_57472_d;
            int k1 = j - field_57473_e;
            int l1 = k - field_57470_f;
            Object obj = null;
            boolean flag = Math.abs(j1) >= 4 || Math.abs(k1) >= 4 || Math.abs(l1) >= 4;
            boolean flag1 = Math.abs(l - field_57471_g) >= 4 || Math.abs(i1 - field_57483_h) >= 4;

            if (j1 < -128 || j1 >= 128 || k1 < -128 || k1 >= 128 || l1 < -128 || l1 >= 128 || field_57485_u > 400)
            {
                field_57485_u = 0;
                obj = new Packet34EntityTeleport(field_57476_a.entityId, i, j, k, (byte)l, (byte)i1);
            }
            else if (flag && flag1)
            {
                obj = new Packet33RelEntityMoveLook(field_57476_a.entityId, (byte)j1, (byte)k1, (byte)l1, (byte)l, (byte)i1);
            }
            else if (flag)
            {
                obj = new Packet31RelEntityMove(field_57476_a.entityId, (byte)j1, (byte)k1, (byte)l1);
            }
            else if (flag1)
            {
                obj = new Packet32EntityLook(field_57476_a.entityId, (byte)l, (byte)i1);
            }

            if (field_57486_t)
            {
                double d = field_57476_a.motionX - field_57481_j;
                double d1 = field_57476_a.motionY - field_57482_k;
                double d2 = field_57476_a.motionZ - field_57479_l;
                double d3 = 0.02D;
                double d4 = d * d + d1 * d1 + d2 * d2;

                if (d4 > d3 * d3 || d4 > 0.0D && field_57476_a.motionX == 0.0D && field_57476_a.motionY == 0.0D && field_57476_a.motionZ == 0.0D)
                {
                    field_57481_j = field_57476_a.motionX;
                    field_57482_k = field_57476_a.motionY;
                    field_57479_l = field_57476_a.motionZ;
                    func_57467_a(new Packet28EntityVelocity(field_57476_a.entityId, field_57481_j, field_57482_k, field_57479_l));
                }
            }

            if (obj != null)
            {
                func_57467_a(((Packet)(obj)));
            }

            DataWatcher datawatcher = field_57476_a.getDataWatcher();

            if (datawatcher.func_57621_a())
            {
                func_57465_b(new Packet40EntityMetadata(field_57476_a.entityId, datawatcher));
            }

            int i2 = MathHelper.floor_float((field_57476_a.func_56180_an() * 256F) / 360F);

            if (Math.abs(i2 - field_57484_i) >= 4)
            {
                func_57467_a(new Packet35EntityHeadRotation(field_57476_a.entityId, (byte)i2));
                field_57484_i = i2;
            }

            if (flag)
            {
                field_57472_d = i;
                field_57473_e = j;
                field_57470_f = k;
            }

            if (flag1)
            {
                field_57471_g = l;
                field_57483_h = i1;
            }
        }

        field_57476_a.isAirBorne = false;

        if (field_57476_a.velocityChanged)
        {
            func_57465_b(new Packet28EntityVelocity(field_57476_a));
            field_57476_a.velocityChanged = false;
        }
    }

    public void func_57467_a(Packet par1Packet)
    {
        EntityPlayerMP entityplayermp;

        for (Iterator iterator = field_57478_o.iterator(); iterator.hasNext(); entityplayermp.field_56268_a.func_56717_b(par1Packet))
        {
            entityplayermp = (EntityPlayerMP)iterator.next();
        }
    }

    public void func_57465_b(Packet par1Packet)
    {
        func_57467_a(par1Packet);

        if (field_57476_a instanceof EntityPlayerMP)
        {
            ((EntityPlayerMP)field_57476_a).field_56268_a.func_56717_b(par1Packet);
        }
    }

    public void func_57463_a()
    {
        func_57467_a(new Packet29DestroyEntity(field_57476_a.entityId));
    }

    public void func_57462_a(EntityPlayerMP par1EntityPlayerMP)
    {
        if (field_57478_o.contains(par1EntityPlayerMP))
        {
            field_57478_o.remove(par1EntityPlayerMP);
        }
    }

    public void func_57461_b(EntityPlayerMP par1EntityPlayerMP)
    {
        if (par1EntityPlayerMP == field_57476_a)
        {
            return;
        }

        double d = par1EntityPlayerMP.posX - (double)(field_57472_d / 32);
        double d1 = par1EntityPlayerMP.posZ - (double)(field_57470_f / 32);

        if (d >= (double)(-field_57474_b) && d <= (double)field_57474_b && d1 >= (double)(-field_57474_b) && d1 <= (double)field_57474_b)
        {
            if (!field_57478_o.contains(par1EntityPlayerMP))
            {
                field_57478_o.add(par1EntityPlayerMP);
                par1EntityPlayerMP.field_56268_a.func_56717_b(func_57464_b());

                if (field_57486_t)
                {
                    par1EntityPlayerMP.field_56268_a.func_56717_b(new Packet28EntityVelocity(field_57476_a.entityId, field_57476_a.motionX, field_57476_a.motionY, field_57476_a.motionZ));
                }

                ItemStack aitemstack[] = field_57476_a.func_56178_y_();

                if (aitemstack != null)
                {
                    for (int i = 0; i < aitemstack.length; i++)
                    {
                        par1EntityPlayerMP.field_56268_a.func_56717_b(new Packet5PlayerInventory(field_57476_a.entityId, i, aitemstack[i]));
                    }
                }

                if (field_57476_a instanceof EntityPlayer)
                {
                    EntityPlayer entityplayer = (EntityPlayer)field_57476_a;

                    if (entityplayer.isPlayerSleeping())
                    {
                        par1EntityPlayerMP.field_56268_a.func_56717_b(new Packet17Sleep(field_57476_a, 0, MathHelper.floor_double(field_57476_a.posX), MathHelper.floor_double(field_57476_a.posY), MathHelper.floor_double(field_57476_a.posZ)));
                    }
                }

                if (field_57476_a instanceof EntityLiving)
                {
                    EntityLiving entityliving = (EntityLiving)field_57476_a;
                    PotionEffect potioneffect;

                    for (Iterator iterator = entityliving.getActivePotionEffects().iterator(); iterator.hasNext(); par1EntityPlayerMP.field_56268_a.func_56717_b(new Packet41EntityEffect(field_57476_a.entityId, potioneffect)))
                    {
                        potioneffect = (PotionEffect)iterator.next();
                    }
                }
            }
        }
        else if (field_57478_o.contains(par1EntityPlayerMP))
        {
            field_57478_o.remove(par1EntityPlayerMP);
            par1EntityPlayerMP.field_56268_a.func_56717_b(new Packet29DestroyEntity(field_57476_a.entityId));
        }
    }

    public void func_57469_b(List par1List)
    {
        EntityPlayer entityplayer;

        for (Iterator iterator = par1List.iterator(); iterator.hasNext(); func_57461_b((EntityPlayerMP)entityplayer))
        {
            entityplayer = (EntityPlayer)iterator.next();
        }
    }

    private Packet func_57464_b()
    {
        if (field_57476_a.isDead)
        {
            System.out.println("Fetching addPacket for removed entity");
        }

        if (field_57476_a instanceof EntityItem)
        {
            EntityItem entityitem = (EntityItem)field_57476_a;
            Packet21PickupSpawn packet21pickupspawn = new Packet21PickupSpawn(entityitem);
            entityitem.posX = (double)packet21pickupspawn.xPosition / 32D;
            entityitem.posY = (double)packet21pickupspawn.yPosition / 32D;
            entityitem.posZ = (double)packet21pickupspawn.zPosition / 32D;
            return packet21pickupspawn;
        }

        if (field_57476_a instanceof EntityPlayerMP)
        {
            return new Packet20NamedEntitySpawn((EntityPlayer)field_57476_a);
        }

        if (field_57476_a instanceof EntityMinecart)
        {
            EntityMinecart entityminecart = (EntityMinecart)field_57476_a;

            if (entityminecart.minecartType == 0)
            {
                return new Packet23VehicleSpawn(field_57476_a, 10);
            }

            if (entityminecart.minecartType == 1)
            {
                return new Packet23VehicleSpawn(field_57476_a, 11);
            }

            if (entityminecart.minecartType == 2)
            {
                return new Packet23VehicleSpawn(field_57476_a, 12);
            }
        }

        if (field_57476_a instanceof EntityBoat)
        {
            return new Packet23VehicleSpawn(field_57476_a, 1);
        }

        if (field_57476_a instanceof IAnimals)
        {
            return new Packet24MobSpawn((EntityLiving)field_57476_a);
        }

        if (field_57476_a instanceof EntityDragon)
        {
            return new Packet24MobSpawn((EntityLiving)field_57476_a);
        }

        if (field_57476_a instanceof EntityFishHook)
        {
            EntityPlayer entityplayer = ((EntityFishHook)field_57476_a).angler;
            return new Packet23VehicleSpawn(field_57476_a, 90, entityplayer == null ? field_57476_a.entityId : ((Entity)(entityplayer)).entityId);
        }

        if (field_57476_a instanceof EntityArrow)
        {
            Entity entity = ((EntityArrow)field_57476_a).shootingEntity;
            return new Packet23VehicleSpawn(field_57476_a, 60, entity == null ? field_57476_a.entityId : entity.entityId);
        }

        if (field_57476_a instanceof EntitySnowball)
        {
            return new Packet23VehicleSpawn(field_57476_a, 61);
        }

        if (field_57476_a instanceof EntityPotion)
        {
            return new Packet23VehicleSpawn(field_57476_a, 73, ((EntityPotion)field_57476_a).getPotionDamage());
        }

        if (field_57476_a instanceof EntityExpBottle)
        {
            return new Packet23VehicleSpawn(field_57476_a, 75);
        }

        if (field_57476_a instanceof EntityEnderPearl)
        {
            return new Packet23VehicleSpawn(field_57476_a, 65);
        }

        if (field_57476_a instanceof EntityEnderEye)
        {
            return new Packet23VehicleSpawn(field_57476_a, 72);
        }

        if (field_57476_a instanceof EntitySmallFireball)
        {
            EntitySmallFireball entitysmallfireball = (EntitySmallFireball)field_57476_a;
            Packet23VehicleSpawn packet23vehiclespawn = null;

            if (entitysmallfireball.shootingEntity != null)
            {
                packet23vehiclespawn = new Packet23VehicleSpawn(field_57476_a, 64, entitysmallfireball.shootingEntity.entityId);
            }
            else
            {
                packet23vehiclespawn = new Packet23VehicleSpawn(field_57476_a, 64, 0);
            }

            packet23vehiclespawn.speedX = (int)(entitysmallfireball.accelerationX * 8000D);
            packet23vehiclespawn.speedY = (int)(entitysmallfireball.accelerationY * 8000D);
            packet23vehiclespawn.speedZ = (int)(entitysmallfireball.accelerationZ * 8000D);
            return packet23vehiclespawn;
        }

        if (field_57476_a instanceof EntityFireball)
        {
            EntityFireball entityfireball = (EntityFireball)field_57476_a;
            Packet23VehicleSpawn packet23vehiclespawn1 = null;

            if (entityfireball.shootingEntity != null)
            {
                packet23vehiclespawn1 = new Packet23VehicleSpawn(field_57476_a, 63, ((EntityFireball)field_57476_a).shootingEntity.entityId);
            }
            else
            {
                packet23vehiclespawn1 = new Packet23VehicleSpawn(field_57476_a, 63, 0);
            }

            packet23vehiclespawn1.speedX = (int)(entityfireball.accelerationX * 8000D);
            packet23vehiclespawn1.speedY = (int)(entityfireball.accelerationY * 8000D);
            packet23vehiclespawn1.speedZ = (int)(entityfireball.accelerationZ * 8000D);
            return packet23vehiclespawn1;
        }

        if (field_57476_a instanceof EntityEgg)
        {
            return new Packet23VehicleSpawn(field_57476_a, 62);
        }

        if (field_57476_a instanceof EntityTNTPrimed)
        {
            return new Packet23VehicleSpawn(field_57476_a, 50);
        }

        if (field_57476_a instanceof EntityEnderCrystal)
        {
            return new Packet23VehicleSpawn(field_57476_a, 51);
        }

        if (field_57476_a instanceof EntityFallingSand)
        {
            EntityFallingSand entityfallingsand = (EntityFallingSand)field_57476_a;
            return new Packet23VehicleSpawn(field_57476_a, 70, entityfallingsand.blockID | entityfallingsand.field_56185_b << 12);
        }

        if (field_57476_a instanceof EntityPainting)
        {
            return new Packet25EntityPainting((EntityPainting)field_57476_a);
        }

        if (field_57476_a instanceof EntityXPOrb)
        {
            return new Packet26EntityExpOrb((EntityXPOrb)field_57476_a);
        }
        else
        {
            throw new IllegalArgumentException((new StringBuilder()).append("Don't know how to add ").append(field_57476_a.getClass()).append("!").toString());
        }
    }

    public void func_57468_c(EntityPlayerMP par1EntityPlayerMP)
    {
        if (field_57478_o.contains(par1EntityPlayerMP))
        {
            field_57478_o.remove(par1EntityPlayerMP);
            par1EntityPlayerMP.field_56268_a.func_56717_b(new Packet29DestroyEntity(field_57476_a.entityId));
        }
    }
}
