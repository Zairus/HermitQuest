package zairus.hermitquest.biome.decorate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import zairus.hermitquest.block.BlockHealthPack;
import zairus.hermitquest.block.HQBlocks;
import zairus.hermitquest.storage.loot.HQLootTableList;
import zairus.hermitquest.tileentity.TileEntityHealthPack;

public class WorldGenStimpack extends WorldGenDecorationBase
{
	public WorldGenStimpack()
	{
		super(GenerationType.ANYWHERE);
	}
	
	@Override
	public List<Biome> getAllowedBiomes()
	{
		List<Biome> biomes = new ArrayList<Biome>();
		
		biomes.add(Biomes.HELL);
		
		return biomes;
	}
	
	@Override
	protected boolean doGenerate(World world, Random rand, BlockPos pos)
	{
		if (!(rand.nextInt(rarity) == 0))
			return false;
		
		BlockPos targetPos = findBlockInArea(world, pos, 11, 11, Blocks.NETHER_BRICK.getDefaultState(), false);
		
		if (targetPos == null)
			return false;
		
		targetPos = targetPos.up();
		
		if (!world.isAirBlock(targetPos))
			return false;
		
		IBlockState stimPack = HQBlocks.HEALTH_PACK.getDefaultState();
		this.setBlockInWorld(world, targetPos, stimPack.withProperty(BlockHealthPack.FACING, EnumFacing.HORIZONTALS[rand.nextInt(EnumFacing.HORIZONTALS.length)]));
		TileEntity te = world.getTileEntity(targetPos);
		
		if (te instanceof TileEntityHealthPack)
		{
			((TileEntityHealthPack)te).setLootTable(HQLootTableList.CHEST_STIMPACK, rand.nextLong());
		}
		
		return true;
	}
}
