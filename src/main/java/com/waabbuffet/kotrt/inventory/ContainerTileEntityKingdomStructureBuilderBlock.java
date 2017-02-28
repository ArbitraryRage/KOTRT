package com.waabbuffet.kotrt.inventory;

import java.util.List;

import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBuilderBlock;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerTileEntityKingdomStructureBuilderBlock extends Container {


	 private TileEntityKingdomStructureBuilderBlock KS;



	 public ContainerTileEntityKingdomStructureBuilderBlock(IInventory playerInv, TileEntityKingdomStructureBuilderBlock ks) {
	        this.KS = ks;
	        
	     // Tile Entity, Slot 0-8, Slot IDs 0-8
	        for (int y = 0; y < 3; ++y) {
	            for (int x = 0; x < 5; ++x) {
	                this.addSlotToContainer(new Slot(KS, x + y * 5,  x * 17 - 32, 55 + y * 18));
	            }
	        }

	        // Player Inventory, Slot 9-35, Slot IDs 9-35
	        for (int y = 0; y < 3; ++y) {
	            for (int x = 0; x < 9; ++x) {
	                this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9,   x * 17 - 66, 128 + y * 18));
	            }
	        }

	        // Player Inventory, Slot 0-8, Slot IDs 36-44
	        for (int x = 0; x < 9; ++x) {
	            this.addSlotToContainer(new Slot(playerInv, x, - 66 + x * 17, 185));
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

	        if (fromSlot < 15) {
	            // From TE Inventory to Player Inventory
	            if (!this.mergeItemStack(current, 15, 45, true))
	                return null;
	        } else {
	            // From Player Inventory to TE Inventory
	            if (!this.mergeItemStack(current, 0, 15, false))
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
