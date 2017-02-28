package com.waabbuffet.kotrt.packet.structure;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.waabbuffet.kotrt.inventory.ContainerTileEntityKingdomStructureBuilderBlock;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBuilderBlock;
import com.waabbuffet.kotrt.util.PlayerData;

public class ReduceStackSizeFromTE implements IMessage, IMessageHandler<ReduceStackSizeFromTE, IMessage> {


	public int BlockX, BlockY, BlockZ, MetaData;
	
	//save/load structure to and from file
	//Generate schematic to world
	
	public ReduceStackSizeFromTE(){ }
	
	public ReduceStackSizeFromTE(int blockX, int blockY, int blockZ, int metaData){
		
		
		this.BlockX = blockX;
		this.BlockY = blockY;
		this.BlockZ = blockZ;
		this.MetaData = metaData;
	}
	
	@Override
	public IMessage onMessage(ReduceStackSizeFromTE message, MessageContext ctx) {
		
		EntityPlayer player = ctx.getServerHandler().playerEntity;
		World world = ctx.getServerHandler().playerEntity.worldObj;
		
		TileEntityKingdomStructureBuilderBlock b = 	(TileEntityKingdomStructureBuilderBlock)(world.getTileEntity(new BlockPos(message.BlockX, message.BlockY, message.BlockZ)));
		
		
		
		b.decrStackSize(message.MetaData, 1);	
		
		
		
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		
		this.BlockX = buf.readInt();
		this.BlockY = buf.readInt();
		this.BlockZ = buf.readInt();
		this.MetaData = buf.readInt();
		
		
		//file name, boolean to say build into world or just load structure, and directory
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
	
		buf.writeInt(BlockX);
		buf.writeInt(BlockY);
		buf.writeInt(BlockZ);
		buf.writeInt(MetaData);
		
	}
	
}