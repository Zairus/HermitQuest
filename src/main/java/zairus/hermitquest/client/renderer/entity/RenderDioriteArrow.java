package zairus.hermitquest.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.hermitquest.HQConstants;

@SideOnly(Side.CLIENT)
public class RenderDioriteArrow extends RenderArrow<EntityTippedArrow>
{
	public static final ResourceLocation ARROW_TEXTURES = new ResourceLocation(HQConstants.MODID, "textures/entity/projectiles/diorite_arrow.png");
	
	public RenderDioriteArrow(RenderManager manager)
	{
		super(manager);
	}
	
	protected ResourceLocation getEntityTexture(EntityTippedArrow entity)
	{
		return ARROW_TEXTURES;
	}
}
