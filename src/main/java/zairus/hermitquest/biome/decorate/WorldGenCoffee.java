package zairus.hermitquest.biome.decorate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import zairus.hermitquest.block.BlockCoffeeBeans;
import zairus.hermitquest.block.HQBlocks;

public class WorldGenCoffee extends WorldGenDecorationBase
{
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
				Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE), 
				true);
		
		if (logPos == null)
			return false;
		
		BlockPos beanPos = null;
		EnumFacing beanFacing = null;
		
		for (EnumFacing facing : EnumFacing.HORIZONTALS)
		{
			if (world.isAirBlock(logPos.offset(facing)))
			{
				beanPos = logPos.offset(facing);
				beanFacing = facing;
				break;
			}
		}
		
		if (beanPos == null || beanFacing == null)
			return false;
		
		this.setBlockInWorld(world, beanPos, HQBlocks.COFFEE_BEANS.getDefaultState().withProperty(BlockCoffeeBeans.FACING, beanFacing.getOpposite()));
		
		return true;
	}
}
