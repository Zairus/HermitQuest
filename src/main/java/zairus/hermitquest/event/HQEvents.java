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
		
		decorations.add(new WorldGenPlant(HQBlocks.PLANT_TEA.getDefaultState().withProperty(BlockPlant.TYPE, 0)).setRarity(60));
		decorations.add(new WorldGenGoldenLog().setRarity(80));
		decorations.add(new WorldGenStimpack().setRarity(70));
		decorations.add(new WorldGenBuildersChest().setRarity(1));
		decorations.add(new WorldGenWelsHelm().setRarity(200));
		decorations.add(new WorldGenOceanGlass().setRarity(1));
		decorations.add(new WorldGenCrown().setRarity(1));
		decorations.add(new WorldGenCraftedBench().setRarity(10));
		decorations.add(new WorldGenHighBlock().setBlockState(HQBlocks.CASTLE_DIORITE.getDefaultState()).setStacked(true).setRarity(30));
		decorations.add(new WorldGenHighBlock().setBlockState(HQBlocks.COSMIC_DUST_WIRE.getDefaultState()).setRarity(5));
		decorations.add(new WorldGenSnakeLeaves().setRarity(80));
		decorations.add(new WorldGenCatLiter().setRarity(100));
		decorations.add(new WorldGenCoffee().setRarity(50));
		
		oreDecorations.add(new WorldGenAt().setRarity(1));
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
		/*
		ItemStack ingredient = event.getItem(3);
		
		if (ingredient.getItem() == Items.SLIME_BALL)
		{
			for (int i = 0; i < 3; ++i)
			{
				if (event.getItem(i).getItem() == Items.POTIONITEM && event.getItem(i).getItemDamage() == 0)
				{
					ItemStack pot = new ItemStack(Items.SPLASH_POTION, 1, 0);
					
					NBTTagCompound potTag = new NBTTagCompound();
					NBTTagList tagList = new NBTTagList();
					
					potTag.setByte("Id", (byte)0);
					potTag.setByte("Amplifier", (byte)1);
					potTag.setInteger("Duration", 3);
					potTag.setBoolean("Ambient", false);
					potTag.setBoolean("ShowParticles", true);
					
					tagList.appendTag(potTag);
					
					pot.setTagCompound(new NBTTagCompound());
					pot.getTagCompound().setTag("CustomPotionEffects", tagList);
					
					event.setItem(i, pot);
				}
			}
		}
		*/
	}
}
