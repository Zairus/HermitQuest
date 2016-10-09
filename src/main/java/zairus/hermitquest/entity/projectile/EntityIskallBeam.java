package zairus.hermitquest.entity.projectile;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.hermitquest.sound.HQSoundEvents;

public class EntityIskallBeam extends Entity implements IProjectile
{
	private int particleRarity = 3;
	private int particleHitRarity = 2;
	private EnumParticleTypes impulseParticle = EnumParticleTypes.CLOUD;
	private EnumParticleTypes hitParticle = EnumParticleTypes.EXPLOSION_LARGE;
	private SoundEvent hitSound = HQSoundEvents.BEAM_HIT;
	
	@SuppressWarnings({ "unchecked" })
	private static final Predicate<Entity> BEAM_TARGETS = Predicates.<Entity>and(
			new Predicate[] {
					EntitySelectors.NOT_SPECTATING, 
					EntitySelectors.IS_ALIVE, 
					new Predicate<Entity>()
					{
						public boolean apply(@Nullable Entity entity)
						{
							return entity.canBeCollidedWith();
						}
					}});
	
	public Entity shootingEntity;
	
	public EntityIskallBeam(World world)
	{
		super(world);
		this.setSize(0.5F, 0.5F);
	}
	
	public EntityIskallBeam(World world, double x, double y, double z)
	{
		super(world);
		this.setPosition(x, y, z);
	}
	
	public EntityIskallBeam(World world, EntityLivingBase shooter, double dirX, double dirY, double dirZ)
	{
		this(world, shooter);
		this.motionX = dirX;
		this.motionY = dirY;
		this.motionZ = dirZ;
	}
	
	public EntityIskallBeam(World world, EntityLivingBase shooter)
	{
		this(world, shooter.posX, shooter.posY + shooter.getEyeHeight(), shooter.posZ);
		this.shootingEntity = shooter;
	}
	
	public EntityIskallBeam configureEffects(int rarity, int hitRarity, EnumParticleTypes impulsePart, EnumParticleTypes hitPart, SoundEvent hitSound)
	{
		this.particleRarity = rarity;
		this.particleHitRarity = hitRarity;
		this.impulseParticle = impulsePart;
		this.hitParticle = hitPart;
		this.hitSound = hitSound;
		
		return this;
	}
	
