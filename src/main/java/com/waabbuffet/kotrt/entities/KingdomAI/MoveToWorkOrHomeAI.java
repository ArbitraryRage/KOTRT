package com.waabbuffet.kotrt.entities.KingdomAI;

import com.waabbuffet.kotrt.entities.Kingdom.EntityFarmer;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MoveToWorkOrHomeAI extends EntityAIBase {


	EntityFarmer Farmer;
	World World;
	 //if false moves back to home
	
	public MoveToWorkOrHomeAI(EntityFarmer farmer, World world)
	{
		Farmer = farmer;
		World = world;
		
	}
	
	
	
	@Override
	public boolean shouldExecute() {
	
		//System.out.println("The dist" + this.Farmer.getDistance(Farmer.getWorkPlace().getX(), Farmer.getWorkPlace().getY(), Farmer.getWorkPlace().getZ()));
			
		if(Farmer.getWorkPlace() != null)
		{
			if(Farmer.getWorkPlace().distanceSq(Farmer.posX, Farmer.posY, Farmer.posZ)  > 100)
			{
				
				return true;
			}
		}
		
		return false;
	}

	
	@Override
	public boolean continueExecuting() 
	{
		
	if(this.shouldExecute())
	{
		this.startExecuting();
	}
		
		return true;
	}
	
	
	
	@Override
	public void startExecuting() 
	{
		
		
		
		if(!this.Farmer.getNavigator().tryMoveToXYZ(Farmer.getWorkPlace().getX(), Farmer.getWorkPlace().getY(), Farmer.getWorkPlace().getZ(), 1.0D)){ // moves to a max of 16 blocks yooo
			this.Farmer.teleportTo(Farmer.getWorkPlace().getX(), Farmer.getWorkPlace().getY() + 1, Farmer.getWorkPlace().getZ());
		}
		super.startExecuting();
	}
	

}
