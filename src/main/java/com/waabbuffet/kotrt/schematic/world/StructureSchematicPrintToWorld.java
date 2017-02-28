package com.waabbuffet.kotrt.schematic.world;

import com.waabbuffet.kotrt.handlers.GenStairsHandler;
import com.waabbuffet.kotrt.proxy.ClientProxy;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBuilderBlock;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StructureSchematicPrintToWorld {


	public static void PrintSchematicToWorld(EntityPlayer player, World world, String Direction){

		//change to place tile entity -> then create fake structure, 
		int point1 = (int) player.posX;
		int point2 = (int) player.posY;
		int point3 = (int) player.posZ;

		for(int x = 0; x < ClientProxy.data.FullBlocks.length; x++) {
			for(int y = 0; y < ClientProxy.data.FullBlocks[0].length; y++) {
				for(int z = 0; z < ClientProxy.data.FullBlocks[0][0].length; z++) {
					int placePosX = point1 + x + 1;
					int placePosY = point2 + y;
					int placePosZ = point3 + z;


					if(!ClientProxy.data.FullBlocks[x][y][z].toString().contains("stair")){

						world.setBlockState(new BlockPos(placePosX, placePosY, placePosZ), ClientProxy.data.FullBlocks[x][y][z]);
					}else{
						if(ClientProxy.data.FullBlocks[x][y][z].toString().contains("stone")){
							world.setBlockState(new BlockPos(placePosX, placePosY, placePosZ), Blocks.STONE_BRICK_STAIRS.getStateFromMeta(GenStairsHandler.CorrectOrientation(ClientProxy.data.FullBlocks[x][y][z], world, new BlockPos(x,y,z), Direction)));
						}else{
							//Minecraft.getMinecraft().theWorld.setBlockState(new BlockPos(placePosX, placePosY, placePosZ), Blocks.oak_stairs.getStateFromMeta(GenStairsHandler.CorrectOrientation(ClientProxy.data.FullBlocks[x][y][z], Minecraft.getMinecraft().theWorld, new BlockPos(x,y,z))));
							world.setBlockState(new BlockPos(placePosX, placePosY, placePosZ), Blocks.OAK_STAIRS.getStateFromMeta(GenStairsHandler.CorrectOrientation(ClientProxy.data.FullBlocks[x][y][z], world, new BlockPos(x,y,z), Direction)));
						}

						//GenStairsHandler.CorrectOrientation(ClientProxy.data.FullBlocks[x][y][z], Minecraft.getMinecraft().theWorld, new BlockPos(x,y,z));
					}
				}
			}

		}


	}



}
