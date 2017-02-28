package com.waabbuffet.kotrt.event;

import java.util.Random;

import com.waabbuffet.kotrt.KnightsOfTheRoundTable;
import com.waabbuffet.kotrt.entities.Kingdom.EntityCathedral;
import com.waabbuffet.kotrt.entities.Kingdom.EntityFarmer;
import com.waabbuffet.kotrt.entities.Kingdom.EntityGold;
import com.waabbuffet.kotrt.entities.Kingdom.EntityRecruiter;
import com.waabbuffet.kotrt.entities.Kingdom.EntityShopKeeper;
import com.waabbuffet.kotrt.entities.Kingdom.barracks.EntityBarracks;
import com.waabbuffet.kotrt.entities.Kingdom.barracks.EntityBarracksHunter;
import com.waabbuffet.kotrt.entities.Kingdom.barracks.EntityBarracksWarrior;
import com.waabbuffet.kotrt.entities.Kingdom.barracks.EntityMagicBolt;
import com.waabbuffet.kotrt.entities.Outpost.EntityOutpostBase;
import com.waabbuffet.kotrt.entities.Outpost.EntitySellShopKeeper;
import com.waabbuffet.kotrt.gui.GuiGoldCurrency;
import com.waabbuffet.kotrt.gui.kingdom.GuiKingdomEntityBarracks;
import com.waabbuffet.kotrt.gui.kingdom.GuiKingdomEntityBase;
import com.waabbuffet.kotrt.gui.kingdom.GuiKingdomEntityCathedral;
import com.waabbuffet.kotrt.gui.kingdom.GuiKingdomEntitySellKeeper;
import com.waabbuffet.kotrt.gui.kingdom.GuiKingdomEntityShopKeeper;
import com.waabbuffet.kotrt.gui.kingdom.GuiOutpostBase;
import com.waabbuffet.kotrt.gui.kingdom.GuiRecruiter;
import com.waabbuffet.kotrt.packet.PacketHandler;
import com.waabbuffet.kotrt.packet.quests.VillagerSkinIDClientUpdate;
import com.waabbuffet.kotrt.packet.structure.ChangeKingdomVillagerInformation;
import com.waabbuffet.kotrt.util.PlayerData;
import com.waabbuffet.kotrt.util.ShopKeeperItemFormat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PropertiesEvent {

	
	
	
	@SubscribeEvent
	public void onEntityLivingDeath(LivingDeathEvent event)
	{
		if(!event.getEntity().worldObj.isRemote)
		{
			
			if(event.getSource().getEntity() instanceof EntityPlayer || event.getSource().getEntity() instanceof EntityArrow || event.getSource().getDamageType().toString().contains("magic") || event.getSource().getEntity() instanceof EntityBarracksWarrior || event.getSource().getEntity() instanceof EntityBarracksHunter)
			{
				if(event.getEntity() instanceof EntityMob)
				{
						World world = event.getEntity().worldObj;
						Random Rand = new Random();
						
					for(int i =0; i < 20; i ++)
					{
							EntityGold g = new EntityGold(world, 1);
							g.setPosition(event.getEntity().getPosition().getX(), event.getEntity().getPosition().getY(), event.getEntity().getPosition().getZ());
							
						switch(Rand.nextInt(4)){
						case 1:
							g.addVelocity(-Rand.nextDouble()*0.2, Rand.nextDouble()*0.4, Rand.nextDouble()*0.2);
							break;
						case 2:
							g.addVelocity(Rand.nextDouble()*0.2, Rand.nextDouble()*0.4, -Rand.nextDouble()*0.2);
							break;
						case 3:
							g.addVelocity(-Rand.nextDouble()*0.2, Rand.nextDouble()*0.4, -Rand.nextDouble()*0.2);
							break;
						case 4:
							g.addVelocity(Rand.nextDouble()*0.2,Rand.nextDouble()*0.4, Rand.nextDouble()*0.2);
							break;
							default:
								g.addVelocity(Rand.nextDouble()*0.2, Rand.nextDouble()*0.4, Rand.nextDouble()*0.2);
								
						}
						
						world.spawnEntityInWorld(g);
					}	
						
						
						
				}
				//Quest Stuff
				
				if(event.getEntity() instanceof EntityZombie)
				{
					this.DecreaseQuestTracker(event.getEntity().worldObj, 4);
				}else if(event.getEntity() instanceof EntitySkeleton)
				{
					this.DecreaseQuestTracker(event.getEntity().worldObj, 5);
				}else if(event.getEntity() instanceof EntitySpider)
				{
					this.DecreaseQuestTracker(event.getEntity().worldObj, 6);
				}else if(event.getEntity() instanceof EntityCreeper)
				{
					this.DecreaseQuestTracker(event.getEntity().worldObj, 7);
				}else if(event.getEntity() instanceof EntityGhast)
				{
					this.DecreaseQuestTracker(event.getEntity().worldObj, 18);
				}else if(event.getEntity() instanceof EntityPigZombie)
				{
					this.DecreaseQuestTracker(event.getEntity().worldObj, 19);
				}else if(event.getEntity() instanceof EntitySheep)
				{
					this.DecreaseQuestTracker(event.getEntity().worldObj, 20);
				}else if(event.getEntity() instanceof EntityCow)
				{
					this.DecreaseQuestTracker(event.getEntity().worldObj, 21);
				}else if(event.getEntity() instanceof EntityPig)
				{
					this.DecreaseQuestTracker(event.getEntity().worldObj, 22);
				}else if(event.getEntity() instanceof EntitySquid)
				{
					this.DecreaseQuestTracker(event.getEntity().worldObj, 23);
				}
				
			}
	
		}
	}
	
	public void DecreaseQuestTracker(World world, int QuestID){
		
		for(int i =0; i < world.loadedEntityList.size(); i ++)
		{
			Entity b = world.loadedEntityList.get(i);
			
			if( b instanceof EntityOutpostBase)
			{
				if(((EntityOutpostBase) b).getQuestID() == QuestID)
				{
					if(((EntityOutpostBase) b).getQuestObjectiveTracker() > 0)
						((EntityOutpostBase) b).decreaseQuestTracker();
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityRightClick(EntityInteractEvent  event)
	{
		if(event.target instanceof EntityFarmer)
		{
			EntityFarmer b = (EntityFarmer) event.target;
			
			Minecraft.getMinecraft().displayGuiScreen(new GuiKingdomEntityBase(event.getEntityPlayer(), b, event.getEntityPlayer().worldObj));
			
		}else if(event.target instanceof EntityShopKeeper)
		{

			EntityShopKeeper b = (EntityShopKeeper) event.target;
			
			Minecraft.getMinecraft().displayGuiScreen(new GuiKingdomEntityShopKeeper(b, event.getEntityPlayer(), event.getEntityPlayer().worldObj, EntityShopKeeper.FillItemRegBlocks()));
		}
		else if(event.target instanceof EntityRecruiter)
		{

			EntityRecruiter b = (EntityRecruiter) event.target;
			
			Minecraft.getMinecraft().displayGuiScreen(new GuiRecruiter(event.getEntityPlayer(), b, event.getEntityPlayer().worldObj));
		}else if(event.target instanceof EntityOutpostBase)
		{

			EntityOutpostBase b = (EntityOutpostBase) event.target;
			
			Minecraft.getMinecraft().displayGuiScreen(new GuiOutpostBase(b, event.getEntityPlayer(), event.getEntityPlayer().worldObj));
		}else if(event.target instanceof EntitySellShopKeeper)
		{
			EntitySellShopKeeper b = (EntitySellShopKeeper) event.target;
			ShopKeeperItemFormat SaleItems[] = new ShopKeeperItemFormat[100];
			
			SaleItems[0] = new ShopKeeperItemFormat(new ItemStack(Items.IRON_INGOT), 15);
			SaleItems[1] = null;// gone
			SaleItems[2] = new ShopKeeperItemFormat(new ItemStack(Items.COAL), 8);
			SaleItems[3] = null;//gone
			SaleItems[4] = new ShopKeeperItemFormat(new ItemStack(Items.GOLD_INGOT), 30);
			
			SaleItems[5] = new ShopKeeperItemFormat(new ItemStack(Items.DIAMOND), 800); 
			SaleItems[6] = null; //gone
			SaleItems[7] = new ShopKeeperItemFormat(new ItemStack(Items.redstone), 15);
			SaleItems[8] = null; //gone
			SaleItems[9] = new ShopKeeperItemFormat(new ItemStack(Items.quartz), 20);
			
			SaleItems[10] = new ShopKeeperItemFormat(new ItemStack(Items.bone), 6);
			SaleItems[11] = null;//gone
			SaleItems[12] = new ShopKeeperItemFormat(new ItemStack(Items.spider_eye), 8);
			SaleItems[13] = null;//gone 
			SaleItems[14] = new ShopKeeperItemFormat(new ItemStack(Items.gunpowder), 10);
			
			
			
			SaleItems[15] = new ShopKeeperItemFormat(new ItemStack(Items.rotten_flesh), 1);
			SaleItems[16] = null;// gone
			SaleItems[17] = new ShopKeeperItemFormat(new ItemStack(Items.slime_ball), 25);
			SaleItems[18] = null;//gone
			SaleItems[19] = new ShopKeeperItemFormat(new ItemStack(Items.ender_pearl), 25);
			
			SaleItems[20] = new ShopKeeperItemFormat(new ItemStack(Items.blaze_rod), 25); 
			SaleItems[21] = null; //gone
			SaleItems[22] = new ShopKeeperItemFormat(new ItemStack(Items.string), 4);
			SaleItems[23] = null; //gone
			SaleItems[24] = new ShopKeeperItemFormat(new ItemStack(Items.feather), 4);
			
			SaleItems[25] = new ShopKeeperItemFormat(new ItemStack(Items.leather), 20);
			SaleItems[26] = null;//gone
			SaleItems[27] = new ShopKeeperItemFormat(new ItemStack(Items.ghast_tear), 200);
			SaleItems[28] = null;//gone 
			SaleItems[29] = new ShopKeeperItemFormat(new ItemStack(Items.magma_cream), 50);
			
			SaleItems[30] = new ShopKeeperItemFormat(new ItemStack(Items.wheat), 2);
			SaleItems[31] = null;// gone
			SaleItems[32] = new ShopKeeperItemFormat(new ItemStack(Items.carrot), 3);
			SaleItems[33] = null;//gone
			SaleItems[34] = new ShopKeeperItemFormat(new ItemStack(Items.potato), 3);
			
			SaleItems[35] = new ShopKeeperItemFormat(new ItemStack(Items.APPLE), 20); 
			SaleItems[36] = null; //gone
			SaleItems[37] = new ShopKeeperItemFormat(new ItemStack(Items.melon), 3);
			SaleItems[38] = null; //gone
			SaleItems[39] = new ShopKeeperItemFormat(new ItemStack(Items.reeds), 2);
			
			SaleItems[40] = new ShopKeeperItemFormat(new ItemStack(Blocks.cactus), 4);
			SaleItems[41] = null;//gone
			SaleItems[42] = new ShopKeeperItemFormat(new ItemStack(Items.cooked_fish), 21);
			SaleItems[43] = null;//gone 
			SaleItems[44] = new ShopKeeperItemFormat(new ItemStack(Items.cooked_chicken), 9);
			
			SaleItems[45] = new ShopKeeperItemFormat(new ItemStack(Items.mushroom_stew), 15);
			SaleItems[46] = null;// gone
			SaleItems[47] = new ShopKeeperItemFormat(new ItemStack(Items.wheat_seeds), 2);
			SaleItems[48] = null;//gone
			SaleItems[49] = new ShopKeeperItemFormat(new ItemStack(Items.cooked_porkchop), 13);
			
			SaleItems[50] = new ShopKeeperItemFormat(new ItemStack(Blocks.yellow_flower), 2); 
			SaleItems[51] = null; //gone
			SaleItems[52] = new ShopKeeperItemFormat(new ItemStack(Blocks.red_flower), 2);
			SaleItems[53] = null; //gone
			SaleItems[54] = new ShopKeeperItemFormat(new ItemStack(Items.milk_bucket), 45);
			
			SaleItems[55] = new ShopKeeperItemFormat(new ItemStack(Blocks.pumpkin), 10);
			SaleItems[56] = null;//gone
			SaleItems[57] = new ShopKeeperItemFormat(new ItemStack(Items.pumpkin_pie), 15);
			SaleItems[58] = null;//gone 
			SaleItems[59] = new ShopKeeperItemFormat(new ItemStack(Items.cake), 30);
			
			
			Minecraft.getMinecraft().displayGuiScreen(new GuiKingdomEntitySellKeeper(b, event.getEntityPlayer(), event.getEntityPlayer().worldObj, SaleItems));
		}else if(event.target instanceof EntityBarracks)
		{

			EntityBarracks b = (EntityBarracks) event.target;
			
			Minecraft.getMinecraft().displayGuiScreen(new GuiKingdomEntityBarracks(b, event.getEntityPlayer(), event.getEntityPlayer().worldObj));
		}else if(event.target instanceof EntityCathedral)
		{

			EntityCathedral b = (EntityCathedral) event.target;
			
			Minecraft.getMinecraft().displayGuiScreen(new GuiKingdomEntityCathedral(b, event.getEntityPlayer(), event.getEntityPlayer().worldObj));
		}
			
		
	}
	
	
/*
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onGuiRender(RenderGameOverlayEvent.Post event)
	{
		
		if(event.isCanceled())
		{
			
			return;
		}
		
		
		EntityPlayer b = Minecraft.getMinecraft().thePlayer;
		GuiGoldCurrency  b1 = new GuiGoldCurrency(b, event.resolution);
		
		if(PlayerData.get(b) != null)
		{
			if(event.type == ElementType.EXPERIENCE){
			
				if(b1 != null)
				{
					b1.initGui();
					
				}
				
			}
			
		}
	
	}
	*/
	
/*	
	@SubscribeEvent
	public void onEntityConstructing(RenderPlayerEvent.Pre e) {
		
		
		e.setCanceled(true);
		
		
		
	
		
	}
	*/
	
	
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing e) {
		
	    if (e.getEntity() instanceof EntityPlayer) {
	        PlayerData.register((EntityPlayer) e.getEntity());
	    }
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent e) {
		
	    if (e.getEntity() instanceof EntityPlayer) {
	        PlayerData.get((EntityPlayer) e.getEntity()).syncMana();
	    }
	    
	    if(e.getEntity() instanceof EntityFarmer)
	    {
	    	EntityFarmer Entity =  (EntityFarmer) e.getEntity();
	    	Entity.DetermineTasks(e.getEntity().worldObj);
	    }
	    
	}
	

	@SubscribeEvent
	public void onPlayerCloned(PlayerEvent.Clone e) {
	    NBTTagCompound nbt = new NBTTagCompound();
	   
	   
	    PlayerData.get(e.getOriginal()).saveReviveRelevantNBTData(nbt, e.isWasDeath());
	    PlayerData.get(e.getEntityPlayer()).loadNBTData(nbt);
	}
}
