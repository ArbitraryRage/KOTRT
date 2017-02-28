package com.waabbuffet.kotrt.generator;

import java.io.File;
import java.util.Random;

import com.waabbuffet.kotrt.blocks.KotrtBlocksHandler;
import com.waabbuffet.kotrt.entities.Kingdom.EntityShopKeeper;
import com.waabbuffet.kotrt.entities.Outpost.EntityOutpostBase;
import com.waabbuffet.kotrt.entities.Outpost.EntitySellShopKeeper;
import com.waabbuffet.kotrt.packet.PacketHandler;
import com.waabbuffet.kotrt.packet.structure.FinishStructure;
import com.waabbuffet.kotrt.proxy.ClientProxy;
import com.waabbuffet.kotrt.schematic.Structure;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBlock;

import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

public class KotrtGenerator implements IWorldGenerator {

	Random Rand = new Random();
	

	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		// TODO Auto-generated method stub
		
		
		int SmallestValueX = 250;
		int SmallestValueZ = 250;
		
		int posX = chunkX * 16 + random.nextInt(16);
		int posZ = chunkZ * 16 + random.nextInt(16);
		int posY = world.getTopSolidOrLiquidBlock(new BlockPos(posX, 0, posZ)).getY();
		if(posY == -1)
			return;

		
		
