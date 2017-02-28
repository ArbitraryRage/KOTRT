package com.waabbuffet.kotrt.entities.KingdomAI;

import java.util.ArrayList;
import java.util.List;

import com.waabbuffet.kotrt.entities.Kingdom.EntityFarmer;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBlock;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class MailManAI extends EntityAIBase {

	EntityFarmer Farmer;
	World World;
	int FarmerCD = 0, ListIndex = 0;
	
	
	
	BlockPos MovingTo;
	List<BlockPos> WhereToGo = new ArrayList();
	
	
	
	
	
	public MailManAI(EntityFarmer farmer, World world) {
		// TODO Coordinate System is wrong
		
		Farmer = farmer;
		World = world;
		ListIndex = 0;
		
		
		//this.setMutexBits(3);
	}
	
	
	@Override
	public boolean shouldExecute() {
		// TODO Auto-generated method stub
		
		
		if(this.Farmer.getStartJob())
		{
			BlockPos[] WhereAM = this.Farmer.getWhereAM();
			
			for(int i =0; i < 4; i ++)
			{
				if(WhereAM[i] != null)
				{
					if(WhereAM[i].getX() == 0 && WhereAM[i].getY() == 0 && WhereAM[i].getZ() == 0)
					{
						return false;
					}
				}
			}
			
			if(this.Farmer.getStorageLocation().getX() == 0 && this.Farmer.getStorageLocation().getY() == 0 && this.Farmer.getStorageLocation().getZ() == 0)
			{
				
				return false;
			}
			
			
			if(this.FarmerCD == 0)	
			{
				
					if(WhereAM != null)
					{
						
						 WhereToGo = this.GetFullPath(this.Farmer.getStorageLocation(), WhereAM);
						 
						 if(WhereToGo == null)
						 {
							
							 return false;
						 }
						
						 if(this.ListIndex < this.WhereToGo.size() -1)
						 	this.MovingTo = WhereToGo.get(ListIndex);
						 
				
						
								if(this.ListIndex < this.WhereToGo.size() - 1)
								{
									this.FarmerCD = 100;
									this.ListIndex++;
								}
							
						 
						if(this.ListIndex == this.WhereToGo.size() - 1)
						{
							
							
							this.FarmerCD = 1200;
							this.ListIndex = 0;
							return true;
						}
						 
						
				}
			}else
			{
				
				this.FarmerCD--;
			}
			
		}
		return true;
	}
	
	public void ReturnInvToTE()
	{
		for(int i = 0; i < this.Farmer.getInventory().length; i ++)
		{
			if(this.Farmer.getInventory()[i] != null){
				
				this.GiveItemToTE(this.Farmer.getInventory()[i]);
				this.Farmer.getInventory()[i] = null;
			}
		}
	}

	@Override
	public boolean continueExecuting() {
		// TODO Auto-generated method stub
		
			if(this.ListIndex < this.WhereToGo.size() -1)
			{
				if(this.shouldExecute())
				{
					this.startExecuting();
				}
					return true;
			}
		
		return false;
	}
	
	@Override
	public void startExecuting() {
		
	//	this.Farmer.getNavigator().tryMoveToXYZ(-50, 63, 275, 1.0D);
		
		//System.out.println("" + this.Farmer.getNavigator().tryMoveToXYZ(this.MovingTo.getX(), this.MovingTo.getY(), this.MovingTo.getZ(), 1.0D));
		
	//	System.out.println("X: " + this.MovingTo.getX());
	//	System.out.println("Y: " + this.MovingTo.getY());
//		System.out.println("Z: " + this.MovingTo.getZ());
	
		
		
		 
		if(this.MovingTo.getY() != 150)
		{
		
			if(!this.Farmer.getNavigator().tryMoveToXYZ(this.MovingTo.getX(), this.MovingTo.getY(), this.MovingTo.getZ(), 1.0D))
			{
				
				this.Farmer.teleportTo(this.MovingTo.getX(), this.MovingTo.getY(), this.MovingTo.getZ());
			}
				
			
			
		}else
		{	
			
				//do te stuff here
			
			//	
				this.getTEItems();
		}
		
			
	/*	
		WhereAM[0] = new BlockPos(-71, 65, 274);
		WhereAM[1] = new BlockPos(-62, 64, 252);
		WhereAM[2] = new BlockPos(-40, 65, 235);
		WhereAM[3] = new BlockPos(-19, 67, 244);
	*/
		
		super.startExecuting();
	}
	

	
	
	
	//Interact With TE, Fill RequestedItems
	
	public void getTEItems()
	{
		TileEntityKingdomStructureBlock B1 = this.getNearestTE();
		
	if(B1 != null){		
		for(int i = 0; i <  B1.inventory.length; i ++)
		{
			
			if(B1.inventory[i] != null)
			{
				
				this.GiveItemToTE(B1.inventory[i]);
				B1.inventory[i] = null;
			}
		}
	}
	}
			
	public void GiveItemToFarmer(ItemStack item)
	{
		boolean PlaceItem = true;
		
		for(int j =0; j < Farmer.getInventory().length; j ++)
		{
			if(Farmer.getInventory()[j] != null)
			{
				if(Farmer.getInventory()[j].isItemEqual(item))
				{
					if(Farmer.getInventory()[j].stackSize + item.stackSize <= 64)
					{
						Farmer.getInventory()[j].stackSize += item.stackSize;
						PlaceItem = false;
					}
				}
			}
		}
		
		if(PlaceItem)
		{
			for(int j =0; j < Farmer.getInventory().length; j ++)
			{
				if(Farmer.getInventory()[j] == null)
				{
					Farmer.getInventory()[j] = item;
				}
			}
		}
	}
	
	
	public void GiveItemToTE(ItemStack Produced)
	{
		
		boolean PlaceItem = true;
		int HowMany = Produced.stackSize;
		
		TileEntityKingdomStructureBlock B1 = this.getTE();
		
		
		for(int m = 0; m < B1.inventory.length; m ++)
		{
			 if(B1.inventory[m] != null)
			 { 
				if(B1.inventory[m].isItemEqual(Produced))
				{
					if((B1.inventory[m].stackSize + HowMany ) <= 64)
					{	
						B1.inventory[m].stackSize += HowMany;
						PlaceItem = false;
						break;
					}
				}
			 }
		}
		
		if(PlaceItem)
		{
			
			 for(int j2 = 0; j2 < B1.inventory.length; j2 ++)
			 {
				 if(B1.inventory[j2] == null)
				 {
					 
					 B1.inventory[j2] = Produced.copy();
					 PlaceItem = false;
					 break;
				 }
			 }
		}	
	}
	
	public TileEntityKingdomStructureBlock getNearestTE()
	{
	
		TileEntityKingdomStructureBlock B1 = null;
		
		for(int j = 0; j < World.loadedTileEntityList.size(); j ++)
		{
			
			if(World.loadedTileEntityList.get(j).getPos() != null)
			{
				if(World.loadedTileEntityList.get(j) instanceof TileEntityKingdomStructureBlock)
				{
						BlockPos SupplyPos = World.loadedTileEntityList.get(j).getPos();
						
						if(Farmer.getPosition().distanceSq(SupplyPos.getX(), SupplyPos.getY(), SupplyPos.getZ()) <= 100)
						{
							 B1 = (TileEntityKingdomStructureBlock)World.getTileEntity(SupplyPos);
						}
				}
			}
		}
		
		
		return B1;
	}
	
	public TileEntityKingdomStructureBlock getTE()
	{
		TileEntityKingdomStructureBlock B1 = null;
		if(this.Farmer.getStorageLocation() != null)
			B1 = (TileEntityKingdomStructureBlock) World.getTileEntity(this.Farmer.getStorageLocation());
		
		
		
		
		/*
		for(int j = 0; j < World.loadedTileEntityList.size(); j ++)
		{
			
			if(World.loadedTileEntityList.get(j).getPos() != null)
			{
				if(World.loadedTileEntityList.get(j) instanceof TileEntityKingdomStructureBlock)
				{
						BlockPos SupplyPos = World.loadedTileEntityList.get(j).getPos();
						
						if(Farmer.getWorkPlace().distanceSq(SupplyPos.getX(), SupplyPos.getY(), SupplyPos.getZ()) <= 100)
						{
						
							 B1 = (TileEntityKingdomStructureBlock)World.getTileEntity(SupplyPos);
						}
				}
			}
		}
		*/
		
		
		return B1;
	}
	
	
	//GETPathLocation, this will have to break it up into several 16 block travels and then come back to work place.
	public List<BlockPos> GetFullPath(BlockPos StartingPosition, BlockPos[] VisitingSpots)
	{
		
		
		if(StartingPosition == null && VisitingSpots.length == 0)
		{
			
			return null;
		}
		
			int NumberOfX = 0, NumberOfZ = 0;
			int CurrentPositionX = StartingPosition.getX();
			int CurrentPositionZ = StartingPosition.getZ();
		
			List<BlockPos> FullPath = new ArrayList();
			
			
				for(int j = 0; j < VisitingSpots.length; j ++)
				{
					if(VisitingSpots[j] != null)
					{
						
					
					while(CurrentPositionX < VisitingSpots[j].getX())
					{
						if(CurrentPositionX + 12 < VisitingSpots[j].getX())
						{
							CurrentPositionX += 12;
							
						}else
							{
								CurrentPositionX = VisitingSpots[j].getX();
							}
						
						FullPath.add(new BlockPos(CurrentPositionX, World.getTopSolidOrLiquidBlock(new BlockPos(CurrentPositionX, 0, StartingPosition.getZ())).getY(),CurrentPositionZ));
						
					}
					
					while(CurrentPositionX > VisitingSpots[j].getX())
					{
						if(CurrentPositionX - 12 > VisitingSpots[j].getX())
						{
							CurrentPositionX -= 12;
							
						}else
							{
								CurrentPositionX = VisitingSpots[j].getX();
							}
						
						FullPath.add(new BlockPos(CurrentPositionX, World.getTopSolidOrLiquidBlock(new BlockPos(CurrentPositionX, 0, StartingPosition.getZ())).getY(),CurrentPositionZ));
						
					}
					
					while(CurrentPositionZ < VisitingSpots[j].getZ())
					{
						if(CurrentPositionZ + 12 < VisitingSpots[j].getZ())
						{
							CurrentPositionZ += 12;
							
						}else
							{
								CurrentPositionZ = VisitingSpots[j].getZ();
							}
						
						FullPath.add(new BlockPos(CurrentPositionX, World.getTopSolidOrLiquidBlock(new BlockPos(CurrentPositionX, 0, StartingPosition.getZ())).getY(),CurrentPositionZ));
						
					}
					while(CurrentPositionZ > VisitingSpots[j].getZ())
					{
						if(CurrentPositionZ - 12 > VisitingSpots[j].getZ())
						{
							CurrentPositionZ -= 12;
							
						}else
							{
								CurrentPositionZ = VisitingSpots[j].getZ();
							}
						
						FullPath.add(new BlockPos(CurrentPositionX, World.getTopSolidOrLiquidBlock(new BlockPos(CurrentPositionX, 0, StartingPosition.getZ())).getY(),CurrentPositionZ));
						
					}
					
					}
					FullPath.add(new BlockPos(0,150,0));
				}
				
				//Make Him go back home
			
			CurrentPositionX = VisitingSpots[VisitingSpots.length - 1].getX();
			CurrentPositionZ = VisitingSpots[VisitingSpots.length - 1].getZ();
			
			
			while(CurrentPositionX < StartingPosition.getX())
			{
				if(CurrentPositionX + 12 < StartingPosition.getX())
				{
					CurrentPositionX += 12;
					
				}else
					{
						CurrentPositionX = StartingPosition.getX();
					}
				
				FullPath.add(new BlockPos(CurrentPositionX, World.getTopSolidOrLiquidBlock(new BlockPos(CurrentPositionX, 0, StartingPosition.getZ())).getY(),CurrentPositionZ));
				
			}
			
			while(CurrentPositionX > StartingPosition.getX())
			{
				if(CurrentPositionX - 12 > StartingPosition.getX())
				{
					CurrentPositionX -= 12;
					
				}else
					{
						CurrentPositionX = StartingPosition.getX();
					}
				
				FullPath.add(new BlockPos(CurrentPositionX, World.getTopSolidOrLiquidBlock(new BlockPos(CurrentPositionX, 0, StartingPosition.getZ())).getY(),CurrentPositionZ));
				
			}
			
			while(CurrentPositionZ < StartingPosition.getZ())
			{
				if(CurrentPositionZ + 12 < StartingPosition.getZ())
				{
					CurrentPositionZ += 12;
					
				}else
					{
						CurrentPositionZ = StartingPosition.getZ();
					}
				
				FullPath.add(new BlockPos(CurrentPositionX, World.getTopSolidOrLiquidBlock(new BlockPos(CurrentPositionX, 0, StartingPosition.getZ())).getY(),CurrentPositionZ));
				
			}
			while(CurrentPositionZ > StartingPosition.getZ())
			{
				if(CurrentPositionZ - 12 > StartingPosition.getZ())
				{
					CurrentPositionZ -= 12;
					
				}else
					{
						CurrentPositionZ = StartingPosition.getZ();
					}
				
				FullPath.add(new BlockPos(CurrentPositionX, World.getTopSolidOrLiquidBlock(new BlockPos(CurrentPositionX, 0, StartingPosition.getZ())).getY(),CurrentPositionZ));
				
			}
			FullPath.add(new BlockPos(this.Farmer.getWorkPlace().getX(), this.Farmer.getWorkPlace().getY(),this.Farmer.getWorkPlace().getZ()));
			
			return FullPath;
		
		
	}
}
