package zairus.hermitquest.block;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.hermitquest.HQConstants;
import zairus.hermitquest.HermitQuest;
import zairus.hermitquest.client.Colorizers;
import zairus.hermitquest.tileentity.TileEntityBuildersChest;
import zairus.hermitquest.tileentity.TileEntityHealthPack;
import zairus.hermitquest.tileentity.TileEntityHermitBanner;
import zairus.hermitquest.tileentity.TileEntityHermitSummoner;

public class HQBlocks
{
	public static final Block PLANT_TEA;
	public static final Block GOLDEN_LOG;
	public static final Block ISKALLIUM_ORE;
	public static final Block JEVIN_SLIME;
	public static final Block ZOMBIE_FLESH_BLOCK;
	public static final Block CROWN_IN_STONE;
	public static final Block LEAVES_SNAKE;
	public static final Block AT_BLOCK;
	public static final Block CASTLE_DIORITE;
	public static final Block CAT_LITER;
	public static final Block COSMIC_DUST;
	public static final Block COSMIC_DUST_WIRE;
	public static final Block CRAFTED_BENCH;
	public static final Block COFFEE_BEANS;
	public static final Block OCEAN_GLASS;
	public static final Block HELMET_IN_STONE;
	public static final Block MINIATURE_CASTLE;
	
	public static final Block ISKALLIUM_PROCESSED;
	public static final Block TEA_CUP; // Biffa's reward is full of tea
	public static final Block SLIME_HEAD;
	public static final Block CATUE;
	public static final Block ORANGE_SPONGE;
	public static final Block COFFEE_MUG;
	
	public static final Block HERMIT_SUMMONER;
	public static final Block HEALTH_PACK;
	public static final Block BUILDERS_CHEST;
	
	public static final Block STAINDING_HERMIT_BANNER;
	public static final Block HANGING_HERMIT_BANNER;
	
	static
	{
		PLANT_TEA = new BlockPlant().setRegistryName(HQConstants.MODID, "plant_tea").setUnlocalizedName("plant_tea");
		GOLDEN_LOG = new BlockGoldenLog().setRegistryName(HQConstants.MODID, "golden_log").setUnlocalizedName("golden_log");
		ISKALLIUM_ORE = new BlockIskalliumOre().setRegistryName(HQConstants.MODID, "iskallium_ore").setUnlocalizedName("iskallium_ore");
		JEVIN_SLIME = new BlockBlueSlime().setRegistryName(HQConstants.MODID, "jevin_slime").setUnlocalizedName("jevin_slime");
		ZOMBIE_FLESH_BLOCK = new BlockZombieFlesh().setRegistryName(HQConstants.MODID, "zombie_flesh_block").setUnlocalizedName("zombie_flesh_block");
		CROWN_IN_STONE = new BlockCrownInStone().setRegistryName(HQConstants.MODID, "crown_in_stone").setUnlocalizedName("crown_in_stone");
		LEAVES_SNAKE = new BlockSnakeLeaves().setRegistryName(HQConstants.MODID, "leaves_snake").setUnlocalizedName("leaves_snake");
		AT_BLOCK = new BlockAt().setRegistryName(HQConstants.MODID, "at_block").setUnlocalizedName("at_block");
		CASTLE_DIORITE = new BlockCastleDiorite().setRegistryName(HQConstants.MODID, "castle_diorite").setUnlocalizedName("castle_diorite");
		CAT_LITER = new BlockCatLiter().setRegistryName(HQConstants.MODID, "cat_liter").setUnlocalizedName("cat_liter");
		COSMIC_DUST = new BlockCosmicDust().setRegistryName(HQConstants.MODID, "cosmic_dust").setUnlocalizedName("cosmic_dust");
		COSMIC_DUST_WIRE = new BlockCosmicDustWire().setRegistryName(HQConstants.MODID, "cosmic_dust_wire").setUnlocalizedName("cosmic_dust_wire");
		CRAFTED_BENCH = new BlockCraftedBench().setRegistryName(HQConstants.MODID, "crafted_bench").setUnlocalizedName("crafted_bench");
		COFFEE_BEANS = new BlockCoffeeBeans().setRegistryName(HQConstants.MODID, "coffee").setUnlocalizedName("coffee");
		OCEAN_GLASS = new BlockOceanGlass().setRegistryName(HQConstants.MODID, "ocean_glass").setUnlocalizedName("ocean_glass");
		HELMET_IN_STONE = new BlockHelmetInStone().setRegistryName(HQConstants.MODID, "helmet_in_stone").setUnlocalizedName("helmet_in_stone");
		MINIATURE_CASTLE = new BlockMiniatureCastle().setRegistryName(HQConstants.MODID, "miniature_castle").setUnlocalizedName("miniature_castle");
		
		ISKALLIUM_PROCESSED = new BlockIskalliumOre().setActive().setLightLevel(1.0F).setRegistryName(HQConstants.MODID, "iskallium_processed").setUnlocalizedName("iskallium_processed");
		TEA_CUP = new BlockTeaCup().setRegistryName(HQConstants.MODID, "tea_cup").setUnlocalizedName("tea_cup");
		SLIME_HEAD = new BlockSlimeHead().setRegistryName(HQConstants.MODID, "slime_head").setUnlocalizedName("slime_head");
		CATUE = new BlockCatue().setRegistryName(HQConstants.MODID, "catue_jellie").setUnlocalizedName("catue_jellie");
		ORANGE_SPONGE = new BlockOrangeSponge().setRegistryName(HQConstants.MODID, "orange_sponge").setUnlocalizedName("orange_sponge");
		COFFEE_MUG = new BlockTeaCup().setRegistryName(HQConstants.MODID, "coffee_mug").setUnlocalizedName("coffee_mug");
		
		HERMIT_SUMMONER = new BlockHermitSummoner().setRegistryName(HQConstants.MODID, "hermit_summoner").setUnlocalizedName("hermit_summoner");
		HEALTH_PACK = new BlockHealthPack().setRegistryName(HQConstants.MODID, "health_pack").setUnlocalizedName("health_pack");
		BUILDERS_CHEST = new BlockBuildersChest().setRegistryName(HQConstants.MODID, "builders_chest").setUnlocalizedName("builders_chest");
		
		STAINDING_HERMIT_BANNER = new BlockHermitBanner.BlockHermitBannerStanding().setRegistryName(HQConstants.MODID, "hermit_banner").setUnlocalizedName("hermit_banner");
		HANGING_HERMIT_BANNER = new BlockHermitBanner.BlockHermitBannerHanging().setRegistryName(HQConstants.MODID, "hermit_banner_hanging").setUnlocalizedName("hermit_banner");
	}
	
