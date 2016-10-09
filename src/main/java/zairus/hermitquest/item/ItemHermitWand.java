package zairus.hermitquest.item;

import java.util.List;

import net.minecraft.block.BlockFence;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
import zairus.hermitquest.entity.boss.EntityHermitBoss;
import zairus.hermitquest.hermits.Hermits;

public class ItemHermitWand extends Item
{
	public ItemHermitWand()
	{
		this.maxStackSize = 1;
		this.setCreativeTab(HermitQuest.hqTab);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
	{
		if (player.isSneaking())
		{
			if (!stack.hasTagCompound())
				initWandTag(stack);
			
			Hermits h = cycleHermit(stack);
			
			stack = updateHermit(stack, h.ordinal());
			
			stack.setStackDisplayName("Spawn: " + h.NAME);
		}
		/*
		else
		{
			world.playSound(player, player.getPosition(), HQSoundEvents.FIREBALL_SHOOT, SoundCategory.MASTER, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 1.5F);
			player.swingArm(EnumHand.MAIN_HAND);
			if (!world.isRemote)
			{
				EntityImpFireball beam = new EntityImpFireball(world, player, player.getLookVec().xCoord, player.getLookVec().yCoord, player.getLookVec().zCoord);
				world.spawnEntityInWorld(beam);
			}
		}*/
		
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}
	
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (player.isSneaking())
			return EnumActionResult.FAIL;
		
		if (world.isRemote)
        {
            return EnumActionResult.SUCCESS;
        }
		else if (!player.canPlayerEdit(pos.offset(facing), facing, stack))
        {
            return EnumActionResult.FAIL;
        }
		else
		{
			IBlockState iblockstate = world.getBlockState(pos);
			
			pos = pos.offset(facing);
			
			double d0 = 0.0D;
			
			if (facing == EnumFacing.UP && iblockstate.getBlock() instanceof BlockFence)
				d0 = 0.5D;
			
			Hermits h = getHermitFromStack(stack);
			EntityHermitBoss hermitBoss = new EntityHermitBoss(world, h);
			hermitBoss.setHermit(h);
			
			double x = pos.getX() + 0.5D;
			double y = pos.getY() + d0;
			double z = pos.getZ() + 0.5D;
			hermitBoss.setLocationAndAngles(x, y, z, MathHelper.wrapDegrees(world.rand.nextFloat() * 360.0F), 0.0F);
			hermitBoss.rotationYawHead = hermitBoss.rotationYaw;
			hermitBoss.renderYawOffset = hermitBoss.rotationYaw;
			hermitBoss.onInitialSpawn(world.getDifficultyForLocation(pos), (IEntityLivingData)null);
			world.spawnEntityInWorld(hermitBoss);
			hermitBoss.playLivingSound();
			
			return EnumActionResult.SUCCESS;
		}
	}
	
	private Hermits getHermitFromStack(ItemStack stack)
	{
		Hermits hermit;
		
		if (!stack.hasTagCompound())
			initWandTag(stack);
		
		int hermitIndex = stack.getTagCompound().getCompoundTag("HERMIT_DATA").getInteger("HERMIT_INDEX");
		
		hermit = Hermits.values()[hermitIndex];
		
		return hermit;
	}
	
	private Hermits cycleHermit(ItemStack stack)
	{
		Hermits hermit;
		
		int hermitIndex = stack.getTagCompound().getCompoundTag("HERMIT_DATA").getInteger("HERMIT_INDEX");
		++hermitIndex;
		
		if (hermitIndex >= Hermits.values().length)
			hermitIndex = 0;
		
		hermit = Hermits.values()[hermitIndex];
		
		updateHermit(stack, hermitIndex);
		
		return hermit;
	}
	
	private ItemStack updateHermit(ItemStack stack, int hermitId)
	{
		if (stack.hasTagCompound())
		{
			stack.getTagCompound().getCompoundTag("HERMIT_DATA").setInteger("HERMIT_INDEX", hermitId);
		}
		
		return stack;
	}
	
	private ItemStack initWandTag(ItemStack stack)
	{
		NBTTagCompound tag = new NBTTagCompound();
		
		tag.setInteger("HERMIT_INDEX", 0);
		
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		
		stack.getTagCompound().setTag("HERMIT_DATA", tag);
		
		return stack;
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		tooltip.add("Sneak-right-click to cycle");
		tooltip.add("Right-click ground to spawn Hermit");
	}
}
