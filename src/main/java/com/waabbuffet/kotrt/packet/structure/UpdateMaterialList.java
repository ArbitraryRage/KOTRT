package com.waabbuffet.kotrt.packet.structure;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBuilderBlock;

public class UpdateMaterialList implements IMessage, IMessageHandler<UpdateMaterialList, IMessage> {


	public int  TeX, TeY, TeZ, StartX, StartY, StartZ, ID;
	public ItemStack Item;
	public String FileName, Direction;
	
	
	//save/load structure to and from file
	//Generate schematic to world
	
	public UpdateMaterialList(){ }
	
	public UpdateMaterialList( int teX, int teY, int teZ, ItemStack item){
		
	
		this.TeX = teX;
		this.TeY = teY;
		this.TeZ = teZ;
		this.Item = item;
		this.ID = 0;
		
	}
	
	public UpdateMaterialList(String FileName, String Direction, int teX, int teY, int teZ, int StartX, int StartY, int StartZ)
	{
	
		this.FileName = FileName;
		this.Direction = Direction;
		this.TeX = teX;
		this.TeY = teY;
		this.TeZ = teZ;
		this.StartX = StartX;
		this.StartY = StartY;
		this.StartZ = StartZ;
		this.ID = 1;
	}
	
	@Override
	public IMessage onMessage(UpdateMaterialList message, MessageContext ctx) {
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		World world = Minecraft.getMinecraft().theWorld;
		TileEntityKingdomStructureBuilderBlock b = null;
		
		if(message.TeX != 0){
		
			b = (TileEntityKingdomStructureBuilderBlock)(world.getTileEntity(new BlockPos(message.TeX, message.TeY, message.TeZ)));
		}
		
		if(message.ID == 0)
		{
			
			if(b != null)
			{
				if(!b.MaterialList1.isEmpty())
				{
					for(int i =0; i < b.MaterialList1.size(); i ++)
					{
						if(!message.Item.isItemEqual(new ItemStack(Items.WATER_BUCKET)) && !message.Item.isItemEqual(new ItemStack(Items.LAVA_BUCKET)))
						{
							if(message.Item.isItemEqual(new ItemStack(b.MaterialList1.get(i).Blockerino.getBlock())))
							{
								if(b.MaterialList1.get(i).TotalNumberOfBlock > 1)
								{
									b.MaterialList1.get(i).TotalNumberOfBlock--;
								}else{
									b.MaterialList1.remove(i);
								}
							}
						}else if(message.Item.isItemEqual(new ItemStack(Items.WATER_BUCKET)))
						{
							if(b.MaterialList1.get(i).Blockerino.toString().contains("water"))
							{
								if(b.MaterialList1.get(i).TotalNumberOfBlock > 0)
								{
									b.MaterialList1.get(i).TotalNumberOfBlock--;
								}else{
									b.MaterialList1.remove(i);
								}
							}
						}else if(message.Item.isItemEqual(new ItemStack(Items.LAVA_BUCKET)))
						{
							if(b.MaterialList1.get(i).Blockerino.toString().contains("lava"))
							{
								if(b.MaterialList1.get(i).TotalNumberOfBlock > 0)
								{
									b.MaterialList1.get(i).TotalNumberOfBlock--;
								}else{
									b.MaterialList1.remove(i);
								}
							}
						}
					}
				}
			}
		}
		
		if(message.ID == 1){
		
			b.RefreshMaterialList(world, message.FileName, message.Direction, message.StartX, message.StartY, message.StartZ);
		}
		
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.ID = buf.readInt();
		
		
		
		
		if(this.ID == 0){
			this.Item = ByteBufUtils.readItemStack(buf);
		}else{
			this.FileName = ByteBufUtils.readUTF8String(buf);
			this.Direction = ByteBufUtils.readUTF8String(buf);
		}

		this.TeX = buf.readInt();
		this.TeY = buf.readInt();
		this.TeZ = buf.readInt();
		
		this.StartX = buf.readInt();
		this.StartY = buf.readInt();
		this.StartZ = buf.readInt();
		
		
		//file name, boolean to say build into world or just load structure, and directory
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
		buf.writeInt(ID);
		
		
		
		
		if(this.ID == 0)
			ByteBufUtils.writeItemStack(buf, Item);
		else{
			ByteBufUtils.writeUTF8String(buf, this.FileName);
			ByteBufUtils.writeUTF8String(buf, this.Direction);
		}
		
		buf.writeInt(TeX);
		buf.writeInt(TeY);
		buf.writeInt(TeZ);
		

		buf.writeInt(StartX);
		buf.writeInt(StartY);
		buf.writeInt(StartZ);
		
	}
	
}