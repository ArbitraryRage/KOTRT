package com.waabbuffet.kotrt.entities.Kingdom;

import com.waabbuffet.kotrt.packet.PacketHandler;
import com.waabbuffet.kotrt.packet.structure.UpdateGold;
import com.waabbuffet.kotrt.util.PlayerData;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityGold extends Entity{

	int Amount;
	public int xpOrbAge;
	public int PickupCD;
	
	public EntityGold(World worldIn) {
		// TODO Auto-generated constructor stub
		super(worldIn);
	}
	
	
	public EntityGold(World worldIn, int Amount) {
		super(worldIn);

		this.Amount = Amount;
		this.setSize(0.25F, 0.25F);
		
	
	}

	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompund) {
		// TODO Auto-generated method stub
		
		setAmount(tagCompund.getInteger("Amount"));
		setXpOrbAge(tagCompund.getInteger("Age"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound) {
		// TODO Auto-generated method stub
		
		tagCompound.setInteger("Amount", this.getAmount());
		tagCompound.setInteger("Age", this.getXpOrbAge());
	}
	
	public void setAmount(int amount) {
		Amount = amount;
	}
	
	public int getAmount() {
		return Amount;
	}
	
	public void setXpOrbAge(int xpOrbAge) {
		this.xpOrbAge = xpOrbAge;
	}
	
	public int getXpOrbAge() {
		return xpOrbAge;
	}
	
	@Override
	public void onCollideWithPlayer(EntityPlayer entityIn) {
		
		
		
		
		
		if(!this.worldObj.isRemote)
		{
			if(this.getXpOrbAge() >= 10)
			{
				
				PlayerData.get(entityIn).increaseMana(1);
				PacketHandler.INSTANCE.sendTo(new UpdateGold(PlayerData.get(entityIn).getMana()), (EntityPlayerMP) entityIn);
				
				
				if (!this.isSilent())
	            {
	                   this.worldObj.playSoundAtEntity(entityIn, "random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
	            }
				this.setDead();
			}
		}	
		
		
		super.onCollideWithPlayer(entityIn);
	}
	
	@Override
	public void onUpdate() {
		
		this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        
        if(!this.onGround){
      
        	this.motionY -= 0.049999999329447746D;
        }else{
        	this.motionY = 0;
        	this.motionX = 0; 
        	this.motionZ = 0;
        }
        
        
        
      
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		
		this.xpOrbAge++;
		
		if(this.xpOrbAge > 6000)
		{
			this.setDead();
		}
		   
		
		super.onUpdate();
	}

}
