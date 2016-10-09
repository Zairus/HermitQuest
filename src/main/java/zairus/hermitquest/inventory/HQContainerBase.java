package zairus.hermitquest.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class HQContainerBase extends Container
{
	private IInventory inventory;
	
	private int rows = 6;
	private int columns = 9;
	
	public HQContainerBase(IInventory playerInventory, IInventory crateInventory, EntityPlayer player)
	{
		this.inventory = crateInventory;
		this.inventory.openInventory(player);
		
		// Container inventory
		for (int i = 0; i < rows; ++i)
		{
			for (int j = 0; j < columns; ++j)
			{
				this.addSlotToContainer(new Slot(inventory, i * columns + j, 7 + j * 18, -7 + i * 18));
			}
		}
		
		// Player inventory
		for (int l = 0; l < 3; ++l)
		{
			for (int j1 = 0; j1 < 9; ++j1)
			{
				this.addSlotToContainer(new Slot(playerInventory, j1 + l * 9 + 9, 7 + j1 * 18, 105 + l * 18));
			}
		}
		
		// Hotbar
        for (int i1 = 0; i1 < 9; ++i1)
        {
            this.addSlotToContainer(new Slot(playerInventory, i1, 7 + i1 * 18, 163));
        }
	}
	
	public HQContainerBase setGrid(int rows, int cols)
	{
		this.rows = rows;
		this.columns = cols;
		
		return this;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return true;
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		super.onContainerClosed(player);
		this.inventory.closeInventory(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(index);
		
		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			
			if (index < 54)
			{
				
				if (!this.mergeItemStack(itemstack1, 53, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 0, 54, false))
			{
				return null;
			}
			
			if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }
		}
		
		return itemstack;
	}
}
