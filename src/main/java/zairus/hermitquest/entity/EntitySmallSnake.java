package zairus.hermitquest.entity;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import zairus.hermitquest.entity.boss.EntityHermitBoss;
import zairus.hermitquest.sound.HQSoundEvents;

public class EntitySmallSnake extends EntityMob
{
	private int lifetime;
    private boolean playerSpawned;
	private EntityHermitBoss boss;
	
	public EntitySmallSnake(World world)
	{
		super(world);
		this.experienceValue = 3;
        this.setSize(0.4F, 0.3F);
	}
	
	public EntitySmallSnake(World world, EntityHermitBoss owner)
	{
		this(world);
		this.boss = owner;
	}
	
	@Override
	public void onDeath(DamageSource cause)
	{
		super.onDeath(cause);
		
		if (boss != null)
			boss.decreaseMinion();
	}
	
	@Override
	protected void initEntityAI()
	{
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
		this.tasks.addTask(3, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
	}
	
	@Override
	public float getEyeHeight()
	{
		return 0.1F;
	}
	
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
	}
	
	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}
	
	@Override
	protected SoundEvent getAmbientSound()
	{
		return HQSoundEvents.LEAVES_HISS;
	}
	
	@Override
	protected SoundEvent getHurtSound()
	{
		return SoundEvents.ENTITY_ENDERMITE_HURT;
	}
	
	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_ENDERMITE_DEATH;
	}
	
	@Override
	protected void playStepSound(BlockPos pos, Block blockIn)
	{
		this.playSound(SoundEvents.ENTITY_ENDERMITE_STEP, 0.15F, 1.0F);
	}
	
	@Override
	@Nullable
	protected ResourceLocation getLootTable()
	{
		return LootTableList.ENTITIES_ENDERMITE;
	}
	
	public static void func_189764_b(DataFixer p_189764_0_)
	{
		EntityLiving.func_189752_a(p_189764_0_, "Endermite");
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);
		this.lifetime = compound.getInteger("Lifetime");
		this.playerSpawned = compound.getBoolean("PlayerSpawned");
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);
		compound.setInteger("Lifetime", this.lifetime);
		compound.setBoolean("PlayerSpawned", this.playerSpawned);
	}
	
	@Override
	public void onUpdate()
	{
		this.renderYawOffset = this.rotationYaw;
		super.onUpdate();
	}
	
	@Override
	public double getYOffset()
	{
		return 0.3D;
	}
	
	public boolean isSpawnedByPlayer()
	{
		return this.playerSpawned;
	}
	
	public void setSpawnedByPlayer(boolean spawnedByPlayer)
	{
		this.playerSpawned = spawnedByPlayer;
	}
	
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
	}
	
	@Override
	protected boolean isValidLightLevel()
	{
		return true;
	}
	
	@Override
	public boolean getCanSpawnHere()
	{
		boolean canSpawn = super.getCanSpawnHere();
		
		return canSpawn;
	}
	
	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEFINED;
	}
}
