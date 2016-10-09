package zairus.hermitquest.sound;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import zairus.hermitquest.HQConstants;

public class HQSoundEvents
{
	public static SoundEvent MUTE;
	public static SoundEvent XISUMA_SAY;
	public static SoundEvent XISUMA_HURT;
	public static SoundEvent XISUMA_DIE;
	public static SoundEvent EVILXISUMA_SAY;
	public static SoundEvent EVILXISUMA_HURT;
	public static SoundEvent EVILXISUMA_DIE;
	public static SoundEvent ISKALL_SAY;
	public static SoundEvent ISKALL_HURT;
	public static SoundEvent ISKALL_DIE;
	public static SoundEvent WELSKNIGHT_SAY;
	public static SoundEvent WELSKNIGHT_HURT;
	public static SoundEvent WELSKNIGHT_DIE;
	public static SoundEvent RENDOG_SAY;
	public static SoundEvent RENDOG_HURT;
	public static SoundEvent RENDOG_DIE;
	public static SoundEvent FALSESYMMETRY_SAY;
	public static SoundEvent FALSESYMMETRY_HURT;
	public static SoundEvent FALSESYMMETRY_DIE;
	public static SoundEvent BIFFA_SAY;
	public static SoundEvent BIFFA_HURT;
	public static SoundEvent BIFFA_DIE;
	public static SoundEvent IJEVIN_SAY;
	public static SoundEvent IJEVIN_HURT;
	public static SoundEvent IJEVIN_DIE;
	public static SoundEvent CLEO_SAY;
	public static SoundEvent CLEO_HURT;
	public static SoundEvent CLEO_DIE;
	public static SoundEvent KINGDADDYDMAC_SAY;
	public static SoundEvent KINGDADDYDMAC_HURT;
	public static SoundEvent KINGDADDYDMAC_DIE;
	public static SoundEvent PYTHON_SAY;
	public static SoundEvent PYTHON_HURT;
	public static SoundEvent PYTHON_DIE;
	public static SoundEvent JOEHILLS_SAY;
	public static SoundEvent JOEHILLS_HURT;
	public static SoundEvent JOEHILLS_DIE;
	public static SoundEvent ZUELJIN_SAY;
	public static SoundEvent ZUELJIN_HURT;
	public static SoundEvent ZUELJIN_DIE;
	public static SoundEvent SCAR_SAY;
	public static SoundEvent SCAR_HURT;
	public static SoundEvent SCAR_DIE;
	public static SoundEvent CUBFAN_SAY;
	public static SoundEvent CUBFAN_HURT;
	public static SoundEvent CUBFAN_DIE;
	public static SoundEvent XBCRAFTED_SAY;
	public static SoundEvent XBCRAFTED_HURT;
	public static SoundEvent XBCRAFTED_DIE;
	public static SoundEvent HYPNO_SAY;
	public static SoundEvent HYPNO_HURT;
	public static SoundEvent HYPNO_DIE;
	public static SoundEvent JESSASSIN_SAY;
	public static SoundEvent JESSASSIN_HURT;
	public static SoundEvent JESSASSIN_DIE;
	
	public static SoundEvent ROCK_CRACK;
	public static SoundEvent ITEMUP;
	public static SoundEvent BEAM_SHOT;
	public static SoundEvent BEAM_HIT;
	public static SoundEvent POOP_SPLAT;
	public static SoundEvent FIREBALL_SHOOT;
	public static SoundEvent LEAVES_HISS;
	public static SoundEvent LADY_ZOMBIE_SAY;
	public static SoundEvent LADY_ZOMBIE_HURT;
	public static SoundEvent LADY_ZOMBIE_DIE;
	public static SoundEvent JELLIE_PURR;
	
	public static SoundEvent registerSound(ResourceLocation location)
	{
		SoundEvent sound = new SoundEvent(location).setRegistryName(location);
		GameRegistry.register(sound);
		return sound;
	}
	
	private static SoundEvent registerSound(String location)
	{
		return registerSound(new ResourceLocation(HQConstants.MODID, location));
	}
	
