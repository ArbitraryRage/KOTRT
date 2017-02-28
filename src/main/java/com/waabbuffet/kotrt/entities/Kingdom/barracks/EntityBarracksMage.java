package com.waabbuffet.kotrt.entities.Kingdom.barracks;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityBarracksMage extends EntityTameable{

	World world;
	int AttackCD = 0;
	public EntityBarracksMage(World worldIn) {
		super(worldIn);
		
		world = worldIn;
		
		this.setSize(1.0F, 1.7F);
		this.tasks.addTask(1, new EntityAISwimming(this));
	    this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.2D, 15.0F, 2.0F));
	    
	    this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
	   
	        
	    this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
	    this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
	    this.targetTasks.addTask(5, new EntityAIHurtByTarget(this, false));
	
	    this.setTamed(true);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
		
		
			if((!par1DamageSource.damageType.equals("arrow")) && !par1DamageSource.damageType.equals("player") &&!par1DamageSource.damageType.equals("magic") && par1DamageSource.getEntity() != null){
				
				return super.attackEntityFrom(par1DamageSource, Math.min(12, par2));
			}
		
		return false;
	}
	
	@Override
	public void onUpdate() {
	
		if(this.getOwner() != null)
		{
			if(this.getOwner().getLastAttacker() != null)
			{
				if(this.getOwner().getDistanceSqToEntity(this.getOwner().getLastAttacker()) < (64))
				{
					if(this.AttackCD == 0)
					{
					//	this.getOwner().getLastAttacker().setVelocity(0.0, 1.2, 0.0); save this for someone else
						
						//parent entity = ghast = this
						//entitylivingbase = target
						  EntityLivingBase entitylivingbase = this.getOwner().getLastAttacker();
						
							double d1 = 4.0D;
	                        Vec3d vec3 = this.getLook(1.0F);
	                        double d2 = entitylivingbase.posX - (this.posX + vec3.xCoord * d1);
	                        double d3 = entitylivingbase.getEntityBoundingBox().minY + (double)(entitylivingbase.height / 2.0F) - (0.5D + this.posY + (double)(this.height / 2.0F));
	                        double d4 = entitylivingbase.posZ - (this.posZ + vec3.zCoord * d1);
	                        world.playEvent((EntityPlayer)null, 1008, new BlockPos(this), 0);
	                        EntityMagicBolt entitylargefireball = new EntityMagicBolt(world, this, d2, d3, d4);
	                    
	                        
	                        entitylargefireball.posX = this.posX + vec3.xCoord * d1;
	                        entitylargefireball.posY = this.posY + (double)(this.height / 2.0F) + 0.5D;
	                        entitylargefireball.posZ = this.posZ + vec3.zCoord * d1;
	                        world.spawnEntityInWorld(entitylargefireball);
	                    
						this.AttackCD = 10;
					}else {
						this.AttackCD--;
					}
				}
			}
		}
		
		super.onUpdate();
	}

}
