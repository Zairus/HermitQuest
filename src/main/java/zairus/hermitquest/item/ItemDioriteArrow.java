package zairus.hermitquest.item;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.hermitquest.HermitQuest;
import zairus.hermitquest.entity.projectile.EntityDioriteArrow;

public class ItemDioriteArrow extends ItemArrow
{
	public ItemDioriteArrow()
	{
		this.setCreativeTab(HermitQuest.hqTab);
	}
	
	public EntityArrow createArrow(World world, ItemStack stack, EntityLivingBase shooter)
	{
		EntityDioriteArrow entitytippedarrow = new EntityDioriteArrow(world, shooter);
		return entitytippedarrow;
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		tooltip.add("iskall85's weakness");
	}
}
