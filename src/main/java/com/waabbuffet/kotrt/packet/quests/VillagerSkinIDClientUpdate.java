package com.waabbuffet.kotrt.packet.quests;



import com.waabbuffet.kotrt.entities.Outpost.EntityOutpostBase;
import com.waabbuffet.kotrt.util.PlayerData;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class VillagerSkinIDClientUpdate implements IMessage, IMessageHandler<VillagerSkinIDClientUpdate, IMessage> {

	int TierLevel, ID;
	
	public VillagerSkinIDClientUpdate(){ }
	
	public VillagerSkinIDClientUpdate(int tier, int ID){
		
		this.TierLevel = tier;
		this.ID = ID;
	}
	
	@Override
	public IMessage onMessage(VillagerSkinIDClientUpdate message, MessageContext ctx) {
		
		World world = Minecraft.getMinecraft().theWorld;
		
		for(int i = 0; i < world.getLoadedEntityList().size(); i ++)
		{
			Entity b = world.getLoadedEntityList().get(i);
			if(b instanceof EntityOutpostBase)
			{
				if(b.getEntityId() == message.ID)
				{
					((EntityOutpostBase) b).setVillagerSkinID(message.TierLevel);
					break;
				}
			}
		}
		
		
			
		
		
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.TierLevel = buf.readInt();
		this.ID = buf.readInt();
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.TierLevel);
		buf.writeInt(ID);
	}

}
