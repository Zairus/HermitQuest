package zairus.hermitquest.client.renderer.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import zairus.hermitquest.client.renderer.entity.layers.LayerBossElytra;
import zairus.hermitquest.entity.boss.EntityHermitBoss;

public class RenderHermitBoss extends RenderLivingBase<EntityHermitBoss>
{
	public RenderHermitBoss(RenderManager renderManager)
	{
		this(renderManager, false);
	}
	
	public RenderHermitBoss(RenderManager renderManager, boolean useSmallArms)
	{
		super(renderManager, new ModelPlayer(0.0F, useSmallArms), 0.5F);
		//this.smallArms = false;
		this.addLayer(new LayerBipedArmor(this));
		this.addLayer(new LayerHeldItem(this));
		this.addLayer(new LayerArrow(this));
		//this.addLayer(new LayerDeadmau5Head(this));
		//this.addLayer(new LayerCape(this));
		this.addLayer(new LayerCustomHead(this.getMainModel().bipedHead));
		this.addLayer(new LayerBossElytra(this));
	}
	
	public ModelPlayer getMainModel()
	{
		return (ModelPlayer)super.getMainModel();
	}
	
	public void doRender(EntityHermitBoss entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		double d0 = y;
		
		if (entity.isSneaking() && !(entity instanceof EntityHermitBoss))
		{
			d0 = y - 0.125D;
		}
		
		this.setModelVisibilities(entity);
		GlStateManager.enableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
		super.doRender(entity, x, d0, z, entityYaw, partialTicks);
		GlStateManager.disableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
	}
	
