package com.waabbuffet.kotrt.entities.Outpost;

import com.waabbuffet.kotrt.KnightsOfTheRoundTable;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class EntitySellShopKeeper extends EntityTameable implements IInventory{

	public EntitySellShopKeeper(World worldIn) {
		super(worldIn);
		this.setSize(1.0F, 1.7F);
		// TODO Auto-generated constructor stub
	}

	
	
	
	
   

    @Override
    public IChatComponent getDisplayName() {
        return this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName());
    }

	@Override
	public int getSizeInventory() {
	
		return 1;
	}
	

	@Override
	public ItemStack getStackInSlot(int index) {
	    if (index < 0 || index >= this.getSizeInventory())
	        return null;
	    return this.getInventory()[index];
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

	    this.getInventory()[index] = stack;
	    this.markDirty();
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 64;
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
	public void markDirty() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
