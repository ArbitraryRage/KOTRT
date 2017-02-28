package com.waabbuffet.kotrt.entities.KingdomAI;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.waabbuffet.kotrt.entities.Kingdom.EntityFarmer;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBlock;
import com.waabbuffet.kotrt.util.StorageAIItemFormat;

public class StorageAI extends EntityAIBase{

	EntityFarmer Farmer;
	World World;
	
	int FarmCD;
	boolean StoreItems = false, GetRequestedItems = false;
	
	
	ItemStack[] RequestedItems = new ItemStack[10];
	
	List<TileEntityChest> LocalChest = new ArrayList();
	List<StorageAIItemFormat> TotalItems = new ArrayList();
	
	public StorageAI(EntityFarmer farmer, World world) {
		// TODO Auto-generated constructor stub
		Farmer = farmer;
		World = world;
		
		FarmCD = 0;
		
		
		
	}
	
	@Override
	public boolean shouldExecute() {
		
	if(this.Farmer.getStartJob())
	{
		TileEntityKingdomStructureBlock TE = this.getTE();
		
		for(int i = 0; i < TE.inventory.length; i ++)
		{
			if(TE.inventory[i] != null)
			{
				//give to random chest
				
				this.StoreItems = true;
				return true;
			}
		}
		
		//also run if there is a special request
		
		if(this.RequestedItems != null)
		{
			this.GetRequestedItems = true;
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
		}
		
		return super.continueExecuting();
	}
	
	
	@Override
	public void startExecuting() {
		LocalChest = this.GetNearestChests();
		TileEntityKingdomStructureBlock TE = this.getTE();
		
		if(this.StoreItems)
		{
			if(this.LocalChest != null)
			{
			
			if(this.FarmCD == 0)
			{
				Here:
				for(int i = 0; i < LocalChest.size(); i ++) // march through List
				{
					for(int j = 0; j < TE.inventory.length; j ++)
					{
						if(TE.inventory[j] != null)
						{
							if(this.AddItemToChest(TE.inventory[j], TE.inventory[j].stackSize, i))
							{
								TE.inventory[j] = null;
								this.FarmCD = 40;
								break Here;
							}
						}				
					}
								
				}
			}else{
				this.FarmCD--;
			}
			}			
		}
		
		if(this.GetRequestedItems)
		{
			for(int i = 0; i < RequestedItems.length; i++)
			{
				if(RequestedItems[i] != null)
				this.GatherRequestedItems(this.RequestedItems[i], this.RequestedItems[i].stackSize, i);
			}
			
		}
		
		super.startExecuting();
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
						
						if(Farmer.getWorkPlace().distanceSq(SupplyPos.getX(), SupplyPos.getY(), SupplyPos.getZ()) <= 1400)
						{
						
							 B1 = (TileEntityKingdomStructureBlock)World.getTileEntity(SupplyPos);
						}
				}
			}
		}
		
		
		return B1;
	}
	
	public void GatherRequestedItems(ItemStack item, int HowMuch, int index)
	{
		List<TileEntityChest> NearestChests = this.GetNearestChests();
		TileEntityKingdomStructureBlock TE = this.getTE();
		
		for(int i = 0; i < NearestChests.size(); i ++)//march through chest
		{
			for(int j = 0; j < NearestChests.get(i).getSizeInventory(); j++)
			{
				if(NearestChests.get(i).getStackInSlot(j) != null)
				{
					if(NearestChests.get(i).getStackInSlot(j).isItemEqual(item))
					{
						if(NearestChests.get(i).getStackInSlot(j).stackSize > HowMuch)
						{
							//find a place to put these items
							NearestChests.get(i).getStackInSlot(j).stackSize -= HowMuch;
							item.stackSize = HowMuch;
							TE.RequestedInventory[index] = item;
							this.RequestedItems[index] = null;
						}else if(NearestChests.get(i).getStackInSlot(j).stackSize == HowMuch)
						{
								NearestChests.get(i).removeStackFromSlot(j);
								item.stackSize = HowMuch;
								TE.RequestedInventory[index] = item;
								this.RequestedItems[index] = null;
						}
					}
				}
			}
		}
	}
	
	
	public void setRequestedItems(ItemStack[] requestedItems) {
		RequestedItems = requestedItems;
	}
	
	public void FilterByName()
	{
		
	}
	
	public void SortByHighest()
	{
		
	}
	
	public List<StorageAIItemFormat> GatherAllItemsForBuilder()
	{
		this.GatherAllItems();
		return this.TotalItems;
	}
	
	public void GatherAllItems()
	{
		List<TileEntityChest> NearestChests = this.GetNearestChests();
		
		for(int i = 0; i < NearestChests.size(); i ++)
		{
			for(int j = 0; j < NearestChests.get(i).getSizeInventory(); j ++)
			{
				if(NearestChests.get(i).getStackInSlot(j) != null)
				{
					if(AlreadyInTheList(NearestChests.get(i).getStackInSlot(j),NearestChests.get(i).getStackInSlot(j).stackSize, this.TotalItems))
					{
						StorageAIItemFormat b = new StorageAIItemFormat(i, NearestChests.get(i).getStackInSlot(j), NearestChests.get(i).getStackInSlot(j).stackSize);
						this.TotalItems.add(b);
						
					}
				}
			}
		}
	}

	
	public boolean AlreadyInTheList(ItemStack Checking, int HowMany, List<StorageAIItemFormat> List)
	{
		boolean NotInsideList = true;
		
		for(int i = 0; i < List.size(); i++){
		
			if(List.get(i).getItem().isItemEqual(Checking))
			{
				NotInsideList = false;
				List.get(i).ChangeHowMany(HowMany);
				break;
			}
		}
		
		return NotInsideList;
	}
	
	public boolean AddItemToChest(ItemStack Items, int HowMany, int index)
	{
		List<TileEntityChest> NearestChests = this.GetNearestChests();
		
		boolean PlaceItem = true, successful = false;
		
		for(int m = 0; m < NearestChests.get(index).getSizeInventory(); m ++)
		{
			 if(NearestChests.get(index).getStackInSlot(m) != null)
			 { 
				if(NearestChests.get(index).getStackInSlot(m).isItemEqual(Items))
				{
					if((NearestChests.get(index).getStackInSlot(m).stackSize + HowMany ) <= 64)
					{	
						NearestChests.get(index).getStackInSlot(m).stackSize += HowMany;
						PlaceItem = false;
						successful = true;
						break;
					}
				}
			 }
		}
		
		if(PlaceItem)
		{
			 for(int j2 = 0; j2 < NearestChests.get(index).getSizeInventory(); j2 ++)
			 {
				 if(NearestChests.get(index).getStackInSlot(j2) == null)
				 {
					 
					 NearestChests.get(index).setInventorySlotContents(j2, Items.copy());
					 PlaceItem = false;
					 successful = true;
					 break;
				 }
			 }
		}	
		
		return successful;
		
	}
	
	public void DeleteItemFromChest(ItemStack Items, int HowMany, int index)
	{
		List<TileEntityChest> NearestChests = this.GetNearestChests();
		
		for(int i = 0; i < NearestChests.get(index).getSizeInventory(); i ++ )
		{
			if(NearestChests.get(index).getStackInSlot(i) != null)
			{
				if(NearestChests.get(index).getStackInSlot(i).isItemEqual(Items))
				{
					if(NearestChests.get(index).getStackInSlot(i).stackSize > HowMany)
					{
							NearestChests.get(index).getStackInSlot(i).stackSize -= HowMany;
						
					}else if(NearestChests.get(index).getStackInSlot(i).stackSize == HowMany)
						{
							NearestChests.get(index).removeStackFromSlot(i);
						}
					
				}
			}
		}
		
	}
	
	public List<TileEntityChest> GetNearestChests()
	{
		List<TileEntityChest> NearestChests = new ArrayList();
		
		for(int i = 0; i < World.loadedTileEntityList.size(); i ++)
		{
			if(World.loadedTileEntityList.get(i) instanceof TileEntityChest)
			{
				if(World.loadedTileEntityList.get(i).getPos().distanceSq(this.Farmer.WorkHome.getX(), this.Farmer.WorkHome.getY(),this.Farmer.WorkHome.getZ()) <= 2000 )
				{
					NearestChests.add((TileEntityChest) World.loadedTileEntityList.get(i));
				}		
			}
		}
		
		return NearestChests;
	}

	
	
}
