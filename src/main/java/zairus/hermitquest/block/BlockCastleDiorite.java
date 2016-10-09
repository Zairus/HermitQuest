package zairus.hermitquest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import zairus.hermitquest.HermitQuest;

public class BlockCastleDiorite extends Block
{
	public BlockCastleDiorite()
	{
		super(Material.ROCK);
		this.setCreativeTab(HermitQuest.hqTab);
		this.setHardness(3.0F);
		this.setHarvestLevel("pickaxe", 1);
		this.setSoundType(SoundType.STONE);
	}
}
