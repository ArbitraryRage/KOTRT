package com.waabbuffet.kotrt.entities;

import java.util.Random;

import com.waabbuffet.kotrt.KnightsOfTheRoundTable;
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

import net.minecraft.entity.EntityList;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class KotrtEntityHandler {

	public static void RegisterEntity(Class EntityClass, String Name ){
		int EntityID = EntityRegistry.findGlobalUniqueEntityId();
		
		
		long x = Name.hashCode();
		Random random = new Random(x);
		int MainColour = random.nextInt() * 16777215;
		int SubColour = random.nextInt() * 16777215;
		
		EntityRegistry.registerGlobalEntityID(EntityClass, Name, EntityID);
		EntityRegistry.registerModEntity(EntityClass, Name, EntityID, KnightsOfTheRoundTable.instance, 80, 3, true);
		
		if(!Name.contains("KingdomGold") && !Name.contains("MagicBolt"))
			EntityList.ENTITY_EGGS.put(Integer.valueOf(EntityID), new EntityList.EntityEggInfo(EntityID, MainColour, SubColour));
	}
	
	
	public static void InitializeEntities()
	{
		RegisterEntity(EntityFarmer.class, "KingdomFarmer");
		RegisterEntity(EntityGold.class, "KingdomGold");
		RegisterEntity(EntityShopKeeper.class, "KingdomShopKeeper");
		RegisterEntity(EntityRecruiter.class, "KingdomRecruiter");
		RegisterEntity(EntityOutpostBase.class, "OutpostBase");
		RegisterEntity(EntitySellShopKeeper.class, "SellShopKeeper");
		RegisterEntity(EntityBarracks.class, "KingdomBarracks");
		RegisterEntity(EntityBarracksWarrior.class, "KingdomBarracksWarrior");
		RegisterEntity(EntityBarracksHunter.class, "KingdomBarracksHunter");
		RegisterEntity(EntityBarracksMage.class, "KingdomBarracksMage");
		RegisterEntity(EntityMagicBolt.class, "MagicBolt");
		RegisterEntity(EntityCathedral.class, "KingdomCathedral");

	}
	
	
}