	private void setModelVisibilities(EntityHermitBoss clientPlayer)
	{
		ModelPlayer modelplayer = this.getMainModel();
		
		ItemStack itemstack = clientPlayer.getHeldItemMainhand();
		ItemStack itemstack1 = clientPlayer.getHeldItemOffhand();
		modelplayer.setInvisible(true);
		modelplayer.bipedHeadwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.HAT);
		modelplayer.bipedBodyWear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.JACKET);
		modelplayer.bipedLeftLegwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.LEFT_PANTS_LEG);
		modelplayer.bipedRightLegwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.RIGHT_PANTS_LEG);
		modelplayer.bipedLeftArmwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.LEFT_SLEEVE);
		modelplayer.bipedRightArmwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.RIGHT_SLEEVE);
		modelplayer.isSneak = clientPlayer.isSneaking();
		ModelBiped.ArmPose modelbiped$armpose = ModelBiped.ArmPose.EMPTY;
		ModelBiped.ArmPose modelbiped$armpose1 = ModelBiped.ArmPose.EMPTY;
		
		if (itemstack != null)
		{
			modelbiped$armpose = ModelBiped.ArmPose.ITEM;
			
			if (clientPlayer.getItemInUseCount() > 0)
			{
				EnumAction enumaction = itemstack.getItemUseAction();
				
				if (enumaction == EnumAction.BLOCK)
				{
					modelbiped$armpose = ModelBiped.ArmPose.BLOCK;
				}
				else if (enumaction == EnumAction.BOW)
				{
					modelbiped$armpose = ModelBiped.ArmPose.BOW_AND_ARROW;
				}
			}
		}
		
		if (itemstack1 != null)
		{
			modelbiped$armpose1 = ModelBiped.ArmPose.ITEM;
			
			if (clientPlayer.getItemInUseCount() > 0)
			{
				EnumAction enumaction1 = itemstack1.getItemUseAction();
				
				if (enumaction1 == EnumAction.BLOCK)
				{
					modelbiped$armpose1 = ModelBiped.ArmPose.BLOCK;
				}
			}
		}
		
		if (clientPlayer.getPrimaryHand() == EnumHandSide.RIGHT)
		{
			modelplayer.rightArmPose = modelbiped$armpose;
			modelplayer.leftArmPose = modelbiped$armpose1;
		}
		else
		{
			modelplayer.rightArmPose = modelbiped$armpose1;
			modelplayer.leftArmPose = modelbiped$armpose;
		}
	}
	
	protected ResourceLocation getEntityTexture(EntityHermitBoss entity)
	{
		return entity.getLocationSkin();
	}
	
	public void transformHeldFull3DItemLayer()
	{
		GlStateManager.translate(0.0F, 0.1875F, 0.0F);
	}
	
	protected void preRenderCallback(EntityHermitBoss entitylivingbaseIn, float partialTickTime)
	{
		GlStateManager.scale(0.9375F, 0.9375F, 0.9375F);
	}
	
	protected void renderEntityName(EntityHermitBoss entityIn, double x, double y, double z, String name, double distance)
	{
		super.renderEntityName(entityIn, x, y, z, name, distance);
	}
	
	public void renderRightArm(EntityHermitBoss clientPlayer)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F);
		ModelPlayer modelplayer = this.getMainModel();
		this.setModelVisibilities(clientPlayer);
		GlStateManager.enableBlend();
		modelplayer.swingProgress = 0.0F;
		modelplayer.isSneak = false;
		modelplayer.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, clientPlayer);
		modelplayer.bipedRightArm.rotateAngleX = 0.0F;
		modelplayer.bipedRightArm.render(0.0625F);
		modelplayer.bipedRightArmwear.rotateAngleX = 0.0F;
		modelplayer.bipedRightArmwear.render(0.0625F);
		GlStateManager.disableBlend();
	}
	
	public void renderLeftArm(EntityHermitBoss clientPlayer)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F);
		ModelPlayer modelplayer = this.getMainModel();
		this.setModelVisibilities(clientPlayer);
		GlStateManager.enableBlend();
		modelplayer.isSneak = false;
		modelplayer.swingProgress = 0.0F;
		modelplayer.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, clientPlayer);
		modelplayer.bipedLeftArm.rotateAngleX = 0.0F;
		modelplayer.bipedLeftArm.render(0.0625F);
		modelplayer.bipedLeftArmwear.rotateAngleX = 0.0F;
		modelplayer.bipedLeftArmwear.render(0.0625F);
		GlStateManager.disableBlend();
	}
	
	protected void renderLivingAt(EntityHermitBoss entityLivingBaseIn, double x, double y, double z)
	{
		if (entityLivingBaseIn.isEntityAlive() && entityLivingBaseIn.isPlayerSleeping())
		{
			super.renderLivingAt(entityLivingBaseIn, x + (double)entityLivingBaseIn.renderOffsetX, y + (double)entityLivingBaseIn.renderOffsetY, z + (double)entityLivingBaseIn.renderOffsetZ);
		}
		else
		{
			super.renderLivingAt(entityLivingBaseIn, x, y, z);
		}
	}
	
	protected void rotateCorpse(EntityHermitBoss entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks)
	{
		if (entityLiving.isEntityAlive() && entityLiving.isPlayerSleeping())
		{
			GlStateManager.rotate(this.getDeathMaxRotation(entityLiving), 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(270.0F, 0.0F, 1.0F, 0.0F);
		}
		else if (entityLiving.isElytraFlying())
		{
			super.rotateCorpse(entityLiving, p_77043_2_, p_77043_3_, partialTicks);
			float f = (float)entityLiving.getTicksElytraFlying() + partialTicks;
			float f1 = MathHelper.clamp_float(f * f / 100.0F, 0.0F, 1.0F);
			GlStateManager.rotate(f1 * (-90.0F - entityLiving.rotationPitch), 1.0F, 0.0F, 0.0F);
			Vec3d vec3d = entityLiving.getLook(partialTicks);
			double d0 = entityLiving.motionX * entityLiving.motionX + entityLiving.motionZ * entityLiving.motionZ;
			double d1 = vec3d.xCoord * vec3d.xCoord + vec3d.zCoord * vec3d.zCoord;
			
			if (d0 > 0.0D && d1 > 0.0D)
			{
				double d2 = (entityLiving.motionX * vec3d.xCoord + entityLiving.motionZ * vec3d.zCoord) / (Math.sqrt(d0) * Math.sqrt(d1));
				double d3 = entityLiving.motionX * vec3d.zCoord - entityLiving.motionZ * vec3d.xCoord;
				GlStateManager.rotate((float)(Math.signum(d3) * Math.acos(d2)) * 180.0F / (float)Math.PI, 0.0F, 1.0F, 0.0F);
			}
		}
		else
		{
			super.rotateCorpse(entityLiving, p_77043_2_, p_77043_3_, partialTicks);
		}
	}
}
