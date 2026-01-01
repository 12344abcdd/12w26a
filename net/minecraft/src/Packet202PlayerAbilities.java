package net.minecraft.src;

import java.io.*;

public class Packet202PlayerAbilities extends Packet
{
    /** Disables player damage. */
    private boolean disableDamage;

    /** Indicates whether the player is flying or not. */
    private boolean isFlying;

    /** Whether or not to allow the player to fly when they double jump. */
    private boolean allowFlying;

    /**
     * Used to determine if creative mode is enabled, and therefore if items should be depleted on usage
     */
    private boolean isCreativeMode;
    private float field_56566_e;
    private float field_56565_f;

    public Packet202PlayerAbilities()
    {
        disableDamage = false;
        isFlying = false;
        allowFlying = false;
        isCreativeMode = false;
    }

    public Packet202PlayerAbilities(PlayerCapabilities par1PlayerCapabilities)
    {
        disableDamage = false;
        isFlying = false;
        allowFlying = false;
        isCreativeMode = false;
        func_56558_a(par1PlayerCapabilities.disableDamage);
        func_56554_b(par1PlayerCapabilities.isFlying);
        func_56559_c(par1PlayerCapabilities.allowFlying);
        func_56562_d(par1PlayerCapabilities.isCreativeMode);
        func_56555_a(par1PlayerCapabilities.func_57527_a());
        func_56561_b(par1PlayerCapabilities.func_57528_b());
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        byte byte0 = par1DataInputStream.readByte();
        func_56558_a((byte0 & 1) > 0);
        func_56554_b((byte0 & 2) > 0);
        func_56559_c((byte0 & 4) > 0);
        func_56562_d((byte0 & 8) > 0);
        func_56555_a((float)par1DataInputStream.readByte() / 255F);
        func_56561_b((float)par1DataInputStream.readByte() / 255F);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        byte byte0 = 0;

        if (func_56563_b())
        {
            byte0 |= 1;
        }

        if (func_56564_c())
        {
            byte0 |= 2;
        }

        if (func_56557_d())
        {
            byte0 |= 4;
        }

        if (func_56556_e())
        {
            byte0 |= 8;
        }

        par1DataOutputStream.writeByte(byte0);
        par1DataOutputStream.writeByte((int)(field_56566_e * 255F));
        par1DataOutputStream.writeByte((int)(field_56565_f * 255F));
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handlePlayerAbilities(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 2;
    }

    public boolean func_56563_b()
    {
        return disableDamage;
    }

    public void func_56558_a(boolean par1)
    {
        disableDamage = par1;
    }

    public boolean func_56564_c()
    {
        return isFlying;
    }

    public void func_56554_b(boolean par1)
    {
        isFlying = par1;
    }

    public boolean func_56557_d()
    {
        return allowFlying;
    }

    public void func_56559_c(boolean par1)
    {
        allowFlying = par1;
    }

    public boolean func_56556_e()
    {
        return isCreativeMode;
    }

    public void func_56562_d(boolean par1)
    {
        isCreativeMode = par1;
    }

    public float func_56560_f()
    {
        return field_56566_e;
    }

    public void func_56555_a(float par1)
    {
        field_56566_e = par1;
    }

    public void func_56561_b(float par1)
    {
        field_56565_f = par1;
    }
}