	@Override
	public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy)
	{
		float f = MathHelper.sqrt_double(x * x + y * y + z * z);
		x = x / (double)f;
		y = y / (double)f;
		z = z / (double)f;
		x = x + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
		y = y + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
		z = z + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
		x = x * (double)velocity;
		y = y * (double)velocity;
		z = z * (double)velocity;
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
		float f1 = MathHelper.sqrt_double(x * x + z * z);
		this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180D / Math.PI));
		this.rotationPitch = (float)(MathHelper.atan2(y, (double)f1) * (180D / Math.PI));
		this.prevRotationYaw = this.rotationYaw;
		this.prevRotationPitch = this.rotationPitch;
	}
	
	@Override
	protected void entityInit()
	{
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance)
	{
		double d0 = this.getEntityBoundingBox().getAverageEdgeLength() * 10.0D;
		
		if (Double.isNaN(d0))
		{
			d0 = 1.0D;
		}
		
		d0 = d0 * 64.0D * getRenderDistanceWeight();
		
		return distance < d0 * d0;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport)
	{
		this.setPosition(x, y, z);
		this.setRotation(yaw, pitch);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void setVelocity(double x, double y, double z)
	{
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
		
		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
		{
			float f = MathHelper.sqrt_double(x * x + z * z);
			this.rotationPitch = (float)(MathHelper.atan2(y, (double)f) * (180D / Math.PI));
			this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180D / Math.PI));
			this.prevRotationPitch = this.rotationPitch;
			this.prevRotationYaw = this.rotationYaw;
			this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
		}
	}
	
	public int damage = 1;
	public int knockbackStrength = 1;
	public int ticksInAir;
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		
		Vec3d vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
		Vec3d vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		RayTraceResult raytraceresult = this.worldObj.rayTraceBlocks(vec3d1, vec3d, false, true, false);
		vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
		vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		
		if (raytraceresult != null)
			vec3d = new Vec3d(raytraceresult.hitVec.xCoord, raytraceresult.hitVec.yCoord, raytraceresult.hitVec.zCoord);
		
		Entity entity = this.findEntityOnPath(vec3d1, vec3d);
		
		if (entity != null)
			raytraceresult = new RayTraceResult(entity);
		
		if (raytraceresult != null && raytraceresult.entityHit != null && raytraceresult.entityHit instanceof EntityPlayer)
		{
			EntityPlayer entityplayer = (EntityPlayer)raytraceresult.entityHit;
			if (this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)this.shootingEntity).canAttackPlayer(entityplayer))
				raytraceresult = null;
		}
		
		if (raytraceresult != null && ticksInAir > 2)
			this.onHit(raytraceresult);
		
		for (int k = 0; this.rand.nextInt(particleRarity) == 0 && k < 1; ++k)
			this.worldObj.spawnParticle(
					impulseParticle, 
					this.posX + this.motionX * (double)k / 4.0D, 
					this.posY + this.motionY * (double)k / 4.0D, 
					this.posZ + this.motionZ * (double)k / 4.0D, 
					-this.motionX, 
					-this.motionY + 0.2D, 
					-this.motionZ, 
					new int[0]);
		
		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		
		++ticksInAir;
		
		this.setPosition(this.posX, this.posY, this.posZ);
		this.doBlockCollisions();
	}
	
	protected void onHit(RayTraceResult raytraceResultIn)
	{
		Entity entity = raytraceResultIn.entityHit;
		
		if (entity != null)
		{
			float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
			int i = MathHelper.ceiling_double_int((double)f * this.damage);
			
			DamageSource damagesource;
			
			if (this.shootingEntity == null)
				damagesource = DamageSource.causeIndirectMagicDamage(this, this);
			else
				damagesource = DamageSource.causeIndirectMagicDamage(this, this.shootingEntity);
			
			if (entity.attackEntityFrom(damagesource, (float)i))
			{
				if (entity instanceof EntityLivingBase)
				{
					EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
					
					float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
					
					if (f1 > 0.0F)
						entitylivingbase.addVelocity(this.motionX * (double)this.knockbackStrength * 0.6000000238418579D / (double)f1, 0.1D, this.motionZ * (double)this.knockbackStrength * 0.6000000238418579D / (double)f1);
					
					if (this.shootingEntity instanceof EntityLivingBase)
					{
						EnchantmentHelper.applyThornEnchantments(entitylivingbase, this.shootingEntity);
						EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase)this.shootingEntity, entitylivingbase);
					}
				}
			}
		}
		
		for (int k = 0; this.rand.nextInt(particleHitRarity) == 0 && k < 5; ++k)
			this.worldObj.spawnParticle(
					hitParticle, 
					this.posX + this.motionX * (double)k / 4.0D, 
					this.posY + this.motionY * (double)k / 4.0D, 
					this.posZ + this.motionZ * (double)k / 4.0D, 
					-this.motionX, 
					-this.motionY + 0.2D, 
					-this.motionZ, 
					new int[0]);
		
		this.setDead();
		this.playSound(hitSound, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
	}
	
	@Nullable
	protected Entity findEntityOnPath(Vec3d start, Vec3d end)
	{
		Entity entity = null;
		List<Entity> list = this.worldObj.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expandXyz(1.0D), BEAM_TARGETS);
		double d0 = 0.0D;
		
		for (int i = 0; i < list.size(); ++i)
		{
			Entity entity1 = list.get(i);
			
			if (entity1 != this.shootingEntity)
			{
				AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expandXyz(0.30000001192092896D);
				RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(start, end);
				
				if (raytraceresult != null)
				{
					double d1 = start.squareDistanceTo(raytraceresult.hitVec);
					
					if (d1 < d0 || d0 == 0.0D)
					{
						entity = entity1;
						d0 = d1;
					}
				}
			}
		}
		
		return entity;
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound)
	{
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound compound)
	{
	}
}
