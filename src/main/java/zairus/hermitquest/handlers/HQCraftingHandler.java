package zairus.hermitquest.handlers;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import zairus.hermitquest.block.HQBlocks;
import zairus.hermitquest.item.HQItems;

public class HQCraftingHandler
{
	public static void addRecipes()
	{
		GameRegistry.addShapedRecipe(
				new ItemStack(HQBlocks.HERMIT_SUMMONER)
				, new Object[] {
						"gqg"
						,"ggg"
						,"gqg"
						,'g'
						,Blocks.GOLD_BLOCK
						,'q'
						,Blocks.QUARTZ_BLOCK
				});
		
		GameRegistry.addShapedRecipe(
				new ItemStack(HQItems.DIORITE_ARROW, 8, 0)
				, new Object[] {
						"aaa"
						,"ada"
						,"aaa"
						,'d'
						,new ItemStack(Blocks.STONE, 1, 3)
						,'a'
						,new ItemStack(Items.ARROW, 1, 0)
				});
		
		GameRegistry.addShapedRecipe(
				new ItemStack(HQItems.BEAM_GUN)
				, new Object[] {
						" rd"
						,"iii"
						,"pcd"
						,'r'
						,Items.REDSTONE
						,'d'
						,Items.DIAMOND
						,'i'
						,Items.IRON_INGOT
						,'p'
						,HQBlocks.ISKALLIUM_PROCESSED
						,'c'
						,Items.COMPARATOR
				});
		
		GameRegistry.addShapedRecipe(
				new ItemStack(HQBlocks.COSMIC_DUST)
				, new Object[] {
						"ddd"
						,"ddd"
						,"ddd"
						,'d'
						,HQItems.COSMICDUST
				});
		
		GameRegistry.addShapelessRecipe(
				new ItemStack(HQItems.COSMICDUST, 9, 0)
				, new Object[] {
						new ItemStack(HQBlocks.COSMIC_DUST)
				});
		
		GameRegistry.addShapelessRecipe(
				new ItemStack(HQItems.IMP_FIREBALL, 2, 0)
				, new Object[] {
						new ItemStack(HQItems.IMP_FIREBALL)
						, new ItemStack(Items.FIRE_CHARGE)
				});
		
		GameRegistry.addShapelessRecipe(
				new ItemStack(HQItems.APPLE_GOLDEN_THROWABLE, 1, 0)
				, new Object[] {
						new ItemStack(Items.GOLDEN_APPLE)
						, new ItemStack(Items.GUNPOWDER)
				});
		
		GameRegistry.addShapelessRecipe(
				new ItemStack(HQItems.BOOK_THROWABLE, 1, 0)
				, new Object[] {
						new ItemStack(Items.WRITTEN_BOOK)
						, new ItemStack(Items.GUNPOWDER)
				});
		
		GameRegistry.addShapelessRecipe(
				new ItemStack(HQItems.TORCH_THROWABLE, 1, 0)
				, new Object[] {
						new ItemStack(Blocks.TORCH)
						, new ItemStack(Items.GUNPOWDER)
				});
		
		GameRegistry.addSmelting(HQBlocks.GOLDEN_LOG, new ItemStack(Items.GOLD_INGOT, 9), 5.0F);
	}
}
