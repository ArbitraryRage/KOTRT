package com.waabbuffet.kotrt.packet.structure;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.waabbuffet.kotrt.entities.Kingdom.EntityFarmer;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBuilderBlock;

public class ChangeKingdomVillagerInformation implements IMessage, IMessageHandler<ChangeKingdomVillagerInformation, IMessage> {


	public String Job;
	public int WorkX, WorkY, WorkZ, ID, WhichOne;
	boolean StartJob;
	
	
	//save/load structure to and from file
	//Generate schematic to world
	
	public ChangeKingdomVillagerInformation(){ }
	
	public ChangeKingdomVillagerInformation(String job, int ID)
	{
		this.WhichOne = 0;
		this.Job = job;
		this.ID = ID;
	}
	
	public ChangeKingdomVillagerInformation(String job, int workX, int workY, int workZ, int ID)
	{
		this.WhichOne = 1;
		this.Job = "Dont";
		this.WorkX = workX;
		this.WorkY = workY;
		this.WorkZ = workZ;
		this.ID = ID;
	}
	
	public ChangeKingdomVillagerInformation(Boolean startJob, int ID, String Job)
	{
		this.Job = Job;
		this.WhichOne = 4;
		this.StartJob = startJob;
		this.ID = ID;
	}
	
	public ChangeKingdomVillagerInformation(Boolean startJob, int ID)
	{
		this.Job = "Dont";
		this.WhichOne = 2;
		this.StartJob = startJob;
		this.ID = ID;
	}
	
	public ChangeKingdomVillagerInformation(Boolean startJob, int i, int ID)
	{
		this.Job = "Dont";
		this.WhichOne = 3;
		this.StartJob = startJob;
		this.ID = ID;
	}
	
	
	
	@Override
	public IMessage onMessage(ChangeKingdomVillagerInformation message, MessageContext ctx) {
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		World world = Minecraft.getMinecraft().theWorld;
		
		
		EntityFarmer b = (EntityFarmer) world.getEntityByID(message.ID);
		
		if(message.WhichOne == 3)
		{
			b.setStartJob(message.StartJob);
			b.RemoveTasks();

		}
		
		if(message.WhichOne == 2)
		{
			
				b.setStartJob(message.StartJob);
				b.setWorkPlace(0, 0, 0);
				b.RemoveTasks();
				b.DetermineTasks(world);
			
		}
		
		
		
		if(message.WhichOne == 0)
		{
			
			b.setJob(message.Job);
		}
		
		if(message.WhichOne == 1)
		{
			b.setWorkPlace(message.WorkX, message.WorkY, message.WorkZ);
		}
		
		
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		
		this.Job = ByteBufUtils.readUTF8String(buf);

		this.WorkX = buf.readInt();
		this.WorkY = buf.readInt();
		this.WorkZ = buf.readInt();
		this.ID = buf.readInt();
		this.StartJob = buf.readBoolean();
		this.WhichOne = buf.readInt();
		
		
		
		//file name, boolean to say build into world or just load structure, and directory
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, Job);
		
		buf.writeInt(WorkX);
		buf.writeInt(WorkY);
		buf.writeInt(WorkZ);
		buf.writeInt(ID);
		buf.writeBoolean(this.StartJob);
		buf.writeInt(WhichOne);
		
	}
}