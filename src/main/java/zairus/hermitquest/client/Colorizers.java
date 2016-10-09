package zairus.hermitquest.client;

import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Colorizers
{
	public static IBlockColor getBlockLeaves(int tint)
	{
		return (state, world, pos, tintIndex) ->
		{
			if (tint == -1 || tintIndex == tint)
			{
				if (world != null && pos != null)
					return BiomeColorHelper.getFoliageColorAtPos(world, pos);
				else
					return ColorizerFoliage.getFoliageColorBasic();
			}
			else
			{
				return 0xFFFFFF;
			}
		};
	}
	
	public static IBlockColor getBlockCosmicDust()
	{
		return (state, world, pos, tintIndex) ->
		{
			return 0x457c9f;
		};
	}
	
	public static final IBlockColor BLOCK_LEAVES = getBlockLeaves(-1);
	public static final IBlockColor BLOCK_COSMIC_DUST_WIRE = getBlockCosmicDust();
}
