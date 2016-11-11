package zairus.hermitquest.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
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
		if (itemSlot == 2)
		{
			if (entity instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer)entity;
				
				List<Potion> toRemove = new ArrayList<Potion>();
				
				for (PotionEffect curEffects : player.getActivePotionEffects())
				{
					if (curEffects.getPotion().isBadEffect())
						toRemove.add(curEffects.getPotion());
				}
				
				for (Potion potions : toRemove)
					player.removeActivePotionEffect(potions);
			}
		}
	}
}
