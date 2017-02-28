package com.waabbuffet.kotrt.util;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;

public class QuestFormat {

	
	int QuestTypeID; //0 = gathering, 1 = killing,  2 = Deliver, 3 = talking, 4 = Commander
	int RarityID; // 0 = common, 1 = green, 2 = blue, 3 = purple, 4 = orange (will define rewards will have 4 lists of items that will be randomly chosen)
	
	Entity KillOrTalkEntity; //Kill/talk/deliver
	ItemStack HarvestItem; //gather/deliver
	
	static Random Rand = new Random();
	
	/*
	 * Auto Gens Gathering quest
	 */
	public QuestFormat(int rarityID, ItemStack harvestItem)
	{
		this.QuestTypeID = 0;
		this.RarityID = rarityID;
		this.HarvestItem = harvestItem;
	}
	
	
	
	public QuestFormat(int questTypeID, int rarityID, Entity killOrTalkEntity, ItemStack harvestItem) {
		
		QuestTypeID = questTypeID;
		RarityID = rarityID;
		KillOrTalkEntity = killOrTalkEntity;
		HarvestItem = harvestItem;
	}

	public int getRarityID() {
		return RarityID;
	}
	
	public int getQuestTypeID() {
		return QuestTypeID;
	}
	
	public Entity getKillOrTalkEntity() {
		return KillOrTalkEntity;
	}
	
	public ItemStack getHarvestItem() {
		return HarvestItem;
	}
	
	
	
	public static RewardInfo CommonList()
	{
		RewardInfo b = null;
		b = new RewardInfo(300, null, 1);
	
		return b;
	}
	
	public static RewardInfo GreenList()
	{
		RewardInfo 	b = new RewardInfo(450, null, 1);
		return b;
	}
	
	public static RewardInfo BlueList()
	{
		RewardInfo 	b = new RewardInfo(900, null, 1);
		return b;
	}
	
	public static RewardInfo PurpleList()
	{
		RewardInfo 	b = new RewardInfo(1250, null, 1);
		return b;
	}
	
	public static RewardInfo OrangeList()
	{
		RewardInfo 	b = new RewardInfo(2000, null, 1);
		return b;
	}
	
}
