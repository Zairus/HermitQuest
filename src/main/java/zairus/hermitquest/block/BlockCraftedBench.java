package zairus.hermitquest.block;

import javax.annotation.Nullable;

import net.minecraft.block.BlockWorkbench;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import zairus.hermitquest.HermitQuest;
import zairus.hermitquest.inventory.ContainerCraftedBench;

public class BlockCraftedBench extends BlockWorkbench
{
	public BlockCraftedBench()
	{
		super();
		this.setCreativeTab(HermitQuest.hqTab);
		this.setHardness(2.5F);
		this.setHarvestLevel("axe", 0);
		this.setSoundType(SoundType.WOOD);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return new AxisAlignedBB(0.00F, 0.0F, 0.00F, 1.0F, 0.875F, 1.0F);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isRemote)
		{
			return true;
		}
		else
		{
			playerIn.displayGui(new BlockCraftedBench.InterfaceCraftingTable(worldIn, pos));
			playerIn.addStat(StatList.CRAFTING_TABLE_INTERACTION);
			return true;
		}
	}
	
	public static class InterfaceCraftingTable implements IInteractionObject
	{
		private final World world;
		private final BlockPos position;
		
		public InterfaceCraftingTable(World world, BlockPos pos)
		{
			this.world = world;
			this.position = pos;
		}
		
		public String getName()
		{
			return null;
		}
		
		public boolean hasCustomName()
		{
			return false;
		}
		
		public ITextComponent getDisplayName()
		{
			return new TextComponentTranslation(Blocks.CRAFTING_TABLE.getUnlocalizedName() + ".name", new Object[0]);
		}
		
		public Container createContainer(InventoryPlayer playerInventory, EntityPlayer player)
		{
			return new ContainerCraftedBench(playerInventory, this.world, this.position);
		}
		
		public String getGuiID()
		{
			return "minecraft:crafting_table";
		}
	}
}
