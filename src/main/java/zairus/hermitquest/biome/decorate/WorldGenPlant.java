package zairus.hermitquest.biome.decorate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class WorldGenPlant extends WorldGenDecorationBase
{
	private IBlockState plant;
	private List<IBlockState> allowedBlocks;
	
	public WorldGenPlant(IBlockState genPlant)
	{
		this.plant = genPlant;
		initAllowedBlocks();
	}
	
	@Override
	public List<Biome>getAllowedBiomes()
	{
		List<Biome> biomes = new ArrayList<Biome>();
		
		biomes.add(Biomes.JUNGLE);
		biomes.add(Biomes.JUNGLE_EDGE);
		biomes.add(Biomes.JUNGLE_HILLS);
		biomes.add(Biomes.MUTATED_JUNGLE);
		biomes.add(Biomes.MUTATED_JUNGLE_EDGE);
		
		return biomes;
	}
	
	public WorldGenPlant setAllowedBlocks(IBlockState... states)
	{
		allowedBlocks = new ArrayList<IBlockState>();
		
		for(IBlockState s : states)
		{
			allowedBlocks.add(s);
		}
		
		return this;
	}
	
	private void initAllowedBlocks()
	{
		allowedBlocks = new ArrayList<IBlockState>();
		allowedBlocks.add(Blocks.DIRT.getDefaultState());
		allowedBlocks.add(Blocks.GRASS.getDefaultState());
	}
	
	@Override
	public boolean doGenerate(World world, Random rand, BlockPos pos)
	{
		if (!allowedBlocks.contains(world.getBlockState(pos).getBlock().getDefaultState()))
			return false;
		
		if (!(rand.nextInt(rarity) == 0))
			return false;
		
		this.setBlockInWorld(world, pos.up(), plant);
		
		return true;
	}
}
