package com.waabbuffet.kotrt.gui.kingdom;

import java.io.IOException;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.waabbuffet.kotrt.entities.Kingdom.EntityCathedral;
import com.waabbuffet.kotrt.entities.Kingdom.barracks.EntityBarracks;
import com.waabbuffet.kotrt.packet.PacketHandler;
import com.waabbuffet.kotrt.packet.structure.SpawnWarrior;
import com.waabbuffet.kotrt.util.PlayerData;

public class GuiKingdomEntityCathedral extends GuiScreen{
	

	public static final ResourceLocation Background1 = new ResourceLocation("kotrt:textures/gui/container/FirstGUI1.png");
	public static final ResourceLocation Background2 = new ResourceLocation("kotrt:textures/gui/container/GUIShopMaybe.png");
	
	//firstGUI starts at 0 0 ends  175 183
	int guiWidth = 256, guiWidth2 = 256;
	int guiHeight = 256, guiHeight2 = 256;
	
	public EntityPlayer Player;
	public EntityCathedral Barracks;
	public World World;
	
	static Random rand = new Random();
	static String b;
	
	
	boolean GreetingScreen = true;
	boolean ShopScreen = false;
	boolean QuestScreen = false;
	
	GuiButton ShopButton, TalkButton, NevermindButton, itemOne, itemTwo, itemThree, itemFour;

	
	
	public GuiKingdomEntityCathedral(EntityCathedral barracks, EntityPlayer entityPlayer, World world) {
		
		if(world.isRemote)
			b = this.WhatToSay1();
		
		Barracks = barracks;
		Player = entityPlayer;
		World = world;
	}
	

	@Override
	public void drawScreen(int x, int y, float ticks) {
		 Minecraft mc = Minecraft.getMinecraft();
		 
		 int guiX = (width - 175) / 2, guiX2 = (width - guiWidth2) / 2;
		 int guiY = (height - 183) / 2, guiY2 = (height - guiHeight2) / 2;
		 

		 GL11.glColor4f(1, 1, 1, 1);
		 drawDefaultBackground();
		 
		if(!this.World.isRemote)
		{
		 if(GreetingScreen){
			 mc.renderEngine.bindTexture(Background1);
			 drawTexturedModalRect(guiX2, guiY2, 0, 0, guiWidth, guiHeight);
			 fontRendererObj.drawString(b, guiX2 + 35, guiY2 + 55, 0x000000);
			 
			 fontRendererObj.drawString("Stored Experience: " + this.Barracks.getStoredXP() + " Levels", guiX2 + 35, guiY2 + 75, 0x000000);
			 
			 fontRendererObj.drawString("Total Gold: " + PlayerData.get(Player).getMana(), guiX2 + 100, guiY2 + 30, 0x8b0000);
	
		 }if(ShopScreen){
			 mc.renderEngine.bindTexture(Background2);
			 drawTexturedModalRect(guiX2 + 10, guiY2 + 10, 0, 0, guiWidth2, guiHeight2);
			 fontRendererObj.drawString("Stored Experience: " + this.Barracks.getStoredXP() + " Levels", guiX + 30, guiY - 10, 0x8b0000);
			 fontRendererObj.drawString("I can store experience for you" , guiX - 140, guiY + 10, 0xffffff);
		
			 fontRendererObj.drawString("Withdraw Levels: ", guiX + 55 , guiY + 25, 0x000000);
			 
			 fontRendererObj.drawString("Deposit Levels: ", guiX + 55, guiY + 75, 0x000000);
			 
		 }if(QuestScreen){
			 mc.renderEngine.bindTexture(Background2);
			 drawTexturedModalRect(guiX2 + 10, guiY2 + 10, 0, 0, guiWidth2, guiHeight2);
			 fontRendererObj.drawString("Stored Experience: " + this.Barracks.getStoredXP() + " Levels", guiX + 30, guiY - 10, 0x8b0000);
			 fontRendererObj.drawString("I can grant buffs using the" , guiX - 120, guiY + 10, 0xffffff);
			 fontRendererObj.drawString("stored experience", guiX - 120 , guiY +20, 0xffffff);
			 
			 fontRendererObj.drawString("Cost: 15 levels", guiX + 90 , guiY + 25, 0x000000);
			 fontRendererObj.drawString("Cost: 15 levels", guiX + 90 , guiY + 55, 0x000000);
			 fontRendererObj.drawString("Cost: 15 levels", guiX + 90 , guiY + 85, 0x000000);
			 fontRendererObj.drawString("Cost: 15 levels", guiX + 90 , guiY + 115, 0x000000);
							 
			 
		 	}
		}
		
		super.drawScreen(x, y, ticks);
		 
	}
	
