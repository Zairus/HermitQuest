package zairus.hermitquest.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import zairus.hermitquest.HQConstants;
import zairus.hermitquest.client.renderer.entity.layers.LayerJevinSlimeGel;
import zairus.hermitquest.entity.EntityJevinSlime;

public class RenderJevinSlime extends RenderLiving<EntityJevinSlime>
{
	private static final ResourceLocation SLIME_TEXTURES = new ResourceLocation(HQConstants.MODID, "textures/entity/jevin_slime.png");
	
	public RenderJevinSlime(RenderManager renderManager)
	{
		this(renderManager, new ModelSlime(1), 1.0F);
	}
	
	public RenderJevinSlime(RenderManager renderManager, ModelBase modelBase, float shadowSize)
	{
		super(renderManager, modelBase, shadowSize);
		this.addLayer(new LayerJevinSlimeGel(this));
	}
	
	public void doRender(EntityJevinSlime entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		this.shadowSize = 0.25F * (float)entity.getSlimeSize();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
	
	protected void preRenderCallback(EntityJevinSlime entitylivingbaseIn, float partialTickTime)
	{
		//float f = 0.999F;
		GlStateManager.scale(0.999F, 0.999F, 0.999F);
		float f1 = (float)entitylivingbaseIn.getSlimeSize();
		float f2 = (entitylivingbaseIn.prevSquishFactor + (entitylivingbaseIn.squishFactor - entitylivingbaseIn.prevSquishFactor) * partialTickTime) / (f1 * 0.5F + 1.0F);
		float f3 = 1.0F / (f2 + 1.0F);
		GlStateManager.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
	}
	
	protected ResourceLocation getEntityTexture(EntityJevinSlime entity)
	{
		return SLIME_TEXTURES;
	}
}
