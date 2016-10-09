package zairus.hermitquest.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import zairus.hermitquest.HermitQuest;
import zairus.hermitquest.tileentity.TileEntityHermitSummoner;

public class BlockHermitSummoner extends Block implements ITileEntityProvider
{
	public BlockHermitSummoner()
	{
		super(Material.ROCK);
		this.setCreativeTab(HermitQuest.hqTab);
		this.setSoundType(SoundType.STONE);
		this.setHardness(1.5F);
		this.setResistance(2000.0F);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityHermitSummoner();
	}
}
