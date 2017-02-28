package com.waabbuffet.kotrt.schematic.world;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class BoundingPoint {

	BlockPos StartingBlockPos;
	
	public BoundingPoint(EntityPlayer player)
	{
		StartingBlockPos = player.getPosition().down();
		player.addChatComponentMessage(new TextComponentString("[KOTRT] Position X: " + StartingBlockPos.getX() + " Position Y: " + StartingBlockPos.getY() + " Position Z: " + StartingBlockPos.getZ())); 
		
	}
	
	
	public int posX() {
		return this.StartingBlockPos.getX();
	}
	
	public int posY() {
		return this.StartingBlockPos.getY();
	}
	
	public int posZ() {
		return this.StartingBlockPos.getZ();
	}
}