	public static void register()
	{
		HermitQuest.proxy.registerBlock(PLANT_TEA, "plant_tea", true);
		HermitQuest.proxy.registerBlock(GOLDEN_LOG, "golden_log", true);
		HermitQuest.proxy.registerBlock(ISKALLIUM_ORE, "iskallium_ore", true);
		HermitQuest.proxy.registerBlock(JEVIN_SLIME, "jevin_slime", true);
		HermitQuest.proxy.registerBlock(ZOMBIE_FLESH_BLOCK, "zombie_flesh_block", true);
		HermitQuest.proxy.registerBlock(CROWN_IN_STONE, "crown_in_stone", true);
		HermitQuest.proxy.registerBlock(LEAVES_SNAKE, "leaves_snake", true);
		HermitQuest.proxy.registerBlock(AT_BLOCK, "at_block", true);
		HermitQuest.proxy.registerBlock(CASTLE_DIORITE, "castle_diorite", true);
		HermitQuest.proxy.registerBlock(CAT_LITER, "cat_liter", true);
		HermitQuest.proxy.registerBlock(COSMIC_DUST, "cosmic_dust", true);
		HermitQuest.proxy.registerBlock(COSMIC_DUST_WIRE, "cosmic_dust_wire", true);
		HermitQuest.proxy.registerBlock(CRAFTED_BENCH, "crafted_bench", true);
		HermitQuest.proxy.registerBlock(COFFEE_BEANS, "coffee", true);
		HermitQuest.proxy.registerBlock(OCEAN_GLASS, "ocean_glass", true);
		HermitQuest.proxy.registerBlock(HELMET_IN_STONE, "helmet_in_stone", true);
		HermitQuest.proxy.registerBlock(MINIATURE_CASTLE, "miniature_castle", true);
		
		HermitQuest.proxy.registerBlock(ISKALLIUM_PROCESSED, "iskallium_processed", true);
		HermitQuest.proxy.registerBlock(TEA_CUP, "tea_cup", true);
		HermitQuest.proxy.registerBlock(SLIME_HEAD, "slime_head", true);
		HermitQuest.proxy.registerBlock(CATUE, "catue_jellie", true);
		HermitQuest.proxy.registerBlock(ORANGE_SPONGE, "orange_sponge", true);
		HermitQuest.proxy.registerBlock(COFFEE_MUG, "coffee_mug", true);
		
		HermitQuest.proxy.registerBlock(HERMIT_SUMMONER, "hermit_summoner", TileEntityHermitSummoner.class, "tileEntityHermitSummoner", true);
		HermitQuest.proxy.registerBlock(HEALTH_PACK, "health_pack", TileEntityHealthPack.class, "tileEntityHealthPack", true);
		HermitQuest.proxy.registerBlock(BUILDERS_CHEST, "builders_chest", TileEntityBuildersChest.class, "tileEntityBuildersChest", true);
		
		HermitQuest.proxy.registerBlock(STAINDING_HERMIT_BANNER, "hermit_banner", TileEntityHermitBanner.class, "tileEntityHermitBanner", false);
		HermitQuest.proxy.registerBlock(HANGING_HERMIT_BANNER, "hermit_banner_hanging", TileEntityHermitBanner.class, "tileEntityHermitBannerHanging", false);
	}
	
	@SideOnly(Side.CLIENT)
	public static void initClient()
	{
		BlockColors blockColors = Minecraft.getMinecraft().getBlockColors();
		ItemColors itemColors = Minecraft.getMinecraft().getItemColors();
		
		registerColors(blockColors, itemColors, Colorizers.BLOCK_LEAVES, new Block[] { HQBlocks.LEAVES_SNAKE });
		registerColors(blockColors, itemColors, Colorizers.BLOCK_COSMIC_DUST_WIRE, new Block[] { HQBlocks.COSMIC_DUST_WIRE });
	}
	
	private static void registerColors(BlockColors blockColors, ItemColors itemColors, IBlockColor color, Block... blocks)
	{
		blockColors.registerBlockColorHandler(color, blocks);
		itemColors.registerItemColorHandler((s, t) -> color.colorMultiplier(Block.getBlockFromItem(s.getItem()).getDefaultState(), null, null, t), blocks);
	}
}
