package zairus.hermitquest.potion;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import zairus.hermitquest.HQConstants;
import zairus.hermitquest.item.HQItems;

public class HQPotionType extends PotionType
{
	public static HQPotion EFFECT_RELAXING;
	
	public static PotionType RELAXING_POTION;
	
	public static void registerPotionTypes()
	{
		HQPotion potion = (HQPotion)Potion.REGISTRY.getObject(new ResourceLocation(HQConstants.MODID, "relaxed"));
		EFFECT_RELAXING = potion;
		
		registerPotionType("hermitquest:relaxing", new PotionType(new PotionEffect[] {new PotionEffect(EFFECT_RELAXING, 1800)}));
		
		RELAXING_POTION = (PotionType)PotionType.REGISTRY.getObject(new ResourceLocation(HQConstants.MODID, "relaxing"));
		
		ItemStack inputStack = new ItemStack(Items.POTIONITEM);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("Potion", "minecraft:awkward");
		inputStack.setTagCompound(nbt);
		
		ItemStack potStack = new ItemStack(Items.POTIONITEM);
		nbt = new NBTTagCompound();
		nbt.setString("Potion", "hermitquest:relaxing");
		potStack.setTagCompound(nbt);
		
		ItemStack splashStack = new ItemStack(Items.SPLASH_POTION);
		nbt = new NBTTagCompound();
		nbt.setString("Potion", "hermitquest:relaxing");
		splashStack.setTagCompound(nbt);
		
		BrewingRecipeRegistry.addRecipe(inputStack, new ItemStack(HQItems.TEA_LEAF), potStack);
		BrewingRecipeRegistry.addRecipe(potStack, new ItemStack(Items.GUNPOWDER), splashStack);
	}
}
