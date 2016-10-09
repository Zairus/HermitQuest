package zairus.hermitquest.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import zairus.hermitquest.HermitQuest;
import zairus.hermitquest.util.MaterialHelper;

public class ItemKnightsHelmet extends ItemArmor
{
	public ItemKnightsHelmet()
	{
		super(MaterialHelper.KNIGHTS_HELMET, 0, EntityEquipmentSlot.HEAD);
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
				Potion effect = MobEffects.RESISTANCE;
				
				int duration = 100;
				Potion pot = Potion.getPotionById(Potion.getIdFromPotion(effect));
				
				PotionEffect ef = new PotionEffect(pot, duration, 0, true, false);
				boolean apply = true;
				
				for (PotionEffect curEffects : player.getActivePotionEffects())
				{
					if (pot == curEffects.getPotion())
					{
						apply = false;
					}
				}
				
				if (apply)
					player.addPotionEffect(ef);
			}
		}
	}
}
