package com.waabbuffet.kotrt.entities.Kingdom;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;




























import com.waabbuffet.kotrt.KnightsOfTheRoundTable;
import com.waabbuffet.kotrt.entities.KingdomAI.BuilderAI;
import com.waabbuffet.kotrt.entities.KingdomAI.CraftingAI;
import com.waabbuffet.kotrt.entities.KingdomAI.MailManAI;
import com.waabbuffet.kotrt.entities.KingdomAI.MinerAI;
import com.waabbuffet.kotrt.entities.KingdomAI.MoveToWorkOrHomeAI;
import com.waabbuffet.kotrt.entities.KingdomAI.PickUpItemsAI;
import com.waabbuffet.kotrt.entities.KingdomAI.ReedMelonFarmerAI;
import com.waabbuffet.kotrt.entities.KingdomAI.StorageAI;
import com.waabbuffet.kotrt.entities.KingdomAI.TreeFarmerAI;
import com.waabbuffet.kotrt.entities.KingdomAI.WheatFarmerAI;
import com.waabbuffet.kotrt.util.CraftingAIFormat;

import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

public class EntityFarmer extends EntityKingdomBase {

	
	
	
	List<CraftingAIFormat> CraftingObjects = new ArrayList();
	
	BlockPos[] WhereAM = new BlockPos[4];
	BlockPos StorageLocation;
	
	//Change so that The farmer, harvester,fighter are just AI //String Job, int Happiness
	public EntityFarmer(World worldIn) {
		super(worldIn);
		
		this.setTamed(true);
		this.setCustomNameTag("Villager");
		this.getNavigator().setHeightRequirement(2F);
		
		this.DetermineTasks(worldObj);
	}
	
	public void DetermineTasks(World worldIn)
	{
		String Job = this.getJob();
		
		this.tasks.addTask(0, new MoveToWorkOrHomeAI(this, worldIn));
		this.tasks.addTask(4, new EntityAILookIdle(this));
		this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
	//	this.tasks.addTask(1, new PickUpItemsAI(this, worldIn));
		
		if(Job.contains("Miners Guild"))
		{
			this.tasks.addTask(0, new MinerAI(this, worldIn));
			
		}else if(Job.contains("Lumberjack Guild"))
		{
			
			this.tasks.addTask(0, new TreeFarmerAI(this, worldIn));
		}else if(Job.contains("Wheat Farmer Guild"))
		{
			this.tasks.addTask(0, new WheatFarmerAI(this, worldIn));
		}else if(Job.contains("Exotic Farmers guild"))
		{

			this.tasks.addTask(0, new ReedMelonFarmerAI(this, worldIn));
		}else if(Job.contains("Messanger Guild"))
		{
			
			this.tasks.addTask(0, new MailManAI(this, worldIn));
		}else if(Job.contains("Carpenter"))
		{
			
			this.tasks.addTask(0, new CraftingAI(this, worldIn, this.FillCarpenterObjects()));
		}else if(Job.contains("Builders Guild"))
		{
			this.tasks.addTask(0, new BuilderAI(this, worldIn));
		}else if(Job.contains("Storage Hut"))
		{
			this.tasks.addTask(0, new StorageAI(this, worldIn));
		}else if(Job.contains("Blacksmith"))
		{
			
			this.tasks.addTask(0, new CraftingAI(this, worldIn,	this.FillBlacksmithObjects()));
		}
			
		
		
		
					
	}
	public void setStorageLocation(BlockPos storageLocation) {
		StorageLocation = storageLocation;
	}
	
	public BlockPos getStorageLocation() {
		return StorageLocation;
	}
	
	public void setWhereAM(BlockPos[] whereAM) {
		WhereAM = whereAM;
	}
	public void setWhereAM1(BlockPos whereAM) {
		WhereAM[0] = whereAM;
	}
	public void setWhereAM2(BlockPos whereAM) {
		WhereAM[1] = whereAM;
	}
	public void setWhereAM3(BlockPos whereAM) {
		WhereAM[2] = whereAM;
	}
	public void setWhereAM4(BlockPos whereAM) {
		WhereAM[3] = whereAM;
	}
	
	
	public BlockPos[] getWhereAM() {
		return WhereAM;
	}
	
