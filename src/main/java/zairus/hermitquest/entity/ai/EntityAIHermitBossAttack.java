package zairus.hermitquest.entity.ai;

import net.minecraft.entity.ai.EntityAIAttackMelee;
import zairus.hermitquest.entity.boss.EntityHermitBoss;

public class EntityAIHermitBossAttack extends EntityAIAttackMelee
{
	public EntityAIHermitBossAttack(EntityHermitBoss boss, double speed, boolean useLongMemory)
	{
		super(boss, speed, useLongMemory);
	}
}
