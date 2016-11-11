package zairus.hermitquest;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class HQConfig
{
	public static boolean spawn_diablo = true;
	public static boolean spawn_swampsnake = true;
	
	public static boolean output_generation_log = true;
	
	public static int generation_tea_plant_rarity = 60;
	public static int generation_golden_log_rarity = 80;
	public static int generation_stimpack_rarity = 70;
	public static int generation_builders_chest_rarity = 1;
	public static int generation_wels_helm_rarity = 180;
	public static int generation_ocean_glass_rarity = 1;
	public static int generation_golden_crown_rarity = 1;
	public static int generation_crafted_bench_rarity = 5;
	public static int generation_castle_diorite_rarity = 50;
	public static int generation_cosmic_dust_rarity = 5;
	public static int generation_snake_leaves_rarity = 60;
	public static int generation_cat_liter_rarity = 80;
	public static int generation_cofee_beans_rarity = 25;
	public static int generation_block_at_rarity = 1;
	
	public static int ore_generation_weight_iskallium = 1;
	
	public static Configuration configuration;
	
	public static void init(File cFile)
	{
		configuration = new Configuration(cFile);
		configuration.load();
		
		configuration.addCustomCategoryComment("ENTITY_SPAWNS", "Control special entity spawns on the world.");
		configuration.addCustomCategoryComment("GENERAL_CONFIGURATION", "Miscellaneous configuration options.");
		configuration.addCustomCategoryComment("WORLD_GENERATION_RARITY", "The higher the number, the more rare generation is.");
		configuration.addCustomCategoryComment("ORE_GENERATION_WEIGHT", "Weight for ore generation, the higher, more generates.");
		
		spawn_diablo = configuration.getBoolean("spawn_diablo_el_pollo", "ENTITY_SPAWNS", spawn_diablo, "Spawns Hostile Chickens in the nether");
		spawn_swampsnake = configuration.getBoolean("spawn_spawn_swampsnake", "ENTITY_SPAWNS", spawn_swampsnake, "Spawns Python Snakes in swamp");
		
		output_generation_log = configuration.getBoolean("output_generation_log", "GENERAL_CONFIGURATION", output_generation_log, "Sets wether output world generation to game log or not.");
		
		generation_tea_plant_rarity = configuration.getInt("generation_tea_plant_rarity", "WORLD_GENERATION_RARITY", generation_tea_plant_rarity, 0, Integer.MAX_VALUE, "");
		generation_golden_log_rarity = configuration.getInt("generation_golden_log_rarity", "WORLD_GENERATION_RARITY", generation_golden_log_rarity, 0, Integer.MAX_VALUE, "");
		generation_stimpack_rarity = configuration.getInt("generation_stimpack_rarity", "WORLD_GENERATION_RARITY", generation_stimpack_rarity, 0, Integer.MAX_VALUE, "");
		generation_builders_chest_rarity = configuration.getInt("generation_builders_chest_rarity", "WORLD_GENERATION_RARITY", generation_builders_chest_rarity, 0, Integer.MAX_VALUE, "");
		generation_wels_helm_rarity = configuration.getInt("generation_wels_helm_rarity", "WORLD_GENERATION_RARITY", generation_wels_helm_rarity, 0, Integer.MAX_VALUE, "");
		generation_ocean_glass_rarity = configuration.getInt("generation_ocean_glass_rarity", "WORLD_GENERATION_RARITY", generation_ocean_glass_rarity, 0, Integer.MAX_VALUE, "");
		generation_golden_crown_rarity = configuration.getInt("generation_golden_crown_rarity", "WORLD_GENERATION_RARITY", generation_golden_crown_rarity, 0, Integer.MAX_VALUE, "");
		generation_crafted_bench_rarity = configuration.getInt("generation_crafted_bench_rarity", "WORLD_GENERATION_RARITY", generation_crafted_bench_rarity, 0, Integer.MAX_VALUE, "");
		generation_castle_diorite_rarity = configuration.getInt("generation_castle_diorite_rarity", "WORLD_GENERATION_RARITY", generation_castle_diorite_rarity, 0, Integer.MAX_VALUE, "");
		generation_cosmic_dust_rarity = configuration.getInt("generation_cosmic_dust_rarity", "WORLD_GENERATION_RARITY", generation_cosmic_dust_rarity, 0, Integer.MAX_VALUE, "");
		generation_snake_leaves_rarity = configuration.getInt("generation_snake_leaves_rarity", "WORLD_GENERATION_RARITY", generation_snake_leaves_rarity, 0, Integer.MAX_VALUE, "");
		generation_cat_liter_rarity = configuration.getInt("generation_cat_liter_rarity", "WORLD_GENERATION_RARITY", generation_cat_liter_rarity, 0, Integer.MAX_VALUE, "");
		generation_cofee_beans_rarity = configuration.getInt("generation_cofee_beans_rarity", "WORLD_GENERATION_RARITY", generation_cofee_beans_rarity, 0, Integer.MAX_VALUE, "");
		generation_block_at_rarity = configuration.getInt("generation_block_at_rarity", "WORLD_GENERATION_RARITY", generation_block_at_rarity, 0, Integer.MAX_VALUE, "");
		
		ore_generation_weight_iskallium = configuration.getInt("ore_generation_weight_iskallium", "ORE_GENERATION_WEIGHT", ore_generation_weight_iskallium, 0, Integer.MAX_VALUE, "");;
		
		configuration.save();
	}
}
