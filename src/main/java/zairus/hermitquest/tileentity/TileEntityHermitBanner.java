package zairus.hermitquest.tileentity;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.hermitquest.hermits.HermitProps;
import zairus.hermitquest.hermits.Hermits;
import zairus.hermitquest.item.HQItems;

public class TileEntityHermitBanner extends TileEntity
{
	private int hermitIndex = 0;
	
	public TileEntityHermitBanner()
	{
		;
	}
	
	public TileEntityHermitBanner(int hIndex)
	{
		this.hermitIndex = hIndex;
	}
	
	public void setItemValues(ItemStack stack)
	{
		NBTTagCompound nbttagcompound = stack.getSubCompound("BlockEntityTag", false);
		
		if (nbttagcompound != null && nbttagcompound.hasKey("hermitIndex"))
		{
			this.hermitIndex = nbttagcompound.getInteger("hermitIndex");
		}
		
		this.updateMe();
	}
	
	public int getHermitIndex()
	{
		return this.hermitIndex;
	}
	
	public ItemStack getBannerStack()
	{
		ItemStack bannerStack = new ItemStack(HQItems.HERMIT_BANNER, 1, this.hermitIndex);
		
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setInteger("hermitIndex", this.hermitIndex);
		nbttagcompound.setString("hermitName", Hermits.values()[this.hermitIndex].NAME);
		bannerStack.setTagInfo("BlockEntityTag", nbttagcompound);
		
		return bannerStack;
	}
	
	public static void setBannerInfo(NBTTagCompound compound, int hermitIndex)
	{
		compound.setInteger("hermitIndex", hermitIndex);
	}
	
	public static int getBaseColor(ItemStack stack)
	{
		return 0;
	}
	
	public int getBaseColor()
	{
		return 0;
	}
	
	@SideOnly(Side.CLIENT)
	public ResourceLocation getResourceLocation()
	{
		return HermitProps.getBannerTexturesArray()[hermitIndex];
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		setBannerInfo(compound, this.hermitIndex);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.hermitIndex = compound.getInteger("hermitIndex");
	}
	
	@Override
	public NBTTagCompound getUpdateTag()
	{
		return this.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		NBTTagCompound syncData = new NBTTagCompound();
		writeSyncableDataToNBT(syncData);
		return new SPacketUpdateTileEntity(this.getPos(), 1, syncData);
	}
	
	@Override
	public void onDataPacket(net.minecraft.network.NetworkManager net, net.minecraft.network.play.server.SPacketUpdateTileEntity pkt)
	{
		readSyncableDataFromNBT(pkt.getNbtCompound());
	}
	
	protected void writeSyncableDataToNBT(NBTTagCompound syncData)
	{
		syncData.setInteger("hermitIndex", this.hermitIndex);
	}
	
	protected void readSyncableDataFromNBT(NBTTagCompound syncData)
	{
		this.hermitIndex = syncData.getInteger("hermitIndex");
	}
	
	private void updateMe()
	{
		this.markDirty();
		if (this.worldObj != null)
		{
			this.worldObj.markBlockRangeForRenderUpdate(getPos().add(-1, -1, -1), getPos().add(1, 1, 1));
			
			IBlockState state = this.worldObj.getBlockState(getPos());
			
			this.worldObj.notifyBlockUpdate(getPos(), state, state, 0);
		}
	}
}
