package zairus.hermitquest.client.renderer.tileentity;

import javax.annotation.Nullable;

import net.minecraft.client.model.ModelBanner;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.hermitquest.block.HQBlocks;
import zairus.hermitquest.tileentity.TileEntityHermitBanner;

@SideOnly(Side.CLIENT)
public class TileEntityHermitBannerRenderer extends TileEntitySpecialRenderer<TileEntityHermitBanner>
{
	private final ModelBanner bannerModel = new ModelBanner();
	
	public TileEntityHermitBannerRenderer()
	{
		this.setRendererDispatcher(TileEntityRendererDispatcher.instance);
	}
	
	@Override
	public void renderTileEntityAt(TileEntityHermitBanner te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		if (te == null)
			te = new TileEntityHermitBanner();
		
		boolean flag = te.getWorld() != null;
		boolean flag1 = !flag || te.getBlockType() == HQBlocks.STAINDING_HERMIT_BANNER;
		int i = flag ? te.getBlockMetadata() : 0;
		long j = flag ? te.getWorld().getTotalWorldTime() % 120000 : 0L;
		GlStateManager.pushMatrix();
		
		if (flag1)
		{
			GlStateManager.translate((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
			float f1 = (float)(i * 360) / 16.0F;
			GlStateManager.rotate(-f1, 0.0F, 1.0F, 0.0F);
			this.bannerModel.bannerStand.showModel = true;
		}
		else
		{
			float f2 = 0.0F;
			
			if (i == 2)
				f2 = 180.0F;
			
			if (i == 4)
				f2 = 90.0F;
			
			if (i == 5)
				f2 = -90.0F;
			
			GlStateManager.translate((float)x + 0.5F, (float)y - 0.16666667F, (float)z + 0.5F);
			GlStateManager.rotate(-f2, 0.0F, 1.0F, 0.0F);
			GlStateManager.translate(0.0F, -0.3125F, -0.4375F);
			this.bannerModel.bannerStand.showModel = false;
		}
		
		BlockPos blockpos = te.getPos();
		float f3 = (float)(blockpos.getX() * 7 + blockpos.getY() * 9 + blockpos.getZ() * 13) + (float)j + partialTicks;
		this.bannerModel.bannerSlate.rotateAngleX = (-0.0125F + 0.01F * MathHelper.cos(f3 * (float)Math.PI * 0.02F)) * (float)Math.PI;
		GlStateManager.enableRescaleNormal();
		ResourceLocation resourcelocation = this.getBannerResourceLocation(te);
		
		if (resourcelocation != null)
		{
			this.bindTexture(resourcelocation);
			GlStateManager.pushMatrix();
			GlStateManager.scale(0.6666667F, -0.6666667F, -0.6666667F);
			this.bannerModel.renderBanner();
			GlStateManager.popMatrix();
		}
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();
	}
	
	@Nullable
	private ResourceLocation getBannerResourceLocation(TileEntityHermitBanner bannerObj)
	{
		return bannerObj.getResourceLocation();
	}
}
