package com.waabbuffet.kotrt.entities.KingdomAI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.waabbuffet.kotrt.entities.Kingdom.EntityFarmer;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBlock;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TreeFarmerAI extends EntityAIBase {

	EntityPlayer Player;
	EntityFarmer Farmer;
	World World;
	int FarmCD, ListIndex = 0;
	boolean DirtMode, HarvestMode;
	
	Random rand = new Random();
	List<BlockPos> DirtBlocks = new ArrayList<BlockPos>();
	List<BlockPos> HarvestBlocks = new ArrayList<BlockPos>();
	
	public TreeFarmerAI(EntityFarmer farmer, World world)
	{
		World = world;
		Farmer = farmer;
		
		DirtMode = false;
		HarvestMode = false;
		
		FarmCD = 0;
		this.setMutexBits(3);
		
	}
	

	@Override
	public boolean shouldExecute() 
	{
		//****************************************************************************************************************************************************************//
		//****************************************************************************************************************************************************************//
		
		if(this.Farmer.getStartJob())
		{
			
		if(this.DirtBlocks.size() == 0)
		{
		
			int SaplingIndex = this.isItemInTileInv(new ItemStack(Blocks.SAPLING), false);
			
			if(SaplingIndex != -1 || this.isInFarmerInv(new ItemStack(Blocks.SAPLING)))
			{
				
				for(int x = 0; x < 5; x ++)
				{
					for(int z = 0; z < 5; z ++)
					{
						BlockPos B1 = new BlockPos(this.Farmer.getWorkPlace().getX() + x,  this.Farmer.getWorkPlace().getY()- 1, this.Farmer.getWorkPlace().getZ() + z);
					
						
						if((!World.getBlockState(B1).toString().contains(":water")))
						{
							
							if(World.getBlockState(B1).toString().contains(":grass") || World.getBlockState(B1).toString().contains(":dirt"))
							{
							
								// minecraft:sapling[stage=0,type=oak]
								if(World.getBlockState(B1.up()).toString().contains(":air")){
									if(!this.AlreadyInsideList(B1, this.DirtBlocks))
									{
									
										this.DirtBlocks.add(B1);
									}
								}
							}	
						}		
					}
				}
				
				if(DirtBlocks.size() > 0)
				{
					
					this.DirtMode = true;
					return true;
				}else
				{
					this.DirtMode = false;
					this.ReturnInvToTE();
				}
				
			}
		}else{
			
			this.DirtMode = true;
			return true;
		}
		//****************************************************************************************************************************************************************//
		//****************************************************************************************************************************************************************//
		//harvest
		if(!this.DirtMode)
		{
			if(this.HarvestBlocks.size() == 0)
			{
			
				int IronAxeIndex = this.isItemInTileInv(new ItemStack(Items.IRON_AXE), false);
				
				if(IronAxeIndex != -1 || this.isInFarmerInv(new ItemStack(Items.IRON_AXE)))
				{
					for(int y = 0; y < 18; y ++)
					{
						for(int x = 0; x < 8; x ++)
						{
							for(int z = 0; z < 8; z ++)
							{
								BlockPos B1 = new BlockPos(this.Farmer.getWorkPlace().getX() + x,  this.Farmer.getWorkPlace().getY() + y, this.Farmer.getWorkPlace().getZ() + z);
							
								
									if(World.getBlockState(B1).equals(Blocks.LOG.getDefaultState()))
									{
										
										if(!this.AlreadyInsideList(B1, this.HarvestBlocks))
										{
											
											this.HarvestBlocks.add(B1);
										}
									}	
								
							}
						}
					}
					
					if(HarvestBlocks.size() > 0)
					{
						
						this.HarvestMode = true;
						return true;
					}else
					{
						this.HarvestMode = false;
						this.ReturnInvToTE();
					}
					
				}
			}else{
				
				this.HarvestMode = true;
				return true;
			}
		}
		
		}
		return false;
	}
	
	@Override
	public boolean continueExecuting() 
	{
		if(this.HarvestMode)
		{
			
				if(this.shouldExecute())
				{
					this.startExecuting();
				}
				
				return true;
			
		
		}
		
		if(this.DirtMode)
		{
			if(!this.HarvestMode)
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
	
		//****************************************************************************************************************************************************************//
		//****************************************************************************************************************************************************************//
				
		if(this.DirtMode)
		 {
			
			 if(this.isItemInTileInv(new ItemStack(Blocks.SAPLING), false) != -1){
				 if(this.DirtBlocks.size() != 0)
				 {
				
					 if(this.FarmCD == 0)
					 {
						 if(this.ListIndex < this.DirtBlocks.size())
						 {
						
						 this.Farmer.getNavigator().tryMoveToXYZ(DirtBlocks.get(ListIndex).getX() + 1 + rand.nextInt(2), DirtBlocks.get(ListIndex).getY(), DirtBlocks.get(ListIndex).getZ() + 1 + rand.nextInt(2), 1.0D);
						 
						 
						 for(int j = 0; j < this.getTE().inventory.length; j ++)
						 {
							 if(this.getTE().inventory[j] != null)
							 {
								 if(this.getTE().inventory[j].toString().contains("sapling"))
								 {
									 
									if(this.getTE().inventory[j].stackSize > 1)
									{
										
									
										this.getTE().inventory[j].stackSize--;
										World.setBlockState(DirtBlocks.get(ListIndex).up(), Blocks.SAPLING.getDefaultState());
										
										
									}else{
										this.getTE().inventory[j] = null;
										World.setBlockState(DirtBlocks.get(ListIndex).up(), Blocks.SAPLING.getDefaultState());
									}
									 
								
									break;
								 }
							 }
						 }
									 
						 DirtBlocks.remove(ListIndex);
						 FarmCD = 40;
						 
						 	this.ListIndex++;
							 if(ListIndex >= this.DirtBlocks.size())
							 {
								 ListIndex = 0;
							 }
						 }else{
							 ListIndex = 0;
						 }
						 
						
						 
					 }else{
							 this.FarmCD--;
						 }
					 
				 }else {
				
					 this.DirtMode = false;
					 ReturnInvToTE();
				 }
		
		
		
			 }
		 }else 
		
		//****************************************************************************************************************************************************************//
		//****************************************************************************************************************************************************************//
		
		if(this.HarvestMode)
		{
			boolean PlaceLog = true, PlaceSapling = false, PlaceApple = false;
			if(!this.isInFarmerInv(new ItemStack(Items.IRON_AXE)))
			{ 
				
				 for(int i = 0; i < this.Farmer.getInventory().length; i ++)
				 {
					 if(this.Farmer.getInventory()[i] == null)
					 {
						
						int HasOrNaw = this.isItemInTileInv(new ItemStack(Items.IRON_AXE), false);
						
						if( HasOrNaw != -1)
						{
							 ItemStack b = this.getTE().inventory[HasOrNaw].copy();
							
							
							 this.Farmer.getInventory()[i] = b;
							 
							 
							 this.getTE().inventory[HasOrNaw] = null;
							 
							 
						
						}
						
					     
						 break;
					 }
				 }
			}
			
			 if(this.isInFarmerInv(new ItemStack(Items.IRON_AXE)))
			 {
				 if(this.HarvestBlocks.size() != 0)
				 {
					
					 if(this.FarmCD == 0)
					 {
						 
						 if(this.ListIndex < this.HarvestBlocks.size())
						 {
							
							 for(int j0 = 0; j0 < this.getTE().inventory.length; j0++)
							 {
								 if(this.getTE().inventory[j0] == null)
								 {	 
									
									 this.Farmer.getNavigator().tryMoveToXYZ(HarvestBlocks.get(ListIndex).getX() + 2 + rand.nextInt(2), HarvestBlocks.get(ListIndex).getY(), HarvestBlocks.get(ListIndex).getZ() + rand.nextInt(2) + 1, 1.0D);
									 World.setBlockState(HarvestBlocks.get(ListIndex), Blocks.AIR.getDefaultState());
									 
									
									 
									 for(int j1 = 0; j1 < this.Farmer.getInventory().length; j1 ++)
									 {
										 if(this.Farmer.getInventory()[j1] != null)
										 {
											 if(this.Farmer.getInventory()[j1].isItemEqual(new ItemStack(Items.IRON_AXE)))
											 {
												 this.Farmer.getInventory()[j1].setItemDamage(this.Farmer.getInventory()[j1].getItemDamage() + 1);
												
										
											 }
										 }
									 }
								 
									 
									 for(int k = 0; k < this.getTE().inventory.length; k ++)
									 {
										 if(this.getTE().inventory[k] != null)
										 {
											if(this.getTE().inventory[k].isItemEqual(new ItemStack(Blocks.LOG)))
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
									
									
									if(rand.nextInt(8) == 0)
									{
										PlaceSapling = true;
										for(int m = 0; m < this.getTE().inventory.length; m ++)
										 {
											 if(this.getTE().inventory[m] != null)
											 {
												 
												if(this.getTE().inventory[m].isItemEqual(new ItemStack(Blocks.SAPLING)))
												{
													if(this.getTE().inventory[m].stackSize < 62)
													{	
														this.getTE().inventory[m].stackSize += 2;
														PlaceSapling = false;
														break;
													}
												}
											 }
										
											
										 }
										
									}
									
									if(rand.nextInt(7) == 0)
									{
										PlaceApple = true;
										for(int m = 0; m < this.getTE().inventory.length; m ++)
										 {
											 if(this.getTE().inventory[m] != null)
											 {
												 
												if(this.getTE().inventory[m].isItemEqual(new ItemStack(Items.APPLE)))
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
									
									break;
							 }
							 
						 }
						 
						 if(PlaceLog)
							{
								
								 for(int j2 = 0; j2 < this.getTE().inventory.length; j2 ++)
								 {
									 if(this.getTE().inventory[j2] == null)
									 {
										 this.getTE().inventory[j2] = new ItemStack(Blocks.LOG);
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
										this.getTE().inventory[j3] = new ItemStack(Blocks.SAPLING);
										this.getTE().inventory[j3].stackSize += 2;
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
										this.getTE().inventory[j4] = new ItemStack(Items.APPLE);
										this.getTE().inventory[j4].stackSize += 1;
										PlaceSapling = false;
										break;
									 }
								}
							}	 
							 
						
						
						 
						 
						 
						 
						 HarvestBlocks.remove(ListIndex);
						 FarmCD = 20;
						 
						 
						 this.ListIndex++;
							 if(ListIndex >= this.HarvestBlocks.size())
							 {
								 ListIndex = 0;
							 }
						 }else
						 	{
							 ListIndex= 0;
						 	}
						 
							 	
						 
						 
					 }else{
						 this.FarmCD--;
					 }
					 
				 }else {
					
					 this.HarvestMode = false;
					 ReturnInvToTE();
				 }
			 }
		}
		
		super.startExecuting();
	}
	
	public boolean AlreadyInsideList(BlockPos Checking, List<BlockPos> BlockList)
	{
		boolean Inside = false;
		
		for(int i = 0; i < BlockList.size(); i ++)
		{
			if(BlockList.get(i).getX() == Checking.getX() && BlockList.get(i).getZ() == Checking.getZ())
			{
				Inside = true;
			}
		}	
		
		return Inside;
		
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
						
						if(World.loadedTileEntityList.get(j) != null)
						{
							if(((TileEntityKingdomStructureBlock)World.loadedTileEntityList.get(j)).structure.getName() != null)
							{
								if(((TileEntityKingdomStructureBlock)World.loadedTileEntityList.get(j)).structure.getName().contains("Lumberjack Guild"))
								{
									if(Farmer.getWorkPlace().distanceSq(SupplyPos.getX(), SupplyPos.getY(), SupplyPos.getZ()) <= 1400)
									{
								
										B1 = (TileEntityKingdomStructureBlock)World.getTileEntity(SupplyPos);
									}
								}
							}
						}
				}
			}
		}
		
		
		return B1;
	}
	
	public boolean ReturnInvToTE()
	{
		TileEntityKingdomStructureBlock Te = this.getTE();
	
		
		int TEIndex;
		
		for(int TE = 0; TE < Te.inventory.length; TE++)
		{
			if(Te.inventory[TE] != null)
			{
				TEIndex = TE;
				for(int i = 0; i < this.Farmer.getInventory().length; i++)
				{
					if(this.Farmer.getInventory()[i] != null)
					{	
						if(this.Farmer.getInventory()[i].isStackable()){
							if(this.getTE().inventory[TEIndex].isItemEqual(this.Farmer.getInventory()[i]))
							{
								if((this.getTE().inventory[TEIndex].stackSize + this.Farmer.getInventory()[i].stackSize) <= 64)
								{					
									this.getTE().inventory[TEIndex].stackSize +=  this.Farmer.getInventory()[i].stackSize;
									this.Farmer.getInventory()[i] = null;
									break;
								}
								
							}
					}
			
					}
				}
						
				
			}
		}
		
		
	
		
		

		if(this.Farmer.getInventory() != null)
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
		
		return true;
	}
	public int isItemInTileInv(ItemStack SearchingFor, boolean SetNull) // returns index of has items otherwise -1 
	{
		int hasItem = -1;
		
		for(int j = 0; j < World.loadedTileEntityList.size(); j ++)
		{
			
			if(World.loadedTileEntityList.get(j).getPos() != null)
			{
				if(World.loadedTileEntityList.get(j) instanceof TileEntityKingdomStructureBlock)
				{
						BlockPos SupplyPos = World.loadedTileEntityList.get(j).getPos();
					
						if(Farmer.getWorkPlace().distanceSq(SupplyPos.getX(), SupplyPos.getY(), SupplyPos.getZ()) <= 2000)
						{
						
							TileEntityKingdomStructureBlock b = (TileEntityKingdomStructureBlock)World.getTileEntity(SupplyPos);
							
						if(b.structure.getName().contains("Lumberjack Guild"))
						{
							
								if(b.inventory != null)
								{
									for(int k = 0; k < b.inventory.length; k++) //tile inv, which finds seeds yea
									{
										if(b.inventory[k] != null)
										{
											if(b.inventory[k].isItemDamaged())
											{
												if(SearchingFor.isItemStackDamageable())
													SearchingFor.setItemDamage(b.inventory[k].getItemDamage());
											}
											
											
												if(b.inventory[k].isItemEqual(SearchingFor))
												{
													hasItem = k;
												
													if(SetNull)
													{
														
														b.inventory[k] = null;
													}
													
													return hasItem;
												}
											
												
										}
									}
								}
						}
						}
				}
			}
		}
		
		
		
		return hasItem;
	}
}
