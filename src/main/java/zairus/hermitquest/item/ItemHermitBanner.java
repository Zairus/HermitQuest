package zairus.hermitquest.item;

import java.util.List;

import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.BlockWallSign;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.hermitquest.HermitQuest;
import zairus.hermitquest.block.HQBlocks;
import zairus.hermitquest.hermits.Hermits;
import zairus.hermitquest.tileentity.TileEntityHermitBanner;

@SuppressWarnings("deprecation")
public class ItemHermitBanner extends ItemBlock
{
	public ItemHermitBanner()
	{
		super(HQBlocks.STAINDING_HERMIT_BANNER);
		this.maxStackSize = 16;
		this.setCreativeTab(HermitQuest.hqTab);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}
	
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		IBlockState iblockstate = world.getBlockState(pos);
		boolean flag = iblockstate.getBlock().isReplaceable(world, pos);
		
		if (facing != EnumFacing.DOWN && (iblockstate.getMaterial().isSolid() || flag) && (!flag || facing == EnumFacing.UP))
		{
			pos = pos.offset(facing);
			
			if (player.canPlayerEdit(pos, facing, stack) && HQBlocks.STAINDING_HERMIT_BANNER.canPlaceBlockAt(world, pos))
			{
				if (world.isRemote)
				{
					return EnumActionResult.SUCCESS;
				}
				else
				{
					pos = flag ? pos.down() : pos;
					
					if (facing == EnumFacing.UP)
					{
						int i = MathHelper.floor_double((double)((player.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
						world.setBlockState(pos, HQBlocks.STAINDING_HERMIT_BANNER.getDefaultState().withProperty(BlockStandingSign.ROTATION, Integer.valueOf(i)), 3);
					}
					else
					{
						world.setBlockState(pos, HQBlocks.HANGING_HERMIT_BANNER.getDefaultState().withProperty(BlockWallSign.FACING, facing), 3);
					}
					
					--stack.stackSize;
					TileEntity tileentity = world.getTileEntity(pos);
					
					if (tileentity instanceof TileEntityHermitBanner)
					{
						((TileEntityHermitBanner)tileentity).setItemValues(stack);
					}
					
					return EnumActionResult.SUCCESS;
				}
			}
			else
			{
				return EnumActionResult.FAIL;
			}
		}
		else
		{
			return EnumActionResult.FAIL;
		}
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		String s = "item.banner.";
		
		if (stack.hasTagCompound())
		{
			NBTTagCompound tag1 = stack.getTagCompound();
			if (tag1.hasKey("BlockEntityTag"))
			{
				NBTTagCompound tag2 = tag1.getCompoundTag("BlockEntityTag");
				s += tag2.getString("hermitName");
				s += ".name";
			}
		}
		
		if (s == "item.banner.")
		{
			s += "default.name";
		}
		
		return I18n.translateToLocal(s);
	}
	
	@SideOnly(Side.CLIENT)
	public static void appendHoverTextFromTileEntityTag(ItemStack stack, List<String> list)
	{
		NBTTagCompound nbttagcompound = stack.getSubCompound("BlockEntityTag", false);
		
		if (nbttagcompound != null && nbttagcompound.hasKey("hermitName"))
		{
			list.add(nbttagcompound.getString("hermitName") + " Banner");
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
	{
		appendHoverTextFromTileEntityTag(stack, tooltip);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> subItems)
	{
		for (int i = 0; i < Hermits.values().length; ++i)
		{
			subItems.add(getHermitBannerFromIndex(i));
		}
	}
	
	public static ItemStack getHermitBannerFromIndex(int index)
	{
		ItemStack stack = new ItemStack(HQItems.HERMIT_BANNER, 1, index);
		
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setInteger("hermitIndex", index);
		nbttagcompound.setString("hermitName", Hermits.values()[index].NAME);
		
		NBTTagCompound nbttagcompound1 = new NBTTagCompound();
		nbttagcompound1.setTag("BlockEntityTag", nbttagcompound);
		stack.setTagCompound(nbttagcompound1);
		
		return stack;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTab()
	{
		return HermitQuest.hqTab;
	}
	
	public static EnumDyeColor getBaseColor(ItemStack stack)
	{
		return EnumDyeColor.WHITE;
	}
}
