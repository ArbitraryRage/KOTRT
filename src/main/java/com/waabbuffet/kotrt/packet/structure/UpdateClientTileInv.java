package com.waabbuffet.kotrt.packet.structure;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.waabbuffet.kotrt.inventory.ContainerTileEntityKingdomStructureBuilderBlock;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBuilderBlock;
import com.waabbuffet.kotrt.util.PlayerData;

public class UpdateClientTileInv implements IMessage, IMessageHandler<UpdateClientTileInv, IMessage> {


	public int BlockX, BlockY, BlockZ, TeX, TeY, TeZ;
	public String FileName, Direction;
	
	//save/load structure to and from file
	//Generate schematic to world
	
	public UpdateClientTileInv(){ }
	
	
	
	public UpdateClientTileInv(String FileName, String Direction, int blockX, int blockY, int blockZ, int teX, int teY, int teZ){
		
		this.FileName = FileName;
		this.Direction = Direction;
		this.BlockX = blockX;
		this.BlockY = blockY;
		this.BlockZ = blockZ;
		this.TeX = teX;
		this.TeY = teY;
		this.TeZ = teZ;
		
	}
	
	public UpdateClientTileInv(String FileName, String Direction, int teX, int teY, int teZ, int id){
		
		this.FileName = FileName;
		this.Direction = Direction;
		this.TeX = teX;
		this.TeY = teY;
		this.TeZ = teZ;
		
	}
	
	@Override
	public IMessage onMessage(UpdateClientTileInv message, MessageContext ctx) {
		
		EntityPlayer player = ctx.getServerHandler().playerEntity;
		World world = ctx.getServerHandler().playerEntity.worldObj;
		
		TileEntityKingdomStructureBuilderBlock b = 	(TileEntityKingdomStructureBuilderBlock)(world.getTileEntity(new BlockPos(message.TeX, message.TeY, message.TeZ)));
		
		
			b.StartCreationProcess(message.FileName, message.Direction, message.BlockX, message.BlockY, message.BlockZ);
	

		
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		
		this.FileName = ByteBufUtils.readUTF8String(buf);
		this.Direction = ByteBufUtils.readUTF8String(buf);
	
	
		this.BlockX = buf.readInt();
		this.BlockY = buf.readInt();
		this.BlockZ = buf.readInt();
		this.TeX = buf.readInt();
		this.TeY = buf.readInt();
		this.TeZ = buf.readInt();
	
		
		
		//file name, boolean to say build into world or just load structure, and directory
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
	
		ByteBufUtils.writeUTF8String(buf, this.FileName);
		ByteBufUtils.writeUTF8String(buf, this.Direction);
		
		buf.writeInt(BlockX);
		buf.writeInt(BlockY);
		buf.writeInt(BlockZ);
		buf.writeInt(TeX);
		buf.writeInt(TeY);
		buf.writeInt(TeZ);
		
		
	}
	
}
