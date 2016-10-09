package zairus.hermitquest.biome.decorate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.base.Predicates;

import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import zairus.hermitquest.block.HQBlocks;

public class WorldGenWelsHelm extends WorldGenDecorationBase
{
	private static final BlockStateMatcher IS_SAND = BlockStateMatcher.forBlock(Blocks.SAND).where(BlockSand.VARIANT, Predicates.equalTo(BlockSand.EnumType.SAND));
	private final IBlockState sandSlab = Blocks.STONE_SLAB.getDefaultState().withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.SAND).withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM);
	private final IBlockState sandstone = Blocks.SANDSTONE.getDefaultState();
	private final IBlockState water = Blocks.FLOWING_WATER.getDefaultState();
	
	@Override
	public List<Biome> getAllowedBiomes()
	{
		List<Biome> biomes = new ArrayList<Biome>();
		
		biomes.add(Biomes.DESERT);
		biomes.add(Biomes.DESERT_HILLS);
		biomes.add(Biomes.MUTATED_DESERT);
		
		return biomes;
	}
	
	@Override
	protected boolean doGenerate(World world, Random rand, BlockPos pos)
	{
		if (!(rand.nextInt(rarity) == 0))
			return false;
		
		while (world.isAirBlock(pos) && pos.getY() > 2)
		{
			pos = pos.down();
		}
		
		if (!IS_SAND.apply(world.getBlockState(pos)))
		{
			return false;
		} else {
			for (int i = -2; i <= 2; ++i)
			{
				for (int j = -2; j <= 2; ++j)
				{
					if (world.isAirBlock(pos.add(i, -1, j)) && world.isAirBlock(pos.add(i, -2, j)))
					{
						return false;
					}
				}
			}
			
			for (int l = -1; l <= 0; ++l)
			{
				for (int l1 = -2; l1 <= 2; ++l1)
				{
					for (int k = -2; k <= 2; ++k)
					{
						world.setBlockState(pos.add(l1, l, k), this.sandstone, 2);
					}
				}
			}
			
			world.setBlockState(pos, this.water, 2);
			
			for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
			{
				world.setBlockState(pos.offset(enumfacing), this.water, 2);
			}
			
			for (int i1 = -2; i1 <= 2; ++i1)
			{
				for (int i2 = -2; i2 <= 2; ++i2)
				{
					if (i1 == -2 || i1 == 2 || i2 == -2 || i2 == 2)
					{
						world.setBlockState(pos.add(i1, 1, i2), this.sandstone, 2);
					}
				}
			}
			
			world.setBlockState(pos.add(2, 1, 0), this.sandSlab, 2);
			world.setBlockState(pos.add(-2, 1, 0), this.sandSlab, 2);
			world.setBlockState(pos.add(0, 1, 2), this.sandSlab, 2);
			world.setBlockState(pos.add(0, 1, -2), this.sandSlab, 2);
			
			for (int j1 = -1; j1 <= 1; ++j1)
			{
				for (int j2 = -1; j2 <= 1; ++j2)
				{
					if (j1 == 0 && j2 == 0)
					{
						world.setBlockState(pos.add(j1, 4, j2), this.sandstone, 2);
					} else {
						world.setBlockState(pos.add(j1, 4, j2), this.sandSlab, 2);
					}
				}
			}
			
			for (int k1 = 1; k1 <= 3; ++k1)
			{
				world.setBlockState(pos.add(-1, k1, -1), this.sandstone, 2);
				world.setBlockState(pos.add(-1, k1, 1), this.sandstone, 2);
				world.setBlockState(pos.add(1, k1, -1), this.sandstone, 2);
				world.setBlockState(pos.add(1, k1, 1), this.sandstone, 2);
			}
			
			world.setBlockState(pos.down(), HQBlocks.HELMET_IN_STONE.getDefaultState(), 2);
			
			return true;
		}
	}
}
