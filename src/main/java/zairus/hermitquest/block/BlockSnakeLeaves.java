package zairus.hermitquest.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.hermitquest.HermitQuest;
import zairus.hermitquest.sound.HQSoundEvents;

public class BlockSnakeLeaves extends Block implements IShearable
{
	protected boolean leavesFancy = true;
	
	public BlockSnakeLeaves()
	{
		super(Material.LEAVES);
		this.setCreativeTab(HermitQuest.hqTab);
		this.setHardness(0.2F);
		this.setLightOpacity(1);
		this.setSoundType(SoundType.PLANT);
	}
	
	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, @Nullable ItemStack stack)
	{
		if (!world.isRemote && stack != null && stack.getItem() == Items.SHEARS)
		{
			player.addStat(StatList.getBlockStats(this));
		}
		else
		{
			super.harvestBlock(world, player, pos, state, te, stack);
		}
	}
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		return new ArrayList<ItemStack>();
	}
	
	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
	{
		return java.util.Arrays.asList(new ItemStack(this, 1, 0));
	}
	
	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos)
	{
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
	{
		if (world.isRainingAt(pos.up()) && !world.getBlockState(pos.down()).isSideSolid(world, pos, EnumFacing.UP) && rand.nextInt(15) == 1)
		{
			double d0 = (double)((float)pos.getX() + rand.nextFloat());
			double d1 = (double)pos.getY() - 0.05D;
			double d2 = (double)((float)pos.getZ() + rand.nextFloat());
			world.spawnParticle(EnumParticleTypes.DRIP_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
		}
		
		if (rand.nextInt(180) == 0)
		{
			world.playSound(
					pos.getX(), 
					pos.getY(), 
					pos.getZ(), 
					HQSoundEvents.LEAVES_HISS, 
					SoundCategory.BLOCKS, 
					1.0F, 
					(1.0F / (rand.nextFloat() * 0.4F + 1.2F) + 0.5F) * 0.7F, 
					true);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void setGraphicsLevel(boolean fancy)
	{
		this.leavesFancy = fancy;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return !this.leavesFancy;
	}
	
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return this.leavesFancy ? BlockRenderLayer.CUTOUT_MIPPED : BlockRenderLayer.SOLID;
	}
	
	public boolean isVisuallyOpaque()
	{
		return false;
	}
	
	@Override
	public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return true;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		return !this.leavesFancy && blockAccess.getBlockState(pos.offset(side)).getBlock() == this ? false : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}
}
