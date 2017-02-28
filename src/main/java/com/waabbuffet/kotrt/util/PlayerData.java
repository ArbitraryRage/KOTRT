package com.waabbuffet.kotrt.util;

import com.waabbuffet.kotrt.packet.PacketHandler;
import com.waabbuffet.kotrt.packet.structure.KingdomData;
import com.waabbuffet.kotrt.packet.structure.StructureConstruct;
import com.waabbuffet.kotrt.packet.structure.UpdateGold;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class PlayerData  implements IExtendedEntityProperties {

    private static final String identifier = "kotrt";
    private final static int Gold_Watcher = 20;

    // PROPERTIES =============================================================
    private int TierLevel, WarriorTroops, HunterTroops, ShadowWarriorTroops, MageTroops;
 
  
    
    private static EntityPlayer player;

    // CONSTRUCTOR, GETTER, REGISTER ==========================================


    public PlayerData(EntityPlayer player) {
        this.player = player;
    }
    
    public void setMana(int tierlevel) {
        this.TierLevel = tierlevel;     
    }
    
    public void increaseWarriorTroops(int Change)
    {
    	this.WarriorTroops += Change;
    }
    public void increaseShadowWarriorTroops(int Change)
    {
    	this.ShadowWarriorTroops += Change;
    }
    public void increaseMageTroops(int Change)
    {
    	this.MageTroops += Change;
    }
    public void increaseHunterTroops(int Change)
    {
    	this.HunterTroops += Change;
    }
    
    public void decreaseWarriorTroops(int Change)
    {
    	this.WarriorTroops -= Change;
    }
    public void decreaseShadowWarriorTroops(int Change)
    {
    	this.ShadowWarriorTroops -= Change;
    }
    public void decreaseMageTroops(int Change)
    {
    	this.MageTroops -= Change;
    }
    public void decreaseHunterTroops(int Change)
    {
    	this.HunterTroops -= Change;
    }
    
    public int getWarriorTroops() {
		return WarriorTroops;
	}

	public void setWarriorTroops(int warriorTroops) {
		WarriorTroops = warriorTroops;
	}

	public int getHunterTroops() {
		return HunterTroops;
	}

	public void setHunterTroops(int hunterTroops) {
		HunterTroops = hunterTroops;
	}

	public int getShadowWarriorTroops() {
		return ShadowWarriorTroops;
	}

	public void setShadowWarriorTroops(int shadowWarriorTroops) {
		ShadowWarriorTroops = shadowWarriorTroops;
	}

	public int getMageTroops() {
		return MageTroops;
	}

	public void setMageTroops(int mageTroops) {
		MageTroops = mageTroops;
	}

	public void increaseMana(int GoldAmount)
    {
    	int Change = this.getMana() + GoldAmount;
    	this.setMana(Change);
    	
    	
    }

    public int getMana() {
        return this.TierLevel;
    }
   
    public void syncGold()
    {
    	
    	 if(this.isServerSide())
    		 PacketHandler.INSTANCE.sendTo(new UpdateGold(this.getMana()), (EntityPlayerMP) this.player);
    }
    
    
    public void syncMana() {
        if (this.isServerSide())
        	PacketHandler.INSTANCE.sendTo(new KingdomData(this.getMana(), this.getWarriorTroops(), this.getHunterTroops(), this.getShadowWarriorTroops(), this.getMageTroops()), (EntityPlayerMP) this.player);
    }
    
    public static PlayerData get(EntityPlayer player) {
        return (PlayerData) player.getExtendedProperties(identifier);
    }

    public static void register(EntityPlayer player) {
        player.registerExtendedProperties(identifier, new PlayerData(player));
    }

    public boolean isServerSide() {
        return this.player instanceof EntityPlayerMP;
    }
    
    public void saveReviveRelevantNBTData(NBTTagCompound nbt, boolean wasDeath) {
        if (wasDeath)
            this.saveNBTData(nbt);
    }

    // LOAD, SAVE =============================================================

    @Override
    public void saveNBTData(NBTTagCompound nbt) {
        nbt.setInteger("tierlevel", this.getMana());
        
        nbt.setInteger("WarriorTroops", this.getWarriorTroops());
        nbt.setInteger("HunterTroops", this.getHunterTroops());
        nbt.setInteger("ShadowWarriorTroops", this.getShadowWarriorTroops());
        nbt.setInteger("MageTroops", this.getMageTroops());
        
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt) {
        if (nbt.hasKey("tierlevel", 3)){
            this.setMana(nbt.getInteger("tierlevel"));
        }
        
        if (nbt.hasKey("WarriorTroops", 3)){
            this.setWarriorTroops(nbt.getInteger("WarriorTroops"));
        }
        
        if (nbt.hasKey("HunterTroops", 3)){
            this.setHunterTroops(nbt.getInteger("HunterTroops"));
        }
        
        if (nbt.hasKey("ShadowWarriorTroops", 3)){
            this.setShadowWarriorTroops(nbt.getInteger("ShadowWarriorTroops"));
        }
        
        if (nbt.hasKey("MageTroops", 3)){
            this.setMageTroops(nbt.getInteger("MageTroops"));
        } 
    }

    @Override
    public void init(Entity entity, World world) {
    
    }
    
    
    
}