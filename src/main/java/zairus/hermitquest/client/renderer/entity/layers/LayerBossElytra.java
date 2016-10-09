package zairus.hermitquest.client.renderer.entity.layers;

import net.minecraft.client.model.ModelElytra;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import zairus.hermitquest.client.renderer.entity.RenderHermitBoss;
import zairus.hermitquest.entity.boss.EntityHermitBoss;

public class LayerBossElytra implements LayerRenderer<EntityHermitBoss>
{
	private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation("textures/entity/elytra.png");
	private final RenderHermitBoss renderHermitBoss;
	private final ModelElytra modelElytra = new ModelElytra();
	
	public LayerBossElytra(RenderHermitBoss renderer)
	{
		this.renderHermitBoss = renderer;
	}
	
	public void doRenderLayer(EntityHermitBoss entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		ItemStack itemstack = entitylivingbaseIn.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
		
		if (itemstack != null && itemstack.getItem() == Items.ELYTRA)
		{
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableBlend();
			
			this.renderHermitBoss.bindTexture(TEXTURE_ELYTRA);
			
			GlStateManager.pushMatrix();
			GlStateManager.translate(0.0F, 0.0F, 0.125F);
			this.modelElytra.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entitylivingbaseIn);
			this.modelElytra.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
			
			if (itemstack.isItemEnchanted())
			{
				LayerArmorBase.renderEnchantedGlint(this.renderHermitBoss, entitylivingbaseIn, this.modelElytra, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
			}
			
			GlStateManager.popMatrix();
		}
	}
	
	public boolean shouldCombineTextures()
	{
		return false;
	}
}
