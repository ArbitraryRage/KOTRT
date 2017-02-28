package com.waabbuffet.kotrt.schematic;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.waabbuffet.kotrt.handlers.IOHandler;
import com.waabbuffet.kotrt.proxy.ClientProxy;
import com.waabbuffet.kotrt.schematic.world.BoundingPoint;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBuilderBlock;
import com.waabbuffet.kotrt.util.BlockList;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public final class Structure {
	
	public static class StructureData{
		
		public  IBlockState FullBlocks[][][];
		
	
		public  int PosXSize, PosYSize, PosZSize;
		

		public List<BlockList> MaterialList = new ArrayList<BlockList>();
		
		//used for builder block + fullblocks
		public String FileName;
		public boolean StartConstruction;
		public int BlockNumberX, BlockNumberY, BlockNumberZ;

		
			
			
	}
	
	public static class StructureReader {
		
		
		public static boolean StructureReaderFromFile(File f) {
		if(f != null){
			NBTTagCompound cmp = IOHandler.getTagCompoundInFile(f);
				
				int xSize = cmp.getInteger("MaxXValue");
				int ySize = cmp.getInteger("MaxYValue");
				int zSize = cmp.getInteger("MaxZValue");
				
				
				
				ClientProxy.data.FullBlocks = new IBlockState[xSize][ySize][zSize];
				  
				
				for(int x = 0; x < ClientProxy.data.FullBlocks.length; x++) {
					for(int y = 0; y < ClientProxy.data.FullBlocks[0].length; y++) {
						for(int z = 0; z < ClientProxy.data.FullBlocks[0][0].length; z++) {
							
							
							int ID = cmp.getInteger(x + "," + y + "," + z);
							
							ClientProxy.data.FullBlocks[x][y][z] = Block.getStateById(ID);
					
							
						}
					}
				}
				
		//		NBTTagCompound blocksCmp = cmp.getCompoundTag("blockData");
			//	Collection<String> keys = blocksCmp.getKeySet();
			
				
			}
			return true;
		}
		
		
		
		
		public static void StructureReaderFromWorld(World world, BoundingPoint point1, BoundingPoint point2){

			
			if(point1 != null && point2 != null){
				int minX = point1.posX() > point2.posX() ? point2.posX() : point1.posX();
				int maxX = point1.posX() > point2.posX() ? point1.posX() : point2.posX();
				int minY = point1.posY() > point2.posY() ? point2.posY() : point1.posY();
				int maxY = point1.posY() > point2.posY() ? point1.posY() : point2.posY();
				int minZ = point1.posZ() > point2.posZ() ? point2.posZ() : point1.posZ();
				int maxZ = point1.posZ() > point2.posZ() ? point1.posZ() : point2.posZ();
				
				int placePosX = 0, placePosY = 0, placePosZ = 0;
				
			
				ClientProxy.data.PosXSize = maxX - minX + 1;
				ClientProxy.data.PosYSize = maxY - minY + 1;
				ClientProxy.data.PosZSize = maxZ - minZ + 1;
				
			
				ClientProxy.data.FullBlocks = new IBlockState[ClientProxy.data.PosXSize][ClientProxy.data.PosYSize][ClientProxy.data.PosZSize];
			

				for(int x = 0; x < ClientProxy.data.FullBlocks.length; x++) {
					for(int y = 0; y < ClientProxy.data.FullBlocks[0].length; y++) {
						for(int z = 0; z < ClientProxy.data.FullBlocks[0][0].length; z++) {
					
						
								if(point1.posX() > point2.posX()){
									 placePosX = point1.posX() - x;
								}else if(point1.posX() < point2.posX()){
									 placePosX = point1.posX() + x;
								}
						
							
									 placePosY = point1.posY() + y;
								
								
								if(point1.posZ() > point2.posZ()){
									 placePosZ = point1.posZ() - z;
								}else if(point1.posZ() < point2.posZ()){
									 placePosZ = point1.posZ() + z;
								}
								
								ClientProxy.data.FullBlocks[x][y][z] =  world.getBlockState(new BlockPos(placePosX, placePosY, placePosZ));
								
						}
					}
				}
				
			}else {
				return;
			}
			
		}
		
		
	}	

	
	public static class StructureSchematicCreator {
		
		public static void SchematicCreator(String fileName){
			
			NBTTagCompound cmp = new NBTTagCompound();
			
			
			for(int x = 0; x < ClientProxy.data.FullBlocks.length; x++) {
				for(int y = 0; y < ClientProxy.data.FullBlocks[0].length; y++) {
					for(int z = 0; z < ClientProxy.data.FullBlocks[0][0].length; z++) {
						
						
						if(!ClientProxy.data.FullBlocks[x][y][z].equals(Blocks.AIR.getDefaultState())){
							
							cmp.setInteger(x + "," + y + "," + z, Block.getStateId(ClientProxy.data.FullBlocks[x][y][z]));
							
							
						//	Block b = ClientProxy.data.FullBlocks[x][y][z].getBlock();
						//	cmp.setInteger(x + "," + y + "," + z, Block.getIdFromBlock(b));
						}
						
						//ClientProxy.data.FullBlocks[0][0][0] = Block.getStateById(cm);
					}
				}
				
			}
			cmp.setInteger("MaxXValue", ClientProxy.data.FullBlocks.length);
			cmp.setInteger("MaxYValue", ClientProxy.data.FullBlocks[0].length);
			cmp.setInteger("MaxZValue", ClientProxy.data.FullBlocks[0][0].length);
			
			
			File file = IOHandler.createOrGetNBTFile(new File(ClientProxy.dataDirectory, fileName));
			IOHandler.injectNBTToFile(cmp, file);
			
			
			
			
		//	cmp.setInteger("id", Block.getIdFromBlock(Blocks));
		}
	}
	
	
}
	