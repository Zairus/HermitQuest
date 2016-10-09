package zairus.hermitquest.world.gen.feature;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import zairus.hermitquest.block.HQBlocks;

public class WorldGenIskalliumOre implements IWorldGenerator
{
	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		if (rand.nextInt(3) == 0 && world.provider.getDimensionType() == DimensionType.OVERWORLD)
		{
			int x = (chunkX * 16) + rand.nextInt(16);
			int y = rand.nextInt(12);
			int z = (chunkZ * 16) + rand.nextInt(16);
			
			BlockPos pos = new BlockPos(x, y, z);
			Chunk chunk = world.getChunkFromBlockCoords(pos);
			
			if (!world.getWorldInfo().getTerrainType().handleSlimeSpawnReduction(rand, world) && chunk.getRandomWithSeed(987234911L).nextInt(10) == 0)
				(new WorldGenMinable(HQBlocks.ISKALLIUM_ORE.getDefaultState(), 1 + rand.nextInt(6))).generate(world, rand, pos);
		}
	}
}
