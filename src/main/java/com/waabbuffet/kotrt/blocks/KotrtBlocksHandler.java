package com.waabbuffet.kotrt.blocks;

import com.waabbuffet.kotrt.blocks.structureblock.StructureBlock;
import com.waabbuffet.kotrt.blocks.structureblock.StructureBuilderBlock;
import com.waabbuffet.kotrt.blocks.structureblock.StructureOulineBlock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class KotrtBlocksHandler {

	
	public static Block kingdom_structureblock;
	public static Block kingdom_structurebuilderblock;
	public static Block kingdom_outline_Block;
	
	public static void init()
	{
		kingdom_structureblock = new StructureBlock(Material.barrier).setUnlocalizedName("kingdom_structureblock");
		kingdom_structurebuilderblock = new StructureBuilderBlock(Material.cloth).setUnlocalizedName("kingdom_structure_builder_block");
		kingdom_outline_Block = new StructureOulineBlock(Material.barrier).setUnlocalizedName("kingdom_outline_block");
	}
	
	public static void register()
	{
		GameRegistry.registerBlock(kingdom_structureblock, kingdom_structureblock.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(kingdom_structurebuilderblock, kingdom_structurebuilderblock.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(kingdom_outline_Block, kingdom_outline_Block.getUnlocalizedName().substring(5));
	}
	
	
	public static void registerRenders()
	{
		registerRender(kingdom_structureblock);
		registerRender(kingdom_structurebuilderblock);
		registerRender(kingdom_outline_Block);
		
	}
	
	public static void registerRender(Block block)
	{
		Item item = Item.getItemFromBlock(block);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation("kotrt:" + item.getUnlocalizedName().substring(5),"inventory"));
	}
	
}
