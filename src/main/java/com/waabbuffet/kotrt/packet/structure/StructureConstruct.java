package com.waabbuffet.kotrt.packet.structure;

import java.io.File;

import com.waabbuffet.kotrt.proxy.ClientProxy;
import com.waabbuffet.kotrt.schematic.Structure;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBuilderBlock;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class StructureConstruct  implements IMessage, IMessageHandler<StructureConstruct, IMessage> {

	
	public boolean RealBuildingOrNaw;
	public int BlockX, BlockY, BlockZ, MetaData, BlockID;
	public String BuildingDir;
	
	//save/load structure to and from file
	//Generate schematic to world
	
	public StructureConstruct(){ }
	
	public StructureConstruct(boolean realBuildingOrNaw, int blockX, int blockY, int blockZ, int blockID, int metaData, String BuildingDir){
		
		this.RealBuildingOrNaw = realBuildingOrNaw;
		this.BuildingDir = BuildingDir;
		this.BlockX = blockX;
		this.BlockY = blockY;
		this.BlockZ = blockZ;
		this.BlockID = blockID;
		this.MetaData = metaData;
	}
	
	
	
	
	@Override
	public IMessage onMessage(StructureConstruct message, MessageContext ctx) {
		
		EntityPlayer player = ctx.getServerHandler().playerEntity;
		World world = ctx.getServerHandler().playerEntity.worldObj;
		
		
		
		if(message.RealBuildingOrNaw){
			
			Block placement = Block.getBlockById(message.BlockID);
			
				if(placement.getDefaultState().toString().contains("stairs") || placement.getDefaultState().toString().contains("torch") || placement.getDefaultState().toString().contains("ladder"))
				{
					if(message.BuildingDir.contains("West"))
					{
						
						// 0 = east
						// 1 = west
						// 2 = south
						// 4 = north
						if(message.MetaData == 1)
						{
							
							message.MetaData = 0;
						
						}else if(message.MetaData == 0)
						{
						
							message.MetaData = 1;
						}
						
						if(message.MetaData == 5)
						{
							message.MetaData = 4;
						}else if(message.MetaData == 4)
						{
							message.MetaData = 5;
						}
						
						
						
						if(message.MetaData == 2)
						{
							
							message.MetaData = 3;
						
						}else if(message.MetaData == 3)
						{
						
							message.MetaData = 2;
						}
						
						if(message.MetaData == 6)
						{
							message.MetaData = 7;
						}else if(message.MetaData == 7)
						{
							message.MetaData = 6;
						}
					}else if(message.BuildingDir.contains("South"))
					{
					
		

						if(message.MetaData == 2)
						{
							
							message.MetaData = 1;
						
						}else if(message.MetaData == 0)
						{
						
							message.MetaData = 2;
						}else
						
						if(message.MetaData == 6)
						{
							message.MetaData = 5;
						}else if(message.MetaData == 4)
						{
							message.MetaData = 6;
						}else
						
						
						
						if(message.MetaData == 3)
						{
							
							message.MetaData = 0;
						
						}else if(message.MetaData == 1)
						{
						
							message.MetaData = 3;
						}else
						
						if(message.MetaData == 7)
						{
							message.MetaData = 4;
						}else if(message.MetaData == 5)
						{
							message.MetaData = 7;
						}
					}else if(message.BuildingDir.contains("North"))
					{
					
		
						if(message.MetaData == 1)
						{
							
							message.MetaData = 2;
						
						}else if(message.MetaData == 2)
						{
						
							message.MetaData = 0;
						}else
						
						if(message.MetaData == 5)
						{
							message.MetaData = 6;
						}else if(message.MetaData == 6)
						{
							message.MetaData = 4;
						}else
						
						
						
						if(message.MetaData == 0)
						{
							
							message.MetaData = 3;
						
						}else if(message.MetaData == 3)
						{
						
							message.MetaData = 1;
						}else
						
						if(message.MetaData == 4)
						{
							message.MetaData = 7;
						}else if(message.MetaData == 7)
						{
							message.MetaData = 5;
						}
					}
				}
				
					world.setBlockState(new BlockPos(message.BlockX, message.BlockY, message.BlockZ), placement.getStateFromMeta(message.MetaData));
				
		
					
						
				
				//minecraft:stone_stairs[facing=north,half=top,shape=straight] = 7
				// south = 6
				//minecraft:stone_stairs[facing=west,half=top,shape=straight] = 5
				//4 = top east
				
				//  minecraft:stone_stairs[facing=west,half=bottom,shape=straight] = 1
				// minecraft:stone_stairs[facing=south,half=bottom,shape=straight] = 2
				// minecraft:stone_stairs[facing=north,half=bottom,shape=straight] = 3
				// minecraft:stone_stairs[facing=east,half=bottom,shape=straight] = 0
			}
			
			//Check to see if space available
			
		//		StructureReader.StructureReaderFromFile(new File(ClientProxy.dataDirectory, message.FileName));
		//		StructureSchematicPrintToWorld.PrintSchematicToWorld(player, world); //not real building class, only for testing
			
		
			
	//		StructureReader.StructureReaderFromWorld(world, ClientProxy.Point1, ClientProxy.Point2);
	//		StructureSchematicCreator.SchematicCreator(message.FileName);
		
		
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.RealBuildingOrNaw= buf.readBoolean();
		
		this.BuildingDir = ByteBufUtils.readUTF8String(buf);
		
		this.BlockX = buf.readInt();
		this.BlockY = buf.readInt();
		this.BlockZ = buf.readInt();
		this.BlockID = buf.readInt();
		this.MetaData = buf.readInt();
		
		
		
		//file name, boolean to say build into world or just load structure, and directory
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(this.RealBuildingOrNaw);
		
		ByteBufUtils.writeUTF8String(buf, BuildingDir);
		
		buf.writeInt(BlockX);
		buf.writeInt(BlockY);
		buf.writeInt(BlockZ);
		buf.writeInt(BlockID);
		buf.writeInt(MetaData);
		
		
	}

}
