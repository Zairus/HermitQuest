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
import zairus.hermitquest.entity.projectile.EntityImpFireball;

@SideOnly(Side.CLIENT)
public class RenderImpFireball extends Render<EntityImpFireball>
{
	public static final ResourceLocation BEAM_TEXTURES1 = new ResourceLocation(HQConstants.MODID, "textures/items/imp_fireball_1.png");
	public static final ResourceLocation BEAM_TEXTURES2 = new ResourceLocation(HQConstants.MODID, "textures/items/imp_fireball_2.png");
	private final float scale;
	
	public RenderImpFireball(RenderManager renderManager)
	{
		this(renderManager, 1.0F);
	}
	
	public RenderImpFireball(RenderManager renderManager, float scale)
	{
		super(renderManager);
		this.scale = scale;
	}
	
	public void doRender(EntityImpFireball entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		GlStateManager.pushMatrix();
		this.bindEntityTexture(entity);
		GlStateManager.translate((float)x, (float)y, (float)z);
		GlStateManager.enableRescaleNormal();
		GlStateManager.scale(this.scale, this.scale, this.scale);
		
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		
		float f = 0.0F;
		float f1 = 1.0F;
		float f2 = 0.0F;
		float f3 = 1.0F;
		
		GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate((float)(this.renderManager.options.thirdPersonView == 2 ? -1 : 1) * -this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		
		if (this.renderOutlines)
		{
			GlStateManager.enableColorMaterial();
			GlStateManager.enableOutlineMode(this.getTeamColor(entity));
		}
		
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
		vertexbuffer.pos(-0.5D, -0.25D, 0.0D).tex((double)f, (double)f3).normal(0.0F, 1.0F, 0.0F).endVertex();
		vertexbuffer.pos(0.5D, -0.25D, 0.0D).tex((double)f1, (double)f3).normal(0.0F, 1.0F, 0.0F).endVertex();
		vertexbuffer.pos(0.5D, 0.75D, 0.0D).tex((double)f1, (double)f2).normal(0.0F, 1.0F, 0.0F).endVertex();
		vertexbuffer.pos(-0.5D, 0.75D, 0.0D).tex((double)f, (double)f2).normal(0.0F, 1.0F, 0.0F).endVertex();
		tessellator.draw();
		
		if (this.renderOutlines)
		{
			GlStateManager.disableOutlineMode();
			GlStateManager.disableColorMaterial();
		}
		
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
	
	protected ResourceLocation getEntityTexture(EntityImpFireball entity)
	{
		return entity.worldObj.rand.nextInt(2) == 0 ? BEAM_TEXTURES1 : BEAM_TEXTURES2;
	}
}
