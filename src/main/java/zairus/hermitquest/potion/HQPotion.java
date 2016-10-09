package zairus.hermitquest.potion;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import zairus.hermitquest.HQConstants;

public class HQPotion extends Potion
{
	protected HQPotion(boolean isBadEffect, int liquidColor)
	{
		super(isBadEffect, liquidColor);
	}
	
	public static void registerPotions()
	{
		Potion.REGISTRY.register(Potion.REGISTRY.getKeys().size() + 1, new ResourceLocation(HQConstants.MODID ,"relaxed"), new HQPotion(true, 14981690).setPotionName("effect.relax").registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "27CD4867-AE94-48C1-B287-A584642E05BB", -0.15000000596046448D, 2));
	}
}
