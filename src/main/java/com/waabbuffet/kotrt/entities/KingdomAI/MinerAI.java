package com.waabbuffet.kotrt.entities.KingdomAI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.waabbuffet.kotrt.entities.Kingdom.EntityFarmer;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBlock;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MinerAI  extends EntityAIBase {

	EntityPlayer Player;
	EntityFarmer Farmer;
	World World;
	int FarmCD, ListIndex = 0;
	

	Block[][][] MiningStructure = new Block[3][3][5];
	Block[][][] PerfectMiningStructure = new Block[3][3][5];
	
	Random rand = new Random();
	
	boolean BuildingMode, MiningMode;
	
	
	public MinerAI(EntityFarmer farmer, World world)
	{
		World = world;
		Farmer = farmer;
		
	
		
	}
	

	public void setPerfectMiningStructure()
	{
		//************************************************************//
				// Floor One
					PerfectMiningStructure[0][0][0] = Blocks.STONE;
					PerfectMiningStructure[1][0][0] = Blocks.STONE;
					PerfectMiningStructure[2][0][0] = Blocks.STONE;
					
					PerfectMiningStructure[0][0][1] = Blocks.STONE;
			//		PerfectMiningStructure[1][0][1] = Blocks.air;
					PerfectMiningStructure[2][0][1] = Blocks.STONE;
					
			//		PerfectMiningStructure[0][0][2] = Blocks.air;
					PerfectMiningStructure[2][0][2] = Blocks.STONE;
					
				
					PerfectMiningStructure[0][0][4] = Blocks.STONE;
					PerfectMiningStructure[1][0][4] = Blocks.STONE;
					PerfectMiningStructure[2][0][4] = Blocks.STONE;
					
					PerfectMiningStructure[0][0][3] = Blocks.STONE;
			//		PerfectMiningStructure[1][0][3] = Blocks.air;
					PerfectMiningStructure[2][0][3] = Blocks.STONE;
					
					//************************************************************//
					// Floor two
					PerfectMiningStructure[0][1][0] = Blocks.COBBLESTONE_WALL;
					PerfectMiningStructure[1][1][0] = Blocks.COBBLESTONE_WALL;
					PerfectMiningStructure[2][1][0] = Blocks.COBBLESTONE_WALL;
					
					PerfectMiningStructure[0][1][1] = Blocks.COBBLESTONE_WALL;
					
					PerfectMiningStructure[2][1][1] = Blocks.COBBLESTONE_WALL;
					
			//		PerfectMiningStructure[0][1][2] = Blocks.air;
					PerfectMiningStructure[1][1][2] = Blocks.COBBLESTONE_WALL;
					PerfectMiningStructure[2][1][2] = Blocks.COBBLESTONE_WALL;
					
					PerfectMiningStructure[0][1][4] = Blocks.COBBLESTONE_WALL;
					PerfectMiningStructure[1][1][4] = Blocks.COBBLESTONE_WALL;
					PerfectMiningStructure[2][1][4] = Blocks.COBBLESTONE_WALL;
					
					PerfectMiningStructure[0][1][3] = Blocks.COBBLESTONE_WALL;
					
					PerfectMiningStructure[2][1][3] = Blocks.COBBLESTONE_WALL;
					
					//************************************************************//
					// Floor three
					
				//	PerfectMiningStructure[0][2][0] = Blocks.air;
					PerfectMiningStructure[1][2][0] = Blocks.COBBLESTONE_WALL;
			//		PerfectMiningStructure[2][2][0] = Blocks.air;
					
					PerfectMiningStructure[0][2][1] = Blocks.COBBLESTONE_WALL;
					PerfectMiningStructure[1][2][1] = Blocks.WATER;
					PerfectMiningStructure[2][2][1] = Blocks.COBBLESTONE_WALL;
					
			//		PerfectMiningStructure[0][2][2] = Blocks.air;
					PerfectMiningStructure[1][2][2] = Blocks.COBBLESTONE_WALL;
			//		PerfectMiningStructure[2][2][2] = Blocks.air;
					
				//	PerfectMiningStructure[0][2][4] = Blocks.air;
					PerfectMiningStructure[1][2][4] = Blocks.COBBLESTONE_WALL;
			//		PerfectMiningStructure[2][2][4] = Blocks.air;
					
					PerfectMiningStructure[0][2][3] = Blocks.COBBLESTONE_WALL;
					PerfectMiningStructure[1][2][3] = Blocks.LAVA;
					PerfectMiningStructure[2][2][3] = Blocks.COBBLESTONE_WALL;
	}
	
	@Override
	public boolean shouldExecute() {
		
		this.setPerfectMiningStructure();
	
		String b1 = this.WhichDirection();
		
		if(b1 == null )
		{
			
			return false;
		}
		//b1[0] = air air air (right)
		//b1[1] = air air stone (straight)
		
		if(this.PerfectMiningStructure != null)
		{
			for(int x = 0; x < this.PerfectMiningStructure.length; x++)
			{
				for(int y = 0; y < this.PerfectMiningStructure[0].length; y++)
				{
					for(int z = 0; z < this.PerfectMiningStructure[0][0].length; z++)
					{
						
						if(this.PerfectMiningStructure[x][y][z] == null)
						{
							continue;
						}
						
						if(b1.contains("East"))
						{
						
							if(!World.getBlockState(new BlockPos(this.Farmer.getWorkPlace().getX() + x, this.Farmer.getWorkPlace().getY() + y, this.Farmer.getWorkPlace().getZ() + z)).equals(PerfectMiningStructure[x][y][z].getDefaultState()))
							{
								this.BuildingMode = true;
								return true;
							}
						}else if(b1.contains("North"))
						{
							if(!World.getBlockState(new BlockPos(this.Farmer.getWorkPlace().getX() + z, this.Farmer.getWorkPlace().getY() + y, this.Farmer.getWorkPlace().getZ() - x)).equals(PerfectMiningStructure[x][y][z].getDefaultState()))
							{
								this.BuildingMode = true;
								return true;
							}
						}else if(b1.contains("South"))
						{
							if(!World.getBlockState(new BlockPos(this.Farmer.getWorkPlace().getX() - z, this.Farmer.getWorkPlace().getY() + y, this.Farmer.getWorkPlace().getZ() + x)).equals(PerfectMiningStructure[x][y][z].getDefaultState()))
							{
								this.BuildingMode = true;
								return true;
							}
							
						}else if(b1.contains("West"))
						{
							if(!World.getBlockState(new BlockPos(this.Farmer.getWorkPlace().getX() - x, this.Farmer.getWorkPlace().getY() + y, this.Farmer.getWorkPlace().getZ() - z)).equals(PerfectMiningStructure[x][y][z].getDefaultState()))
							{
								this.BuildingMode = true;
								return true;
							}
						}
						
						
					}
				}
			}
		}
		
		if(!this.BuildingMode)
		{
			
			if(this.isItemInTileInv(new ItemStack(Items.IRON_PICKAXE)) != -1 || this.isInFarmerInv(new ItemStack(Items.IRON_PICKAXE)))
			{
				this.Farmer.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_PICKAXE));
				//don't know if this will work...gonna try it anyway
				//0 to EntityEquipmentSlot.MAINHAND because slots are handled differently
				this.MiningMode = true;
				
				return true;
			}
		}
		
		
		return false;
	}
	
	public void SetTENeedTool()
	{
		TileEntityKingdomStructureBlock TE = this.getTE();
		
		TE.setNeedTool(true);
	}

	
	public String WhichDirection()
	{
		String b = null;
		
		
		//b[0] = (triple air)
		//b[1] = (two air and stone)
		// 1 = east
		// 2 = west
		// 3 = north
		// 4 = south
		//[21:22:05] [Server thread/INFO] [STDOUT]: [com.waabbuffet.kotrt.entities.KingdomAI.MinerAI:WhichDirection:165]: BlockPos{x=-907, y=5, z=400}
		
	
		
		

				if(World.getBlockState(this.Farmer.getWorkPlace().west()).equals(Blocks.AIR.getDefaultState()))
				{
					if(World.getBlockState(this.Farmer.getWorkPlace().west().west()).equals(Blocks.AIR.getDefaultState()))
					{
						if(World.getBlockState(this.Farmer.getWorkPlace().west().west().west()).equals(Blocks.STONEBRICK.getDefaultState()))
						{
								b = "West";
						}		
					}
				}
				
				if(World.getBlockState(this.Farmer.getWorkPlace().north()).equals(Blocks.AIR.getDefaultState()))
				{
					if(World.getBlockState(this.Farmer.getWorkPlace().north().north()).equals(Blocks.AIR.getDefaultState()))
					{
						if(World.getBlockState(this.Farmer.getWorkPlace().north().north().north()).equals(Blocks.STONEBRICK.getDefaultState()))
						{
							
								b = "North";
						}		
					}
				}
					
					if(World.getBlockState(this.Farmer.getWorkPlace().south()).equals(Blocks.AIR.getDefaultState()))
					{
						if(World.getBlockState(this.Farmer.getWorkPlace().south().south()).equals(Blocks.AIR.getDefaultState()))
						{
							if(World.getBlockState(this.Farmer.getWorkPlace().south().south().south()).equals(Blocks.STONEBRICK.getDefaultState()))
							{
								
									b = "South";
							}		
						}
					}
						
		
						
						if(World.getBlockState(this.Farmer.getWorkPlace().east()).equals(Blocks.AIR.getDefaultState()))
						{
							if(World.getBlockState(this.Farmer.getWorkPlace().east().east()).equals(Blocks.AIR.getDefaultState()))
							{
								if(World.getBlockState(this.Farmer.getWorkPlace().east().east().east()).equals(Blocks.STONEBRICK.getDefaultState()))
								{
									 
										b = "East";
								}		
							}
						}
		
		
			
				if(World.getBlockState(this.Farmer.getWorkPlace().up().up().west()).equals(Blocks.COBBLESTONE_WALL.getDefaultState()))
				{
					if(World.getBlockState(this.Farmer.getWorkPlace().up().up().west().west()).equals(Blocks.AIR.getDefaultState()))
					{
						if(World.getBlockState(this.Farmer.getWorkPlace().up().up().west().west().west()).equals(Blocks.AIR.getDefaultState()))
						{
								b = "West";
						}		
					}
				}
				
				if(World.getBlockState(this.Farmer.getWorkPlace().up().up().north()).equals(Blocks.COBBLESTONE_WALL.getDefaultState()))
				{
					if(World.getBlockState(this.Farmer.getWorkPlace().up().up().north().north()).equals(Blocks.AIR.getDefaultState()))
					{
						if(World.getBlockState(this.Farmer.getWorkPlace().up().up().north().north().north()).equals(Blocks.AIR.getDefaultState()))
						{
							
								b = "North";
						}		
					}
				}
					
					if(World.getBlockState(this.Farmer.getWorkPlace().up().up().south()).equals(Blocks.COBBLESTONE_WALL.getDefaultState()))
					{
						if(World.getBlockState(this.Farmer.getWorkPlace().up().up().south().south()).equals(Blocks.AIR.getDefaultState()))
						{
							if(World.getBlockState(this.Farmer.getWorkPlace().up().up().south().south().south()).equals(Blocks.AIR.getDefaultState()))
							{
								
									b = "South";
							}		
						}
					}
						
			
						
						
						if(World.getBlockState(this.Farmer.getWorkPlace().up().up().east()).equals(Blocks.COBBLESTONE_WALL.getDefaultState()))
						{
							if(World.getBlockState(this.Farmer.getWorkPlace().up().up().east().east()).equals(Blocks.AIR.getDefaultState()))
							{
								if(World.getBlockState(this.Farmer.getWorkPlace().up().up().east().east().east()).equals(Blocks.AIR.getDefaultState()))
								{
									 
										b = "East";
								}		
							}
						}
						
						
						
						
						if(World.getBlockState(this.Farmer.getWorkPlace().west()).equals(Blocks.AIR.getDefaultState()))
						{
							if(World.getBlockState(this.Farmer.getWorkPlace().west().west()).equals(Blocks.AIR.getDefaultState()))
							{
								if(World.getBlockState(this.Farmer.getWorkPlace().west().west().west()).equals(Blocks.STONEBRICK.getDefaultState()))
								{
										b = "West";
								}		
							}
						}
						
						if(World.getBlockState(this.Farmer.getWorkPlace().north()).equals(Blocks.AIR.getDefaultState()))
						{
							if(World.getBlockState(this.Farmer.getWorkPlace().north().north()).equals(Blocks.AIR.getDefaultState()))
							{
								if(World.getBlockState(this.Farmer.getWorkPlace().north().north().north()).equals(Blocks.STONEBRICK.getDefaultState()))
								{
									
										b = "North";
								}		
							}
						}
							
							if(World.getBlockState(this.Farmer.getWorkPlace().south()).equals(Blocks.AIR.getDefaultState()))
							{
								if(World.getBlockState(this.Farmer.getWorkPlace().south().south()).equals(Blocks.AIR.getDefaultState()))
								{
									if(World.getBlockState(this.Farmer.getWorkPlace().south().south().south()).equals(Blocks.STONEBRICK.getDefaultState()))
									{
										
											b = "South";
									}		
								}
							}
								
				
								
								if(World.getBlockState(this.Farmer.getWorkPlace().east()).equals(Blocks.AIR.getDefaultState()))
								{
									if(World.getBlockState(this.Farmer.getWorkPlace().east().east()).equals(Blocks.AIR.getDefaultState()))
									{
										if(World.getBlockState(this.Farmer.getWorkPlace().east().east().east()).equals(Blocks.STONEBRICK.getDefaultState()))
										{
											 
												b = "East";
										}		
									}
								}
			
			
	
		
		return b;
	}
	
	
	@Override
	public boolean continueExecuting() {
		
		
		if(this.Farmer.getStartJob())
		{
			
			if(MiningMode)
			{
				if(this.shouldExecute())
				{
					this.startExecuting();
				}
					
				return true;
			}
			
			if(BuildingMode)
			{
				if(this.shouldExecute())
				{
					this.startExecuting();
				}
				return true;
			}
		}
		
		return false;
	}
	
	
	@Override
	public void startExecuting() {
	
	
		if(this.BuildingMode)
		{
			this.setPerfectMiningStructure();
			
			String b1 = this.WhichDirection();
			
			if(b1 == null)
			{
			
				return;
			}
			//b1[0] = air air air (right)
			
			
			if(this.PerfectMiningStructure != null)
			{
				for(int x = 0; x < this.PerfectMiningStructure.length; x++)
				{
					for(int y = 0; y < this.PerfectMiningStructure[0].length; y++)
					{
						for(int z = 0; z < this.PerfectMiningStructure[0][0].length; z++)
						{
							if(this.PerfectMiningStructure[x][y][z] != null)
							{
							
							if(b1.contains("East"))
							{
								if(!World.getBlockState(new BlockPos(this.Farmer.getWorkPlace().getX() + x, this.Farmer.getWorkPlace().getY() + y, this.Farmer.getWorkPlace().getZ() + z)).equals(PerfectMiningStructure[x][y][z].getDefaultState()))
								{
								
									
									int hasOr = this.isItemInTileInv(new ItemStack(this.PerfectMiningStructure[x][y][z]));
									
									if( hasOr != -1)
									{
										this.getTE().decrStackSize(hasOr, 1);
										World.setBlockState(new BlockPos(this.Farmer.getWorkPlace().getX() + x, this.Farmer.getWorkPlace().getY() + y, this.Farmer.getWorkPlace().getZ() + z), this.PerfectMiningStructure[x][y][z].getDefaultState());
									
									}
									
								}
							}else if(b1.contains("North"))
							{
								
								if(!World.getBlockState(new BlockPos(this.Farmer.getWorkPlace().getX() + z, this.Farmer.getWorkPlace().getY() + y, this.Farmer.getWorkPlace().getZ() - x)).equals(PerfectMiningStructure[x][y][z].getDefaultState()))
								{
									int hasOr = this.isItemInTileInv(new ItemStack(this.PerfectMiningStructure[x][y][z]));
									if( hasOr != -1)
									{
										this.getTE().decrStackSize(hasOr, 1);
										World.setBlockState(new BlockPos(this.Farmer.getWorkPlace().getX() + z, this.Farmer.getWorkPlace().getY() + y, this.Farmer.getWorkPlace().getZ() - x), this.PerfectMiningStructure[x][y][z].getDefaultState());
									}
								}
							}else if(b1.contains("South"))
							{
								
								if(!World.getBlockState(new BlockPos(this.Farmer.getWorkPlace().getX() - z, this.Farmer.getWorkPlace().getY() + y, this.Farmer.getWorkPlace().getZ() + x)).equals(PerfectMiningStructure[x][y][z].getDefaultState()))
								{
									int hasOr = this.isItemInTileInv(new ItemStack(this.PerfectMiningStructure[x][y][z]));
									if( hasOr != -1)
									{
										this.getTE().decrStackSize(hasOr, 1);
										World.setBlockState(new BlockPos(this.Farmer.getWorkPlace().getX() - z, this.Farmer.getWorkPlace().getY() + y, this.Farmer.getWorkPlace().getZ() + x), this.PerfectMiningStructure[x][y][z].getDefaultState());
									}
								}
								
							}else if(b1.contains("West"))
							{
								if(!World.getBlockState(new BlockPos(this.Farmer.getWorkPlace().getX() - x, this.Farmer.getWorkPlace().getX() + y, this.Farmer.getWorkPlace().getX() - z)).equals(PerfectMiningStructure[x][y][z].getDefaultState()))
								{
									int hasOr = this.isItemInTileInv(new ItemStack(this.PerfectMiningStructure[x][y][z]));
									if( hasOr != -1)
									{
										this.getTE().decrStackSize(hasOr, 1);
										World.setBlockState(new BlockPos(this.Farmer.getWorkPlace().getX() - x, this.Farmer.getWorkPlace().getX() + y, this.Farmer.getWorkPlace().getX() - z), this.PerfectMiningStructure[x][y][z].getDefaultState());
									}
								}
							}
							}
							
						}
					}
				}
			}
		}else
		
		if(this.MiningMode)
		{
			this.setPerfectMiningStructure();
			
			String b1 = this.WhichDirection();
			
			if(b1 == null)
			{
			
				return;
			}
	
			
			if(this.FarmCD == 0)
			{
				if(b1.contains("East"))
				{
					if(World.getBlockState(new BlockPos(this.Farmer.getWorkPlace().getX() + 1, this.Farmer.getWorkPlace().getY(), this.Farmer.getWorkPlace().getZ() + 2)).equals(Blocks.cobblestone.getDefaultState())) 
					{
						World.setBlockToAir(new BlockPos(this.Farmer.getWorkPlace().getX() + 1, this.Farmer.getWorkPlace().getY(), this.Farmer.getWorkPlace().getZ() + 2));
						this.Farmer.getNavigator().tryMoveToXYZ(this.Farmer.getWorkPlace().getX() + 1, this.Farmer.getWorkPlace().getY(), this.Farmer.getWorkPlace().getZ() + 2, 1.0D);
						this.giveItems();
						this.decreaseIronDura();
						this.FarmCD = 80;
					}
				}else if(b1.contains("West"))
				{
					if(World.getBlockState(new BlockPos(this.Farmer.getWorkPlace().getX() - 1, this.Farmer.getWorkPlace().getY(), this.Farmer.getWorkPlace().getZ() - 2)).equals(Blocks.cobblestone.getDefaultState())) 
					{
						World.setBlockToAir(new BlockPos(this.Farmer.getWorkPlace().getX() - 1, this.Farmer.getWorkPlace().getY(), this.Farmer.getWorkPlace().getZ() - 2));
						this.Farmer.getNavigator().tryMoveToXYZ(this.Farmer.getWorkPlace().getX() - 1, this.Farmer.getWorkPlace().getY(), this.Farmer.getWorkPlace().getZ() - 2, 1.0D);
						this.giveItems();
						this.decreaseIronDura();
						this.FarmCD =80;
					}
				}else if(b1.contains("North"))
				{
					if(World.getBlockState(new BlockPos(this.Farmer.getWorkPlace().getX() + 2, this.Farmer.getWorkPlace().getY(), this.Farmer.getWorkPlace().getZ() - 1)).equals(Blocks.cobblestone.getDefaultState())) 
					{
						World.setBlockToAir(new BlockPos(this.Farmer.getWorkPlace().getX() + 2, this.Farmer.getWorkPlace().getY(), this.Farmer.getWorkPlace().getZ() - 1));
						this.Farmer.getNavigator().tryMoveToXYZ(this.Farmer.getWorkPlace().getX() + 2, this.Farmer.getWorkPlace().getY(), this.Farmer.getWorkPlace().getZ() - 1, 1.0D);
						this.giveItems();
						this.decreaseIronDura();
						this.FarmCD = 80;
					}
				}else if(b1.contains("South"))
				{
				
					if(World.getBlockState(new BlockPos(this.Farmer.getWorkPlace().getX() - 2, this.Farmer.getWorkPlace().getY(), this.Farmer.getWorkPlace().getZ() + 1)).equals(Blocks.cobblestone.getDefaultState())) 
					{
						World.setBlockToAir(new BlockPos(this.Farmer.getWorkPlace().getX() - 2, this.Farmer.getWorkPlace().getY(), this.Farmer.getWorkPlace().getZ() + 1));
						this.Farmer.getNavigator().tryMoveToXYZ(this.Farmer.getWorkPlace().getX() - 2, this.Farmer.getWorkPlace().getY(), this.Farmer.getWorkPlace().getZ() + 1, 1.0D);
						this.giveItems();
						this.decreaseIronDura();
						this.FarmCD = 80;
					}
				}
			}else{	
				this.FarmCD--;
			}
			
		}
		
		
		
		super.startExecuting();
	}
	
	public void decreaseIronDura()
	{
		TileEntityKingdomStructureBlock Te = this.getTE();
		
		for(int i = 0; i < Te.getSizeInventory(); i ++)
		{
			if(Te.getStackInSlot(i) != null)
			{
				if(Te.getStackInSlot(i).isItemEqual(new ItemStack(Items.IRON_PICKAXE)))
				{
					if(Te.getStackInSlot(i).getItemDamage() < Te.getStackInSlot(i).getMaxDamage())
						Te.getStackInSlot(i).setItemDamage(Te.getStackInSlot(i).getItemDamage() + 1);
					else{
						Te.inventory[i] = null;
					}
				}
			}
		}
	}
	
		public void giveItems()
		{
			boolean PlaceLog = true, PlaceSapling = false, PlaceApple = false;
			
			 for(int k = 0; k < this.getTE().inventory.length; k ++)
			 {
				 if(this.getTE().inventory[k] != null)
				 {
					if(this.getTE().inventory[k].isItemEqual(new ItemStack(Blocks.cobblestone)))
					{
						if(this.getTE().inventory[k].stackSize < 64)
						{
							this.getTE().inventory[k].stackSize ++;
							PlaceLog = false;
							break;
						}
					}
				 }
			 }
			
			
			if(rand.nextInt(10) == 0)
			{
				PlaceSapling = true;
				for(int m = 0; m < this.getTE().inventory.length; m ++)
				 {
					 if(this.getTE().inventory[m] != null)
					 {
						 
						if(this.getTE().inventory[m].isItemEqual(new ItemStack(Items.coal)))
						{
							if(this.getTE().inventory[m].stackSize < 64)
							{	
								this.getTE().inventory[m].stackSize += 1;
								PlaceSapling = false;
								break;
							}
						}
					 }
				
					
				 }
				
			}
			
			if(rand.nextInt(50) == 0)
			{
				PlaceApple = true;
				for(int m = 0; m < this.getTE().inventory.length; m ++)
				 {
					 if(this.getTE().inventory[m] != null)
					 {
						 
						if(this.getTE().inventory[m].isItemEqual(new ItemStack(Blocks.iron_ore)))
						{
							if(this.getTE().inventory[m].stackSize < 63)
							{	
								this.getTE().inventory[m].stackSize += 1;
								PlaceApple = false;
								break;
							}
						}
					 }
				
					
				 }
			}
			
			 if(PlaceLog)
				{
					
					 for(int j2 = 0; j2 < this.getTE().inventory.length; j2 ++)
					 {
						 if(this.getTE().inventory[j2] == null)
						 {
							 this.getTE().inventory[j2] = new ItemStack(Blocks.cobblestone);
							 PlaceLog = false;
							 break;
						 }
					 }
					
				}
			 
			 if(PlaceSapling)
				{
					 for(int j3 = 0; j3 < this.getTE().inventory.length; j3 ++)
					 {
						 if(this.getTE().inventory[j3] == null)
						 {
							this.getTE().inventory[j3] = new ItemStack(Items.coal);
							this.getTE().inventory[j3].stackSize += 1;
							PlaceSapling = false;
							break;
						 }
					}
				}
						 
			 if(PlaceApple)
				{
					 for(int j4 = 0; j4 < this.getTE().inventory.length; j4 ++)
					 {
						 if(this.getTE().inventory[j4] == null)
						 {
							this.getTE().inventory[j4] = new ItemStack(Blocks.iron_ore);
							this.getTE().inventory[j4].stackSize += 1;
							PlaceSapling = false;
							break;
						 }
					}
				}	 
			
			
			
		}
	
	
	public boolean ReturnInvToTE()
	{
		TileEntityKingdomStructureBlock Te = this.getTE();
	
		
		int TEIndex;
	if(Te != null)
	{
			for(int TE = 0; TE < Te.inventory.length; TE++)
			{
				if(Te.inventory[TE] == null)
				{
					TEIndex = TE;
					for(int i = 0; i < this.Farmer.getInventory().length; i++)
					{
						if(this.Farmer.getInventory()[i] != null)
						{
								
								Te.inventory[TEIndex] = this.Farmer.getInventory()[i];
								this.Farmer.getInventory()[i] = null;
								
							//	Farmer.setCurrentItemOrArmor(0, null);
							
						}
					}
				}
			
			}
	
	}
		if(this.Farmer.getInventory() != null)
		{
			return false;
		}
		
		return true;
	}
	
	public boolean isInFarmerInv(ItemStack SearchingFor)
	{
		boolean hasItem = false;
		
		for(int i = 0; i < this.Farmer.getInventory().length; i ++)
		{
			if(this.Farmer.getInventory()[i] != null)
			{
				if(Farmer.getInventory()[i].isItemDamaged())
				{
					if(SearchingFor.isItemStackDamageable())
						SearchingFor.setItemDamage(Farmer.getInventory()[i].getItemDamage());
				}
				
				if(this.Farmer.getInventory()[i].getIsItemStackEqual(SearchingFor))
				{
					hasItem = true;
				}
		/*	
				if(this.Farmer.getInventory()[i].toString().contains("seeds"))
				{
					if(this.Farmer.getInventory()[i].stackSize < 1)
					{
						this.Farmer.getInventory()[i] = null;
					}
				}
				*/
			}
		}
		
		
		return hasItem;
	}
	
	
	public TileEntityKingdomStructureBlock getTE()
	{
		TileEntityKingdomStructureBlock B1 = null;
		
		for(int j = 0; j < World.loadedTileEntityList.size(); j ++)
		{
			
			if(World.loadedTileEntityList.get(j).getPos() != null)
			{
				if(World.loadedTileEntityList.get(j) instanceof TileEntityKingdomStructureBlock)
				{
						BlockPos SupplyPos = World.loadedTileEntityList.get(j).getPos();
						
						
						if(Farmer.getWorkPlace().distanceSq(SupplyPos.getX(), SupplyPos.getY(), SupplyPos.getZ()) <= 600)
						{
							 
							 B1 = (TileEntityKingdomStructureBlock)World.loadedTileEntityList.get(j);
							
							 return B1;
						}
				}
			}
		}
		
		
		return B1;
	}
	
	
	public int isItemInTileInv(ItemStack SearchingFor) // returns index of has items otherwise -1 
	{
		int hasItem = -1;
		TileEntityKingdomStructureBlock b = this.getTE();
		
		
		for(int i =0; i < b.getSizeInventory(); i ++)
		{
			
			if(b.getStackInSlot(i) != null)
			{
				if(b.getStackInSlot(i).isItemEqual(SearchingFor))
				{
					
					hasItem = i;
					return hasItem;
					
				}else{	
					if(b.getStackInSlot(i).isItemDamaged())
					{
						ItemStack Copy = b.getStackInSlot(i).copy();
						Copy.setItemDamage(0);
						
						if(Copy.isItemEqual(SearchingFor))
						{
							hasItem = i;
							return hasItem;
						}
					}
				}
			}
		}
		
		return hasItem;
	}
	
	
}