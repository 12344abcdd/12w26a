package net.minecraft.src;

public class IntegratedPlayerList extends ServerConfigurationManager
{
    private NBTTagCompound field_57150_e;

    public IntegratedPlayerList(IntegratedServer par1IntegratedServer)
    {
        super(par1IntegratedServer);
        field_57150_e = null;
        field_57131_d = 10;
    }

    protected void func_57118_b(EntityPlayerMP par1EntityPlayerMP)
    {
        if (par1EntityPlayerMP.func_55088_aJ().equals(func_57149_r().func_56296_H()))
        {
            field_57150_e = new NBTTagCompound();
            par1EntityPlayerMP.writeToNBT(field_57150_e);
        }

        super.func_57118_b(par1EntityPlayerMP);
    }

    public IntegratedServer func_57149_r()
    {
        return (IntegratedServer)super.func_57085_p();
    }

    public NBTTagCompound func_57119_q()
    {
        return field_57150_e;
    }

    public MinecraftServer func_57085_p()
    {
        return func_57149_r();
    }
}
