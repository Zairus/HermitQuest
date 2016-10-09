package zairus.hermitquest.hermits;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import zairus.hermitquest.sound.HQSoundEvents;
import zairus.hermitquest.storage.loot.HQLootTableList;

public enum Hermits
{
	XISUMA("Xisuma", HQSoundEvents.XISUMA_SAY, HQSoundEvents.XISUMA_HURT, HQSoundEvents.XISUMA_DIE, HQLootTableList.ENTITIES_BOSS_XISUMA)
	,ISKALL85("iskall85", HQSoundEvents.ISKALL_SAY, HQSoundEvents.ISKALL_HURT, HQSoundEvents.ISKALL_DIE, HQLootTableList.ENTITIES_BOSS_ISKALL85)
	,WELSKNIGHT("Welsknight", HQSoundEvents.WELSKNIGHT_SAY, HQSoundEvents.WELSKNIGHT_HURT, HQSoundEvents.WELSKNIGHT_DIE, HQLootTableList.ENTITIES_BOSS_WELSKNIGHT)
	,RENDOG("Renthedog", HQSoundEvents.RENDOG_SAY, HQSoundEvents.RENDOG_HURT, HQSoundEvents.RENDOG_DIE, HQLootTableList.ENTITIES_BOSS_RENDOG)
	,FALSESYMMETRY("falsesymmetry", HQSoundEvents.FALSESYMMETRY_SAY, HQSoundEvents.FALSESYMMETRY_HURT, HQSoundEvents.FALSESYMMETRY_DIE, HQLootTableList.ENTITIES_BOSS_FALSESYMMETRY)
	,BIFFA2001("Biffa2001", HQSoundEvents.BIFFA_SAY, HQSoundEvents.BIFFA_HURT, HQSoundEvents.BIFFA_DIE, HQLootTableList.ENTITIES_BOSS_BIFFA2001)
	,IJEVIN("ijevin", HQSoundEvents.IJEVIN_SAY, HQSoundEvents.IJEVIN_HURT, HQSoundEvents.IJEVIN_DIE, HQLootTableList.ENTITIES_BOSS_IJEVIN)
	,ZOMBIECLEO("ZombieCleo", HQSoundEvents.CLEO_SAY, HQSoundEvents.CLEO_HURT, HQSoundEvents.CLEO_DIE, HQLootTableList.ENTITIES_BOSS_ZOMBIECLEO)
	,KINGDADDYDMAC("KingDaddyDMAC", HQSoundEvents.KINGDADDYDMAC_SAY, HQSoundEvents.KINGDADDYDMAC_HURT, HQSoundEvents.KINGDADDYDMAC_DIE, HQLootTableList.ENTITIES_BOSS_KINGDADDYDMAC)
	,PYTHONGB("PythonGB", HQSoundEvents.PYTHON_SAY, HQSoundEvents.PYTHON_HURT, HQSoundEvents.PYTHON_DIE, HQLootTableList.ENTITIES_BOSS_PYTHONGB)
	,JOEHILLS("joehillssays", HQSoundEvents.JOEHILLS_SAY, HQSoundEvents.JOEHILLS_HURT, HQSoundEvents.JOEHILLS_DIE, HQLootTableList.ENTITIES_BOSS_JOEHILLS)
	,ZUELJIN("zueljin", HQSoundEvents.ZUELJIN_SAY, HQSoundEvents.ZUELJIN_HURT, HQSoundEvents.ZUELJIN_DIE, HQLootTableList.ENTITIES_BOSS_ZUELJIN)
	,GOODTIMESWITHSCAR("GoodtimewithScar", HQSoundEvents.SCAR_SAY, HQSoundEvents.SCAR_HURT, HQSoundEvents.SCAR_DIE, HQLootTableList.ENTITIES_BOSS_GOODTIMESWITHSCAR)
	,CUBFAN135("cubfan135", HQSoundEvents.CUBFAN_SAY, HQSoundEvents.CUBFAN_HURT, HQSoundEvents.CUBFAN_DIE, HQLootTableList.ENTITIES_BOSS_CUBFAN135)
	,XBCRAFTED("xBCrafted", HQSoundEvents.XBCRAFTED_SAY, HQSoundEvents.XBCRAFTED_HURT, HQSoundEvents.XBCRAFTED_DIE, HQLootTableList.ENTITIES_BOSS_XBCRADTED)
	,HYPNO("hypnotizd_", HQSoundEvents.HYPNO_SAY, HQSoundEvents.HYPNO_HURT, HQSoundEvents.HYPNO_DIE, HQLootTableList.ENTITIES_BOSS_HYPNO)
	,JESSASSIN("Jessassin", HQSoundEvents.JESSASSIN_SAY, HQSoundEvents.JESSASSIN_HURT, HQSoundEvents.JESSASSIN_DIE, HQLootTableList.ENTITIES_BOSS_JESSASSIN)
	
	,XISUMAVOID("xisumavoid", HQSoundEvents.XISUMA_SAY, HQSoundEvents.XISUMA_HURT, HQSoundEvents.XISUMA_DIE, HQLootTableList.ENTITIES_BOSS_XISUMA)
	,EVILX("EvilXisuma", HQSoundEvents.EVILXISUMA_SAY, HQSoundEvents.EVILXISUMA_HURT, HQSoundEvents.EVILXISUMA_DIE, HQLootTableList.ENTITIES_BOSS_XISUMA)
	
	;
	
	public String NAME;
	public SoundEvent AMBIENT_SOUND;
	public SoundEvent HURT_SOUND;
	public SoundEvent DEATH_SOUND;
	public ResourceLocation LOOT_TABLE;
	
	private Hermits(String name, SoundEvent ambientSound, SoundEvent hurtSound, SoundEvent deathSound, ResourceLocation lootTable)
	{
		this.NAME = name;
		this.AMBIENT_SOUND = ambientSound;
		this.HURT_SOUND = hurtSound;
		this.DEATH_SOUND = deathSound;
		this.LOOT_TABLE = lootTable;
	}
}
