package zairus.hermitquest.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.hermitquest.HermitQuest;

public class ItemColoredBook extends Item
{
	private String[] quotes = {
			"Keep adventuring!",
			"Howdy, y'all!",
			"Help, I'm trapped in a boss loot factory!",
			"When you radicalize moderation, only the radicals are moderate?",
			"Saw the phrase 'blizzy McTay' used to mean 'well-suited.' However, I'm unsure if it's blizzy McTay to its own purpose.",
			"What happened to me? I just spent two minutes trying to write a Minority Report pun and ended up with nothing good. I used to be cool.",
			"Precog Mail is great, but it's currently limited to the D.C. area and the pricing structure is murder.",
			"I love the replicator. It lets us eat panda whenever we want. Even though we killed them off for their meat centuries ago.",
			"Girl, you said you'd never hurt me. And I believed you. I've learned nothing from the holodeck.",
			"There might be four lights, there might be five. What matters is that they're all green.",
			"Sometimes when we stand on the transporter pad and begin to shimmer, I imagine we're breaking up.",
			"Gears whirl wildly through space, nearer their nature than the other wreckage, which was never intended to spin that fast.",
			"The monsters under your skin want your bones to live under the bed again—they feel safe there.",
			"Poetry for the mind is like food for the stomach. I want poetry I feel in my eyes, the way I taste this spread on my tongue.",
			"When compulsive space travel surprised us all, my diaspora studies left me ill-prepared to determine if gravity only felt stronger here.",
			"Walked past two perfectly fine but unattended crutches on the ground. I choose to believe a miracle occured here.",
			"I'll feel dumb for driving less once it's proven that global warming is created by future time travelers tearing the fabric of reality. If I can walk to the store, they can walk to the Victorian Era.",
			"There should be a store brand called 4% Alcohol. The other 96% is left to your imagination and the small text on the back.",
			"Was it a bocce ball? if so, my condolences—those hurt worst of all. Well, except for fireballs.",
			"Okay I went back and checked more, and there are logs and stone under some of them, or maybe I just couldn't see due to darkness.",
			"I just learned that In some income brackets, macaroni and cheese is a side dish.",
			"I just watched a Werner Herzog movie about the Death Penalty. Wine and sandwiches were served.",
			"Just got done appreciating Egyptian artifacts at the @FristCenter. I am displeased by how materialisticly centered their views of death were.",
			"Why didn't anyone tell me that the Rufio in Battle Royale is better than the one in Hook?",
			"I've been assured this procedure has a high survival rate.",
			"My favorite YouTube comment about me today: \"Joe you are a monster (in the bad way)\" Today someone can't call me a monster or beast without it misconstrued as praise. Tomorrow: \"Joe, you're a great satan (in the bad way)\"",
			"The future's always brightest as you cast Hiroshima shadows.",
			"I'm now tennanting around the room making faces and saying yes, wait, and no alternately.",
			"Poverty isn't a virtue, it's a crucible.",
			"There should be an episode of EVERY SINGLE TELEVISION SERIES where the cast of Sliders runs past in the background.",
			"Marion isn't sure if the baby will need a playpen. I am certain I will paint \"FREE SPEECH ZONE\" on it if we do.",
			"It was a bright and clear night. The three suns that the black hole had ejected earlier in the day were worrying the dogs.",
			"My wife sometimes she assumes my hasty oversights are some sort of intentional fashion statement. I'll leave the house with my collar twisted and look like an idiot, because he assumes I think I am cool enough to pull it off.",
			"I need a toddler-sized ASBO jumpsuit for my daughter to wear when she's scrubbing her own Crayola graffiti off the walls.",
			"I keep seeing #scexit trying to be a thing. It'll never beat out #ScotstaLaVistaBritty"};
	
	public ItemColoredBook()
	{
		this.setMaxDamage(64);
		this.setCreativeTab(HermitQuest.hqTab);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand)
	{
		player.addChatComponentMessage(new TextComponentString(quotes[itemRand.nextInt(quotes.length)] + " - Joe Hills"));
		
		return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStack);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		tooltip.add("Right-click for Joe's wisdom.");
	}
}
