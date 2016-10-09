package zairus.hermitquest.block;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.hermitquest.item.HQItems;

public class BlockCosmicDustWire extends BlockRedstoneWire
{
	public BlockCosmicDustWire()
	{
		;
	}
	
	@SideOnly(Side.CLIENT)
	public static int colorMultiplier(int mult)
	{
		float f = (float)mult / 15.0F;
		float f1 = f * 0.6F + 0.4F;
		
		if (mult == 0)
		{
			f1 = 0.3F;
		}
		
		float f2 = f * f * 0.7F - 0.5F;
		float f3 = f * f * 0.6F - 0.7F;
		
		if (f2 < 0.0F)
		{
			f2 = 0.0F;
		}
		
		if (f3 < 0.0F)
		{
			f3 = 0.0F;
		}
		
		int i = MathHelper.clamp_int((int)(f1 * 255.0F), 0, 255);
		int j = MathHelper.clamp_int((int)(f2 * 255.0F), 0, 255);
		int k = MathHelper.clamp_int((int)(f3 * 255.0F), 0, 255);
		return -16777216 | i << 16 | j << 8 | k;
	}
	
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
	{
		int i = ((Integer)state.getValue(POWER)).intValue();
		
		if (i != 0)
		{
			double d0 = (double)pos.getX() + 0.5D + ((double)rand.nextFloat() - 0.5D) * 0.2D;
			double d1 = (double)((float)pos.getY() + 0.0625F);
			double d2 = (double)pos.getZ() + 0.5D + ((double)rand.nextFloat() - 0.5D) * 0.2D;
			float f = (float)i / 15.0F;
			float f1 = (f * 0.03F + 0.002F) * (float)(1 - rand.nextInt(3));
			float f2 = 0.1F;
			float f3 = (f * 0.03F + 0.002F) * (float)(1 - rand.nextInt(3));
			world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, d0, d1, d2, (double)f1, (double)f2, (double)f3, new int[0]);
		}
	}
	
	@Override
	public ItemStack getItem(World world, BlockPos pos, IBlockState state)
	{
		return new ItemStack(HQItems.COSMICDUST);
	}
	
	@Override
	@Nullable
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return HQItems.COSMICDUST;
	}
	
	protected static boolean canConnectUpwardsTo(IBlockAccess worldIn, BlockPos pos)
	{
		return canConnectTo(worldIn.getBlockState(pos), null, worldIn, pos);
	}
	
	protected static boolean canConnectTo(IBlockState blockState, @Nullable EnumFacing side, IBlockAccess world, BlockPos pos )
	{
		Block block = blockState.getBlock();
		
		if (block == HQBlocks.COSMIC_DUST_WIRE)
		{
			return true;
		}
		else if (Blocks.UNPOWERED_REPEATER.isSameDiode(blockState))
		{
			EnumFacing enumfacing = (EnumFacing)blockState.getValue(BlockRedstoneRepeater.FACING);
			return enumfacing == side || enumfacing.getOpposite() == side;
		}
		else
		{
			return blockState.getBlock().canConnectRedstone(blockState, world, pos, side);
		}
	}
	
	static enum EnumAttachPosition implements IStringSerializable
    {
        UP("up"),
        SIDE("side"),
        NONE("none");

        private final String name;

        private EnumAttachPosition(String name)
        {
            this.name = name;
        }

        public String toString()
        {
            return this.getName();
        }

        public String getName()
        {
            return this.name;
        }
    }
}
