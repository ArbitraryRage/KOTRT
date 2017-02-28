package com.waabbuffet.kotrt.entities.KingdomAI;

import java.util.ArrayList;
import java.util.List;

import com.waabbuffet.kotrt.entities.Kingdom.EntityFarmer;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBlock;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ReedMelonFarmerAI extends EntityAIBase{

	EntityFarmer Farmer;
	World World;
	int FarmerCD, ListIndex; 
	
	boolean HoeMode, HarvestingMode;
	List<BlockPos> ReedBlocks = new ArrayList<BlockPos>();
	List<BlockPos> MelonBlocks = new ArrayList<BlockPos>();
	
	
	public ReedMelonFarmerAI(EntityFarmer farmer, World world) {
		
		this.Farmer = farmer;
		this.World = world;
		
		this.HoeMode = false;
		this.HarvestingMode = false;
		this.ListIndex = 0;
		this.setMutexBits(3);
		
		
		//Hard Coded Structure
		
		//*********************************************************//
	
		
	}

	@Override
	public boolean shouldExecute() {
	
	if(this.Farmer.getStartJob())
	{
		//TODO Never Leaves THis AI problem, there is always a dirt block here...Change to scan for things to pick/ plant, if none exit
		if(this.ReedBlocks.size() == 0)
		{
			
			int HasOrNaw = this.isItemInTileInv(new ItemStack(Items.iron_hoe), false);
			
			if(HasOrNaw != -1 )
			{
				for(int x = 0; x < 8; x ++)
				{
					for(int z = 0; z < 8; z ++)
					{
						BlockPos B1 = new BlockPos(this.Farmer.getWorkPlace().getX() + x,  this.Farmer.getWorkPlace().getY()- 1, this.Farmer.getWorkPlace().getZ() + z);
	
			
								if(World.getBlockState(B1).toString().contains(":grass") || World.getBlockState(B1).toString().contains(":dirt"))
								{			
										this.ReedBlocks.add(B1);		
								}
						

						if(x != 7 && x != 0 && z != 0 && z != 7&& (z == 1 || x == 1))
						{
							if(!World.getBlockState(B1).equals(Blocks.water.getDefaultState()))
							{
								World.setBlockState(B1, Blocks.water.getDefaultState());
							}
						}
						
					
						
						if(x != 7 && x != 0 && z != 0 && z != 7 && (z == 6 || x == 6))
						{
							
								if(!World.getBlockState(B1).equals(Blocks.water.getDefaultState()))
								{
									World.setBlockState(B1, Blocks.water.getDefaultState());
								}
						}
						
				
					}
					
					
				}
				
				if(this.ReedBlocks.size() != 0)
				{
					
					this.HoeMode = true;
					return true;
				}
				
			}else{
				return false;
			}
				
		
		}else
			{
				this.HoeMode = true;
				return true;
			}
	}
		return false;
	}
	
	
	
	
	
	@Override
	public boolean continueExecuting() {
		
		
		
			if(this.shouldExecute())
			{
				this.startExecuting();
				return true;
			}
		
		
		return false;
	}
	
	@Override
	public void startExecuting() {
		
		if(this.ReedBlocks.size() != 0)
		{
			int HasMelons = this.isItemInTileInv(new ItemStack(Items.melon_seeds), false);
			int HasSugarCane = this.isItemInTileInv(new ItemStack(Items.reeds), false);
			
			if(this.FarmerCD == 0)
			{
				if(this.ListIndex >= 0)
				{
				
					if(HasMelons != -1)
					{
						int NewX = this.ReedBlocks.get(ListIndex).getX() - this.Farmer.WorkHome.getX();
						int NewZ = this.ReedBlocks.get(ListIndex).getZ() - this.Farmer.WorkHome.getZ();
						
					
						if((NewX != 0 && NewX != 7) && (NewX != 1 && NewX != 6)&& NewZ != 0 && NewZ != 6 && (NewZ % 3 == 0 || NewZ % 4 == 0))
						{
						
	
							if(!this.World.getBlockState(this.ReedBlocks.get(ListIndex).up()).toString().contains(":melon_stem"))
							{
								if(this.DeleteItemsFromTE(new ItemStack(Items.melon_seeds), 1));
								{
									World.setBlockState(this.ReedBlocks.get(ListIndex), Blocks.farmland.getDefaultState());
									World.setBlockState(this.ReedBlocks.get(ListIndex).up(), Blocks.melon_stem.getDefaultState());
									
									this.FarmerCD = 40;
								}
							}
						}
						
					}
					
				
					if(HasSugarCane != -1)
					{
						
						int NewX = this.ReedBlocks.get(ListIndex).getX() - this.Farmer.WorkHome.getX();
						int NewZ = this.ReedBlocks.get(ListIndex).getZ() - this.Farmer.WorkHome.getZ();
						
						
							if((NewX == 0 || NewX == 7) || (NewZ == 0 || NewZ == 7))
							{
								
								if(!this.World.getBlockState(this.ReedBlocks.get(ListIndex).up()).toString().contains(":reeds"))
								{
									
									if(Blocks.reeds.canPlaceBlockAt(World, this.ReedBlocks.get(ListIndex).up()))
									{
												if(this.DeleteItemsFromTE(new ItemStack(Items.reeds), 1));
												{
													World.setBlockState(this.ReedBlocks.get(ListIndex).up(), Blocks.reeds.getDefaultState());
													this.FarmerCD = 40;
												}
									}
									
								}
							}
						
					}
					
					
						int NewX = this.ReedBlocks.get(ListIndex).getX() - this.Farmer.WorkHome.getX();
						int NewZ = this.ReedBlocks.get(ListIndex).getZ() - this.Farmer.WorkHome.getZ();
						
						if((NewX != 0 && NewX != 7) && (NewX != 1 && NewX != 6) && (NewZ == 2 || NewZ == 5))
						{
							if(this.World.getBlockState(this.ReedBlocks.get(ListIndex).up()).toString().contains("melon"))
							{
									this.GiveItemToTE(new ItemStack(Items.melon, 3));
									this.World.setBlockToAir(this.ReedBlocks.get(ListIndex).up());
									this.FarmerCD = 40;
								
							}
						}
						
					
						if((NewX == 0 || NewX == 7) || (NewZ == 0 || NewZ == 7))
						{
							
								if(this.World.getBlockState(this.ReedBlocks.get(ListIndex).up().up()).toString().contains(":reeds"))
								{
								
									this.GiveItemToTE(new ItemStack(Items.reeds, 1));
									this.World.setBlockToAir(this.ReedBlocks.get(ListIndex).up().up());
									this.FarmerCD = 40;
								
								}
							
						}
						
						this.ListIndex--;
				
					
				}else
					{
						this.ListIndex = this.ReedBlocks.size() -1;
					}
				
			}else{
				this.FarmerCD--;
			}
	}else{
		return;
	}
		
		super.startExecuting();
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
						
						if(Farmer.getWorkPlace().distanceSq(SupplyPos.getX(), SupplyPos.getY(), SupplyPos.getZ()) <= 1200)
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
	
	public void GiveItemToTE(ItemStack Produced)
	{
		boolean PlaceItem = true;
		int HowMany = Produced.stackSize;
		for(int m = 0; m < this.getTE().inventory.length; m ++)
		{
			 if(this.getTE().inventory[m] != null)
			 { 
				if(this.getTE().inventory[m].isItemEqual(Produced))
				{
					if((this.getTE().inventory[m].stackSize + HowMany ) <= 64)
					{	
						this.getTE().inventory[m].stackSize += HowMany;
						PlaceItem = false;
						break;
					}
				}
			 }
		}
		
		if(PlaceItem)
		{
			 for(int j2 = 0; j2 < this.getTE().inventory.length; j2 ++)
			 {
				 if(this.getTE().inventory[j2] == null)
				 {
					 
					 this.getTE().inventory[j2] = Produced.copy();
					 PlaceItem = false;
					 break;
				 }
			 }
		}	
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
						
						if(Farmer.getWorkPlace().distanceSq(SupplyPos.getX(), SupplyPos.getY(), SupplyPos.getZ()) <= 1200)
						{
						
							 B1 = (TileEntityKingdomStructureBlock)World.getTileEntity(SupplyPos);
						}
				}
			}
		}
		
		
		return B1;
	}

	
	public boolean DeleteItemsFromTE(ItemStack Remove, int HowMany)
	{
		//Delete item from TE
		
		
		
		boolean DidItWork = true;
		
		TileEntityKingdomStructureBlock TE = this.getTE();
		
		
				for(int i = 0; i < TE.inventory.length; i ++)
				{
					if(TE.inventory[i] != null)
					{
						if(TE.inventory[i].isItemEqual(Remove))
						{
							if(TE.inventory[i].stackSize > HowMany)
							{
								TE.inventory[i].stackSize -= HowMany;
								
								break;
							}else if (TE.inventory[i].stackSize == HowMany)
								{
									TE.inventory[i] = null;
									break;
								}else
									{
										DidItWork = false;
										return DidItWork;
									}
						}
					}
			
		}
		return DidItWork;
	}
	
}
