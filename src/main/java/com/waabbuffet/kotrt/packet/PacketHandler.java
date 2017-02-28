package com.waabbuffet.kotrt.packet;


import com.waabbuffet.kotrt.packet.quests.FinishDelivery;
import com.waabbuffet.kotrt.packet.quests.FinishQuest;
import com.waabbuffet.kotrt.packet.quests.VillagerSkinIDClientUpdate;
import com.waabbuffet.kotrt.packet.structure.ChangeKingdomVillagerInformation;
import com.waabbuffet.kotrt.packet.structure.FinishStructure;
import com.waabbuffet.kotrt.packet.structure.GiveItemToPlayer;
import com.waabbuffet.kotrt.packet.structure.KingdomData;
import com.waabbuffet.kotrt.packet.structure.ReduceStackSizeFromTE;
import com.waabbuffet.kotrt.packet.structure.SpawnWarrior;
import com.waabbuffet.kotrt.packet.structure.StructureConstruct;
import com.waabbuffet.kotrt.packet.structure.StructureSchematic;
import com.waabbuffet.kotrt.packet.structure.UpdateClientTileInv;
import com.waabbuffet.kotrt.packet.structure.UpdateClientTileInv1;
import com.waabbuffet.kotrt.packet.structure.UpdateGold;
import com.waabbuffet.kotrt.packet.structure.UpdateMaterialList;
import com.waabbuffet.kotrt.references.SimpleReferences;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

	    public static SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("kotrt"); 
	    
	    public static void init() {
	    	//changed discrim here
	     //   INSTANCE.registerMessage(StructureSchematic.class, StructureSchematic.class, 0, Side.SERVER);
	        INSTANCE.registerMessage(StructureConstruct.class, StructureConstruct.class, 1, Side.SERVER);
	        INSTANCE.registerMessage(KingdomData.class, KingdomData.class, 2, Side.CLIENT);
	        INSTANCE.registerMessage(ReduceStackSizeFromTE.class, ReduceStackSizeFromTE.class, 3, Side.SERVER);
	        INSTANCE.registerMessage(UpdateClientTileInv.class, UpdateClientTileInv.class, 0, Side.SERVER);
	        INSTANCE.registerMessage(UpdateClientTileInv1.class, UpdateClientTileInv1.class, 4, Side.CLIENT);
	        INSTANCE.registerMessage(UpdateMaterialList.class, UpdateMaterialList.class, 6, Side.CLIENT);
	        INSTANCE.registerMessage(ChangeKingdomVillagerInformation.class, ChangeKingdomVillagerInformation.class, 5, Side.CLIENT);
	        INSTANCE.registerMessage(FinishStructure.class, FinishStructure.class, 7, Side.SERVER);
	        INSTANCE.registerMessage(GiveItemToPlayer.class, GiveItemToPlayer.class, 8, Side.SERVER);
	        INSTANCE.registerMessage(FinishDelivery.class,FinishDelivery.class, 9, Side.SERVER);
	        INSTANCE.registerMessage(FinishQuest.class, FinishQuest.class, 10, Side.SERVER);
	        INSTANCE.registerMessage(SpawnWarrior.class, SpawnWarrior.class, 11, Side.SERVER);
	        INSTANCE.registerMessage(UpdateGold.class, UpdateGold.class, 12, Side.CLIENT);
	        
	        
	    //    INSTANCE.registerMessage(VillagerSkinIDClientUpdate.class, VillagerSkinIDClientUpdate.class, 11, Side.CLIENT);
		    
	        

	    //    INSTANCE.registerMessage(MessageDownloadBegin.class, MessageDownloadBegin.class, 1, Side.CLIENT);
	    //    INSTANCE.registerMessage(MessageDownloadBeginAck.class, MessageDownloadBeginAck.class, 2, Side.SERVER);
	     //   INSTANCE.registerMessage(MessageDownloadChunk.class, MessageDownloadChunk.class, 3, Side.CLIENT);
	     //   INSTANCE.registerMessage(MessageDownloadChunkAck.class, MessageDownloadChunkAck.class, 4, Side.SERVER);
	     //   INSTANCE.registerMessage(MessageDownloadEnd.class, MessageDownloadEnd.class, 5, Side.CLIENT);
	    
	    }	
}
