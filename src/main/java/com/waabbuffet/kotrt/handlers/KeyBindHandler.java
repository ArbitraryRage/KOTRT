package com.waabbuffet.kotrt.handlers;

import java.io.File;

import org.lwjgl.input.Keyboard;

import com.sun.jna.Structure;
import com.waabbuffet.kotrt.KnightsOfTheRoundTable;
import com.waabbuffet.kotrt.packet.PacketHandler;
import com.waabbuffet.kotrt.packet.structure.StructureSchematic;
import com.waabbuffet.kotrt.proxy.ClientProxy;
import com.waabbuffet.kotrt.proxy.CommonProxy;
import com.waabbuffet.kotrt.schematic.Structure.StructureData;
import com.waabbuffet.kotrt.schematic.Structure.StructureReader;
import com.waabbuffet.kotrt.schematic.Structure.StructureSchematicCreator;
import com.waabbuffet.kotrt.schematic.world.BoundingPoint;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBuilderBlock;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;



public class KeyBindHandler {
	
	
	
	static KeyBinding key = new KeyBinding("KOTRT save to File", Keyboard.KEY_L, "key.categories.misc");
	static KeyBinding key1 = new KeyBinding("KOTRT Point 1", Keyboard.KEY_K, "key.categories.misc");
	static KeyBinding key2 = new KeyBinding("KOTRT Point 2", Keyboard.KEY_J, "key.categories.misc");
	static KeyBinding key3 = new KeyBinding("KOTRT Save Menu", Keyboard.KEY_H, "key.categories.misc");

	public KeyBindHandler() {
		ClientRegistry.registerKeyBinding(key);
		ClientRegistry.registerKeyBinding(key1);
		ClientRegistry.registerKeyBinding(key2);
		ClientRegistry.registerKeyBinding(key3);
	}
	
	 @SubscribeEvent
	 public void onKeyInput(final InputEvent event) {
		
		 if(key.isPressed()){
			
			 //L
			StructureSchematicCreator.SchematicCreator("Lumberjack Guild");
			 //builds
		//	 PacketHandler.INSTANCE.sendToServer(new StructureSchematic("TestCreation.schematic", true));
/*
				
			*/
		 }
		 
		 if(key1.isPressed()){
			 //k
			ClientProxy.Point1 = new BoundingPoint(Minecraft.getMinecraft().thePlayer);
		 }
		 
		 if(key2.isPressed()){
			 //j
			 ClientProxy.Point2 = new BoundingPoint(Minecraft.getMinecraft().thePlayer);
		 }
		 
		 if(key3.isPressed()){
			//h
			//schem
		//	 PacketHandler.INSTANCE.sendToServer(new StructureSchematic("TestCreation.schematic", false));
			 
			 StructureReader.StructureReaderFromWorld(Minecraft.getMinecraft().thePlayer.worldObj, ClientProxy.Point1, ClientProxy.Point2);
		//	 StructureReader.StructureReaderFromFile(new File(ClientProxy.dataDirectory, "MainBase.schematic"));
			
			 /*
			 for(int x = 0; x < ClientProxy.data.FullBlocks.length; x++) {
					for(int y = 0; y < ClientProxy.data.FullBlocks[0].length; y++) {
						for(int z = 0; z < ClientProxy.data.FullBlocks[0][0].length; z++) {
							
							System.out.println("The block is: " + ClientProxy.data.FullBlocks[x][y][z]);
						}
					}
			 }
			 */
		 }
		 
		 
		 
	 }

	
	
 }
	
