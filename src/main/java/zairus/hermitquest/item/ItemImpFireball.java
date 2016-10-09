package zairus.hermitquest.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import zairus.hermitquest.HermitQuest;
import zairus.hermitquest.entity.projectile.EntityImpFireball;
import zairus.hermitquest.sound.HQSoundEvents;

public class ItemImpFireball extends Item
{
	public ItemImpFireball()
	{
		this.setCreativeTab(HermitQuest.hqTab);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand)
	{
		shootFireball(world, player, hand);
		
		if (!player.capabilities.isCreativeMode)
		{
			--itemStack.stackSize;
			if (itemStack.stackSize == 0)
				player.inventory.deleteStack(itemStack);
		}
		
		return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStack);
	}
	
	public static void shootFireball(World world, EntityLivingBase entity, EnumHand hand)
	{
		world.playSound(
				entity.posX, 
				entity.posY, 
				entity.posZ, 
				HQSoundEvents.FIREBALL_SHOOT, 
				SoundCategory.MASTER, 
				1.0F, 
				1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 1.5F, 
				true);
		
		entity.swingArm(hand);
		
		if (!world.isRemote)
		{
			EntityImpFireball beam = new EntityImpFireball(world, entity, entity.getLookVec().xCoord, entity.getLookVec().yCoord, entity.getLookVec().zCoord);
			
			beam.damage = 4;
			beam.posY -= 0.6D;
			beam.configureEffects(4, 1, EnumParticleTypes.SMOKE_NORMAL, EnumParticleTypes.SMOKE_LARGE, SoundEvents.ENTITY_GENERIC_EXPLODE);
			
			world.spawnEntityInWorld(beam);
		}
	}
}
