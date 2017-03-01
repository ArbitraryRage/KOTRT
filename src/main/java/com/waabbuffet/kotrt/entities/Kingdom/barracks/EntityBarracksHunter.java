package com.waabbuffet.kotrt.entities.Kingdom.barracks;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityBarracksHunter extends EntityTameable implements IRangedAttackMob
{
    private EntityAIAttackRanged aiArrowAttack = new EntityAIAttackRanged(this, 0.6D, 20, 20, 15.0F);
  
    
    private static final String __OBFID = "CL_00001697";

    public EntityBarracksHunter(World world)
    {
    	super(world);
		this.setSize(1.0F, 1.7F);
		this.tasks.addTask(1, new EntityAISwimming(this));
	    this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.2D, 10.0F, 2.0F));
	    this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
	    this.tasks.addTask(9, new EntityAILookIdle(this));
	        
	    this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
	    this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
	    this.targetTasks.addTask(5, new EntityAIHurtByTarget(this, false));
	      
	    
	    this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        this.tasks.addTask(4, this.aiArrowAttack);
        
        
        this.setTamed(true);
        

    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.50D);
    }

    protected void entityInit()
    {
        super.entityInit();
        
    }

    public boolean isAIEnabled()
    {
        return true;
    }

 
    public boolean attackEntityAsMob(Entity p_70652_1_)
    {
        if (super.attackEntityAsMob(p_70652_1_))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void setItemStackToSlot(EntityEquipmentSlot p_70062_1_, ItemStack p_70062_2_)
    {
        super.setItemStackToSlot(p_70062_1_, p_70062_2_);

    }
    
    @Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
		
		
			if((!par1DamageSource.damageType.equals("arrow")) && !par1DamageSource.damageType.equals("player") &&!par1DamageSource.damageType.equals("magic") && par1DamageSource.getEntity() != null){
				
				return super.attackEntityFrom(par1DamageSource, Math.min(12, par2));
			}
		
		return false;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable p_90011_1_) {
		
		return null;
	}

	 public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_)
	    {
	        EntityTippedArrow entityarrow = new EntityTippedArrow(this.worldObj, this);
	        int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, this.getHeldItem(swingingHand));
	        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, this.getHeldItem(swingingHand));
	        entityarrow.setDamage((double) 5.0);

	        if (i > 0)
	        {
	            entityarrow.setDamage(entityarrow.getDamage() + (double)3);
	        }

	        if (j > 0)
	        {
	            entityarrow.setKnockbackStrength(j);
	        }

	        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, this.getHeldItem(swingingHand)) > 0)
	        {
	            entityarrow.setFire(100);
	        }

	        //this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
	        this.worldObj.spawnEntityInWorld(entityarrow);
	    }
}
