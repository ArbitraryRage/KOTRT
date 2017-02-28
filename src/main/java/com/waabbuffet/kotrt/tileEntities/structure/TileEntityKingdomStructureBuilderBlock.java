package com.waabbuffet.kotrt.tileEntities.structure;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import com.google.common.collect.Sets;
import com.waabbuffet.kotrt.KnightsOfTheRoundTable;
import com.waabbuffet.kotrt.packet.PacketHandler;
import com.waabbuffet.kotrt.packet.structure.ReduceStackSizeFromTE;
import com.waabbuffet.kotrt.packet.structure.StructureConstruct;
import com.waabbuffet.kotrt.packet.structure.UpdateClientTileInv1;
import com.waabbuffet.kotrt.packet.structure.UpdateMaterialList;
import com.waabbuffet.kotrt.proxy.ClientProxy;
import com.waabbuffet.kotrt.schematic.Structure;
import com.waabbuffet.kotrt.util.BlockList;


public class TileEntityKingdomStructureBuilderBlock extends TileEntity implements IInventory, ITickable
{
	public ItemStack[] inventory;

	private int BlockCoolDown, CurrentBlockIndex;
	private String customName = "Builder's Inventory";

	public  IBlockState FullBlocks[][][];
	private Ticket chunkTicket;

	public List<BlockList> MaterialList1 = new ArrayList<BlockList>();

	public String FileName, BuildingDirection;
	public boolean StartConstruction, UpdateClient = false, Completetion = false;
	public int TileBlockPosX = 0, TileBlockPosY = 0, TileBlockPosZ = 0;
	public int[] TopBlocks = new int[8];

	public TileEntityKingdomStructureBuilderBlock() {

		this.inventory = new ItemStack[this.getSizeInventory()];

		this.StartConstruction = false;
		this.BlockCoolDown = 0;
		this.Completetion = false;

	}

	public void setBuildingCoordinates(int x, int y, int z)
	{
		this.TileBlockPosX = x;
		this.TileBlockPosY = y;
		this.TileBlockPosZ = z;
	}


	public void setFileName(String File){
		this.FileName = File;
	}

	public void setConstruction(boolean Construction){
		this.StartConstruction = Construction;
	}

	public void setDirection(String direction){
		this.BuildingDirection = direction;
	}

