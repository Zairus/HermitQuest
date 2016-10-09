package zairus.hermitquest.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.storage.loot.ILootContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.hermitquest.inventory.HQContainerBase;

public abstract class HQTileEntityBase extends TileEntityLockableLoot implements ITickable, ILootContainer
{
	public int playersUsing;
	public float openPhase;
	public float prevOpenPhase;
	
	protected ItemStack[] chestContents = new ItemStack[54];
	protected String customName;
	protected String defaultName = "HQContainer";
	
	private int ticksSinceSync;
	
	public HQTileEntityBase()
	{
		;
	}
	
	public String getDefaultName()
	{
		return this.defaultName;
	}
	
	@Override
	public String getName()
	{
		return this.hasCustomName()? this.customName : "container." + this.defaultName;
	}
	
	@Override
	public boolean hasCustomName()
	{
		return customName != null;
	}
	
	public void setCustomName(String name)
	{
		this.customName = name;
	}
	
	@SideOnly(Side.CLIENT)
	public abstract ResourceLocation getTextures();
	
	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer player)
	{
		this.fillWithLoot(player);
		return new HQContainerBase(playerInventory, this, player).setGrid(6, 9);
	}
	
	@Override
	public String getGuiID()
	{
		return "hermitquest:" + this.defaultName;
	}
	
	@Override
	public int getSizeInventory()
	{
		return chestContents.length;
	}
	
	public boolean isEmpty()
	{
		boolean empty = true;
		
		for (int i = 0; i < chestContents.length; ++i)
		{
			if (chestContents[i] != null)
			{
				empty = false;
				break;
			}
		}
		
		return empty;
	}
	
	@Override
	public void update()
	{
		int i = this.pos.getX();
		int j = this.pos.getY();
		int k = this.pos.getZ();
		++this.ticksSinceSync;
		
		if (this.playersUsing != 0 && (this.ticksSinceSync) % 200 == 0)
		{
			this.playersUsing = 0;
			
			for (EntityPlayer entityplayer : this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB((double)((float)i - 5.0F), (double)((float)j - 5.0F), (double)((float)k - 5.0F), (double)((float)(i + 1) + 5.0F), (double)((float)(j + 1) + 5.0F), (double)((float)(k + 1) + 5.0F))))
			{
				if (entityplayer.openContainer instanceof HQContainerBase)
				{
					++this.playersUsing;
				}
			}
		}
		
		this.prevOpenPhase = this.openPhase;
		
		if (this.playersUsing > 0 && this.openPhase == 0.0F)
		{
			this.worldObj.playSound(
					(EntityPlayer)null, 
					i, 
					j, 
					k, 
					SoundEvents.BLOCK_CHEST_OPEN, 
					SoundCategory.BLOCKS, 
					0.5F, 
					this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}
		
		if (this.playersUsing == 0 && this.openPhase > 0.0F || this.playersUsing > 0 && this.openPhase < 1.0F)
		{
			float f2 = this.openPhase;
			
			if (this.playersUsing > 0)
				this.openPhase += 0.1F;
			else
				this.openPhase -= 0.1F;
			
			if (this.openPhase > 1.0F)
				this.openPhase = 1.0F;
			
			if (this.openPhase < 0.5F && f2 >= 0.5F)
			{
				this.worldObj.playSound(
						(EntityPlayer)null, 
						i, 
						j, 
						k, 
						SoundEvents.BLOCK_CHEST_CLOSE, 
						SoundCategory.BLOCKS, 
						0.5F, 
						this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}
			
			if (this.openPhase < 0.0F)
				this.openPhase = 0.0F;
		}
	}
	
	@Override
	public ItemStack getStackInSlot(int index)
	{
		this.fillWithLoot((EntityPlayer)null);
		return this.chestContents[index];
	}
	
	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		this.fillWithLoot((EntityPlayer)null);
		
		if (this.chestContents[index] != null)
		{
			if (this.chestContents[index].stackSize <= count)
			{
				ItemStack itemstack1 = this.chestContents[index];
                this.chestContents[index] = null;
                this.markDirty();
                return itemstack1;
			}
			else
			{
				ItemStack itemstack = this.chestContents[index].splitStack(count);
				
                if (this.chestContents[index].stackSize == 0)
                {
                    this.chestContents[index] = null;
                }
                
                this.markDirty();
                return itemstack;
			}
		}
		else
		{
			return null;
		}
	}
	
	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		this.fillWithLoot((EntityPlayer)null);
		
		if (this.chestContents[index] != null)
        {
            ItemStack itemstack = this.chestContents[index];
            this.chestContents[index] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		this.fillWithLoot((EntityPlayer)null);
		
		this.chestContents[index] = stack;
		
        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
        {
            stack.stackSize = this.getInventoryStackLimit();
        }
        
        this.markDirty();
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return true;
	}
	
	@Override
	public void openInventory(EntityPlayer player)
	{
		if (!player.isSpectator())
		{
			if (this.playersUsing < 0)
			{
				this.playersUsing = 0;
			}
			
			++this.playersUsing;
			this.worldObj.addBlockEvent(this.pos, this.getBlockType(), 1, this.playersUsing);
			this.worldObj.notifyNeighborsOfStateChange(this.pos, this.getBlockType());
			this.worldObj.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockType());
		}
	}
	
	@Override
	public void closeInventory(EntityPlayer player)
	{
		if (!player.isSpectator())
		{
			--this.playersUsing;
			this.worldObj.addBlockEvent(this.pos, this.getBlockType(), 1, this.playersUsing);
			this.worldObj.notifyNeighborsOfStateChange(this.pos, this.getBlockType());
			this.worldObj.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockType());
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		NBTTagList nbttaglist = compound.getTagList("Items", 10);
		this.chestContents = new ItemStack[this.getSizeInventory()];
		
		if (compound.hasKey("CustomName", 8))
		{
			this.customName = compound.getString("CustomName");
		}
		
		if (!this.checkLootAndRead(compound))
		{
			for (int i = 0; i < nbttaglist.tagCount(); ++i)
			{
				NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
				int j = nbttagcompound.getByte("Slot") & 255;
				
				if (j >= 0 && j < this.chestContents.length)
				{
					this.chestContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
				}
			}
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		
		if (!this.checkLootAndWrite(compound))
		{
			NBTTagList nbttaglist = new NBTTagList();
			
			for (int i = 0; i < this.chestContents.length; ++i)
			{
				if (this.chestContents[i] != null)
				{
					NBTTagCompound nbttagcompound = new NBTTagCompound();
					nbttagcompound.setByte("Slot", (byte)i);
					this.chestContents[i].writeToNBT(nbttagcompound);
					nbttaglist.appendTag(nbttagcompound);
				}
			}
			
			compound.setTag("Items", nbttaglist);
		}
		
		if (this.hasCustomName())
		{
			compound.setString("CustomName", this.customName);
		}
		
		return compound;
	}
	
	@Override
	public void updateContainingBlockInfo()
	{
		super.updateContainingBlockInfo();
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return true;
	}
	
	@Override
	public int getField(int id)
	{
		return 0;
	}
	
	@Override
	public void setField(int id, int value)
	{
		;
	}
	
	@Override
	public int getFieldCount()
	{
		return 0;
	}
	
	@Override
	public void clear()
	{
		this.fillWithLoot((EntityPlayer)null);
		
		for (int i = 0; i < this.chestContents.length; ++i)
		{
			this.chestContents[i] = null;
		}
	}
	
	@Override
	public boolean receiveClientEvent(int id, int type)
	{
		if (id == 1)
		{
			this.playersUsing = type;
			return true;
		}
		else
		{
			return super.receiveClientEvent(id, type);
		}
	}
	
	@Override
	public void invalidate()
	{
		super.invalidate();
		this.updateContainingBlockInfo();
	}
}
