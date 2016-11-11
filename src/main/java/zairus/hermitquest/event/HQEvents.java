package zairus.hermitquest.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.brewing.PotionBrewEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zairus.hermitquest.HQConfig;
import zairus.hermitquest.biome.decorate.WorldGenAt;
import zairus.hermitquest.biome.decorate.WorldGenBuildersChest;
import zairus.hermitquest.biome.decorate.WorldGenCatLiter;
import zairus.hermitquest.biome.decorate.WorldGenCoffee;
import zairus.hermitquest.biome.decorate.WorldGenCraftedBench;
import zairus.hermitquest.biome.decorate.WorldGenCrown;
import zairus.hermitquest.biome.decorate.WorldGenDecorationBase;
import zairus.hermitquest.biome.decorate.WorldGenGoldenLog;
import zairus.hermitquest.biome.decorate.WorldGenHighBlock;
import zairus.hermitquest.biome.decorate.WorldGenOceanGlass;
import zairus.hermitquest.biome.decorate.WorldGenPlant;
import zairus.hermitquest.biome.decorate.WorldGenSnakeLeaves;
import zairus.hermitquest.biome.decorate.WorldGenStimpack;
import zairus.hermitquest.biome.decorate.WorldGenWelsHelm;
import zairus.hermitquest.block.BlockPlant;
import zairus.hermitquest.block.HQBlocks;

public class HQEvents
{
	private static List<WorldGenDecorationBase> decorations;
	private static List<WorldGenDecorationBase> oreDecorations;
	
	static
	{
		decorations = new ArrayList<WorldGenDecorationBase>();
		oreDecorations = new ArrayList<WorldGenDecorationBase>();
		
		decorations.add(new WorldGenPlant(HQBlocks.PLANT_TEA.getDefaultState().withProperty(BlockPlant.TYPE, 0)).setRarity(HQConfig.generation_tea_plant_rarity));
		decorations.add(new WorldGenGoldenLog().setRarity(HQConfig.generation_golden_log_rarity));
		decorations.add(new WorldGenStimpack().setRarity(HQConfig.generation_stimpack_rarity));
		decorations.add(new WorldGenBuildersChest().setRarity(HQConfig.generation_builders_chest_rarity));
		decorations.add(new WorldGenWelsHelm().setRarity(HQConfig.generation_wels_helm_rarity));
		decorations.add(new WorldGenOceanGlass().setRarity(HQConfig.generation_ocean_glass_rarity));
		decorations.add(new WorldGenCrown().setRarity(HQConfig.generation_golden_crown_rarity));
		decorations.add(new WorldGenCraftedBench().setRarity(HQConfig.generation_crafted_bench_rarity));
		decorations.add(new WorldGenHighBlock().setBlockState(HQBlocks.CASTLE_DIORITE.getDefaultState()).setStacked(true).setRarity(HQConfig.generation_castle_diorite_rarity));
		decorations.add(new WorldGenHighBlock().setBlockState(HQBlocks.COSMIC_DUST_WIRE.getDefaultState()).setRarity(HQConfig.generation_cosmic_dust_rarity));
		decorations.add(new WorldGenSnakeLeaves().setRarity(HQConfig.generation_snake_leaves_rarity));
		decorations.add(new WorldGenCatLiter().setRarity(HQConfig.generation_cat_liter_rarity));
		decorations.add(new WorldGenCoffee().setRarity(HQConfig.generation_cofee_beans_rarity));
		
		oreDecorations.add(new WorldGenAt().setRarity(HQConfig.generation_block_at_rarity));
	}
	
	@SubscribeEvent
	public void onMapGenEvent(InitMapGenEvent event)
	{
		if (event.getType() == EventType.SCATTERED_FEATURE)
		{
			;
		}
	}
	
	@SubscribeEvent
	public void BiomeOreGen(OreGenEvent.Pre event)
	{
		World world = event.getWorld();
		BlockPos pos = event.getPos();
		Random rand = event.getRand();
		Biome biome = world.getBiomeForCoordsBody(pos);
		
		for (WorldGenDecorationBase decoration : oreDecorations)
		{
			if (decoration.getAllowedBiomes().contains(biome))
			{
				decoration.generate(world, rand, pos);
			}
		}
	}
	
	@SubscribeEvent
	public void BiomeDecorate(DecorateBiomeEvent.Post event)
	{
		World world = event.getWorld();
		BlockPos pos = event.getPos();
		Random rand = event.getRand();
		Biome biome = world.getBiomeForCoordsBody(pos);
		
		for (WorldGenDecorationBase decoration : decorations)
		{
			if (decoration.getAllowedBiomes().contains(biome))
			{
				decoration.generate(world, rand, pos);
			}
		}
	}
	
	@SubscribeEvent
	public void onBrew(PotionBrewEvent.Pre event)
	{
		;
	}
}
