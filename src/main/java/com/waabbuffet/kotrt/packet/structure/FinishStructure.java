package com.waabbuffet.kotrt.packet.structure;

import java.io.File;
import java.util.ConcurrentModificationException;

import com.waabbuffet.kotrt.blocks.KotrtBlocksHandler;
import com.waabbuffet.kotrt.entities.Kingdom.EntityCathedral;
import com.waabbuffet.kotrt.entities.Kingdom.EntityFarmer;
import com.waabbuffet.kotrt.entities.Kingdom.EntityRecruiter;
import com.waabbuffet.kotrt.entities.Kingdom.EntityShopKeeper;
import com.waabbuffet.kotrt.entities.Kingdom.barracks.EntityBarracks;
import com.waabbuffet.kotrt.entities.Outpost.EntitySellShopKeeper;
import com.waabbuffet.kotrt.proxy.ClientProxy;
import com.waabbuffet.kotrt.schematic.Structure;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBlock;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBuilderBlock;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class FinishStructure implements IMessage, IMessageHandler<FinishStructure, IMessage> {

	

	public int BlockX, BlockY, BlockZ, TeX, TeY, TeZ;
	public String BuildingDir, FileName;
	
	
	
	public FinishStructure(){ }
	
	
	public FinishStructure(int TEX, int TEY, int TEZ)
	{
		this.BuildingDir = "You son of a";
		this.FileName = "dylan";
		
		
		this.TeX = TEX;
		this.TeY = TEY;
		this.TeZ = TEZ;
	}
	
	public FinishStructure(int blockX, int blockY, int blockZ, int TEX, int TEY, int TEZ, String BuildingDir, String FileName){
		

		this.BuildingDir = BuildingDir;
		this.FileName = FileName;
		this.BlockX = blockX;
		this.BlockY = blockY;
		this.BlockZ = blockZ;
		
		this.TeX = TEX;
		this.TeY = TEY;
		this.TeZ = TEZ;
	}
	
	
	
	
	@Override
	public IMessage onMessage(FinishStructure message, MessageContext ctx) throws ConcurrentModificationException{
		
		EntityPlayer player = ctx.getServerHandler().playerEntity;
		World world = ctx.getServerHandler().playerEntity.worldObj;
			
		
		
			if(message.BlockX != 0 && message.BlockY != 0 && message.BlockZ != 0)
			{
				TileEntityKingdomStructureBuilderBlock b = (TileEntityKingdomStructureBuilderBlock) world.getTileEntity(new BlockPos(message.TeX, message.TeY, message.TeZ));
				
				if(b != null)
				{
					Structure.StructureReader.StructureReaderFromFile(new File(ClientProxy.dataDirectory, message.FileName));
					b.setFullBlock();
					
					//have to place stairs right too 
					
					if(message.BuildingDir.contains("East"))
					{
						
						for(int x = 0; x < b.FullBlocks.length; x ++)
						{
							for(int y =0; y < b.FullBlocks[0].length; y ++)
							{
								for(int z =0; z < b.FullBlocks[0][0].length; z ++)
								{
									
									if(!world.getBlockState(new BlockPos(message.BlockX + x, message.BlockY + y, message.BlockZ + z)).equals(b.FullBlocks[x][y][z]))
									{
										world.setBlockState(new BlockPos(message.BlockX + x, message.BlockY + y, message.BlockZ + z), b.FullBlocks[x][y][z]);
									}
								}
							}
						}
					}else if(message.BuildingDir.contains("West"))
					{
						
						
		
						for(int x = 0; x < b.FullBlocks.length; x ++)
						{
							for(int y =0; y < b.FullBlocks[0].length; y ++)
							{
								for(int z =0; z < b.FullBlocks[0][0].length; z ++)
								{
									
									if(!world.getBlockState(new BlockPos(message.BlockX - x, message.BlockY + y, message.BlockZ - z)).equals(b.FullBlocks[x][y][z]))
									{
										
										
										if(b.FullBlocks[x][y][z].toString().contains("stairs") || b.FullBlocks[x][y][z].toString().contains("torch") || b.FullBlocks[x][y][z].toString().contains("ladder") )
										{
											
												
												// 0 = east
												// 1 = west
												// 2 = south
												// 3 = north
												if(b.FullBlocks[x][y][z].getBlock().getMetaFromState(b.FullBlocks[x][y][z]) == 1)
												{
													b.FullBlocks[x][y][z] = b.FullBlocks[x][y][z].getBlock().getStateFromMeta(0);
													
												
												}else if(b.FullBlocks[x][y][z].getBlock().getMetaFromState(b.FullBlocks[x][y][z]) == 0)
												{
													b.FullBlocks[x][y][z] = b.FullBlocks[x][y][z].getBlock().getStateFromMeta(1);
													
												}else
												
												if(b.FullBlocks[x][y][z].getBlock().getMetaFromState(b.FullBlocks[x][y][z]) == 5)
												{
													b.FullBlocks[x][y][z] = b.FullBlocks[x][y][z].getBlock().getStateFromMeta(4);
													
												}else if(b.FullBlocks[x][y][z].getBlock().getMetaFromState(b.FullBlocks[x][y][z]) == 4)
												{
													b.FullBlocks[x][y][z] = b.FullBlocks[x][y][z].getBlock().getStateFromMeta(5);
													
												}else
												
												
												
												if(b.FullBlocks[x][y][z].getBlock().getMetaFromState(b.FullBlocks[x][y][z]) == 2)
												{
													b.FullBlocks[x][y][z] = b.FullBlocks[x][y][z].getBlock().getStateFromMeta(3);
													
												}else if(b.FullBlocks[x][y][z].getBlock().getMetaFromState(b.FullBlocks[x][y][z]) == 3)
												{
													b.FullBlocks[x][y][z] = b.FullBlocks[x][y][z].getBlock().getStateFromMeta(2);
													
												}else
												
												if(b.FullBlocks[x][y][z].getBlock().getMetaFromState(b.FullBlocks[x][y][z]) == 6)
												{
													b.FullBlocks[x][y][z] = b.FullBlocks[x][y][z].getBlock().getStateFromMeta(7);
													
												}else if(b.FullBlocks[x][y][z].getBlock().getMetaFromState(b.FullBlocks[x][y][z]) == 7)
												{
													b.FullBlocks[x][y][z] = b.FullBlocks[x][y][z].getBlock().getStateFromMeta(6);
													
												}
											}
										
										world.setBlockState(new BlockPos(message.BlockX - x, message.BlockY + y, message.BlockZ - z), b.FullBlocks[x][y][z]);
									}
								}
							}
						}
					}else if(message.BuildingDir.contains("North"))
					{
						//North - > west  (done)
						// South - > east 
						//East - > north for sure (Done)
						//west - > south
						
						for(int x = 0; x < b.FullBlocks.length; x ++)
						{
							for(int y =0; y < b.FullBlocks[0].length; y ++)
							{
								for(int z =0; z < b.FullBlocks[0][0].length; z ++)
								{
									
									if(!world.getBlockState(new BlockPos(message.BlockX + z, message.BlockY + y, message.BlockZ - x)).equals(b.FullBlocks[x][y][z]))
									{
										
										
										if(b.FullBlocks[x][y][z].toString().contains("stairs") || b.FullBlocks[x][y][z].toString().contains("torch") || b.FullBlocks[x][y][z].toString().contains("ladder") )
										{
											
											
												// 0 = east 4
												// 1 = west 5 
												// 2 = south 6 
												// 3 = north 7
												if(b.FullBlocks[x][y][z].getBlock().getMetaFromState(b.FullBlocks[x][y][z]) == 1)
												{
													b.FullBlocks[x][y][z] = b.FullBlocks[x][y][z].getBlock().getStateFromMeta(2);
													
												
												}else if(b.FullBlocks[x][y][z].getBlock().getMetaFromState(b.FullBlocks[x][y][z]) == 2)
												{
													b.FullBlocks[x][y][z] = b.FullBlocks[x][y][z].getBlock().getStateFromMeta(0);
													
												}else
												
												if(b.FullBlocks[x][y][z].getBlock().getMetaFromState(b.FullBlocks[x][y][z]) == 5)
												{
													b.FullBlocks[x][y][z] = b.FullBlocks[x][y][z].getBlock().getStateFromMeta(6);
													
												}else if(b.FullBlocks[x][y][z].getBlock().getMetaFromState(b.FullBlocks[x][y][z]) == 6)
												{
													b.FullBlocks[x][y][z] = b.FullBlocks[x][y][z].getBlock().getStateFromMeta(4);
													
												}else
												
												
												
												if(b.FullBlocks[x][y][z].getBlock().getMetaFromState(b.FullBlocks[x][y][z]) == 0)
												{
													b.FullBlocks[x][y][z] = b.FullBlocks[x][y][z].getBlock().getStateFromMeta(3);
													
												}else if(b.FullBlocks[x][y][z].getBlock().getMetaFromState(b.FullBlocks[x][y][z]) == 3)
												{
													b.FullBlocks[x][y][z] = b.FullBlocks[x][y][z].getBlock().getStateFromMeta(1);
													
												}else
												
												if(b.FullBlocks[x][y][z].getBlock().getMetaFromState(b.FullBlocks[x][y][z]) == 4)
												{
													b.FullBlocks[x][y][z] = b.FullBlocks[x][y][z].getBlock().getStateFromMeta(7);
													
												}else if(b.FullBlocks[x][y][z].getBlock().getMetaFromState(b.FullBlocks[x][y][z]) == 7)
												{
													b.FullBlocks[x][y][z] = b.FullBlocks[x][y][z].getBlock().getStateFromMeta(5);
													
												}
												
											}
										
										world.setBlockState(new BlockPos(message.BlockX + z, message.BlockY + y, message.BlockZ - x), b.FullBlocks[x][y][z]);
									}
								}
							}
						}
					}else if(message.BuildingDir.contains("South"))
					{
						//North - > west  (done)
						// South - > east 
						//East - > north for sure (Done)
						//west - > south
						
						for(int x = 0; x < b.FullBlocks.length; x ++)
						{
							for(int y =0; y < b.FullBlocks[0].length; y ++)
							{
								for(int z =0; z < b.FullBlocks[0][0].length; z ++)
								{
									
									if(!world.getBlockState(new BlockPos(message.BlockX - z, message.BlockY + y, message.BlockZ + x)).equals(b.FullBlocks[x][y][z]))
									{
										
										
										if(b.FullBlocks[x][y][z].toString().contains("stairs") || b.FullBlocks[x][y][z].toString().contains("torch") || b.FullBlocks[x][y][z].toString().contains("ladder") )
										{
											
											
												// 0 = east 4
												// 1 = west 5 
												// 2 = south 6 
												// 3 = north 7
												if(b.FullBlocks[x][y][z].getBlock().getMetaFromState(b.FullBlocks[x][y][z]) == 2)
												{
													b.FullBlocks[x][y][z] = b.FullBlocks[x][y][z].getBlock().getStateFromMeta(1);
													
												
												}else if(b.FullBlocks[x][y][z].getBlock().getMetaFromState(b.FullBlocks[x][y][z]) == 0)
												{
													b.FullBlocks[x][y][z] = b.FullBlocks[x][y][z].getBlock().getStateFromMeta(2);
													
												}else
												
												if(b.FullBlocks[x][y][z].getBlock().getMetaFromState(b.FullBlocks[x][y][z]) == 6)
												{
													b.FullBlocks[x][y][z] = b.FullBlocks[x][y][z].getBlock().getStateFromMeta(5);
													
												}else if(b.FullBlocks[x][y][z].getBlock().getMetaFromState(b.FullBlocks[x][y][z]) == 4)
												{
													b.FullBlocks[x][y][z] = b.FullBlocks[x][y][z].getBlock().getStateFromMeta(6);
													
												}else
												
												
												
												if(b.FullBlocks[x][y][z].getBlock().getMetaFromState(b.FullBlocks[x][y][z]) == 3)
												{
													b.FullBlocks[x][y][z] = b.FullBlocks[x][y][z].getBlock().getStateFromMeta(0);
													
												}else if(b.FullBlocks[x][y][z].getBlock().getMetaFromState(b.FullBlocks[x][y][z]) == 1)
												{
													b.FullBlocks[x][y][z] = b.FullBlocks[x][y][z].getBlock().getStateFromMeta(3);
													
												}else
												
												if(b.FullBlocks[x][y][z].getBlock().getMetaFromState(b.FullBlocks[x][y][z]) == 7)
												{
													b.FullBlocks[x][y][z] = b.FullBlocks[x][y][z].getBlock().getStateFromMeta(4);
													
												}else if(b.FullBlocks[x][y][z].getBlock().getMetaFromState(b.FullBlocks[x][y][z]) == 5)
												{
													b.FullBlocks[x][y][z] = b.FullBlocks[x][y][z].getBlock().getStateFromMeta(7);
													
												}
												
											}
										
										world.setBlockState(new BlockPos(message.BlockX - z, message.BlockY + y, message.BlockZ + x), b.FullBlocks[x][y][z]);
									}
								}
							
							}
						}
					}
						
					
				}
				
				//Place builder block with specific info
				TileEntityKingdomStructureBlock FinishProduct = new TileEntityKingdomStructureBlock();
				
				
			/*	
				FinishProduct.structure.setName(message.FileName);
				FinishProduct.structure.setDirection(message.BuildingDir);
				FinishProduct.structure.setCurrentWorkers(0);
				
				FinishProduct.structure.setStartX(message.BlockX);
				FinishProduct.structure.setStartY(message.BlockY);
				FinishProduct.structure.setStartZ(message.BlockZ);
				*/
				BlockPos b1 = this.GetTEPos(message.FileName, message.BlockX, message.BlockY, message.BlockZ, message.BuildingDir, world);
				
				world.setTileEntity(b1, FinishProduct);
				world.setBlockState(b1, KotrtBlocksHandler.kingdom_structureblock.getDefaultState());
				
				TileEntityKingdomStructureBlock FinishProduct1 = (TileEntityKingdomStructureBlock) world.getTileEntity(b1);
				FinishProduct1.structure.setName(message.FileName);
				FinishProduct1.structure.setDirection(message.BuildingDir);
				FinishProduct1.structure.setCurrentWorkers(0);
				
				FinishProduct1.structure.setStartX(message.BlockX);
				FinishProduct1.structure.setStartY(message.BlockY);
				FinishProduct1.structure.setStartZ(message.BlockZ);
				
				b.StartConstruction = false;
				b.FileName = null;
				b.BuildingDirection = "East";
			}else if(message.FileName.contains("dylan"))
			{
				EntityFarmer b = new EntityFarmer(world);
				TileEntityKingdomStructureBlock b2 = (TileEntityKingdomStructureBlock) world.getTileEntity(new BlockPos(message.TeX, message.TeY, message.TeZ));
				
				
				
				BlockPos b1 = b2.GetWorkPlaceLocationsBasedOnName(b2.structure.getName(), b2.structure.getStartX(), b2.structure.getStartY(), b2.structure.getStartZ(), b2.structure.getCurrentWorkers(), b2.structure.getDirection());
				b2.structure.AddOneToWorker();
				
				b.setPosition(b1.getX(), b1.getY(), b1.getZ());
				
				world.spawnEntityInWorld(b);
			}
		
	
		
	
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
	
		this.FileName = ByteBufUtils.readUTF8String(buf);
		this.BuildingDir = ByteBufUtils.readUTF8String(buf);
		
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
	
		ByteBufUtils.writeUTF8String(buf, FileName);
		ByteBufUtils.writeUTF8String(buf, BuildingDir);
		
		buf.writeInt(BlockX);
		buf.writeInt(BlockY);
		buf.writeInt(BlockZ);
		
		
		buf.writeInt(TeX);
		buf.writeInt(TeY);
		buf.writeInt(TeZ);
		
		
	}
	//530, 7, -1230
	public BlockPos GetTEPos(String Name, int startX, int startY, int startZ, String Dir, World world)
	{
		BlockPos WhereToPlace = new BlockPos(startX, startY, startZ);
		
		if(Dir.contains("East"))
		{
			if(Name.contains("Town Hall"))
			{
				// 460 8 -1727 -> 470 17 -1703
				EntityRecruiter b = new EntityRecruiter(world);
				b.setPosition(startX + 10, startY + 10, startZ + 25);
				world.spawnEntityInWorld(b);
				
				
				WhereToPlace = new BlockPos(startX + 10, startY + 10, startZ+ 24);
				
			}else if(Name.contains("Miners Guild"))
			{
				// 540 7 -1681 - > 559 9 -1668
				
			
				WhereToPlace = new BlockPos(startX + 19, startY + 2, startZ+ 13);
			
				
			}else if(Name.contains("Lumberjack Guild"))
			{
				//460 8 -1653 -->> 491 12, -1644
				
				WhereToPlace = new BlockPos(startX + 31, startY + 5, startZ +9);
			}else if(Name.contains("Small House"))
			{
				//460 8 -1620 --> 472 17 -1615
			
				WhereToPlace = new BlockPos(startX + 12, startY + 9, startZ +5);
				
			}else if(Name.contains("Shopkeeper Guild"))
			{
				//460 8 -1584 --> 479 16 -1561
				
				//810 6 -454 - > -796 9 -447
				//810 6 -454 - > -796 9 -443
				
				EntityShopKeeper b = new EntityShopKeeper(world);
				b.setPosition(startX + 14, startY + 5, startZ + 11);
				world.spawnEntityInWorld(b);
				
				EntitySellShopKeeper b1 = new EntitySellShopKeeper(world);
				b1.setPosition(startX + 14, startY + 5, startZ + 11);
				world.spawnEntityInWorld(b1);
				
				WhereToPlace = new BlockPos(startX + 19, startY + 9, startZ+ 23);
				
				
				
			}else if(Name.contains("Wheat Farmer Guild"))
			{
				//530, 8, 1239 - > 534, 8, 1228
				WhereToPlace.add(4, 1, -11);
				WhereToPlace = new BlockPos(startX + 4, startY + 1, startZ + 11);
			}else if(Name.contains("Carpenter"))
			{
				//460 8 -1492 - >> 480 11 -1474
				
				WhereToPlace = new BlockPos(startX + 20, startY + 3, startZ+ 18);
			}else if(Name.contains("Blacksmith"))
			{
				
				WhereToPlace = new BlockPos(startX + 17, startY + 2, startZ+ 18);
				
			}else if(Name.contains("Storage Hut"))
			{
				//-970 5 1491 - > -944 7 1518
				WhereToPlace = new BlockPos(startX + 26, startY + 2, startZ+ 27);
			}else if(Name.contains("Messanger Guild"))
			{
				//-870 5 -314 -> -864 10 -310
			
				WhereToPlace = new BlockPos(startX + 6, startY + 7, startZ + 4);
			}else if(Name.contains("Exotic Farmers guild"))
			{
				//460 8 -1310 -> 474 9 -1302
			
				WhereToPlace = new BlockPos(startX + 14, startY + 2, startZ + 8);
			}else if(Name.contains("Builders Guild"))
			{
				//-780 5 -396 - > -767 5 -384
			
				WhereToPlace = new BlockPos(startX + 13, startY, startZ + 12);
			}else if(Name.contains("Barracks"))
			{
				//-1063 4 -679 -> -1014 19 -634
				//-1063 4 -679 -> -1040 6 -654
				EntityBarracks b = new EntityBarracks(world);
				b.setPosition(startX + 23, startY + 3, startZ + 25);
				world.spawnEntityInWorld(b);
			
				WhereToPlace = new BlockPos(startX + 49, startY + 15, startZ + 45);
			}else if(Name.contains("Cathedral"))
			{
				//-966 4 -589 - > -926 28 -567
			
				//-966 4 -589 - > -917 8 -567
				
				EntityCathedral b = new EntityCathedral(world);
				b.setPosition(startX + 49, startY + 9, startZ + 22);
				world.spawnEntityInWorld(b);
				
				WhereToPlace = new BlockPos(startX + 40, startY + 24, startZ + 22);
			}
			
			
		
						
		}else if(Dir.contains("West"))
		{
			if(Name.contains("Town Hall"))
			{
				// 460 8 -1727 -> 470 17 -1703
				EntityRecruiter b = new EntityRecruiter(world);
				b.setPosition(startX - 10, startY + 10, startZ - 25);
				world.spawnEntityInWorld(b);
				
				
				WhereToPlace = new BlockPos(startX - 10, startY + 10, startZ - 24);
				
			}else if(Name.contains("Miners Guild"))
			{
				// 540 7 -1681 - > 559 9 -1668
				
			
				WhereToPlace = new BlockPos(startX - 19, startY + 2, startZ - 13);
			
				
			}else if(Name.contains("Lumberjack Guild"))
			{
				//460 8 -1653 -->> 491 12, -1644
				
				WhereToPlace = new BlockPos(startX - 31, startY + 5, startZ -9);
			}else if(Name.contains("Small House"))
			{
				//460 8 -1620 --> 472 17 -1615
			
				WhereToPlace = new BlockPos(startX - 12, startY + 9, startZ -5);
				
			}else if(Name.contains("Shopkeeper Guild"))
			{
				//460 8 -1584 --> 479 16 -1561
				
				//810 6 -454 - > -796 9 -447
				//810 6 -454 - > -796 9 -443
				
				EntityShopKeeper b = new EntityShopKeeper(world);
				b.setPosition(startX - 14, startY + 5, startZ - 11);
				world.spawnEntityInWorld(b);
				
				EntitySellShopKeeper b1 = new EntitySellShopKeeper(world);
				b1.setPosition(startX - 14, startY + 5, startZ - 11);
				world.spawnEntityInWorld(b1);
				
				WhereToPlace = new BlockPos(startX - 19, startY + 9, startZ - 23);
				
				
				
			}else if(Name.contains("Wheat Farmer Guild"))
			{
				//530, 8, 1239 - > 534, 8, 1228
				WhereToPlace.add(4, 1, -11);
				WhereToPlace = new BlockPos(startX - 4, startY + 1, startZ - 11);
			}else if(Name.contains("Carpenter"))
			{
				//460 8 -1492 - >> 480 11 -1474
				
				WhereToPlace = new BlockPos(startX - 20, startY + 3, startZ - 18);
			}else if(Name.contains("Blacksmith"))
			{
				
				WhereToPlace = new BlockPos(startX - 17, startY + 2, startZ - 18);
				
			}else if(Name.contains("Storage Hut"))
			{
				//-970 5 1491 - > -944 7 1518
				WhereToPlace = new BlockPos(startX - 26, startY + 2, startZ - 27);
			}else if(Name.contains("Messanger Guild"))
			{
				//-870 5 -314 -> -864 10 -310
			
				WhereToPlace = new BlockPos(startX - 6, startY + 7, startZ - 4);
			}else if(Name.contains("Exotic Farmers guild"))
			{
				//460 8 -1310 -> 474 9 -1302
			
				WhereToPlace = new BlockPos(startX - 14, startY + 2, startZ - 8);
			}else if(Name.contains("Builders Guild"))
			{
				//-780 5 -396 - > -767 5 -384
			
				WhereToPlace = new BlockPos(startX - 13, startY, startZ - 12);
			}else if(Name.contains("Barracks"))
			{
				//-1063 4 -679 -> -1014 19 -634
				//-1063 4 -679 -> -1040 6 -654
				EntityBarracks b = new EntityBarracks(world);
				b.setPosition(startX - 23, startY + 3, startZ - 25);
				world.spawnEntityInWorld(b);
			
				WhereToPlace = new BlockPos(startX - 49, startY + 15, startZ - 45);
			}else if(Name.contains("Cathedral"))
			{
				//-966 4 -589 - > -926 28 -567
			
				//-966 4 -589 - > -917 8 -567
				
				EntityCathedral b = new EntityCathedral(world);
				b.setPosition(startX - 49, startY + 9, startZ - 22);
				world.spawnEntityInWorld(b);
				
				WhereToPlace = new BlockPos(startX - 40, startY + 24, startZ - 22);
			}
		}else if(Dir.contains("North"))
		{
			if(Name.contains("Town Hall"))
			{
				// 460 8 -1727 -> 470 17 -1703
				EntityRecruiter b = new EntityRecruiter(world);
				b.setPosition(startX + 25, startY + 10, startZ - 10);
				world.spawnEntityInWorld(b);
				
				WhereToPlace = new BlockPos(startX + 24, startY + 10, startZ - 10);
			//	WhereToPlace = new BlockPos(startX + 10, startY + 10, startZ+ 24);
				
			}else if(Name.contains("Miners Guild"))
			{
				// 540 7 -1681 - > 559 9 -1668
				
				WhereToPlace = new BlockPos(startX + 13, startY + 2, startZ - 19);
			//	WhereToPlace = new BlockPos(startX + 19, startY + 2, startZ+ 13);
			
				
			}else if(Name.contains("Lumberjack Guild"))
			{
				//460 8 -1653 -->> 491 12, -1644
				WhereToPlace = new BlockPos(startX + 9, startY + 5, startZ - 31);
			//	WhereToPlace = new BlockPos(startX + 31, startY + 5, startZ +9);
			}else if(Name.contains("Small House"))
			{
				//460 8 -1620 --> 472 17 -1615
				WhereToPlace = new BlockPos(startX + 5, startY + 9, startZ - 12);
			//	WhereToPlace = new BlockPos(startX + 12, startY + 9, startZ +5);
				
			}else if(Name.contains("Shopkeeper Guild"))
			{
				//460 8 -1584 --> 479 16 -1561
				
				//810 6 -454 - > -796 9 -447
				//810 6 -454 - > -796 9 -443
				
				EntityShopKeeper b = new EntityShopKeeper(world);
				b.setPosition(startX + 11, startY + 5, startZ - 14);
				world.spawnEntityInWorld(b);
			
				EntitySellShopKeeper b1 = new EntitySellShopKeeper(world);
				b1.setPosition(startX + 11, startY + 5, startZ - 14);
				world.spawnEntityInWorld(b1);
				
				WhereToPlace = new BlockPos(startX + 23, startY + 9, startZ - 19);
		//		WhereToPlace = new BlockPos(startX + 19, startY + 9, startZ+ 23);
				
				
				
			}else if(Name.contains("Wheat Farmer Guild"))
			{
				//530, 8, 1239 - > 534, 8, 1228
				WhereToPlace = new BlockPos(startX + 11, startY + 1, startZ - 4);	
			//	WhereToPlace = new BlockPos(startX + 4, startY + 1, startZ + 11);
			}else if(Name.contains("Carpenter"))
			{
				//460 8 -1492 - >> 480 11 -1474
				
				WhereToPlace = new BlockPos(startX + 18, startY + 3, startZ - 20);
			//	WhereToPlace = new BlockPos(startX + 20, startY + 3, startZ+ 18);
			}else if(Name.contains("Blacksmith"))
			{
				WhereToPlace = new BlockPos(startX + 18, startY + 2, startZ - 17);
			//	WhereToPlace = new BlockPos(startX + 17, startY + 2, startZ+ 18);
				
			}else if(Name.contains("Storage Hut"))
			{
				//-970 5 1491 - > -944 7 1518
				WhereToPlace = new BlockPos(startX + 27, startY + 2, startZ - 26);
			//	WhereToPlace = new BlockPos(startX + 26, startY + 2, startZ+ 27);
			}else if(Name.contains("Messanger Guild"))
			{
				//-870 5 -314 -> -864 10 -310
				WhereToPlace = new BlockPos(startX + 4, startY + 7, startZ - 6);
		//		WhereToPlace = new BlockPos(startX + 6, startY + 7, startZ + 4);
			}else if(Name.contains("Exotic Farmers guild"))
			{
				//460 8 -1310 -> 474 9 -1302
				WhereToPlace = new BlockPos(startX + 8, startY + 2, startZ - 14);
			//	WhereToPlace = new BlockPos(startX + 14, startY + 2, startZ + 8);
			}else if(Name.contains("Builders Guild"))
			{
				//-780 5 -396 - > -767 5 -384
				WhereToPlace = new BlockPos(startX + 12, startY, startZ - 13);
			//	WhereToPlace = new BlockPos(startX + 13, startY, startZ + 12);
			}else if(Name.contains("Barracks"))
			{
				//-1063 4 -679 -> -1014 19 -634
				//-1063 4 -679 -> -1040 6 -654
				EntityBarracks b = new EntityBarracks(world);
				b.setPosition(startX + 25, startY + 3, startZ - 23);
				world.spawnEntityInWorld(b);
			
				WhereToPlace = new BlockPos(startX + 45, startY + 15, startZ - 49);
			}else if(Name.contains("Cathedral"))
			{
				//-966 4 -589 - > -926 28 -567
			
				//-966 4 -589 - > -917 8 -567
				
				EntityCathedral b = new EntityCathedral(world);
				b.setPosition(startX + 22, startY + 9, startZ - 49);
				world.spawnEntityInWorld(b);
				
				WhereToPlace = new BlockPos(startX + 22, startY + 24, startZ - 40);
			}
			
			
		}else if(Dir.contains("South"))
		{
			if(Name.contains("Town Hall"))
			{
				// 460 8 -1727 -> 470 17 -1703
				EntityRecruiter b = new EntityRecruiter(world);
				b.setPosition(startX - 25, startY + 10, startZ + 10);
				world.spawnEntityInWorld(b);
				
				WhereToPlace = new BlockPos(startX - 24, startY + 10, startZ + 10);
			//	WhereToPlace = new BlockPos(startX + 10, startY + 10, startZ+ 24);
				
			}else if(Name.contains("Miners Guild"))
			{
				// 540 7 -1681 - > 559 9 -1668
				
				WhereToPlace = new BlockPos(startX - 13, startY + 2, startZ + 19);
			//	WhereToPlace = new BlockPos(startX + 19, startY + 2, startZ+ 13);
			
				
			}else if(Name.contains("Lumberjack Guild"))
			{
				//460 8 -1653 -->> 491 12, -1644
				WhereToPlace = new BlockPos(startX - 9, startY + 5, startZ + 31);
			//	WhereToPlace = new BlockPos(startX + 31, startY + 5, startZ +9);
			}else if(Name.contains("Small House"))
			{
				//460 8 -1620 --> 472 17 -1615
				WhereToPlace = new BlockPos(startX - 5, startY + 9, startZ + 12);
			//	WhereToPlace = new BlockPos(startX + 12, startY + 9, startZ +5);
				
			}else if(Name.contains("Shopkeeper Guild"))
			{
				//460 8 -1584 --> 479 16 -1561
				
				//810 6 -454 - > -796 9 -447
				//810 6 -454 - > -796 9 -443
				
				EntityShopKeeper b = new EntityShopKeeper(world);
				b.setPosition(startX - 11, startY + 5, startZ + 14);
				world.spawnEntityInWorld(b);
				
				EntitySellShopKeeper b1 = new EntitySellShopKeeper(world);
				b1.setPosition(startX - 11, startY + 5, startZ + 14);
				world.spawnEntityInWorld(b1);
			
				
				WhereToPlace = new BlockPos(startX - 23, startY + 9, startZ + 19);
		//		WhereToPlace = new BlockPos(startX + 19, startY + 9, startZ+ 23);
				
				
				
			}else if(Name.contains("Wheat Farmer Guild"))
			{
				//530, 8, 1239 - > 534, 8, 1228
				WhereToPlace = new BlockPos(startX - 11, startY + 1, startZ + 4);	
			//	WhereToPlace = new BlockPos(startX + 4, startY + 1, startZ + 11);
			}else if(Name.contains("Carpenter"))
			{
				//460 8 -1492 - >> 480 11 -1474
				
				WhereToPlace = new BlockPos(startX - 18, startY + 3, startZ + 20);
			//	WhereToPlace = new BlockPos(startX + 20, startY + 3, startZ+ 18);
			}else if(Name.contains("Blacksmith"))
			{
				WhereToPlace = new BlockPos(startX - 18, startY + 2, startZ + 17);
			//	WhereToPlace = new BlockPos(startX + 17, startY + 2, startZ+ 18);
				
			}else if(Name.contains("Storage Hut"))
			{
				//-970 5 1491 - > -944 7 1518
				WhereToPlace = new BlockPos(startX - 27, startY + 2, startZ + 26);
			//	WhereToPlace = new BlockPos(startX + 26, startY + 2, startZ+ 27);
			}else if(Name.contains("Messanger Guild"))
			{
				//-870 5 -314 -> -864 10 -310
				WhereToPlace = new BlockPos(startX - 4, startY + 7, startZ + 6);
		//		WhereToPlace = new BlockPos(startX + 6, startY + 7, startZ + 4);
			}else if(Name.contains("Exotic Farmers guild"))
			{
				//460 8 -1310 -> 474 9 -1302
				WhereToPlace = new BlockPos(startX - 8, startY + 2, startZ + 14);
			//	WhereToPlace = new BlockPos(startX + 14, startY + 2, startZ + 8);
			}else if(Name.contains("Builders Guild"))
			{
				//-780 5 -396 - > -767 5 -384
				WhereToPlace = new BlockPos(startX - 12, startY, startZ + 13);
			//	WhereToPlace = new BlockPos(startX + 13, startY, startZ + 12);
			}else if(Name.contains("Barracks"))
			{
				//-1063 4 -679 -> -1014 19 -634
				//-1063 4 -679 -> -1040 6 -654
				EntityBarracks b = new EntityBarracks(world);
				b.setPosition(startX - 25, startY + 3, startZ + 23);
				world.spawnEntityInWorld(b);
			
				WhereToPlace = new BlockPos(startX - 45, startY + 15, startZ + 49);
			}else if(Name.contains("Cathedral"))
			{
				//-966 4 -589 - > -926 28 -567
			
				//-966 4 -589 - > -917 8 -567
				
				EntityCathedral b = new EntityCathedral(world);
				b.setPosition(startX - 22, startY + 9, startZ + 49);
				world.spawnEntityInWorld(b);
				
				WhereToPlace = new BlockPos(startX - 22, startY + 24, startZ + 40);
			}
			
			
		}
		
		return WhereToPlace;
	}

}
