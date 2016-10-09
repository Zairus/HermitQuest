package zairus.hermitquest.entity.projectile;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityThrowableTorch extends EntityThrowable
{
	public EntityThrowableTorch(World world)
	{
		super(world);
	}
	
	public EntityThrowableTorch(World world, EntityLivingBase thrower)
	{
		super(world, thrower);
	}
	
	public EntityThrowableTorch(World world, double x, double y, double z)
	{
		super(world, x, y, z);
	}
	
	public static void func_189662_a(DataFixer fixer)
	{
		EntityThrowable.func_189661_a(fixer, "ThrowableTorch");
	}
	
	@Override
	protected void onImpact(RayTraceResult result)
	{
		for (int j = 0; j < 8; ++j)
			this.worldObj.spawnParticle(
					EnumParticleTypes.FLAME, 
					this.posX, 
					this.posY, 
					this.posZ, 
					0.03D * (1 - this.rand.nextInt(3)), 
					0.06D, 
					0.03D * (1 - this.rand.nextInt(3)), 
					new int[0]);
		
		boolean blockFlag = false;
		boolean dropItem = false;
		BlockPos pos = this.getPosition();
		IBlockState torch = Blocks.TORCH.getDefaultState();
		EnumFacing facing = EnumFacing.UP;
		
		if (result.entityHit != null)
		{
			int i = 1;
			result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float)i);
			result.entityHit.setFire(3);
			dropItem = false;
		}
		else if (result.typeOfHit == RayTraceResult.Type.BLOCK && !this.worldObj.isAirBlock(result.getBlockPos()))
		{
			pos = result.getBlockPos();
			
			if (this.worldObj.isAirBlock(pos.up()))
			{
				facing = EnumFacing.UP;
				pos = pos.up();
				blockFlag = true;
				dropItem = false;
			}
			else
			{
				for (EnumFacing side : EnumFacing.HORIZONTALS)
				{
					if (this.worldObj.isAirBlock(pos.offset(side)))
					{
						facing = side;
						pos = pos.offset(side);
						blockFlag = true;
						dropItem = false;
						break;
					}
				}
			}
		}
		
		if (blockFlag)
			this.worldObj.setBlockState(pos, torch.withProperty(BlockTorch.FACING, facing));
		
		if (dropItem)
		{
			ItemStack torchStack = new ItemStack(Blocks.TORCH);
			EntityItem itemEntity = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, torchStack);
			if (!this.worldObj.isRemote)
				this.worldObj.spawnEntityInWorld(itemEntity);
		}
		
		if (!this.worldObj.isRemote)
			this.setDead();
	}
}
