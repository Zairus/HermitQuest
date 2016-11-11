package zairus.hermitquest.biome.decorate;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenVillage;
import zairus.hermitquest.block.HQBlocks;

public class WorldGenCraftedBench extends WorldGenDecorationBase
{
	@Override
	public List<Biome>getAllowedBiomes()
	{
		return MapGenVillage.VILLAGE_SPAWN_BIOMES;
	}
	
	@Override
	protected boolean doGenerate(World world, Random rand, BlockPos pos)
	{
		if (!(rand.nextInt(rarity) == 0))
			return false;
		
		BlockPos bench = this.findBlockInArea(world, pos, 20, 20, Blocks.CRAFTING_TABLE.getDefaultState(), true);
		
		if (bench == null)
			return false;
		
		this.setBlockInWorld(world, bench, HQBlocks.CRAFTED_BENCH.getDefaultState());
		
		return true;
	}
}
