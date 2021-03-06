package zairus.hermitquest.biome.decorate;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenVillage;
import zairus.hermitquest.block.HQBlocks;
import zairus.hermitquest.storage.loot.HQLootTableList;
import zairus.hermitquest.tileentity.TileEntityBuildersChest;

public class WorldGenBuildersChest extends WorldGenDecorationBase
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
		
		BlockPos furnace = this.findBlockInArea(world, pos, 20, 20, Blocks.FURNACE.getDefaultState(), true);
		
		if (furnace == null)
			return false;
		
		pos = furnace.down();
		
		if (world.getBlockState(pos).getBlock() == Blocks.FURNACE)
			pos = pos.down();
		
		pos = this.findBlockInArea(world, pos, 2, 0, Blocks.AIR.getDefaultState(), true);
		
		if (pos == null)
			return false;
		
		if (!world.isAirBlock(pos) && world.isAirBlock(pos.up()))
			pos = pos.up();
		
		furnace = this.findBlockInArea(world, pos, 5, 1, HQBlocks.BUILDERS_CHEST.getDefaultState(), true);
		
		if (furnace != null)
			return false;
		
		this.setBlockInWorld(world, pos, HQBlocks.BUILDERS_CHEST.getDefaultState());
		
		TileEntity te = world.getTileEntity(pos);
		
		if (te instanceof TileEntityBuildersChest)
		{
			((TileEntityBuildersChest)te).setLootTable(HQLootTableList.CHEST_BUILDERS, rand.nextLong());
		}
		
		return true;
	}
}
