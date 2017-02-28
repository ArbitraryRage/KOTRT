package com.waabbuffet.kotrt.util;

import net.minecraft.item.ItemStack;

public class RewardInfo {

	
	private int GiveGold;
	private ItemStack GiveItemStack;
	private int NumberOfStacks;
	
	public RewardInfo(int giveGold, ItemStack giveItemStack, int numberOfStacks) {
	
		GiveGold = giveGold;
		GiveItemStack = giveItemStack;
		this.NumberOfStacks = numberOfStacks;
	}

	public int getGiveGold() {
		return GiveGold;
	}

	public void setGiveGold(int giveGold) {
		GiveGold = giveGold;
	}

	public ItemStack getGiveItemStack() {
		return GiveItemStack;
	}

	public void setGiveItemStack(ItemStack giveItemStack) {
		GiveItemStack = giveItemStack;
	}
	
	
	public int getNumberOfStacks() {
		return NumberOfStacks;
	}
}
