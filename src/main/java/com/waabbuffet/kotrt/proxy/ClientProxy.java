package com.waabbuffet.kotrt.proxy;

import java.io.File;

import org.lwjgl.input.Keyboard;

import com.waabbuffet.kotrt.blocks.KotrtBlocksHandler;
import com.waabbuffet.kotrt.entities.Kingdom.EntityCathedral;
import com.waabbuffet.kotrt.entities.Kingdom.EntityFarmer;
import com.waabbuffet.kotrt.entities.Kingdom.EntityGold;
import com.waabbuffet.kotrt.entities.Kingdom.EntityRecruiter;
import com.waabbuffet.kotrt.entities.Kingdom.EntityShopKeeper;
import com.waabbuffet.kotrt.entities.Kingdom.barracks.EntityBarracks;
import com.waabbuffet.kotrt.entities.Kingdom.barracks.EntityBarracksHunter;
import com.waabbuffet.kotrt.entities.Kingdom.barracks.EntityBarracksMage;
import com.waabbuffet.kotrt.entities.Kingdom.barracks.EntityBarracksWarrior;
import com.waabbuffet.kotrt.entities.Kingdom.barracks.EntityMagicBolt;
import com.waabbuffet.kotrt.entities.Outpost.EntityOutpostBase;
import com.waabbuffet.kotrt.entities.Outpost.EntitySellShopKeeper;
import com.waabbuffet.kotrt.entities.render.RenderBarracks;
import com.waabbuffet.kotrt.entities.render.RenderBarracksHunter;
import com.waabbuffet.kotrt.entities.render.RenderBarracksMage;
import com.waabbuffet.kotrt.entities.render.RenderBarracksWarrior;
import com.waabbuffet.kotrt.entities.render.RenderCathedral;
import com.waabbuffet.kotrt.entities.render.RenderEntitySellShopKeeper;
import com.waabbuffet.kotrt.entities.render.RenderFarmer;
import com.waabbuffet.kotrt.entities.render.RenderGold;
import com.waabbuffet.kotrt.entities.render.RenderMagicBolt;
import com.waabbuffet.kotrt.entities.render.RenderOutpostBase;
import com.waabbuffet.kotrt.entities.render.RenderRecruiter;
import com.waabbuffet.kotrt.entities.render.RenderShopKeeper;
import com.waabbuffet.kotrt.handlers.KeyBindHandler;




import com.waabbuffet.kotrt.items.KotrtItemsHandler;
import com.waabbuffet.kotrt.packet.PacketHandler;
import com.waabbuffet.kotrt.references.SimpleReferences;
import com.waabbuffet.kotrt.schematic.Structure.StructureData;
import com.waabbuffet.kotrt.schematic.world.BoundingPoint;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	
	// Used for everything else
	
	
	
	
	 @Override
	    public void PreInit(final FMLPreInitializationEvent event) {
	      super.PreInit(event);
		 
	      
	      
	        dataDirectory = new File(event.getModConfigurationDirectory(), SimpleReferences.Mod_ID);
	        data = new StructureData();
	        
	       
	       
	 }
	 
	 
	 @Override
	   public void Init(final FMLInitializationEvent event) {
	        
		 super.Init(event);
		 
		 
	  //  MinecraftForge.EVENT_BUS.register(new KeyBindHandler());
	        
	    RenderingRegistry.registerEntityRenderingHandler(EntityFarmer.class, new RenderFarmer(new ModelBiped(), 0.3F));
	    
	    RenderingRegistry.registerEntityRenderingHandler(EntityShopKeeper.class, new RenderShopKeeper(new ModelBiped(), 0.3F));
	    
	    RenderingRegistry.registerEntityRenderingHandler(EntityRecruiter.class, new RenderRecruiter(new ModelBiped(), 0.3F));
	    
	    RenderingRegistry.registerEntityRenderingHandler(EntityOutpostBase.class, new RenderOutpostBase(new ModelBiped(), 0.3F));
	    
	    RenderingRegistry.registerEntityRenderingHandler(EntitySellShopKeeper.class, new RenderEntitySellShopKeeper(new ModelBiped(), 0.3F));
	    
	    RenderingRegistry.registerEntityRenderingHandler(EntityCathedral.class, new RenderCathedral(new ModelBiped(), 0.3F));
	    
	    RenderingRegistry.registerEntityRenderingHandler(EntityBarracks.class, new RenderBarracks(new ModelBiped(), 0.3F));
	    
	    RenderingRegistry.registerEntityRenderingHandler(EntityBarracksWarrior.class, new RenderBarracksWarrior(new ModelBiped(), 0.3F));
	    
	    RenderingRegistry.registerEntityRenderingHandler(EntityBarracksHunter.class, new RenderBarracksHunter(new ModelBiped(), 0.3F));
	    
	    RenderingRegistry.registerEntityRenderingHandler(EntityBarracksMage.class, new RenderBarracksMage(new ModelBiped(), 0.3F));
	    
	    RenderingRegistry.registerEntityRenderingHandler(EntityMagicBolt.class, new RenderMagicBolt(Minecraft.getMinecraft().getRenderManager()));
	    
	    RenderingRegistry.registerEntityRenderingHandler(EntityGold.class, new RenderGold(Minecraft.getMinecraft().getRenderManager()));
	    
	   
	    
	    
	    
	   
	 }
	 
	@Override
	public void unloadSchematic() {
	   
	    
	       
	  
	}


	@Override
	public boolean loadSchematic(EntityPlayer player, File directory, String filename) {
		
		
		return false;
	}
	
	
	
	@Override
	public void registerRenders()
	{
		KotrtBlocksHandler.registerRenders();
		KotrtItemsHandler.registerRenders();
	}
	

	
}
