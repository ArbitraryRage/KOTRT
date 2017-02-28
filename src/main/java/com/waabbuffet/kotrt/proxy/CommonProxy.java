package com.waabbuffet.kotrt.proxy;

import java.io.File;
import java.io.IOException;

import com.waabbuffet.kotrt.blocks.KotrtBlocksHandler;
import com.waabbuffet.kotrt.entities.KotrtEntityHandler;
import com.waabbuffet.kotrt.entities.Kingdom.EntityFarmer;
import com.waabbuffet.kotrt.items.KotrtItemsHandler;
import com.waabbuffet.kotrt.packet.PacketHandler;
import com.waabbuffet.kotrt.references.SimpleReferences;
import com.waabbuffet.kotrt.schematic.Structure.StructureData;
import com.waabbuffet.kotrt.schematic.world.BoundingPoint;
import com.waabbuffet.kotrt.tileEntities.KotrtTileEntitiesHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public abstract class CommonProxy {

	public static BoundingPoint Point1, Point2;
	public static StructureData data;
	
	
	public static File dataDirectory; 
	
	 public void PreInit(final FMLPreInitializationEvent event) {
		
		 KotrtBlocksHandler.init();
		 KotrtBlocksHandler.register();


		 KotrtItemsHandler.init();
		 KotrtItemsHandler.register();

		 KotrtTileEntitiesHandler.register();

		 KotrtEntityHandler.InitializeEntities();

		 PacketHandler.init();
		 
	 }
	
	
	 public void Init(final FMLInitializationEvent event) {
		 
	 }
	
	 public void PostInit(final FMLPostInitializationEvent event) {
		 
	 }
	
	
	 public void unloadSchematic() {
	 }
	
	 public void createFolders() {
	       
	 }
	
	 
	 public void getDirectory(){
		 
	 }
	
	 public void registerRenders()
	{
			
	}
	
	public abstract boolean loadSchematic(EntityPlayer player, File directory, String filename);

	
	}
