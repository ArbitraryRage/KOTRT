package com.waabbuffet.kotrt.util;

import net.minecraft.item.ItemStack;

public class ShopKeeperItemFormat {

	
	private ItemStack Item;
	private int Cost;
	
	private int Inflation;

	public ShopKeeperItemFormat(ItemStack item, int cost) {
		super();
		Item = item;
		Cost = cost;
		
	}

	public ItemStack getItem() {
		return Item;
	}

	public void setItem(ItemStack item) {
		Item = item;
	}

	public int getCost() {
		return Cost;
	}

	public void setCost(int cost) {
		Cost = cost;
	}

	public int getInflation() {
		return Inflation;
	}

	public void setInflation(int inflation) {
		Inflation = inflation;
	}
	
	
}
