package zairus.hermitquest.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import zairus.hermitquest.item.HQItems;
import zairus.hermitquest.sound.HQSoundEvents;

public class EntityDioriteArrow extends EntityTippedArrow
{
	public EntityDioriteArrow(World world)
	{
		super(world);
	}
	
	public EntityDioriteArrow(World world, EntityLivingBase shooter)
	{
		super(world, shooter);
	}
	
	@Override
	protected void onHit(RayTraceResult raytraceResult)
	{
		super.onHit(raytraceResult);
		this.playSound(HQSoundEvents.POOP_SPLAT, 1.8F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
	}
	
	@Override
	protected void arrowHit(EntityLivingBase living)
	{
		super.arrowHit(living);
	}
	
	@Override
	protected ItemStack getArrowStack()
	{
		ItemStack itemstack = new ItemStack(HQItems.DIORITE_ARROW);
		
		return itemstack;
	}
}
