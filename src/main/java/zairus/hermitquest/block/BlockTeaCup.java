package zairus.hermitquest.block;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import zairus.hermitquest.item.HQItems;

public class BlockTeaCup extends Block
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	public BlockTeaCup()
	{
		super(Material.ROCK);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.setHardness(0.5F);
		this.setSoundType(SoundType.STONE);
		this.setHarvestLevel("hand", 0);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return new AxisAlignedBB(0.2F, 0.0F, 0.2F, 0.8F, 0.62F, 0.8F);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state)
	{
		return false;
	}
	
	@Override
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		EnumFacing enumfacing = EnumFacing.getHorizontal(MathHelper.floor_double((double)(placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3).getOpposite();
		state = state.withProperty(FACING, enumfacing);
		world.setBlockState(pos, state, 3);
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {FACING});
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing enumfacing = EnumFacing.getFront(meta);
		
		if (enumfacing.getAxis() == EnumFacing.Axis.Y)
		{
			enumfacing = EnumFacing.NORTH;
		}
		
		return this.getDefaultState().withProperty(FACING, enumfacing);
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}
	
	@Nullable
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return this.getItemFromState(state);
	}
	
	@Override
	public ItemStack getItem(World world, BlockPos pos, IBlockState state)
	{
		ItemStack itemstack = new ItemStack(this.getItemFromState(state));
		return itemstack;
	}
	
	private Item getItemFromState(IBlockState state)
	{
		Item drop;
		
		if (state.getBlock() == HQBlocks.TEA_CUP)
			drop = HQItems.CUP_TEA;
		else
			drop = HQItems.CUP_COFFEE;
		
		return drop;
	}
	
	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, @Nullable ItemStack stack)
	{
		ItemStack itemstack = new ItemStack(this.getItemFromState(state));
		spawnAsEntity(world, pos, itemstack);
	}
}
