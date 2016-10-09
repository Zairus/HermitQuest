package zairus.hermitquest.block;

import java.util.List;
import java.util.Queue;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import zairus.hermitquest.HermitQuest;

public class BlockOrangeSponge extends Block
{
	public BlockOrangeSponge()
	{
		super(Material.SPONGE);
		this.setCreativeTab(HermitQuest.hqTab);
		this.setHardness(1.0F);
		this.setSoundType(SoundType.PLANT);
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		this.tryAbsorb(world, pos, state);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block)
	{
		this.tryAbsorb(world, pos, state);
		super.neighborChanged(state, world, pos, block);
	}
	
	protected void tryAbsorb(World world, BlockPos pos, IBlockState state)
	{
		if (this.absorb(world, pos))
		{
			world.playEvent(2001, pos, Block.getIdFromBlock(Blocks.WATER));
		}
	}
	
	private boolean absorb(World world, BlockPos pos)
	{
		Queue<Tuple<BlockPos, Integer>> queue = Lists.<Tuple<BlockPos, Integer>>newLinkedList();
		List<BlockPos> list = Lists.<BlockPos>newArrayList();
		queue.add(new Tuple<BlockPos, Integer>(pos, Integer.valueOf(0)));
		int i = 0;
		
		while (!((Queue<Tuple<BlockPos, Integer>>)queue).isEmpty())
		{
			Tuple<BlockPos, Integer> tuple = (Tuple<BlockPos, Integer>)queue.poll();
			BlockPos blockpos = (BlockPos)tuple.getFirst();
			int j = ((Integer)tuple.getSecond()).intValue();
			
			for (EnumFacing enumfacing : EnumFacing.values())
			{
				BlockPos blockpos1 = blockpos.offset(enumfacing);
				
				if (world.getBlockState(blockpos1).getMaterial() == Material.WATER)
				{
					world.setBlockState(blockpos1, Blocks.AIR.getDefaultState(), 2);
					list.add(blockpos1);
					++i;
					
					if (j < 48)
					{
						queue.add(new Tuple<BlockPos, Integer>(blockpos1, Integer.valueOf(j + 1)));
					}
				}
			}
			
			if (i > 64)
			{
				break;
			}
		}
		
		for (BlockPos blockpos2 : list)
		{
			world.notifyNeighborsOfStateChange(blockpos2, Blocks.AIR);
		}
		
		return i > 0;
	}
}
