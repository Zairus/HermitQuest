package zairus.hermitquest.block;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.hermitquest.client.renderer.ISpecialRendered;
import zairus.hermitquest.client.renderer.tileentity.TileEntityHermitBannerRenderer;
import zairus.hermitquest.item.HQItems;
import zairus.hermitquest.tileentity.TileEntityHermitBanner;

@SuppressWarnings("deprecation")
public class BlockHermitBanner extends BlockContainer implements ISpecialRendered
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyInteger ROTATION = PropertyInteger.create("rotation", 0, 15);
	protected static final AxisAlignedBB STANDING_AABB = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
	
	public BlockHermitBanner()
	{
		super(Material.WOOD);
		this.setHardness(1.0F);
		this.setSoundType(SoundType.WOOD);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityHermitBanner(meta);
	}
	
	@Override
	public String getLocalizedName()
	{
		return I18n.translateToLocal("item.banner.Xisuma.name");
	}
	
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
	{
		return NULL_AABB;
	}
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
	{
		return true;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean canSpawnInBlock()
	{
		return false;
	}
	
	@Nullable
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return HQItems.HERMIT_BANNER;
	}
	
	@Nullable
	private ItemStack getTileDataItemStack(World world, BlockPos pos, IBlockState state)
	{
		TileEntity tileEntity = world.getTileEntity(pos);
		ItemStack bannerStack = null;
		
		if (tileEntity instanceof TileEntityHermitBanner)
		{
			TileEntityHermitBanner banner = (TileEntityHermitBanner)tileEntity;
			bannerStack = banner.getBannerStack();
		}
		
		return bannerStack;
	}
	
	@Override
	public ItemStack getItem(World world, BlockPos pos, IBlockState state)
	{
		ItemStack itemstack = this.getTileDataItemStack(world, pos, state);
		return itemstack != null ? itemstack : new ItemStack(HQItems.HERMIT_BANNER);
	}
	
	@Override
	public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune)
	{
		super.dropBlockAsItemWithChance(world, pos, state, chance, fortune);
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
	{
		return !this.hasInvalidNeighbor(world, pos) && super.canPlaceBlockAt(world, pos);
	}
	
	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, @Nullable ItemStack stack)
	{
		if (te instanceof TileEntityHermitBanner)
		{
			TileEntityHermitBanner banner = (TileEntityHermitBanner)te;
			ItemStack itemstack = banner.getBannerStack();
			spawnAsEntity(world, pos, itemstack);
		}
		else
		{
			super.harvestBlock(world, player, pos, state, (TileEntity)null, stack);
		}
	}
	
	@Override
	public java.util.List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		TileEntity te = world.getTileEntity(pos);
		java.util.List<ItemStack> ret = new java.util.ArrayList<ItemStack>();
		
		if (te instanceof TileEntityHermitBanner)
		{
			TileEntityHermitBanner banner = (TileEntityHermitBanner)te;
			ItemStack item = banner.getBannerStack();
			ret.add(item);
		}
		else
		{
			ret.add(new ItemStack(HQItems.HERMIT_BANNER, 1, 0));
		}
		
		return ret;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	@Nullable
	public Object getTESR()
	{
		return new TileEntityHermitBannerRenderer();
	}
	
	public static class BlockHermitBannerHanging extends BlockHermitBanner
	{
		protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 0.78125D, 1.0D);
		protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.78125D, 0.125D);
		protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 0.78125D, 1.0D);
		protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 0.78125D, 1.0D);
		
		public BlockHermitBannerHanging()
		{
			this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		}
		
		@Override
		public IBlockState withRotation(IBlockState state, Rotation rot)
		{
			return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
		}
		
		@Override
		public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
		{
			return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
		}
		
		@Override
		public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
		{
			switch ((EnumFacing)state.getValue(FACING))
			{
				case NORTH:
				default:
					return NORTH_AABB;
				case SOUTH:
					return SOUTH_AABB;
				case WEST:
					return WEST_AABB;
				case EAST:
					return EAST_AABB;
            }
		}
		
		@Override
		public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block)
		{
			EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
			
			if (!world.getBlockState(pos.offset(enumfacing.getOpposite())).getMaterial().isSolid())
			{
				this.dropBlockAsItem(world, pos, state, 0);
				world.setBlockToAir(pos);
				
				super.neighborChanged(state, world, pos, block);
			}
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
		
		@Override
		protected BlockStateContainer createBlockState()
		{
			return new BlockStateContainer(this, new IProperty[] {FACING});
		}
	}
	
	public static class BlockHermitBannerStanding extends BlockHermitBanner
	{
		public BlockHermitBannerStanding()
		{
			this.setDefaultState(this.blockState.getBaseState().withProperty(ROTATION, Integer.valueOf(0)));
		}
		
		@Override
		public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
		{
			return STANDING_AABB;
		}
		
		@Override
		public IBlockState withRotation(IBlockState state, Rotation rot)
		{
			return state.withProperty(ROTATION, Integer.valueOf(rot.rotate(((Integer)state.getValue(ROTATION)).intValue(), 16)));
		}
		
		@Override
		public IBlockState withMirror(IBlockState state, Mirror mirror)
		{
			return state.withProperty(ROTATION, Integer.valueOf(mirror.mirrorRotation(((Integer)state.getValue(ROTATION)).intValue(), 16)));
		}
		
		@Override
		public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block)
		{
			if (!world.getBlockState(pos.down()).getMaterial().isSolid())
			{
				this.dropBlockAsItem(world, pos, state, 0);
				world.setBlockToAir(pos);
			}
			
			super.neighborChanged(state, world, pos, block);
		}
		
		@Override
		public IBlockState getStateFromMeta(int meta)
		{
			return this.getDefaultState().withProperty(ROTATION, Integer.valueOf(meta));
		}
		
		@Override
		public int getMetaFromState(IBlockState state)
		{
			return ((Integer)state.getValue(ROTATION)).intValue();
		}
		
		@Override
		protected BlockStateContainer createBlockState()
		{
			return new BlockStateContainer(this, new IProperty[] {ROTATION});
		}
	}
}
