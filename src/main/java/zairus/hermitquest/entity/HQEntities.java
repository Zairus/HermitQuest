package zairus.hermitquest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.hermitquest.HQConfig;
import zairus.hermitquest.HermitQuest;
import zairus.hermitquest.client.renderer.RenderFactory;
import zairus.hermitquest.client.renderer.entity.RenderDiabloElPollo;
import zairus.hermitquest.client.renderer.entity.RenderDioriteArrow;
import zairus.hermitquest.client.renderer.entity.RenderHermitBoss;
import zairus.hermitquest.client.renderer.entity.RenderImpFireball;
import zairus.hermitquest.client.renderer.entity.RenderIskallBeam;
import zairus.hermitquest.client.renderer.entity.RenderJevinSlime;
import zairus.hermitquest.client.renderer.entity.RenderLadyZombie;
import zairus.hermitquest.client.renderer.entity.RenderSmallSnake;
import zairus.hermitquest.client.renderer.entity.RenderThrowableBook;
import zairus.hermitquest.client.renderer.entity.RenderThrowableGoldenApple;
import zairus.hermitquest.client.renderer.entity.RenderThrowableTorch;
import zairus.hermitquest.entity.boss.EntityHermitBoss;
import zairus.hermitquest.entity.projectile.EntityDioriteArrow;
import zairus.hermitquest.entity.projectile.EntityImpFireball;
import zairus.hermitquest.entity.projectile.EntityIskallBeam;
import zairus.hermitquest.entity.projectile.EntityThrowableBook;
import zairus.hermitquest.entity.projectile.EntityThrowableGoldenApple;
import zairus.hermitquest.entity.projectile.EntityThrowableTorch;

public class HQEntities
{
	private static int lastEntityId = -1;
	
	public static void registerAll()
	{
		registerEntity(EntityHermitBoss.class, "hermitboss", 160, 1, true, 0x000000, 0xffffff);
		registerEntity(EntityDiabloElPollo.class, "diabloelpollo", 160, 1, true, 0x0, 0x0);
		registerEntity(EntityJevinSlime.class, "jevinslime", 160, 1, true, 0x5063a2, 0x6e84ce);
		registerEntity(EntityLadyZombie.class, "lady_zombie", 160, 1, true, 0x3a682e, 0xde3b79);
		registerEntity(EntityAngryVillager.class, "angry_villager", 160, 1, true, 0xb3a285, 0xeddfc8);
		registerEntity(EntitySmallSnake.class, "small_snake", 160, 1, true, 0x95460c, 0xf8a96f);
		
		registerEntity(EntityIskallBeam.class, "iskallbeam", 160, 1, true);
		registerEntity(EntityDioriteArrow.class, "dioritearrow", 160, 1, true);
		registerEntity(EntityImpFireball.class, "impfireball", 160, 1, true);
		registerEntity(EntityThrowableGoldenApple.class, "throwableGoldenApple", 160, 1, true);
		registerEntity(EntityThrowableBook.class, "throwableBook", 160, 1, true);
		registerEntity(EntityThrowableTorch.class, "throwableTorch", 160, 1, true);
		
		if (HQConfig.spawn_diablo)
			EntityRegistry.addSpawn(EntityDiabloElPollo.class, 5, 0, 1, EnumCreatureType.MONSTER, Biomes.HELL);
		
		if (HQConfig.spawn_swampsnake)
			EntityRegistry.addSpawn(EntitySmallSnake.class, 5, 0, 1, EnumCreatureType.MONSTER, Biomes.SWAMPLAND);
		
		EntityRegistry.addSpawn(EntityLadyZombie.class, 5, 0, 1, EnumCreatureType.MONSTER, Biomes.HELL);
		for (Biome biome : Biome.REGISTRY)
		{
			if (
					biome != Biomes.MUSHROOM_ISLAND 
					&& biome != Biomes.MUSHROOM_ISLAND_SHORE
					&& biome != Biomes.SKY)
				EntityRegistry.addSpawn(EntityJevinSlime.class, 20, 0, 1, EnumCreatureType.MONSTER, biome);
		}
	}
	
	public static void registerEntity(Class<? extends Entity> clazz, String name, int range, int frequency, boolean velocityUpdates)
	{
		EntityRegistry.registerModEntity(clazz, name, getNextEntityId(), HermitQuest.instance, range, frequency, velocityUpdates);
	}
	
	public static void registerEntity(Class<? extends Entity> clazz, String name, int range, int frequency, boolean velocityUpdates, int primaryColor, int secondaryColor)
	{
		registerEntity(clazz, name, range, frequency, velocityUpdates);
		EntityRegistry.registerEgg(clazz, primaryColor, secondaryColor);
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerEntityRenderers()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityHermitBoss.class, RenderFactory.create(RenderHermitBoss.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityDiabloElPollo.class, RenderFactory.create(RenderDiabloElPollo.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityJevinSlime.class, RenderFactory.create(RenderJevinSlime.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityLadyZombie.class, RenderFactory.create(RenderLadyZombie.class));
		RenderingRegistry.registerEntityRenderingHandler(EntitySmallSnake.class, RenderFactory.create(RenderSmallSnake.class));
		
		RenderingRegistry.registerEntityRenderingHandler(EntityIskallBeam.class, RenderFactory.createR2(RenderIskallBeam.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityDioriteArrow.class, RenderFactory.createR2(RenderDioriteArrow.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityImpFireball.class, RenderFactory.createR2(RenderImpFireball.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityThrowableGoldenApple.class, RenderFactory.createR2(RenderThrowableGoldenApple.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityThrowableBook.class, RenderFactory.createR2(RenderThrowableBook.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityThrowableTorch.class, RenderFactory.createR2(RenderThrowableTorch.class));
	}
	
	public static int getNextEntityId()
	{
		++lastEntityId;
		return lastEntityId;
	}
}