	public static void register()
	{
		MUTE = registerSound("mute");
		XISUMA_SAY = registerSound("xisuma_idle");
		XISUMA_HURT = registerSound("xisuma_hurt");
		XISUMA_DIE = registerSound("xisuma_die");
		EVILXISUMA_SAY = registerSound("evilxisuma_idle");
		EVILXISUMA_HURT = registerSound("evilxisuma_hurt");
		EVILXISUMA_DIE = registerSound("evilxisuma_die");
		ISKALL_SAY = registerSound("iskall_say");
		ISKALL_HURT = registerSound("iskall_hurt");
		ISKALL_DIE = registerSound("iskall_die");
		WELSKNIGHT_SAY = registerSound("wels_say");
		WELSKNIGHT_HURT = registerSound("wels_hurt");
		WELSKNIGHT_DIE = registerSound("wels_die");
		RENDOG_SAY = registerSound("rendog_say");
		RENDOG_HURT = registerSound("rendog_hurt");
		RENDOG_DIE = registerSound("rendog_die");
		FALSESYMMETRY_SAY = registerSound("false_say");
		FALSESYMMETRY_HURT = registerSound("false_hurt");
		FALSESYMMETRY_DIE = registerSound("false_die");
		BIFFA_SAY = registerSound("biffa_say");
		BIFFA_HURT = registerSound("biffa_hurt");
		BIFFA_DIE = registerSound("biffa_die");
		IJEVIN_SAY = MUTE;
		IJEVIN_HURT = SoundEvents.ENTITY_PLAYER_HURT;
		IJEVIN_DIE = SoundEvents.ENTITY_PLAYER_DEATH;
		CLEO_SAY = registerSound("cleo_say");
		CLEO_HURT = registerSound("cleo_hurt");
		CLEO_DIE = registerSound("cleo_die");
		KINGDADDYDMAC_SAY = MUTE;
		KINGDADDYDMAC_HURT = SoundEvents.ENTITY_PLAYER_HURT;
		KINGDADDYDMAC_DIE = SoundEvents.ENTITY_PLAYER_DEATH;
		PYTHON_SAY = registerSound("python_say");
		PYTHON_HURT = registerSound("python_hurt");
		PYTHON_DIE = registerSound("python_die");
		JOEHILLS_SAY = registerSound("joe_say");
		JOEHILLS_HURT = registerSound("joe_hurt");
		JOEHILLS_DIE = JOEHILLS_HURT;
		ZUELJIN_SAY = registerSound("zueljin_say");
		ZUELJIN_HURT = registerSound("zueljin_hurt");
		ZUELJIN_DIE = registerSound("zueljin_die");
		SCAR_SAY = registerSound("scar_say");
		SCAR_HURT = registerSound("scar_hurt");
		SCAR_DIE = registerSound("scar_die");
		CUBFAN_SAY = registerSound("cubfan_say");
		CUBFAN_HURT = registerSound("cubfan_hurt");
		CUBFAN_DIE = registerSound("cubfan_die");
		XBCRAFTED_SAY = MUTE;
		XBCRAFTED_HURT = SoundEvents.ENTITY_PLAYER_HURT;
		XBCRAFTED_DIE = SoundEvents.ENTITY_PLAYER_DEATH;
		HYPNO_SAY = MUTE;
		HYPNO_HURT = SoundEvents.ENTITY_PLAYER_HURT;
		HYPNO_DIE = SoundEvents.ENTITY_PLAYER_DEATH;
		JESSASSIN_SAY = MUTE;
		JESSASSIN_HURT = SoundEvents.ENTITY_PLAYER_HURT;
		JESSASSIN_DIE = SoundEvents.ENTITY_PLAYER_DEATH;
		
		ROCK_CRACK = registerSound("rock_crack");
		ITEMUP = registerSound("itemup");
		BEAM_SHOT = registerSound("beam_shot");
		BEAM_HIT = registerSound("beam_hit");
		POOP_SPLAT = registerSound("poop_splat");
		FIREBALL_SHOOT = registerSound("fireball_shoot");
		LEAVES_HISS = registerSound("leaves_hiss");
		LADY_ZOMBIE_SAY = registerSound("lady_zombie_say");
		LADY_ZOMBIE_HURT = registerSound("lady_zombie_hurt");
		LADY_ZOMBIE_DIE = registerSound("lady_zombie_die");
		JELLIE_PURR = registerSound("jellie_purr");
	}
}
