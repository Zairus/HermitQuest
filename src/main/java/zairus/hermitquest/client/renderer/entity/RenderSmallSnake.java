package zairus.hermitquest.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import zairus.hermitquest.HQConstants;
import zairus.hermitquest.client.model.ModelMiniSnake;
import zairus.hermitquest.entity.EntitySmallSnake;

public class RenderSmallSnake extends RenderLiving<EntitySmallSnake>
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(HQConstants.MODID, "textures/entity/small_snake.png");
	
	public RenderSmallSnake(RenderManager renderManager)
	{
		super(renderManager, new ModelMiniSnake(), 0.3F);
	}
	
	protected float getDeathMaxRotation(EntitySmallSnake entityLivingBase)
	{
		return 180.0F;
	}
	
	protected ResourceLocation getEntityTexture(EntitySmallSnake entity)
	{
		return TEXTURES;
	}
}
