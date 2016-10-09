package zairus.hermitquest.tileentity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import zairus.hermitquest.block.HQBlocks;
import zairus.hermitquest.entity.boss.EntityHermitBoss;
import zairus.hermitquest.hermits.Hermits;

public class TileEntityHermitSummoner extends TileEntity implements ITickable
{
	public TileEntityHermitSummoner()
	{
		;
	}
	
	@Override
	public void update()
	{
		AxisAlignedBB bb = new AxisAlignedBB(
				this.getPos().getX() + 1, 
				this.getPos().getY() + 2, 
				this.getPos().getZ() + 1, 
				this.getPos().getX(), 
				this.getPos().getY(), 
				this.getPos().getZ());
		
		List<Entity> entities = this.worldObj.getEntitiesWithinAABB(Entity.class, bb);
		
		for (Entity entity : entities)
		{
			if (entity instanceof EntityItem)
			{
				EntityItem itemEntity = (EntityItem)entity;
				
				if (itemEntity.getEntityItem().getItem() == Item.getItemFromBlock(HQBlocks.ISKALLIUM_ORE))
				{
					processBossSpawn(itemEntity, Hermits.ISKALL85);
				}
				else if (itemEntity.getEntityItem().getItem() == Item.getItemFromBlock(HQBlocks.HEALTH_PACK))
				{
					processBossSpawn(itemEntity, Hermits.XISUMA);
				}
				else if (itemEntity.getEntityItem().getItem() == Item.getItemFromBlock(HQBlocks.GOLDEN_LOG))
				{
					processBossSpawn(itemEntity, Hermits.RENDOG);
				}
				else if (itemEntity.getEntityItem().getItem() == Item.getItemFromBlock(HQBlocks.BUILDERS_CHEST))
				{
					processBossSpawn(itemEntity, Hermits.FALSESYMMETRY);
				}
				else if (itemEntity.getEntityItem().getItem() == Item.getItemFromBlock(HQBlocks.PLANT_TEA))
				{
					processBossSpawn(itemEntity, Hermits.BIFFA2001);
				}
				else if (itemEntity.getEntityItem().getItem() == Item.getItemFromBlock(HQBlocks.JEVIN_SLIME))
				{
					processBossSpawn(itemEntity, Hermits.IJEVIN);
				}
				else if (itemEntity.getEntityItem().getItem() == Item.getItemFromBlock(HQBlocks.ZOMBIE_FLESH_BLOCK))
				{
					processBossSpawn(itemEntity, Hermits.ZOMBIECLEO);
				}
				else if (itemEntity.getEntityItem().getItem() == Item.getItemFromBlock(HQBlocks.CROWN_IN_STONE))
				{
					processBossSpawn(itemEntity, Hermits.KINGDADDYDMAC);
				}
				else if (itemEntity.getEntityItem().getItem() == Item.getItemFromBlock(HQBlocks.LEAVES_SNAKE))
				{
					processBossSpawn(itemEntity, Hermits.PYTHONGB);
				}
				else if (itemEntity.getEntityItem().getItem() == Item.getItemFromBlock(HQBlocks.AT_BLOCK))
				{
					processBossSpawn(itemEntity, Hermits.JOEHILLS);
				}
				else if (itemEntity.getEntityItem().getItem() == Item.getItemFromBlock(HQBlocks.CASTLE_DIORITE))
				{
					processBossSpawn(itemEntity, Hermits.ZUELJIN);
				}
				else if (itemEntity.getEntityItem().getItem() == Item.getItemFromBlock(HQBlocks.CAT_LITER))
				{
					processBossSpawn(itemEntity, Hermits.GOODTIMESWITHSCAR);
				}
				else if (itemEntity.getEntityItem().getItem() == Item.getItemFromBlock(HQBlocks.COSMIC_DUST))
				{
					processBossSpawn(itemEntity, Hermits.CUBFAN135);
				}
				else if (itemEntity.getEntityItem().getItem() == Item.getItemFromBlock(HQBlocks.CRAFTED_BENCH))
				{
					processBossSpawn(itemEntity, Hermits.XBCRAFTED);
				}
				else if (itemEntity.getEntityItem().getItem() == Item.getItemFromBlock(HQBlocks.COFFEE_BEANS))
				{
					processBossSpawn(itemEntity, Hermits.HYPNO);
				}
				else if (itemEntity.getEntityItem().getItem() == Item.getItemFromBlock(HQBlocks.OCEAN_GLASS))
				{
					processBossSpawn(itemEntity, Hermits.JESSASSIN);
				}
				else if (itemEntity.getEntityItem().getItem() == Item.getItemFromBlock(HQBlocks.HELMET_IN_STONE))
				{
					processBossSpawn(itemEntity, Hermits.WELSKNIGHT);
				}
			}
		}
	}
	
	private void processBossSpawn(EntityItem offer, Hermits boss)
	{
		offer.setDead();
		summonHermit(boss);
	}
	
	private void summonHermit(Hermits hermit)
	{
		EntityHermitBoss boss = new EntityHermitBoss(this.worldObj, hermit);
		boss.setPosition(this.getPos().getX(), this.getPos().getY() + 1, this.getPos().getZ());
		
		this.worldObj.playSound(
				(EntityPlayer)null, 
				this.getPos().getX(), 
				this.getPos().getY(), 
				this.getPos().getZ(), 
				SoundEvents.ENTITY_GENERIC_EXPLODE, 
				SoundCategory.AMBIENT, 
				1.5F, 
				this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
		
		for (int i = 0; i < 5; ++i)
			this.worldObj.spawnParticle(
					EnumParticleTypes.EXPLOSION_HUGE, 
					this.getPos().getX(), 
					this.getPos().getY() + 1, 
					this.getPos().getZ(), 
					0.0D, 
					0.0D, 
					0.0D, 
					new int[]{});
		
		boss.setHermit(hermit);
		
		if (!this.worldObj.isRemote)
			this.worldObj.spawnEntityInWorld(boss);
	}
}
