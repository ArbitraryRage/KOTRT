package com.waabbuffet.kotrt.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.waabbuffet.kotrt.references.SimpleReferences;

public class KotrtItemsHandler {

	
	public static Item kingdom_staff;
	
	public static void init()
	{
		kingdom_staff = new Item().setUnlocalizedName("kingdom_staff");
		
		
	}
	
	public static void register()
	{
		GameRegistry.registerItem(kingdom_staff, kingdom_staff.getUnlocalizedName().substring(5));
		
		
	}
	
	public static void registerRenders()
	{
		registerRender(kingdom_staff);
	
	}

	
	public static void registerRender(Item item)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation("kotrt:" + item.getUnlocalizedName().substring(5),"inventory"));
		
	}
}
