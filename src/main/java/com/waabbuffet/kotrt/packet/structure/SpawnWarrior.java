package com.waabbuffet.kotrt.packet.structure;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.waabbuffet.kotrt.entities.Kingdom.barracks.EntityBarracksHunter;
import com.waabbuffet.kotrt.entities.Kingdom.barracks.EntityBarracksMage;
import com.waabbuffet.kotrt.entities.Kingdom.barracks.EntityBarracksWarrior;
import com.waabbuffet.kotrt.util.PlayerData;

public class SpawnWarrior implements IMessage, IMessageHandler<SpawnWarrior, IMessage> {

	int TierLevel, blockX, blockY, blockZ;
	boolean Spawn;
	
	public SpawnWarrior(){ }
	
	public SpawnWarrior(int tier, boolean Spawn, int blockX, int blockY, int blockZ){
		
		this.TierLevel = tier;
		this.Spawn = Spawn;
		this.blockX = blockX;
		this.blockY = blockY;
		this.blockZ = blockZ;
	}
	
	@Override
	public IMessage onMessage(SpawnWarrior message, MessageContext ctx) {
		
		
		EntityPlayer player =  ctx.getServerHandler().playerEntity;
		World world = ctx.getServerHandler().playerEntity.worldObj;
		
		
		if(message.Spawn)
		{
			if(message.TierLevel == 0) //warrior
			{
				if(PlayerData.get(player).getWarriorTroops() > 0)
				{
					EntityBarracksWarrior b = new EntityBarracksWarrior(ctx.getServerHandler().playerEntity.worldObj);
					
					b.setCustomNameTag("Warrior");
				
			
					b.setPosition(message.blockX, message.blockY, message.blockZ);
					b.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
					
				    b.setCurrentItemOrArmor(1, new ItemStack(Items.leather_boots));
				    
					
					
				    b.setOwnerId(ctx.getServerHandler().playerEntity.getUniqueID().toString());
				    b.setTamed(true);
				    
				    ctx.getServerHandler().playerEntity.worldObj.spawnEntityInWorld(b);
				 
				    
				    PlayerData.get(player).decreaseWarriorTroops(1);
				}
				    
				
			}else if(message.TierLevel == 1) //hunter
			{
				if(PlayerData.get(player).getHunterTroops() > 0)
				{
					EntityBarracksHunter b = new EntityBarracksHunter(ctx.getServerHandler().playerEntity.worldObj);
					
					b.setCustomNameTag("Hunter");
				
			
					b.setPosition(message.blockX, message.blockY, message.blockZ);
					b.setCurrentItemOrArmor(0, new ItemStack(Items.bow));
				    b.setCurrentItemOrArmor(1, new ItemStack(Items.leather_boots));
					
					
				    b.setOwnerId(ctx.getServerHandler().playerEntity.getUniqueID().toString());
				    b.setTamed(true);
				    
				    ctx.getServerHandler().playerEntity.worldObj.spawnEntityInWorld(b);
				 
				    
				    PlayerData.get(player).decreaseHunterTroops(1);
				}
				
			}else if(message.TierLevel == 2)// shadow warrior
			{
				
			}else if(message.TierLevel == 3)// mage
			{
				if(PlayerData.get(player).getMageTroops() > 0)
				{
					EntityBarracksMage b = new EntityBarracksMage(ctx.getServerHandler().playerEntity.worldObj);
					
					b.setCustomNameTag("Mage");
				
			
					b.setPosition(message.blockX, message.blockY, message.blockZ);
				
				    b.setCurrentItemOrArmor(1, new ItemStack(Items.leather_boots));
					
					
				    b.setOwnerId(ctx.getServerHandler().playerEntity.getUniqueID().toString());
				    b.setTamed(true);
				    
				    ctx.getServerHandler().playerEntity.worldObj.spawnEntityInWorld(b);
				 
				    
				    PlayerData.get(player).decreaseMageTroops(1);
				}
			}
			
		}else{
		
			
			if(message.TierLevel == 0)
			{
				for(int i =0; i < world.loadedEntityList.size(); i++)
				{
					if(world.loadedEntityList.get(i) instanceof EntityBarracksWarrior)
					{
						PlayerData.get(player).increaseWarriorTroops(1);
						world.loadedEntityList.get(i).setDead();
						break;
					}
				}
			}else if (message.TierLevel == 1)
			{
				for(int i =0; i < world.loadedEntityList.size(); i++)
				{
					if(world.loadedEntityList.get(i) instanceof EntityBarracksHunter)
					{
						PlayerData.get(player).increaseHunterTroops(1);
						world.loadedEntityList.get(i).setDead();
						break;
					}
				}
			}else if (message.TierLevel == 3)
			{
				for(int i =0; i < world.loadedEntityList.size(); i++)
				{
					if(world.loadedEntityList.get(i) instanceof EntityBarracksMage)
					{
						PlayerData.get(player).increaseMageTroops(1);
						world.loadedEntityList.get(i).setDead();
						break;
					}
				}
			}
			
		}
		
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		
		this.TierLevel = buf.readInt();
		this.Spawn = buf.readBoolean();
		
		this.blockX = buf.readInt();
		this.blockY = buf.readInt();
		this.blockZ = buf.readInt();
				
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
		buf.writeInt(this.TierLevel);
		buf.writeBoolean(Spawn);
	
		buf.writeInt(blockX);
		buf.writeInt(blockY);
		buf.writeInt(blockZ);
		
	}

	
}
