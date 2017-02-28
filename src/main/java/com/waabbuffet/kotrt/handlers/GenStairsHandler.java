package com.waabbuffet.kotrt.handlers;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GenStairsHandler {

	

	public static int CorrectOrientation(IBlockState FullBlockData, World world, BlockPos Position, String BuildingDirection){
		
		int NBT = 0;
		
		
		if(FullBlockData.toString().contains("facing=east")){
			NBT = 0;
		}else if(FullBlockData.toString().contains("facing=north")){
			NBT = 2;
		}else
		if(FullBlockData.toString().contains("facing=west")){
			NBT = 1;
		}else 
		if(FullBlockData.toString().contains("facing=south")){
			NBT = 3;
		}
		
		//
		return NBT;
	}
}
