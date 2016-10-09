package zairus.hermitquest.biome.decorate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import zairus.hermitquest.block.HQBlocks;

public class WorldGenGoldenLog extends WorldGenDecorationBase
{
	@Override
	public List<Biome>getAllowedBiomes()
	{
		List<Biome> biomes = new ArrayList<Biome>();
		
		biomes.add(Biomes.PLAINS);
		biomes.add(Biomes.FOREST);
		biomes.add(Biomes.FOREST_HILLS);
		biomes.add(Biomes.SWAMPLAND);
		
		return biomes;
	}
	
	@Override
	protected boolean doGenerate(World world, Random rand, BlockPos pos)
	{
		if (!(rand.nextInt(rarity) == 0))
			return false;
		
		BlockPos logPos = this.findBlockInArea(
				world, 
				pos, 
				3 + rand.nextInt(6), 
				3 + rand.nextInt(6), 
				Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.OAK), 
				true);
		
		if (logPos == null)
			return false;
		
		IBlockState log = world.getBlockState(logPos);
		
		BlockLog.EnumAxis axis = (BlockLog.EnumAxis)log.getValue(BlockLog.LOG_AXIS);
		
		this.setBlockInWorld(world, logPos, HQBlocks.GOLDEN_LOG.getDefaultState().withProperty(BlockLog.LOG_AXIS, axis));
		
		return true;
	}
}