	public void RemoveTasks()
	{
		this.tasks.taskEntries.clear();
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setString("BuildingName", this.BuildingName);
		nbt.setString("JobName", this.Job);
		nbt.setBoolean("StartJob", this.StartJob);
		
		
	
	
		if(this.getWorkPlace() != null)
		{
			nbt.setInteger("WorkPlaceX", this.getWorkPlace().getX());
			nbt.setInteger("WorkPlaceY", this.getWorkPlace().getY());
			nbt.setInteger("WorkPlaceZ", this.getWorkPlace().getZ());
		}
		//Make sure they remember everything
		
		if(this.getStorageLocation() != null)
		{
			nbt.setInteger("SourceLocalX", this.getStorageLocation().getX());
			nbt.setInteger("SourceLocalY", this.getStorageLocation().getY());
			nbt.setInteger("SourceLocalZ", this.getStorageLocation().getZ());
		}
		
		
			for(int i =0; i < 4; i ++)
			{
				if(this.getWhereAM()[i] != null)
				{
					nbt.setInteger("WhereAmX" + i, this.getWhereAM()[i].getX());
					nbt.setInteger("WhereAmY" + i, this.getWhereAM()[i].getY());
					nbt.setInteger("WhereAmZ" + i, this.getWhereAM()[i].getZ());
				}
				
			}
		
		
		
		super.writeToNBT(nbt);
	}
	
	
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		
		this.setBuildingName(nbt.getString("BuildingName"));
		this.setJob(nbt.getString("JobName"));
		
		this.setStartJob(nbt.getBoolean("StartJob"));

		this.setWorkPlace(nbt.getInteger("WorkPlaceX"), nbt.getInteger("WorkPlaceY"), nbt.getInteger("WorkPlaceZ"));
		
		this.setStorageLocation(new BlockPos(nbt.getInteger("SourceLocalX"), nbt.getInteger("SourceLocalY"), nbt.getInteger("SourceLocalZ")));
		
