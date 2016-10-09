package zairus.hermitquest.biome.decorate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import zairus.hermitquest.block.HQBlocks;

public class WorldGenOceanGlass extends WorldGenDecorationBase
{
	public WorldGenOceanGlass()
	{
		super(GenerationType.ANYWHERE);
	}
	
	@Override
	public List<Biome> getAllowedBiomes()
	{
		List<Biome> biomes = new ArrayList<Biome>();
		
		biomes.add(Biomes.OCEAN);
		biomes.add(Biomes.DEEP_OCEAN);
		
		return biomes;
	}
	
	@Override
	protected boolean doGenerate(World world, Random rand, BlockPos pos)
	{
		if (!(rand.nextInt(rarity) == 0))
			return false;
		
		if (world.getBlockState(pos).getBlock() == Blocks.GRAVEL)
		{
			return this.setBlockInWorld(world, pos, HQBlocks.OCEAN_GLASS.getDefaultState());
		}
		
		return false;
	}
}
