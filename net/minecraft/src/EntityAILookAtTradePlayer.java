package net.minecraft.src;

public class EntityAILookAtTradePlayer extends EntityAIWatchClosest
{
    private final EntityVillager field_56836_b;

    public EntityAILookAtTradePlayer(EntityVillager par1EntityVillager)
    {
        super(par1EntityVillager, net.minecraft.src.EntityPlayer.class, 8F);
        field_56836_b = par1EntityVillager;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (field_56836_b.func_56227_A())
        {
            closestEntity = field_56836_b.func_56221_a();
            return true;
        }
        else
        {
            return false;
        }
    }
}
