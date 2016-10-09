package zairus.hermitquest.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import zairus.hermitquest.HermitQuest;
import zairus.hermitquest.util.MaterialHelper;

public class ItemCubsCoat extends ItemArmor
{
	public ItemCubsCoat()
	{
		super(MaterialHelper.CUBS_COAT, 0, EntityEquipmentSlot.CHEST);
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
				
				for (PotionEffect curEffects : player.getActivePotionEffects())
				{
					if (!curEffects.getPotion().isBeneficial())
						player.removeActivePotionEffect(curEffects.getPotion());
				}
			}
		}
	}
}
