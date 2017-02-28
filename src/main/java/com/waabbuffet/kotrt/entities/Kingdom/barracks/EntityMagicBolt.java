package com.waabbuffet.kotrt.entities.Kingdom.barracks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMagicBolt extends EntityFireball{

	 public long boltVertex;
	 public int Ticks;
	 EntityLivingBase Target;
	 private int xTile = -1;
	    private int yTile = -1;
	    private int zTile = -1;
	    private Block inTile;
	    private boolean inGround;
	    public EntityLivingBase shootingEntity;
	    private int ticksAlive;
	    private int ticksInAir;
	    public double accelerationX;
	    public double accelerationY;
	    public double accelerationZ;

	
	public EntityMagicBolt(World worldIn) {
		super(worldIn);
		
		this.isImmuneToFire = true;
		
		  this.boltVertex = this.rand.nextLong();
		// TODO Auto-generated constructor stub
	}

	public EntityMagicBolt(World worldIn, EntityLivingBase Target, double xAccel, double yAccel, double zAccel) 
	{
		 super(worldIn, Target, xAccel, yAccel, zAccel);

	}
	
	
	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public boolean canBeCollidedWith() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	protected void onImpact(RayTraceResult movingObject)
    {
        if (!this.worldObj.isRemote)
        {
        	if(!(movingObject.entityHit instanceof EntityPlayer))
        	{
	            if (movingObject.entityHit != null)
	            {
	                movingObject.entityHit.attackEntityFrom(DamageSource.magic, 10.0F);
	                this.setDead();
	            }
        	}
	            
	         
	            
        	
        }
    }
	
	
	
}
