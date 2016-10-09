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

public class WorldGenAt extends WorldGenDecorationBase
{
	public WorldGenAt()
	{
		super(GenerationType.ANYWHERE);
	}
	
	@Override
	public List<Biome>getAllowedBiomes()
	{
		List<Biome> biomes = new ArrayList<Biome>();
		
		biomes.add(Biomes.SKY);
		
		return biomes;
	}
	
	@Override
	protected boolean doGenerate(World world, Random rand, BlockPos pos)
	{
		if (!(rand.nextInt(rarity) == 0))
			return false;
		
		BlockPos targetPos = this.findBlockInArea(world, pos, 15, 15, Blocks.PURPUR_BLOCK.getDefaultState(), false);
		
		if (targetPos == null)
			return false;
		
		this.setBlockInWorld(world, targetPos, HQBlocks.AT_BLOCK.getDefaultState());
		
		return true;
	}
}
