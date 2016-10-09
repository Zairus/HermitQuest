package zairus.hermitquest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import zairus.hermitquest.HermitQuest;

public class BlockCrownInStone extends Block
{
	public BlockCrownInStone()
	{
		super(Material.ROCK);
		this.setHardness(3.0F);
		this.setHarvestLevel("pickaxe", 1);
		this.setSoundType(SoundType.STONE);
		this.setCreativeTab(HermitQuest.hqTab);
	}
}
