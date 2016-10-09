package zairus.hermitquest.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import zairus.hermitquest.client.gui.GuiBuildersChest;
import zairus.hermitquest.inventory.HQContainerBase;
import zairus.hermitquest.tileentity.TileEntityBuildersChest;

public class GuiHandler implements IGuiHandler
{
	public static final int GUI_BUILDERSCHEST = 0;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (ID)
		{
		case GUI_BUILDERSCHEST:
			TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
			if (tileEntity instanceof TileEntityBuildersChest)
			{
				return new HQContainerBase(player.inventory, (TileEntityBuildersChest)tileEntity, player);
			}
			break;
		}
		
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (ID)
		{
		case GUI_BUILDERSCHEST:
			TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
			if (tileEntity instanceof TileEntityBuildersChest)
			{
				return new GuiBuildersChest(player.inventory, (TileEntityBuildersChest)tileEntity, player);
			}
			break;
		}
		
		return null;
	}
}
