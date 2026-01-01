package net.minecraft.src;

public class ItemInWorldManager
{
    public World field_57370_a;
    public EntityPlayerMP field_57368_b;
    private EnumGameType field_57369_c;
    private boolean field_57366_d;
    private int field_57367_e;
    private int field_57364_f;
    private int field_57365_g;
    private int field_57377_h;
    private int field_57378_i;
    private boolean field_57375_j;
    private int field_57376_k;
    private int field_57373_l;
    private int field_57374_m;
    private int field_57371_n;
    private int field_57372_o;

    public ItemInWorldManager(World par1World)
    {
        field_57369_c = EnumGameType.NOT_SET;
        field_57372_o = -1;
        field_57370_a = par1World;
    }

    public void func_57359_a(EnumGameType par1EnumGameType)
    {
        field_57369_c = par1EnumGameType;
        par1EnumGameType.func_57532_a(field_57368_b.capabilities);
        field_57368_b.func_50009_aI();
    }

    public EnumGameType func_57362_b()
    {
        return field_57369_c;
    }

    public boolean func_57363_c()
    {
        return field_57369_c.func_57534_d();
    }

    public void func_57354_b(EnumGameType par1EnumGameType)
    {
        if (field_57369_c == EnumGameType.NOT_SET)
        {
            field_57369_c = par1EnumGameType;
        }

        func_57359_a(field_57369_c);
    }

    public void func_57353_a()
    {
        field_57378_i++;

        if (field_57375_j)
        {
            int i = field_57378_i - field_57371_n;
            int k = field_57370_a.getBlockId(field_57376_k, field_57373_l, field_57374_m);

            if (k == 0)
            {
                field_57375_j = false;
            }
            else
            {
                Block block1 = Block.blocksList[k];
                float f = block1.func_58073_a(field_57368_b, field_57368_b.worldObj, field_57376_k, field_57373_l, field_57374_m) * (float)(i + 1);
                int i1 = (int)(f * 10F);

                if (i1 != field_57372_o)
                {
                    field_57370_a.func_56837_h(field_57368_b.entityId, field_57376_k, field_57373_l, field_57374_m, i1);
                    field_57372_o = i1;
                }

                if (f >= 1.0F)
                {
                    field_57375_j = false;
                    func_57361_b(field_57376_k, field_57373_l, field_57374_m);
                }
            }
        }
        else if (field_57366_d)
        {
            int j = field_57370_a.getBlockId(field_57364_f, field_57365_g, field_57377_h);
            Block block = Block.blocksList[j];

            if (block == null)
            {
                field_57370_a.func_56837_h(field_57368_b.entityId, field_57364_f, field_57365_g, field_57377_h, -1);
                field_57372_o = -1;
                field_57366_d = false;
            }
            else
            {
                int l = field_57378_i - field_57367_e;
                float f1 = block.func_58073_a(field_57368_b, field_57368_b.worldObj, field_57364_f, field_57365_g, field_57377_h) * (float)(l + 1);
                int j1 = (int)(f1 * 10F);

                if (j1 != field_57372_o)
                {
                    field_57370_a.func_56837_h(field_57368_b.entityId, field_57364_f, field_57365_g, field_57377_h, j1);
                    field_57372_o = j1;
                }
            }
        }
    }

    public void func_57352_a(int par1, int par2, int par3, int par4)
    {
        if (field_57369_c.func_57540_c())
        {
            return;
        }

        if (func_57363_c())
        {
            if (!field_57370_a.func_48457_a(null, par1, par2, par3, par4))
            {
                func_57361_b(par1, par2, par3);
            }

            return;
        }

        field_57370_a.func_48457_a(field_57368_b, par1, par2, par3, par4);
        field_57367_e = field_57378_i;
        float f = 1.0F;
        int i = field_57370_a.getBlockId(par1, par2, par3);

        if (i > 0)
        {
            Block.blocksList[i].onBlockClicked(field_57370_a, par1, par2, par3, field_57368_b);
            f = Block.blocksList[i].func_58073_a(field_57368_b, field_57368_b.worldObj, par1, par2, par3);
        }

        if (i > 0 && f >= 1.0F)
        {
            func_57361_b(par1, par2, par3);
        }
        else
        {
            field_57366_d = true;
            field_57364_f = par1;
            field_57365_g = par2;
            field_57377_h = par3;
            int j = (int)(f * 10F);
            field_57370_a.func_56837_h(field_57368_b.entityId, par1, par2, par3, j);
            field_57372_o = j;
        }
    }

