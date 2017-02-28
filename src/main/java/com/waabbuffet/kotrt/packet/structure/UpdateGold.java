package com.waabbuffet.kotrt.packet.structure;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.waabbuffet.kotrt.util.PlayerData;

public class UpdateGold implements IMessage, IMessageHandler<UpdateGold, IMessage> {

	int TierLevel;
	
	public UpdateGold(){ }
	
	public UpdateGold(int tier){
		
		this.TierLevel = tier;
		
	}
	
	
	


	@Override
    public IMessage onMessage(final UpdateGold message, MessageContext ctx) {
        IThreadListener mainThread = Minecraft.getMinecraft(); // or Minecraft.getMinecraft() on the client
        mainThread.addScheduledTask(new Runnable() {
            @Override
            public void run() {
            	
            	EntityPlayer p = Minecraft.getMinecraft().thePlayer;
            	PlayerData.get(p).setMana(message.TierLevel);
       
     
            }
        });
        return null; // no response in this case
    

	}
	
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.TierLevel = buf.readInt();
		
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.TierLevel);
		

		
	}

}