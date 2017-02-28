package com.waabbuffet.kotrt.entities.Kingdom.barracks;

import net.minecraft.client.particle.ParticleFirework;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityBarracks extends EntityTameable {

	public EntityBarracks(World worldIn) {
		
		super(worldIn);
		
		
		this.setCustomNameTag("Barracks");
		this.setAlwaysRenderNameTag(true);
		this.setSize(1.0F, 1.7F);
		// TODO Auto-generated constructor stub
	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		// TODO Auto-generated method stub
		return null;
	}
	

	protected void applyEntityAttributes(){
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
		
	}
	
	
	

}
