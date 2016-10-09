package zairus.hermitquest.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class HQDamageSource extends DamageSource
{
	private final EntityLivingBase damager;
	
	public HQDamageSource(String damageType, EntityLivingBase entity)
	{
		super(damageType);
		this.damager = entity;
	}
	
	public EntityLivingBase getAttacker()
	{
		return this.damager;
	}
}
