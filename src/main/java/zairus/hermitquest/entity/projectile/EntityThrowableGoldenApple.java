package zairus.hermitquest.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityThrowableGoldenApple extends EntityThrowable
{
	public EntityThrowableGoldenApple(World world)
	{
		super(world);
	}
	
	public EntityThrowableGoldenApple(World world, EntityLivingBase thrower)
	{
		super(world, thrower);
	}
	
	public EntityThrowableGoldenApple(World world, double x, double y, double z)
	{
		super(world, x, y, z);
	}
	
	public static void func_189662_a(DataFixer fixer)
	{
		EntityThrowable.func_189661_a(fixer, "GoldenApple");
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
		
		if (result.entityHit instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)result.entityHit;
			player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100, 1));
            player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 2400, 0));
		}
		
		if (!this.worldObj.isRemote)
			this.setDead();
	}
}
