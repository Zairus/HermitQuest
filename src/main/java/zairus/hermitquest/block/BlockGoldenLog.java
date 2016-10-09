package zairus.hermitquest.block;

import java.util.Random;

import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.hermitquest.HermitQuest;

public class BlockGoldenLog extends BlockLog
{
	public BlockGoldenLog()
	{
		super();
		this.setCreativeTab(HermitQuest.hqTab);
		this.setHardness(3.0F);
		this.setSoundType(SoundType.METAL);
		this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		IBlockState iblockstate = this.getDefaultState();
		
		switch (meta & 12)
		{
			case 0:
				iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
				break;
			case 4:
				iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
				break;
			case 8:
				iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
				break;
			default:
				iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
		}
		
		return iblockstate;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
	{
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
	public int getMetaFromState(IBlockState state)
	{
		switch ((BlockLog.EnumAxis)state.getValue(LOG_AXIS))
		{
			case X: return 0b0100;
			case Y: return 0b0000;
			case Z: return 0b1000;
			default: return 0b1100;
		}
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {LOG_AXIS});
	}
}
