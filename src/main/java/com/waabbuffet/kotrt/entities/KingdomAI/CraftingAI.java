package com.waabbuffet.kotrt.entities.KingdomAI;

import java.util.List;

import com.waabbuffet.kotrt.entities.Kingdom.EntityFarmer;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBlock;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBuilderBlock;
import com.waabbuffet.kotrt.util.CraftingAIFormat;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CraftingAI  extends EntityAIBase {

	
	EntityFarmer Farmer;
	World World;
	boolean StartCrafting;
	int CraftingCD;
	
	List<CraftingAIFormat> CanMakeItems;
	
	public CraftingAI(EntityFarmer farmer, World world, List<CraftingAIFormat> canMakeItems) 
	{
		
		this.Farmer = farmer;
		this.World = world;
		
		this.CanMakeItems = canMakeItems;
		
		this.CraftingCD = 0;
		
	}
	
	
	@Override
	public void startExecuting() {
	
		
		if(this.Farmer.getStartJob())
		{
			if(this.CanMakeItems.size() == 0)
			{
				if(this.Farmer.getJob().contains("Blacksmith"))
				{
					this.CanMakeItems = this.Farmer.FillBlacksmithObjects();
				}else{
					this.CanMakeItems = this.Farmer.FillCarpenterObjects();
				}
			}
			
			
		if(StartCrafting)
		{
			int hasOr = this.RequestedItemMatchesList(this.Farmer.getRequestedItem());
			
			if(hasOr != -1)
			{
			
				boolean[] HasOr = this.DoesTeHaveItems(this.CanMakeItems.get(hasOr).getCost(), this.CanMakeItems.get(hasOr).getHowMany());
				
				for(int i = 0; i < HasOr.length; i ++)
				{
					
					if(!HasOr[i])
					{
						
						this.StartCrafting = false;
						return;
					}
				}
				
				
				if(this.CraftingCD == 0 && this.StartCrafting)
				{
					
					//remove items then give item
					if(this.DeleteItemsFromTE(hasOr))
					{
						this.GiveItemToTE(this.CanMakeItems.get(hasOr).getProduced()); //giving wrong how many here
						this.CraftingCD = this.CanMakeItems.get(hasOr).getCraftingTime();
						
					}
				}else	
					{
						this.CraftingCD--;
					}
				
				
			}
			
			
			
			
		}
		}
		super.startExecuting();
	}
	
	@Override
	public boolean continueExecuting() {
	
	
		if(this.shouldExecute())
		{
			this.startExecuting();
		}
		
		
		return false;
	}
	
	
	@Override
	public boolean shouldExecute() {
		
		
		if(this.Farmer.getRequestedItem() != null)
		{
			if(this.CanMakeItems.size() == 0)
			{
				if(this.Farmer.getJob().contains("Blacksmith"))
				{
					this.CanMakeItems = this.Farmer.FillBlacksmithObjects();
				}else{
					this.CanMakeItems = this.Farmer.FillCarpenterObjects();
				}
			}
			int hasOr = this.RequestedItemMatchesList(this.Farmer.getRequestedItem());
			
		
			if( hasOr != -1)
			{
			
				this.StartCrafting = true;
				return true;
			}
		}
		
		return false;
	}
	
	
	public int RequestedItemMatchesList(ItemStack MatchItem)
	{
		int HasOrNaw = -1;
		
		
		for(int i =0; i < this.CanMakeItems.size(); i ++)
		{
			
			
			if(this.CanMakeItems.get(i).getProduced().isItemEqual(MatchItem))
			{
				
				HasOrNaw = i;
				return HasOrNaw;
			}
		}
			
		
		return HasOrNaw;
	}
	//Clean up
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
	
	
	public boolean DeleteItemsFromTE(int index)
	{
		//Delete item from TE
		
		ItemStack[] Remove = this.CanMakeItems.get(index).getCost();
		int HowMany[] = this.CanMakeItems.get(index).getHowMany();
		boolean DidItWork = true;
		
		TileEntityKingdomStructureBlock TE = this.getTE();
		
		for(int j = 0; j < Remove.length; j ++)
		{
			if(Remove[j] != null)
			{
				for(int i = 0; i < TE.inventory.length; i ++)
				{
					if(TE.inventory[i] != null)
					{
						if(TE.inventory[i].isItemEqual(Remove[j]))
						{
							if(TE.inventory[i].stackSize > HowMany[j])
							{
								TE.inventory[i].stackSize -= HowMany[j];
								
								break;
							}else if (TE.inventory[i].stackSize == HowMany[j])
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
			}
		}
		return DidItWork;
	}
	
	
	public boolean[] DoesTeHaveItems(ItemStack[] Cost, int[] HowMany)
	{
		boolean[] HasOrNaw = new boolean[Cost.length];
	
	
		TileEntityKingdomStructureBlock TE = this.getTE();
	
		
		for(int j = 0; j < Cost.length; j ++)
		{
			if(Cost[j] != null)
			{
				for(int i =0; i < TE.inventory.length; i ++)
				{
					if(TE.inventory[i] != null)
					{
						if(Cost[j].isItemEqual(TE.inventory[i]))
						{
							if(TE.inventory[i].stackSize >= HowMany[j])
							{
								HasOrNaw[j] = true;
								break;
							}
						}
					}
					
				}
			}else {
				HasOrNaw[j] = true;
			}
		}
	
		
		return HasOrNaw;
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

}
