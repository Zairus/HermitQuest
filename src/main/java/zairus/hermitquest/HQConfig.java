package zairus.hermitquest;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class HQConfig
{
	public static boolean spawn_diablo = true;
	public static boolean spawn_swampsnake = true;
	
	public static Configuration configuration;
	
	public static void init(File cFile)
	{
		configuration = new Configuration(cFile);
		configuration.load();
		
		spawn_diablo = configuration.getBoolean("spawn_diablo_el_pollo", "ENTITY_SPAWNS", spawn_diablo, "Spawns Hostile Chickens in the nether");
		spawn_swampsnake = configuration.getBoolean("spawn_spawn_swampsnake", "ENTITY_SPAWNS", spawn_swampsnake, "Spawns Python Snakes in swamp");
		
		configuration.save();
	}
}
