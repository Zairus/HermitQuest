package zairus.hermitquest.biome.decorate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import zairus.hermitquest.block.HQBlocks;

public class WorldGenCatLiter extends WorldGenDecorationBase
{
	@Override
	public List<Biome>getAllowedBiomes()
	{
		List<Biome> biomes = new ArrayList<Biome>();
		
		biomes.add(Biomes.BEACH);
		biomes.add(Biomes.COLD_BEACH);
		
		return biomes;
	}
	
	@Override
	protected boolean doGenerate(World world, Random rand, BlockPos pos)
	{
		if (!(rand.nextInt(rarity) == 0))
			return false;
		
		if (!(world.getBlockState(pos).getBlock() == Blocks.SAND))
			return false;
		
		this.setBlockInWorld(world, pos, HQBlocks.CAT_LITER.getDefaultState());
		
		for (EnumFacing facing : EnumFacing.HORIZONTALS)
		{
			if (rand.nextInt(5) == 0 && world.getBlockState(pos.offset(facing)).getBlock() == Blocks.SAND)
				this.setBlockInWorld(world, pos.offset(facing), HQBlocks.CAT_LITER.getDefaultState());
		}
		
		return true;
	}
}
