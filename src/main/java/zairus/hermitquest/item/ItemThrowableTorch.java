package zairus.hermitquest.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import zairus.hermitquest.HermitQuest;
import zairus.hermitquest.entity.projectile.EntityThrowableTorch;

public class ItemThrowableTorch extends Item
{
	public ItemThrowableTorch()
	{
		this.setMaxStackSize(64);
		this.setCreativeTab(HermitQuest.hqTab);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand)
	{
		if (!player.capabilities.isCreativeMode)
			--itemStack.stackSize;
		
		world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		
		if (!world.isRemote)
		{
			EntityThrowableTorch entity = new EntityThrowableTorch(world, player);
			entity.setHeadingFromThrower(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
			world.spawnEntityInWorld(entity);
		}
		
		player.addStat(StatList.getObjectUseStats(this));
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStack);
	}
}
