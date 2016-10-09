package zairus.hermitquest.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import zairus.hermitquest.entity.boss.EntityHermitBoss;

public class EntityAIHermitBossRanged extends EntityAIBase
{
	private final EntityHermitBoss entity;
	private final double moveSpeedAmp;
	private final float maxAttackDistance;
	private int seeTime;
	private boolean strafingClockwise;
	private boolean strafingBackwards;
	private int strafingTime = -1;
	
	public EntityAIHermitBossRanged(EntityHermitBoss boss, double speedAmplifier, int delay, float maxDistance)
	{
		this.entity = boss;
		this.moveSpeedAmp = speedAmplifier;
		this.maxAttackDistance = maxDistance * maxDistance;
		this.setMutexBits(3);
	}
	
	public void setAttackCooldown(int cooldown)
	{
		;
	}
	
	public boolean shouldExecute()
	{
		return !(this.entity.getAttackTarget() == null);
	}
	
	public boolean continueExecuting()
	{
		return (this.shouldExecute() || !this.entity.getNavigator().noPath());
	}
	
	public void startExecuting()
	{
		super.startExecuting();
	}
	
	public void resetTask()
	{
		super.resetTask();
		this.seeTime = 0;
	}
	
	public void updateTask()
	{
		EntityLivingBase entitylivingbase = this.entity.getAttackTarget();
		
		if (entitylivingbase != null)
		{
			double d0 = this.entity.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
			boolean flag = this.entity.getEntitySenses().canSee(entitylivingbase);
			boolean flag1 = this.seeTime > 0;
			
			if (flag != flag1)
			{
				this.seeTime = 0;
			}
			
			if (flag)
			{
				++this.seeTime;
			}
			else
			{
				--this.seeTime;
			}
			
			if (d0 <= (double)this.maxAttackDistance && this.seeTime >= 20)
			{
				this.entity.getNavigator().clearPathEntity();
				++this.strafingTime;
			}
			else
			{
				this.entity.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.moveSpeedAmp);
				this.strafingTime = -1;
			}
			
			if (this.strafingTime >= 20)
			{
				if ((double)this.entity.getRNG().nextFloat() < 0.3D)
				{
					this.strafingClockwise = !this.strafingClockwise;
				}
				
				if ((double)this.entity.getRNG().nextFloat() < 0.3D)
				{
					this.strafingBackwards = !this.strafingBackwards;
				}
				
				this.strafingTime = 0;
			}
			
			if (this.strafingTime > -1)
			{
				if (d0 > (double)(this.maxAttackDistance * 0.75F))
				{
					this.strafingBackwards = false;
				}
				else if (d0 < (double)(this.maxAttackDistance * 0.25F))
				{
					this.strafingBackwards = true;
				}
				
				this.entity.getMoveHelper().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
				this.entity.faceEntity(entitylivingbase, 30.0F, 30.0F);
			}
			else
			{
				this.entity.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
			}
			
			if (!flag && this.seeTime < -60)
			{
				this.entity.resetActiveHand();
			}
			else if (flag)
			{
				this.entity.attackEntityWithRangedAttack(entitylivingbase, 1);
			}
		}
	}
}
