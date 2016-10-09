package zairus.hermitquest.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityImpFireball extends EntityIskallBeam
{
	public EntityImpFireball(World world)
	{
		super(world);
	}
	
	public EntityImpFireball(World world, EntityLivingBase shooter, double dirX, double dirY, double dirZ)
	{
		super(world, shooter);
		this.motionX = dirX * 0.8D;
		this.motionY = dirY * 0.8D;
		this.motionZ = dirZ * 0.8D;
	}
}
