package zairus.hermitquest.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearest;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import zairus.hermitquest.HermitQuest;

public class BlockSlimeHead extends Block
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	public BlockSlimeHead()
	{
		super(Material.CLAY);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.setCreativeTab(HermitQuest.hqTab);
		this.setSoundType(SoundType.SLIME);
		this.setHardness(1.0F);
		
		this.setTickRandomly(true);
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		AxisAlignedBB axis = new AxisAlignedBB(pos).expand(16, 16, 16);
		
		List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class, axis);
		
		for (EntityPlayer player : players)
		{
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(Potion.getIdFromPotion(MobEffects.JUMP_BOOST)), 500, 2, true, false));
		}
		
		List<EntitySlime> slimes = world.getEntitiesWithinAABB(EntitySlime.class, axis);
		
		for (EntitySlime slime : slimes)
		{
			slime.setAttackTarget(null);
			
			if (slime.targetTasks != null && slime.targetTasks.taskEntries != null && !slime.targetTasks.taskEntries.isEmpty())
			{
				List<EntityAIBase> aiToRemove = new ArrayList<EntityAIBase>();
				
				for (EntityAITaskEntry entry : slime.targetTasks.taskEntries)
					if (entry.action instanceof EntityAIFindEntityNearestPlayer || entry.action instanceof EntityAIFindEntityNearest)
						aiToRemove.add(entry.action);
				
				for (EntityAIBase ai : aiToRemove)
					slime.targetTasks.removeTask(ai);
			}
		}
	}
	
	@Override
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		EnumFacing enumfacing = EnumFacing.getHorizontal(MathHelper.floor_double((double)(placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3).getOpposite();
		state = state.withProperty(FACING, enumfacing);
		world.setBlockState(pos, state, 3);
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {FACING});
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing enumfacing = EnumFacing.getFront(meta);
		
		if (enumfacing.getAxis() == EnumFacing.Axis.Y)
		{
			enumfacing = EnumFacing.NORTH;
		}
		
		return this.getDefaultState().withProperty(FACING, enumfacing);
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}
}
