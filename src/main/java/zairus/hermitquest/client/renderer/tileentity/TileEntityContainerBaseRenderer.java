package zairus.hermitquest.client.renderer.tileentity;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import zairus.hermitquest.block.BlockBuildersChest;
import zairus.hermitquest.tileentity.HQTileEntityBase;
import zairus.hermitquest.tileentity.TileEntityBuildersChest;

public class TileEntityContainerBaseRenderer extends TileEntitySpecialRenderer<HQTileEntityBase>
{
	private final ModelChest simpleChest = new ModelChest();
	
	public TileEntityContainerBaseRenderer()
	{
		;
	}
	
	public void renderTileEntityAt(HQTileEntityBase te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		GlStateManager.enableDepth();
		GlStateManager.depthFunc(515);
		GlStateManager.depthMask(true);
		int i;
		
		if (te == null)
			te = new TileEntityBuildersChest();
		
		if (te.hasWorldObj())
		{
			Block block = te.getBlockType();
			i = te.getBlockMetadata();
			
			if (block instanceof BlockBuildersChest && i == 0)
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
			this.bindTexture(te.getTextures());
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
		float f = te.prevOpenPhase + (te.openPhase - te.prevOpenPhase) * partialTicks;
		
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
