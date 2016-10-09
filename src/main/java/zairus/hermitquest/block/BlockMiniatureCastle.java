package zairus.hermitquest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import zairus.hermitquest.HermitQuest;

public class BlockMiniatureCastle extends Block
{
	public BlockMiniatureCastle()
	{
		super(Material.ROCK);
		this.setCreativeTab(HermitQuest.hqTab);
		this.setHardness(2.5F);
		this.setResistance(15.0F);
		this.setHarvestLevel("pickaxe", 0);
		this.setSoundType(SoundType.STONE);
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
}
