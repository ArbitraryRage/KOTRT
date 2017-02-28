package com.waabbuffet.kotrt.entities.KingdomAI;

import java.util.List;

import com.waabbuffet.kotrt.entities.Kingdom.EntityFarmer;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBlock;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class PickUpItemsAI extends EntityAIBase {

	EntityPlayer Player;
	EntityFarmer Farmer;
	World World;
	

	
	public PickUpItemsAI(EntityFarmer farmer, World world)
	{
		World = world;
		Farmer = farmer;
	
	}
	
	@Override
	public void startExecuting() 
	{
		
		TileEntityKingdomStructureBlock Te = this.getTE();

		for(int i = 0; i < Te.inventory.length; i ++)
		{
			if(Te.inventory[i] == null)
			{
				
				List<EntityItem> ItemsOnFloor =	World.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.fromBounds(this.Farmer.getWorkPlace().getX() - 10, this.Farmer.getWorkPlace().getY() - 10, this.Farmer.getWorkPlace().getZ() - 10, this.Farmer.getWorkPlace().getX() + 10, this.Farmer.getWorkPlace().getY() + 10, this.Farmer.getWorkPlace().getZ() + 10));
				
				for(int j =0; j < ItemsOnFloor.size(); j ++)
				{
					
						this.Farmer.getNavigator().tryMoveToXYZ(ItemsOnFloor.get(j).posX,ItemsOnFloor.get(j).posY, ItemsOnFloor.get(j).posZ, 1.0D);
					
						this.ReturnInvToTE();
					
				/*
					for(int TE = 0; TE < Te.inventory.length; TE++)
					{
						if(Te.inventory[TE] != null)
						{
				
									if(this.getTE().inventory[TE].isItemEqual(ItemsOnFloor.get(i).getEntityItem()))
									{
										if((this.getTE().inventory[TE].stackSize + this.Farmer.getInventory()[i].stackSize) <= 64)
										{					
											this.getTE().inventory[TE].stackSize +=  ItemsOnFloor.get(i).getEntityItem().stackSize;
											ItemsOnFloor.remove(i);
											break;
										}else{
											
											for(int TE1 = 0; TE1 < Te.inventory.length; TE1++)
											{
												if(Te.inventory[TE1] == null)
												{
													
														
																
																Te.inventory[TE1] = ItemsOnFloor.get(i).getEntityItem();
																ItemsOnFloor.remove(i);
																break;
														
															
														
													}
												}
											
											}
										}
										
									}
									
						
								*/
						
									
							
						}
					}
				}
			
			
		
	}
	

	@Override
	public boolean shouldExecute() {
	
		TileEntityKingdomStructureBlock TE = this.getTE();
	
		for(int i = 0; i < TE.inventory.length; i ++)
		{
			if(TE.inventory[i] == null)
			{
				
				List<EntityItem> ItemsOnFloor =	World.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.fromBounds(this.Farmer.getWorkPlace().getX() - 10, this.Farmer.getWorkPlace().getY() - 10, this.Farmer.getWorkPlace().getZ() - 10, this.Farmer.getWorkPlace().getX() + 10, this.Farmer.getWorkPlace().getY() + 10, this.Farmer.getWorkPlace().getZ() + 10));
				
				if(ItemsOnFloor.size() > 0){
					this.startExecuting();
					return true;
					
				}
			}
		}
		return false;

	}

	@Override
	public boolean continueExecuting() 
	{
	
		if(this.shouldExecute())
		{
			this.startExecuting();
		}
		
	
		
		
		return false;
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
	
}