		this.setWhereAM1(new BlockPos(nbt.getInteger("WhereAmX0"), nbt.getInteger("WhereAmY0"), nbt.getInteger("WhereAmZ0")));
		this.setWhereAM2(new BlockPos(nbt.getInteger("WhereAmX1"), nbt.getInteger("WhereAmY1"), nbt.getInteger("WhereAmZ1")));
		this.setWhereAM3(new BlockPos(nbt.getInteger("WhereAmX2"), nbt.getInteger("WhereAmY2"), nbt.getInteger("WhereAmZ2")));
		this.setWhereAM4(new BlockPos(nbt.getInteger("WhereAmX3"), nbt.getInteger("WhereAmY3"), nbt.getInteger("WhereAmZ3")));
		
		
		super.readFromNBT(nbt);
	}
	
	
	
	public List<CraftingAIFormat> FillCarpenterObjects()
	{
		ItemStack[] TempItems = new ItemStack[5];
		int[] TempHowMany = new int[5];
		
		/*Sticks, Bookshelf, Can change into all types of wood, bed, doors, fences, planks, ladders
		 * chest, wooden stairs, pressure plate, torch, trap door, crafting station, sign
		 */
	
	//******************************************//
	// Door
		
		TempHowMany[0] = 6;
		TempItems[0] = new ItemStack(Blocks.planks);
		

	
			this.CraftingObjects.add(new CraftingAIFormat(TempItems.clone(), 200, TempHowMany, new ItemStack(Items.oak_door, 2))); //cost, craftingTime (Ticks), howMany, produced
		
			
		
	//BookShelf
		
		TempHowMany[0] = 6; // planks
		TempHowMany[1] = 3; // book
	
		TempItems[0] = new ItemStack(Blocks.planks);
		TempItems[1] = new ItemStack(Items.book);
	
		this.CraftingObjects.add(new CraftingAIFormat(TempItems.clone(), 320, TempHowMany, new ItemStack(Blocks.bookshelf, 2))); //cost, craftingTime (Ticks), howMany, produced
		
	
	//Fences
		
		TempHowMany[0] = 4; // planks
		TempHowMany[1] = 2; // sticks
	
		TempItems[0] = new ItemStack(Blocks.planks);
		TempItems[1] = new ItemStack(Items.stick);
		
		
		this.CraftingObjects.add(new CraftingAIFormat(TempItems.clone(), 280, TempHowMany, new ItemStack(Blocks.oak_fence, 8)));
	//Ladder
		
		TempHowMany[0] = 7; // sticks
	
		TempItems[0] = new ItemStack(Items.stick);
		
	
			this.CraftingObjects.add(new CraftingAIFormat(TempItems.clone(), 280, TempHowMany, new ItemStack(Blocks.ladder)));
	//Change Wood (0 = oak, 1 = spruce, 2 = birch, 3 = jungle, 4 = Acapaca, 5 = dark oak) logs are the same pattern
		//oak into spruce
			TempHowMany[0] = 1;
			TempItems[0] = new ItemStack(Blocks.planks, 1, 0);
			
			this.CraftingObjects.add(new CraftingAIFormat(TempItems.clone(), 5, TempHowMany, new ItemStack(Blocks.planks, 1,1)));
			
			//spruce into birch
			TempHowMany[0] = 1; 
			TempItems[0] = new ItemStack(Blocks.planks, 1, 1);
			
			this.CraftingObjects.add(new CraftingAIFormat(TempItems.clone(), 5, TempHowMany, new ItemStack(Blocks.planks, 1,2)));
			//birch into jungle
			TempHowMany[0] = 1;
			TempItems[0] = new ItemStack(Blocks.planks, 1, 2);
			
			this.CraftingObjects.add(new CraftingAIFormat(TempItems.clone(), 5, TempHowMany, new ItemStack(Blocks.planks, 1,3)));
			//jungle into acapaca
			TempHowMany[0] = 1; 
			TempItems[0] = new ItemStack(Blocks.planks, 1, 3);
			
			this.CraftingObjects.add(new CraftingAIFormat(TempItems.clone(), 5, TempHowMany, new ItemStack(Blocks.planks, 1,4)));
			
			//Acapaca into dark
			TempHowMany[0] = 1; 
			TempItems[0] = new ItemStack(Blocks.planks, 1, 4);
			
			this.CraftingObjects.add(new CraftingAIFormat(TempItems.clone(), 5, TempHowMany, new ItemStack(Blocks.planks, 1,5)));
			//Dark into oak
			TempHowMany[0] = 1; 
			TempItems[0] = new ItemStack(Blocks.planks, 1, 5);
			
			this.CraftingObjects.add(new CraftingAIFormat(TempItems.clone(), 5, TempHowMany, new ItemStack(Blocks.planks, 1,0)));
	//Stairs
		TempHowMany[0] = 6; // sticks
		TempItems[0] = new ItemStack(Blocks.planks);
		
		this.CraftingObjects.add( new CraftingAIFormat(TempItems.clone(), 280, TempHowMany, new ItemStack(Blocks.oak_stairs)));
		
	//Trap door
		TempHowMany[0] = 6; // sticks
		TempItems[0] = new ItemStack(Blocks.planks);
		
		this.CraftingObjects.add(new CraftingAIFormat(TempItems.clone(), 280, TempHowMany, new ItemStack(Blocks.trapdoor)));


		//Crafting Table
		TempHowMany[0] = 4; // sticks
		TempItems[0] = new ItemStack(Blocks.planks);
				
		this.CraftingObjects.add( new CraftingAIFormat(TempItems.clone(), 280, TempHowMany, new ItemStack(Blocks.crafting_table)));
		//leaves 
		//oak leaves
			TempHowMany[0] = 4; // sticks
			TempItems[0] = new ItemStack(Blocks.sapling);
					
			this.CraftingObjects.add( new CraftingAIFormat(TempItems.clone(), 280, TempHowMany, new ItemStack(Blocks.sapling, 4, 0)));
		//Spruce leaves	
			TempHowMany[0] = 4; // sticks
			TempItems[0] = new ItemStack(Blocks.sapling, 1, 1);
					
			this.CraftingObjects.add( new CraftingAIFormat(TempItems.clone(), 280, TempHowMany, new ItemStack(Blocks.sapling, 4, 1)));
		//birch
			TempHowMany[0] = 4; // sticks
			TempItems[0] = new ItemStack(Blocks.sapling, 1, 2);
					
			this.CraftingObjects.add( new CraftingAIFormat(TempItems.clone(), 280, TempHowMany, new ItemStack(Blocks.sapling, 4, 2)));
			
		//books
			TempHowMany[0] = 3; // Paper
			TempHowMany[1] = 1; // leather
			
			TempItems[0] = new ItemStack(Items.paper);
			TempItems[1] = new ItemStack(Items.leather);
					
			this.CraftingObjects.add(new CraftingAIFormat(TempItems.clone(), 280, TempHowMany, new ItemStack(Items.book)));
		
		return this.CraftingObjects;
	
	}
	
	public List<CraftingAIFormat> FillBlacksmithObjects() //smelting objects too
	{

		ItemStack[] TempItems = new ItemStack[5];
		int[] TempHowMany = new int[5];
		
		//Stone -> cooked Stone, Wood -> Charcoal, Sand -> Glass, ores into produce, stone -> lava (long operation)
		//****************************************************************//
	//	Stone -> cooked stone
		TempHowMany[0] = 1; // cobblestone
		TempItems[0] = new ItemStack(Blocks.cobblestone);
		
		this.CraftingObjects.add(0, new CraftingAIFormat(TempItems.clone(), 40, TempHowMany, new ItemStack(Blocks.stone, 2)));
		
//		Wood-> Charcoal
		TempHowMany[0] = 1; // cobblestone
		TempItems[0] = new ItemStack(Blocks.log);
			
		this.CraftingObjects.add(1, new CraftingAIFormat(TempItems.clone(), 40, TempHowMany, new ItemStack(Items.coal, 1, 1)));
		
//		Sand -> Glass
		TempHowMany[0] = 1; // cobblestone
		TempItems[0] = new ItemStack(Blocks.sand);
			
		this.CraftingObjects.add(2, new CraftingAIFormat(TempItems.clone(), 40, TempHowMany, new ItemStack(Blocks.glass, 2)));
		
//		Iron ore -> Iron
		TempHowMany[0] = 1; // cobblestone
		TempItems[0] = new ItemStack(Blocks.iron_ore);
			
		this.CraftingObjects.add(3, new CraftingAIFormat(TempItems.clone(), 40, TempHowMany, new ItemStack(Items.iron_ingot, 2)));
		
//		Gold ore -> Gold
		TempHowMany[0] = 1; // cobblestone
		TempItems[0] = new ItemStack(Blocks.gold_ore);
			
		this.CraftingObjects.add(4, new CraftingAIFormat(TempItems.clone(), 40, TempHowMany, new ItemStack(Items.gold_ingot, 2)));
		
//		Clay ore -> brick
		TempHowMany[0] = 1; // cobblestone
		TempItems[0] = new ItemStack(Items.clay_ball);
			
		this.CraftingObjects.add(5, new CraftingAIFormat(TempItems.clone(), 40, TempHowMany, new ItemStack(Items.brick, 2)));
//		Clay block -> Harden
		TempHowMany[0] = 1; // cobblestone
		TempItems[0] = new ItemStack(Blocks.clay);
			
		this.CraftingObjects.add(6, new CraftingAIFormat(TempItems.clone(), 40, TempHowMany, new ItemStack(Blocks.hardened_clay, 2)));
		
//		Piston 
		TempHowMany[0] = 4; // cobblestone
		TempHowMany[1] = 3; // plank
		TempHowMany[2] = 1; // iron
		TempHowMany[3] = 1; // redstone
		
		TempItems[0] = new ItemStack(Blocks.cobblestone);
		TempItems[1] = new ItemStack(Blocks.planks);
		TempItems[2] = new ItemStack(Items.iron_ingot);
		TempItems[3] = new ItemStack(Items.redstone);
			
		this.CraftingObjects.add(7, new CraftingAIFormat(TempItems.clone(), 40, TempHowMany, new ItemStack(Blocks.piston, 2)));
		
//		Rail -> Harden (0 = rail, 1 = , 2 = )
		TempHowMany[0] = 1; // stick
		TempHowMany[1] = 6; // iron
		
		TempItems[0] = new ItemStack(Items.stick);
		TempItems[1] = new ItemStack(Items.iron_ingot);
			
		this.CraftingObjects.add(8, new CraftingAIFormat(TempItems.clone(), 40, TempHowMany, new ItemStack(Blocks.rail, 20)));	
		
//		Powered Rail 
		TempHowMany[0] = 1; // stick
		TempHowMany[1] = 6; // gold
		TempHowMany[2] = 1; // redstone
		
		TempItems[0] = new ItemStack(Items.stick);
		TempItems[1] = new ItemStack(Items.iron_ingot);
		TempItems[2] = new ItemStack(Items.redstone);
		
		this.CraftingObjects.add(9, new CraftingAIFormat(TempItems.clone(), 40, TempHowMany, new ItemStack(Blocks.golden_rail, 9)));	
//		Stone -> lava
		TempHowMany[0] = 1; // Stone
		TempItems[0] = new ItemStack(Blocks.stone);
			
		this.CraftingObjects.add(10, new CraftingAIFormat(TempItems.clone(), 40, TempHowMany, new ItemStack(Items.lava_bucket, 1)));	
			
	
		return this.CraftingObjects;
	}
	
	
	
	public List<CraftingAIFormat> FillNatureObjects()// empty
	{

		return this.CraftingObjects;
	}
	
	public ItemStack getRequestedItem() {
		return RequestedItem;
	}
	
	 public boolean teleportTo(double x, double y, double z)
	    {
	        net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(this, x, y, z, 0);
	        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return false;
	        double d0 = this.posX;
	        double d1 = this.posY;
	        double d2 = this.posZ;
	        this.posX = event.targetX;
	        this.posY = event.targetY;
	        this.posZ = event.targetZ;
	        boolean flag = false;
	        BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);

	        if (this.worldObj.isBlockLoaded(blockpos))
	        {
	            boolean flag1 = false;

	            while (!flag1 && blockpos.getY() > 0)
	            {
	                BlockPos blockpos1 = blockpos.down();
	                Block block = this.worldObj.getBlockState(blockpos1).getBlock();

	                if (block.getMaterial().blocksMovement())
	                {
	                    flag1 = true;
	                }
	                else
	                {
	                    --this.posY;
	                    blockpos = blockpos1;
	                }
	            }

	            if (flag1)
	            {
	                super.setPositionAndUpdate(this.posX, this.posY, this.posZ);

	                if (this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(this.getEntityBoundingBox()))
	                {
	                    flag = true;
	                }
	            }
	        }

	        if (!flag)
	        {
	            this.setPosition(d0, d1, d2);
	            return false;
	        }
	        else
	        {
	            int i = 128;

	            for (int j = 0; j < i; ++j)
	            {
	                double d6 = (double)j / ((double)i - 1.0D);
	                float f = (this.rand.nextFloat() - 0.5F) * 0.2F;
	                float f1 = (this.rand.nextFloat() - 0.5F) * 0.2F;
	                float f2 = (this.rand.nextFloat() - 0.5F) * 0.2F;
	                double d3 = d0 + (this.posX - d0) * d6 + (this.rand.nextDouble() - 0.5D) * (double)this.width * 2.0D;
	                double d4 = d1 + (this.posY - d1) * d6 + this.rand.nextDouble() * (double)this.height;
	                double d5 = d2 + (this.posZ - d2) * d6 + (this.rand.nextDouble() - 0.5D) * (double)this.width * 2.0D;
	                this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, d3, d4, d5, (double)f, (double)f1, (double)f2, new int[0]);
	            }

	            this.worldObj.playSoundEffect(d0, d1, d2, "mob.endermen.portal", 1.0F, 1.0F);
	            this.playSound("mob.endermen.portal", 1.0F, 1.0F);
	            return true;
	        }
	    }

   
	
}
