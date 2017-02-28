package com.waabbuffet.kotrt.packet.structure;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBuilderBlock;

public class UpdateClientTileInv1 implements IMessage, IMessageHandler<UpdateClientTileInv1, IMessage> {


	public int TeX, TeY, TeZ, id, StartX, StartY, StartZ;
	public String FileName, Direction;
	
	//save/load structure to and from file
	//Generate schematic to world
	
	public UpdateClientTileInv1(){ }
	
	
	
	public UpdateClientTileInv1(String FileName, String Direction, int teX, int teY, int teZ){
		
		this.FileName = FileName;
		this.Direction = Direction;
	
		this.TeX = teX;
		this.TeY = teY;
		this.TeZ = teZ;
		this.id = 2;
		
	}
	
	public UpdateClientTileInv1(String FileName, String Direction, int teX, int teY, int teZ, int startX, int startY, int startZ, int id){
		
		this.FileName = FileName;
		this.Direction = Direction;
	
		this.TeX = teX;
		this.TeY = teY;
		this.TeZ = teZ;
		
		this.StartX = startX;
		this.StartY = startY;
		this.StartZ = startZ;
		
		this.id = id;
		
	}
	
	
	@Override
	public IMessage onMessage(UpdateClientTileInv1 message, MessageContext ctx) {
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		World world = Minecraft.getMinecraft().theWorld;
		
		TileEntityKingdomStructureBuilderBlock b = 	(TileEntityKingdomStructureBuilderBlock)(world.getTileEntity(new BlockPos(message.TeX, message.TeY, message.TeZ)));
		
		b.setFileName(message.FileName);
		b.setDirection(message.Direction);
		
		b.TileBlockPosX = message.StartX;
		b.TileBlockPosY = message.StartY;
		b.TileBlockPosZ = message.StartZ;
		
		if(message.id == 1)
		{
			b.StartConstruction = true;
		}
		
		if(message.id == 2)
		{
			
			b.StartConstruction = false;
		
		}
		

		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		
		this.FileName = ByteBufUtils.readUTF8String(buf);
		this.Direction = ByteBufUtils.readUTF8String(buf);
	
	
		this.TeX = buf.readInt();
		this.TeY = buf.readInt();
		this.TeZ = buf.readInt();
		
		this.StartX = buf.readInt();
		this.StartY = buf.readInt();
		this.StartZ = buf.readInt();
		
		this.id = buf.readInt();
	
		
		
		//file name, boolean to say build into world or just load structure, and directory
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
	
		ByteBufUtils.writeUTF8String(buf, this.FileName);
		ByteBufUtils.writeUTF8String(buf, this.Direction);
		
		buf.writeInt(TeX);
		buf.writeInt(TeY);
		buf.writeInt(TeZ);
		
		buf.writeInt(StartX);
		buf.writeInt(StartY);
		buf.writeInt(StartZ);
		
		buf.writeInt(id);
		
		
	}
	
}