    public void func_57358_a(int par1, int par2, int par3)
    {
        if (par1 == field_57364_f && par2 == field_57365_g && par3 == field_57377_h)
        {
            int i = field_57378_i - field_57367_e;
            int j = field_57370_a.getBlockId(par1, par2, par3);

            if (j != 0)
            {
                Block block = Block.blocksList[j];
                float f = block.func_58073_a(field_57368_b, field_57368_b.worldObj, par1, par2, par3) * (float)(i + 1);

                if (f >= 0.7F)
                {
                    field_57366_d = false;
                    field_57370_a.func_56837_h(field_57368_b.entityId, par1, par2, par3, -1);
                    func_57361_b(par1, par2, par3);
                }
                else if (!field_57375_j)
                {
                    field_57366_d = false;
                    field_57375_j = true;
                    field_57376_k = par1;
                    field_57373_l = par2;
                    field_57374_m = par3;
                    field_57371_n = field_57367_e;
                }
            }
        }
    }

    public void func_57351_c(int par1, int par2, int par3)
    {
        field_57366_d = false;
        field_57370_a.func_56837_h(field_57368_b.entityId, field_57364_f, field_57365_g, field_57377_h, -1);
    }

    private boolean func_57356_d(int par1, int par2, int par3)
    {
        Block block = Block.blocksList[field_57370_a.getBlockId(par1, par2, par3)];
        int i = field_57370_a.getBlockMetadata(par1, par2, par3);

        if (block != null)
        {
            block.func_56767_a(field_57370_a, par1, par2, par3, i, field_57368_b);
        }

        boolean flag = field_57370_a.setBlockWithNotify(par1, par2, par3, 0);

        if (block != null && flag)
        {
            block.onBlockDestroyedByPlayer(field_57370_a, par1, par2, par3, i);
        }

        return flag;
    }

    public boolean func_57361_b(int par1, int par2, int par3)
    {
        if (field_57369_c.func_57540_c())
        {
            return false;
        }

        int i = field_57370_a.getBlockId(par1, par2, par3);
        int j = field_57370_a.getBlockMetadata(par1, par2, par3);
        field_57370_a.playAuxSFXAtEntity(field_57368_b, 2001, par1, par2, par3, i + (field_57370_a.getBlockMetadata(par1, par2, par3) << 12));
        boolean flag = func_57356_d(par1, par2, par3);

        if (func_57363_c())
        {
            field_57368_b.field_56268_a.func_56717_b(new Packet53BlockChange(par1, par2, par3, field_57370_a));
        }
        else
        {
            ItemStack itemstack = field_57368_b.getCurrentEquippedItem();
            boolean flag1 = field_57368_b.canHarvestBlock(Block.blocksList[i]);

            if (itemstack != null)
            {
                itemstack.func_58115_a(field_57370_a, i, par1, par2, par3, field_57368_b);

                if (itemstack.stackSize == 0)
                {
                    field_57368_b.destroyCurrentEquippedItem();
                }
            }

            if (flag && flag1)
            {
                Block.blocksList[i].harvestBlock(field_57370_a, field_57368_b, par1, par2, par3, j);
            }
        }

        return flag;
    }

    public boolean func_57357_a(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack)
    {
        int i = par3ItemStack.stackSize;
        int j = par3ItemStack.getItemDamage();
        ItemStack itemstack = par3ItemStack.useItemRightClick(par2World, par1EntityPlayer);

        if (itemstack != par3ItemStack || itemstack != null && itemstack.stackSize != i || itemstack != null && itemstack.getMaxItemUseDuration() > 0)
        {
            par1EntityPlayer.inventory.mainInventory[par1EntityPlayer.inventory.currentItem] = itemstack;

            if (func_57363_c())
            {
                itemstack.stackSize = i;
                itemstack.setItemDamage(j);
            }

            if (itemstack.stackSize == 0)
            {
                par1EntityPlayer.inventory.mainInventory[par1EntityPlayer.inventory.currentItem] = null;
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean func_57355_a(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        int i = par2World.getBlockId(par4, par5, par6);

        if (i > 0 && Block.blocksList[i].func_56764_a(par2World, par4, par5, par6, par1EntityPlayer, par7, par8, par9, par10))
        {
            return true;
        }

        if (par3ItemStack == null)
        {
            return false;
        }

        if (func_57363_c())
        {
            int j = par3ItemStack.getItemDamage();
            int k = par3ItemStack.stackSize;
            boolean flag = par3ItemStack.func_56991_a(par1EntityPlayer, par2World, par4, par5, par6, par7, par8, par9, par10);
            par3ItemStack.setItemDamage(j);
            par3ItemStack.stackSize = k;
            return flag;
        }
        else
        {
            return par3ItemStack.func_56991_a(par1EntityPlayer, par2World, par4, par5, par6, par7, par8, par9, par10);
        }
    }

    public void func_57360_a(WorldServer par1WorldServer)
    {
        field_57370_a = par1WorldServer;
    }
}
