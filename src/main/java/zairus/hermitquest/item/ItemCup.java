package zairus.hermitquest.item;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.hermitquest.HermitQuest;
import zairus.hermitquest.block.BlockTeaCup;
import zairus.hermitquest.block.HQBlocks;

public class ItemCup extends Item
{
	private static final int RECHARGE_TICKS = 6000;
	
	private int cupType;
	
	public ItemCup()
	{
		this.setMaxStackSize(1);
		this.setCreativeTab(HermitQuest.hqTab);
	}
	
	public ItemCup setCupType(int type)
	{
		this.cupType = type;
		return this;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 32;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.DRINK;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand)
	{
		ActionResult<ItemStack> result = new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStack);
		
		int timer = this.getStackTime(itemStack);
		
		if (timer >= RECHARGE_TICKS)
		{
			player.setActiveHand(hand);
			result = new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStack);
		}
		
		return result;
	}
	
	private int getStackTime(ItemStack stack)
	{
		int timer = 0;
		
		if (stack.hasTagCompound())
		{
			NBTTagCompound tag = stack.getTagCompound();
			if (!tag.hasKey("rechargeTimer"))
				tag.setInteger("rechargeTimer", 0);
			timer = tag.getInteger("rechargeTimer");
		}
		else
		{
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("rechargeTimer", 0);
			stack.setTagCompound(tag);
		}
		
		return timer;
	}
	
	private void setStackTime(ItemStack stack, int time)
	{
		if (stack.hasTagCompound())
		{
			NBTTagCompound tag = stack.getTagCompound();
			if (!tag.hasKey("rechargeTimer"))
				tag.setInteger("rechargeTimer", 0);
			tag.setInteger("rechargeTimer", time);
		}
		else
		{
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("rechargeTimer", 0);
			stack.setTagCompound(tag);
		}
	}
	
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		Block cupBlock = null;
		
		if (stack.getItem() == HQItems.CUP_TEA)
			cupBlock = HQBlocks.TEA_CUP;
		else
			cupBlock = HQBlocks.COFFEE_MUG;
		
		boolean flag = world.getBlockState(pos).getBlock().isReplaceable(world, pos);
		BlockPos blockpos = flag ? pos : pos.offset(facing);
		
		if (
				player.canPlayerEdit(blockpos, facing, stack) 
				&& world.canBlockBePlaced(world.getBlockState(blockpos).getBlock(), blockpos, false, facing, (Entity)null, stack) 
				&& cupBlock.canPlaceBlockAt(world, blockpos))
		{
			if (!player.capabilities.isCreativeMode)
				--stack.stackSize;
			
			IBlockState state = cupBlock.getDefaultState();
			EnumFacing enumfacing = EnumFacing.getHorizontal(MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3).getOpposite();
			state = state.withProperty(BlockTeaCup.FACING, enumfacing);
			
			world.setBlockState(blockpos, state, 3);
			
			return EnumActionResult.SUCCESS;
		}
		
		return EnumActionResult.FAIL;
	}
	
	@Override
	@Nullable
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving)
	{
		EntityPlayer entityplayer = entityLiving instanceof EntityPlayer ? (EntityPlayer)entityLiving : null;
		
		if (!world.isRemote)
		{
			List<PotionEffect> effects = new ArrayList<PotionEffect>();
			
			switch(this.cupType)
			{
			case 1:
				effects.add(new PotionEffect(MobEffects.SPEED, 600, 2));
				effects.add(new PotionEffect(MobEffects.HUNGER, 600, 0));
				break;
			default:
				effects.add(new PotionEffect(MobEffects.SATURATION, 600, 0));
				effects.add(new PotionEffect(MobEffects.SPEED, 600, 0));
				break;
			}
			
			for (PotionEffect effect : effects)
				entityLiving.addPotionEffect(effect);
			
			if (entityplayer == null || !entityplayer.capabilities.isCreativeMode)
				this.setStackTime(stack, 0);
		}
		
		return stack;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		int time = this.getStackTime(stack);
		
		if (time < RECHARGE_TICKS)
		{
			++time;
			this.setStackTime(stack, time);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		if (this.getStackTime(stack) < RECHARGE_TICKS)
		{
			tooltip.add("Empty");
			tooltip.add("Refilling...");
		}
		else
		{
			tooltip.add("Full");
			tooltip.add("Take a sip");
		}
	}
}
