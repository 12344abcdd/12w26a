package net.minecraft.src;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

public class NetServerHandler extends NetHandler
{
    public static Logger field_56728_a = Logger.getLogger("Minecraft");
    public NetworkManager field_56726_b;
    public boolean field_56727_c;
    private MinecraftServer field_56724_d;
    private EntityPlayerMP field_56725_e;
    private int field_56722_f;
    private int field_56723_g;
    private boolean field_56735_h;
    private int field_56736_i;
    private long field_56733_j;
    private static Random field_56734_k = new Random();
    private long field_56731_l;
    private int field_56732_m;
    private int field_56729_n;
    private double field_56730_o;
    private double field_56740_p;
    private double field_56739_q;
    private boolean field_56738_r;
    private IntHashMap field_56737_s;

    public NetServerHandler(MinecraftServer par1MinecraftServer, NetworkManager par2NetworkManager, EntityPlayerMP par3EntityPlayerMP)
    {
        field_56727_c = false;
        field_56732_m = 0;
        field_56729_n = 0;
        field_56738_r = true;
        field_56737_s = new IntHashMap();
        field_56724_d = par1MinecraftServer;
        field_56726_b = par2NetworkManager;
        par2NetworkManager.func_57273_a(this);
        field_56725_e = par3EntityPlayerMP;
        par3EntityPlayerMP.field_56268_a = this;
    }

    public void func_56718_b()
    {
        field_56735_h = false;
        field_56722_f++;
        field_56726_b.processReadPackets();

        if ((long)field_56722_f - field_56731_l > 20L)
        {
            field_56731_l = field_56722_f;
            field_56733_j = System.nanoTime() / 0xf4240L;
            field_56736_i = field_56734_k.nextInt();
            func_56717_b(new Packet0KeepAlive(field_56736_i));
        }

        if (field_56732_m > 0)
        {
            field_56732_m--;
        }

        if (field_56729_n > 0)
        {
            field_56729_n--;
        }
    }

    public void func_56716_a(String par1Str)
    {
        if (field_56727_c)
        {
            return;
        }
        else
        {
            field_56725_e.func_56249_D();
            func_56717_b(new Packet255KickDisconnect(par1Str));
            field_56726_b.serverShutdown();
            field_56724_d.func_56339_Z().func_57114_a(new Packet3Chat((new StringBuilder()).append("\247e").append(field_56725_e.username).append(" left the game.").toString()));
            field_56724_d.func_56339_Z().func_57093_e(field_56725_e);
            field_56727_c = true;
            return;
        }
    }

