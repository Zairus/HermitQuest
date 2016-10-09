package zairus.hermitquest.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import zairus.hermitquest.block.HQBlocks;

public class ContainerCraftedBench extends ContainerWorkbench
{
	private final World worldObj;
	private final BlockPos pos;
	
	public ContainerCraftedBench(InventoryPlayer playerInventory, World world, BlockPos pos)
	{
		super(playerInventory, world, pos);
		this.worldObj = world;
		this.pos = pos;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return this.worldObj.getBlockState(this.pos).getBlock() != HQBlocks.CRAFTED_BENCH ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}
}
