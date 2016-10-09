package zairus.hermitquest.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.util.ResourceLocation;
import zairus.hermitquest.HQConstants;

public class RenderDiabloElPollo extends RenderChicken
{
	private static final ResourceLocation CHICKEN_TEXTURES = new ResourceLocation(HQConstants.MODID, "textures/entity/diablo_el_pollo.png");
	
	public RenderDiabloElPollo(RenderManager renderManager)
	{
		this(renderManager, new ModelChicken(), 0.5F);
	}
	
	public RenderDiabloElPollo(RenderManager renderManager, ModelBase modelBase, float shadowSizeIn)
	{
		super(renderManager, modelBase, shadowSizeIn);
	}
	
	protected ResourceLocation getEntityTexture(EntityChicken entity)
	{
		return CHICKEN_TEXTURES;
	}
}
