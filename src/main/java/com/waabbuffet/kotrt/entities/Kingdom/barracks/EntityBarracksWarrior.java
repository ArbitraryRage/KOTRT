package com.waabbuffet.kotrt.entities.Kingdom.barracks;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityBarracksWarrior extends EntityTameable{


	int TPDelay = 0;
	
	public EntityBarracksWarrior(World par1World)
	{
		super(par1World);
		this.setSize(1.0F, 1.7F);

		
	        this.tasks.addTask(1, new EntityAISwimming(this));
	        this.tasks.addTask(1, new EntityAIAttackOnCollide(this, 1.5D, true));
	        this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.2D, 10.0F, 2.0F));
	        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
	        this.tasks.addTask(9, new EntityAILookIdle(this));
	        
	        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
	        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
	        this.targetTasks.addTask(5, new EntityAIHurtByTarget(this, false));
	       
	      
		   this.setTamed(true);
       
        
	}

	protected void applyEntityAttributes(){
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
		
	}
	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
		
		
			if((!par1DamageSource.damageType.equals("arrow")) && !par1DamageSource.damageType.equals("player") &&!par1DamageSource.damageType.equals("magic") && par1DamageSource.getEntity() != null){
				
				return super.attackEntityFrom(par1DamageSource, Math.min(12, par2));
			}
		
		return false;
	}
	
	protected boolean teleportRandomly()
    {
        double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 32.0D;
        double d1 = this.posY + (double)(this.rand.nextInt(64) - 32);
        double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 32.0D;
        return this.teleportTo(d0, d1, d2);
    }
	 /**
     * Teleport the enderman to another entity
     */
    protected boolean teleportToEntity(Entity p_70816_1_)
    {
        Vec3 vec3 = new Vec3(this.posX - p_70816_1_.posX, this.getEntityBoundingBox().minY + (double)(this.height / 2.0F) - p_70816_1_.posY + (double)p_70816_1_.getEyeHeight(), this.posZ - p_70816_1_.posZ);
        vec3 = vec3.normalize();
        double d0 = 16.0D;
        double d1 = this.posX + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3.xCoord * d0;
        double d2 = this.posY + (double)(this.rand.nextInt(16) - 8) - vec3.yCoord * d0;
        double d3 = this.posZ + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3.zCoord * d0;
        return this.teleportTo(d1, d2, d3);
    }

    /**
     * Teleport the enderman
     */
    protected boolean teleportTo(double x, double y, double z)
    {
        net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(this, x, y, z, 0);
        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return false;
        double d0 = this.posX;
        double d1 = this.posY;
        double d2 = this.posZ;
        this.posX = event.targetX;
        this.posY = event.targetY;
        this.posZ = event.targetZ;
        boolean flag = false;
        BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);

        if (this.worldObj.isBlockLoaded(blockpos))
        {
            boolean flag1 = false;

            while (!flag1 && blockpos.getY() > 0)
            {
                BlockPos blockpos1 = blockpos.down();
                Block block = this.worldObj.getBlockState(blockpos1).getBlock();

                if (block.getMaterial().blocksMovement())
                {
                    flag1 = true;
                }
                else
                {
                    --this.posY;
                    blockpos = blockpos1;
                }
            }

            if (flag1)
            {
                super.setPositionAndUpdate(this.posX, this.posY, this.posZ);

                if (this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(this.getEntityBoundingBox()))
                {
                    flag = true;
                }
            }
        }

        if (!flag)
        {
            this.setPosition(d0, d1, d2);
            return false;
        }
        else
        {
            int i = 128;

            for (int j = 0; j < i; ++j)
            {
                double d6 = (double)j / ((double)i - 1.0D);
                float f = (this.rand.nextFloat() - 0.5F) * 0.2F;
                float f1 = (this.rand.nextFloat() - 0.5F) * 0.2F;
                float f2 = (this.rand.nextFloat() - 0.5F) * 0.2F;
                double d3 = d0 + (this.posX - d0) * d6 + (this.rand.nextDouble() - 0.5D) * (double)this.width * 2.0D;
                double d4 = d1 + (this.posY - d1) * d6 + this.rand.nextDouble() * (double)this.height;
                double d5 = d2 + (this.posZ - d2) * d6 + (this.rand.nextDouble() - 0.5D) * (double)this.width * 2.0D;
                this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, d3, d4, d5, (double)f, (double)f1, (double)f2, new int[0]);
            }

            this.worldObj.playSoundEffect(d0, d1, d2, "mob.endermen.portal", 1.0F, 1.0F);
            this.playSound("mob.endermen.portal", 1.0F, 1.0F);
            return true;
        }
    }
	
	
	
	

	@Override
	protected boolean canDespawn()
	{
		return false;
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entity)
	{

			int i;
		    i = 7;
			teleportTo(entity.posX + 1, entity.posY, entity.posZ + 1);
			teleportRandomly();
			
	        return entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float)i);
	   
	}

	
	
	
    @Override
	public EntityAgeable createChild(EntityAgeable entityageable)
	{
		return null;
	
	}



}
