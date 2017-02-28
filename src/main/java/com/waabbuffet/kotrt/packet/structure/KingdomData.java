package com.waabbuffet.kotrt.packet.structure;

import com.waabbuffet.kotrt.util.PlayerData;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class KingdomData implements IMessage, IMessageHandler<KingdomData, IMessage> {

	int TierLevel, WarriorTroops, HunterTroops, ShadowWarriorTroops, MageTroops;
	
	public KingdomData(){ }
	
	public KingdomData(int tier, int warriorTroops, int hunterTroops, int shadowWarriorTroops, int mageTroops){
		
		this.TierLevel = tier;
		
		this.WarriorTroops = warriorTroops;
		this.HunterTroops = hunterTroops;
		this.ShadowWarriorTroops = shadowWarriorTroops;
		this.MageTroops = mageTroops;
	}
	
	
	


	@Override
    public IMessage onMessage(final KingdomData message, MessageContext ctx) {
        IThreadListener mainThread = Minecraft.getMinecraft(); 
        mainThread.addScheduledTask(new Runnable() {
            @Override
            public void run() {
            	
          
            	
            	EntityPlayer p = Minecraft.getMinecraft().thePlayer;
            	
            	PlayerData.get(p).setMana(message.TierLevel);
        		PlayerData.get(p).setWarriorTroops(message.WarriorTroops);
        		PlayerData.get(p).setHunterTroops(message.HunterTroops);
        		PlayerData.get(p).setShadowWarriorTroops(message.ShadowWarriorTroops);
        		PlayerData.get(p).setMageTroops(message.MageTroops);
        	
            }
        });
        return null; // no response in this case
    

	}
	
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.TierLevel = buf.readInt();
		
		this.WarriorTroops = buf.readInt();
		this.HunterTroops = buf.readInt();
		this.ShadowWarriorTroops = buf.readInt();
		this.MageTroops = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.TierLevel);
		
		buf.writeInt(WarriorTroops);
		buf.writeInt(HunterTroops);
		buf.writeInt(ShadowWarriorTroops);
		buf.writeInt(MageTroops);
		
	}

}
