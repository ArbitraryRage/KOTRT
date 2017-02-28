package com.waabbuffet.kotrt.entities.Outpost;

import com.waabbuffet.kotrt.util.QuestFormat;

import scala.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityOutpostBase extends EntityTameable implements IRangedAttackMob{
	
	private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.2D, 20, 20, 15.0F);
    private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide(this, 1.2D, false);
	
	private int KingdomID, KingdomStructureID, QuestID, SingleReputation, QuestTimer, QuestObjectiveTracker;
	private String VillagerName;
	public int DeliveryHomeID;
	public BlockPos DeliveryHome;
	public boolean NeedsUpdating = true;
	
	public boolean AmTarget = false;
	
	Random Rand = new Random();
	
	//Quest Giver (basic quests) (20% to get quest), commanders (story line quests) (10% to get quest), special shopkeepers(normal items), normal villagers(dont exist)
		/*
		 * Commander List (these will only be names that will define the skin/cheese):
		 * - Builder (have quests to expand the city/more resource based quest)
		 * - War person (have quests to fight/detective quests)
		 * - Magic person (have stories that will be based around rare artifacts/magic rituals )
		 * - food person (have stories around food/food pills)
		 * - leader person (have problems with leadership/rebels/alliance)
		 * - will increase over all reputation 
		 */
		
		
		/* Quest Givers are the normal villagers
		 * - normal gather quests
		 * - will offer basic gold/resources
		 * - will increase single reputation so you can take them
		 * - 
		 */
		
		//Will track progression of quests by defining a segment to each quest...1-10 = quest 1
		// will have to remember kingdomID, kingdom structure ID, work location, job 
	public EntityOutpostBase(World worldIn) {
		super(worldIn);
		this.setSize(1.0F, 1.7F);
		
		if(this.VillagerName != null)
			this.setCustomNameTag(this.VillagerName);
		else{
			this.setCustomNameTag("Villager");
		}
	
		
		
		
		
		this.tasks.addTask(4, new EntityAILookIdle(this));
		this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
	
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
	
		this.dataWatcher.addObject(20, 0);//VillageID
	}
	
	
	
	public EntityOutpostBase(World worldIn, String villagerName, int villagerSkinID, boolean isGuard)
	{
		super(worldIn);
		this.setSize(1.0F, 1.7F);
		this.VillagerName = villagerName;
		this.setVillagerSkinID(villagerSkinID);
		
		this.QuestID = 0; // means no quest
		this.QuestTimer = 1200;
		this.QuestObjectiveTracker = -1; // -1 means no quest, 0 means completed
		
		if(this.VillagerName != null)
			this.setCustomNameTag(this.VillagerName);
		else{
			this.setCustomNameTag("Villager");
		}
		
		
		if(isGuard)
		{
			if(villagerSkinID == 14)
			{
				this.setCurrentItemOrArmor(0, new ItemStack(Items.bow));
				this.tasks.addTask(4, this.aiArrowAttack);
			}else{
				
				this.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
				this.tasks.addTask(4, this.aiAttackOnCollide);
			}
			
		
				 
	        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntitySpider.class, true));
	        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityZombie.class, true));
	        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntitySkeleton.class, true));
	        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityWitch.class, true));
	       
		}
		
		this.tasks.addTask(4, new EntityAILookIdle(this));
		this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
	
	}
	
	
	protected void applyEntityAttributes()
	    {
	        super.applyEntityAttributes();
	        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.50D);
	        
	    }
	
	
	public void setVillagerSkinID(int villagerSkinID) {
		this.getDataWatcher().updateObject(20, villagerSkinID);
	}


	
	@Override
	public boolean attackEntityAsMob(Entity entity)
    {
        if (super.attackEntityAsMob(entity))
        {
          

            return true;
        }
        else
        {
        	int i;
		    i = 7;
		
			this.swingItem();
	        return entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float)i);
           
        }
    }
	public int getQuestObjectiveTracker() {
		return QuestObjectiveTracker;
	}
		
	@Override
	protected boolean canDespawn() {
		// TODO Auto-generated method stub
		return false;
	}
	public int getQuestTimer() {
		return QuestTimer;
	}

	public void setQuestTimer(int questTimer) {
		QuestTimer = questTimer;
	}
	
	public int getKingdomID() {
		return KingdomID;
	}

	public void setKingdomID(int kingdomID) {
		KingdomID = kingdomID;
	}

	public int getKingdomStructureID() {
		return KingdomStructureID;
	}

	public void setKingdomStructureID(int kingdomStructureID) {
		KingdomStructureID = kingdomStructureID;
	}

	public int getQuestID() {
		return QuestID;
	}

	public void setQuestID(int questID) {
		QuestID = questID;
	}

	public int getSingleReputation() {
		return SingleReputation;
	}

	public void setSingleReputation(int singleReputation) {
		SingleReputation = singleReputation;
	}

	public String getVillagerName() {
		return VillagerName;
	}

	public void decreaseQuestTracker(){
		this.QuestObjectiveTracker--;
	}
	public void setVillagerName(String villagerName) {
		VillagerName = villagerName;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		// TODO Auto-generated method stub
		return null;
	}
	public void setQuestObjectiveTracker(int questObjectiveTracker) {
		QuestObjectiveTracker = questObjectiveTracker;
	}
	
	public void ResetQuest()
	{
		this.QuestID = 0;
		this.QuestObjectiveTracker = -1;
		this.QuestTimer = 1200;
		this.DeliveryHomeID = 0;
		this.AmTarget = false;
	}
	
	public int getRandomVillagerPos()
	{
		int b1 = 0;
		for(int i = 0; i < this.worldObj.getLoadedEntityList().size(); i ++)
		{
			Entity b = this.worldObj.getLoadedEntityList().get(i);
			if(b instanceof EntityOutpostBase)
			{
				if(Rand.nextInt(4) == 1)
				{
					
						if(!b.getPosition().equals(this.getPosition()))
						{
							this.DeliveryHome = b.getPosition();
							b1 = b.getEntityId();
							break;
						}
					
				}
				
			}
		}
		
		if(b1 == 0)
		{
			for(int i = 0; i < this.worldObj.getLoadedEntityList().size(); i ++)
			{
				Entity b = this.worldObj.getLoadedEntityList().get(i);
				if(b instanceof EntityOutpostBase)
				{
					
					if(!b.getPosition().equals(this.getPosition()))
					{
						this.DeliveryHome = b.getPosition();
						b1 = b.getEntityId();
						break;
					}
					
					
				}
			}
		}
	
		return b1;
	}
	
	/*
	 * this will set the info the other getQuest is used for quest info
	 */
	public void setQuest()
	{
		//in the get quest have to set rarity, ID, maxProgress tracker: commander quests will work like this 20 -30 = ritual, has 10 objectives and only the final quest gives reward
		//i can give rewards anytime
		
		
		
		
		switch(QuestID){
		case 1:
			//Gather 16 furnaces
			this.QuestObjectiveTracker = 16;
		//	b = new QuestFormat(1, new ItemStack(Blocks.furnace, 16));
			break;
		case 2:
			//Gather 16 Bricks
			this.QuestObjectiveTracker = 16;
		//	b = new QuestFormat(1, new ItemStack(Blocks.brick_block, 16 ));
			break;
		case 3:
			//Gather 16 glass
			this.QuestObjectiveTracker = 16;
	//		b = new QuestFormat(0, new ItemStack(Blocks.glass, 16));
			break;
		case 4:
			//Kill 16 zombies
			this.QuestObjectiveTracker = 16;
	//		b = new QuestFormat(1,1, new EntityZombie(this.worldObj), new ItemStack(Items.apple));
			
			break;
		case 5:
			//Kill 16 Skeletons
			this.QuestObjectiveTracker = 16;
			break;
		case 6:
			//Kill 16 spiders
			this.QuestObjectiveTracker = 16;
			break;
		case 7:
			//kill 16 Creepers
			this.QuestObjectiveTracker = 16;
			break;
		case 8:
			//Deliver 16 apples to cow
			this.QuestObjectiveTracker = 1;
			this.DeliveryHomeID = this.getRandomVillagerPos();
			break;
		case 9:
			//Deliver a Cake to a skeleton
			this.QuestObjectiveTracker = 1;
			this.DeliveryHomeID = this.getRandomVillagerPos();
			break;
		case 10:
			//Deliver 8 fish to Villager
			this.QuestObjectiveTracker = 1;
			this.DeliveryHomeID = this.getRandomVillagerPos();
			break;
		case 11:
			//Deliver 16 Iron ore to Villager
			this.QuestObjectiveTracker = 1;
			this.DeliveryHomeID = this.getRandomVillagerPos();
			break;
		case 12:
			//Deliver 3 mushroom soup to Villager
			this.QuestObjectiveTracker = 1;
			this.DeliveryHomeID = this.getRandomVillagerPos();
			break;
			
		case 13:
			//Deliver 16 piston to Villager
			this.QuestObjectiveTracker = 1;
			this.DeliveryHomeID = this.getRandomVillagerPos();
			break;
		case 14:
			// Deliver 16 Jack O lantern to Villager
			this.QuestObjectiveTracker = 1;
			this.DeliveryHomeID = this.getRandomVillagerPos();
			break;
		case 15:
			//Deliver 2 Diamonds to Villager
			this.QuestObjectiveTracker = 1;
			this.DeliveryHomeID = this.getRandomVillagerPos();
			break;
		case 16:
			//Deliver 32 Arrows to Villager
			this.QuestObjectiveTracker = 1;
			this.DeliveryHomeID = this.getRandomVillagerPos();
			break;
		case 17:
			//Deliver 16 white stained clay to Villager
			this.QuestObjectiveTracker = 1;
			this.DeliveryHomeID = this.getRandomVillagerPos();
			break;
		case 18:
			//Kill 16 Ghast 
			this.QuestObjectiveTracker = 16;
			break;
		case 19:
			//Kill 16 Pigman 
			this.QuestObjectiveTracker = 16;
			break;
		case 20:
			//Kill 16 Sheep 
			this.QuestObjectiveTracker = 16;
			break;
		case 21:
			//Kill 16 Cow 
			this.QuestObjectiveTracker = 16;
			break;
		case 22:
			//Kill 16 Pig
			this.QuestObjectiveTracker = 16;
			break;
		case 23:
			//Kill 16  squid
			this.QuestObjectiveTracker = 16;
			break;
		case 24:
			//Gather 16 furnaces
			this.QuestObjectiveTracker = 16;
			break;
		case 25:
			//Gather 16 furnaces
			this.QuestObjectiveTracker = 16;
			break;
		case 26:
			//Gather 16 Cactus
			this.QuestObjectiveTracker = 16;
			break;
		case 27:
			//Gather 16 Tnt
			this.QuestObjectiveTracker = 16;
			break;
		case 28:
			//Gather 16 Slimes
			this.QuestObjectiveTracker = 8;
			break;
		case 29:
			//Gather 16 Blaze Rods
			this.QuestObjectiveTracker = 16;
			break;
		case 30:
			//Gather 8 Ender Pearls
			this.QuestObjectiveTracker = 8;
			break;
		case 31:
			this.QuestObjectiveTracker = 16;
			break;
		case 32:
			this.QuestObjectiveTracker = 16;
			break;
		case 33:
			this.QuestObjectiveTracker = 16;
			break;
		case 34:
			this.QuestObjectiveTracker = 16;
			break;
		case 35:
			this.QuestObjectiveTracker = 16;
			break;
		case 36:
			this.QuestObjectiveTracker = 16;
			break;
		case 37:
			this.QuestObjectiveTracker = 64;
			break;
		}
	
		
	}
	
	
	public int getVillagerSkinID() {
		return this.dataWatcher.getWatchableObjectInt(20);
	}
	
	
	//used to get info of quest anytime
	public QuestFormat getQuest(int QuestID)
	{
		//in the get quest have to set rarity, ID, maxProgress tracker: commander quests will work like this 20 -30 = ritual, has 10 objectives and only the final quest gives reward
		
		QuestFormat b = null;
		
		switch(QuestID){
		case 1:
			//Gather 16 furnaces
			
			b = new QuestFormat(1, new ItemStack(Blocks.furnace, 16));
			break;
		case 2:
			//Gather 16 Bricks
		
			b = new QuestFormat(1, new ItemStack(Blocks.brick_block, 16 ));
			break;
		case 3:
			//Gather 16 glass
			
			b = new QuestFormat(0, new ItemStack(Blocks.glass, 16));
			break;
		case 4:
			//Kill 15 zombies
			
			b = new QuestFormat(2,1, new EntityZombie(this.worldObj), new ItemStack(Items.apple));
			break;
		case 5:
			//Kill 16 Skeletons
			b = new QuestFormat(2, 1, new EntitySkeleton(this.worldObj), new ItemStack(Items.apple));
			break;
		case 6:
			//Kill 16 Spiders
			b = new QuestFormat(2, 1, new EntitySpider(this.worldObj), new ItemStack(Items.apple));
			break;
		case 7:
			//Kill 16 Creepers
			b = new QuestFormat(3, 1, new EntityCreeper(this.worldObj), new ItemStack(Items.apple));
			break;
		case 8:
			//Deliver 16 apples to cow
			b = new QuestFormat(2, 1, new EntityOutpostBase(this.worldObj), new ItemStack(Items.apple, 16));
			break;
		case 9:
			//Deliver cake to skeleton
			b = new QuestFormat(2, 1, new EntityOutpostBase(this.worldObj), new ItemStack(Items.cake));
			break;
		case 10:
			//Deliver 16 Fish to Villager
			b = new QuestFormat(4, 1, new EntityOutpostBase(this.worldObj), new ItemStack(Items.fish, 8));
			break;
		case 11:
			//Deliver 16 Iron to Villager
			b = new QuestFormat(3, 1, new EntityOutpostBase(this.worldObj), new ItemStack(Blocks.iron_ore, 16));
			break;
		case 12:
			//Deliver 1 Mushroom soup to Villager
			b = new QuestFormat(2, 1, new EntityOutpostBase(this.worldObj), new ItemStack(Items.mushroom_stew, 1));
			break;
		case 13:
			//Deliver 16 Piston to Villager
			b = new QuestFormat(3, 1, new EntityOutpostBase(this.worldObj), new ItemStack(Blocks.piston, 16));
			break;
		
		case 14:
			//Deliver 16 Jack O lantern to Villager
			b = new QuestFormat(2, 1, new EntityOutpostBase(this.worldObj), new ItemStack(Blocks.pumpkin, 16));
			break;
		case 15:
			//Deliver 2 Diamond to Villager
			b = new QuestFormat(4, 1, new EntityOutpostBase(this.worldObj), new ItemStack(Items.diamond, 2));
			break;
		case 16:
			//Deliver 32 Arrow to Villager
			b = new QuestFormat(2, 1, new EntityOutpostBase(this.worldObj), new ItemStack(Items.arrow, 32));
			break;
		case 17:
			//Deliver 16 White stained clay to Villager
			b = new QuestFormat(2, 1, new EntityOutpostBase(this.worldObj), new ItemStack(Blocks.stained_hardened_clay, 16, 0));
			break;
		case 18:
			//Kill 16 Ghast
			b = new QuestFormat(4, 1, new EntityGhast(this.worldObj), new ItemStack(Items.wheat, 16));
			
			break;
		case 19:
			//Kill 16 Pigman
			b = new QuestFormat(3, 1, new EntityPigZombie(this.worldObj), new ItemStack(Items.wheat, 16));
			
			break;
		case 20:
			//Kill 16 sheep
			b = new QuestFormat(1, 1, new EntitySheep(this.worldObj), new ItemStack(Items.wheat, 16));
			
			break;
		case 21:
			//Kill 16 cow
			b = new QuestFormat(2, 1, new EntityCow(this.worldObj), new ItemStack(Items.wheat, 16));
			
			break;
		case 22:
			//Kill 16 Pig
			b = new QuestFormat(2, 1, new EntityPig(this.worldObj), new ItemStack(Items.wheat, 16));
			
			break;
		case 23:
			//Kill 16 Squid
			b = new QuestFormat(2, 1, new EntitySquid(this.worldObj), new ItemStack(Items.wheat, 16));
			
			break;
		case 24:
			//Gather 16 Furnaces
			b = new QuestFormat(4, 1, new EntityGhast(this.worldObj), new ItemStack(Blocks.furnace, 16));
			break;
		case 25:
			//Gather 16 bookshelf
			b = new QuestFormat(1, 1, new EntityGhast(this.worldObj), new ItemStack(Blocks.bookshelf, 16));
			break;
		case 26:
			//Gather 16 Cactus
			b = new QuestFormat(1, 1, new EntityGhast(this.worldObj), new ItemStack(Blocks.cactus, 16));
			break;
		case 27:
			//Gather 16 Tnt
			b = new QuestFormat(2, 1, new EntityGhast(this.worldObj), new ItemStack(Blocks.tnt, 16));
			break;
		case 28:
			//Gather 16 Slimes
			b = new QuestFormat(3, 1, new EntityGhast(this.worldObj), new ItemStack(Blocks.lapis_block, 8));
			break;
		case 29:
			//Gather 16 Blaze Rods
			b = new QuestFormat(3, 1, new EntityGhast(this.worldObj), new ItemStack(Blocks.redstone_block, 8));
			break;
		case 30:
			//Gather 8 Ender Pearls
			b = new QuestFormat(3, 1, new EntityGhast(this.worldObj), new ItemStack(Items.ender_pearl, 8));
			break;
		case 31:
			//Gather 16 dand
			b = new QuestFormat(0, 1, new EntityGhast(this.worldObj), new ItemStack(Blocks.yellow_flower, 16));
			break;
		case 32:
			//Gather 16 rose
			b = new QuestFormat(0, 1, new EntityGhast(this.worldObj), new ItemStack(Blocks.red_flower, 16));
			break;
		case 33:
			//Deliver
			b = new QuestFormat(2, 1, new EntityOutpostBase(this.worldObj), new ItemStack(Items.cooked_porkchop, 16));
			break;
		case 34:
			//Gather cooked beef
			b = new QuestFormat(1, 1, new EntityGhast(this.worldObj), new ItemStack(Items.cooked_beef, 16));
			break;
		case 35:
			//Gather 16 reeds
			b = new QuestFormat(1, 1, new EntityGhast(this.worldObj), new ItemStack(Items.reeds, 16));
			break;
		case 36:
			//Gather cooked beef
			b = new QuestFormat(1, 1, new EntityGhast(this.worldObj), new ItemStack(Blocks.red_mushroom, 16));
			break;
		case 37:
			//Gather 64 leaves
			b = new QuestFormat(0, 1, new EntityGhast(this.worldObj), new ItemStack(Blocks.leaves, 64));
			break;
				
		}
	
		return b;
	}
	//int the gui Have Complete Quest

	
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompund) {
		// TODO Auto-generated method stub
		
		super.writeEntityToNBT(tagCompund);
		
		if(this.getVillagerName() != null)
			tagCompund.setString("Name", this.getVillagerName());
		/*
		tagCompund.setInteger("KingdomID", this.getKingdomID());
		tagCompund.setInteger("KingdomStructureID", this.getKingdomStructureID());
		tagCompund.setInteger("SingleReputation", this.getSingleReputation());
		*/
		
		//ADD stuff
		tagCompund.setInteger("VillagerSkinID", this.getVillagerSkinID());
		tagCompund.setInteger("QuestID", this.getQuestID());
		tagCompund.setInteger("QuestTimer", this.getQuestTimer());
		tagCompund.setInteger("QuestTracker", this.QuestObjectiveTracker);
		tagCompund.setBoolean("AmTarget", this.AmTarget);
	
		
		
		if(this.DeliveryHome != null)
		{
			tagCompund.setInteger("BlockX", this.DeliveryHome.getX());
			tagCompund.setInteger("BlockY", this.DeliveryHome.getY());
			tagCompund.setInteger("BlockZ", this.DeliveryHome.getZ());
			
		}
		
		
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);
		this.setVillagerName(tagCompund.getString("Name"));
		/*
		this.setKingdomID(tagCompund.getInteger("KingdomID"));
		this.setKingdomStructureID(tagCompund.getInteger("KingdomStructureID"));
		this.setSingleReputation(tagCompund.getInteger("SingleReputation"));
		*/
		
		this.setVillagerSkinID(tagCompund.getInteger("VillagerSkinID")); 
		this.setQuestID(tagCompund.getInteger("QuestID"));
		this.setQuestTimer(tagCompund.getInteger("QuestTimer"));
		this.setQuestObjectiveTracker(tagCompund.getInteger("QuestTracker"));
		this.AmTarget = tagCompund.getBoolean("AmTarget");
		
		
		this.DeliveryHome = new BlockPos(tagCompund.getInteger("BlockX"), tagCompund.getInteger("BlockY"), tagCompund.getInteger("BlockZ"));
		
		
		
	}
	
	
	
	public void setDeliveryID()
	{
		for(int i = 0 ; i < this.worldObj.getLoadedEntityList().size(); i ++)
		{
			
			
				
				if(this.worldObj.getLoadedEntityList().get(i) instanceof EntityOutpostBase)
				{
					
			//		System.out.println("Del Home:" + this.DeliveryHome);
			//		System.out.println("World Home: " + this.worldObj.getLoadedEntityList().get(i).getPosition());
			//		System.out.println("Dis" + this.worldObj.getLoadedEntityList().get(i).getPosition().distanceSq(this.DeliveryHome.getX(), this.DeliveryHome.getY(), this.DeliveryHome.getZ()));
			//		System.out.println("Dylan" + (this.worldObj.getLoadedEntityList().get(i).getPosition().distanceSq(this.DeliveryHome.getX(), this.DeliveryHome.getY(), this.DeliveryHome.getZ()) <= 1));
					
					if(this.worldObj.getLoadedEntityList().get(i).getPosition().distanceSq(this.DeliveryHome.getX(), this.DeliveryHome.getY(), this.DeliveryHome.getZ()) <= 1.0)
					{
						
						this.DeliveryHomeID = this.worldObj.getLoadedEntityList().get(i).getEntityId();
						break;
					}
				}
			
		}
		
		if(this.DeliveryHomeID == 0)
		{
			this.AmTarget = false;
			this.DeliveryHomeID = this.getRandomVillagerPos();
		}
		
	}
		
	
	
	@Override
	public void onUpdate() {
				
	if(this.NeedsUpdating)
	{
		
			if(this.getVillagerSkinID() > 13)
			{
				
			
				if(this.getVillagerSkinID() == 14)
				{
					this.setCurrentItemOrArmor(0, new ItemStack(Items.bow));
					this.tasks.addTask(4, this.aiArrowAttack);
				}else{
				
					this.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
					this.tasks.addTask(4, this.aiAttackOnCollide);
				}
				this.setCurrentItemOrArmor(1, new ItemStack(Items.leather_boots));
				this.setCurrentItemOrArmor(2, new ItemStack(Items.iron_leggings));
				this.setCurrentItemOrArmor(3, new ItemStack(Items.iron_chestplate));
				this.setCurrentItemOrArmor(4, new ItemStack(Items.iron_helmet));
				
		        
		        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntitySpider.class, true));
		        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityZombie.class, true));
		        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntitySkeleton.class, true));
		        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityWitch.class, true));
		        this.NeedsUpdating = false;
			}
	}
		
		
	if(!this.worldObj.isRemote)
	{
			
		
		if(this.getQuestID() != 0)
		{
			if(this.getQuest(this.QuestID).getQuestTypeID() == 2)
			{
				if(this.DeliveryHomeID == 0)
				{
					if(this.DeliveryHome != null)
						this.setDeliveryID();
				}
			}
		}
	
		if(this.getQuestID() == 0)
		{
			if(this.QuestTimer == 0)
			{
				int Random = Rand.nextInt(100);
				if(Random <= 20)
				{
					
					this.setQuestID(Rand.nextInt(37));
					this.setQuest();
					
				}
				
				this.setQuestTimer(1200);
			}else{
				this.QuestTimer--;
				
			}
		}
	}
		/* dont like this because player only has certain time to finish quest...maybe just make a counter to reset quest
		 
		if(this.getQuestID() != 0)
		{
			if(this.QuestTimer > 0){
				this.QuestTimer--;
				
			}else {
				
				this.setQuestID(0);
				this.setQuestTimer(0);
			}
		}else {
			
			if(this.QuestTimer == 0)
			{
				//RandomChance here
				int Random = Rand.nextInt(100);
				if(Random <= 20)
				{
					this.setQuestID(Rand.nextInt(10));
					this.QuestTimer = 24000;
				}else
					this.QuestTimer = 1200;
			}else{
				this.QuestTimer--;
			}
		}
	/*
	 * if have quest 
	 * then count down to zero when zero go back to chance of starting quest
	 * 
	 * else
	 * count to 60 secs then check to get a 20% chance of quest
	 */
		
		
		
		super.onUpdate();
	}



	 public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_)
	    {
	        EntityArrow entityarrow = new EntityArrow(this.worldObj, this, p_82196_1_, 1.6F, (float)(14 - this.worldObj.getDifficulty().getDifficultyId() * 4));
	        int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, this.getHeldItem());
	        int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, this.getHeldItem());
	        entityarrow.setDamage((double) 5.0);

	        if (i > 0)
	        {
	            entityarrow.setDamage(entityarrow.getDamage() + (double)3);
	        }

	        if (j > 0)
	        {
	            entityarrow.setKnockbackStrength(j);
	        }

	        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.getHeldItem()) > 0)
	        {
	            entityarrow.setFire(100);
	        }

	        this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
	        this.worldObj.spawnEntityInWorld(entityarrow);
	    }
}
