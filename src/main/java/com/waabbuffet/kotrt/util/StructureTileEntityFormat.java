package com.waabbuffet.kotrt.util;

import net.minecraft.util.BlockPos;

public class StructureTileEntityFormat {

	
	//output rate, input rate (gold, if making bread then need constant supply of wheat)
	private String name, Direction;
	private int CurrentWorkers;
	private int StartX, StartY, StartZ;
	
	public StructureTileEntityFormat() {
	
	}
	
	public int getCurrentWorkers() {
		return CurrentWorkers;
	}
	
	public String getName() {
		return name;
	}
	
	public int getStartX() {
		return StartX;
	}
	public int getStartY() {
		return StartY;
	}
	public int getStartZ() {
		return StartZ;
	}
	public void setStartX(int startX) {
		StartX = startX;
	}
	
	public void setStartY(int startY) {
		StartY = startY;
	}
	 public void setStartZ(int startZ) {
		StartZ = startZ;
	}
	 
	public void AddOneToWorker()
	{
		CurrentWorkers++;
	}
			
	public void DecreaseByOneWorker()
	{
		CurrentWorkers--;
	}
	  
	  public void setCurrentWorkers(int currentWorkers) {
		CurrentWorkers = currentWorkers;
	}
	  public void setName(String name) {
		this.name = name;
	}
	   
	  public void setDirection(String direction) {
		Direction = direction;
	}
	  
	  public String getDirection() {
		return Direction;
	}
}
