package zairus.hermitquest.client.renderer.tileentity;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.hermitquest.HQConstants;
import zairus.hermitquest.block.BlockHealthPack;
import zairus.hermitquest.tileentity.TileEntityHealthPack;

@SideOnly(Side.CLIENT)
public class TileEntityHealthPackRenderer extends TileEntitySpecialRenderer<TileEntityHealthPack>
{
	private static final ResourceLocation TEXTURE_NORMAL = new ResourceLocation(HQConstants.MODID, "textures/entity/health_pack.png");
	private final ModelChest simpleChest = new ModelChest();
	
	public TileEntityHealthPackRenderer()
	{
		;
	}
	
	public void renderTileEntityAt(TileEntityHealthPack te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		GlStateManager.enableDepth();
		GlStateManager.depthFunc(515);
		GlStateManager.depthMask(true);
		int i;
		
		if (te == null)
			te = new TileEntityHealthPack();
		
		if (te.hasWorldObj())
		{
			Block block = te.getBlockType();
			i = te.getBlockMetadata();
			
			if (block instanceof BlockHealthPack && i == 0)
			{
				i = te.getBlockMetadata();
			}
		}
		else
		{
			i = 0;
		}
		
		ModelChest modelchest;
		
		modelchest = this.simpleChest;
		
		if (destroyStage >= 0)
		{
			this.bindTexture(DESTROY_STAGES[destroyStage]);
			GlStateManager.matrixMode(5890);
			GlStateManager.pushMatrix();
			GlStateManager.scale(4.0F, 4.0F, 1.0F);
			GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
			GlStateManager.matrixMode(5888);
		}
		else
		{
			this.bindTexture(TEXTURE_NORMAL);
		}
		
		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();
		
		if (destroyStage < 0)
		{
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		}
		
		GlStateManager.translate((float)x, (float)y + 1.0F, (float)z + 1.0F);
		GlStateManager.scale(1.0F, -1.0F, -1.0F);
		GlStateManager.translate(0.5F, 0.5F, 0.5F);
		int j = 0;
		
		if (i == 2)
		{
			j = 180;
		}
		
		if (i == 3)
		{
			j = 0;
		}
		
		if (i == 4)
		{
			j = 90;
		}
		
		if (i == 5)
		{
			j = -90;
		}
		
		GlStateManager.rotate((float)j, 0.0F, 1.0F, 0.0F);
		GlStateManager.translate(-0.5F, -0.5F, -0.5F);
		float f = te.prevLidAngle + (te.lidAngle - te.prevLidAngle) * partialTicks;
		
		f = 1.0F - f;
		f = 1.0F - f * f * f;
		modelchest.chestLid.rotateAngleX = -(f * ((float)Math.PI / 2F));
		modelchest.renderAll();
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		
		if (destroyStage >= 0)
		{
			GlStateManager.matrixMode(5890);
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(5888);
		}
	}
}
