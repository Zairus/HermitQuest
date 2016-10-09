package zairus.hermitquest.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.hermitquest.HermitQuest;

public class BlockZombieFlesh extends Block
{
	public BlockZombieFlesh()
	{
		super(Material.CLAY);
		this.setCreativeTab(HermitQuest.hqTab);
		this.setHardness(1.5F);
		this.setHarvestLevel("shovel", 0);
		this.setTickRandomly(true);
		
		this.setSoundType(new SoundType(
				1.0F, 
				1.0F, 
				SoundEvents.ENTITY_ZOMBIE_DEATH, //Break
				SoundEvents.BLOCK_SLIME_STEP, //Step
				SoundEvents.ENTITY_ZOMBIE_AMBIENT, //Place
				SoundEvents.ENTITY_ZOMBIE_HURT, //Hit
				SoundEvents.ENTITY_ZOMBIE_HURT //Fall
				));
	}
	
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random rand)
	{
		if (rand.nextInt(180) == 0)
		{
			world.playSound(
					pos.getX(), 
					pos.getY(), 
					pos.getZ(), 
					SoundEvents.ENTITY_ZOMBIE_AMBIENT, 
					SoundCategory.BLOCKS, 
					1.0F, 
					1.0F / (rand.nextFloat() * 0.4F + 1.2F) + 0.5F, 
					true);
		}
	}
}
