package zairus.hermitquest.block;

import java.util.Random;

import net.minecraft.block.BlockBreakable;
import net.minecraft.block.BlockStone;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.hermitquest.HermitQuest;
import zairus.hermitquest.sound.HQSoundEvents;

public class BlockIskalliumOre extends BlockBreakable
{
	private boolean isActive = false;
	
	public BlockIskalliumOre()
	{
		super(Material.CLAY, false, MapColor.GRASS);
		this.setCreativeTab(HermitQuest.hqTab);
		this.setLightLevel(0.75F);
		this.slipperiness = 0.98F;
		this.setSoundType(SoundType.STONE);
		this.setTickRandomly(true);
		this.setHardness(0.5F);
	}
	
	public BlockIskalliumOre setActive()
	{
		this.isActive = true;
		return this;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}
	
	@Override
	public void randomTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		super.randomTick(world, pos, state, rand);
		
		BlockPos targetPos = pos.add(2 - rand.nextInt(5), 2 - rand.nextInt(5), 2 - rand.nextInt(5));
		
		if (world.isRemote)
			world.playSound(
					targetPos.getX(), 
					targetPos.getY(), 
					targetPos.getZ(), 
					HQSoundEvents.ROCK_CRACK, 
					SoundCategory.BLOCKS, 
					1.0F, 
					1.0F / (rand.nextFloat() * 0.4F + 1.2F) + 0.5F, 
					true);
		
		if (this.isActive)
		{
			if (world.getBlockState(targetPos).getBlock() == Blocks.COBBLESTONE)
				world.setBlockState(targetPos, Blocks.GRAVEL.getDefaultState());
			else if (world.getBlockState(targetPos).getBlock() == Blocks.GRAVEL)
				world.setBlockState(targetPos, Blocks.SAND.getDefaultState());
			else if (world.getBlockState(targetPos).getBlock() == HQBlocks.ISKALLIUM_ORE)
				world.setBlockState(targetPos, HQBlocks.ISKALLIUM_PROCESSED.getDefaultState());
			else if (world.getBlockState(targetPos) == Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE))
				world.setBlockToAir(targetPos);
		}
		
		if (world.getBlockState(targetPos).getBlock() == Blocks.STONE)
			world.setBlockState(targetPos, Blocks.COBBLESTONE.getDefaultState());
	}
	
	@Override
	public void onFallenUpon(World world, BlockPos pos, Entity entity, float fallDistance)
	{
		if (entity.isSneaking())
		{
			super.onFallenUpon(world, pos, entity, fallDistance);
		}
		else
		{
			entity.fall(fallDistance, 0.0F);
		}
	}
	
	@Override
	public void onLanded(World world, Entity entity)
	{
		if (entity.isSneaking())
		{
			super.onLanded(world, entity);
		}
		else if (entity.motionY < 0.0D)
		{
			entity.motionY = -entity.motionY;
			
			if (!(entity instanceof EntityLivingBase))
			{
				entity.motionY *= 0.8D;
			}
		}
	}
}
