package zairus.hermitquest.tileentity;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.hermitquest.HQConstants;

public class TileEntityBuildersChest extends HQTileEntityBase
{
	private static final ResourceLocation CHEST_TEXTURES = new ResourceLocation(HQConstants.MODID, "textures/entity/builders_chest.png");
	
	protected String defaultName = "builderschest";
	
	public TileEntityBuildersChest()
	{
		;
	}
	
	public String getGUIDisplayName()
	{
		return this.hasCustomName()? this.customName : "Builder's Chest";
	}
	
	@Override
	public String getDefaultName()
	{
		return this.defaultName;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ResourceLocation getTextures()
	{
		return CHEST_TEXTURES;
	}
}
