package net.minecraft.src;

public class ChestItemRenderHelper
{
    /** The static instance of ChestItemRenderHelper. */
    public static ChestItemRenderHelper instance = new ChestItemRenderHelper();
    private TileEntityChest field_35610_b;
    private TileEntityEnderChest field_56995_c;

    public ChestItemRenderHelper()
    {
        field_35610_b = new TileEntityChest();
        field_56995_c = new TileEntityEnderChest();
    }

    public void func_35609_a(Block par1Block, int par2, float par3)
    {
        if (par1Block.blockID == Block.field_56777_bS.blockID)
        {
            TileEntityRenderer.instance.renderTileEntityAt(field_56995_c, 0.0D, 0.0D, 0.0D, 0.0F);
        }
        else
        {
            TileEntityRenderer.instance.renderTileEntityAt(field_35610_b, 0.0D, 0.0D, 0.0D, 0.0F);
        }
    }
}
