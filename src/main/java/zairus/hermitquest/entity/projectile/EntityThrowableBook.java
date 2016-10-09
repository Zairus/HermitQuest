package zairus.hermitquest.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityThrowableBook extends EntityThrowable
{
	public EntityThrowableBook(World world)
	{
		super(world);
	}
	
	public EntityThrowableBook(World world, EntityLivingBase thrower)
	{
		super(world, thrower);
	}
	
	public EntityThrowableBook(World world, double x, double y, double z)
	{
		super(world, x, y, z);
	}
	
	public static void func_189662_a(DataFixer fixer)
	{
		EntityThrowable.func_189661_a(fixer, "ThrowableBook");
	}
	
	@Override
	protected void onImpact(RayTraceResult result)
	{
		if (result.entityHit != null)
		{
			int i = 0;
			result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float)i);
		}
		
		for (int j = 0; j < 8; ++j)
			this.worldObj.spawnParticle(EnumParticleTypes.CRIT, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
		
		if (!this.worldObj.isRemote)
			this.setDead();
	}
}