    public void handleFlying(Packet10Flying par1Packet10Flying)
    {
        WorldServer worldserver = field_56724_d.func_56325_a(field_56725_e.dimension);
        field_56735_h = true;

        if (field_56725_e.field_56275_i)
        {
            return;
        }

        if (!field_56738_r)
        {
            double d = par1Packet10Flying.yPosition - field_56740_p;

            if (par1Packet10Flying.xPosition == field_56730_o && d * d < 0.01D && par1Packet10Flying.zPosition == field_56739_q)
            {
                field_56738_r = true;
            }
        }

        if (field_56738_r)
        {
            if (field_56725_e.ridingEntity != null)
            {
                float f = field_56725_e.rotationYaw;
                float f1 = field_56725_e.rotationPitch;
                field_56725_e.ridingEntity.updateRiderPosition();
                double d2 = field_56725_e.posX;
                double d4 = field_56725_e.posY;
                double d6 = field_56725_e.posZ;
                double d8 = 0.0D;
                double d9 = 0.0D;

                if (par1Packet10Flying.rotating)
                {
                    f = par1Packet10Flying.yaw;
                    f1 = par1Packet10Flying.pitch;
                }

                if (par1Packet10Flying.moving && par1Packet10Flying.yPosition == -999D && par1Packet10Flying.stance == -999D)
                {
                    if (par1Packet10Flying.xPosition > 1.0D || par1Packet10Flying.zPosition > 1.0D)
                    {
                        System.err.println((new StringBuilder()).append(field_56725_e.username).append(" was caught trying to crash the server with an invalid position.").toString());
                        func_56716_a("Nope!");
                        return;
                    }

                    d8 = par1Packet10Flying.xPosition;
                    d9 = par1Packet10Flying.zPosition;
                }

                field_56725_e.onGround = par1Packet10Flying.onGround;
                field_56725_e.func_56247_x();
                field_56725_e.moveEntity(d8, 0.0D, d9);
                field_56725_e.setPositionAndRotation(d2, d4, d6, f, f1);
                field_56725_e.motionX = d8;
                field_56725_e.motionZ = d9;

                if (field_56725_e.ridingEntity != null)
                {
                    worldserver.func_56853_b(field_56725_e.ridingEntity, true);
                }

                if (field_56725_e.ridingEntity != null)
                {
                    field_56725_e.ridingEntity.updateRiderPosition();
                }

                field_56724_d.func_56339_Z().func_57106_d(field_56725_e);
                field_56730_o = field_56725_e.posX;
                field_56740_p = field_56725_e.posY;
                field_56739_q = field_56725_e.posZ;
                worldserver.updateEntity(field_56725_e);
                return;
            }

            if (field_56725_e.isPlayerSleeping())
            {
                field_56725_e.func_56247_x();
                field_56725_e.setPositionAndRotation(field_56730_o, field_56740_p, field_56739_q, field_56725_e.rotationYaw, field_56725_e.rotationPitch);
                worldserver.updateEntity(field_56725_e);
                return;
            }

            double d1 = field_56725_e.posY;
            field_56730_o = field_56725_e.posX;
            field_56740_p = field_56725_e.posY;
            field_56739_q = field_56725_e.posZ;
            double d3 = field_56725_e.posX;
            double d5 = field_56725_e.posY;
            double d7 = field_56725_e.posZ;
            float f2 = field_56725_e.rotationYaw;
            float f3 = field_56725_e.rotationPitch;

            if (par1Packet10Flying.moving && par1Packet10Flying.yPosition == -999D && par1Packet10Flying.stance == -999D)
            {
                par1Packet10Flying.moving = false;
            }

            if (par1Packet10Flying.moving)
            {
                d3 = par1Packet10Flying.xPosition;
                d5 = par1Packet10Flying.yPosition;
                d7 = par1Packet10Flying.zPosition;
                double d10 = par1Packet10Flying.stance - par1Packet10Flying.yPosition;

                if (!field_56725_e.isPlayerSleeping() && (d10 > 1.6499999999999999D || d10 < 0.10000000000000001D))
                {
                    func_56716_a("Illegal stance");
                    field_56728_a.warning((new StringBuilder()).append(field_56725_e.username).append(" had an illegal stance: ").append(d10).toString());
                    return;
                }

                if (Math.abs(par1Packet10Flying.xPosition) > 32000000D || Math.abs(par1Packet10Flying.zPosition) > 32000000D)
                {
                    func_56716_a("Illegal position");
                    return;
                }
            }

            if (par1Packet10Flying.rotating)
            {
                f2 = par1Packet10Flying.yaw;
                f3 = par1Packet10Flying.pitch;
            }

            field_56725_e.func_56247_x();
            field_56725_e.ySize = 0.0F;
            field_56725_e.setPositionAndRotation(field_56730_o, field_56740_p, field_56739_q, f2, f3);

            if (!field_56738_r)
            {
                return;
            }

            double d11 = d3 - field_56725_e.posX;
            double d12 = d5 - field_56725_e.posY;
            double d13 = d7 - field_56725_e.posZ;
            double d14 = Math.min(Math.abs(d11), Math.abs(field_56725_e.motionX));
            double d15 = Math.min(Math.abs(d12), Math.abs(field_56725_e.motionY));
            double d16 = Math.min(Math.abs(d13), Math.abs(field_56725_e.motionZ));
            double d17 = d14 * d14 + d15 * d15 + d16 * d16;

            if (d17 > 100D && (!field_56724_d.func_56307_I() || !field_56724_d.func_56296_H().equals(field_56725_e.username)))
            {
                field_56728_a.warning((new StringBuilder()).append(field_56725_e.username).append(" moved too quickly! ").append(d11).append(",").append(d12).append(",").append(d13).append(" (").append(d14).append(", ").append(d15).append(", ").append(d16).append(")").toString());
                func_56719_a(field_56730_o, field_56740_p, field_56739_q, field_56725_e.rotationYaw, field_56725_e.rotationPitch);
                return;
            }

            float f4 = 0.0625F;
            boolean flag = worldserver.getCollidingBoundingBoxes(field_56725_e, field_56725_e.boundingBox.copy().contract(f4, f4, f4)).isEmpty();

            if (field_56725_e.onGround && !par1Packet10Flying.onGround && d12 > 0.0D)
            {
                field_56725_e.addExhaustion(0.2F);
            }

            field_56725_e.moveEntity(d11, d12, d13);
            field_56725_e.onGround = par1Packet10Flying.onGround;
            field_56725_e.addMovementStat(d11, d12, d13);
            double d18 = d12;
            d11 = d3 - field_56725_e.posX;
            d12 = d5 - field_56725_e.posY;

            if (d12 > -0.5D || d12 < 0.5D)
            {
                d12 = 0.0D;
            }

            d13 = d7 - field_56725_e.posZ;
            d17 = d11 * d11 + d12 * d12 + d13 * d13;
            boolean flag1 = false;

            if (d17 > 0.0625D && !field_56725_e.isPlayerSleeping() && !field_56725_e.field_56267_c.func_57363_c())
            {
                flag1 = true;
                field_56728_a.warning((new StringBuilder()).append(field_56725_e.username).append(" moved wrongly!").toString());
                System.out.println((new StringBuilder()).append("Got position ").append(d3).append(", ").append(d5).append(", ").append(d7).toString());
                System.out.println((new StringBuilder()).append("Expected ").append(field_56725_e.posX).append(", ").append(field_56725_e.posY).append(", ").append(field_56725_e.posZ).toString());
            }

            field_56725_e.setPositionAndRotation(d3, d5, d7, f2, f3);
            boolean flag2 = worldserver.getCollidingBoundingBoxes(field_56725_e, field_56725_e.boundingBox.copy().contract(f4, f4, f4)).isEmpty();

            if (flag && (flag1 || !flag2) && !field_56725_e.isPlayerSleeping())
            {
                func_56719_a(field_56730_o, field_56740_p, field_56739_q, f2, f3);
                return;
            }

            AxisAlignedBB axisalignedbb = field_56725_e.boundingBox.copy().expand(f4, f4, f4).addCoord(0.0D, -0.55000000000000004D, 0.0D);

            if (!field_56724_d.func_56357_V() && !field_56725_e.field_56267_c.func_57363_c() && !worldserver.func_56840_c(axisalignedbb))
            {
                if (d18 >= -0.03125D)
                {
                    field_56723_g++;

                    if (field_56723_g > 80)
                    {
                        field_56728_a.warning((new StringBuilder()).append(field_56725_e.username).append(" was kicked for floating too long!").toString());
                        func_56716_a("Flying is not enabled on this server");
                        return;
                    }
                }
            }
            else
            {
                field_56723_g = 0;
            }

            field_56725_e.onGround = par1Packet10Flying.onGround;
            field_56724_d.func_56339_Z().func_57106_d(field_56725_e);
            field_56725_e.func_56255_b(field_56725_e.posY - d1, par1Packet10Flying.onGround);
        }
    }

