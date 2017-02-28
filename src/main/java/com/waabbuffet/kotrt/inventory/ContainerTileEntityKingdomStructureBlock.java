package com.waabbuffet.kotrt.inventory;

import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBlock;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerTileEntityKingdomStructureBlock extends Container {


	 private TileEntityKingdomStructureBlock KS;

	 public ContainerTileEntityKingdomStructureBlock(IInventory playerInv, TileEntityKingdomStructureBlock ks) {
	        this.KS = ks;
	        
	     // Tile Entity, Slot 0-8, Slot IDs 0-8
	        for (int y = 0; y < 3; ++y) {
	            for (int x = 0; x < 5; ++x) {
	                this.addSlotToContainer(new Slot(KS, x + y * 5, 87 + x * 17, 54 + y * 18));
	            }
	        }

	        // Player Inventory, Slot 9-35, Slot IDs 9-35
	        for (int y = 0; y < 3; ++y) {
	            for (int x = 0; x < 9; ++x) {
	                this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 55 + x * 18, 126 + y * 18));
	            }
	        }

	        // Player Inventory, Slot 0-8, Slot IDs 36-44
	        for (int x = 0; x < 9; ++x) {
	            this.addSlotToContainer(new Slot(playerInv, x, 55 + x * 17, 182));
	        }
	    }
	 
	
	 
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
	
		 return this.KS.isUseableByPlayer(playerIn);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
	    ItemStack previous = null;
	    Slot slot = (Slot) this.inventorySlots.get(fromSlot);

	    if (slot != null && slot.getHasStack()) {
	        ItemStack current = slot.getStack();
	        previous = current.copy();

	        if (fromSlot < 9) {
	            // From TE Inventory to Player Inventory
	            if (!this.mergeItemStack(current, 9, 45, true))
	                return null;
	        } else {
	            // From Player Inventory to TE Inventory
	            if (!this.mergeItemStack(current, 0, 9, false))
	                return null;
	        }

	        if (current.stackSize == 0)
	            slot.putStack((ItemStack) null);
	        else
	            slot.onSlotChanged();

	        if (current.stackSize == previous.stackSize)
	            return null;
	        slot.onPickupFromSlot(playerIn, current);
	    }
	    return previous;
	}

}