	public static String WhatToSay1() {
		int a = rand.nextInt(8) + 1;
	
		switch(a){
			case 1:
				b = "How are you?";
				break;
			case 2:
				b = "What can I do for you?";
				break;
			case 3:
				b = "What is it?";
				break;
			case 4:
				b = "Make this Quick!";
				break;
			case 5:
				b = "Oh you Again!";
				break;
			case 6:
				b = "How are you doing?";
				break;
			case 7:
				b = "You look tired, how can i help";
				break;
			default:
				b = "Alright...";
		}
		return b;
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
		if(GreetingScreen){
			buttonList.add(ShopButton = new GuiButton(0, guiX + 30, guiY + 140, 80, 20, "Store Experience"));
			buttonList.add(TalkButton = new GuiButton(1, guiX + 30, guiY + 170, 80, 20, "Recieve Blessings"));
			buttonList.add(NevermindButton = new GuiButton(2, guiX + 30, guiY + 200, 80, 20, "Nevermind"));
		}if(ShopScreen){
			guiX = (width - 173)/2;
			guiY = (height - 183)/2;
			buttonList.add(itemOne = new GuiButton(3, guiX + 35, guiY + 40, 30, 15, "- 1"));
			buttonList.add(itemOne = new GuiButton(4, guiX + 75, guiY + 40, 30, 15, "- 5"));
			buttonList.add(itemOne = new GuiButton(5, guiX + 115, guiY + 40, 30, 15, "- 10"));
			
			buttonList.add(itemOne = new GuiButton(6, guiX + 35, guiY + 90, 30, 15, "+ 1"));
			buttonList.add(itemOne = new GuiButton(7, guiX + 75, guiY + 90, 30, 15, "+ 5"));
			buttonList.add(itemOne = new GuiButton(8, guiX + 115, guiY + 90, 30, 15, "+ 10"));
			
			
			
			
			//buttonList.add(itemOne = new GuiButton(9, guiX + 25, guiY + 50, 150, 20, ""));
			
			//targetedEntity.addPotionEffect((new PotionEffect(Potion.confusion.getId(), 80, 2)));
			
		}
		if(QuestScreen){
			guiX = (width - 173)/2;
			guiY = (height - 183)/2;
			buttonList.add(itemTwo = new GuiButton(9, guiX + 25, guiY + 20, 60, 20, "Haste"));
			buttonList.add(itemTwo = new GuiButton(10, guiX + 25, guiY + 50, 60, 20, "Strength"));
			buttonList.add(itemTwo = new GuiButton(11, guiX + 25, guiY + 80, 60, 20, "Night vision"));
			buttonList.add(itemTwo = new GuiButton(12, guiX + 25, guiY + 110, 60, 20, "Speed"));
			
			
		//support/mage as final 
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
			case 1:
				GreetingScreen = false;
				ShopScreen = false;
				QuestScreen = true;
				initGui();
				break;
			case 2:
				Player.closeScreen();
				break;
			case 3:
				if(this.Barracks.getStoredXP() - 1 >= 0)
				{
					this.Barracks.decreaseStoredXP(1);
					this.Player.addExperienceLevel(1);
					
				}
				break;
			case 4:
				if(this.Barracks.getStoredXP() - 5 >= 0)
				{
					this.Barracks.decreaseStoredXP(5);
					this.Player.addExperienceLevel(5);
				}
				break;
			case 5:
				if(this.Barracks.getStoredXP() - 10 >= 0)
				{
					this.Barracks.decreaseStoredXP(10);
					this.Player.addExperienceLevel(10);
				}
				break;
			case 6:
				
				if(this.Player.experienceLevel >= 1)
				{
					this.Barracks.increaseStoredXP(1);
					this.Player.experienceLevel-=1;
					this.Player.addExperience(1);
					
				}
				break;
			case 7:
				if(this.Player.experienceLevel >= 5)
				{
					this.Barracks.increaseStoredXP(5);
					this.Player.experienceLevel-=5;
					this.Player.addExperience(1);
				}
				break;
			case 8:
				if(this.Player.experienceLevel >= 10)
				{
					this.Barracks.increaseStoredXP(10);
					this.Player.experienceLevel-=10;
					this.Player.addExperience(1);
				}
				break;
			case 9:
				//haste
				if(this.Barracks.StoredXP >= 15)
				{
					this.Barracks.decreaseStoredXP(15);
				
					this.Player.addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), 12000, 1));
				}
				break;
			case 10:
				//strength
				if(this.Barracks.StoredXP >= 15)
				{
					this.Barracks.decreaseStoredXP(15);
				
					this.Player.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), 12000, 2));
				}
				break;
			case 11:
				//night vision
				if(this.Barracks.StoredXP >= 15)
				{
					this.Barracks.decreaseStoredXP(15);
					
					this.Player.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 12000, 2));
				}
				break;
			case 12:
				//speed
				if(this.Barracks.StoredXP >= 15)
				{
					this.Barracks.decreaseStoredXP(15);

					this.Player.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 12000, 2));
				}
				break;
				
					
			default:
		}
		
		super.actionPerformed(Button);
	}

}