package com.waabbuffet.kotrt.gui.kingdom;

import java.io.IOException;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.waabbuffet.kotrt.entities.Outpost.EntityOutpostBase;
import com.waabbuffet.kotrt.packet.PacketHandler;
import com.waabbuffet.kotrt.packet.quests.FinishDelivery;
import com.waabbuffet.kotrt.packet.quests.FinishQuest;
import com.waabbuffet.kotrt.util.PlayerData;
import com.waabbuffet.kotrt.util.QuestFormat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiOutpostBase extends GuiScreen{

	public static final ResourceLocation Background1 = new ResourceLocation("kotrt:textures/gui/container/FirstGUI1.png");
	public static final ResourceLocation Background2 = new ResourceLocation("kotrt:textures/gui/container/GUIShopMaybe.png");
	
	 //firstGUI starts at 0 0 ends  175 183
	 int guiWidth = 256, guiWidth2 = 256;
	 int guiHeight = 256, guiHeight2 = 256;
	 
	 EntityPlayer Player;
	 EntityOutpostBase OutpostBase, DeliveryOriginal, NeedToGo;
	 World World;
	 QuestFormat Quest;
	 
	 boolean GreetingScreen = true;
	 boolean ShopScreen = false;
	 

	 
	 GuiButton ShopButton, TalkButton, NevermindButton; 
	
	 

	 public GuiOutpostBase(EntityOutpostBase Entity, EntityPlayer entityPlayer, World world) {
		Player = entityPlayer;
		this.OutpostBase = Entity;
		this.World = world;
		
		
		Quest = Entity.getQuest(Entity.getQuestID());
		this.getDeliveryOriginal();
		
		if(Quest != null)
		{
			if(this.OutpostBase.getEntityId() != 0)
			{
				
			
				if(this.OutpostBase.getQuest(this.OutpostBase.getQuestID()).getQuestTypeID() == 2){
					
					if(this.World.getEntityByID(this.OutpostBase.DeliveryHomeID) != null)
						((EntityOutpostBase)this.World.getEntityByID(this.OutpostBase.DeliveryHomeID)).AmTarget = true;
						
					NeedToGo = (EntityOutpostBase) World.getEntityByID(this.OutpostBase.DeliveryHomeID);
					
				}
			
				
			}
		}
		
	 }

	 public void getDeliveryOriginal()
	 {
		 boolean YeaOR = false;
		
		 for(int i =0; i < this.World.getLoadedEntityList().size(); i ++)
		 {
			Entity b = this.World.getLoadedEntityList().get(i);
			if(b instanceof EntityOutpostBase)
			{
				if(((EntityOutpostBase) b).DeliveryHomeID == this.OutpostBase.getEntityId())
				{
				
						this.DeliveryOriginal = (EntityOutpostBase) b;
						return;
						
					
				}
			}
		 }
		 
		
	 }
	 
	@Override
	public void drawScreen(int x, int y, float ticks) {
		
		 
		 int guiX = (width - 175) / 2, guiX2 = (width - guiWidth2) / 2;
		 int guiY = (height - 183) / 2, guiY2 = (height - guiHeight2) / 2;
		 
		
		 GL11.glColor4f(1, 1, 1, 1);
		 drawDefaultBackground();
		
		 if(!this.World.isRemote)
		{
		 if(GreetingScreen){
			 mc.renderEngine.bindTexture(Background1);
			 this.drawModalRectWithCustomSizedTexture(guiX2, guiY2 + 10, 0, 0, 256, 240, 256, 240);
			 
			
			 fontRendererObj.drawString("Total Gold: " + PlayerData.get(Player).getMana(), guiX2 + 100, guiY2 + 30, 0x8b0000);
			 
			 
			 if(this.OutpostBase.AmTarget && this.DeliveryOriginal != null && this.DeliveryOriginal.getQuestID() != 0)
			 {
				fontRendererObj.drawString("Delivery Item: " + this.OutpostBase.getQuest(this.DeliveryOriginal.getQuestID()).getHarvestItem().getDisplayName() + "*" + this.OutpostBase.getQuest(this.DeliveryOriginal.getQuestID()).getHarvestItem().stackSize, guiX - 5, guiY + 88, 0x8b0000);	 
			 }
		 }if(ShopScreen){
			
			 mc.renderEngine.bindTexture(Background2);
			 this.drawModalRectWithCustomSizedTexture(guiX2 + 5, guiY2 + 10, 0, 0, 286, 256, 286, 256);
			
			 fontRendererObj.drawString("Your Gold is: " + PlayerData.get(Player).getMana(), guiX + 50, guiY -10, 0x8b0000);
			
			 
			 if(this.Quest != null)
			 {
				 fontRendererObj.drawString("Why dont you be useful and" , guiX - 120, guiY + 10, 0xffffff);
				 fontRendererObj.drawString("do something for me!", guiX - 120 , guiY +20, 0xffffff);
				 
				 if(this.Quest.getQuestTypeID() == 0){
				 	fontRendererObj.drawString("Quest Objective: Time to Harvest!", guiX + 30 , guiY +20, 0x8b0000);
				 }else if(this.Quest.getQuestTypeID() == 1)
				 {
						fontRendererObj.drawString("Quest Objective: Fight For Us!", guiX + 30 , guiY +20, 0x8b0000);
				 }else if(this.Quest.getQuestTypeID() == 2)
				 {
						fontRendererObj.drawString("Quest Objective: Delivery Boy!", guiX + 30 , guiY +20, 0x8b0000);
				 }else if(this.Quest.getQuestTypeID() == 3)
				 {
						fontRendererObj.drawString("Quest Objective: A Way with Words!", guiX + 30 , guiY +20, 0x8b0000);
				 }else if(this.Quest.getQuestTypeID() == 4)
				 {
						fontRendererObj.drawString("Quest Objective: Special Request!", guiX + 30 , guiY +20, 0x8b0000);
				 }
				 
				 fontRendererObj.drawString("Listen, the name of the game ", guiX + 40 , guiY +30, 0x8b0000);
				 fontRendererObj.drawString("is the game of the name ok?", guiX + 40 , guiY +40, 0x8b0000);
			
				 if(this.Quest.getQuestTypeID() == 0){
					 // harvest
					 	fontRendererObj.drawString("Im just too busy to do anything", guiX + 30 , guiY +60, 0x000000);
					 	fontRendererObj.drawString("anymore, eveyone needs my help", guiX + 30 , guiY +70, 0x000000);
					 	fontRendererObj.drawString("Help me help others by bringing...", guiX + 30 , guiY +80, 0x000000);
					 	fontRendererObj.drawString(this.Quest.getHarvestItem().getDisplayName() + "*" + this.Quest.getHarvestItem().stackSize, guiX + 70, guiY + 100, 0x8b0000);
					 	 
					 	
						
					 }else if(this.Quest.getQuestTypeID() == 1)
					 {
						 //Kill
							fontRendererObj.drawString("Sometimes We have to do what", guiX + 30 , guiY +60, 0x000000);
							fontRendererObj.drawString("we dont want to do...but I really", guiX + 30 , guiY +70, 0x000000);
							fontRendererObj.drawString("dont want to do it so let me make", guiX + 30 , guiY +80, 0x000000);
							fontRendererObj.drawString("you do it!!! Please defeat:", guiX + 30 , guiY +90, 0x000000);
						 	fontRendererObj.drawString(this.Quest.getKillOrTalkEntity().getName() + "*" + this.OutpostBase.getQuestObjectiveTracker(), guiX + 70, guiY + 110, 0x8b0000);
					 }else if(this.Quest.getQuestTypeID() == 2)
					 {
						 //Delivery 
							fontRendererObj.drawString("I got an important product on the", guiX + 30 , guiY +60, 0x000000);
							fontRendererObj.drawString("market that needs to be delivered", guiX + 30 , guiY +70, 0x000000);
							fontRendererObj.drawString("dont ask questions you dont want", guiX + 30 , guiY +80, 0x000000);
							fontRendererObj.drawString("the answer to. I need you to Deliver:", guiX + 30 , guiY +90, 0x000000);
						 	fontRendererObj.drawString(this.Quest.getHarvestItem().getDisplayName() +"x" +this.Quest.getHarvestItem().stackSize +" to "+this.Quest.getKillOrTalkEntity().getCustomNameTag(), guiX + 40, guiY + 110, 0x8b0000);
						 	
						 
						 	if(this.NeedToGo != null)
						 	{
						 		fontRendererObj.drawString("Located at X: " + this.NeedToGo.getPosition().getX()+" Y: " + this.NeedToGo.getPosition().getY() + " Z:" + this.NeedToGo.getPosition().getZ(), guiX + 40, guiY + 120, 0x8b0000);	
						 	}
					 }else if(this.Quest.getQuestTypeID() == 3)
						 //Talk
					 {	fontRendererObj.drawString("Sometimes I cant say the words", guiX + 30 , guiY +60, 0x000000);
						fontRendererObj.drawString("that need to be said...So you", guiX + 30 , guiY +70, 0x000000);
						fontRendererObj.drawString("will have to do it instead of me", guiX + 30 , guiY +80, 0x000000);
						fontRendererObj.drawString("Find and Talk to this person:", guiX + 30 , guiY +90, 0x000000);
						fontRendererObj.drawString(this.Quest.getKillOrTalkEntity().getName() +"Last Located at", guiX + 70, guiY + 110, 0x8b0000);

					 	if(this.NeedToGo != null)
					 	{
					 		fontRendererObj.drawString("Located at X: " + this.NeedToGo.getPosition().getX()+" Y: " + this.NeedToGo.getPosition().getY() + " Z:" + this.NeedToGo.getPosition().getZ(), guiX + 40, guiY + 120, 0x8b0000);	
					 	}
						
					 }else if(this.Quest.getQuestTypeID() == 4)
					 {
						 //commander (individually hard coded)
						if(this.OutpostBase.getQuestID() == 20)
						{
						 	fontRendererObj.drawString("I have a very important request", guiX + 30 , guiY +60, 0x000000);
							fontRendererObj.drawString("for you and only you...Ill explain", guiX + 30 , guiY +70, 0x000000);
							fontRendererObj.drawString("m", guiX + 30 , guiY +80, 0x000000);
							fontRendererObj.drawString("", guiX + 30 , guiY +90, 0x000000);
						}
					 }
				 
				 	fontRendererObj.drawString("Of course I dont expect you", guiX + 30 , guiY +130, 0x000000);
				 	fontRendererObj.drawString("to work for free...Nothing is free", guiX + 30 , guiY + 140, 0x000000);
				 	fontRendererObj.drawString("in life, so expect a reward", guiX + 30 , guiY + 150, 0x000000);
				 	if(this.Quest.getRarityID() == 0 )
				 	{
				 		fontRendererObj.drawString("Rarity: Common", guiX + 30 , guiY + 160, 0xffffff);
				 	}else if(this.Quest.getRarityID() == 1 )
				 	{
				 		fontRendererObj.drawString("Rarity: Uncommon", guiX + 30 , guiY + 160, 0x13A803);
				 	}else if(this.Quest.getRarityID() == 2 )
				 	{
				 		fontRendererObj.drawString("Rarity: Rare", guiX + 30 , guiY + 160, 0x0026FF);
				 	}else if(this.Quest.getRarityID() == 3 )
				 	{
				 		fontRendererObj.drawString("Rarity: Epic", guiX + 30 , guiY + 160, 0xB159FF);
				 	}else if(this.Quest.getRarityID() == 4 )
				 	{
				 		fontRendererObj.drawString("Rarity: Legendary", guiX + 30 , guiY + 160, 0xFF180C);
				 	}
			 }else {
				 fontRendererObj.drawString("I dont need Help Right Now", guiX + 30 , guiY +20, 0x8b0000);
			 }

		 }
		
		}
		super.drawScreen(x, y, ticks);
		 
	}
	


	@Override
	 public boolean doesGuiPauseGame()
	    {
			return false;
	    }
	

	@Override
	public void initGui(){
		int guiX = (width - guiWidth) / 2;
		int guiY = (height - guiHeight) / 2;
		
		buttonList.clear();
		if(!this.World.isRemote)
		{
			if(GreetingScreen){
			
				
				buttonList.add(TalkButton = new GuiButton(0, guiX + 30, guiY + 170, 50, 20, "Talk"));
				
				if(this.OutpostBase != null)
				{
					if(this.Quest != null)
					{
						if((this.OutpostBase.getQuestID() != 0 && this.OutpostBase.getQuestObjectiveTracker() == 0 ) || this.Quest.getQuestTypeID() == 0)
						{
							buttonList.add(TalkButton = new GuiButton(3, guiX + 90, guiY + 170, 50, 20, "Finish Quest"));
						}
					}
				}	
				
			//	if(this.DeliveryOriginal != null && this.NeedToGo != null && this.OutpostBase.getQuest(this.DeliveryOriginal.getQuestID()).getQuestTypeID() == 2)
				if(this.OutpostBase.AmTarget)
				{
					buttonList.add(TalkButton = new GuiButton(4, guiX + 90, guiY + 140, 100, 20, "Hand in Delivery"));
				}
				buttonList.add(NevermindButton = new GuiButton(2, guiX + 30, guiY + 200, 100, 20, "Nevermind"));
			
			}else if(ShopScreen){
				
				guiX = (width - 175)/2;
				guiY = (height - 183)/2;
				
				
				
			}
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton Button) throws IOException {
		
		switch(Button.id){
			case 0:
				GreetingScreen = false;
				ShopScreen = true;
			
				initGui();
				break;
			case 2:
				Player.closeScreen();
				break;
			case 3:
				//Finish Quest
				PacketHandler.INSTANCE.sendToServer(new FinishQuest(this.OutpostBase.getEntityId(), this.OutpostBase.getQuestID()));
				initGui();
				break;
			case 4:
				//Hand In Delivery
				if(this.DeliveryOriginal != null){
					PacketHandler.INSTANCE.sendToServer(new FinishDelivery(this.DeliveryOriginal.getEntityId(), this.DeliveryOriginal.getQuestID()));
					this.OutpostBase.AmTarget = false;
				}
				initGui();
				break;
			default:
				
				
		}
		
		super.actionPerformed(Button);
	}



}
