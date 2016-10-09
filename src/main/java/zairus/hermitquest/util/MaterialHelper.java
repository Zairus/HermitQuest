package zairus.hermitquest.util;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class MaterialHelper
{
	public static final ArmorMaterial FALSE_GOOGLES = EnumHelper.addArmorMaterial(
			"false_googles", 
			"hermitquest:false_symmetry", 
			1500, 
			new int[] {2,7,5,3}, 
			10, 
			SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 
			1);
	
	public static final ArmorMaterial CHRISTMAS_HAT = EnumHelper.addArmorMaterial(
			"christmas_hat", 
			"hermitquest:christmas_hat", 
			1500, 
			new int[] {2,7,5,3}, 
			10, 
			SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 
			1);
	
	public static final ArmorMaterial SNAKE_HEAD = EnumHelper.addArmorMaterial(
			"snakehead", 
			"hermitquest:snakehead", 
			1500, 
			new int[] {2,7,5,3}, 
			10, 
			SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 
			1);
	
	public static final ArmorMaterial KNIGHTS_HELMET = EnumHelper.addArmorMaterial(
			"knights_helmet", 
			"hermitquest:knights_helmet", 
			1500, 
			new int[] {2,7,5,3}, 
			10, 
			SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 
			1);
	
	public static final ArmorMaterial GOLDEN_CROWN = EnumHelper.addArmorMaterial(
			"golden_crown", 
			"hermitquest:golden_crown", 
			1500, 
			new int[] {2,7,5,3}, 
			10, 
			SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 
			1);
	
	public static final ArmorMaterial CUBS_COAT = EnumHelper.addArmorMaterial(
			"cubs_coat", 
			"hermitquest:cubs_coat", 
			1500, 
			new int[] {2,7,5,3}, 
			10, 
			SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 
			1);
}