    public void func_56719_a(double par1, double par3, double par5, float par7, float par8)
    {
        field_56738_r = false;
        field_56730_o = par1;
        field_56740_p = par3;
        field_56739_q = par5;
        field_56725_e.setPositionAndRotation(par1, par3, par5, par7, par8);
        field_56725_e.field_56268_a.func_56717_b(new Packet13PlayerLookMove(par1, par3 + 1.6200000047683716D, par3, par5, par7, par8, false));
    }

    public void handleBlockDig(Packet14BlockDig par1Packet14BlockDig)
    {
        WorldServer worldserver = field_56724_d.func_56325_a(field_56725_e.dimension);

        if (par1Packet14BlockDig.status == 4)
        {
            field_56725_e.dropOneItem();
            return;
        }

        if (par1Packet14BlockDig.status == 5)
        {
            field_56725_e.stopUsingItem();
            return;
        }

        boolean flag = worldserver.field_56872_J = worldserver.worldProvider.worldType != 0 || field_56724_d.func_56339_Z().func_57087_e(field_56725_e.username) || field_56724_d.func_56307_I();
        boolean flag1 = false;

        if (par1Packet14BlockDig.status == 0)
        {
            flag1 = true;
        }

        if (par1Packet14BlockDig.status == 2)
        {
            flag1 = true;
        }

        int i = par1Packet14BlockDig.xPosition;
        int j = par1Packet14BlockDig.yPosition;
        int k = par1Packet14BlockDig.zPosition;

        if (flag1)
        {
            double d = field_56725_e.posX - ((double)i + 0.5D);
            double d1 = (field_56725_e.posY - ((double)j + 0.5D)) + 1.5D;
            double d3 = field_56725_e.posZ - ((double)k + 0.5D);
            double d5 = d * d + d1 * d1 + d3 * d3;

            if (d5 > 36D)
            {
                return;
            }

            if (j >= field_56724_d.func_56348_X())
            {
                return;
            }
        }

        ChunkCoordinates chunkcoordinates = worldserver.getSpawnPoint();
        int l = MathHelper.func_57511_a(i - chunkcoordinates.posX);
        int i1 = MathHelper.func_57511_a(k - chunkcoordinates.posZ);

        if (l > i1)
        {
            i1 = l;
        }

        if (par1Packet14BlockDig.status == 0)
        {
            if (i1 > 16 || flag)
            {
                field_56725_e.field_56267_c.func_57352_a(i, j, k, par1Packet14BlockDig.face);
            }
            else
            {
                field_56725_e.field_56268_a.func_56717_b(new Packet53BlockChange(i, j, k, worldserver));
            }
        }
        else if (par1Packet14BlockDig.status == 2)
        {
            field_56725_e.field_56267_c.func_57358_a(i, j, k);

            if (worldserver.getBlockId(i, j, k) != 0)
            {
                field_56725_e.field_56268_a.func_56717_b(new Packet53BlockChange(i, j, k, worldserver));
            }
        }
        else if (par1Packet14BlockDig.status == 1)
        {
            field_56725_e.field_56267_c.func_57351_c(i, j, k);

            if (worldserver.getBlockId(i, j, k) != 0)
            {
                field_56725_e.field_56268_a.func_56717_b(new Packet53BlockChange(i, j, k, worldserver));
            }
        }
        else if (par1Packet14BlockDig.status == 3)
        {
            double d2 = field_56725_e.posX - ((double)i + 0.5D);
            double d4 = field_56725_e.posY - ((double)j + 0.5D);
            double d6 = field_56725_e.posZ - ((double)k + 0.5D);
            double d7 = d2 * d2 + d4 * d4 + d6 * d6;

            if (d7 < 256D)
            {
                field_56725_e.field_56268_a.func_56717_b(new Packet53BlockChange(i, j, k, worldserver));
            }
        }

        worldserver.field_56872_J = false;
    }

