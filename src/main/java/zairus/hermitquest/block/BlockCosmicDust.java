package zairus.hermitquest.block;

import java.util.Random;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.hermitquest.HermitQuest;

public class BlockCosmicDust extends BlockFalling
{
	public BlockCosmicDust()
	{
		super(Material.SAND);
		this.setCreativeTab(HermitQuest.hqTab);
		this.setHardness(1.0F);
		this.setHarvestLevel("shovel", 0);
		this.setSoundType(SoundType.CLOTH);
	}
	
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
	{
		super.randomDisplayTick(state, world, pos, rand);
		
		int partFrec = 24;
		
		if (rand.nextInt(partFrec) == 0)
			world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, (double)((float)pos.getX() + 1.0F), (double)((float)pos.getY() + rand.nextFloat()), (double)((float)pos.getZ() + rand.nextFloat()), rand.nextGaussian() * 0.05D, rand.nextGaussian() * 0.05D, rand.nextGaussian() * 0.05D, new int[0]);
		if (rand.nextInt(partFrec) == 0)
			world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, (double)((float)pos.getX()), (double)((float)pos.getY() + rand.nextFloat()), (double)((float)pos.getZ() + rand.nextFloat()), rand.nextGaussian() * 0.05D, rand.nextGaussian() * 0.05D, rand.nextGaussian() * 0.05D, new int[0]);
		if (rand.nextInt(partFrec) == 0)
			world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, (double)((float)pos.getX() + rand.nextFloat()), (double)((float)pos.getY() + rand.nextFloat()), (double)((float)pos.getZ() + 1.0F), rand.nextGaussian() * 0.05D, rand.nextGaussian() * 0.05D, rand.nextGaussian() * 0.05D, new int[0]);
		if (rand.nextInt(partFrec) == 0)
			world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, (double)((float)pos.getX() + rand.nextFloat()), (double)((float)pos.getY() + rand.nextFloat()), (double)((float)pos.getZ()), rand.nextGaussian() * 0.05D, rand.nextGaussian() * 0.05D, rand.nextGaussian() * 0.05D, new int[0]);
		if (rand.nextInt(partFrec) == 0)
			world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, (double)((float)pos.getX() + rand.nextFloat()), (double)((float)pos.getY() + 1.0F), (double)((float)pos.getZ() + rand.nextFloat()), rand.nextGaussian() * 0.05D, rand.nextGaussian() * 0.05D, rand.nextGaussian() * 0.05D, new int[0]);
		if (rand.nextInt(partFrec) == 0)
			world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, (double)((float)pos.getX() + rand.nextFloat()), (double)((float)pos.getY()), (double)((float)pos.getZ() + rand.nextFloat()), rand.nextGaussian() * 0.05D, rand.nextGaussian() * 0.05D, rand.nextGaussian() * 0.05D, new int[0]);
	}
	
	@Override
	public boolean canProvidePower(IBlockState state)
	{
		return true;
	}
	
	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		return 15;
	}
}
