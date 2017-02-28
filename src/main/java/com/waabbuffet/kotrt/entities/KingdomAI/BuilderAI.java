package com.waabbuffet.kotrt.entities.KingdomAI;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.waabbuffet.kotrt.entities.Kingdom.EntityFarmer;
import com.waabbuffet.kotrt.inventory.ContainerTileEntityKingdomStructureBuilderBlock;
import com.waabbuffet.kotrt.packet.PacketHandler;
import com.waabbuffet.kotrt.packet.structure.StructureConstruct;
import com.waabbuffet.kotrt.packet.structure.UpdateClientTileInv;
import com.waabbuffet.kotrt.proxy.ClientProxy;
import com.waabbuffet.kotrt.schematic.Structure;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBlock;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBuilderBlock;
import com.waabbuffet.kotrt.util.BlockList;
import com.waabbuffet.kotrt.util.StorageAIItemFormat;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BuilderAI extends EntityAIBase{

	EntityFarmer Farmer;
	World World;
	int FarmerCD = 0, ListIndex = 0;
	
//	TileEntityKingdomStructureBuilderBlock StorageLocation;
	BlockPos StorageLocation;
	
	List<BlockPos> FullPathYo;
	List<BlockList> MaterialList1;;
	
	List<StorageAIItemFormat> TotalItems = new ArrayList();    
	
	 public  IBlockState FullBlocks[][][];
	
	/*
	 * 
	 */
	
	public BuilderAI(EntityFarmer farmer, World world) {
		// TODO Auto-generated constructor stub
		
		Farmer = farmer;
		World = world;
		
		this.StorageLocation = farmer.getStorageLocation();
	}

	@Override
	public boolean shouldExecute() {
		//TODO Create packet from client to server cuz server cant see the client FullBlockData yo
		
		if(this.StorageLocation != null)
		{
		if(this.StorageLocation.getX() == 0 && this.StorageLocation.getY() == 0&& this.StorageLocation.getZ() == 0)
		{
			return false;
		}
				if(this.Farmer.getStartJob())
				{
					TileEntityKingdomStructureBuilderBlock b = this.getBuilderTE();
				//	System.out.println("Te MaterialList" + );
					
						if(b != null)
						{
								this.MaterialList1 = new ArrayList<BlockList>(b.GetMaterialList());
						}
						
						if(this.MaterialList1 != null)
						{
							if(!this.MaterialList1.isEmpty())
							{
								if(DoesStorageTEHaveItems(this.MaterialList1))
								{
								
									return true;
								}
							}
						}
				}
		}else{
			this.StorageLocation = this.Farmer.getStorageLocation();
		}
		return false;
	}
	
	
	
	public void SortStorageAIItemFormatList()
	{
		 Collections.sort(TotalItems, new Comparator<StorageAIItemFormat>() {
		        @Override public int compare(StorageAIItemFormat p1, StorageAIItemFormat p2) {
		            return p2.getHowMany()- p1.getHowMany(); //descending
		        }

		    });
		
		
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
		
		//if(this.isFarmerWithinRange()) // within distance
	//	{
			
			this.GatherRequestedItems();
		
	//		System.out.println("should be ehere: ");
			//request items
	//	}
			
			
			
			//move closer
		
		
		
		super.startExecuting();
	}
	
	
	
	
	public void setFullBlock() {
		FullBlocks = ClientProxy.data.FullBlocks;
	}
	
	public void GatherRequestedItems()
	{
		this.TotalItems.clear();
		this.TotalItems = this.GatherAllItems();
	
		
		
			for(int i =0; i < this.MaterialList1.size(); i ++)
			{
				for(int j = 0; j < TotalItems.size(); j ++)
				{
					
					//725
					if(TotalItems.get(j).getItem().isItemEqual(new ItemStack(MaterialList1.get(i).Blockerino.getBlock())))
					{
					
						
						if(TotalItems.get(j).getHowMany() < MaterialList1.get(i).TotalNumberOfBlock)
						{
							if(GiveItemToTE(TotalItems.get(j).getItem(),  TotalItems.get(j).getHowMany()))
							{
								return;
							}
							
							this.DeleteItemFromChest(TotalItems.get(j).getItem(), TotalItems.get(j).getHowMany(), TotalItems.get(j).getChestIndex());
							
							if(MaterialList1.get(i).getTotalNumberOfBlock() > TotalItems.get(j).getHowMany())
							{
								MaterialList1.get(i).TotalNumberOfBlock -= TotalItems.get(j).getHowMany();
							}else 
							{
								MaterialList1.remove(i);
							}
							TotalItems.remove(j);
						}else if(TotalItems.get(j).getHowMany() > MaterialList1.get(i).TotalNumberOfBlock)
						{
							
						}
					
						
					/*	
							
							if(GiveItemToTE(TotalItems.get(j).getItem(),  TotalItems.get(j).getItem().stackSize))
							{
								return;
							}
						*/	
							//229 - 64
							
							
						
					}
				}
			}
			
		
		
	}
	
	public boolean GiveItemToTE(ItemStack Produced, int HowMany)
	{
		
		boolean PlaceItem = true;
		
		
		TileEntityKingdomStructureBuilderBlock B1 = this.getBuilderTE();
		
		if(B1 == null)
		{
			return PlaceItem;
		}
		
				for(int m = 0; m < B1.getSizeInventory(); m ++)
				{
					 if(B1.getStackInSlot(m) != null)
					 { 
							
						if(B1.getStackInSlot(m).isItemEqual(Produced))
						{
							if((B1.getStackInSlot(m).stackSize + HowMany ) <= 64)
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
				
				 for(int j2 = 0; j2 <B1.getSizeInventory(); j2 ++)
				 {
					
					 if(B1.getStackInSlot(j2) == null)
					 {
						 
						 B1.inventory[j2] = new ItemStack(Produced.getItem(), HowMany);
					//	 B1.setInventorySlotContents(j2, new ItemStack(Produced.getItem(), HowMany));
						 
						 PlaceItem = false;
						 
						
						 break;
					 }
				 }
			}	
		
		
	//Send packet
	
		return PlaceItem;
	}
	
	public void DeleteItemFromChest(ItemStack Items, int HowMany, int index)
	{
		List<TileEntityChest> NearestChests = this.GetNearestChests();
		
		if(NearestChests.size() == 0)
		{
			return;
		}
		
		
		for(int i = 0; i < NearestChests.get(index).getSizeInventory(); i ++ )
		{
			if(NearestChests.get(index).getStackInSlot(i) != null)
			{
				if(NearestChests.get(index).getStackInSlot(i).getIsItemStackEqual(Items))
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
		
		//send packet to update client
		
	}
	
	
	public List<BlockPos> GetFullPath(BlockPos StartingPosition, BlockPos VisitingSpots)
	{
		int NumberOfX = 0, NumberOfZ = 0;
		int CurrentPositionX = StartingPosition.getX();
		int CurrentPositionZ = StartingPosition.getZ();
	
		List<BlockPos> FullPath = new ArrayList();
		
		
			
				while(CurrentPositionX < VisitingSpots.getX())
				{
					if(CurrentPositionX + 12 < VisitingSpots.getX())
					{
						CurrentPositionX += 12;
						
					}else
						{
							CurrentPositionX = VisitingSpots.getX();
						}
					
					FullPath.add(new BlockPos(CurrentPositionX, World.getTopSolidOrLiquidBlock(new BlockPos(CurrentPositionX, 0, StartingPosition.getZ())).getY(),CurrentPositionZ));
					
				}
				
				while(CurrentPositionX > VisitingSpots.getX())
				{
					if(CurrentPositionX - 12 > VisitingSpots.getX())
					{
						CurrentPositionX -= 12;
						
					}else
						{
							CurrentPositionX = VisitingSpots.getX();
						}
					
					FullPath.add(new BlockPos(CurrentPositionX, World.getTopSolidOrLiquidBlock(new BlockPos(CurrentPositionX, 0, StartingPosition.getZ())).getY(),CurrentPositionZ));
					
				}
				
				while(CurrentPositionZ < VisitingSpots.getZ())
				{
					if(CurrentPositionZ + 12 < VisitingSpots.getZ())
					{
						CurrentPositionZ += 12;
						
					}else
						{
							CurrentPositionZ = VisitingSpots.getZ();
						}
					
					FullPath.add(new BlockPos(CurrentPositionX, World.getTopSolidOrLiquidBlock(new BlockPos(CurrentPositionX, 0, StartingPosition.getZ())).getY(),CurrentPositionZ));
					
				}
				while(CurrentPositionZ > VisitingSpots.getZ())
				{
					if(CurrentPositionZ - 12 > VisitingSpots.getZ())
					{
						CurrentPositionZ -= 12;
						
					}else
						{
							CurrentPositionZ = VisitingSpots.getZ();
						}
					
					FullPath.add(new BlockPos(CurrentPositionX, World.getTopSolidOrLiquidBlock(new BlockPos(CurrentPositionX, 0, StartingPosition.getZ())).getY(),CurrentPositionZ));
					
				}
				
				
				FullPath.add(new BlockPos(0,150,0));
			
			
			//Make Him go back home
		
		CurrentPositionX = VisitingSpots.getX();
		CurrentPositionZ = VisitingSpots.getZ();
		
		
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
	
	public boolean isFarmerWithinRange()
	{
		
		if(this.Farmer.getPosition().distanceSq(this.StorageLocation.getX(), this.StorageLocation.getY(),this.StorageLocation.getZ()) <=144)
		{
			return true;
		}
		
		return false;
	}
	
	
	public void setStorageLocation(BlockPos pos)
	{
		
			StorageLocation = pos;
		
	}
	
	
	
	public boolean DoesStorageTEHaveItems(List<BlockList> ReferenceList)
	{
		this.GatherAllItems();
		
		
		if(this.TotalItems != null)	
		{
			for(int i =0; i < ReferenceList.size(); i ++)
			{
				for(int j =0; j < TotalItems.size(); j ++)
				{
					if(TotalItems.get(j).getItem().isItemEqual(new ItemStack(ReferenceList.get(i).Blockerino.getBlock())))
					{
						return true;
					}
				}
			}
		}
		return false;
	}
	

	

	public List<StorageAIItemFormat> GetItemList() // not used 
	{
		List<StorageAIItemFormat> NearestChests = new ArrayList();
		
		if(NearestChests.size() == 0)
		{
			return null;
		}
		for(int i = 0; i < World.loadedEntityList.size(); i ++)
		{
			if(World.loadedEntityList.get(i) instanceof EntityFarmer)
			{
				EntityFarmer TestFarmer = (EntityFarmer) World.loadedEntityList.get(i);
				if(TestFarmer.getJob().contains("StorageAI"))
				{
					if(TestFarmer.getPosition().distanceSq(this.StorageLocation.getX(), this.StorageLocation.getY(),this.StorageLocation.getZ()) <= 100 )
					{
						NearestChests = this.GatherAllItems();
					}
				}
			}
		}
		
		return NearestChests;
	}
	
	public List<TileEntityChest> GetNearestChests()
	{
		List<TileEntityChest> NearestChests = new ArrayList();
		
		for(int i = 0; i < World.loadedTileEntityList.size(); i ++)
		{
			if(World.loadedTileEntityList.get(i) instanceof TileEntityChest)
			{
				if(World.loadedTileEntityList.get(i).getPos().distanceSq(this.StorageLocation.getX(), this.StorageLocation.getY(), this.StorageLocation.getZ()) <= 120 )
				{
					NearestChests.add((TileEntityChest) World.loadedTileEntityList.get(i));
				}		
			}
		}
		
		return NearestChests;
	}
	
	public List<StorageAIItemFormat> GatherAllItems()
	{
		
		List<TileEntityChest> NearestChests = this.GetNearestChests();
		
		if(NearestChests.size() != 0)
		{
			for(int i = 0; i < NearestChests.size(); i ++)
			{
				for(int j = 0; j < NearestChests.get(i).getSizeInventory(); j ++)
				{
					if(NearestChests.get(i).getStackInSlot(j) != null)
					{
					//	if(AlreadyInTheList(NearestChests.get(i).getStackInSlot(j), i ,NearestChests.get(i).getStackInSlot(j).stackSize, this.TotalItems))
				//		{
							StorageAIItemFormat b = new StorageAIItemFormat(i, NearestChests.get(i).getStackInSlot(j), NearestChests.get(i).getStackInSlot(j).stackSize);
							TotalItems.add(b);
							
						//}
					}
				}
			}
		}
		SortStorageAIItemFormatList();
		return TotalItems;
	}

	
	public boolean AlreadyInTheList(ItemStack Checking, int ChestIndex, int HowMany, List<StorageAIItemFormat> List)
	{
		boolean NotInsideList = true;
		
		for(int i = 0; i < List.size(); i++){
		
			if(List.get(i).getItem().isItemEqual(Checking) && List.get(i).getChestIndex() == ChestIndex)
			{
				NotInsideList = false;
				List.get(i).ChangeHowMany(HowMany);
				break;
			}
		}
		
		return NotInsideList;
	}
	
	
	

	
	public TileEntityKingdomStructureBuilderBlock getBuilderTE()
	{
		TileEntityKingdomStructureBuilderBlock B1 = null;
		
		for(int j = 0; j < World.loadedTileEntityList.size(); j ++)
		{
			
			if(World.loadedTileEntityList.get(j).getPos() != null)
			{
				if(World.loadedTileEntityList.get(j) instanceof TileEntityKingdomStructureBuilderBlock)
				{
						BlockPos SupplyPos = World.loadedTileEntityList.get(j).getPos();
						
						if(Farmer.getWorkPlace().distanceSq(SupplyPos.getX(), SupplyPos.getY(), SupplyPos.getZ()) <= 100)
						{
						
							 B1 = (TileEntityKingdomStructureBuilderBlock)World.getTileEntity(SupplyPos);
						}
				}
			}
		}
		
		
		return B1;
	}
	
	
	
	
}


