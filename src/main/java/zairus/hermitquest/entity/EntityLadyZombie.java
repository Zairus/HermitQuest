package zairus.hermitquest.entity;

import java.util.Calendar;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import zairus.hermitquest.block.HQBlocks;
import zairus.hermitquest.sound.HQSoundEvents;
import zairus.hermitquest.storage.loot.HQLootTableList;

public class EntityLadyZombie extends EntityZombie
{
	public EntityLadyZombie(World world)
	{
		super(world);
	}
	
	@Override
	public boolean isChild()
	{
		return false;
	}
	
	@Override
	public void setChild(boolean childZombie)
	{
		;
	}
	
	@Override
	public boolean isVillager()
	{
		return false;
	}
	
	@Override
	public void setVillagerType(@Nullable net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession type)
	{
		;
	}
	
	@Override
	protected SoundEvent getAmbientSound()
	{
		return HQSoundEvents.LADY_ZOMBIE_SAY;
	}
	
	@Override
	protected SoundEvent getHurtSound()
	{
		return HQSoundEvents.LADY_ZOMBIE_HURT;
	}
	
	@Override
	protected SoundEvent getDeathSound()
	{
		return HQSoundEvents.LADY_ZOMBIE_DIE;
	}
	
	@Override
	protected void playStepSound(BlockPos pos, Block blockIn)
	{
		SoundEvent soundevent = SoundEvents.ENTITY_ZOMBIE_VILLAGER_STEP;
		this.playSound(soundevent, 0.15F, 1.0F);
	}
	
	@Override
	@Nullable
	protected ResourceLocation getLootTable()
	{
		return HQLootTableList.ENTITIES_LADY_ZOMBIE;
	}
	
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
	{
		livingdata = super.onInitialSpawn(difficulty, livingdata);
		
		float f = difficulty.getClampedAdditionalDifficulty();
		this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * f);
		
		this.setBreakDoorsAItask(this.rand.nextFloat() < f * 0.1F);
		this.setEquipmentBasedOnDifficulty(difficulty);
		this.setEnchantmentBasedOnDifficulty(difficulty);
		
		if (this.getItemStackFromSlot(EntityEquipmentSlot.HEAD) == null)
		{
			Calendar calendar = this.worldObj.getCurrentDate();
			
			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F)
			{
				this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(this.rand.nextFloat() < 0.1F ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
				this.inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0F;
			}
		}
		
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextDouble() * 0.05000000074505806D, 0));
		double d0 = this.rand.nextDouble() * 1.5D * (double)f;
		
		if (d0 > 1.0D)
		{
			this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).applyModifier(new AttributeModifier("Random zombie-spawn bonus", d0, 2));
		}
		
		if (this.rand.nextFloat() < f * 0.05F)
		{
			this.getEntityAttribute(SPAWN_REINFORCEMENTS_CHANCE).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 0.25D + 0.5D, 0));
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 3.0D + 1.0D, 2));
			this.setBreakDoorsAItask(true);
		}
		
		if (this.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND) == null)
			this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(HQBlocks.ZOMBIE_FLESH_BLOCK));
		
		if (this.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND) == null)
			this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Blocks.RED_FLOWER));
		
		return livingdata;
	}
}
