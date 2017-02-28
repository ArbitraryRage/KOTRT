package com.waabbuffet.kotrt.packet.structure;

import java.io.File;

import com.waabbuffet.kotrt.proxy.ClientProxy;
import com.waabbuffet.kotrt.schematic.Structure.StructureReader;
import com.waabbuffet.kotrt.schematic.Structure.StructureSchematicCreator;
import com.waabbuffet.kotrt.schematic.world.StructureSchematicPrintToWorld;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class StructureSchematic  implements IMessage, IMessageHandler<StructureSchematic, IMessage> {

	
	public boolean BuildStructure = false;
	public String FileName = "";
	//save/load structure to and from file
	//Generate schematic to world
	
	public StructureSchematic(){ }
	
	public StructureSchematic(String fileName, boolean buildStructure){
		this.FileName = fileName;
		this.BuildStructure = buildStructure;
		
		
	}
	
	@Override
	public IMessage onMessage(StructureSchematic message, MessageContext ctx) {
		
		EntityPlayer player = ctx.getServerHandler().playerEntity;
		World world = ctx.getServerHandler().playerEntity.worldObj;
		
		if(message.BuildStructure){
			
			//Check to see if space available
			
		//		StructureReader.StructureReaderFromFile(new File(ClientProxy.dataDirectory, message.FileName));
			//	StructureSchematicPrintToWorld.PrintSchematicToWorld(player, world); //not real building class, only for testing
			
		}else {
	//		StructureReader.StructureReaderFromWorld(world, ClientProxy.Point1, ClientProxy.Point2);
			StructureSchematicCreator.SchematicCreator(message.FileName);
		}
		
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.BuildStructure = buf.readBoolean();
		this.FileName = ByteBufUtils.readUTF8String(buf);
	
		
		//file name, boolean to say build into world or just load structure, and directory
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(this.BuildStructure);
		ByteBufUtils.writeUTF8String(buf, this.FileName);
		
		
	}

}
