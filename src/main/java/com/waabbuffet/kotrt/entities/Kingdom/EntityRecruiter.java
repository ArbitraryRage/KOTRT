package com.waabbuffet.kotrt.entities.Kingdom;

import java.util.Random;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityRecruiter extends EntityTameable {

	private int RecruitTime;
	private double ChanceToRecruit;
	private int GoldPutDown;
	private boolean Recruit;
	Random Rand = new Random();
	
	//chance of to recruit will be low... can give some gold to increase chance.... otherwise go to other kingdom and find people
	
	public EntityRecruiter(World worldIn) {
		super(worldIn);
		
		this.setSize(1.0F, 1.7F);
		this.tasks.addTask(4, new EntityAILookIdle(this));
		this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		
	}
	
	public int getRecruitTime() {
		return RecruitTime;
	}
	
	public double getChanceToRecruit() {
		return ChanceToRecruit;
	}
	
	public int getGoldPutDown() {
		return GoldPutDown;
	}

	public void setRecruitTime(int recruitTime) {
		RecruitTime = recruitTime;
	}
	
	public void setChanceToRecruit(double d) {
		ChanceToRecruit = d;
	}
	
	public void setGoldPutDown(int goldPutDown) {
		GoldPutDown = goldPutDown;
	}
	
	public void setRecruit(boolean recruit) {
		Recruit = recruit;
	}
	
	public boolean getRecruit() {
		
		return this.Recruit;
	}
	
	public void CalcGoldChange()
	{
		//every 100 gold = 0.1 chance increase
		//every 1000 ticks will check for chance
		this.ChanceToRecruit = this.getGoldPutDown() * 0.001;
	}
	
	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompund) {
		// TODO Auto-generated method stub
		
		tagCompund.setInteger("RecruitTime", this.getRecruitTime());
		tagCompund.setInteger("GoldPutDown", this.getGoldPutDown());
		tagCompund.setDouble("ChanceToRecruit", this.getChanceToRecruit());
		tagCompund.setBoolean("Recruit", this.Recruit);
		
		return super.writeToNBT(tagCompund);
	}
	
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompund) {
		
		
		this.setRecruitTime(tagCompund.getInteger("RecruitTime"));
		this.setGoldPutDown(tagCompund.getInteger("GoldPutDown"));
		this.setChanceToRecruit(tagCompund.getDouble("ChanceToRecruit"));
		this.setRecruit(tagCompund.getBoolean("Recruit"));
		
		super.readFromNBT(tagCompund);
	}
	
	
	@Override
	public void onUpdate() {

		if(!this.worldObj.isRemote)
		{
			if(!this.getRecruit())	
			{
				if(this.getGoldPutDown() > 0)
				{
					this.CalcGoldChange();
					
					if(this.RecruitTime >= 1200)
					{
						
						int Random = Rand.nextInt(100);
						
						
						if(Random <= this.ChanceToRecruit)
						{
							this.setRecruit(true);
						}
						
						this.setRecruitTime(0);
						
					}else{
						this.RecruitTime++;
					}
				}
			}
		}
		
		super.onUpdate();
	}
	
	
	
}
