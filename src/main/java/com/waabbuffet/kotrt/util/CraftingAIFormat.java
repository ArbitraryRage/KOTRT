package com.waabbuffet.kotrt.util;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CraftingAIFormat {

		ItemStack Produced;
		int CraftingTime;
		
		ItemStack[] Cost = new ItemStack[5];
		int[] HowMany = new int[5];
	
		public CraftingAIFormat(ItemStack[] cost, int craftingTime, int[] howMany, ItemStack produced) {
			
			this.setItem(cost, craftingTime, howMany, produced);
		}
		
		public void setItem(ItemStack[] cost, int craftingTime, int[] howMany, ItemStack produced)
		{
			this.Cost= cost;
			this.HowMany = howMany;
			this.Produced = produced;
			this.CraftingTime = craftingTime;
			
		}
		
		public int[] getHowMany() {
			return HowMany;
		}
		
		public ItemStack getProduced() {
			return Produced;
		}
		
		public int getCraftingTime() {
			return CraftingTime;
		}
		
		public ItemStack[] getCost() {
			return Cost;
		}
		
}
