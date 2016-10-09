package zairus.hermitquest.block;

import net.minecraft.block.BlockSlime;
import net.minecraft.block.SoundType;
import zairus.hermitquest.HermitQuest;

public class BlockBlueSlime extends BlockSlime
{
	public BlockBlueSlime()
	{
		super();
		this.setSoundType(SoundType.SLIME);
		this.setCreativeTab(HermitQuest.hqTab);
	}
}
