package net.minecraft.src;

public class ServerData
{
    public String field_57449_a;
    public String field_57447_b;
    public String field_57448_c;
    public String field_57445_d;
    public long field_57446_e;
    public boolean field_57443_f;
    private boolean field_57444_g;
    private boolean field_57450_h;

    public ServerData(String par1Str, String par2Str)
    {
        field_57443_f = false;
        field_57444_g = true;
        field_57450_h = false;
        field_57449_a = par1Str;
        field_57447_b = par2Str;
    }

    public NBTTagCompound func_57442_a()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setString("name", field_57449_a);
        nbttagcompound.setString("ip", field_57447_b);

        if (!field_57444_g)
        {
            nbttagcompound.setBoolean("acceptTextures", field_57450_h);
        }

        return nbttagcompound;
    }

    public boolean func_57440_b()
    {
        return field_57450_h;
    }

    public boolean func_57441_c()
    {
        return field_57444_g;
    }

    public void func_57438_a(boolean par1)
    {
        field_57450_h = par1;
        field_57444_g = false;
    }

    public static ServerData func_57439_a(NBTTagCompound par0NBTTagCompound)
    {
        ServerData serverdata = new ServerData(par0NBTTagCompound.getString("name"), par0NBTTagCompound.getString("ip"));

        if (par0NBTTagCompound.hasKey("acceptTextures"))
        {
            serverdata.func_57438_a(par0NBTTagCompound.getBoolean("acceptTextures"));
        }

        return serverdata;
    }
}
