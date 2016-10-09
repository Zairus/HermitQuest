package zairus.hermitquest.block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import zairus.hermitquest.HermitQuest;

public class BlockCoffeeBeans extends BlockCocoa
{
	public BlockCoffeeBeans()
	{
		super();
		this.setCreativeTab(HermitQuest.hqTab);
		this.setHardness(0.2F);
		this.setResistance(5.0F);
		this.setHarvestLevel("axe", 0);
		this.setSoundType(SoundType.WOOD);
	}
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		List<ItemStack> list = new ArrayList<ItemStack>();
		
		int age = state.getValue(BlockCocoa.AGE);
		
		list.add(new ItemStack(this, age + 1, 0));
		
		return list;
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
	{
		boolean canPlace = false;
		
		for (EnumFacing facing : EnumFacing.HORIZONTALS)
		{
			if (canBlockStay(world, pos, this.getDefaultState().withProperty(FACING, facing)))
				canPlace = true;
		}
		
		return canPlace;
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		for (EnumFacing facing : EnumFacing.HORIZONTALS)
		{
			if (canBlockStay(world, pos, state.withProperty(FACING, facing)))
			{
				world.setBlockState(pos, state.withProperty(FACING, facing), 2);
			}
		}
	}
	
	@Override
	public boolean canBlockStay(World world, BlockPos pos, IBlockState state)
	{
		pos = pos.offset((EnumFacing)state.getValue(FACING));
		IBlockState iblockstate = world.getBlockState(pos);
		return iblockstate.getBlock() == Blocks.LOG && iblockstate.getValue(BlockOldLog.VARIANT) == BlockPlanks.EnumType.JUNGLE;
	}
}
