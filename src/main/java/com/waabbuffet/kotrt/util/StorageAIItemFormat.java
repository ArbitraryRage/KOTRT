package com.waabbuffet.kotrt.util;

import net.minecraft.item.ItemStack;

public class StorageAIItemFormat {

	
	int ChestIndex;
	ItemStack Item;
	int HowMany;
	
	public StorageAIItemFormat(int chestIndex, ItemStack item, int howMany) {
		
		ChestIndex = chestIndex;
		Item = item;
		HowMany = howMany;
	}
	
	
	public int getChestIndex() {
		return ChestIndex;
	}
	
	public int getHowMany() {
		return HowMany;
	}
	
	public ItemStack getItem() {
		return Item;
	}
	
	public void setChestIndex(int chestIndex) {
		ChestIndex = chestIndex;
	}
	
	public void setHowMany(int howMany) {
		HowMany = howMany;
	}
	
	public void	ChangeHowMany(int howMany) {
		HowMany += howMany;
	}
	
	public void setItem(ItemStack item) {
		Item = item;
	}
	
	
	
}
