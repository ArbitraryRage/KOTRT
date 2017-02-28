package com.waabbuffet.kotrt.items.structure;

import java.util.List;

import com.waabbuffet.kotrt.util.PlayerData;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemKingdomStructureStaff extends Item {

	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		
		return false;
	}
	
	
	//@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX,float hitY, float hitZ) {
		
		
	
		return false;
	}
	
	
	//@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		
		
		return super.onItemRightClick(itemStackIn, worldIn, playerIn, null);
	}
	
	
	//@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		
		
	}
	
	
	
	
}
