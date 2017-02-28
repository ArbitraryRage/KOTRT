package com.waabbuffet.kotrt.gui;



import com.waabbuffet.kotrt.entities.Outpost.EntitySellShopKeeper;
import com.waabbuffet.kotrt.gui.kingdom.GuiKingdomEntitySellKeeper;
import com.waabbuffet.kotrt.gui.kingdom.GuiKingdomEntityShopKeeper;
import com.waabbuffet.kotrt.gui.structure.GuiTileEntityKingdomStructure;
import com.waabbuffet.kotrt.gui.structure.GuiTileEntityKingdomStructureBuilderBlock;
import com.waabbuffet.kotrt.inventory.ContainerTileEntityKingdomStructureBlock;
import com.waabbuffet.kotrt.inventory.ContainerTileEntityKingdomStructureBuilderBlock;
import com.waabbuffet.kotrt.packet.PacketHandler;
import com.waabbuffet.kotrt.packet.structure.ChangeKingdomVillagerInformation;
import com.waabbuffet.kotrt.packet.structure.UpdateClientTileInv1;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBlock;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBuilderBlock;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class KotrtGuiHandler implements IGuiHandler {

	
	
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
       
    	if(ID == 0){
    		 return new ContainerTileEntityKingdomStructureBlock(player.inventory, (TileEntityKingdomStructureBlock) world.getTileEntity(new BlockPos(x, y, z)));
    	}
    	if(ID == 1){
    		  TileEntityKingdomStructureBuilderBlock Te = (TileEntityKingdomStructureBuilderBlock) world.getTileEntity(new BlockPos(x, y, z));
    		  
    		 
    		if(Te.FileName != null && Te.BuildingDirection != null) {
    			
    		 	PacketHandler.INSTANCE.sendToAllAround(new UpdateClientTileInv1(Te.FileName, Te.BuildingDirection,Te.getPos().getX(), Te.getPos().getY(), Te.getPos().getZ(), Te.TileBlockPosX, Te.TileBlockPosY, Te.TileBlockPosZ, Te.StartConstruction ? 1:2),  new TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 10));
    		}
    		
    		 return new ContainerTileEntityKingdomStructureBuilderBlock(player.inventory, Te);
    	
    	}
    	
    	
    
    	return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        
    	if(ID == 0){
    		return new GuiTileEntityKingdomStructure(player.inventory, (TileEntityKingdomStructureBlock) world.getTileEntity(new BlockPos(x, y, z)));
    	}
    	if(ID == 1){
    		return new GuiTileEntityKingdomStructureBuilderBlock(player.inventory, (TileEntityKingdomStructureBuilderBlock) world.getTileEntity(new BlockPos(x, y, z)), player);
    		
    	
    	}
    		
    	return null;
    }
    
    public EntitySellShopKeeper getEntity(World world, int posX, int posZ)
    {
    	EntitySellShopKeeper b = null;
    	
    	for (int i = 0; i < world.getLoadedEntityList().size(); i++) {
			
    		if(world.getLoadedEntityList().get(i) instanceof EntitySellShopKeeper)
    		{
    			if(world.getLoadedEntityList().get(i).getPosition().getX() == posX && world.getLoadedEntityList().get(i).getPosition().getZ() == posZ)
    			{
    				b = (EntitySellShopKeeper) world.getLoadedEntityList().get(i);
    				
    				return b;
    			}
    		}
		}
    	
    	return b;
    	
    }
    
}
