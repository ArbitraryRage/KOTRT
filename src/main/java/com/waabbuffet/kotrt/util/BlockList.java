package com.waabbuffet.kotrt.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockList  {

	 public IBlockState Blockerino;
	 public int TotalNumberOfBlock;
	 
	 public BlockList(IBlockState b, int totalNumberOfBlock){
		 Blockerino = b;
		 TotalNumberOfBlock = totalNumberOfBlock;
		 
	 }
	 public int getTotalNumberOfBlock() {
			return TotalNumberOfBlock;
		}

	
	

	
	
}
