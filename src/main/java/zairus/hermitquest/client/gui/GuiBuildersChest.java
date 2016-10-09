package zairus.hermitquest.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.hermitquest.HQConstants;
import zairus.hermitquest.inventory.HQContainerBase;
import zairus.hermitquest.tileentity.TileEntityBuildersChest;

@SideOnly(Side.CLIENT)
public class GuiBuildersChest extends GuiContainer
{
	private static final ResourceLocation GUI_BACKGROUND = new ResourceLocation(HQConstants.MODID, "textures/gui/gui_container_large.png");
	
	private IInventory inventory;
	
	public GuiBuildersChest(IInventory playerInv, IInventory inventorySlots, EntityPlayer player)
	{
		super(new HQContainerBase(playerInv, inventorySlots, player));
		this.inventory = inventorySlots;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.fontRendererObj.drawString(((TileEntityBuildersChest)inventory).getGUIDisplayName(), 7, -16, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GUI_BACKGROUND);
		this.ySize = 207;
		int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize + 10);
	}
}
