package com.waabbuffet.kotrt.packet.structure;

import io.netty.buffer.ByteBuf;

import java.io.File;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.waabbuffet.kotrt.blocks.KotrtBlocksHandler;
import com.waabbuffet.kotrt.packet.PacketHandler;
import com.waabbuffet.kotrt.proxy.ClientProxy;
import com.waabbuffet.kotrt.schematic.Structure;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBlock;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBuilderBlock;
import com.waabbuffet.kotrt.util.PlayerData;

public class GiveItemToPlayer  implements IMessage, IMessageHandler<GiveItemToPlayer, IMessage> {

	

	public int ItemID, HowMany, Cost, Meta;
	boolean SellOrGive;
	
	//save/load structure to and from file
	//Generate schematic to world
	
	public GiveItemToPlayer(){ }
	
	public GiveItemToPlayer(int itemID, int HowMany, int Cost, int meta){
		
		this.ItemID = itemID;
		this.HowMany = HowMany;
		this.Cost = Cost;
		this.Meta = meta;
		this.SellOrGive = true;
	
	}
	
	public GiveItemToPlayer(int itemID, int HowMany, int Cost, int meta, boolean SellOrGive){
		
		this.ItemID = itemID;
		this.HowMany = HowMany;
		this.Cost = Cost;
		this.Meta = meta;
		this.SellOrGive = false;
	}
	
	
	
	@Override
	public IMessage onMessage(GiveItemToPlayer message, MessageContext ctx) {
		
		EntityPlayer player = ctx.getServerHandler().playerEntity;
		
	if(message.SellOrGive)		
	{
			if(PlayerData.get(player).getMana() >= (message.Cost * message.HowMany))
			{
				ItemStack b = new ItemStack(Item.getItemById(message.ItemID), message.HowMany, message.Meta);
				if(player.inventory.addItemStackToInventory(b))
				{
					PlayerData.get(player).increaseMana(-(message.Cost * message.HowMany));
					
				}		
				
			}
	}else{
		
		if(player.inventory.hasItemStack(new ItemStack(Item.getItemById(message.ItemID))))
		{
			for(int i =0; i < player.inventory.getSizeInventory(); i ++)
			{
				if(player.inventory.getStackInSlot(i) != null)
				{
					if(player.inventory.getStackInSlot(i).isItemEqual(new ItemStack(Item.getItemById(message.ItemID))))
					{
						if(player.inventory.getStackInSlot(i).stackSize >= message.HowMany)
						{
							player.inventory.decrStackSize(i, message.HowMany);
							PlayerData.get(player).increaseMana(message.Cost * message.HowMany);
							return new UpdateGold(PlayerData.get(player).getMana());
						}
					}
				}
			}
		}else{
			return null;
		}
	}
		
		return new UpdateGold(PlayerData.get(player).getMana());
	}

	@Override
	public void fromBytes(ByteBuf buf) {
	
	
		this.ItemID = buf.readInt();
		this.HowMany = buf.readInt();
		this.Cost = buf.readInt();
		this.Meta = buf.readInt();
		this.SellOrGive = buf.readBoolean();
		
		//file name, boolean to say build into world or just load structure, and directory
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
	
		buf.writeInt(this.ItemID);
		buf.writeInt(this.HowMany);
		buf.writeInt(this.Cost);
		buf.writeInt(this.Meta);
		buf.writeBoolean(this.SellOrGive);
	}
	

}