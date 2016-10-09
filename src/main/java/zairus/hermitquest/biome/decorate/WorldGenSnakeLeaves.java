package zairus.hermitquest.biome.decorate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import zairus.hermitquest.block.HQBlocks;

public class WorldGenSnakeLeaves extends WorldGenDecorationBase
{
	@Override
	public List<Biome>getAllowedBiomes()
	{
		List<Biome> biomes = new ArrayList<Biome>();
		
		biomes.add(Biomes.MUTATED_SWAMPLAND);
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
				Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.OAK), 
				true);
		
		if (logPos == null)
			return false;
		
		this.setBlockInWorld(world, logPos, HQBlocks.LEAVES_SNAKE.getDefaultState());
		
		return true;
	}
}
