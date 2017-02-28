package com.waabbuffet.kotrt.entities.Kingdom;

import com.waabbuffet.kotrt.packet.PacketHandler;
import com.waabbuffet.kotrt.packet.structure.ChangeKingdomVillagerInformation;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class EntityKingdomBase extends EntityTameable {
	
	protected String BuildingName = "Nothing";
	protected String Job = "Nothing";
	ItemStack RequestedItem = null;
	private int Happiness; 
	public BlockPos WorkHome;
	boolean StartJob;

	public EntityKingdomBase(World worldIn) {
		super(worldIn);
		
		//should have Data Watchers instead maybe
		this.setAlwaysRenderNameTag(true);
		this.setSize(1.0F, 1.7F);
	
	}
	

	@Override
	public boolean canPickUpLoot() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	protected void applyEntityAttributes(){
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
		
	}
	
	public void setWorkPlace(int x, int y, int z)
	{
		//Can add sync stuff yo
		this.WorkHome = new BlockPos(x,y,z);
		
	}
	
	public String getJob() {
		return Job;
	}
	
	
	public BlockPos getWorkPlace()
	{
		return this.WorkHome;
	}
	
	
	@Override
	public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {
	
		super.setItemStackToSlot(slotIn, stack);
	}
	
	@Override
	public void setTamed(boolean tamed) {
		
		super.setTamed(tamed);
	}
	
	public void setBuildingName(String buildingName) {
		BuildingName = buildingName;
	}
	
	public void setHappiness(int happiness) {
		Happiness = happiness;
	}
	
	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		
		return null;
	}
	
	
	
	
	public void setRequestedItem(ItemStack requestedItem) {
		RequestedItem = requestedItem;
	}
	
	public void setJob(String job) {
		Job = job;
	}
	
	public void setStartJob(boolean startJob) {
		StartJob = startJob;
	}
	
	public boolean getStartJob()
	{
		return this.StartJob;
	}
	
	@Override
	protected boolean canDespawn() {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	@Override
	public void setHomePosAndDistance(BlockPos pos, int distance) {
	
		super.setHomePosAndDistance(pos, distance);
	}
}
