package com.waabbuffet.kotrt.entities.KingdomAI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;






import com.waabbuffet.kotrt.entities.Kingdom.EntityFarmer;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBlock;


public class WheatFarmerAI extends EntityAIBase {

	EntityPlayer Player;
	EntityFarmer Farmer;
	
	List<BlockPos> HoeBlocks = new ArrayList<BlockPos>();
	List<BlockPos> SeedBlocks = new ArrayList<BlockPos>();
	List<BlockPos> WheatBlocks = new ArrayList<BlockPos>();
	
	World World;
	Random rand = new Random();
	int FarmCD, ListIndex = 0;
	boolean HoeMode, SeedMode, HarvestMode;
	
	//TODO Try to not take the seeds and maybe leave it in the TE inv
	public WheatFarmerAI(EntityFarmer farmer, World world)
	{
		
		Farmer = farmer;
	//	Inventory = farmer.getInventory();
		World = world;
		FarmCD = 0;
		
		this.setMutexBits(3);
		
	}


	
	@Override
	public boolean shouldExecute() 
	{
		//****************************************************************************************************************************************************************//
		//****************************************************************************************************************************************************************//
		//****************************************************************************************************************************************************************//
	if(this.Farmer.getStartJob())
	{
		if(this.HoeBlocks.size() == 0)
		{
		
			int IronHoeIndex = this.isItemInTileInv(new ItemStack(Items.IRON_HOE), false);
			
			if(IronHoeIndex != -1 || this.isInFarmerInv(new ItemStack(Items.IRON_HOE)))
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
								
								if(!this.AlreadyInsideList(B1, this.HoeBlocks))
								{
								
									this.HoeBlocks.add(B1);
								}
							}	
						}		
					}
				}
				
				if(HoeBlocks.size() > 0)
				{
					
					this.HoeMode = true;
					return true;
				}else
				{
					this.HoeMode = false;
					this.ReturnInvToTE();
				}
				
			}
		}else{
			
			this.HoeMode = true;
			return true;
		}
	
			//****************************************************************************************************************************************************************//
			//****************************************************************************************************************************************************************//
			//****************************************************************************************************************************************************************//
			
			
			//Seed Mode

			if(!this.HoeMode)
			{
				
				if(SeedBlocks.size() == 0)
				{
					int SeedsIndex = this.isItemInTileInv(new ItemStack(Items.wheat_seeds), false);
					
					if(SeedsIndex != -1 || this.isInFarmerInv(new ItemStack(Items.wheat_seeds)))
					{
						for(int x = 0; x < 5; x ++)
						{
							for(int z = 0; z < 5; z ++)
							{
								BlockPos B2 = new BlockPos(this.Farmer.getWorkPlace().getX() + x,  this.Farmer.getWorkPlace().getY()- 1, this.Farmer.getWorkPlace().getZ() + z);
								
								
								if((!World.getBlockState(B2).toString().contains(":water")))
								{
									if(World.getBlockState(B2).toString().contains(":farmland") && World.getBlockState(B2.up()).toString().contains(":air"))
									{
										if(!this.AlreadyInsideList(B2, this.SeedBlocks))
										{
											
											this.SeedBlocks.add(B2);
										}
									}	
								}		
							}
						}
					}
					
					if(SeedBlocks.size() > 0)
					{
					
						this.SeedMode = true;
						return true;
					}else
						{
							this.SeedMode = false;
							this.ReturnInvToTE();
						}
				}else{
					this.SeedMode = true;
					return true;
				}
			}
			
			//****************************************************************************************************************************************************************//
			//****************************************************************************************************************************************************************//
			//****************************************************************************************************************************************************************//
			//Harvest Mode
			if(!HoeMode && !SeedMode)
			{
			
					if(this.WheatBlocks.size() == 0)
					{
						
						for(int x = 0; x < 5; x ++)
						{
							for(int z = 0; z < 5; z ++)
							{
								BlockPos B3 = new BlockPos(this.Farmer.getWorkPlace().getX() + x,  this.Farmer.getWorkPlace().getY(), this.Farmer.getWorkPlace().getZ() + z);
								
								
								if((!World.getBlockState(B3).toString().contains(":water")))
								{
									
								//	System.out.println("The block is: " + World.getBlockState(B1));
									
									if(World.getBlockState(B3).toString().contains("wheat[age=7]"))
									{
										
										if(!this.AlreadyInsideList(B3, this.WheatBlocks))
										{
										
											this.WheatBlocks.add(B3);
										}
									}	
								}		
							}
						}
						
						if(WheatBlocks.size() > 0)
						{
							
							this.HarvestMode = true;
							return true;
						}else
						{
							this.HarvestMode = false;
							this.ReturnInvToTE();
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
	//		if(!this.HoeMode && !this.SeedMode)
	//		{
				if(this.shouldExecute())
				{
					this.startExecuting();
				}
				
				return true;
	//		}
		
		}
		
		if(this.HoeMode)
		{
			
			if(this.shouldExecute())
		{
				this.startExecuting();
			}
		
			return true;
		}
		
		if(this.SeedMode)
		{
	//		if(!this.HoeMode)
	//		{
				if(this.shouldExecute())
				{
					this.startExecuting();
				}
				return true;
	//		}
		
		}
		
		return false;
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
	
	@Override
	public void startExecuting() {
	
		
		 if(this.HoeMode)
		 {
			
			if(!this.isInFarmerInv(new ItemStack(Items.IRON_HOE)))
			{ 
				
				 for(int i = 0; i < this.Farmer.getInventory().length; i ++)
				 {
					 if(this.Farmer.getInventory()[i] == null)
					 {
						// this.Farmer.getInventory()[i] = this.getTE().inventory[this.isItemInTileInv(new ItemStack(Items.IRON_HOE), true)];
						int HasOrNaw = this.isItemInTileInv(new ItemStack(Items.IRON_HOE), false);
						
						if( HasOrNaw != -1)
						{
							// ItemStack b = this.getTE().inventory[HasOrNaw].copy();
							// b.setItemDamage(this.getTE().inventory[HasOrNaw].getItemDamage());
							
						//	 this.Farmer.getInventory()[i] = b;
							 
							 
							 this.getTE().inventory[HasOrNaw] = null;	 
							 this.Farmer.setCurrentItemOrArmor(0, new ItemStack(Items.IRON_HOE));
						}
						
					     
						 break;
					 }
				 }
			}
			
			 if(this.isInFarmerInv(new ItemStack(Items.IRON_HOE))){
				 if(this.HoeBlocks.size() != 0)
				 {
					 
					 if(this.FarmCD == 0)
					 {
						
						 if(this.ListIndex < this.HoeBlocks.size())
						 {
							
						 
						 //TODO Find better water placement yo
						 if(ListIndex == 0)
						 {
							 World.setBlockState(new BlockPos(this.Farmer.getWorkPlace().getX() + 2,  this.Farmer.getWorkPlace().getY()- 1, this.Farmer.getWorkPlace().getZ() + 2), Blocks.water.getDefaultState());
							 
							 
						 }
						 this.Farmer.getNavigator().tryMoveToXYZ(HoeBlocks.get(ListIndex).getX() + 2, HoeBlocks.get(ListIndex).getY(), HoeBlocks.get(ListIndex).getZ() + 1, 1.0D);
						 World.setBlockState(HoeBlocks.get(ListIndex), Blocks.FARMLAND.getDefaultState());
						 
						
						 
						 for(int j = 0; j < this.Farmer.getInventory().length; j ++)
						 {
							 if(this.Farmer.getInventory()[j] != null)
							 {
								 if(this.Farmer.getInventory()[j].isItemEqual(new ItemStack(Items.IRON_HOE)))
								 {
									 this.Farmer.getInventory()[j].setItemDamage(this.Farmer.getInventory()[j].getItemDamage() + 1);
									
							
								 }
							 }
						 }
						 
						
						 
						 
						 HoeBlocks.remove(ListIndex);
						 FarmCD = 40;
						 
						 
						 this.ListIndex++;
							 if(ListIndex >= this.HoeBlocks.size())
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
					
					 this.HoeMode = false;
					 ReturnInvToTE();
				 }
				 
			 }
				 
		 }else
			//****************************************************************************************************************************************************************//
			 
		 if(this.SeedMode)
		 {
		
			 if(this.isItemInTileInv(new ItemStack(Items.wheat_seeds), false) != -1){
				 if(this.SeedBlocks.size() != 0)
				 {
				
					 if(this.FarmCD == 0)
					 {
						 if(this.ListIndex < this.SeedBlocks.size())
						 {
						
						 this.Farmer.getNavigator().tryMoveToXYZ(SeedBlocks.get(ListIndex).getX() + 2, SeedBlocks.get(ListIndex).getY(), SeedBlocks.get(ListIndex).getZ() + 1, 1.0D);
						 
						 
						 for(int j = 0; j < this.getTE().inventory.length; j ++)
						 {
							 if(this.getTE().inventory[j] != null)
							 {
								 if(this.getTE().inventory[j].toString().contains("seeds"))
								 {
									 
									if(this.getTE().inventory[j].stackSize > 1)
									{
										
									
										this.getTE().inventory[j].stackSize--;
										World.setBlockState(SeedBlocks.get(ListIndex).up(), Blocks.WHEAT.getDefaultState());
										
										
									}else{
										this.getTE().inventory[j] = null;
										World.setBlockState(SeedBlocks.get(ListIndex).up(), Blocks.WHEAT.getDefaultState());
									}
									 
									// if(this.Inventory[j].stackSize > 0){
									// 	this.Inventory[j].stackSize--;
								//	 	System.out.println("yo" + this.Inventory[j].stackSize);
							//		 	break;
								//	 }
									break;
								 }
							 }
						 }
									 
						 SeedBlocks.remove(ListIndex);
						 FarmCD = 40;
						 
						 	this.ListIndex++;
							 if(ListIndex >= this.SeedBlocks.size())
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
				
					 this.SeedMode = false;
					 ReturnInvToTE();
				 }
		 }
			 
		 
		 }else
		 
		//****************************************************************************************************************************************************************//
		 //HarvestMode (Needs cleaning)
		 if(this.HarvestMode)
		 {
			 boolean PlaceWheat = true, PlaceSeeds = false;
				 if(this.WheatBlocks.size() != 0)
				 {
				
					 if(this.FarmCD == 0)
					 {
						 if(this.ListIndex < this.WheatBlocks.size())
						 {
						
						 this.Farmer.getNavigator().tryMoveToXYZ(WheatBlocks.get(ListIndex).getX() + 2, WheatBlocks.get(ListIndex).getY(), WheatBlocks.get(ListIndex).getZ() + 1, 1.0D);
						 
						 
						 for(int j = 0; j < this.getTE().inventory.length; j ++)
						 {
							 if(this.getTE().inventory[j] == null)
							 {
								 	
									World.setBlockState(WheatBlocks.get(ListIndex), Blocks.AIR.getDefaultState());
									World.setBlockState(WheatBlocks.get(ListIndex).down(), Blocks.DIRT.getDefaultState());
									this.HoeMode = false;
									
									for(int k = 0; k < this.getTE().inventory.length; k ++)
									 {
										 if(this.getTE().inventory[k] != null)
										 {
											if(this.getTE().inventory[k].isItemEqual(new ItemStack(Items.WHEAT)))
											{
												if(this.getTE().inventory[k].stackSize < 64)
												{
													this.getTE().inventory[k].stackSize ++;
													PlaceWheat = false;
													break;
												}
											}
										 }
									 }
									
									
									if(rand.nextInt(2) == 0)
									{
										PlaceSeeds = true;
										for(int m = 0; m < this.getTE().inventory.length; m ++)
										 {
											 if(this.getTE().inventory[m] != null)
											 {
												 
												if(this.getTE().inventory[m].isItemEqual(new ItemStack(Items.wheat_seeds)))
												{
													if(this.getTE().inventory[m].stackSize < 62)
													{	
														this.getTE().inventory[m].stackSize += 2;
														PlaceSeeds = false;
														break;
													}
												}
											 }
										
											
										 }
										
									}
									
									break;
							 }
							 
						 }
						 
						 if(PlaceWheat)
							{
								
								 for(int j2 = 0; j2 < this.getTE().inventory.length; j2 ++)
								 {
									 if(this.getTE().inventory[j2] == null)
									 {
										 this.getTE().inventory[j2] = new ItemStack(Items.WHEAT);
										 PlaceWheat = false;
										 break;
									 }
								 }
								
							}
						 
						 if(PlaceSeeds)
							{
								 for(int j3 = 0; j3 < this.getTE().inventory.length; j3 ++)
								 {
									 if(this.getTE().inventory[j3] == null)
									 {
										this.getTE().inventory[j3] = new ItemStack(Items.wheat_seeds);
										this.getTE().inventory[j3].stackSize += 2;
										PlaceSeeds = false;
										break;
									 }
								}
							}
									 
						 WheatBlocks.remove(ListIndex);
						 FarmCD = 40;
						 
						     this.ListIndex++;
							 if(ListIndex >= this.WheatBlocks.size())
							 {
								 ListIndex = 0;
							 }
						 }else
						 {
							 ListIndex = 0;
						 }
						 
						
						 
					 }else{
							 this.FarmCD--;
						 }
					 
				 }else {
				
					 this.HarvestMode = false;
					 ReturnInvToTE();
				 }
			 
		 }
		 
		super.startExecuting();
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see net.minecraft.entity.ai.EntityAIBase#shouldExecute()
	 * 
	 * Rewrite with three booleans, 1 for planting, 1 for harvesting, 1 for hoeing
	 * inside the should execute will determine what should be done
	 */
	
	
	//does not count stack size
	public boolean ReturnInvToTE()
	{
		TileEntityKingdomStructureBlock Te = this.getTE();
	
		if(Te == null)
		{
			return false;
		}
		
		int TEIndex;
		
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
						
							 B1 = (TileEntityKingdomStructureBlock)World.getTileEntity(SupplyPos);
						}
				}
			}
		}
		
		
		return B1;
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
						
						if(Farmer.getWorkPlace().distanceSq(SupplyPos.getX(), SupplyPos.getY(), SupplyPos.getZ()) <= 600)
						{
							
							TileEntityKingdomStructureBlock b = (TileEntityKingdomStructureBlock)World.getTileEntity(SupplyPos);
							
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
		
		
		
		return hasItem;
	}
	
	
}