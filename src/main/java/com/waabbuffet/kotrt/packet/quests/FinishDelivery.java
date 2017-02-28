package com.waabbuffet.kotrt.packet.quests;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.waabbuffet.kotrt.entities.Outpost.EntityOutpostBase;
import com.waabbuffet.kotrt.packet.structure.UpdateGold;
import com.waabbuffet.kotrt.util.PlayerData;
import com.waabbuffet.kotrt.util.QuestFormat;
import com.waabbuffet.kotrt.util.RewardInfo;

public class FinishDelivery implements IMessage, IMessageHandler<FinishDelivery, IMessage> {

	

	public int QuestID, EntityID;
	
	
	//save/load structure to and from file
	//Generate schematic to world
	
	public FinishDelivery(){ }
	
	//Expecting Orignal EntityId, and Orignal Quest ID
	public FinishDelivery(int EntityID, int questID){
		
		this.EntityID = EntityID;
		this.QuestID = questID;
	}
	
	
	
	
	@Override
	public IMessage onMessage(FinishDelivery message, MessageContext ctx) {
		
		EntityPlayer player = ctx.getServerHandler().playerEntity;
		EntityOutpostBase b = (EntityOutpostBase) player.worldObj.getEntityByID(message.EntityID);
		
		QuestFormat b1 = b.getQuest(message.QuestID);
		RewardInfo b2 = null;
		
			//Delivery
		for(int i =0; i < player.inventory.getSizeInventory(); i ++)
		{
			if(player.inventory.getStackInSlot(i) != null)
			{
				if(player.inventory.getStackInSlot(i).isItemEqual(b1.getHarvestItem()))
				{
					if(player.inventory.getStackInSlot(i).stackSize >= b1.getHarvestItem().stackSize)
					{
						player.inventory.decrStackSize(i, b1.getHarvestItem().stackSize);
						b2 =this.giveReward(b1.getRarityID());
						b.ResetQuest();
					//47 64 326
						
						if(b2 != null)
						{
							if(b2.getGiveGold() != 0)
							{
							
								PlayerData.get(player).increaseMana(b2.getGiveGold());
							}
							
							if(b2.getGiveItemStack() != null)
							{
								
								player.inventory.addItemStackToInventory(b2.getGiveItemStack());
							}
						}
						return new UpdateGold(PlayerData.get(player).getMana());
					}
				}else{
				
				}
			}
		}
			
		
		
		return new UpdateGold(PlayerData.get(player).getMana());
	}
	

	public RewardInfo giveReward(int rarity2) {
		
		RewardInfo b = null;
	
		
		
		if(rarity2 == 0)
		{
			//common
			b = QuestFormat.CommonList();
		}else if(rarity2 == 1)
		{
			//uncommon
			b = QuestFormat.GreenList();
		}else if(rarity2 == 2)
		{
			//rare
			b = QuestFormat.BlueList();
		}else if(rarity2 == 3)
		{
			//epic
			b = QuestFormat.PurpleList();
		}else if(rarity2 == 4)
		{
			//legendary
			b = QuestFormat.OrangeList();
		}
		
		
		return b;
	}


	@Override
	public void fromBytes(ByteBuf buf) {
	
	
		
		this.QuestID = buf.readInt();
		this.EntityID = buf.readInt();
		//file name, boolean to say build into world or just load structure, and directory
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
	
		buf.writeInt(this.QuestID);
		buf.writeInt(EntityID);
	}
	

}