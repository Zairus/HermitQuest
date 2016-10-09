package zairus.hermitquest.entity;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.Biomes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import zairus.hermitquest.block.HQBlocks;
import zairus.hermitquest.entity.boss.EntityHermitBoss;
import zairus.hermitquest.storage.loot.HQLootTableList;

public class EntityJevinSlime extends EntitySlime
{
	private EntityHermitBoss boss;
	
	public EntityJevinSlime(World world)
	{
		super(world);
		this.setSlimeSize(3);
	}
	
	public EntityJevinSlime(World world, EntityHermitBoss owner)
	{
		this(world);
		this.boss = owner;
	}
	
	public void setSize(int size)
	{
		this.setSlimeSize(size);
	}
	
	@Override
	public void onDeath(DamageSource cause)
	{
		super.onDeath(cause);
		
		if (boss != null)
			boss.decreaseMinion();
	}
	
	@Override
	public void setDead()
	{
		this.isDead = true;
	}
	
	@Override
	protected Item getDropItem()
	{
		return new ItemStack(HQBlocks.JEVIN_SLIME).getItem();
	}
	
	@Override
	@Nullable
	protected ResourceLocation getLootTable()
	{
		return HQLootTableList.ENTITIES_JEVIN_SLIME;
	}
	
	@Override
	public boolean getCanSpawnHere()
	{
		BlockPos blockpos = new BlockPos(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ));
		Chunk chunk = this.worldObj.getChunkFromBlockCoords(blockpos);
		
		if (this.worldObj.getWorldInfo().getTerrainType().handleSlimeSpawnReduction(rand, worldObj))
		{
			return false;
		}
		else
		{
			if (this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL)
			{
				Biome biome = this.worldObj.getBiomeGenForCoords(blockpos);
				
				if (biome == Biomes.SWAMPLAND && this.posY > 50.0D && this.posY < 70.0D && this.rand.nextFloat() < 0.5F && this.rand.nextFloat() < this.worldObj.getCurrentMoonPhaseFactor() && this.worldObj.getLightFromNeighbors(new BlockPos(this)) <= this.rand.nextInt(8))
				{
					return super.getCanSpawnHere();
				}
				
				if (this.rand.nextInt(10) == 0 && chunk.getRandomWithSeed(987234911L).nextInt(10) == 0 && this.posY < 40.0D)
				{
					boolean canSpawn;
					
					IBlockState iblockstate = this.worldObj.getBlockState((new BlockPos(this)).down());
					canSpawn = iblockstate.func_189884_a(this);
					
					return canSpawn;
				}
			}
			
			return false;
		}
	}
}