	public String getCustomName() {
		return this.customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}


	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : "container.kotrt_kingdom_structure_builder_block";
	}

	@Override
	public boolean hasCustomName() {
		return this.customName != null && !this.customName.equals("");
	}

	@Override
	public IChatComponent getDisplayName() {
		return this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName());
	}

	@Override
	public int getSizeInventory() {
		//change to be dependent on what tier the building is
		return 15;
	}


	@Override
	public ItemStack getStackInSlot(int index) {
		if (index < 0 || index >= this.getSizeInventory())
			return null;
		return this.inventory[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (this.getStackInSlot(index) != null) {
			ItemStack itemstack;

			if (this.getStackInSlot(index).stackSize <= count) {
				itemstack = this.getStackInSlot(index);
				this.setInventorySlotContents(index, null);
				this.markDirty();
				return itemstack;
			} else {
				itemstack = this.getStackInSlot(index).splitStack(count);

				if (this.getStackInSlot(index).stackSize <= 0) {
					this.setInventorySlotContents(index, null);
				} else {
					//Just to show that changes happened
					this.setInventorySlotContents(index, this.getStackInSlot(index));
				}

				this.markDirty();
				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack stack = this.getStackInSlot(index);
		this.setInventorySlotContents(index, null);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (index < 0 || index >= this.getSizeInventory())
			return;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
			stack.stackSize = this.getInventoryStackLimit();

		if (stack != null && stack.stackSize == 0)
			stack = null;

		this.inventory[index] = stack;
		this.markDirty();
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.getPos()) == this && player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		// not needed

	}

	@Override
	public void closeInventory(EntityPlayer player) {
		// not needed

	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.getSizeInventory(); i++)
			this.setInventorySlotContents(i, null);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setBoolean("StartConstruction", this.StartConstruction);

		if(this.FileName != null && this.FileName != "")
		{
			nbt.setString("FileName", this.FileName);
			nbt.setString("Direction", this.BuildingDirection);
			nbt.setInteger("StartX", this.TileBlockPosX);
			nbt.setInteger("StartY", this.TileBlockPosY);
			nbt.setInteger("StartZ", this.TileBlockPosZ);

		}




		NBTTagList list = new NBTTagList();

		for (int i = 0; i < this.getSizeInventory(); ++i) {
			if (this.getStackInSlot(i) != null) {
				NBTTagCompound stackTag = new NBTTagCompound();
				stackTag.setByte("Slot", (byte) i);
				this.getStackInSlot(i).writeToNBT(stackTag);
				list.appendTag(stackTag);
			}
		}
		nbt.setTag("Items", list);

		if (this.hasCustomName()) {
			nbt.setString("CustomName", this.getCustomName());
		}



	}


	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.setConstruction(nbt.getBoolean("StartConstruction"));

		this.setFileName(nbt.getString("FileName"));
		this.setDirection(nbt.getString("Direction"));
		this.setBuildingCoordinates(nbt.getInteger("StartX"), nbt.getInteger("StartY"), nbt.getInteger("StartZ"));

		if(this.FileName != null && this.BuildingDirection != null && this.StartConstruction)
		{
			this.UpdateClient = true;

		}

		NBTTagList list = nbt.getTagList("Items", 10);
		for (int i = 0; i < list.tagCount(); ++i) {
			NBTTagCompound stackTag = list.getCompoundTagAt(i);
			int slot = stackTag.getByte("Slot") & 255;
			this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(stackTag));
		}

		if (nbt.hasKey("CustomName", 8)) {
			this.setCustomName(nbt.getString("CustomName"));
		}



	}


	public void setFullBlock(){
		FullBlocks = ClientProxy.data.FullBlocks;
	}

	public boolean AlreadyInTheList(List<BlockList> List, IBlockState OneBlock)
	{
		boolean NotInsideList = true;

		for(int i = 0; i < List.size(); i++){


			if(List.get(i).Blockerino.getBlock().toString().substring(10).equals(OneBlock.getBlock().toString().substring(10)))
			{
				MaterialList1.get(i).TotalNumberOfBlock += 1;
				NotInsideList = false;

			}
		}

		return NotInsideList;
	}



	public void FillMaterialList(String FileName, boolean Construction)

	{


		if(Construction)
		{
			for(int x = 0; x < this.FullBlocks.length; x++) {
				for(int y = 0; y < this.FullBlocks[0].length; y++) {
					for(int z = 0; z < this.FullBlocks[0][0].length; z++) {


						IBlockState B = this.FullBlocks[x][y][z];

						if(MaterialList1.size() != 0 ){

							if(!(B.equals(Blocks.air.getDefaultState()))){

								if(AlreadyInTheList(MaterialList1, B))
								{

									BlockList NewBlock = new BlockList(B, 1);
									MaterialList1.add(NewBlock);

								}
							}

						}else {

							BlockList NewBlock = new BlockList(B,1);
							MaterialList1.add(NewBlock);

						}
					}

				}
			}



		}else{
			// TODO load schematic not done yet



		}


		//		System.out.println("the size" + MaterialList1.get(i).TotalNumberOfBlock);
		//		System.out.println("The block: " + MaterialList1.get(i).Blockerino.getBlock());




	}
	public void StartCreationProcess(String fileName, String Direction, int posX, int posY, int posZ){

		setFileName(fileName);
		setDirection(Direction);
		setConstruction(true);
		setBuildingCoordinates(posX, posY, posZ);

	}

	public List<BlockList> GetMaterialList()
	{
		this.FillMaterialList("", false);
		return MaterialList1;	
	}

	public void SortList()
	{
		Collections.sort(MaterialList1, new Comparator<BlockList>() {
			@Override public int compare(BlockList p1, BlockList p2) {
				return p2.getTotalNumberOfBlock()- p1.getTotalNumberOfBlock(); // Ascending
			}

		});


	}


	public void PlaceBlock()
	{
		BlockPos pos = new BlockPos(0,0,0);
		//	BlockPos NextBlockToPlace = this.GetMaterialListWantedBlock();
		//		IBlockState meta = this.FullBlocks[NextBlockToPlace.getX()][NextBlockToPlace.getY()][NextBlockToPlace.getZ()];

		for(int x = 0; x < this.FullBlocks.length; x++) {
			for(int y = 0; y < this.FullBlocks[0].length; y++) {
				for(int z = 0; z < this.FullBlocks[0][0].length; z++) {


					if(!this.FullBlocks[x][y][z].getBlock().toString().contains(":air"))
					{
						if(this.MaterialList1.get(CurrentBlockIndex).Blockerino.getBlock().getDefaultState().equals(this.FullBlocks[x][y][z].getBlock().getDefaultState()))
						{
							pos = new BlockPos(x,y,z);
							break;

						}
					}

				}

			}

		}


		//bug when player logs out, cuz it will reload file 
		//	Block Test = meta.getBlock();



		//	for(int i = 0; i > this.inventory.length; i++){

		//	if(this.inventory[i].getIsItemStackEqual(new ItemStack(Test))){

		//	this.decrStackSize(this.MostUsedBlockInList(), 1);

		IBlockState meta = this.FullBlocks[pos.getX()][pos.getY()][pos.getZ()];
		Block Test = meta.getBlock();
		//Positive: 
		//East: X: ^ Z: ^
		//South: X: D Z: ^
		//West: X: D Z: D
		//North: X: ^ Z: D




		if(Minecraft.getMinecraft().theWorld != null){

			if(this.BuildingDirection.contains("East"))
			{


				if(!Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(this.TileBlockPosX + pos.getX(), this.TileBlockPosY + pos.getY(), this.TileBlockPosZ + pos.getZ())).getBlock().getDefaultState().equals(meta.getBlock().getDefaultState())){
					PacketHandler.INSTANCE.sendToServer(new StructureConstruct(true, this.TileBlockPosX + pos.getX(), this.TileBlockPosY + pos.getY(), this.TileBlockPosZ + pos.getZ(), Test.getIdFromBlock(Test), Test.getMetaFromState(meta), this.BuildingDirection));
					this.MaterialList1.get(this.CurrentBlockIndex).TotalNumberOfBlock--;


				}else {

					this.MaterialList1.get(this.CurrentBlockIndex).TotalNumberOfBlock--;
					this.FullBlocks[pos.getX()][pos.getY()][pos.getZ()] = Blocks.air.getDefaultState();

					if(this.MaterialList1.get(this.CurrentBlockIndex).TotalNumberOfBlock > 0)
					{
						this.PlaceBlock();

					}else if(this.MaterialList1.get(this.CurrentBlockIndex).TotalNumberOfBlock <= 0){
						this.MaterialList1.remove(CurrentBlockIndex);

					}


				}




			}else if(this.BuildingDirection.contains("South")){
				if(!Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(this.TileBlockPosX - pos.getZ(), this.TileBlockPosY + pos.getY(), this.TileBlockPosZ + pos.getX())).getBlock().getDefaultState().equals(meta.getBlock().getDefaultState())){
					PacketHandler.INSTANCE.sendToServer(new StructureConstruct(true, this.TileBlockPosX - pos.getZ(), this.TileBlockPosY + pos.getY(), this.TileBlockPosZ + pos.getX(), Test.getIdFromBlock(Test), Test.getMetaFromState(meta), this.BuildingDirection));
					this.MaterialList1.get(this.CurrentBlockIndex).TotalNumberOfBlock--;
				}else {

					this.MaterialList1.get(this.CurrentBlockIndex).TotalNumberOfBlock--;
					this.FullBlocks[pos.getX()][pos.getY()][pos.getZ()] = Blocks.air.getDefaultState();

					if(this.MaterialList1.get(this.CurrentBlockIndex).TotalNumberOfBlock > 0)
					{
						this.PlaceBlock();

					}else if(this.MaterialList1.get(this.CurrentBlockIndex).TotalNumberOfBlock <= 0){
						this.MaterialList1.remove(CurrentBlockIndex);

					}

				}

			}else if(this.BuildingDirection.contains("West")){

				if(!Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(this.TileBlockPosX - pos.getX(), this.TileBlockPosY + pos.getY(), this.TileBlockPosZ - pos.getZ())).getBlock().getDefaultState().equals(meta.getBlock().getDefaultState())){
					PacketHandler.INSTANCE.sendToServer(new StructureConstruct(true, this.TileBlockPosX - pos.getX(), this.TileBlockPosY + pos.getY(), this.TileBlockPosZ - pos.getZ(), Test.getIdFromBlock(Test), Test.getMetaFromState(meta), this.BuildingDirection));
					this.MaterialList1.get(this.CurrentBlockIndex).TotalNumberOfBlock--;
				}else {

					this.MaterialList1.get(this.CurrentBlockIndex).TotalNumberOfBlock--;
					this.FullBlocks[pos.getX()][pos.getY()][pos.getZ()] = Blocks.air.getDefaultState();

					if(this.MaterialList1.get(this.CurrentBlockIndex).TotalNumberOfBlock > 0)
					{
						this.PlaceBlock();

					}else if(this.MaterialList1.get(this.CurrentBlockIndex).TotalNumberOfBlock <= 0){
						this.MaterialList1.remove(CurrentBlockIndex);

					}

				}

			}else if(this.BuildingDirection.contains("North")){


				if(!Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(this.TileBlockPosX + pos.getZ(), this.TileBlockPosY + pos.getY(), this.TileBlockPosZ - pos.getX())).getBlock().getDefaultState().equals(meta.getBlock().getDefaultState())){
					PacketHandler.INSTANCE.sendToServer(new StructureConstruct(true, this.TileBlockPosX + pos.getZ(), this.TileBlockPosY + pos.getY(), this.TileBlockPosZ - pos.getX(), Test.getIdFromBlock(Test), Test.getMetaFromState(meta), this.BuildingDirection));
					this.MaterialList1.get(this.CurrentBlockIndex).TotalNumberOfBlock--;
				}else {

					this.MaterialList1.get(this.CurrentBlockIndex).TotalNumberOfBlock--;
					this.FullBlocks[pos.getX()][pos.getY()][pos.getZ()] = Blocks.air.getDefaultState();

					if(this.MaterialList1.get(this.CurrentBlockIndex).TotalNumberOfBlock > 0)
					{
						this.PlaceBlock();

					}else if(this.MaterialList1.get(this.CurrentBlockIndex).TotalNumberOfBlock <= 0){
						this.MaterialList1.remove(CurrentBlockIndex);

					}

				}

			}

			this.BlockCoolDown = 5;
			this.FullBlocks[pos.getX()][pos.getY()][pos.getZ()] = Blocks.air.getDefaultState();

		}

		//	}
		//	}

	}



	public int isItemInListAndInv()
	{
		this.CurrentBlockIndex = -1;


		for(int i = 0; i < this.inventory.length; i ++)
		{

			for(int j = 0; j < this.MaterialList1.size(); j ++){


				if(this.inventory[i] != null)
				{

					if(this.inventory[i].isItemEqual(new ItemStack(this.MaterialList1.get(j).Blockerino.getBlock())))
					{
						if(this.inventory[i].stackSize > 0 && this.MaterialList1.get(j).TotalNumberOfBlock > 0){	
							this.CurrentBlockIndex = j;



							if(this.MaterialList1.get(j).Blockerino.getBlock() != Blocks.torch)
								PacketHandler.INSTANCE.sendToServer(new ReduceStackSizeFromTE(this.pos.getX(), this.pos.getY(), this.pos.getZ(), i));

							return CurrentBlockIndex;
						}
					}else if(this.MaterialList1.get(j).Blockerino.toString().contains("water")){

						if(this.inventory[i].isItemEqual(new ItemStack(Items.water_bucket)))
						{
							//give back bucket though
							this.CurrentBlockIndex = j;
							this.decrStackSize(i, 1);
							this.setInventorySlotContents(i, new ItemStack(Items.bucket));
							this.MaterialList1.get(j).TotalNumberOfBlock--;
							return CurrentBlockIndex;
						}
					}else if(this.MaterialList1.get(j).Blockerino.toString().contains("lava")){

						if(this.inventory[i].isItemEqual(new ItemStack(Items.lava_bucket)))
						{
							//give back bucket though
							this.CurrentBlockIndex = j;
							this.decrStackSize(i, 1);
							this.setInventorySlotContents(i, new ItemStack(Items.bucket));
							this.MaterialList1.get(j).TotalNumberOfBlock--;
							return CurrentBlockIndex;
						}
					}

				}
			}
		}




		//System.out.println("Most used" + MostUsed);
		return this.CurrentBlockIndex;
	}

	public void SpawnStructureTE(String FileName, BlockPos Placement)
	{

		//send packet here
		//	TileEntityKingdomStructureBlock b = new TileEntityKingdomStructureBlock();
		//	b.structure.setName(FileName);
		//	b.setPos(Placement);
		//	b.structure.setCurrentLevel(0);



	}

	public void RefreshMaterialList(World world, String FileName, String Direction, int StartX, int StartY, int StartZ)
	{

		Structure.StructureReader.StructureReaderFromFile(new File(ClientProxy.dataDirectory, FileName));
		this.setFullBlock();
		this.MaterialList1 = new ArrayList<BlockList>();


		if(this.MaterialList1.isEmpty())
		{

			FillMaterialList("", true); 
		}


		for(int x = 0; x < this.FullBlocks.length; x++) {
			for(int y = 0; y < this.FullBlocks[0].length; y++) {
				for(int z = 0; z < this.FullBlocks[0][0].length; z++) {

					IBlockState B = this.FullBlocks[x][y][z];

					//Positive: 
					//East: X: ^ Z: ^
					//South: X: D Z: ^
					//West: X: D Z: D 
					//North: X: ^ Z: D

					if(B.getBlock() != Blocks.air)
					{

						if(Direction.contains("East"))
						{


							if(B.getBlock().equals(world.getBlockState(new BlockPos(StartX + x, StartY + y, StartZ + z)).getBlock()))
							{
								for(int i = 0; i < this.MaterialList1.size(); i ++)
								{
									if(MaterialList1.get(i).Blockerino.getBlock().equals(B.getBlock()))
									{
										if(MaterialList1.get(i).TotalNumberOfBlock > 0)
											MaterialList1.get(i).TotalNumberOfBlock--;
										else{
											MaterialList1.remove(i);
										}
									}
								}
							}

						}else if(Direction.contains("West"))
						{

							if(B.getBlock().equals(world.getBlockState(new BlockPos(StartX - x, StartY + y, StartZ - z)).getBlock()))
							{



								for(int i = 0; i < this.MaterialList1.size(); i ++)
								{
									if(MaterialList1.get(i).Blockerino.getBlock().equals(B.getBlock()))
									{
										if(MaterialList1.get(i).TotalNumberOfBlock > 0)
											MaterialList1.get(i).TotalNumberOfBlock--;
										else{
											MaterialList1.remove(i);
										}
									}
								}
							}

						}else if(Direction.contains("North"))
						{
							if(B.getBlock().equals(world.getBlockState(new BlockPos(StartX + z, StartY + y, StartZ - x)).getBlock()))
							{
								for(int i = 0; i < this.MaterialList1.size(); i ++)
								{
									if(MaterialList1.get(i).Blockerino.getBlock().equals(B.getBlock()))
									{
										if(MaterialList1.get(i).TotalNumberOfBlock > 0)
											MaterialList1.get(i).TotalNumberOfBlock--;
										else{
											MaterialList1.remove(i);
										}
									}
								}
							}

						}else if(Direction.contains("South"))
						{
							if(B.getBlock().equals(world.getBlockState(new BlockPos(StartX - z, StartY + y, StartZ + x)).getBlock()))
							{
								for(int i = 0; i < this.MaterialList1.size(); i ++)
								{
									if(MaterialList1.get(i).Blockerino.getBlock().equals(B.getBlock()))
									{
										if(MaterialList1.get(i).TotalNumberOfBlock > 0)
											MaterialList1.get(i).TotalNumberOfBlock--;
										else{
											MaterialList1.remove(i);
										}
									}
								}
							}

						}
					}

				}

			}




		}
	}

	public void UpdateMaterialList(IBlockState state)
	{
		//sendpacket, need X, Y,Z, and then material list index


		if(!state.toString().contains("water") && !state.toString().contains("lava"))
		{
			ItemStack item = new ItemStack(state.getBlock());
			PacketHandler.INSTANCE.sendToAll(new UpdateMaterialList(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), item));
		}else if(state.toString().contains("water"))		
		{
			ItemStack item = new ItemStack(Items.water_bucket);
			PacketHandler.INSTANCE.sendToAll(new UpdateMaterialList(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), item));
		}else if(state.toString().contains("lava"))		
		{
			ItemStack item = new ItemStack(Items.lava_bucket);
			PacketHandler.INSTANCE.sendToAll(new UpdateMaterialList(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), item));
		}

	}

	@Override
	public void invalidate() {
		if (chunkTicket != null) {
			ForgeChunkManager.releaseTicket(chunkTicket);
		}

		super.invalidate();

	}

	@Override
	public void onChunkUnload() {
		this.invalidate();
	}

	public void forceChunkLoading(Ticket ticket) {

		if (chunkTicket == null) {
			chunkTicket = ticket;
		}

		Set<ChunkCoordIntPair> chunks = Sets.newHashSet();
		ChunkCoordIntPair quarryChunk = new ChunkCoordIntPair(pos.getX() >> 4, pos.getZ() >> 4);
		chunks.add(quarryChunk);
		ForgeChunkManager.forceChunk(ticket, quarryChunk);



		for (int chunkX = (this.pos.getX() >> 4) - 5; chunkX <= (6 + (this.pos.getX() >> 4)); chunkX++) {
			for (int chunkZ = (this.pos.getZ() >> 4) - 5; chunkZ <=  (6 + (this.pos.getZ() >> 4)); chunkZ++) {
				ChunkCoordIntPair chunk = new ChunkCoordIntPair(chunkX, chunkZ);
				ForgeChunkManager.forceChunk(ticket, chunk);

				chunks.add(chunk);
			}
		}




		//-207

	}

	public boolean CanPlaceTorch(int item)
	{
		boolean CanPlace = false;

		for(int x = 0; x < this.FullBlocks.length; x++) {
			for(int y = 0; y < this.FullBlocks[0].length; y++) {
				for(int z = 0; z < this.FullBlocks[0][0].length; z++) {

					if(this.MaterialList1.get(item).Blockerino.getBlock().getDefaultState().equals(this.FullBlocks[x][y][z].getBlock().getDefaultState()))
					{
						if(this.BuildingDirection.contains("East"))
						{
							if(this.worldObj.getBlockState(new BlockPos(this.TileBlockPosX + x + 1, this.TileBlockPosY + y, this.TileBlockPosZ + z)) != Blocks.air.getDefaultState())
							{

								return true;
							}else{
								if(this.worldObj.getBlockState(new BlockPos(this.TileBlockPosX + x - 1, this.TileBlockPosY + y, this.TileBlockPosZ + z)) != Blocks.air.getDefaultState())
								{

									return true;
								}else{

									if(this.worldObj.getBlockState(new BlockPos(this.TileBlockPosX + x, this.TileBlockPosY + y, this.TileBlockPosZ + z + 1)) != Blocks.air.getDefaultState())
									{


										return true;
									}else{
										if(this.worldObj.getBlockState(new BlockPos(this.TileBlockPosX + x, this.TileBlockPosY + y, this.TileBlockPosZ + z - 1)) != Blocks.air.getDefaultState())
										{

											return true;
										}else{
											if(this.worldObj.getBlockState(new BlockPos(this.TileBlockPosX + x, this.TileBlockPosY + y -1, this.TileBlockPosZ + z)) != Blocks.air.getDefaultState())
											{

												return true;
											}
										}
									}

								}

							}
						}else if(this.BuildingDirection.contains("West"))
						{
							if(this.worldObj.getBlockState(new BlockPos(this.TileBlockPosX - x + 1, this.TileBlockPosY + y, this.TileBlockPosZ - z)) != Blocks.air.getDefaultState())
							{
								return true;
							}else{
								if(this.worldObj.getBlockState(new BlockPos(this.TileBlockPosX - x - 1, this.TileBlockPosY + y, this.TileBlockPosZ - z)) != Blocks.air.getDefaultState())
								{
									return true;
								}else{

									if(this.worldObj.getBlockState(new BlockPos(this.TileBlockPosX - x, this.TileBlockPosY + y, this.TileBlockPosZ - z + 1)) != Blocks.air.getDefaultState())
									{
										return true;
									}else{
										if(this.worldObj.getBlockState(new BlockPos(this.TileBlockPosX - x, this.TileBlockPosY + y, this.TileBlockPosZ - z - 1)) != Blocks.air.getDefaultState())
										{
											return true;
										}else{
											if(this.worldObj.getBlockState(new BlockPos(this.TileBlockPosX - x, this.TileBlockPosY + y -1, this.TileBlockPosZ - z)) != Blocks.air.getDefaultState())
											{
												return true;
											}
										}
									}

								}

							}
						}else if(this.BuildingDirection.contains("North"))
						{
							if(this.worldObj.getBlockState(new BlockPos(this.TileBlockPosX + z + 1, this.TileBlockPosY + y, this.TileBlockPosZ - x)) != Blocks.air.getDefaultState())
							{
								return true;
							}else{
								if(this.worldObj.getBlockState(new BlockPos(this.TileBlockPosX + z - 1, this.TileBlockPosY + y, this.TileBlockPosZ - x)) != Blocks.air.getDefaultState())
								{
									return true;
								}else{

									if(this.worldObj.getBlockState(new BlockPos(this.TileBlockPosX + z, this.TileBlockPosY + y, this.TileBlockPosZ - x + 1)) != Blocks.air.getDefaultState())
									{
										return true;
									}else{
										if(this.worldObj.getBlockState(new BlockPos(this.TileBlockPosX + z, this.TileBlockPosY + y, this.TileBlockPosZ - x - 1)) != Blocks.air.getDefaultState())
										{
											return true;
										}else{
											if(this.worldObj.getBlockState(new BlockPos(this.TileBlockPosX + z, this.TileBlockPosY + y -1, this.TileBlockPosZ - x)) != Blocks.air.getDefaultState())
											{
												return true;
											}
										}
									}

								}

							}
						}else if(this.BuildingDirection.contains("South"))
						{
							if(this.worldObj.getBlockState(new BlockPos(this.TileBlockPosX - z + 1, this.TileBlockPosY + y, this.TileBlockPosZ + x)) != Blocks.air.getDefaultState())
							{
								return true;
							}else{
								if(this.worldObj.getBlockState(new BlockPos(this.TileBlockPosX - z - 1, this.TileBlockPosY + y, this.TileBlockPosZ + x)) != Blocks.air.getDefaultState())
								{
									return true;
								}else{

									if(this.worldObj.getBlockState(new BlockPos(this.TileBlockPosX - z, this.TileBlockPosY + y, this.TileBlockPosZ + x + 1)) != Blocks.air.getDefaultState())
									{
										return true;
									}else{
										if(this.worldObj.getBlockState(new BlockPos(this.TileBlockPosX - z, this.TileBlockPosY + y, this.TileBlockPosZ + x - 1)) != Blocks.air.getDefaultState())
										{
											return true;
										}else{
											if(this.worldObj.getBlockState(new BlockPos(this.TileBlockPosX - z, this.TileBlockPosY + y -1, this.TileBlockPosZ + x)) != Blocks.air.getDefaultState())
											{
												return true;
											}
										}
									}

								}

							}
						}




					}



				}

			}

		}

		return CanPlace;

	}


	@Override
	public void update() {


		if(this.chunkTicket == null)
		{
			chunkTicket = ForgeChunkManager.requestTicket(KnightsOfTheRoundTable.instance, worldObj, Type.NORMAL);


		}

		if(this.chunkTicket != null)
		{
			chunkTicket.getModData().setInteger("quarryX", pos.getX());
			chunkTicket.getModData().setInteger("quarryY", pos.getY());
			chunkTicket.getModData().setInteger("quarryZ", pos.getZ());
			ForgeChunkManager.forceChunk(chunkTicket, new ChunkCoordIntPair(pos.getX() >> 4, pos.getZ() >> 4));
		}



		if(this.worldObj.isRemote)
		{

			if(this.StartConstruction)
			{
				if(MaterialList1.size() <= 3 && !MaterialList1.isEmpty())
				{

					this.Completetion = true;
				}

				if(this.MaterialList1.isEmpty())
				{
					Structure.StructureReader.StructureReaderFromFile(new File(ClientProxy.dataDirectory, FileName));
					this.setFullBlock();
					FillMaterialList("", true); 

				}
			}
		}	

		if(!this.worldObj.isRemote)
		{


			if(StartConstruction)
			{

				this.SortList();
				if(this.BlockCoolDown <= 0)
				{
					if(FileName != null  && FullBlocks != null)
					{

						if(MaterialList1.size() > 1)
						{

							if(MaterialList1.size() <= 3 && !MaterialList1.isEmpty())
							{

								this.Completetion = true;
							}

							int item = this.isItemInListAndInv();


							if(item != -1)
							{

								if(this.MaterialList1.get(item).Blockerino.getBlock().equals(Blocks.torch))
								{
									if(!this.CanPlaceTorch(item))
									{
										return;
									}



									for(int i =0; i < this.inventory.length; i ++)
									{
										if(this.inventory[i] != null)
										{
											if(this.inventory[i].isItemEqual(new ItemStack(this.MaterialList1.get(item).Blockerino.getBlock())))
											{
												PacketHandler.INSTANCE.sendToServer(new ReduceStackSizeFromTE(this.pos.getX(), this.pos.getY(), this.pos.getZ(), i));
												break;
											}
										}
									}

								}

								//if list is zero then do ending stuff and exit	

								UpdateMaterialList(this.MaterialList1.get(item).Blockerino);
								PlaceBlock();


							}else{
								//update material list

							}


						}else{

							//	System.out.println("Ill be over here");


						}

					}else{



						Structure.StructureReader.StructureReaderFromFile(new File(ClientProxy.dataDirectory, FileName));
						this.setFullBlock();
						FillMaterialList("", true); 

					}

				}else {
					this.BlockCoolDown--;
				}

			}
		}
	}

}
