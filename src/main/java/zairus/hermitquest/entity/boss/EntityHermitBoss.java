package zairus.hermitquest.entity.boss;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.ZombieType;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.hermitquest.HQConstants;
import zairus.hermitquest.entity.EntityAngryVillager;
import zairus.hermitquest.entity.EntityDiabloElPollo;
import zairus.hermitquest.entity.EntityJevinSlime;
import zairus.hermitquest.entity.EntitySmallSnake;
import zairus.hermitquest.entity.ai.EntityAIHermitBossAttack;
import zairus.hermitquest.entity.ai.EntityAIHermitBossRanged;
import zairus.hermitquest.entity.projectile.EntityDioriteArrow;
import zairus.hermitquest.entity.projectile.EntityIskallBeam;
import zairus.hermitquest.entity.projectile.EntityThrowableBook;
import zairus.hermitquest.entity.projectile.EntityThrowableGoldenApple;
import zairus.hermitquest.entity.projectile.EntityThrowableTorch;
import zairus.hermitquest.hermits.Hermits;
import zairus.hermitquest.item.ItemHermitBanner;
import zairus.hermitquest.item.ItemImpFireball;
import zairus.hermitquest.potion.HQPotionType;
import zairus.hermitquest.sound.HQSoundEvents;

public class EntityHermitBoss extends EntityCreature implements IRangedAttackMob
{
	public float renderOffsetX;
    @SideOnly(Side.CLIENT)
    public float renderOffsetY;
    public float renderOffsetZ;
    
    private static final DataParameter<Integer> HERMIT_INDEX = EntityDataManager.<Integer>createKey(EntityHermitBoss.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> SHOOTING_TIME = EntityDataManager.<Integer>createKey(EntityHermitBoss.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> TARGET_ENTITY = EntityDataManager.<Integer>createKey(EntityHermitBoss.class, DataSerializers.VARINT); 
    
    private final EntityAIHermitBossAttack hermitBossStandardAttack = new EntityAIHermitBossAttack(this, 1.5D, false);
    private final EntityAIHermitBossRanged rangedAttack = new EntityAIHermitBossRanged(this, 1.0D, 20, 15.0F);
    
    private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(this.getBossName(), BossInfo.Color.GREEN, BossInfo.Overlay.PROGRESS));
    
    private Hermits hermit;
    private int livingTicks;
    private int shootingTime = 0;
    private int shootingTimeLast = 0;
    private int minionCount = 0;
    private boolean vulnerable = false;
    private int lastVulnerable = 0;
    
    private int targetAirTicks = 200;
    private int targetDrownTicks = 20;
    
    protected boolean isImmuneToFire = false;
    
    public EntityHermitBoss(World world)
	{
		this(world, Hermits.XISUMA);
	}
    
    public EntityHermitBoss(World world, Hermits h)
    {
    	super(world);
    	this.hermit = h;
    	this.setSize(0.6F, 1.95F);
    	((PathNavigateGround)this.getNavigator()).setBreakDoors(true);
    	this.setCanPickUpLoot(true);
    	this.enablePersistence();
    	
    	this.setActiveHand(EnumHand.MAIN_HAND);
    	this.activeItemStack = new ItemStack(Items.DIAMOND_SWORD);
    }
    
    @Override
    @Nullable
    public ItemStack getHeldItemMainhand()
    {
    	return new ItemStack(Items.DIAMOND_SWORD);
    }
    
    @Override
    @Nullable
    public ItemStack getHeldItemOffhand()
    {
    	return new ItemStack(Items.DIAMOND_AXE);
    }
    
	public void setHermit(Hermits h)
	{
		this.hermit = h;
		this.getDataManager().set(HERMIT_INDEX, Integer.valueOf(h.ordinal()));
	}
	
	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.getDataManager().register(HERMIT_INDEX, Integer.valueOf(0));
		this.getDataManager().register(SHOOTING_TIME, Integer.valueOf(0));
		this.getDataManager().register(TARGET_ENTITY, Integer.valueOf(0));
	}
	
	@Override
	public void notifyDataManagerChange(DataParameter<?> key)
	{
		if (HERMIT_INDEX.equals(key))
			this.setHermit(Hermits.values()[this.getDataManager().get(HERMIT_INDEX).intValue()]);
		
		if (SHOOTING_TIME.equals(key))
			this.shootingTime = this.dataManager.get(SHOOTING_TIME).intValue();
	}
	
	private void setTargetEntity(int entityId)
	{
		this.getDataManager().set(TARGET_ENTITY, Integer.valueOf(entityId));
	}
	
	@Nullable
	private Entity getTargetedEntity()
	{
		return this.worldObj.getEntityByID(this.getDataManager().get(TARGET_ENTITY).intValue());
	}
	
