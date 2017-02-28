package com.waabbuffet.kotrt.entities.Kingdom;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityCathedral extends EntityTameable{

	

	public int StoredXP; 
	
	
	public EntityCathedral(World worldIn) {
		super(worldIn);
		
		this.setAlwaysRenderNameTag(true);
		this.setSize(1.0F, 1.7F);
	}
	
	
	
	
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompund) {
		
		tagCompund.setInteger("StoredXP", this.StoredXP);
		
		super.writeToNBT(tagCompund);
	}
	
	public int getStoredXP() {
		return StoredXP;
	}
	
	public void increaseStoredXP(int Change)
	{
		this.StoredXP += Change;
	}
	public void decreaseStoredXP(int Change)
	{
		this.StoredXP -= Change;
	}
	
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompund) {
		
		this.StoredXP = tagCompund.getInteger("StoredXP");
				
		
		
		
		super.readFromNBT(tagCompund);
	}
	

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
	
		return null;
	}

}
