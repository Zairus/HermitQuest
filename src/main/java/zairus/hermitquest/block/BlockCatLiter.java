package zairus.hermitquest.block;

import java.util.Random;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import zairus.hermitquest.HermitQuest;

public class BlockCatLiter extends BlockFalling
{
	public BlockCatLiter()
	{
		super();
		this.setCreativeTab(HermitQuest.hqTab);
		this.setHardness(0.5F);
		this.setHarvestLevel("shovel", 0);
		this.setSoundType(SoundType.GROUND);
		this.setTickRandomly(true);
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if (rand.nextInt(6) == 0)
		{
			BlockPos targetPos = pos.add(2 - rand.nextInt(5), 2 - rand.nextInt(5), 2 - rand.nextInt(5));
			
			if (world.isAirBlock(targetPos) && !world.isAirBlock(targetPos.down()))
			{
				EntityOcelot ocelot = new EntityOcelot(world);
				ocelot.setTameSkin(1 + rand.nextInt(3));
				ocelot.setPosition((double)targetPos.getX() + 0.5D, (double)targetPos.getY(), (double)targetPos.getZ() + 0.5D);
				
				if (!world.isRemote)
					world.spawnEntityInWorld(ocelot);
			}
		}
		
		super.updateTick(world, pos, state, rand);
	}
}
