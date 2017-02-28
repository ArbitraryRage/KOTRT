package com.waabbuffet.kotrt.tileEntities.structure;

import java.util.Set;

import com.google.common.collect.Sets;
import com.waabbuffet.kotrt.KnightsOfTheRoundTable;
import com.waabbuffet.kotrt.entities.Kingdom.EntityFarmer;
import com.waabbuffet.kotrt.util.StructureTileEntityFormat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.ITickable;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

public class TileEntityKingdomStructureBlock extends TileEntity implements IInventory, ITickable
{
	public ItemStack[] inventory;
	public ItemStack[] RequestedInventory; 
   
    private String customName = "Structure's Inventory";
    private Ticket chunkTicket;
    
    int cooldown = 0;
    boolean NeedTool;
    
    
   public StructureTileEntityFormat structure = new StructureTileEntityFormat();

    public TileEntityKingdomStructureBlock() {
    	
        this.inventory = new ItemStack[this.getSizeInventory()];
        this.RequestedInventory = new ItemStack[10];
        
    }
    
    @Override
    public Packet getDescriptionPacket() {
    	//Debug
    //	System.out.println("[DEBUG]:Server sent tile sync packet");

    	NBTTagCompound tagCompound = new NBTTagCompound();
    	this.writeToNBT(tagCompound);
    	return new SPacketUpdateTileEntity(this.getPos(), 1, tagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
    	//Debug
    //	System.out.println("[DEBUG]:Client recived tile sync packet");

    	readFromNBT(pkt.getNbtCompound());
    	worldObj.markBlockForUpdate(this.getPos());
    	this.markDirty();
    }
    
    
    public void setNeedTool(boolean needTool) {
    	
		NeedTool = needTool;
		
	}
    
   
    
    public String getCustomName() {
        return this.customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }
	
	
    @Override
    public String getName() {
        return this.hasCustomName() ? this.customName : "container.kotrt_kingdom_structure_block";
    }

    @Override
    public boolean hasCustomName() {
        return this.customName != null && !this.customName.equals("");
    }

    @Override
    public ITextComponent getDisplayName() {
        return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
    }

	@Override
	public int getSizeInventory() {
		//change to be dependent on what tier the building is
		return 10;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
	    if (index < 0 || index >= this.getSizeInventory())
	        return null;
	    return this.inventory[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
	    if (this.getStackInSlot(index) != null) {
	        ItemStack itemstack;

	        if (this.getStackInSlot(index).stackSize <= count) {
	            itemstack = this.getStackInSlot(index);
	            this.setInventorySlotContents(index, null);
	            this.markDirty();
	            return itemstack;
	        } else {
	            itemstack = this.getStackInSlot(index).splitStack(count);

	            if (this.getStackInSlot(index).stackSize <= 0) {
	                this.setInventorySlotContents(index, null);
	            } else {
	                //Just to show that changes happened
	                this.setInventorySlotContents(index, this.getStackInSlot(index));
	            }

	            this.markDirty();
	            return itemstack;
	        }
	    } else {
	        return null;
	    }
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
	    ItemStack stack = this.getStackInSlot(index);
	    this.setInventorySlotContents(index, null);
	    return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
	    if (index < 0 || index >= this.getSizeInventory())
	        return;

	    if (stack != null && stack.stackSize > this.getInventoryStackLimit())
	        stack.stackSize = this.getInventoryStackLimit();
	        
	    if (stack != null && stack.stackSize == 0)
	        stack = null;

	    this.inventory[index] = stack;
	    this.markDirty();
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
	    return this.worldObj.getTileEntity(this.getPos()) == this && player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		// not needed
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		// not needed
		
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
	    for (int i = 0; i < this.getSizeInventory(); i++)
	        this.setInventorySlotContents(i, null);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
	    super.writeToNBT(nbt);

	    if(this.structure.getName() != null)
	    {
	    	nbt.setString("FileName1", this.structure.getName());
	    	nbt.setString("Direction1", this.structure.getDirection());
	    }
	    
	 
	    
	    nbt.setInteger("StartX1", this.structure.getStartX());
	    nbt.setInteger("StartY1", this.structure.getStartY());
	    nbt.setInteger("StartZ1", this.structure.getStartZ());
	    
	    nbt.setInteger("NumberOfWorkers1", this.structure.getCurrentWorkers());
	    
	    
	    NBTTagList list = new NBTTagList();
	    for (int i = 0; i < this.getSizeInventory(); ++i) {
	        if (this.getStackInSlot(i) != null) {
	            NBTTagCompound stackTag = new NBTTagCompound();
	            stackTag.setByte("Slot", (byte) i);
	            this.getStackInSlot(i).writeToNBT(stackTag);
	            list.appendTag(stackTag);
	        }
	    }
	    nbt.setTag("Items", list);

	    if (this.hasCustomName()) {
	        nbt.setString("CustomName", this.getCustomName());
	    }
	}


	@Override
	public void readFromNBT(NBTTagCompound nbt) {
	    super.readFromNBT(nbt);

	    this.structure.setName(nbt.getString("FileName1"));
	    this.structure.setDirection(nbt.getString("Direction1"));
	    
	    this.structure.setStartX(nbt.getInteger("StartX1"));
	    this.structure.setStartY(nbt.getInteger("StartY1"));
	    this.structure.setStartZ(nbt.getInteger("StartZ1"));
	    
	    this.structure.setCurrentWorkers(nbt.getInteger("NumberOfWorkers1"));
	    
	    
	    NBTTagList list = nbt.getTagList("Items", 10);
	    for (int i = 0; i < list.tagCount(); ++i) {
	        NBTTagCompound stackTag = list.getCompoundTagAt(i);
	        int slot = stackTag.getByte("Slot") & 255;
	        this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(stackTag));
	    }

	    if (nbt.hasKey("CustomName", 8)) {
	        this.setCustomName(nbt.getString("CustomName"));
	    }
	}


	public boolean IsLocalTaken(String Name, World world, BlockPos pos)
	{
		boolean YeaOr = false;
		
		for(int i =0; i < world.loadedEntityList.size(); i ++)
		{
			Entity b = world.loadedEntityList.get(i);
			
			if(b instanceof EntityFarmer)
			{
				System.out.println("Pos: " + pos);
				System.out.println("EntityFarmer: " + ((EntityFarmer) b).getWorkPlace());
				System.out.println(pos.up().equals(((EntityFarmer) b).getWorkPlace()));
				if(pos.equals(((EntityFarmer) b).getWorkPlace()) && Name.contains(((EntityFarmer) b).getJob()))
				{
					YeaOr = true;
					break;
				}
			}
		}
		
		return YeaOr;
	}

	public BlockPos GetWorkPlaceLocationsBasedOnName(String Name, int startX, int startY, int startZ, int NumberOfWorkers, String Direction)
	{
		BlockPos WhereToPlace = null;
			
	
		if(Direction.contains("East"))
		{
			if(Name.contains("Town Hall"))
			{
				//Doesnt Need Workers	
			}else if(Name.contains("Miners Guild"))
			{
				// 730 8 -1335 - > 747 8 -1331
				// 730 8 -1335 - > 755 8 -1327
				// 730 8 -1335 - > 755 9 -1319
				
				
				
			if(NumberOfWorkers == 0)
			{
				WhereToPlace = new BlockPos(startX + 17, startY, startZ + 3);
			}else if(NumberOfWorkers == 1)
			{
				WhereToPlace = new BlockPos(startX + 25, startY, startZ + 8);
			}else if(NumberOfWorkers == 2)
			{
				WhereToPlace = new BlockPos(startX + 25, startY + 1, startZ + 16);
			}else{
				
			}
				
			}else if(Name.contains("Lumberjack Guild"))
			{
				//719 8 -1379 - > 720 8 -1379
				// 719 8 -1379 - > 720 8 -1360
				// 719 8 -1379 - > 769 5 -1368
				// 719 8 -1379 - > 834 5 -1383
				if(NumberOfWorkers == 0)
				{
					WhereToPlace = new BlockPos(startX, startY, startZ);
				}else if(NumberOfWorkers == 1)
				{
					WhereToPlace = new BlockPos(startX, startY, startZ + 19);
				}else if(NumberOfWorkers == 2)
				{
					WhereToPlace = new BlockPos(startX + 50, startY, startZ);
				}else if(NumberOfWorkers == 3)
				{
					WhereToPlace = new BlockPos(startX + 50, startY, startZ + 19);
				}
				
			}else if(Name.contains("Small House"))
			{
				//-970 5 -277 -> -960 8 -269
				//-970 5 -277 -> -959 8 -259
				//-970 5 -277 -> -959 17 -267
				//-970 5 -277 -> -962 12 -263
				
				
				if(NumberOfWorkers == 0)
				{
					WhereToPlace = new BlockPos(startX + 10, startY + 4, startZ + 8);
				}else if(NumberOfWorkers == 1)
				{
					WhereToPlace = new BlockPos(startX + 11, startY + 4, startZ + 18);
				}else if(NumberOfWorkers == 2)
				{
					WhereToPlace = new BlockPos(startX + 11, startY + 13, startZ + 10);
				}else if(NumberOfWorkers == 3)
				{
					WhereToPlace = new BlockPos(startX + 8, startY + 8, startZ + 14);
				}
				
				
			}else if(Name.contains("Shopkeeper Guild"))
			{
				//810 6 -454 - > -800 9 -417
				//810 6 -454 - > -796 9 -447
				//810 6 -454 - > -796 9 -443
				
				
			}else if(Name.contains("Wheat Farmer Guild"))
			{
				//900 7 -1430 - > 905 8 -1426
				//900 7 -1430  - > 905 8 -1416
				//900 7 -1430  - > 905 8 -1411
				//900 7 -1430  - > 910 8 -1411
				
				
				if(NumberOfWorkers == 0)
				{
					WhereToPlace = new BlockPos(startX + 5, startY, startZ + 4);
				}else if(NumberOfWorkers == 1)
				{
					WhereToPlace = new BlockPos(startX + 5, startY, startZ + 14);
				}else if(NumberOfWorkers == 2)
				{
					WhereToPlace = new BlockPos(startX + 5, startY, startZ + 19);
				}else if(NumberOfWorkers == 3)
				{
					WhereToPlace = new BlockPos(startX + 10, startY, startZ + 19);
				}else if(NumberOfWorkers == 4)
				{
					WhereToPlace = new BlockPos(startX + 10, startY, startZ + 14);
				}
				
			}else if(Name.contains("Carpenter"))
			{
				//-1100 5 1527 -> -1077 8 1567
				//-1100 5 1527 -> - 1077 15 1568
				//-1100 5 1527 -> -1080 15 1551
				//-1100 5 1527 -> -1080 8 1551
				if(NumberOfWorkers == 0)
				{
					WhereToPlace = new BlockPos(startX + 23, startY + 3, startZ + 40);
				}else if(NumberOfWorkers == 1)
				{
					WhereToPlace = new BlockPos(startX + 23, startY + 10, startZ + 41);
				}else if(NumberOfWorkers == 2)
				{
					WhereToPlace = new BlockPos(startX + 20, startY + 10, startZ + 24);
				}else if(NumberOfWorkers == 3)
				{
					WhereToPlace = new BlockPos(startX + 20, startY + 3, startZ + 24);
				}
				
			}else if(Name.contains("Blacksmith"))
			{
				// -1020 5 1531 -> -1008 5 1554
				// -1020 5 1531 - > -999 5 1554
				// -1020 5 1531 - > -1006 11 1556
				// -1020 5 1531 - > -993 11 1554
				if(NumberOfWorkers == 0)
				{
					WhereToPlace = new BlockPos(startX + 12, startY, startZ + 23);
				}else if(NumberOfWorkers == 1)
				{
					WhereToPlace = new BlockPos(startX + 21, startY, startZ + 23);
				}else if(NumberOfWorkers == 2)
				{
					WhereToPlace = new BlockPos(startX + 14, startY, startZ + 25);
				}else if(NumberOfWorkers == 3)
				{
					WhereToPlace = new BlockPos(startX + 27, startY, startZ + 23);
				}
				
			}else if(Name.contains("Storage Hut"))
			{
				//-970 5 1491 - > -944 7 1524
				if(NumberOfWorkers == 0)
				{
					WhereToPlace = new BlockPos(startX + 26, startY + 2, startZ + 33);
				}
			}else if(Name.contains("Messanger Guild"))
			{
				//-915 5 -308 -> -898 5 -298
				//-915 5 -308 -> -892 10 -295
				//-915 5 -308 -> =904 10 -303
				//-915 5 -308 ->
				if(NumberOfWorkers == 0)
				{
					WhereToPlace = new BlockPos(startX + 17, startY, startZ + 10);
				}else if(NumberOfWorkers == 1)
				{
					WhereToPlace = new BlockPos(startX + 23, startY + 5, startZ + 13);
				}else if(NumberOfWorkers == 2)
				{
					WhereToPlace = new BlockPos(startX + 11, startY + 5, startZ + 5);
				}
				
			}else if(Name.contains("Exotic Farmers guild"))
			{
				//-895 5 -385 - > -894 5 -384
				//-840 5 399 - > -839 5 -389
				//-840 5 399 - > - 839 5 - 380
				//-840 5 399 - > -810 6 -398
				//-840 5 399 - >
				//-840 5 399 - >
				if(NumberOfWorkers == 0)
				{
					WhereToPlace = new BlockPos(startX + 1, startY, startZ + 1);
				}else if(NumberOfWorkers == 1)
				{
					WhereToPlace = new BlockPos(startX + 1, startY, startZ + 10);
				}else if(NumberOfWorkers == 2)
				{
					WhereToPlace = new BlockPos(startX + 1, startY, startZ + 19);
				}else if(NumberOfWorkers == 3)
				{
					WhereToPlace = new BlockPos(startX + 30, startY + 1, startZ + 1);
				}else if(NumberOfWorkers == 4)
				{
					WhereToPlace = new BlockPos(startX + 30, startY + 1, startZ + 10);
				}else if(NumberOfWorkers == 5)
				{
					WhereToPlace = new BlockPos(startX + 30, startY + 1, startZ + 19);
				}
			}else if(Name.contains("Builders Guild"))
			{
				//-780 5 -396 -> -767 5 -379
				//-780 5 -396 -> -767 5 -369
				//-780 5 -396  - > -756 5 - 376
				if(NumberOfWorkers == 0)
				{
					WhereToPlace = new BlockPos(startX + 13, startY, startZ + 17);
				}else if(NumberOfWorkers == 1)
				{
					WhereToPlace = new BlockPos(startX + 1, startY, startZ + 27);
				}else if(NumberOfWorkers == 2)
				{
					WhereToPlace = new BlockPos(startX + 24, startY, startZ + 20);
				}
			}
	}else if(Direction.contains("West"))
	{
		if(Name.contains("Town Hall"))
		{
			//Doesnt Need Workers	
		}else if(Name.contains("Miners Guild"))
		{
			// 730 8 -1335 - > 747 8 -1331
			// 730 8 -1335 - > 755 8 -1327
			// 730 8 -1335 - > 755 9 -1319
			
			
			
		if(NumberOfWorkers == 0)
		{
			WhereToPlace = new BlockPos(startX - 17, startY, startZ - 3);
		}else if(NumberOfWorkers == 1)
		{
			WhereToPlace = new BlockPos(startX - 25, startY, startZ - 8);
		}else if(NumberOfWorkers == 2)
		{
			WhereToPlace = new BlockPos(startX - 25, startY + 1, startZ - 16);
		}else{
			
		}
			
		}else if(Name.contains("Lumberjack Guild"))
		{
			//719 8 -1379 - > 720 8 -1379
			// 719 8 -1379 - > 720 8 -1360
			// 719 8 -1379 - > 769 5 -1368
			// 719 8 -1379 - > 834 5 -1383
			if(NumberOfWorkers == 0)
			{
				WhereToPlace = new BlockPos(startX - 4, startY, startZ - 4);
			}else if(NumberOfWorkers == 1)
			{
				WhereToPlace = new BlockPos(startX - 4, startY, startZ - 23);
			}else if(NumberOfWorkers == 2)
			{
				WhereToPlace = new BlockPos(startX - 54, startY, startZ - 4);
			}else if(NumberOfWorkers == 3)
			{
				WhereToPlace = new BlockPos(startX - 54, startY, startZ - 23);
			}
			
		}else if(Name.contains("Small House"))
		{
			//-970 5 -277 -> -960 8 -269
			//-970 5 -277 -> -959 8 -259
			//-970 5 -277 -> -959 17 -267
			//-970 5 -277 -> -962 12 -263
			
			
			if(NumberOfWorkers == 0)
			{
				WhereToPlace = new BlockPos(startX - 10, startY + 4, startZ - 8);
			}else if(NumberOfWorkers == 1)
			{
				WhereToPlace = new BlockPos(startX - 11, startY + 4, startZ - 18);
			}else if(NumberOfWorkers == 2)
			{
				WhereToPlace = new BlockPos(startX - 11, startY + 13, startZ - 10);
			}else if(NumberOfWorkers == 3)
			{
				WhereToPlace = new BlockPos(startX - 8, startY + 8, startZ - 14);
			}
			
			
		}else if(Name.contains("Shopkeeper Guild"))
		{
			//810 6 -454 - > -800 9 -417
			//810 6 -454 - > -796 9 -447
			//810 6 -454 - > -796 9 -443
			
			
		}else if(Name.contains("Wheat Farmer Guild"))
		{
			//900 7 -1430 - > 905 8 -1426
			//900 7 -1430  - > 905 8 -1416
			//900 7 -1430  - > 905 8 -1411
			//900 7 -1430  - > 910 8 -1411
			
			
			if(NumberOfWorkers == 0)
			{
				WhereToPlace = new BlockPos(startX - 9, startY, startZ - 8);
			}else if(NumberOfWorkers == 1)
			{
				WhereToPlace = new BlockPos(startX - 9, startY, startZ - 18);
			}else if(NumberOfWorkers == 2)
			{
				WhereToPlace = new BlockPos(startX - 9, startY, startZ - 23);
			}else if(NumberOfWorkers == 3)
			{
				WhereToPlace = new BlockPos(startX - 14, startY, startZ - 23);
			}else if(NumberOfWorkers == 4)
			{
				WhereToPlace = new BlockPos(startX - 14, startY, startZ - 18);
			}
			
		}else if(Name.contains("Carpenter"))
		{
			//-1100 5 1527 -> -1077 8 1567
			//-1100 5 1527 -> - 1077 15 1568
			//-1100 5 1527 -> -1080 15 1551
			//-1100 5 1527 -> -1080 8 1551
			if(NumberOfWorkers == 0)
			{
				WhereToPlace = new BlockPos(startX - 23, startY + 3, startZ - 40);
			}else if(NumberOfWorkers == 1)
			{
				WhereToPlace = new BlockPos(startX - 23, startY + 10, startZ - 41);
			}else if(NumberOfWorkers == 2)
			{
				WhereToPlace = new BlockPos(startX - 20, startY + 10, startZ - 24);
			}else if(NumberOfWorkers == 3)
			{
				WhereToPlace = new BlockPos(startX - 20, startY + 3, startZ - 24);
			}
			
		}else if(Name.contains("Blacksmith"))
		{
			// -1020 5 1531 -> -1008 5 1554
			// -1020 5 1531 - > -999 5 1554
			// -1020 5 1531 - > -1006 11 1556
			// -1020 5 1531 - > -993 11 1554
			if(NumberOfWorkers == 0)
			{
				WhereToPlace = new BlockPos(startX - 12, startY, startZ - 23);
			}else if(NumberOfWorkers == 1)
			{
				WhereToPlace = new BlockPos(startX - 21, startY, startZ - 23);
			}else if(NumberOfWorkers == 2)
			{
				WhereToPlace = new BlockPos(startX - 14, startY, startZ - 25);
			}else if(NumberOfWorkers == 3)
			{
				WhereToPlace = new BlockPos(startX - 27, startY, startZ - 23);
			}
			
		}else if(Name.contains("Storage Hut"))
		{
			//-970 5 1491 - > -944 7 1524
			if(NumberOfWorkers == 0)
			{
				WhereToPlace = new BlockPos(startX - 26, startY + 2, startZ - 33);
			}
		}else if(Name.contains("Messanger Guild"))
		{
			//-915 5 -308 -> -898 5 -298
			//-915 5 -308 -> -892 10 -295
			//-915 5 -308 -> =904 10 -303
			//-915 5 -308 ->
			if(NumberOfWorkers == 0)
			{
				WhereToPlace = new BlockPos(startX - 17, startY, startZ - 10);
			}else if(NumberOfWorkers == 1)
			{
				WhereToPlace = new BlockPos(startX - 23, startY + 5, startZ - 13);
			}else if(NumberOfWorkers == 2)
			{
				WhereToPlace = new BlockPos(startX - 11, startY + 5, startZ - 5);
			}
			
		}else if(Name.contains("Exotic Farmers guild"))
		{
			//-895 5 -385 - > -894 5 -384
			//-840 5 399 - > -839 5 -389
			//-840 5 399 - > - 839 5 - 380
			//-840 5 399 - > -810 6 -398
			//-840 5 399 - >
			//-840 5 399 - >
			if(NumberOfWorkers == 0)
			{
				WhereToPlace = new BlockPos(startX - 8, startY, startZ - 8);
			}else if(NumberOfWorkers == 1)
			{
				WhereToPlace = new BlockPos(startX - 8, startY, startZ - 18);
			}else if(NumberOfWorkers == 2)
			{
				WhereToPlace = new BlockPos(startX - 8, startY, startZ - 27);
			}else if(NumberOfWorkers == 3)
			{
				WhereToPlace = new BlockPos(startX - 37, startY + 1, startZ - 8);
			}else if(NumberOfWorkers == 4)
			{
				WhereToPlace = new BlockPos(startX - 37, startY + 1, startZ - 18);
			}else if(NumberOfWorkers == 5)
			{
				WhereToPlace = new BlockPos(startX - 37, startY + 1, startZ - 27);
			}
		}else if(Name.contains("Builders Guild"))
		{
			//-780 5 -396 -> -767 5 -379
			//-780 5 -396 -> -767 5 -369
			//-780 5 -396  - > -756 5 - 376
			if(NumberOfWorkers == 0)
			{
				WhereToPlace = new BlockPos(startX - 13, startY, startZ - 17);
			}else if(NumberOfWorkers == 1)
			{
				WhereToPlace = new BlockPos(startX - 1, startY, startZ - 27);
			}else if(NumberOfWorkers == 2)
			{
				WhereToPlace = new BlockPos(startX - 24, startY, startZ - 20);
			}
		}
		}else if(Direction.contains("North"))
		{

			if(Name.contains("Town Hall"))
			{
				//Doesnt Need Workers	
			}else if(Name.contains("Miners Guild"))
			{
				// 730 8 -1335 - > 747 8 -1331
				// 730 8 -1335 - > 755 8 -1327
				// 730 8 -1335 - > 755 9 -1319
				
				
				
			if(NumberOfWorkers == 0)
			{
				WhereToPlace = new BlockPos(startX + 3, startY, startZ - 17);
			//	WhereToPlace = new BlockPos(startX + 17, startY, startZ + 4);
			}else if(NumberOfWorkers == 1)
			{
				WhereToPlace = new BlockPos(startX + 8, startY, startZ - 25);
			//	WhereToPlace = new BlockPos(startX + 25, startY, startZ + 8);
			}else if(NumberOfWorkers == 2)
			{
				WhereToPlace = new BlockPos(startX + 16, startY + 1, startZ - 25);
				//WhereToPlace = new BlockPos(startX + 25, startY + 1, startZ + 16);
			}else{
				
			}
				
			}else if(Name.contains("Lumberjack Guild"))
			{
				//719 8 -1379 - > 720 8 -1379
				// 719 8 -1379 - > 720 8 -1360
				// 719 8 -1379 - > 769 5 -1368
				// 719 8 -1379 - > 834 5 -1383
				if(NumberOfWorkers == 0)
				{
					WhereToPlace = new BlockPos(startX, startY, startZ - 4);
				}else if(NumberOfWorkers == 1)
				{
					WhereToPlace = new BlockPos(startX + 19, startY, startZ - 4);
					//WhereToPlace = new BlockPos(startX, startY, startZ + 19);
				}else if(NumberOfWorkers == 2)
				{
					 WhereToPlace = new BlockPos(startX, startY, startZ - 54);
					//WhereToPlace = new BlockPos(startX + 50, startY, startZ);
				}else if(NumberOfWorkers == 3)
				{
					WhereToPlace = new BlockPos(startX + 19, startY, startZ - 54);
					//WhereToPlace = new BlockPos(startX + 50, startY, startZ + 19);
				}
				
			}else if(Name.contains("Small House"))
			{
				//-970 5 -277 -> -960 8 -269
				//-970 5 -277 -> -959 8 -259
				//-970 5 -277 -> -959 17 -267
				//-970 5 -277 -> -962 12 -263
				
				
				if(NumberOfWorkers == 0)
				{
					WhereToPlace = new BlockPos(startX + 8, startY + 4, startZ - 10);
				//	WhereToPlace = new BlockPos(startX + 10, startY + 4, startZ + 8);
				}else if(NumberOfWorkers == 1)
				{
					WhereToPlace = new BlockPos(startX + 18, startY + 4, startZ - 11);
				//	WhereToPlace = new BlockPos(startX + 11, startY + 4, startZ + 18);
				}else if(NumberOfWorkers == 2)
				{
					WhereToPlace = new BlockPos(startX + 10, startY + 13, startZ - 11);
				//	WhereToPlace = new BlockPos(startX + 11, startY + 13, startZ + 10);
				}else if(NumberOfWorkers == 3)
				{
					WhereToPlace = new BlockPos(startX + 14, startY + 8, startZ - 8);
				//	WhereToPlace = new BlockPos(startX + 8, startY + 8, startZ + 14);
				}
				
				
			}else if(Name.contains("Shopkeeper Guild"))
			{
				//810 6 -454 - > -800 9 -417
				//810 6 -454 - > -796 9 -447
				//810 6 -454 - > -796 9 -443
				
				
			}else if(Name.contains("Wheat Farmer Guild"))
			{
				//900 7 -1430 - > 905 8 -1426
				//900 7 -1430  - > 905 8 -1416
				//900 7 -1430  - > 905 8 -1411
				//900 7 -1430  - > 910 8 -1411
				
				
				if(NumberOfWorkers == 0)
				{
					WhereToPlace = new BlockPos(startX + 4, startY, startZ - 17);
				//	WhereToPlace = new BlockPos(startX + 5, startY, startZ + 4);
				}else if(NumberOfWorkers == 1)
				{
					WhereToPlace = new BlockPos(startX + 14, startY, startZ - 17);
				//	WhereToPlace = new BlockPos(startX + 5, startY, startZ + 14);
				}else if(NumberOfWorkers == 2)
				{
					WhereToPlace = new BlockPos(startX + 19, startY, startZ - 17);
					//WhereToPlace = new BlockPos(startX + 10, startY, startZ + 19);
				}else if(NumberOfWorkers == 3)
				{
					WhereToPlace = new BlockPos(startX + 19, startY, startZ - 14);
					//WhereToPlace = new BlockPos(startX + 10, startY, startZ + 19);
				}else if(NumberOfWorkers == 4)
				{
					WhereToPlace = new BlockPos(startX + 14, startY, startZ - 14);
				//	WhereToPlace = new BlockPos(startX + 10, startY, startZ + 14);
				}
				
			}else if(Name.contains("Carpenter"))
			{
				//-1100 5 1527 -> -1077 8 1567
				//-1100 5 1527 -> - 1077 15 1568
				//-1100 5 1527 -> -1080 15 1551
				//-1100 5 1527 -> -1080 8 1551
				if(NumberOfWorkers == 0)
				{
					WhereToPlace = new BlockPos(startX + 40, startY + 3, startZ - 23);
				//	WhereToPlace = new BlockPos(startX + 23, startY + 3, startZ + 40);
				}else if(NumberOfWorkers == 1)
				{
					WhereToPlace = new BlockPos(startX + 41, startY + 10, startZ - 23);
					//WhereToPlace = new BlockPos(startX + 23, startY + 10, startZ + 41);
				}else if(NumberOfWorkers == 2)
				{
					WhereToPlace = new BlockPos(startX + 24, startY + 10, startZ - 20);
				//	WhereToPlace = new BlockPos(startX + 20, startY + 10, startZ + 24);
				}else if(NumberOfWorkers == 3)
				{
					WhereToPlace = new BlockPos(startX + 24, startY + 3, startZ - 20);
				//	WhereToPlace = new BlockPos(startX + 20, startY + 3, startZ + 24);
				}
				
			}else if(Name.contains("Blacksmith"))
			{
				// -1020 5 1531 -> -1008 5 1554
				// -1020 5 1531 - > -999 5 1554
				// -1020 5 1531 - > -1006 11 1556
				// -1020 5 1531 - > -993 11 1554
				if(NumberOfWorkers == 0)
				{
					WhereToPlace = new BlockPos(startX + 23, startY, startZ - 12);
				//	WhereToPlace = new BlockPos(startX + 12, startY, startZ + 23);
				}else if(NumberOfWorkers == 1)
				{
					WhereToPlace = new BlockPos(startX + 23, startY, startZ - 21);
				//	WhereToPlace = new BlockPos(startX + 21, startY, startZ + 23);
				}else if(NumberOfWorkers == 2)
				{
					WhereToPlace = new BlockPos(startX + 25, startY, startZ - 14);
					//WhereToPlace = new BlockPos(startX + 14, startY, startZ + 25);
				}else if(NumberOfWorkers == 3)
				{
					WhereToPlace = new BlockPos(startX + 23, startY, startZ - 27);
				//	WhereToPlace = new BlockPos(startX + 27, startY, startZ + 23);
				}
				
			}else if(Name.contains("Storage Hut"))
			{
				//-970 5 1491 - > -944 7 1524
				if(NumberOfWorkers == 0)
				{
					WhereToPlace = new BlockPos(startX + 33, startY + 2, startZ - 26);
				//	WhereToPlace = new BlockPos(startX + 26, startY + 2, startZ + 33);
				}
			}else if(Name.contains("Messanger Guild"))
			{
				//-915 5 -308 -> -898 5 -298
				//-915 5 -308 -> -892 10 -295
				//-915 5 -308 -> =904 10 -303
				//-915 5 -308 ->
				
				
				if(NumberOfWorkers == 0)
				{
					WhereToPlace = new BlockPos(startX + 10, startY, startZ - 17);
				//	WhereToPlace = new BlockPos(startX + 17, startY, startZ + 10);
				}else if(NumberOfWorkers == 1)
				{
					WhereToPlace = new BlockPos(startX + 13, startY + 5, startZ - 23);
				//	WhereToPlace = new BlockPos(startX + 23, startY + 5, startZ + 13);
				}else if(NumberOfWorkers == 2)
				{
					WhereToPlace = new BlockPos(startX + 5, startY + 5, startZ - 11);
				//	WhereToPlace = new BlockPos(startX + 11, startY + 5, startZ + 5);
				}
				
			}else if(Name.contains("Exotic Farmers guild"))
			{
				//-895 5 -385 - > -894 5 -384
				//-840 5 399 - > -839 5 -389
				//-840 5 399 - > - 839 5 - 380
				//-840 5 399 - > -810 6 -398
				//-840 5 399 - >
				//-840 5 399 - >
				
				
				
				
				if(NumberOfWorkers == 0)
				{
					WhereToPlace = new BlockPos(startX + 1, startY, startZ - 8);
				//	WhereToPlace = new BlockPos(startX + 1, startY, startZ + 1);
				}else if(NumberOfWorkers == 1)
				{
					WhereToPlace = new BlockPos(startX + 10, startY, startZ - 8);
				//	WhereToPlace = new BlockPos(startX + 1, startY, startZ + 10);
				}else if(NumberOfWorkers == 2)
				{
					WhereToPlace = new BlockPos(startX + 19, startY, startZ - 8);
				//	WhereToPlace = new BlockPos(startX + 1, startY, startZ + 19);
				}else if(NumberOfWorkers == 3)
				{
					WhereToPlace = new BlockPos(startX + 1, startY + 1, startZ - 37);
				//	WhereToPlace = new BlockPos(startX + 30, startY + 1, startZ + 1);
				}else if(NumberOfWorkers == 4)
				{
					WhereToPlace = new BlockPos(startX + 10, startY + 1, startZ - 37);
				//	WhereToPlace = new BlockPos(startX + 30, startY + 1, startZ + 10);
				}else if(NumberOfWorkers == 5)
				{
					WhereToPlace = new BlockPos(startX + 19, startY + 1, startZ - 37);
				//	WhereToPlace = new BlockPos(startX + 30, startY + 1, startZ + 19);
				}
			}else if(Name.contains("Builders Guild"))
			{
				//-780 5 -396 -> -767 5 -379
				//-780 5 -396 -> -767 5 -369
				//-780 5 -396  - > -756 5 - 376
				if(NumberOfWorkers == 0)
				{
					WhereToPlace = new BlockPos(startX + 17, startY, startZ - 13);
				//	WhereToPlace = new BlockPos(startX + 13, startY, startZ + 17);
				}else if(NumberOfWorkers == 1)
				{
					WhereToPlace = new BlockPos(startX + 27, startY, startZ - 1);
				//	WhereToPlace = new BlockPos(startX + 1, startY, startZ + 27);
				}else if(NumberOfWorkers == 2)
				{
					WhereToPlace = new BlockPos(startX + 20, startY, startZ - 24);
				//	WhereToPlace = new BlockPos(startX + 24, startY, startZ + 20);
				}
			}
		}else if(Direction.contains("South"))
		{
			
			if(Name.contains("Town Hall"))
			{
				//Doesnt Need Workers	
			}else if(Name.contains("Miners Guild"))
			{
				// 730 8 -1335 - > 747 8 -1331
				// 730 8 -1335 - > 755 8 -1327
				// 730 8 -1335 - > 755 9 -1319
				
				
					
				if(NumberOfWorkers == 0)
				{
					WhereToPlace = new BlockPos(startX - 3, startY, startZ + 17);
				//	WhereToPlace = new BlockPos(startX + 17, startY, startZ + 4);
				}else if(NumberOfWorkers == 1)
				{
					WhereToPlace = new BlockPos(startX - 8, startY, startZ + 25);
				//	WhereToPlace = new BlockPos(startX + 25, startY, startZ + 8);
				}else if(NumberOfWorkers == 2)
				{
					WhereToPlace = new BlockPos(startX  -16, startY + 1, startZ + 25);
					//WhereToPlace = new BlockPos(startX + 25, startY + 1, startZ + 16);
				}else{
					
				}
				
			}else if(Name.contains("Lumberjack Guild"))
			{
				
				//719 8 -1379 - > 720 8 -1379
				// 719 8 -1379 - > 720 8 -1360
				// 719 8 -1379 - > 769 5 -1368
				// 719 8 -1379 - > 834 5 -1383
				if(NumberOfWorkers == 0)
				{
					WhereToPlace = new BlockPos(startX - 4, startY, startZ);
				}else if(NumberOfWorkers == 1)
				{
					WhereToPlace = new BlockPos(startX - 23, startY, startZ);
					//WhereToPlace = new BlockPos(startX, startY, startZ + 19);
				}else if(NumberOfWorkers == 2)
				{
					 WhereToPlace = new BlockPos(startX - 4, startY, startZ + 54);
					//WhereToPlace = new BlockPos(startX + 50, startY, startZ);
				}else if(NumberOfWorkers == 3)
				{
					WhereToPlace = new BlockPos(startX - 23, startY, startZ + 54);
					//WhereToPlace = new BlockPos(startX + 50, startY, startZ + 19);
				}
				
			}else if(Name.contains("Small House"))
			{
				//-970 5 -277 -> -960 8 -269
				//-970 5 -277 -> -959 8 -259
				//-970 5 -277 -> -959 17 -267
				//-970 5 -277 -> -962 12 -263
				
				
				if(NumberOfWorkers == 0)
				{
					WhereToPlace = new BlockPos(startX - 8, startY + 4, startZ + 10);
				//	WhereToPlace = new BlockPos(startX + 10, startY + 4, startZ + 8);
				}else if(NumberOfWorkers == 1)
				{
					WhereToPlace = new BlockPos(startX - 18, startY + 4, startZ + 11);
				//	WhereToPlace = new BlockPos(startX + 11, startY + 4, startZ + 18);
				}else if(NumberOfWorkers == 2)
				{
					WhereToPlace = new BlockPos(startX - 10, startY + 13, startZ + 11);
				//	WhereToPlace = new BlockPos(startX + 11, startY + 13, startZ + 10);
				}else if(NumberOfWorkers == 3)
				{
					WhereToPlace = new BlockPos(startX - 14, startY + 8, startZ + 8);
				//	WhereToPlace = new BlockPos(startX + 8, startY + 8, startZ + 14);
				}
				
				
			}else if(Name.contains("Shopkeeper Guild"))
			{
				//810 6 -454 - > -800 9 -417
				//810 6 -454 - > -796 9 -447
				//810 6 -454 - > -796 9 -443
				
				
			}else if(Name.contains("Wheat Farmer Guild"))
			{
				//900 7 -1430 - > 905 8 -1426
				//900 7 -1430  - > 905 8 -1416
				//900 7 -1430  - > 905 8 -1411
				//900 7 -1430  - > 910 8 -1411
				
				
				if(NumberOfWorkers == 0)
				{
					WhereToPlace = new BlockPos(startX - 8, startY, startZ + 5);
				//	WhereToPlace = new BlockPos(startX + 5, startY, startZ + 4);
				}else if(NumberOfWorkers == 1)
				{
					WhereToPlace = new BlockPos(startX - 18, startY, startZ + 5);
				//	WhereToPlace = new BlockPos(startX + 5, startY, startZ + 14);
				}else if(NumberOfWorkers == 2)
				{
					WhereToPlace = new BlockPos(startX - 23, startY, startZ + 5);
					//WhereToPlace = new BlockPos(startX + 10, startY, startZ + 19);
				}else if(NumberOfWorkers == 3)
				{
					WhereToPlace = new BlockPos(startX - 23, startY, startZ + 10);
					//WhereToPlace = new BlockPos(startX + 10, startY, startZ + 19);
				}else if(NumberOfWorkers == 4)
				{
					WhereToPlace = new BlockPos(startX - 18, startY, startZ + 10);
				//	WhereToPlace = new BlockPos(startX + 10, startY, startZ + 14);
				}
				
			}else if(Name.contains("Carpenter"))
			{
				//-1100 5 1527 -> -1077 8 1567
				//-1100 5 1527 -> - 1077 15 1568
				//-1100 5 1527 -> -1080 15 1551
				//-1100 5 1527 -> -1080 8 1551
				if(NumberOfWorkers == 0)
				{
					WhereToPlace = new BlockPos(startX - 40, startY + 3, startZ + 23);
				//	WhereToPlace = new BlockPos(startX + 23, startY + 3, startZ + 40);
				}else if(NumberOfWorkers == 1)
				{
					WhereToPlace = new BlockPos(startX - 41, startY + 10, startZ + 23);
					//WhereToPlace = new BlockPos(startX + 23, startY + 10, startZ + 41);
				}else if(NumberOfWorkers == 2)
				{
					WhereToPlace = new BlockPos(startX - 24, startY + 10, startZ + 20);
				//	WhereToPlace = new BlockPos(startX + 20, startY + 10, startZ + 24);
				}else if(NumberOfWorkers == 3)
				{
					WhereToPlace = new BlockPos(startX - 24, startY + 3, startZ + 20);
				//	WhereToPlace = new BlockPos(startX + 20, startY + 3, startZ + 24);
				}
				
			}else if(Name.contains("Blacksmith"))
			{
				// -1020 5 1531 -> -1008 5 1554
				// -1020 5 1531 - > -999 5 1554
				// -1020 5 1531 - > -1006 11 1556
				// -1020 5 1531 - > -993 11 1554
				if(NumberOfWorkers == 0)
				{
					WhereToPlace = new BlockPos(startX - 23, startY, startZ + 12);
				//	WhereToPlace = new BlockPos(startX + 12, startY, startZ + 23);
				}else if(NumberOfWorkers == 1)
				{
					WhereToPlace = new BlockPos(startX - 23, startY, startZ + 21);
				//	WhereToPlace = new BlockPos(startX + 21, startY, startZ + 23);
				}else if(NumberOfWorkers == 2)
				{
					WhereToPlace = new BlockPos(startX - 25, startY, startZ + 14);
					//WhereToPlace = new BlockPos(startX + 14, startY, startZ + 25);
				}else if(NumberOfWorkers == 3)
				{
					WhereToPlace = new BlockPos(startX - 23, startY, startZ + 27);
				//	WhereToPlace = new BlockPos(startX + 27, startY, startZ + 23);
				}
				
			}else if(Name.contains("Storage Hut"))
			{
				//-970 5 1491 - > -944 7 1524
				if(NumberOfWorkers == 0)
				{
					WhereToPlace = new BlockPos(startX - 33, startY + 2, startZ + 26);
				//	WhereToPlace = new BlockPos(startX + 26, startY + 2, startZ + 33);
				}
			}else if(Name.contains("Messanger Guild"))
			{
				//-915 5 -308 -> -898 5 -298
				//-915 5 -308 -> -892 10 -295
				//-915 5 -308 -> =904 10 -303
				//-915 5 -308 ->
				
				
				if(NumberOfWorkers == 0)
				{
					WhereToPlace = new BlockPos(startX - 10, startY, startZ + 17);
				//	WhereToPlace = new BlockPos(startX + 17, startY, startZ + 10);
				}else if(NumberOfWorkers == 1)
				{
					WhereToPlace = new BlockPos(startX - 13, startY + 5, startZ + 23);
				//	WhereToPlace = new BlockPos(startX + 23, startY + 5, startZ + 13);
				}else if(NumberOfWorkers == 2)
				{
					WhereToPlace = new BlockPos(startX - 5, startY + 5, startZ + 11);
				//	WhereToPlace = new BlockPos(startX + 11, startY + 5, startZ + 5);
				}
				
			}else if(Name.contains("Exotic Farmers guild"))
			{
				//-895 5 -385 - > -894 5 -384
				//-840 5 399 - > -839 5 -389
				//-840 5 399 - > - 839 5 - 380
				//-840 5 399 - > -810 6 -398
				//-840 5 399 - >
				//-840 5 399 - >
				
				if(NumberOfWorkers == 0)
				{
					WhereToPlace = new BlockPos(startX - 9, startY, startZ + 1);
				//	WhereToPlace = new BlockPos(startX + 1, startY, startZ + 1);
				}else if(NumberOfWorkers == 1)
				{
					WhereToPlace = new BlockPos(startX - 18, startY, startZ + 1);
				//	WhereToPlace = new BlockPos(startX + 1, startY, startZ + 10);
				}else if(NumberOfWorkers == 2)
				{
					WhereToPlace = new BlockPos(startX - 27, startY, startZ + 1);
				//	WhereToPlace = new BlockPos(startX + 1, startY, startZ + 19);
				}else if(NumberOfWorkers == 3)
				{
					WhereToPlace = new BlockPos(startX - 9, startY + 1, startZ + 30);
				//	WhereToPlace = new BlockPos(startX + 30, startY + 1, startZ + 1);
				}else if(NumberOfWorkers == 4)
				{
					WhereToPlace = new BlockPos(startX - 18, startY + 1, startZ + 30);
				//	WhereToPlace = new BlockPos(startX + 30, startY + 1, startZ + 10);
				}else if(NumberOfWorkers == 5)
				{
					WhereToPlace = new BlockPos(startX - 27, startY + 1, startZ + 30);
				//	WhereToPlace = new BlockPos(startX + 30, startY + 1, startZ + 19);
				}
			}else if(Name.contains("Builders Guild"))
			{
				//-780 5 -396 -> -767 5 -379
				//-780 5 -396 -> -767 5 -369
				//-780 5 -396  - > -756 5 - 376
				if(NumberOfWorkers == 0)
				{
					WhereToPlace = new BlockPos(startX - 17, startY, startZ + 13);
				//	WhereToPlace = new BlockPos(startX + 13, startY, startZ + 17);
				}else if(NumberOfWorkers == 1)
				{
					WhereToPlace = new BlockPos(startX - 27, startY, startZ + 1);
				//	WhereToPlace = new BlockPos(startX + 1, startY, startZ + 27);
				}else if(NumberOfWorkers == 2)
				{
					WhereToPlace = new BlockPos(startX - 20, startY, startZ + 24);
				//	WhereToPlace = new BlockPos(startX + 24, startY, startZ + 20);
				}
			}
		
	}
		
		return WhereToPlace;
					
	}
	
	@Override
    public void invalidate() {
        if (chunkTicket != null) {
            ForgeChunkManager.releaseTicket(chunkTicket);
        }

        super.invalidate();
      
	}
	
	@Override
    public void onChunkUnload() {
        	this.invalidate();
	}
	
	public void forceChunkLoading(Ticket ticket) {
        if (chunkTicket == null) {
            chunkTicket = ticket;
        }

        Set<ChunkCoordIntPair> chunks = Sets.newHashSet();
        ChunkCoordIntPair quarryChunk = new ChunkCoordIntPair(pos.getX() >> 4, pos.getZ() >> 4);
        chunks.add(quarryChunk);
        ForgeChunkManager.forceChunk(ticket, quarryChunk);
        int i = 0;
       
        for (int chunkX = ticket.getModData().getInteger("StartX") >> 4; chunkX <= (ticket.getModData().getInteger("StartX") + 102) >> 4; chunkX++) {
            for (int chunkZ = ticket.getModData().getInteger("StartZ") >> 4; chunkZ <= (ticket.getModData().getInteger("StartZ") + 101) >> 4; chunkZ++) {
                ChunkCoordIntPair chunk = new ChunkCoordIntPair(chunkX, chunkZ);
                ForgeChunkManager.forceChunk(ticket, chunk);
              
                chunks.add(chunk);
            }
            
        }

      
}

	@Override
	public void update() {
	
	//	System.out.println("" + this.structure.getName());
	//	System.out.println("" + this.getPos());
	
		
		
	
		
			if(this.chunkTicket == null)
			{
				if(this.structure.getName() != null)
				{
					if(this.structure.getName().contains("OutpostOne"))
					{
						
					 	chunkTicket = ForgeChunkManager.requestTicket(KnightsOfTheRoundTable.instance, worldObj, Type.NORMAL);
					 	chunkTicket.setChunkListDepth(140);
					}
				}
			}
			
			if(this.chunkTicket != null)
			{
				 chunkTicket.getModData().setInteger("quarryX", pos.getX());
		         chunkTicket.getModData().setInteger("quarryY", pos.getY());
		         chunkTicket.getModData().setInteger("quarryZ", pos.getZ());
		         
		         chunkTicket.getModData().setInteger("StartX", pos.getX() - 39);
		         chunkTicket.getModData().setInteger("StartY", pos.getY() - 40);
		         chunkTicket.getModData().setInteger("StartZ", pos.getZ() - 40);
		         
				 ForgeChunkManager.forceChunk(chunkTicket, new ChunkCoordIntPair(pos.getX() >> 4, pos.getZ() >> 4));
				
			}
	
	//Eventually make it so that it will only place blocks first then things with meta after
	//	System.out.println("" +Blocks.trapdoor.getStateFromMeta(15));
		//Bottom closed
		// 0 = north
		// 1 = south
		// 2 = west
		// 3 = east
		//bottom open
		// 4 = north
		// 5 = south
		// 6 = west
		// 7 = east
		//Top closed
		// 8 = north
		// 9 = south
		// 10 = west
		// 11 = east	
		//Top open
		// 12 = north
		// 13 = south
		// 14 = west
		// 15 = east
	}

	
	
}