	@Override
	protected void initEntityAI()
	{
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIMoveIndoors(this));
		this.tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
		this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 12.0F));
		this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		
		this.applyEntityAI();
	}
	
	protected void applyEntityAI()
	{
		this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {this.getClass()}));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
	}
	
	@Override
	@Nullable
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
	{
		livingdata = super.onInitialSpawn(difficulty, livingdata);
		
		changeCombat(true);
		
		return livingdata;
	}
	
	@SuppressWarnings("deprecation")
	private void changeCombat(boolean standard)
	{
		this.tasks.removeTask(hermitBossStandardAttack);
		this.tasks.removeTask(rangedAttack);
		
		switch(this.hermit)
		{
		case XISUMA:
		case ISKALL85:
		case XBCRAFTED:
		case CUBFAN135:
			if (this.rand.nextInt(6) == 0 && !standard)
			{
				this.shootingTime = 1;
				this.dataManager.set(SHOOTING_TIME, Integer.valueOf(1));
				this.tasks.addTask(2, this.rangedAttack);
			}
			else
			{
				this.shootingTime = 0;
				this.dataManager.set(SHOOTING_TIME, Integer.valueOf(0));
				this.shootingTimeLast = 0;
				this.tasks.addTask(2, this.hermitBossStandardAttack);
			}
			break;
		case IJEVIN:
			if (this.rand.nextInt(7) == 0)
			{
				EntityJevinSlime slime = new EntityJevinSlime(this.worldObj);
				prepareMinion(slime);
				
				slime.setSize(3);
				slime.setAIMoveSpeed(8.0F);
				
				this.worldObj.spawnEntityInWorld(slime);
			}
			this.tasks.addTask(2, this.hermitBossStandardAttack);
			break;
		case BIFFA2001:
			if (this.rand.nextInt(7) == 0)
			{
				EntityWolf wolf = new EntityWolf(this.worldObj);
				prepareMinion(wolf);
				
				wolf.setAngry(true);
				wolf.setAttackTarget(this.getAttackTarget());
				wolf.setLastAttacker(this.getLastAttacker());
				
				this.worldObj.spawnEntityInWorld(wolf);
			}
			this.tasks.addTask(2, this.hermitBossStandardAttack);
			break;
		case ZOMBIECLEO:
		case WELSKNIGHT:
			if (this.rand.nextInt(7) == 0)
			{
				EntityZombie miniHusk = new EntityZombie(this.worldObj);
				prepareMinion(miniHusk);
				
				miniHusk.func_189778_a(ZombieType.HUSK);
				
				if (this.hermit == Hermits.WELSKNIGHT)
				{
					List<ItemStack> headGear = new ArrayList<ItemStack>();
					headGear.add(new ItemStack(Items.LEATHER_HELMET));
					headGear.add(new ItemStack(Items.IRON_HELMET));
					headGear.add(new ItemStack(Items.GOLDEN_HELMET));
					headGear.add(new ItemStack(Items.CHAINMAIL_HELMET));
					headGear.add(new ItemStack(Items.DIAMOND_HELMET));
					
					List<ItemStack> chestGear = new ArrayList<ItemStack>();
					chestGear.add(new ItemStack(Items.LEATHER_CHESTPLATE));
					chestGear.add(new ItemStack(Items.IRON_CHESTPLATE));
					chestGear.add(new ItemStack(Items.GOLDEN_CHESTPLATE));
					chestGear.add(new ItemStack(Items.CHAINMAIL_CHESTPLATE));
					chestGear.add(new ItemStack(Items.DIAMOND_CHESTPLATE));
					
					List<ItemStack> legGear = new ArrayList<ItemStack>();
					legGear.add(new ItemStack(Items.LEATHER_LEGGINGS));
					legGear.add(new ItemStack(Items.IRON_LEGGINGS));
					legGear.add(new ItemStack(Items.GOLDEN_LEGGINGS));
					legGear.add(new ItemStack(Items.CHAINMAIL_LEGGINGS));
					legGear.add(new ItemStack(Items.DIAMOND_LEGGINGS));
					
					List<ItemStack> footGear = new ArrayList<ItemStack>();
					footGear.add(new ItemStack(Items.LEATHER_BOOTS));
					footGear.add(new ItemStack(Items.IRON_BOOTS));
					footGear.add(new ItemStack(Items.GOLDEN_BOOTS));
					footGear.add(new ItemStack(Items.CHAINMAIL_BOOTS));
					footGear.add(new ItemStack(Items.DIAMOND_BOOTS));
					
					List<ItemStack> handGear = new ArrayList<ItemStack>();
					handGear.add(new ItemStack(Items.WOODEN_SWORD));
					handGear.add(new ItemStack(Items.STONE_SWORD));
					handGear.add(new ItemStack(Items.IRON_SWORD));
					handGear.add(new ItemStack(Items.GOLDEN_SWORD));
					handGear.add(new ItemStack(Items.DIAMOND_SWORD));
					
					miniHusk.setItemStackToSlot(EntityEquipmentSlot.HEAD, headGear.get(this.rand.nextInt(headGear.size())));
					miniHusk.setItemStackToSlot(EntityEquipmentSlot.CHEST, chestGear.get(this.rand.nextInt(chestGear.size())));
					miniHusk.setItemStackToSlot(EntityEquipmentSlot.LEGS, legGear.get(this.rand.nextInt(legGear.size())));
					miniHusk.setItemStackToSlot(EntityEquipmentSlot.FEET, footGear.get(this.rand.nextInt(footGear.size())));
					miniHusk.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, handGear.get(this.rand.nextInt(handGear.size())));
				}
				else
				{
					miniHusk.setChild(true);
				}
				
				this.worldObj.spawnEntityInWorld(miniHusk);
			}
			this.tasks.addTask(2, this.hermitBossStandardAttack);
			break;
		case KINGDADDYDMAC:
			if (this.rand.nextInt(7) == 0)
			{
				EntityAngryVillager villager = new EntityAngryVillager(this.worldObj);
				prepareMinion(villager);
				
				this.worldObj.spawnEntityInWorld(villager);
			}
			this.tasks.addTask(2, this.hermitBossStandardAttack);
			break;
		case PYTHONGB:
			if (this.rand.nextInt(6) == 0)
			{
				EntitySmallSnake snake = new EntitySmallSnake(this.worldObj);
				prepareMinion(snake);
				
				this.worldObj.spawnEntityInWorld(snake);
			}
			this.tasks.addTask(2, this.hermitBossStandardAttack);
			break;
		case FALSESYMMETRY:
			this.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10, 2));
			this.tasks.addTask(2, this.hermitBossStandardAttack);
			break;
		case RENDOG:
			if(minionCount < 9 && this.getAITarget() != null)
			{
				EntityDiabloElPollo diablo = new EntityDiabloElPollo(this.worldObj, this);
				prepareMinion(diablo);
				
				this.worldObj.spawnEntityInWorld(diablo);
			}
		case JOEHILLS:
			if (this.rand.nextInt(10) == 0)
			{
				bossTeleport();
			}
			this.tasks.addTask(2, this.hermitBossStandardAttack);
			break;
		case ZUELJIN:
			if (this.rand.nextInt(3) == 0)
			{
				if (this.getAITarget() != null)
				{
					EntityLivingBase target = this.getAITarget();
					
					double d0 = target.posY + (double)target.getEyeHeight() - 1.100000023841858D;
					double d1 = target.posX + target.motionX - this.posX;
					double d2 = d0 - this.posY;
					double d3 = target.posZ + target.motionZ - this.posZ;
		            float f = MathHelper.sqrt_double(d1 * d1 + d3 * d3);
		            
					PotionType potiontype = PotionTypes.SLOWNESS;
					EntityPotion entitypotion = new EntityPotion(this.worldObj, this, PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), potiontype));
					entitypotion.rotationPitch -= -20.0F;
					entitypotion.setThrowableHeading(d1, d2 + (double)(f * 0.2F), d3, 0.75F, 8.0F);
					this.worldObj.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_WITCH_THROW, this.getSoundCategory(), 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);
					this.worldObj.spawnEntityInWorld(entitypotion);
				}
			}
			this.tasks.addTask(2, this.hermitBossStandardAttack);
			break;
		case HYPNO:
			if (this.rand.nextInt(4) == 0)
			{
				EntityLivingBase target = this.getAITarget();
				
				if (target != null)
				{
					double d0 = target.posY + (double)target.getEyeHeight() - 1.100000023841858D;
					double d1 = target.posX + target.motionX - this.posX;
					double d2 = d0 - this.posY;
					double d3 = target.posZ + target.motionZ - this.posZ;
		            float f = MathHelper.sqrt_double(d1 * d1 + d3 * d3);
		            
					Potion pot = MobEffects.NAUSEA;
		            PotionEffect ef = new PotionEffect(pot, 400, 1, true, false);
		            List<PotionEffect> effects = new ArrayList<PotionEffect>();
		            effects.add(ef);
		            ItemStack potionStack = new ItemStack(Items.SPLASH_POTION);
		            PotionUtils.appendEffects(potionStack, effects);
		            
		            PotionType potiontype = PotionTypes.WEAKNESS;
		            EntityPotion entitypotion = new EntityPotion(this.worldObj, this, PotionUtils.addPotionToItemStack(potionStack, potiontype));
					entitypotion.rotationPitch -= -20.0F;
					entitypotion.setThrowableHeading(d1, d2 + (double)(f * 0.2F), d3, 0.75F, 8.0F);
					this.worldObj.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_WITCH_THROW, this.getSoundCategory(), 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);
					this.worldObj.spawnEntityInWorld(entitypotion);
				}
			}
			this.tasks.addTask(2, this.hermitBossStandardAttack);
			break;
		case GOODTIMESWITHSCAR:
			if (this.rand.nextInt(4) == 0)
			{
				BlockPos leavesPos = new BlockPos(this.posX + this.getLookVec().xCoord, this.posY, this.posZ + this.getLookVec().zCoord);
				
				if (!this.worldObj.isAirBlock(leavesPos))
					leavesPos = leavesPos.up();
				
				if (this.worldObj.isAirBlock(leavesPos))
				{
					List<IBlockState> leaves = new ArrayList<IBlockState>();
					leaves.add(Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.OAK));
					leaves.add(Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.BIRCH));
					leaves.add(Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.SPRUCE));
					leaves.add(Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.JUNGLE));
					leaves.add(Blocks.LEAVES2.getDefaultState().withProperty(BlockNewLeaf.VARIANT, BlockPlanks.EnumType.ACACIA));
					leaves.add(Blocks.LEAVES2.getDefaultState().withProperty(BlockNewLeaf.VARIANT, BlockPlanks.EnumType.DARK_OAK));
					
					this.worldObj.setBlockState(leavesPos, leaves.get(this.rand.nextInt(leaves.size())));
					this.swingArm(getActiveHand());
					
					if (this.rand.nextInt(2) == 0 && this.worldObj.isAirBlock(leavesPos.up()))
					{
						this.worldObj.setBlockState(leavesPos.up(), leaves.get(this.rand.nextInt(leaves.size())));
						this.swingArm(getActiveHand());
					}
				}
			}
			this.tasks.addTask(2, this.hermitBossStandardAttack);
			break;
		case JESSASSIN:
			if (this.getAITarget() != null)
			{
				if (this.getAITarget() instanceof EntityPlayer)
				{
					this.getDataManager().set(TARGET_ENTITY, this.getAITarget().getEntityId());
					
					if (targetAirTicks == 200)
						((EntityPlayer)this.getAITarget()).addChatComponentMessage(new TextComponentString("<Jessassin> You're about to drown >:D"));
				}
			}
			this.tasks.addTask(2, this.hermitBossStandardAttack);
			break;
		default:
			this.tasks.addTask(2, this.hermitBossStandardAttack);
			break;
		}
	}
	
	private void bossTeleport()
	{
		this.attemptTeleport(this.posX + (6 - this.rand.nextInt(13)), this.posY, this.posZ + (6 - this.rand.nextInt(13)));
		this.worldObj.playSound((EntityPlayer)null, this.prevPosX, this.prevPosY, this.prevPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F);
        this.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
	}
	
	private void prepareMinion(EntityLivingBase minion)
	{
		minion.setPosition(this.posX, this.posY, this.posZ);
		minion.motionX = this.getLookVec().xCoord;
		minion.motionY = 0.6D;
		minion.motionZ = this.getLookVec().zCoord;
		++minionCount;
	}
	
	public void decreaseMinion()
	{
		--minionCount;
	}
	
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(180.0D);
	}
	
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		
		this.bossInfo.setName(getBossName());
		this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
		
		if (this.getDataManager().get(HERMIT_INDEX).intValue() == Hermits.JESSASSIN.ordinal())
		{
			Entity p = this.worldObj.getEntityByID(this.getDataManager().get(TARGET_ENTITY).intValue());
			if (p != null)
			{
				if (p instanceof EntityPlayer)
				{
					EntityPlayer player = (EntityPlayer)p;
					
					if (this.targetAirTicks > 0)
					{
						--this.targetAirTicks;
					}
					else
					{
						--this.targetDrownTicks;
						if (this.targetDrownTicks < 0)
						{
							this.targetDrownTicks = 20;
							player.attackEntityFrom(DamageSource.drown, 1.0F);
						}
					}
				}
			}
		}
		
		if (
				this.getDataManager().get(HERMIT_INDEX).intValue() == Hermits.XBCRAFTED.ordinal()
				|| this.getDataManager().get(HERMIT_INDEX).intValue() == Hermits.CUBFAN135.ordinal())
		{
			if (this.getDataManager().get(SHOOTING_TIME).intValue() > 0 && this.getTargetedEntity() != null)
			{
				EnumParticleTypes particle;
				SoundEvent sound;
				
				if (this.getDataManager().get(HERMIT_INDEX).intValue() == Hermits.XBCRAFTED.ordinal())
				{
					particle = EnumParticleTypes.WATER_BUBBLE;
					sound = SoundEvents.ENTITY_GUARDIAN_ATTACK;
				}
				else
				{
					particle = EnumParticleTypes.SMOKE_NORMAL;
					sound = SoundEvents.ENTITY_FIREWORK_LAUNCH;
				}
				
				Entity entity = this.getTargetedEntity();
				this.getLookHelper().setLookPositionWithEntity(entity, 90.0F, 90.0F);
				this.getLookHelper().onUpdateLook();
				double d5 = (double)((float)this.shootingTime / 120.0F);
				double d0 = entity.posX - this.posX;
				double d1 = entity.posY + (double)(entity.height * 0.5F) - (this.posY + (double)this.getEyeHeight());
				double d2 = entity.posZ - this.posZ;
				double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
				d0 = d0 / d3;
				d1 = d1 / d3;
				d2 = d2 / d3;
				double d4 = this.rand.nextDouble();
				
				this.playSound(sound, 1.0F, 1.0F);
				
				while (d4 < d3)
				{
					d4 += 1.8D - d5 + this.rand.nextDouble() * (1.7D - d5);
					
					this.worldObj.spawnParticle(
							particle, 
							this.posX + d0 * d4, 
							this.posY + d1 * d4 + (double)this.getEyeHeight(), 
							this.posZ + d2 * d4, 
							0.0D, 
							0.0D, 
							0.0D, 
							new int[0]);
				}
			}
		}
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		
		++this.livingTicks;
		
		if (this.shootingTime > 0)
		{
			++this.shootingTime;
			this.dataManager.set(SHOOTING_TIME, Integer.valueOf(this.shootingTime));
		}
		
		if (this.hermit == Hermits.IJEVIN || this.hermit == Hermits.HYPNO)
		{
			List<EntityPotion> potList = this.worldObj.<EntityPotion>getEntitiesWithinAABB(EntityPotion.class, this.getEntityBoundingBox().expandXyz(1.0D));
			
			for (EntityPotion pot : potList)
			{
				ItemStack itemstack = pot.getPotion();
				PotionType potiontype = PotionUtils.getPotionFromItem(itemstack);
				
				if (potiontype.getNamePrefixed("").contains("water"))
				{
					EntityDamageSource ds = new EntityDamageSource("water", pot.getThrower());
					
					this.attackEntityFrom(ds, 1.0F);
				}
			}
		}
		else if (this.hermit == Hermits.ZOMBIECLEO)
		{
			List<EntityPotion> potList = this.worldObj.<EntityPotion>getEntitiesWithinAABB(EntityPotion.class, this.getEntityBoundingBox().expandXyz(1.0D));
			
			for (EntityPotion pot : potList)
			{
				ItemStack itemstack = pot.getPotion();
				PotionType potiontype = PotionUtils.getPotionFromItem(itemstack);
				
				if (potiontype.getNamePrefixed("").contains("healing"))
				{
					EntityDamageSource ds = new EntityDamageSource("healing", pot.getThrower());
					
					this.attackEntityFrom(ds, 1.0F);
				}
			}
		}
	}
	
	@Override
	protected void updateAITasks()
	{
		super.updateAITasks();
		
		List<EntityPlayer> list = this.worldObj.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().expand(20.0D, 8.0D, 20.0D), Predicates.<EntityPlayer>and(EntitySelectors.IS_ALIVE, EntitySelectors.NOT_SPECTATING));
		
		for (int i = 0; i < 10 && !list.isEmpty(); ++i)
		{
			EntityPlayer player = (EntityPlayer)list.get(this.rand.nextInt(list.size()));
			
			if (player != null && player.isEntityAlive() && this.canEntityBeSeen(player))
			{
				if (!player.capabilities.disableDamage && !player.capabilities.isCreativeMode)
					this.setAttackTarget(player);
				
				break;
			}
			
			list.remove(player);
		}
		
		int shootingTimer = 80;
		
		if (this.hermit == Hermits.XBCRAFTED)
			shootingTimer = 120;
		
		if (this.hermit == Hermits.CUBFAN135)
			shootingTimer = 10;
		
		if (this.rand.nextInt(12) == 0 && (this.shootingTime > shootingTimer + this.rand.nextInt(shootingTimer) || this.shootingTime == 0))
			changeCombat(false);
	}
	
	@Override
	public void addTrackingPlayer(EntityPlayerMP player)
	{
		super.addTrackingPlayer(player);
		this.bossInfo.addPlayer(player);
	}
	
	@Override
	public void removeTrackingPlayer(EntityPlayerMP player)
	{
		super.removeTrackingPlayer(player);
		this.bossInfo.removePlayer(player);
	}
	
	@Override
	protected int getExperiencePoints(EntityPlayer player)
	{
		return 150;
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		float damageFactor = 0.05F;
		float damage;
		Entity sourceEntity = source.getEntity();
		
		if (this.hermit == Hermits.XISUMA)
		{
			if (sourceEntity != null && sourceEntity instanceof EntityLivingBase)
			{
				ItemStack heldStack = ((EntityLivingBase)sourceEntity).getHeldItemMainhand();
				
				if (heldStack == null)
					damageFactor = 4.0F + (rand.nextFloat() * 4.0F);
			}
		}
		else if (this.hermit == Hermits.ISKALL85)
		{
			if (source.getSourceOfDamage() != null && source.getSourceOfDamage() instanceof EntityDioriteArrow)
			{
				damageFactor = 1.0F;
			}
		}
		else if (this.hermit == Hermits.FALSESYMMETRY)
		{
			if (source.getSourceOfDamage() != null && source.getSourceOfDamage() instanceof EntityThrowableGoldenApple)
			{
				amount = 6.0F;
				damageFactor = 1.0F;
			}
		}
		else if(this.hermit == Hermits.RENDOG)
		{
			if (sourceEntity != null)
			{
				for (ItemStack heldStack : sourceEntity.getHeldEquipment())
				{
					if (heldStack != null && heldStack.getItem() == Items.DIAMOND_AXE)
						damageFactor = 1.0F;
				}
			}
		}
		else if (this.hermit == Hermits.IJEVIN || this.hermit == Hermits.HYPNO)
		{
			if (source.getDamageType() == "water")
			{
				damageFactor = 12.0F;
			}
		}
		else if (this.hermit == Hermits.ZOMBIECLEO)
		{
			if (source.getDamageType() == "healing")
			{
				damageFactor = 22.0F;
			}
		}
		else if (this.hermit == Hermits.CUBFAN135)
		{
			if (sourceEntity != null)
			{
				for (ItemStack heldStack : sourceEntity.getHeldEquipment())
				{
					if (heldStack != null && heldStack.getItem() == Items.SHEARS && sourceEntity instanceof EntityLivingBase)
					{
						heldStack.damageItem(2, (EntityLivingBase)sourceEntity);
						damageFactor = 4.0F + (rand.nextFloat() * 4.0F);
					}
				}
			}
		}
		else if (this.hermit == Hermits.KINGDADDYDMAC)
		{
			if (sourceEntity != null)
			{
				List<Item> weaknessItem = new ArrayList<Item>();
				weaknessItem.add(Items.GOLDEN_AXE);
				weaknessItem.add(Items.GOLDEN_SHOVEL);
				weaknessItem.add(Items.GOLDEN_PICKAXE);
				weaknessItem.add(Items.GOLDEN_SWORD);
				
				for (ItemStack heldStack : sourceEntity.getHeldEquipment())
				{
					if (heldStack != null && weaknessItem.contains(heldStack.getItem()))
					{
						damageFactor = 1.0F;
					}
				}
			}
		}
		else if (this.hermit == Hermits.PYTHONGB)
		{
			if (this.vulnerable)
			{
				if (source.damageType == "fire")
					damageFactor = 1.0F;
				else
					damageFactor = 0.7F;
				
				if (this.livingTicks - this.lastVulnerable > 200)
				{
					this.vulnerable = false;
				}
			}
		}
		else if (this.hermit == Hermits.WELSKNIGHT)
		{
			if (source.getSourceOfDamage() != null && source.getSourceOfDamage() instanceof EntityArrow)
			{
				damageFactor = 1.0F;
			}
		}
		else if (this.hermit == Hermits.XBCRAFTED)
		{
			if (source.getEntity() != null)
				source.getEntity().attackEntityFrom(DamageSource.causeThornsDamage(this), 2.0F);
			
			if (source == DamageSource.lava)
			{
				damageFactor = 1.0F;
			}
		}
		else if (this.hermit == Hermits.ZUELJIN)
		{
			if (this.isInWeb)
			{
				damageFactor = 0.8F;
			}
		}
		else if (this.hermit == Hermits.JOEHILLS)
		{
			if (this.rand.nextInt(4) == 0)
			{
				bossTeleport();
			}
			else
			{
				if (source.getSourceOfDamage() != null && source.getSourceOfDamage() instanceof EntityThrowableBook)
				{
					amount = 6.0F;
					damageFactor = 1.0F;
				}
			}
		}
		else if (this.hermit == Hermits.JESSASSIN)
		{
			if (source.getSourceOfDamage() != null && source.getSourceOfDamage() instanceof EntityThrowableTorch)
			{
				amount = 6.0F;
				damageFactor = 1.0F;
			}
		}
		else if (this.hermit == Hermits.BIFFA2001)
		{
			for (PotionEffect effect : this.getActivePotionEffects())
			{
				if (effect.getPotion() == HQPotionType.EFFECT_RELAXING)
				{
					damageFactor = 1.0F;
				}
			}
		}
		else if (this.hermit == Hermits.GOODTIMESWITHSCAR)
		{
			AxisAlignedBB axis = new AxisAlignedBB(new BlockPos(this.posX, this.posY, this.posZ));
			List<EntityOcelot> cats = this.worldObj.<EntityOcelot>getEntitiesWithinAABB(EntityOcelot.class, axis.expand(8.0D, 4.0D, 8.0D));
			if (cats.size() > 0)
			{
				if (!this.worldObj.isRemote && source.getEntity() instanceof EntityPlayer)
				{
					((EntityPlayer)source.getEntity()).addChatComponentMessage(new TextComponentString("<GoodTimesWithScar> I can't defend my self when pretty little kitties are around! :S"));
				}
				damageFactor = 1.0F;
			}
		}
		else
		{
			if (sourceEntity != null)
			{
				for (ItemStack heldStack : sourceEntity.getHeldEquipment())
				{
					if (heldStack != null && heldStack.getItem() == Items.DIAMOND_AXE)
						damageFactor = 2.0F;
				}
			}
		}
		
		damage = amount * damageFactor;
		
		if (super.attackEntityFrom(source, damage))
		{
			boolean flag = true;
			
			if (sourceEntity instanceof EntityPlayer)
			{
				if (((EntityPlayer)sourceEntity).capabilities.isCreativeMode)
					flag = false;
			}
			
			if (!(sourceEntity instanceof EntityLivingBase))
				flag = false;
			
			if (flag)
				this.setLastAttacker(sourceEntity);
			
			return true;
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		float f = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
		
		if (this.hermit == Hermits.XISUMA)
		{
			f += 16.0F;
		}
		
		DamageSource source = DamageSource.causeMobDamage(this);
		boolean flag = entity.attackEntityFrom(source, f);
		
		if (flag)
		{
			if (this.isBurning())
			{
				entity.setFire(11);
			}
			
			if (entity instanceof EntityPlayer)
			{
				EntityPlayer entityplayer = (EntityPlayer)entity;
				ItemStack itemstack = this.getHeldItemMainhand();
				ItemStack itemstack1 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : null;
				
				if (itemstack != null && itemstack1 != null && itemstack.getItem() instanceof ItemAxe && itemstack1.getItem() == Items.SHIELD)
				{
					float f1 = 0.25F + (float)EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;
					
					if (this.rand.nextFloat() < f1)
					{
						entityplayer.getCooldownTracker().setCooldown(Items.SHIELD, 100);
						this.worldObj.setEntityState(entityplayer, (byte)30);
					}
				}
			}
			
			this.applyEnchantments(this, entity);
			this.swingArm(EnumHand.MAIN_HAND);
		}
		
		return flag;
	}
	
	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack)
	{
		if (stack != null && stack.getItem() == Items.FLINT_AND_STEEL)
		{
			this.worldObj.playSound(
					player, 
					this.posX, 
					this.posY, 
					this.posZ, 
					SoundEvents.ITEM_FLINTANDSTEEL_USE, 
					this.getSoundCategory(), 
					1.0F, 
					this.rand.nextFloat() * 0.4F + 0.8F);
			
			player.swingArm(hand);
			
			if (!this.worldObj.isRemote && this.hermit == Hermits.PYTHONGB && !this.isBurning())
			{
				this.setFire(11);
				
				stack.damageItem(2, player);
				this.vulnerable = true;
				this.lastVulnerable = this.livingTicks;
				this.attackEntityFrom(new EntityDamageSource("fire", player), 9.0F);
				
				return true;
			}
		}
		
		return super.processInteract(player, hand, stack);
	}
	
	@Override
	protected void damageEntity(DamageSource damageSrc, float damageAmount)
	{
		super.damageEntity(damageSrc, damageAmount);
	}
	
	@Override
	protected SoundEvent getAmbientSound()
	{
		return hermit.AMBIENT_SOUND;
	}
	
	@Override
	protected SoundEvent getHurtSound()
	{
		return hermit.HURT_SOUND;
	}
	
	@Override
	protected SoundEvent getDeathSound()
	{
		return hermit.DEATH_SOUND;
	}
	
	@Override
	protected void playStepSound(BlockPos pos, Block block)
	{
		super.playStepSound(pos, block);
	}
	
	@Nullable
	@Override
	protected ResourceLocation getLootTable()
	{
		return hermit.LOOT_TABLE;
	}
	
	@Override
	public void onDeath(DamageSource cause)
	{
		ItemStack banner = ItemHermitBanner.getHermitBannerFromIndex(this.hermit.ordinal());
		
		EntityItem itemEntity = new EntityItem(this.worldObj);
		itemEntity.setEntityItemStack(banner);
		itemEntity.setPosition(this.posX, this.posY, this.posZ);
		
		if (!this.worldObj.isRemote)
		{
			this.worldObj.spawnEntityInWorld(itemEntity);
		}
		
		super.onDeath(cause);
	}
	
	@Override
	public Iterable<ItemStack> getArmorInventoryList()
	{
		return new ArrayList<ItemStack>();
	}
	
	@Override
	public ItemStack getItemStackFromSlot(EntityEquipmentSlot slot)
	{
		return null;
	}
	
	@Override
	public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack)
	{
		;
	}
	
	@Override
	public EnumHandSide getPrimaryHand()
	{
		return EnumHandSide.RIGHT;
	}
	
	@Override
	public String getName()
	{
		String name;
		
		if (this.hasCustomName())
		{
			name = this.getCustomNameTag();
		}
		else
		{
			Hermits h = Hermits.values()[this.getHermitIdFromData()];
			name = h.NAME;
		}
		
		return name;
	}
	
	public ITextComponent getBossName()
	{
		return new TextComponentString(this.getName() + " Boss");
	}
	
	public boolean isWearing(EnumPlayerModelParts part)
	{
		return true;
	}
	
	private int getHermitIdFromData()
	{
		return this.getDataManager().get(HERMIT_INDEX).intValue();
	}
	
	@SideOnly(Side.CLIENT)
	public ResourceLocation getLocationSkin()
	{
		int hIndex = this.getHermitIdFromData();
		
		return new ResourceLocation(HQConstants.MODID, "textures/entity/hermits/" + Hermits.values()[hIndex].NAME + ".png");
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);
		
		if (compound.hasKey("HermitIndex"))
		{
			int hId = compound.getInteger("HermitIndex");
			this.setHermit(Hermits.values()[hId]);
		}
    }
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);
		
		compound.setInteger("HermitIndex", this.getHermitIdFromData());
	}
	
	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float f1)
	{
		switch(this.hermit)
		{
		case XISUMA:
			if (this.shootingTime - this.shootingTimeLast > (8 + rand.nextInt(12)))
			{
				this.shootingTimeLast = this.shootingTime;
				this.playSound(HQSoundEvents.FIREBALL_SHOOT, 1.0F, 1.0F / (this.rand.nextFloat() * 0.4F + 1.2F) + 1.5F);
				this.swingArm(EnumHand.MAIN_HAND);
				ItemImpFireball.shootFireball(this.worldObj, this, EnumHand.MAIN_HAND);
			}
			break;
		case ISKALL85:
			this.playSound(HQSoundEvents.BEAM_SHOT, 1.0F, 1.0F / (this.rand.nextFloat() * 0.4F + 1.2F) + 1.5F);
			
			if (!this.worldObj.isRemote)
			{
				EntityIskallBeam beam = new EntityIskallBeam(this.worldObj, this, this.getLookVec().xCoord, this.getLookVec().yCoord, this.getLookVec().zCoord);
				beam.damage = 2;
				this.worldObj.spawnEntityInWorld(beam);
			}
			break;
		case XBCRAFTED:
			if (this.getAttackTarget() != null)
			{
				
				EntityLivingBase entitylivingbase = this.getAttackTarget();
				this.setTargetEntity(entitylivingbase.getEntityId());
				
				if (this.shootingTime > 80.0F)
				{
					entitylivingbase.attackEntityFrom(DamageSource.causeThornsDamage(this), 3.0F);
					this.shootingTime -= 10 + this.rand.nextInt(50);
					if (this.rand.nextInt(100) == 0)
						this.changeCombat(true);
				}
			}
			break;
		case CUBFAN135:
			if (this.getAttackTarget() != null)
			{
				EntityLivingBase entitylivingbase = this.getAttackTarget();
				this.setTargetEntity(entitylivingbase.getEntityId());
				
				if (this.shootingTime > 6.0F)
				{
					entitylivingbase.attackEntityFrom(DamageSource.magic, 1.0F);
					entitylivingbase.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100, 2));
					this.changeCombat(true);
				}
			}
			break;
		default:
			break;
		}
	}
}
