package zairus.hermitquest.block;

import javax.annotation.Nullable;

import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.hermitquest.HermitQuest;
import zairus.hermitquest.client.renderer.ISpecialRendered;
import zairus.hermitquest.client.renderer.tileentity.TileEntityHealthPackRenderer;
import zairus.hermitquest.tileentity.TileEntityHealthPack;

public class BlockHealthPack extends BlockChest implements ISpecialRendered
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	protected static final AxisAlignedBB NORTH_CHEST_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0D, 0.9375D, 0.875D, 0.9375D);
	protected static final AxisAlignedBB SOUTH_CHEST_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.875D, 1.0D);
	protected static final AxisAlignedBB WEST_CHEST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0625D, 0.9375D, 0.875D, 0.9375D);
	protected static final AxisAlignedBB EAST_CHEST_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 1.0D, 0.875D, 0.9375D);
	protected static final AxisAlignedBB NOT_CONNECTED_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.875D, 0.9375D);
	
	public BlockHealthPack()
	{
		super(BlockChest.Type.BASIC);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.setCreativeTab(HermitQuest.hqTab);
		this.setHardness(2.0F);
		this.setSoundType(SoundType.CLOTH);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	@Nullable
	public Object getTESR()
	{
		return new TileEntityHealthPackRenderer();
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return NOT_CONNECTED_AABB;
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		//this.checkForSurroundingChests(worldIn, pos, state);
		/*
		for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
		{
			BlockPos blockpos = pos.offset(enumfacing);
			IBlockState iblockstate = worldIn.getBlockState(blockpos);
			
			if (iblockstate.getBlock() == this)
			{
				this.checkForSurroundingChests(worldIn, blockpos, iblockstate);
			}
		}*/
	}
	
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		EnumFacing enumfacing = EnumFacing.getHorizontal(MathHelper.floor_double((double)(placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3).getOpposite();
		state = state.withProperty(FACING, enumfacing);
		worldIn.setBlockState(pos, state, 3);
		
		if (stack.hasDisplayName())
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);
			
			if (tileentity instanceof TileEntityHealthPack)
			{
				((TileEntityHealthPack)tileentity).setCustomName(stack.getDisplayName());
			}
		}
	}
	/*
	public IBlockState checkForSurroundingChests(World worldIn, BlockPos pos, IBlockState state)
	{
		if (worldIn.isRemote)
		{
			return state;
		}
		else
		{
			IBlockState iblockstate = worldIn.getBlockState(pos.north());
			IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
			IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
			IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
			EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
			
			if (iblockstate.getBlock() != this && iblockstate1.getBlock() != this)
			{
				boolean flag = iblockstate.isFullBlock();
				boolean flag1 = iblockstate1.isFullBlock();
				
				if (iblockstate2.getBlock() == this || iblockstate3.getBlock() == this)
				{
					BlockPos blockpos1 = iblockstate2.getBlock() == this ? pos.west() : pos.east();
					IBlockState iblockstate7 = worldIn.getBlockState(blockpos1.north());
					IBlockState iblockstate6 = worldIn.getBlockState(blockpos1.south());
					enumfacing = EnumFacing.SOUTH;
					EnumFacing enumfacing2;
					
					if (iblockstate2.getBlock() == this)
					{
						enumfacing2 = (EnumFacing)iblockstate2.getValue(FACING);
					}
					else
					{
						enumfacing2 = (EnumFacing)iblockstate3.getValue(FACING);
					}
					
					if (enumfacing2 == EnumFacing.NORTH)
					{
						enumfacing = EnumFacing.NORTH;
					}
					
					if ((flag || iblockstate7.isFullBlock()) && !flag1 && !iblockstate6.isFullBlock())
					{
						enumfacing = EnumFacing.SOUTH;
					}
					
					if ((flag1 || iblockstate6.isFullBlock()) && !flag && !iblockstate7.isFullBlock())
					{
						enumfacing = EnumFacing.NORTH;
					}
				}
			}
			else
			{
				BlockPos blockpos = iblockstate.getBlock() == this ? pos.north() : pos.south();
				IBlockState iblockstate4 = worldIn.getBlockState(blockpos.west());
				IBlockState iblockstate5 = worldIn.getBlockState(blockpos.east());
				enumfacing = EnumFacing.EAST;
				EnumFacing enumfacing1;
				
				if (iblockstate.getBlock() == this)
				{
					enumfacing1 = (EnumFacing)iblockstate.getValue(FACING);
				}
				else
				{
					enumfacing1 = (EnumFacing)iblockstate1.getValue(FACING);
				}
				
				if (enumfacing1 == EnumFacing.WEST)
				{
					enumfacing = EnumFacing.WEST;
				}
				
				if ((iblockstate2.isFullBlock() || iblockstate4.isFullBlock()) && !iblockstate3.isFullBlock() && !iblockstate5.isFullBlock())
				{
					enumfacing = EnumFacing.EAST;
				}
				
				if ((iblockstate3.isFullBlock() || iblockstate5.isFullBlock()) && !iblockstate2.isFullBlock() && !iblockstate4.isFullBlock())
				{
					enumfacing = EnumFacing.WEST;
				}
			}
			
			state = state.withProperty(FACING, enumfacing);
			worldIn.setBlockState(pos, state, 3);
			return state;
		}
	}
	*/
	
	public IBlockState correctFacing(World worldIn, BlockPos pos, IBlockState state)
	{
		EnumFacing enumfacing = null;
		
		for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL)
		{
			IBlockState iblockstate = worldIn.getBlockState(pos.offset(enumfacing1));
			
			if (iblockstate.getBlock() == this)
			{
				return state;
			}
			
			if (iblockstate.isFullBlock())
			{
				if (enumfacing != null)
				{
					enumfacing = null;
					break;
				}
				
				enumfacing = enumfacing1;
			}
		}
		
		if (enumfacing != null)
		{
			return state.withProperty(FACING, enumfacing.getOpposite());
		}
		else
		{
			EnumFacing enumfacing2 = (EnumFacing)state.getValue(FACING);
			
			if (worldIn.getBlockState(pos.offset(enumfacing2)).isFullBlock())
			{
				enumfacing2 = enumfacing2.getOpposite();
			}
			
			if (worldIn.getBlockState(pos.offset(enumfacing2)).isFullBlock())
			{
				enumfacing2 = enumfacing2.rotateY();
			}
			
			if (worldIn.getBlockState(pos.offset(enumfacing2)).isFullBlock())
			{
				enumfacing2 = enumfacing2.getOpposite();
			}
			
			return state.withProperty(FACING, enumfacing2);
		}
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		return true;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		TileEntity tileentity = worldIn.getTileEntity(pos);
		
		if (tileentity instanceof IInventory)
		{
			InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
			worldIn.updateComparatorOutputLevel(pos, this);
		}
		
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isRemote)
		{
			return true;
		}
		else
		{
			ILockableContainer ilockablecontainer = this.getLockableContainer(worldIn, pos);
			
			if (ilockablecontainer != null)
			{
				playerIn.displayGUIChest(ilockablecontainer);
				
				playerIn.addStat(StatList.CHEST_OPENED);
			}
			
			return true;
		}
	}
	
	@Nullable
	public ILockableContainer getLockableContainer(World worldIn, BlockPos pos)
	{
		return this.getContainer(worldIn, pos, false);
	}
	
	@Nullable
	public ILockableContainer getContainer(World world, BlockPos pos, boolean flag)
	{
		TileEntity tileentity = world.getTileEntity(pos);
		
		if (!(tileentity instanceof TileEntityHealthPack))
		{
			return null;
		}
		else
		{
			ILockableContainer ilockablecontainer = (TileEntityHealthPack)tileentity;
			
			if (this.isBlocked(world, pos))
			{
				return null;
			}
			else
			{
				return ilockablecontainer;
			}
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		TileEntityHealthPack healthPack = new TileEntityHealthPack();
		healthPack.setCustomName("Stimpack");
		return healthPack;
	}
	
	@Override
	public boolean canProvidePower(IBlockState state)
	{
		return true;
	}
	
	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		if (!blockState.canProvidePower())
		{
			return 0;
		}
		else
		{
			int i = 0;
			TileEntity tileentity = blockAccess.getTileEntity(pos);
			
			if (tileentity instanceof TileEntityHealthPack)
			{
				i = ((TileEntityHealthPack)tileentity).numPlayersUsing;
			}
			
			return MathHelper.clamp_int(i, 0, 15);
		}
	}
	
	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		return side == EnumFacing.UP ? blockState.getWeakPower(blockAccess, pos, side) : 0;
	}
	
	private boolean isBlocked(World worldIn, BlockPos pos)
	{
		return this.isBelowSolidBlock(worldIn, pos) || this.isOcelotSittingOnChest(worldIn, pos);
	}
	
	private boolean isBelowSolidBlock(World worldIn, BlockPos pos)
	{
		return worldIn.getBlockState(pos.up()).isSideSolid(worldIn, pos.up(), EnumFacing.DOWN);
	}
	
	private boolean isOcelotSittingOnChest(World worldIn, BlockPos pos)
	{
		for (Entity entity : worldIn.getEntitiesWithinAABB(EntityOcelot.class, new AxisAlignedBB((double)pos.getX(), (double)(pos.getY() + 1), (double)pos.getZ(), (double)(pos.getX() + 1), (double)(pos.getY() + 2), (double)(pos.getZ() + 1))))
		{
			EntityOcelot entityocelot = (EntityOcelot)entity;
			
			if (entityocelot.isSitting())
			{
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean hasComparatorInputOverride(IBlockState state)
	{
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos)
	{
		return Container.calcRedstoneFromInventory(this.getLockableContainer(worldIn, pos));
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
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {FACING});
	}
}