		if(!world.isRemote)	
		{
		
					int RandNumber = Rand.nextInt(50);
			
		
								
					if(RandNumber == 51 && this.CanSpawn(world))
					{
					

					Biome biome = world.getBiomeGenForCoords(new BlockPos(posX, posY, posZ));
				
					
					if(!biome.equals(Biomes.OCEAN) && !biome.equals(Biomes.RIVER) && !biome.equals(Biomes.JUNGLE) && !biome.equals(Biomes.DEEP_OCEAN))
					{
							if(world.getBlockState(new BlockPos(posX, posY - 1, posZ)).equals(biome.topBlock))
							{
								
									
									
									 Structure.StructureReader.StructureReaderFromFile(new File(ClientProxy.dataDirectory, "OutpostOne"));
									 
									 if(posY > 73)
										{
											
											return;
										}else{
											
											for(int i =0; i <ClientProxy.data.FullBlocks.length; i ++)
											{
												if(world.getTopSolidOrLiquidBlock(new BlockPos(posX + i, posY, posZ)).getY() < SmallestValueX) 
												{
													SmallestValueX = world.getTopSolidOrLiquidBlock(new BlockPos(posX + i, posY, posZ)).getY();
												}
											}
											
											for(int i =0; i < ClientProxy.data.FullBlocks[0][0].length; i ++)
											{
												if(world.getTopSolidOrLiquidBlock(new BlockPos(posX, posY, posZ + i)).getY() < SmallestValueZ) 
												{
													SmallestValueZ = world.getTopSolidOrLiquidBlock(new BlockPos(posX, posY, posZ + i)).getY();
												}
											}
											
											if(SmallestValueX <= SmallestValueZ)
											{
												if(posY - SmallestValueX <= 3)
													posY = SmallestValueX;
												else{
												
													return;
												}
											}else
											if(SmallestValueZ <= SmallestValueX)
											{
												if(posY - SmallestValueZ <= 3)
													posY = SmallestValueZ;
												else{
												
													return;
												}
											}
										}
										
										for(int x = 0; x < ClientProxy.data.FullBlocks.length; x ++)
										{
											for(int y =0; y < ClientProxy.data.FullBlocks[0].length; y ++)
											{
												for(int z =0; z < ClientProxy.data.FullBlocks[0][0].length; z ++)
												{
													
												
													world.setBlockState(new BlockPos(posX + x, posY + y, posZ + z), ClientProxy.data.FullBlocks[x][y][z]);
													
												}
											}
										
									}
									this.SpawnVillagers(posX, posY, posZ, world);
								
								//}
							}
							
						}
					}
				
			}
		
	}

	public boolean CanSpawn( World world)
	{
		boolean Yes = true;
		
		for(int i =0; i < world.loadedEntityList.size(); i ++)
		{
			if(world.loadedEntityList.get(i) instanceof EntityOutpostBase)
			{
				
				return false;
			}
		}
		
		return Yes;
	}
	
	
	public void SpawnVillagers(int posX, int posY, int posZ, World world)
	{
		
		EntityOutpostBase b21 = new EntityOutpostBase(world, "Europa Laurens", 14, true); //name, skin id, is guard
		b21.setPosition(posX + 37, posY + 1, posZ + 11);//1
		world.spawnEntityInWorld(b21);
		
		EntityOutpostBase b22 = new EntityOutpostBase(world, "Bacis Sireyjol", 15, true); //name, skin id, is guard
		b22.setPosition(posX + 33, posY + 1, posZ + 13);//1
		world.spawnEntityInWorld(b22);
		
		EntityOutpostBase b23 = new EntityOutpostBase(world, "Evemy Ertl", 14, true); //name, skin id, is guard
		b23.setPosition(posX +31, posY + 1, posZ + 22);//1
		world.spawnEntityInWorld(b23);
		
		EntityOutpostBase b24 = new EntityOutpostBase(world, "Ribald Rou", 15, true); //name, skin id, is guard
		b24.setPosition(posX + 66, posY + 1, posZ + 18);//1
		world.spawnEntityInWorld(b24);
		
		
		EntityOutpostBase b25 = new EntityOutpostBase(world, "Emery Lonard", 14, true); //name, skin id, is guard
		b25.setPosition(posX + 59, posY + 11, posZ + 32);//1
		world.spawnEntityInWorld(b25);
		
		EntityOutpostBase b26 = new EntityOutpostBase(world, "Agiwulf Kleber", 15, true); //name, skin id, is guard
		b26.setPosition(posX + 80, posY + 11, posZ + 41);//1
		world.spawnEntityInWorld(b26);
		
		EntityOutpostBase b27 = new EntityOutpostBase(world, "Saxo Froissart", 15, true); //name, skin id, is guard
		b27.setPosition(posX + 71, posY + 39, posZ + 50);//1
		world.spawnEntityInWorld(b27);
		
		
		TileEntityKingdomStructureBlock FinishProduct1 = new TileEntityKingdomStructureBlock();
		

		
		world.setBlockState(new BlockPos(posX + 39, posY + 40, posZ + 40), KotrtBlocksHandler.kingdom_structureblock.getDefaultState());
		world.setTileEntity(new BlockPos(posX + 38, posY + 40, posZ + 40), FinishProduct1);
		
		TileEntityKingdomStructureBlock FinishProduct = (TileEntityKingdomStructureBlock) world.getTileEntity(new BlockPos(posX + 39, posY + 40, posZ + 40));
		
		FinishProduct.structure.setName("OutpostOne");
		FinishProduct.structure.setDirection("East");
		FinishProduct.structure.setCurrentWorkers(0);
		
		FinishProduct.structure.setStartX(posX + 39);
		FinishProduct.structure.setStartY(posY + 40);
		FinishProduct.structure.setStartZ(posZ + 40);
		
		
		EntityOutpostBase b = new EntityOutpostBase(world, "Burel Sbokos", 1, false); //name, skin id, is guard
		b.setVillagerSkinID(1);
		b.setPosition(posX + 53, posY + 1, posZ + 25);//1
		world.spawnEntityInWorld(b);
		
		EntityOutpostBase b1 = new EntityOutpostBase(world, "Ysmena Hiemer", 2, false);
		b1.setPosition(posX + 86, posY + 2, posZ+ 24);//2
		world.spawnEntityInWorld(b1);
		
		EntityOutpostBase b2 = new EntityOutpostBase(world, "Hientje Kern", 3, false);
		b2.setPosition(posX + 76, posY+ 2, posZ+ 35); // 3
		world.spawnEntityInWorld(b2);
		
		EntityOutpostBase b3 = new EntityOutpostBase(world, "Meriet Horn", 4, false);
		b3.setPosition(posX + 73, posY + 1, posZ + 48); //4
		world.spawnEntityInWorld(b3);
		
		EntityOutpostBase b4 = new EntityOutpostBase(world, "Apal Sarria", 5, false);
		b4.setPosition(posX + 58, posY + 1, posZ + 47);
		world.spawnEntityInWorld(b4);
		
		EntityOutpostBase b5 = new EntityOutpostBase(world, "Huon Sonntag", 6, false);
		b5.setPosition(posX + 56, posY + 1, posZ + 34);
		world.spawnEntityInWorld(b5);
		
		EntityOutpostBase b7 = new EntityOutpostBase(world, "Taiaho Fleck", 7, false);
		b7.setPosition(posX + 65, posY + 1, posZ + 62);
		world.spawnEntityInWorld(b7);
		
		EntityOutpostBase b6 = new EntityOutpostBase(world, "Robin Capellari", 8, false);
		b6.setPosition(posX + 53, posY + 1, posZ + 63);
		world.spawnEntityInWorld(b6);
		
		EntityOutpostBase b8 = new EntityOutpostBase(world, "Alex", 9, false);
		b8.setPosition(posX + 60, posY + 11, posZ + 48);
		world.spawnEntityInWorld(b8);
		
		EntityOutpostBase b9 = new EntityOutpostBase(world, "Musaeus Wepper", 10, false);
		b9.setPosition(posX + 61, posY + 11, posZ + 19);
		world.spawnEntityInWorld(b9);
		
		EntityOutpostBase b10 = new EntityOutpostBase(world, "Jeromia Perrer", 11, false);
		b10.setPosition(posX + 73, posY + 11, posZ + 22);
		world.spawnEntityInWorld(b10);
		
		EntityOutpostBase b11 = new EntityOutpostBase(world, "Adela Haghio", 12, false);
		b11.setPosition(posX + 81, posY + 11, posZ + 34);
		world.spawnEntityInWorld(b11);
		
		EntityOutpostBase b13 = new EntityOutpostBase(world, "Elyne Lauzirika", 13, false);
		b13.setPosition(posX+ 71, posY + 11, posZ+ 32);
		world.spawnEntityInWorld(b13);
		
		EntityOutpostBase b14 = new EntityOutpostBase(world, "Lopene Thoms", 1, false);
		b14.setPosition(posX + 62, posY + 40, posZ + 35);
		world.spawnEntityInWorld(b14);
		
		EntityOutpostBase b15 = new EntityOutpostBase(world, "Usue Dragomir", 2, false);
		b15.setPosition(posX+ 62, posY + 40, posZ + 45);
		world.spawnEntityInWorld(b15);
		
		EntityOutpostBase b16 = new EntityOutpostBase(world, "Monima Ivanov", 3, false);
		b16.setPosition(posX + 67, posY + 40, posZ + 40);
		world.spawnEntityInWorld(b16);
		
		EntityOutpostBase b17 = new EntityOutpostBase(world, "Wlfildis Cottaz", 4, false);
		b17.setPosition(posX + 46, posY + 40, posZ + 40);
		world.spawnEntityInWorld(b17);
		
		EntityOutpostBase b18 = new EntityOutpostBase(world, "Madhalberta Boon", 5, false);
		b18.setPosition(posX + 58, posY + 1, posZ + 14);
		world.spawnEntityInWorld(b18); 
		
		EntityOutpostBase b19 = new EntityOutpostBase(world, "Bartje Fiacconi", 6, false);
		b19.setPosition(posX + 83, posY + 1, posZ + 29);
		world.spawnEntityInWorld(b19);
		
		EntityShopKeeper b20 = new EntityShopKeeper(world);
		b20.setPosition(posX + 27, posY + 1, posZ + 43);
		world.spawnEntityInWorld(b20);
		
		EntitySellShopKeeper b28 = new EntitySellShopKeeper(world);
		b28.setPosition(posX + 48, posY + 1, posZ + 68);
		world.spawnEntityInWorld(b28);
		
		//-361 60 -505 - > -334 60 -462
		//-361 60 -505 - > -315 60 -437
		
		//Maybe 30 Entities
		//-266 5 620 
		
		//-266 5 620 - > -237 5 642
		// -266 5 620 -> -213 5 645
		//-266 5 620 -> -180 6 644
		//-266 5 620  - > -190 6 655
		//-266 5 620  - > -193 5 668
		//-266 5 620 -> -208 5 667
		//-266 5 620  - > -210 5 654
		//-266 5 620  - > -201 5 682
		//-266 5 620  - > -213 5 683
		// -266 5 620 -> -206 15 668
		//-266 5 620  - > -205 15 639
		//-266 5 620  - > -193 15 642
		//-266 5 620  - > -185 15 654
		//-266 5 620 - > -195 15 662
		//-266 5 620 -> -204 44 655
		//-266 5 620  - > -204 44 665
		//-266 5 620 -> -199 44 660
		//-266 5 620  - > -220 44 660
		//ShopKeeperCd: -266 5 620 -> -231 5 655
		//-266 5 620 -> -208 5 634
		//-266 5 620 -> -183 5 649
		
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		// TODO Auto-generated method stub
		
	}

}
