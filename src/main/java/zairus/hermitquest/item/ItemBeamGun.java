package zairus.hermitquest.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import zairus.hermitquest.HermitQuest;
import zairus.hermitquest.entity.projectile.EntityIskallBeam;
import zairus.hermitquest.sound.HQSoundEvents;

public class ItemBeamGun extends Item
{
	public ItemBeamGun()
	{
		this.maxStackSize = 1;
		this.setMaxDamage(500);
		this.setCreativeTab(HermitQuest.hqTab);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 72000;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.BOW;
	}
	
	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase entity, int count)
	{
		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entity;
			
			World world = player.worldObj;
			
			world.playSound(player, player.getPosition(), HQSoundEvents.BEAM_SHOT, SoundCategory.MASTER, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 1.5F);
			
			if (!world.isRemote)
			{
				EntityIskallBeam beam = new EntityIskallBeam(
						world, 
						player, 
						player.getLookVec().xCoord, 
						player.getLookVec().yCoord, 
						player.getLookVec().zCoord);
				
				Vec3d p = new Vec3d(
						player.getLookVec().zCoord, 
						player.getLookVec().yCoord, 
						player.getLookVec().xCoord * -1);
				
				beam.setPosition(
						(player.posX + (p.xCoord * 0.06D)),
						(player.posY + player.eyeHeight - 0.08D), 
						(player.posZ + (p.zCoord * 0.06D)));
				
				beam.setPosition(
						beam.posX + beam.motionX, 
						beam.posY + beam.motionY, 
						beam.posZ + beam.motionZ);
				
				beam.damage = 3;
				
				world.spawnEntityInWorld(beam);
			}
		}
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entity, int timeLeft)
	{
		int amount = (int)(((((72000.0F - (float)timeLeft) / 72000.0F) * (float)this.getMaxDamage(stack))) * 50.0F);
		
		stack.damageItem(amount, entity);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		int damage = stack.getItemDamage();
		
		if (damage > 0)
		{
			--damage;
			stack.setItemDamage(damage);
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand)
	{
		player.setActiveHand(hand);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStack);
	}
	
	@Override
	public int getItemEnchantability()
	{
		return 1;
	}
}
