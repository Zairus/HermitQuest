package zairus.hermitquest.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.hermitquest.HQConstants;
import zairus.hermitquest.entity.projectile.EntityIskallBeam;

@SideOnly(Side.CLIENT)
public class RenderIskallBeam<T extends EntityIskallBeam> extends Render<T>
{
	public static final ResourceLocation BEAM_TEXTURES = new ResourceLocation(HQConstants.MODID, "textures/entity/projectiles/laser_beam.png");
	
	public RenderIskallBeam(RenderManager renderManager)
	{
		super(renderManager);
	}
	
	@Override
	public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		this.bindEntityTexture(entity);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		GlStateManager.translate((float)x, (float)y, (float)z);
		GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		
		GlStateManager.enableRescaleNormal();
		
		GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.scale(0.05625F, 0.05625F, 0.05625F);
		GlStateManager.translate(-4.0F, 0.0F, 0.0F);
		
		if (this.renderOutlines)
		{
			GlStateManager.enableColorMaterial();
			GlStateManager.enableOutlineMode(this.getTeamColor(entity));
		}
		
		GlStateManager.glNormal3f(0.05625F, 0.0F, 0.0F);
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos(-7.0D, -2.0D, -2.0D).tex(0.0D, 0.15625D).endVertex();
		vertexbuffer.pos(-7.0D, -2.0D, 2.0D).tex(0.15625D, 0.15625D).endVertex();
		vertexbuffer.pos(-7.0D, 2.0D, 2.0D).tex(0.15625D, 0.3125D).endVertex();
		vertexbuffer.pos(-7.0D, 2.0D, -2.0D).tex(0.0D, 0.3125D).endVertex();
		tessellator.draw();
		GlStateManager.glNormal3f(-0.05625F, 0.0F, 0.0F);
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos(-7.0D, 2.0D, -2.0D).tex(0.0D, 0.15625D).endVertex();
		vertexbuffer.pos(-7.0D, 2.0D, 2.0D).tex(0.15625D, 0.15625D).endVertex();
		vertexbuffer.pos(-7.0D, -2.0D, 2.0D).tex(0.15625D, 0.3125D).endVertex();
		vertexbuffer.pos(-7.0D, -2.0D, -2.0D).tex(0.0D, 0.3125D).endVertex();
		tessellator.draw();
		
		for (int j = 0; j < 4; ++j)
		{
			GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.glNormal3f(0.0F, 0.0F, 0.05625F);
			vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
			vertexbuffer.pos(-8.0D, -2.0D, 0.0D).tex(0.0D, 0.0D).endVertex();
			vertexbuffer.pos(8.0D, -2.0D, 0.0D).tex(0.5D, 0.0D).endVertex();
			vertexbuffer.pos(8.0D, 2.0D, 0.0D).tex(0.5D, 0.15625D).endVertex();
			vertexbuffer.pos(-8.0D, 2.0D, 0.0D).tex(0.0D, 0.15625D).endVertex();
			tessellator.draw();
		}
		
		if (this.renderOutlines)
		{
			GlStateManager.disableOutlineMode();
			GlStateManager.disableColorMaterial();
		}
		
		GlStateManager.disableRescaleNormal();
		GlStateManager.enableLighting();
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity)
	{
		return BEAM_TEXTURES;
	}
}
