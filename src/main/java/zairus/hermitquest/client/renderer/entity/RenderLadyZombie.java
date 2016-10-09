package zairus.hermitquest.client.renderer.entity;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.model.ModelZombieVillager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerVillagerArmor;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;
import zairus.hermitquest.HQConstants;

public class RenderLadyZombie extends RenderBiped<EntityZombie>
{
	private static final ResourceLocation ZOMBIE_TEXTURES = new ResourceLocation(HQConstants.MODID, "textures/entity/lady_zombie.png");
	private final ModelBiped defaultModel;
	private final ModelZombieVillager zombieVillagerModel;
	private final List<LayerRenderer<EntityZombie>> villagerLayers;
	private final List<LayerRenderer<EntityZombie>> defaultLayers;
	
	public RenderLadyZombie(RenderManager renderManager)
	{
		super(renderManager, new ModelZombie(), 0.5F, 1.0F);
		LayerRenderer<?> layerrenderer = (LayerRenderer<EntityZombie>)this.layerRenderers.get(0);
		this.defaultModel = this.modelBipedMain;
		this.zombieVillagerModel = new ModelZombieVillager();
		this.addLayer(new LayerHeldItem(this));
		
		LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this)
		{
			protected void initArmor()
			{
				this.modelLeggings = new ModelZombie(0.5F, true);
				this.modelArmor = new ModelZombie(1.0F, true);
			}
		};
		
		this.addLayer(layerbipedarmor);
		this.defaultLayers = Lists.newArrayList(this.layerRenderers);
		
		if (layerrenderer instanceof LayerCustomHead)
		{
			this.removeLayer(layerrenderer);
			this.addLayer(new LayerCustomHead(this.zombieVillagerModel.bipedHead));
		}
		
		this.removeLayer(layerbipedarmor);
		this.addLayer(new LayerVillagerArmor(this));
		this.villagerLayers = Lists.newArrayList(this.layerRenderers);
	}
	
	protected void preRenderCallback(EntityZombie entitylivingbaseIn, float partialTickTime)
	{
		super.preRenderCallback(entitylivingbaseIn, partialTickTime);
	}
	
	public void doRender(EntityZombie entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		this.swapArmor(entity);
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
	
	protected ResourceLocation getEntityTexture(EntityZombie entity)
	{
		return ZOMBIE_TEXTURES;
	}
	
	private void swapArmor(EntityZombie zombie)
	{
		if (zombie.isVillager())
		{
			this.mainModel = this.zombieVillagerModel;
			this.layerRenderers = this.villagerLayers;
		}
		else
		{
			this.mainModel = this.defaultModel;
			this.layerRenderers = this.defaultLayers;
		}
		
		this.modelBipedMain = (ModelBiped)this.mainModel;
	}
	
	protected void rotateCorpse(EntityZombie entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks)
	{
		if (entityLiving.isConverting())
		{
			p_77043_3_ += (float)(Math.cos((double)entityLiving.ticksExisted * 3.25D) * Math.PI * 0.25D);
		}
		
		super.rotateCorpse(entityLiving, p_77043_2_, p_77043_3_, partialTicks);
	}
}
