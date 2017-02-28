package com.waabbuffet.kotrt.tileEntities;

import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBlock;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBuilderBlock;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class KotrtTileEntitiesHandler 
{
	public static void register()
	{
		GameRegistry.registerTileEntity(TileEntityKingdomStructureBlock.class, "kotrtKindgomStructureBlock");
		GameRegistry.registerTileEntity(TileEntityKingdomStructureBuilderBlock.class, "kotrtKindgomStructureBuilderBlock");
		
	}
	
	
}
