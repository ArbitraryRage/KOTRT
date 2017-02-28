package com.waabbuffet.kotrt;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.Mod.Instance;

import com.google.common.collect.Lists;
import com.waabbuffet.kotrt.blocks.KotrtBlocksHandler;
import com.waabbuffet.kotrt.blocks.structureblock.StructureBlock;
import com.waabbuffet.kotrt.blocks.structureblock.StructureBuilderBlock;
import com.waabbuffet.kotrt.entities.KotrtEntityHandler;
import com.waabbuffet.kotrt.event.PropertiesEvent;
import com.waabbuffet.kotrt.generator.KotrtGenerator;
import com.waabbuffet.kotrt.gui.GuiGoldCurrency;
import com.waabbuffet.kotrt.gui.KotrtGuiHandler;
import com.waabbuffet.kotrt.items.KotrtItemsHandler;
import com.waabbuffet.kotrt.packet.PacketHandler;
import com.waabbuffet.kotrt.proxy.CommonProxy;
import com.waabbuffet.kotrt.references.SimpleReferences;
import com.waabbuffet.kotrt.tileEntities.KotrtTileEntitiesHandler;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBlock;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBuilderBlock;

@Mod(modid = SimpleReferences.Mod_ID, name = SimpleReferences.Mod_Name, version = SimpleReferences.Mod_Version)
public class KnightsOfTheRoundTable {

	//To Do Change everything into the proxies!!
	@SidedProxy(serverSide = SimpleReferences.Proxy_Server, clientSide = SimpleReferences.Proxy_Client)
	public static CommonProxy proxy;
	
	@Instance
    public static KnightsOfTheRoundTable instance = new KnightsOfTheRoundTable();
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		/*
		KotrtBlocksHandler.init();
		KotrtBlocksHandler.register();
		
		
		KotrtItemsHandler.init();
		KotrtItemsHandler.register();
		
		KotrtTileEntitiesHandler.register();
		
		KotrtEntityHandler.InitializeEntities();
		
		PacketHandler.init();
		*/
		proxy.PreInit(event);
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent event)
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new KotrtGuiHandler());
		MinecraftForge.EVENT_BUS.register(new PropertiesEvent());
		MinecraftForge.EVENT_BUS.register(new GuiGoldCurrency());
		
		
		GameRegistry.registerWorldGenerator(new KotrtGenerator(), 9000);
		GameRegistry.addShapedRecipe(new ItemStack(KotrtBlocksHandler.kingdom_structurebuilderblock), new Object[] {"ppp", "gdr","sss" ,'p',Blocks.PLANKS, 'r', Blocks.REDSTONE_BLOCK, 'g',  Blocks.GOLD_BLOCK, 'd',Items.DIAMOND, 's', Blocks.STONE});
	
		
		
		
		proxy.registerRenders();
		proxy.Init(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		ForgeChunkManager.setForcedChunkLoadingCallback(instance, new QuarryChunkloadCallback());
		
		
		proxy.PostInit(event);
	}
	
	
	public class QuarryChunkloadCallback implements ForgeChunkManager.OrderedLoadingCallback {
	    @Override
		    public void ticketsLoaded(List<ForgeChunkManager.Ticket> tickets, World world) {
	    	
		        for (ForgeChunkManager.Ticket ticket : tickets) {
		        	
		            int quarryX = ticket.getModData().getInteger("quarryX");
		            int quarryY = ticket.getModData().getInteger("quarryY");
		            int quarryZ = ticket.getModData().getInteger("quarryZ");
		            BlockPos pos = new BlockPos(quarryX, quarryY, quarryZ);
		
		            Block block = world.getBlockState(pos).getBlock();
		            
		            if (block instanceof StructureBuilderBlock) {
		                TileEntityKingdomStructureBuilderBlock tq = (TileEntityKingdomStructureBuilderBlock) world.getTileEntity(pos);
		                tq.forceChunkLoading(ticket);
		            }
		            
		            if (block instanceof StructureBlock) {
		                TileEntityKingdomStructureBlock tq = (TileEntityKingdomStructureBlock) world.getTileEntity(pos);
		                tq.forceChunkLoading(ticket);
		            }
		        }
		}

				@Override
				public List<Ticket> ticketsLoaded(List<Ticket> tickets, World world, int maxTicketCount) {
					
					  List<ForgeChunkManager.Ticket> validTickets = Lists.newArrayList();
					  
			          for (ForgeChunkManager.Ticket ticket : tickets) {
			        	  
			              int quarryX = ticket.getModData().getInteger("quarryX");
			              int quarryY = ticket.getModData().getInteger("quarryY");
			              int quarryZ = ticket.getModData().getInteger("quarryZ");
			              
			              BlockPos pos = new BlockPos(quarryX, quarryY, quarryZ);
		
			              Block block = world.getBlockState(pos).getBlock();
			              
			              if (block instanceof StructureBuilderBlock) {
			                 validTickets.add(ticket);
			              }
			              
			              if (block instanceof StructureBlock) {
				                 validTickets.add(ticket);
				          }
			          }
			          return validTickets;
				}
					
			}
		
}



