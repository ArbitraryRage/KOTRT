package com.waabbuffet.kotrt.entities.Kingdom;

import com.waabbuffet.kotrt.util.ShopKeeperItemFormat;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityShopKeeper extends EntityTameable {

	
	
	
	
	public EntityShopKeeper(World worldIn) {
		super(worldIn);
		
		
		this.setCustomNameTag("Shopkeeper");
		this.setAlwaysRenderNameTag(true);
		this.setSize(1.0F, 1.7F);
	
		this.tasks.addTask(4, new EntityAILookIdle(this));
		this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
	}
	

	@Override
	public boolean canPickUpLoot() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	protected void applyEntityAttributes(){
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
		
	}
	
	
	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		// TODO Auto-generated method stub
		super.writeToNBT(tagCompound);
	}
	
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompund) {
		// TODO Auto-generated method stub
		super.readFromNBT(tagCompund);
	}
	
	public static ShopKeeperItemFormat[] FillItemRegBlocks()
	{
		ShopKeeperItemFormat[] b = new ShopKeeperItemFormat[50];
	
		//dirt
		b[0] = new ShopKeeperItemFormat(new ItemStack(Blocks.dirt), 2);
		
		//cobble
		b[1] = new ShopKeeperItemFormat(new ItemStack(Blocks.cobblestone), 2);
		
		//Grass
		b[2] = new ShopKeeperItemFormat(new ItemStack(Blocks.grass), 8);
		
		//Stone
		b[3] = new ShopKeeperItemFormat(new ItemStack(Blocks.stone), 4);
		
		//Gravel
		b[4] = new ShopKeeperItemFormat(new ItemStack(Blocks.gravel), 6);
		
		//Sand
		b[5] = new ShopKeeperItemFormat(new ItemStack(Blocks.sand), 2);
		
		//clay
		b[6] = new ShopKeeperItemFormat(new ItemStack(Blocks.clay), 24);
		
		//Obsidian (Old log)
		b[7] = new ShopKeeperItemFormat(new ItemStack(Blocks.obsidian), 24);
	//	b[7] = new ShopKeeperItemFormat(new ItemStack(Blocks.log), 16);
		
		//Bricks
		b[8] = new ShopKeeperItemFormat(new ItemStack(Blocks.stonebrick), 4);
	//	b[8] = new ShopKeeperItemFormat(new ItemStack(Blocks.log, 1, 1), 16);
		
		//Wool
		b[9] = new ShopKeeperItemFormat(new ItemStack(Blocks.wool), 12);
		
		//brick
		b[10] = new ShopKeeperItemFormat(new ItemStack(Blocks.brick_block), 30);
		
		//Harden clay
		b[11] = new ShopKeeperItemFormat(new ItemStack(Blocks.hardened_clay), 30);
	//	b[11] = new ShopKeeperItemFormat(new ItemStack(Blocks.leaves), 2);
		
		//Glow Stone
		b[12] = new ShopKeeperItemFormat(new ItemStack(Blocks.glowstone), 280);
		
		//Hay Bale
		b[13] = new ShopKeeperItemFormat(new ItemStack(Blocks.hay_block), 40);
		
		//BookShelf
		b[14] = new ShopKeeperItemFormat(new ItemStack(Blocks.bookshelf), 150);
		
		//Glass
		b[15] = new ShopKeeperItemFormat(new ItemStack(Blocks.glass), 5);
		
		//Coal
		b[16] = new ShopKeeperItemFormat(new ItemStack(Blocks.coal_ore), 320);
		
		//Iron
		b[17] = new ShopKeeperItemFormat(new ItemStack(Blocks.iron_ore), 150);
		
		//Gold
		b[18] = new ShopKeeperItemFormat(new ItemStack(Blocks.gold_ore), 600);
		
		//Diamond
		b[19] = new ShopKeeperItemFormat(new ItemStack(Blocks.diamond_ore),6400);
		
		//Lapis
		b[20] = new ShopKeeperItemFormat(new ItemStack(Blocks.lapis_ore), 2400);
		
		//Emerald
		b[21] = new ShopKeeperItemFormat(new ItemStack(Blocks.emerald_ore), 7200);
		
		//Quartz
		b[22] = new ShopKeeperItemFormat(new ItemStack(Blocks.quartz_ore), 400);
		
		//Redstone
		b[23] = new ShopKeeperItemFormat(new ItemStack(Blocks.redstone_ore), 1000);
		
		
		//netherrack
		b[24] = new ShopKeeperItemFormat(new ItemStack(Blocks.netherrack), 3);
		
		//Obsidian
		b[25] = new ShopKeeperItemFormat(new ItemStack(Blocks.obsidian), 24);
		
		//nether brick
		b[26] = new ShopKeeperItemFormat(new ItemStack(Blocks.nether_brick), 16);
		
		//Melon
		b[27] = new ShopKeeperItemFormat(new ItemStack(Blocks.melon_block), 50);
		
		//Pumpkin
		b[28] = new ShopKeeperItemFormat(new ItemStack(Blocks.pumpkin), 20);
		
		//reed
		b[29] = new ShopKeeperItemFormat(new ItemStack(Items.reeds), 6);
		
		//oak
		b[30] = new ShopKeeperItemFormat(new ItemStack(Blocks.log, 1 ,0), 16);
		
		//Spruce
		b[31] = new ShopKeeperItemFormat(new ItemStack(Blocks.log, 1 ,1), 16);
		
		//Birch
		b[32] = new ShopKeeperItemFormat(new ItemStack(Blocks.log, 1 ,2), 18);
		
		//Jungle
		b[33] = new ShopKeeperItemFormat(new ItemStack(Blocks.log, 1 ,3), 15);
		
		//
		b[34] = new ShopKeeperItemFormat(new ItemStack(Blocks.log2, 1 ,0), 15);
		
		//
		b[35] = new ShopKeeperItemFormat(new ItemStack(Blocks.log2, 1 ,1), 20);
		
		//
		b[36] = new ShopKeeperItemFormat(new ItemStack(Blocks.sapling, 1 ,0), 30);
		
		//spruce sap
		b[37] = new ShopKeeperItemFormat(new ItemStack(Blocks.sapling, 1 ,1), 30);
		//birch sap
		b[38] = new ShopKeeperItemFormat(new ItemStack(Blocks.sapling, 1 ,2), 40);
		
		//jungle sap
		b[39] = new ShopKeeperItemFormat(new ItemStack(Blocks.sapling, 1 ,3), 24);
		
		//oak leaves
		b[40] = new ShopKeeperItemFormat(new ItemStack(Blocks.leaves, 1, 0), 1);
		
		//spruce
		b[41] = new ShopKeeperItemFormat(new ItemStack(Blocks.leaves, 1, 1), 1);
		//birch
		b[42] = new ShopKeeperItemFormat(new ItemStack(Blocks.leaves, 1, 2), 2);
		//jungle
		b[43] = new ShopKeeperItemFormat(new ItemStack(Blocks.leaves, 1, 3), 1);
		
	
		return b;
	}

}
