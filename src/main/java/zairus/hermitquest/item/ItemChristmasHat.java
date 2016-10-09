package zairus.hermitquest.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import zairus.hermitquest.HermitQuest;
import zairus.hermitquest.util.MaterialHelper;

public class ItemChristmasHat extends ItemArmor
{
	public ItemChristmasHat()
	{
		super(MaterialHelper.CHRISTMAS_HAT, 0, EntityEquipmentSlot.HEAD);
		this.setCreativeTab(HermitQuest.hqTab);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if (itemSlot == 3)
		{
			if (entity instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer)entity;
				
				player.worldObj.spawnParticle(
						EnumParticleTypes.SNOW_SHOVEL, 
						player.posX + (1.0F - (itemRand.nextFloat() * 2.0F)),
						player.posY + 3.0D, 
						player.posZ + (1.0F - (itemRand.nextFloat() * 2.0F)),
						0.0F, 
						0.2F, 
						0.0F, 
						new int[] {});
			}
		}
	}
}