    public void handlePlace(Packet15Place par1Packet15Place)
    {
        WorldServer worldserver = field_56724_d.func_56325_a(field_56725_e.dimension);
        ItemStack itemstack = field_56725_e.inventory.getCurrentItem();
        boolean flag = false;
        int i = par1Packet15Place.func_56524_b();
        int j = par1Packet15Place.func_56523_c();
        int k = par1Packet15Place.func_56521_d();
        int l = par1Packet15Place.func_56520_e();
        boolean flag1 = worldserver.field_56872_J = worldserver.worldProvider.worldType != 0 || field_56724_d.func_56339_Z().func_57087_e(field_56725_e.username) || field_56724_d.func_56307_I();

        if (par1Packet15Place.func_56520_e() == 255)
        {
            if (itemstack == null)
            {
                return;
            }

            field_56725_e.field_56267_c.func_57357_a(field_56725_e, worldserver, itemstack);
        }
        else if (par1Packet15Place.func_56523_c() < field_56724_d.func_56348_X() - 1 || par1Packet15Place.func_56520_e() != 1 && par1Packet15Place.func_56523_c() < field_56724_d.func_56348_X())
        {
            ChunkCoordinates chunkcoordinates = worldserver.getSpawnPoint();
            int i1 = MathHelper.func_57511_a(i - chunkcoordinates.posX);
            int j1 = MathHelper.func_57511_a(k - chunkcoordinates.posZ);

            if (i1 > j1)
            {
                j1 = i1;
            }

            if (field_56738_r && field_56725_e.getDistanceSq((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D) < 64D && (j1 > 16 || flag1))
            {
                field_56725_e.field_56267_c.func_57355_a(field_56725_e, worldserver, itemstack, i, j, k, l, par1Packet15Place.func_56517_g(), par1Packet15Place.func_56522_h(), par1Packet15Place.func_56519_i());
            }

            flag = true;
        }
        else
        {
            field_56725_e.field_56268_a.func_56717_b(new Packet3Chat((new StringBuilder()).append("\2477Height limit for building is ").append(field_56724_d.func_56348_X()).toString()));
            flag = true;
        }

        if (flag)
        {
            field_56725_e.field_56268_a.func_56717_b(new Packet53BlockChange(i, j, k, worldserver));

            if (l == 0)
            {
                j--;
            }

            if (l == 1)
            {
                j++;
            }

            if (l == 2)
            {
                k--;
            }

            if (l == 3)
            {
                k++;
            }

            if (l == 4)
            {
                i--;
            }

            if (l == 5)
            {
                i++;
            }

            field_56725_e.field_56268_a.func_56717_b(new Packet53BlockChange(i, j, k, worldserver));
        }

        itemstack = field_56725_e.inventory.getCurrentItem();

        if (itemstack != null && itemstack.stackSize == 0)
        {
            field_56725_e.inventory.mainInventory[field_56725_e.inventory.currentItem] = null;
            itemstack = null;
        }

        if (itemstack == null || itemstack.getMaxItemUseDuration() == 0)
        {
            field_56725_e.field_56263_g = true;
            field_56725_e.inventory.mainInventory[field_56725_e.inventory.currentItem] = ItemStack.copyItemStack(field_56725_e.inventory.mainInventory[field_56725_e.inventory.currentItem]);
            Slot slot = field_56725_e.craftingInventory.func_56978_a(field_56725_e.inventory, field_56725_e.inventory.currentItem);
            field_56725_e.craftingInventory.updateCraftingResults();
            field_56725_e.field_56263_g = false;

            if (!ItemStack.areItemStacksEqual(field_56725_e.inventory.getCurrentItem(), par1Packet15Place.func_56518_f()))
            {
                func_56717_b(new Packet103SetSlot(field_56725_e.craftingInventory.windowId, slot.slotNumber, field_56725_e.inventory.getCurrentItem()));
            }
        }

        worldserver.field_56872_J = false;
    }

    public void handleErrorMessage(String par1Str, Object par2ArrayOfObj[])
    {
        field_56728_a.info((new StringBuilder()).append(field_56725_e.username).append(" lost connection: ").append(par1Str).toString());
        field_56724_d.func_56339_Z().func_57114_a(new Packet3Chat((new StringBuilder()).append("\247e").append(field_56725_e.username).append(" left the game.").toString()));
        field_56724_d.func_56339_Z().func_57093_e(field_56725_e);
        field_56727_c = true;

        if (field_56724_d.func_56307_I() && field_56725_e.username.equals(field_56724_d.func_56296_H()))
        {
            field_56728_a.info("Stopping singleplayer server as player logged out");
            field_56724_d.func_56286_k();
        }
    }

    public void registerPacket(Packet par1Packet)
    {
        field_56728_a.warning((new StringBuilder()).append(getClass()).append(" wasn't prepared to deal with a ").append(par1Packet.getClass()).toString());
        func_56716_a("Protocol error, unexpected packet");
    }

    public void func_56717_b(Packet par1Packet)
    {
        if (par1Packet instanceof Packet3Chat)
        {
            Packet3Chat packet3chat = (Packet3Chat)par1Packet;
            int i = field_56725_e.func_56257_K();

            if (i == 2)
            {
                return;
            }

            if (i == 1 && !packet3chat.func_56534_b())
            {
                return;
            }
        }

        field_56726_b.addToSendQueue(par1Packet);
    }

    public void handleBlockItemSwitch(Packet16BlockItemSwitch par1Packet16BlockItemSwitch)
    {
        if (par1Packet16BlockItemSwitch.id < 0 || par1Packet16BlockItemSwitch.id >= InventoryPlayer.func_56198_h())
        {
            field_56728_a.warning((new StringBuilder()).append(field_56725_e.username).append(" tried to set an invalid carried item").toString());
            return;
        }
        else
        {
            field_56725_e.inventory.currentItem = par1Packet16BlockItemSwitch.id;
            return;
        }
    }

    public void handleChat(Packet3Chat par1Packet3Chat)
    {
        if (field_56725_e.func_56257_K() == 2)
        {
            func_56717_b(new Packet3Chat("Cannot send chat message."));
            return;
        }

        String s = par1Packet3Chat.message;

        if (s.length() > 100)
        {
            func_56716_a("Chat message too long");
            return;
        }

        s = s.trim();

        for (int i = 0; i < s.length(); i++)
        {
            if (!ChatAllowedCharacters.isAllowedCharacter(s.charAt(i)))
            {
                func_56716_a("Illegal characters in chat");
                return;
            }
        }

        if (s.startsWith("/"))
        {
            func_56721_b(s);
        }
        else
        {
            if (field_56725_e.func_56257_K() == 1)
            {
                func_56717_b(new Packet3Chat("Cannot send chat message."));
                return;
            }

            s = (new StringBuilder()).append("<").append(field_56725_e.username).append("> ").append(s).toString();
            field_56728_a.info(s);
            field_56724_d.func_56339_Z().func_57114_a(new Packet3Chat(s, false));
        }

        field_56732_m += 20;

        if (field_56732_m > 200 && !field_56724_d.func_56339_Z().func_57087_e(field_56725_e.username))
        {
            func_56716_a("disconnect.spam");
        }
    }

    private void func_56721_b(String par1Str)
    {
        if (field_56724_d.func_56339_Z().func_57087_e(field_56725_e.username) || "/seed".equals(par1Str))
        {
            field_56728_a.info((new StringBuilder()).append(field_56725_e.username).append(" issued server command: ").append(par1Str).toString());
            field_56724_d.func_56344_E().func_55355_a(field_56725_e, par1Str);
        }
    }

    public void handleAnimation(Packet18Animation par1Packet18Animation)
    {
        if (par1Packet18Animation.animate == 1)
        {
            field_56725_e.swingItem();
        }
    }

    /**
     * runs registerPacket on the given Packet19EntityAction
     */
    public void handleEntityAction(Packet19EntityAction par1Packet19EntityAction)
    {
        if (par1Packet19EntityAction.state == 1)
        {
            field_56725_e.setSneaking(true);
        }
        else if (par1Packet19EntityAction.state == 2)
        {
            field_56725_e.setSneaking(false);
        }
        else if (par1Packet19EntityAction.state == 4)
        {
            field_56725_e.setSprinting(true);
        }
        else if (par1Packet19EntityAction.state == 5)
        {
            field_56725_e.setSprinting(false);
        }
        else if (par1Packet19EntityAction.state == 3)
        {
            field_56725_e.wakeUpPlayer(false, true, true);
            field_56738_r = false;
        }
    }

    public void handleKickDisconnect(Packet255KickDisconnect par1Packet255KickDisconnect)
    {
        field_56726_b.networkShutdown("disconnect.quitting", new Object[0]);
    }

    public int func_56720_c()
    {
        return field_56726_b.func_57271_e();
    }

    public void handleUseEntity(Packet7UseEntity par1Packet7UseEntity)
    {
        WorldServer worldserver = field_56724_d.func_56325_a(field_56725_e.dimension);
        Entity entity = worldserver.func_56861_a(par1Packet7UseEntity.targetEntity);

        if (entity != null)
        {
            boolean flag = field_56725_e.canEntityBeSeen(entity);
            double d = 36D;

            if (!flag)
            {
                d = 9D;
            }

            if (field_56725_e.getDistanceSqToEntity(entity) < d)
            {
                if (par1Packet7UseEntity.isLeftClick == 0)
                {
                    field_56725_e.func_56243_e(entity);
                }
                else if (par1Packet7UseEntity.isLeftClick == 1)
                {
                    field_56725_e.attackTargetEntityWithCurrentItem(entity);
                }
            }
        }
    }

    public void func_56715_a(Packet205ClientCommand par1Packet205ClientCommand)
    {
        if (par1Packet205ClientCommand.field_56587_a == 1)
        {
            if (field_56725_e.field_56275_i)
            {
                field_56725_e = field_56724_d.func_56339_Z().func_57112_a(field_56725_e, 0, true);
            }
            else if (field_56725_e.func_56260_H().getWorldInfo().isHardcoreModeEnabled())
            {
                if (field_56724_d.func_56307_I())
                {
                    field_56725_e.field_56268_a.func_56716_a("You have died. Game over, man, it's game over!");
                    field_56724_d.func_56319_N();
                }
                else
                {
                    BanEntry banentry = new BanEntry(field_56725_e.username);
                    banentry.func_57020_b("Death in Hardcore");
                    field_56724_d.func_56339_Z().func_57110_e().func_57346_a(banentry);
                    field_56725_e.field_56268_a.func_56716_a("You have died. Game over, man, it's game over!");
                }
            }
            else
            {
                if (field_56725_e.getHealth() > 0)
                {
                    return;
                }

                field_56725_e = field_56724_d.func_56339_Z().func_57112_a(field_56725_e, 0, false);
            }
        }
    }

    /**
     * respawns the player
     */
    public void handleRespawn(Packet9Respawn packet9respawn)
    {
    }

    public void handleCloseWindow(Packet101CloseWindow par1Packet101CloseWindow)
    {
        field_56725_e.func_56251_C();
    }

    public void handleWindowClick(Packet102WindowClick par1Packet102WindowClick)
    {
        if (field_56725_e.craftingInventory.windowId == par1Packet102WindowClick.window_Id && field_56725_e.craftingInventory.func_56979_c(field_56725_e))
        {
            ItemStack itemstack = field_56725_e.craftingInventory.slotClick(par1Packet102WindowClick.inventorySlot, par1Packet102WindowClick.mouseClick, par1Packet102WindowClick.holdingShift, field_56725_e);

            if (ItemStack.areItemStacksEqual(par1Packet102WindowClick.itemStack, itemstack))
            {
                field_56725_e.field_56268_a.func_56717_b(new Packet106Transaction(par1Packet102WindowClick.window_Id, par1Packet102WindowClick.action, true));
                field_56725_e.field_56263_g = true;
                field_56725_e.craftingInventory.updateCraftingResults();
                field_56725_e.func_56248_B();
                field_56725_e.field_56263_g = false;
            }
            else
            {
                field_56737_s.addKey(field_56725_e.craftingInventory.windowId, Short.valueOf(par1Packet102WindowClick.action));
                field_56725_e.field_56268_a.func_56717_b(new Packet106Transaction(par1Packet102WindowClick.window_Id, par1Packet102WindowClick.action, false));
                field_56725_e.craftingInventory.func_56982_a(field_56725_e, false);
                ArrayList arraylist = new ArrayList();

                for (int i = 0; i < field_56725_e.craftingInventory.inventorySlots.size(); i++)
                {
                    arraylist.add(((Slot)field_56725_e.craftingInventory.inventorySlots.get(i)).getStack());
                }

                field_56725_e.func_56245_a(field_56725_e.craftingInventory, arraylist);
            }
        }
    }

    public void handleEnchantItem(Packet108EnchantItem par1Packet108EnchantItem)
    {
        if (field_56725_e.craftingInventory.windowId == par1Packet108EnchantItem.windowId && field_56725_e.craftingInventory.func_56979_c(field_56725_e))
        {
            field_56725_e.craftingInventory.enchantItem(field_56725_e, par1Packet108EnchantItem.enchantment);
            field_56725_e.craftingInventory.updateCraftingResults();
        }
    }

    /**
     * Handle a creative slot packet.
     */
    public void handleCreativeSetSlot(Packet107CreativeSetSlot par1Packet107CreativeSetSlot)
    {
        if (field_56725_e.field_56267_c.func_57363_c())
        {
            boolean flag = par1Packet107CreativeSetSlot.slot < 0;
            ItemStack itemstack = par1Packet107CreativeSetSlot.itemStack;
            boolean flag1 = par1Packet107CreativeSetSlot.slot >= 1 && par1Packet107CreativeSetSlot.slot < 36 + InventoryPlayer.func_56198_h();
            boolean flag2 = itemstack == null || itemstack.itemID < Item.itemsList.length && itemstack.itemID >= 0 && Item.itemsList[itemstack.itemID] != null;
            boolean flag3 = itemstack == null || itemstack.getItemDamage() >= 0 && itemstack.getItemDamage() >= 0 && itemstack.stackSize <= 64 && itemstack.stackSize > 0;

            if (flag1 && flag2 && flag3)
            {
                if (itemstack == null)
                {
                    field_56725_e.inventorySlots.putStackInSlot(par1Packet107CreativeSetSlot.slot, null);
                }
                else
                {
                    field_56725_e.inventorySlots.putStackInSlot(par1Packet107CreativeSetSlot.slot, itemstack);
                }

                field_56725_e.inventorySlots.func_56982_a(field_56725_e, true);
            }
            else if (flag && flag2 && flag3 && field_56729_n < 200)
            {
                field_56729_n += 20;
                EntityItem entityitem = field_56725_e.dropPlayerItem(itemstack);

                if (entityitem != null)
                {
                    entityitem.func_56186_n();
                }
            }
        }
    }

    public void handleTransaction(Packet106Transaction par1Packet106Transaction)
    {
        Short short1 = (Short)field_56737_s.lookup(field_56725_e.craftingInventory.windowId);

        if (short1 != null && par1Packet106Transaction.shortWindowId == short1.shortValue() && field_56725_e.craftingInventory.windowId == par1Packet106Transaction.windowId && !field_56725_e.craftingInventory.func_56979_c(field_56725_e))
        {
            field_56725_e.craftingInventory.func_56982_a(field_56725_e, true);
        }
    }

    /**
     * Updates Client side signs
     */
    public void handleUpdateSign(Packet130UpdateSign par1Packet130UpdateSign)
    {
        WorldServer worldserver = field_56724_d.func_56325_a(field_56725_e.dimension);

        if (worldserver.blockExists(par1Packet130UpdateSign.xPosition, par1Packet130UpdateSign.yPosition, par1Packet130UpdateSign.zPosition))
        {
            TileEntity tileentity = worldserver.getBlockTileEntity(par1Packet130UpdateSign.xPosition, par1Packet130UpdateSign.yPosition, par1Packet130UpdateSign.zPosition);

            if (tileentity instanceof TileEntitySign)
            {
                TileEntitySign tileentitysign = (TileEntitySign)tileentity;

                if (!tileentitysign.isEditable())
                {
                    field_56724_d.func_56311_g((new StringBuilder()).append("Player ").append(field_56725_e.username).append(" just tried to change non-editable sign").toString());
                    return;
                }
            }

            for (int i = 0; i < 4; i++)
            {
                boolean flag = true;

                if (par1Packet130UpdateSign.signLines[i].length() > 15)
                {
                    flag = false;
                }
                else
                {
                    for (int l = 0; l < par1Packet130UpdateSign.signLines[i].length(); l++)
                    {
                        if (ChatAllowedCharacters.allowedCharacters.indexOf(par1Packet130UpdateSign.signLines[i].charAt(l)) < 0)
                        {
                            flag = false;
                        }
                    }
                }

                if (!flag)
                {
                    par1Packet130UpdateSign.signLines[i] = "!?";
                }
            }

            if (tileentity instanceof TileEntitySign)
            {
                int j = par1Packet130UpdateSign.xPosition;
                int k = par1Packet130UpdateSign.yPosition;
                int i1 = par1Packet130UpdateSign.zPosition;
                TileEntitySign tileentitysign1 = (TileEntitySign)tileentity;
                System.arraycopy(par1Packet130UpdateSign.signLines, 0, tileentitysign1.signText, 0, 4);
                tileentitysign1.onInventoryChanged();
                worldserver.markBlockNeedsUpdate(j, k, i1);
            }
        }
    }

    /**
     * Handle a keep alive packet.
     */
    public void handleKeepAlive(Packet0KeepAlive par1Packet0KeepAlive)
    {
        if (par1Packet0KeepAlive.randomId == field_56736_i)
        {
            int i = (int)(System.nanoTime() / 0xf4240L - field_56733_j);
            field_56725_e.field_56273_h = (field_56725_e.field_56273_h * 3 + i) / 4;
        }
    }

    /**
     * determine if it is a server handler
     */
    public boolean isServerHandler()
    {
        return true;
    }

    /**
     * Handle a player abilities packet.
     */
    public void handlePlayerAbilities(Packet202PlayerAbilities par1Packet202PlayerAbilities)
    {
        field_56725_e.capabilities.isFlying = par1Packet202PlayerAbilities.func_56564_c() && field_56725_e.capabilities.allowFlying;
    }

    public void func_55321_a(Packet203AutoComplete par1Packet203AutoComplete)
    {
        StringBuilder stringbuilder = new StringBuilder();
        String s;

        for (Iterator iterator = field_56724_d.func_56315_a(field_56725_e, par1Packet203AutoComplete.func_55174_b()).iterator(); iterator.hasNext(); stringbuilder.append(s))
        {
            s = (String)iterator.next();

            if (stringbuilder.length() > 0)
            {
                stringbuilder.append("\0");
            }
        }

        field_56725_e.field_56268_a.func_56717_b(new Packet203AutoComplete(stringbuilder.toString()));
    }

    public void func_55319_a(Packet204ClientInfo par1Packet204ClientInfo)
    {
        field_56725_e.func_56246_a(par1Packet204ClientInfo);
    }

    public void handleCustomPayload(Packet250CustomPayload par1Packet250CustomPayload)
    {
        if ("MC|BEdit".equals(par1Packet250CustomPayload.channel))
        {
            try
            {
                DataInputStream datainputstream = new DataInputStream(new ByteArrayInputStream(par1Packet250CustomPayload.data));
                ItemStack itemstack = Packet.readItemStack(datainputstream);

                if (!ItemWritableBook.func_56831_a(itemstack.getTagCompound()))
                {
                    throw new IOException("Invalid book tag!");
                }

                ItemStack itemstack2 = field_56725_e.inventory.getCurrentItem();

                if (itemstack != null && itemstack.itemID == Item.field_55215_bF.shiftedIndex && itemstack.itemID == itemstack2.itemID)
                {
                    itemstack2.setTagCompound(itemstack.getTagCompound());
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
        else if ("MC|BSign".equals(par1Packet250CustomPayload.channel))
        {
            try
            {
                DataInputStream datainputstream1 = new DataInputStream(new ByteArrayInputStream(par1Packet250CustomPayload.data));
                ItemStack itemstack1 = Packet.readItemStack(datainputstream1);

                if (!ItemEditableBook.func_56822_a(itemstack1.getTagCompound()))
                {
                    throw new IOException("Invalid book tag!");
                }

                ItemStack itemstack3 = field_56725_e.inventory.getCurrentItem();

                if (itemstack1 != null && itemstack1.itemID == Item.field_55216_bG.shiftedIndex && itemstack3.itemID == Item.field_55215_bF.shiftedIndex)
                {
                    itemstack3.setTagCompound(itemstack1.getTagCompound());
                    itemstack3.itemID = Item.field_55216_bG.shiftedIndex;
                }
            }
            catch (Exception exception1)
            {
                exception1.printStackTrace();
            }
        }
        else if ("MC|TrSel".equals(par1Packet250CustomPayload.channel))
        {
            try
            {
                DataInputStream datainputstream2 = new DataInputStream(new ByteArrayInputStream(par1Packet250CustomPayload.data));
                int i = datainputstream2.readInt();
                Container container = field_56725_e.craftingInventory;

                if (container instanceof ContainerMerchant)
                {
                    ((ContainerMerchant)container).func_56984_c(i);
                }
            }
            catch (Exception exception2)
            {
                exception2.printStackTrace();
            }
        }
    }
}
