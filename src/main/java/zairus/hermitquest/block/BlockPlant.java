package zairus.hermitquest.block;

import java.util.Random;

import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import zairus.hermitquest.HermitQuest;
import zairus.hermitquest.item.HQItems;

public class BlockPlant extends BlockBush
{
	public static final PropertyInteger TYPE = PropertyInteger.create("type", 0, 1);
	
	public BlockPlant()
	{
		this.setCreativeTab(HermitQuest.hqTab);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, 0));
		this.setSoundType(SoundType.PLANT);
		this.setTickRandomly(true);
	}
	
	@Override
	public int damageDropped(IBlockState state)
	{
		return 0;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(TYPE, 0);
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {TYPE});
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		BlockPlant.dropLeaves(world, pos);
	}
	
	public static void dropLeaves(World world, BlockPos pos)
	{
		if (world.rand.nextInt(3) == 0)
		{
			int amount = 1 + world.rand.nextInt(3);
			ItemStack leafStack = new ItemStack(HQItems.TEA_LEAF, amount);
			EntityItem itemEntity = new EntityItem(world, pos.getX(), pos.getY() + 1, pos.getZ(), leafStack);
			
			if (!world.isRemote)
				world.spawnEntityInWorld(itemEntity);
		}
	}
}
