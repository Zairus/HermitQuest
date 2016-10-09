package zairus.hermitquest.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import zairus.hermitquest.HermitQuest;
import zairus.hermitquest.block.HQBlocks;

public class ItemCosmicDust extends Item
{
	public ItemCosmicDust()
	{
		this.setCreativeTab(HermitQuest.hqTab);
	}
	
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		boolean flag = world.getBlockState(pos).getBlock().isReplaceable(world, pos);
		BlockPos blockpos = flag ? pos : pos.offset(facing);
		
		if (player.canPlayerEdit(blockpos, facing, stack) && world.canBlockBePlaced(world.getBlockState(blockpos).getBlock(), blockpos, false, facing, (Entity)null, stack) && HQBlocks.COSMIC_DUST_WIRE.canPlaceBlockAt(world, blockpos))
		{
			--stack.stackSize;
			world.setBlockState(blockpos, HQBlocks.COSMIC_DUST_WIRE.getDefaultState());
			return EnumActionResult.SUCCESS;
		}
		else
		{
			return EnumActionResult.FAIL;
		}
	}
}
