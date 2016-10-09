package zairus.hermitquest.item;

import net.minecraft.item.Item;
import zairus.hermitquest.HQConstants;
import zairus.hermitquest.HermitQuest;
import zairus.hermitquest.hermits.Hermits;

public class HQItems
{
	public static Item HERMIT_WAND;
	public static Item DIORITE_ARROW;
	public static Item BEAM_GUN;
	public static Item IMP_FIREBALL;
	public static Item COSMICDUST;
	public static Item APPLE_GOLDEN_THROWABLE;
	public static Item BOOK_THROWABLE;
	public static Item TEA_LEAF;
	public static Item TORCH_THROWABLE;
	public static Item CUP_COFFEE;
	public static Item CUP_TEA;
	
	public static Item WAGGLE_SWORD;
	public static Item FANG_SWORD;
	public static Item FALSE_GOOGLES; //
	public static Item CANDY_CANE;
	public static Item GOLDEN_CROWN;
	public static Item SNAKE_HEAD;
	public static Item CUBS_COAT;
	public static Item CHRISTMAS_HAT;
	public static Item KNIGHTS_HELMET;
	public static Item BOOK_JOE;
	
	public static Item HERMIT_BANNER;
	
	static
	{
		HERMIT_WAND = new ItemHermitWand().setRegistryName(HQConstants.MODID, "hermitwand").setUnlocalizedName("hermitwand");
		DIORITE_ARROW = new ItemDioriteArrow().setRegistryName(HQConstants.MODID, "diorite_arrow").setUnlocalizedName("diorite_arrow");
		BEAM_GUN = new ItemBeamGun().setRegistryName(HQConstants.MODID, "beam_gun").setUnlocalizedName("beam_gun");
		IMP_FIREBALL = new ItemImpFireball().setRegistryName(HQConstants.MODID, "imp_fireball").setUnlocalizedName("imp_fireball");
		COSMICDUST = new ItemCosmicDust().setRegistryName(HQConstants.MODID, "cosmicdust").setUnlocalizedName("cosmicdust");
		APPLE_GOLDEN_THROWABLE = new ItemThrowableGoldenApple().setRegistryName(HQConstants.MODID, "apple_golden_throwable").setUnlocalizedName("apple_golden_throwable");
		BOOK_THROWABLE = new ItemThrowableBook().setRegistryName(HQConstants.MODID, "book_throwable").setUnlocalizedName("book_throwable");
		TEA_LEAF = new Item().setRegistryName(HQConstants.MODID, "tea_leaf").setUnlocalizedName("tea_leaf").setMaxStackSize(64);
		TORCH_THROWABLE = new ItemThrowableTorch().setRegistryName(HQConstants.MODID, "torch_throwable").setUnlocalizedName("torch_throwable");
		CUP_TEA = new ItemCup().setCupType(0).setRegistryName(HQConstants.MODID, "cup_tea").setUnlocalizedName("cup_tea");
		CUP_COFFEE = new ItemCup().setCupType(1).setRegistryName(HQConstants.MODID, "cup_coffee").setUnlocalizedName("cup_coffee");
		
		WAGGLE_SWORD = new ItemWaggleSword().setRegistryName(HQConstants.MODID, "waggle_sword").setUnlocalizedName("waggle_sword");
		FANG_SWORD = new ItemFangSword().setRegistryName(HQConstants.MODID, "fang_sword").setUnlocalizedName("fang_sword");
		FALSE_GOOGLES = new ItemFalseGoogles().setRegistryName(HQConstants.MODID, "false_googles").setUnlocalizedName("false_googles");
		CANDY_CANE = new ItemCandyCane().setRegistryName(HQConstants.MODID, "candy_cane").setUnlocalizedName("candy_cane");
		GOLDEN_CROWN = new ItemGoldenCrown().setRegistryName(HQConstants.MODID, "golden_crown").setUnlocalizedName("golden_crown");
		SNAKE_HEAD = new ItemSnakeHead().setRegistryName(HQConstants.MODID, "snake_head").setUnlocalizedName("snake_head");
		CUBS_COAT = new ItemCubsCoat().setRegistryName(HQConstants.MODID, "cubs_coat").setUnlocalizedName("cubs_coat");
		CHRISTMAS_HAT = new ItemChristmasHat().setRegistryName(HQConstants.MODID, "christmas_hat").setUnlocalizedName("christmas_hat");
		KNIGHTS_HELMET = new ItemKnightsHelmet().setRegistryName(HQConstants.MODID, "knights_helmet").setUnlocalizedName("knights_helmet");
		BOOK_JOE = new ItemColoredBook().setRegistryName(HQConstants.MODID, "book_joe").setUnlocalizedName("book_joe");
		
		HERMIT_BANNER = new ItemHermitBanner().setRegistryName(HQConstants.MODID, "hermit_banner").setUnlocalizedName("hermit_banner");
	}
	
	public static final void register()
	{
		HermitQuest.proxy.registerItem(HERMIT_WAND, "hermitwand", 0, true);
		HermitQuest.proxy.registerItem(DIORITE_ARROW, "diorite_arrow", 0, true);
		HermitQuest.proxy.registerItem(BEAM_GUN, "beam_gun", 0, true);
		HermitQuest.proxy.registerItem(IMP_FIREBALL, "imp_fireball", 0, true);
		HermitQuest.proxy.registerItem(COSMICDUST, "cosmicdust", 0, true);
		HermitQuest.proxy.registerItem(APPLE_GOLDEN_THROWABLE, "apple_golden_throwable", 0, true);
		HermitQuest.proxy.registerItem(BOOK_THROWABLE, "book_throwable", 0, true);
		HermitQuest.proxy.registerItem(TEA_LEAF, "tea_leaf", 0, true);
		HermitQuest.proxy.registerItem(TORCH_THROWABLE, "torch_throwable", 0, true);
		HermitQuest.proxy.registerItem(CUP_TEA, "cup_tea", 0, true);
		HermitQuest.proxy.registerItem(CUP_COFFEE, "cup_coffee", 0, true);
		
		HermitQuest.proxy.registerItem(WAGGLE_SWORD, "waggle_sword", 0, true);
		HermitQuest.proxy.registerItem(FANG_SWORD, "fang_sword", 0, true);
		HermitQuest.proxy.registerItem(FALSE_GOOGLES, "false_googles", 0, true);
		HermitQuest.proxy.registerItem(CANDY_CANE, "candy_cane", 0, true);
		HermitQuest.proxy.registerItem(GOLDEN_CROWN, "golden_crown", 0, true);
		HermitQuest.proxy.registerItem(SNAKE_HEAD, "snake_head", 0, true);
		HermitQuest.proxy.registerItem(CUBS_COAT, "cubs_coat", 0, true);
		HermitQuest.proxy.registerItem(CHRISTMAS_HAT, "christmas_hat", 0, true);
		HermitQuest.proxy.registerItem(KNIGHTS_HELMET, "knights_helmet", 0, true);
		HermitQuest.proxy.registerItem(BOOK_JOE, "book_joe", 0, true);
		
		for (int i = 0; i < Hermits.values().length; ++i)
			HermitQuest.proxy.registerItem(HERMIT_BANNER, "hermit_banner", i, true);
	}
}
