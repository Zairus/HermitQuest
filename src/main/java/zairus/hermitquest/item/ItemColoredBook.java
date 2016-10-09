package zairus.hermitquest.item;

import net.minecraft.item.Item;
import zairus.hermitquest.HermitQuest;

public class ItemColoredBook extends Item
{
	public ItemColoredBook()
	{
		this.setMaxDamage(64);
		this.setCreativeTab(HermitQuest.hqTab);
	}
}
