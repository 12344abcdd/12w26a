package net.minecraft.src;

import java.util.*;

public class EntityTracker
{
    private final WorldServer field_57600_a;
    private Set field_57598_b;
    private IntHashMap field_57599_c;
    private int field_57597_d;

    public EntityTracker(WorldServer par1WorldServer)
    {
        field_57598_b = new HashSet();
        field_57599_c = new IntHashMap();
        field_57600_a = par1WorldServer;
        field_57597_d = par1WorldServer.func_56852_J().func_56339_Z().func_57100_a();
    }

    public void func_57595_a(Entity par1Entity)
    {
        if (par1Entity instanceof EntityPlayerMP)
        {
            func_57589_a(par1Entity, 512, 2);
            EntityPlayerMP entityplayermp = (EntityPlayerMP)par1Entity;
            Iterator iterator = field_57598_b.iterator();

            do
            {
                if (!iterator.hasNext())
                {
                    break;
                }

                EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)iterator.next();

                if (entitytrackerentry.field_57476_a != entityplayermp)
                {
                    entitytrackerentry.func_57461_b(entityplayermp);
                }
            }
            while (true);
        }
        else if (par1Entity instanceof EntityFishHook)
        {
            func_57591_a(par1Entity, 64, 5, true);
        }
        else if (par1Entity instanceof EntityArrow)
        {
            func_57591_a(par1Entity, 64, 20, false);
        }
        else if (par1Entity instanceof EntitySmallFireball)
        {
            func_57591_a(par1Entity, 64, 10, false);
        }
        else if (par1Entity instanceof EntityFireball)
        {
            func_57591_a(par1Entity, 64, 10, false);
        }
        else if (par1Entity instanceof EntitySnowball)
        {
            func_57591_a(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityEnderPearl)
        {
            func_57591_a(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityEnderEye)
        {
            func_57591_a(par1Entity, 64, 4, true);
        }
        else if (par1Entity instanceof EntityEgg)
        {
            func_57591_a(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityPotion)
        {
            func_57591_a(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityExpBottle)
        {
            func_57591_a(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityItem)
        {
            func_57591_a(par1Entity, 64, 20, true);
        }
        else if (par1Entity instanceof EntityMinecart)
        {
            func_57591_a(par1Entity, 80, 3, true);
        }
        else if (par1Entity instanceof EntityBoat)
        {
            func_57591_a(par1Entity, 80, 3, true);
        }
        else if (par1Entity instanceof EntitySquid)
        {
            func_57591_a(par1Entity, 64, 3, true);
        }
        else if (par1Entity instanceof IAnimals)
        {
            func_57591_a(par1Entity, 80, 3, true);
        }
        else if (par1Entity instanceof EntityDragon)
        {
            func_57591_a(par1Entity, 160, 3, true);
        }
        else if (par1Entity instanceof EntityTNTPrimed)
        {
            func_57591_a(par1Entity, 160, 10, true);
        }
        else if (par1Entity instanceof EntityFallingSand)
        {
            func_57591_a(par1Entity, 160, 20, true);
        }
        else if (par1Entity instanceof EntityPainting)
        {
            func_57591_a(par1Entity, 160, 0x7fffffff, false);
        }
        else if (par1Entity instanceof EntityXPOrb)
        {
            func_57591_a(par1Entity, 160, 20, true);
        }
        else if (par1Entity instanceof EntityEnderCrystal)
        {
            func_57591_a(par1Entity, 256, 0x7fffffff, false);
        }
    }

    public void func_57589_a(Entity par1Entity, int par2, int par3)
    {
        func_57591_a(par1Entity, par2, par3, false);
    }

    public void func_57591_a(Entity par1Entity, int par2, int par3, boolean par4)
    {
        if (par2 > field_57597_d)
        {
            par2 = field_57597_d;
        }

        if (field_57599_c.containsItem(par1Entity.entityId))
        {
            throw new IllegalStateException("Entity is already tracked!");
        }
        else
        {
            EntityTrackerEntry entitytrackerentry = new EntityTrackerEntry(par1Entity, par2, par3, par4);
            field_57598_b.add(entitytrackerentry);
            field_57599_c.addKey(par1Entity.entityId, entitytrackerentry);
            entitytrackerentry.func_57469_b(field_57600_a.playerEntities);
            return;
        }
    }

    public void func_57594_b(Entity par1Entity)
    {
        if (par1Entity instanceof EntityPlayerMP)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)par1Entity;
            EntityTrackerEntry entitytrackerentry1;

            for (Iterator iterator = field_57598_b.iterator(); iterator.hasNext(); entitytrackerentry1.func_57462_a(entityplayermp))
            {
                entitytrackerentry1 = (EntityTrackerEntry)iterator.next();
            }
        }

        EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)field_57599_c.removeObject(par1Entity.entityId);

        if (entitytrackerentry != null)
        {
            field_57598_b.remove(entitytrackerentry);
            entitytrackerentry.func_57463_a();
        }
    }

    public void func_57592_a()
    {
        ArrayList arraylist = new ArrayList();
        Iterator iterator = field_57598_b.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)iterator.next();
            entitytrackerentry.func_57466_a(field_57600_a.playerEntities);

            if (entitytrackerentry.field_57477_n && (entitytrackerentry.field_57476_a instanceof EntityPlayerMP))
            {
                arraylist.add((EntityPlayerMP)entitytrackerentry.field_57476_a);
            }
        }
        while (true);

        for (Iterator iterator1 = arraylist.iterator(); iterator1.hasNext();)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator1.next();
            EntityPlayerMP entityplayermp1 = entityplayermp;
            Iterator iterator2 = field_57598_b.iterator();

            while (iterator2.hasNext())
            {
                EntityTrackerEntry entitytrackerentry1 = (EntityTrackerEntry)iterator2.next();

                if (entitytrackerentry1.field_57476_a != entityplayermp1)
                {
                    entitytrackerentry1.func_57461_b(entityplayermp1);
                }
            }
        }
    }

    public void func_57593_a(Entity par1Entity, Packet par2Packet)
    {
        EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)field_57599_c.lookup(par1Entity.entityId);

        if (entitytrackerentry != null)
        {
            entitytrackerentry.func_57467_a(par2Packet);
        }
    }

    public void func_57596_b(Entity par1Entity, Packet par2Packet)
    {
        EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)field_57599_c.lookup(par1Entity.entityId);

        if (entitytrackerentry != null)
        {
            entitytrackerentry.func_57465_b(par2Packet);
        }
    }

    public void func_57590_a(EntityPlayerMP par1EntityPlayerMP)
    {
        EntityTrackerEntry entitytrackerentry;

        for (Iterator iterator = field_57598_b.iterator(); iterator.hasNext(); entitytrackerentry.func_57468_c(par1EntityPlayerMP))
        {
            entitytrackerentry = (EntityTrackerEntry)iterator.next();
        }
    }
}
