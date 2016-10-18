package zairus.hermitquest;

import org.apache.logging.log4j.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import zairus.hermitquest.block.HQBlocks;
import zairus.hermitquest.event.HQEvents;
import zairus.hermitquest.gui.GuiHandler;
import zairus.hermitquest.handlers.HQCraftingHandler;
import zairus.hermitquest.item.HQItems;
import zairus.hermitquest.potion.HQPotion;
import zairus.hermitquest.potion.HQPotionType;
import zairus.hermitquest.proxy.CommonProxy;
import zairus.hermitquest.sound.HQSoundEvents;
import zairus.hermitquest.world.gen.feature.WorldGenIskalliumOre;

@Mod(modid = HQConstants.MODID, name = HQConstants.MODNAME, version = HQConstants.VERSION)
public class HermitQuest
{
	@Mod.Instance(HQConstants.MODID)
	public static HermitQuest instance;
	
	@SidedProxy(clientSide = HQConstants.CLIENT_PROXY, serverSide = HQConstants.COMMON_PROXY)
	public static CommonProxy proxy;
	
	public static Logger logger;
	
	public static CreativeTabs hqTab = new CreativeTabs("hermitQuest") {
		@Override
		public Item getTabIconItem()
		{
			return Item.getItemFromBlock(HQBlocks.HERMIT_SUMMONER);
		}
	};
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
		
		HermitQuest.proxy.preInit(event);
		
		HQConfig.init(event.getSuggestedConfigurationFile());
		
		HQSoundEvents.register();
	}
	
    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        HermitQuest.proxy.init(event);
        
        HQItems.register();
        HQBlocks.register();
        
        HermitQuest.proxy.initBuiltinShapes();
        
        GameRegistry.registerWorldGenerator(new WorldGenIskalliumOre(), 1);
        
        HQPotion.registerPotions();
        HQPotionType.registerPotionTypes();
        
        HQCraftingHandler.addRecipes();
        
        HQEvents eventHandler = new HQEvents();
        MinecraftForge.EVENT_BUS.register(eventHandler);
        MinecraftForge.TERRAIN_GEN_BUS.register(eventHandler);
        MinecraftForge.ORE_GEN_BUS.register(eventHandler);
        
        NetworkRegistry.INSTANCE.registerGuiHandler(HermitQuest.instance, new GuiHandler());
    }
    
    @Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
    	HermitQuest.proxy.postInit(event);
	}
}
