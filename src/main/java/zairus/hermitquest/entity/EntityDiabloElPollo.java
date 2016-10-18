package zairus.hermitquest.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import zairus.hermitquest.entity.boss.EntityHermitBoss;

public class EntityDiabloElPollo extends EntityChicken
{
	private EntityHermitBoss boss;
	
	public EntityDiabloElPollo(World world)
	{
		this(world, null);
	}
	
	public EntityDiabloElPollo(World world, EntityHermitBoss owner)
	{
		super(world);
		this.boss = owner;
	}
	
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
	}
	
	@Override
	public boolean getCanSpawnHere()
	{
		IBlockState iblockstate = this.worldObj.getBlockState((new BlockPos(this)).down());
		boolean canspawn = iblockstate.func_189884_a(this);
		
		if (iblockstate.getBlock() == Blocks.BEDROCK)
			canspawn = false;
		
		return canspawn;
	}
	
	@Override
	protected void initEntityAI()
	{
		super.initEntityAI();
		
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {this.getClass()}));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
		
		this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
		this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.3D, false));
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		float f = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
		
		double multiplier = (1.0D + (this.motionX + this.motionZ)) * 1.0D;
		f *= (float)multiplier;
		
		DamageSource source = DamageSource.causeMobDamage(this);
		boolean flag = entity.attackEntityFrom(source, f);
		
		if (flag)
		{
			if (entity instanceof EntityPlayer)
			{
				EntityPlayer entityplayer = (EntityPlayer)entity;
				ItemStack itemstack = this.getHeldItemMainhand();
				ItemStack itemstack1 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : null;
				
				if (itemstack != null && itemstack1 != null && itemstack.getItem() instanceof ItemAxe && itemstack1.getItem() == Items.SHIELD)
				{
					float f1 = 0.25F + (float)EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;
					
					if (this.rand.nextFloat() < f1)
					{
						entityplayer.getCooldownTracker().setCooldown(Items.SHIELD, 100);
						this.worldObj.setEntityState(entityplayer, (byte)30);
					}
				}
			}
			
			this.applyEnchantments(this, entity);
		}
		
		return flag;
	}
	
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		
		if (this.rand.nextInt(15) == 0)
		{
			this.motionX = this.getLookVec().xCoord;
			this.motionY = 0.2D;
			this.motionZ = this.getLookVec().zCoord;
		}
	}
	
	@Override
	public void onDeath(DamageSource cause)
	{
		super.onDeath(cause);
		
		if (boss != null)
			boss.decreaseMinion();
	}
	
	@Override
	protected boolean canDespawn()
	{
		return true;
	}
}
