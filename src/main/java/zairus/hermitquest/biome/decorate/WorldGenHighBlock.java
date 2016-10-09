package zairus.hermitquest.biome.decorate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class WorldGenHighBlock extends WorldGenDecorationBase
{
	private IBlockState state;
	private boolean stack = false;
	
	@Override
	public List<Biome> getAllowedBiomes()
	{
		List<Biome> biomes = new ArrayList<Biome>();
		
		biomes.add(Biomes.EXTREME_HILLS);
		biomes.add(Biomes.EXTREME_HILLS_EDGE);
		biomes.add(Biomes.EXTREME_HILLS_WITH_TREES);
		
		return biomes;
	}
	
	public WorldGenHighBlock setStacked(boolean stacked)
	{
		this.stack = stacked;
		return this;
	}
	
	public WorldGenHighBlock setBlockState(IBlockState s)
	{
		this.state = s;
		return this;
	}
	
	@Override
	protected boolean doGenerate(World world, Random rand, BlockPos pos)
	{
		if (state == null)
			return false;
		
		if (!(rand.nextInt(rarity) == 0))
			return false;
		
		if (pos.getY() < 96)
			return false;
		
		if (!world.isAirBlock(pos.up()))
			return false;
		
		this.setBlockInWorld(world, pos, state);
		
		if(stack)
		{
			this.setBlockInWorld(world, pos.up(), state);
			
			if (world.isAirBlock(pos.up(2)))
				this.setBlockInWorld(world, pos.up(2), state);
		}
		
		return true;
	}
}
