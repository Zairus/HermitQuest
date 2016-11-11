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

public class WorldGenCrown extends WorldGenDecorationBase
{
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
		
		pos = new BlockPos(pos.getX(), 60, pos.getY());
		
		BlockPos tnt = this.findBlockInArea(world, pos, 20, 20, Blocks.TNT.getDefaultState(), false);
		
		if (tnt == null)
			tnt = this.findBlockInArea(world, pos, 20, 20, Blocks.STAINED_HARDENED_CLAY.getDefaultState(), false);
		
		if (tnt == null)
			return false;
		
		BlockPos crownPos = tnt.add(10 - rand.nextInt(21), rand.nextInt(10), 10 - rand.nextInt(21));
		
		if (world.getBlockState(crownPos).getBlock() == Blocks.SANDSTONE)
		{
			return this.setBlockInWorld(world, crownPos, HQBlocks.CROWN_IN_STONE.getDefaultState());
		}
		
		return false;
	}
}
