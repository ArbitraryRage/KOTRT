package com.waabbuffet.kotrt.gui.kingdom;

import java.io.IOException;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.waabbuffet.kotrt.entities.Kingdom.barracks.EntityBarracks;
import com.waabbuffet.kotrt.packet.PacketHandler;
import com.waabbuffet.kotrt.packet.structure.SpawnWarrior;
import com.waabbuffet.kotrt.packet.structure.UpdateGold;
import com.waabbuffet.kotrt.util.PlayerData;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiKingdomEntityBarracks extends GuiScreen{
	

	public static final ResourceLocation Background1 = new ResourceLocation("kotrt:textures/gui/container/FirstGUI1.png");
	public static final ResourceLocation Background2 = new ResourceLocation("kotrt:textures/gui/container/GUIShopMaybe.png");
	
	//firstGUI starts at 0 0 ends  175 183
	int guiWidth = 256, guiWidth2 = 256;
	int guiHeight = 256, guiHeight2 = 256;
	
	public EntityPlayer Player;
	public EntityBarracks Barracks;
	public World World;
	
	static Random rand = new Random();
	static String b;
	
	
	boolean GreetingScreen = true;
	boolean ShopScreen = false;
	boolean QuestScreen = false;
	
	GuiButton ShopButton, TalkButton, NevermindButton, itemOne, itemTwo, itemThree, itemFour;

	
	
	public GuiKingdomEntityBarracks(EntityBarracks barracks, EntityPlayer entityPlayer, World world) {
		
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
			 
			 fontRendererObj.drawString("Warrior Troops: " + PlayerData.get(Player).getWarriorTroops(), guiX2 + 35, guiY2 + 75, 0x000000);
			 fontRendererObj.drawString("Hunter Troops: " +  PlayerData.get(Player).getHunterTroops(), guiX2 + 35, guiY2 + 85, 0x000000);
			 fontRendererObj.drawString("Mage Troops: " +  PlayerData.get(Player).getMageTroops(), guiX2 + 35, guiY2 + 95, 0x000000);
			 fontRendererObj.drawString("Shadow Warrior Troops: " +  PlayerData.get(Player).getShadowWarriorTroops(), guiX2 + 35, guiY2 + 105, 0x000000);
			
		
			 fontRendererObj.drawString("Total Gold: " + PlayerData.get(Player).getMana(), guiX2 + 100, guiY2 + 30, 0x8b0000);
	
		 }if(ShopScreen){
			 mc.renderEngine.bindTexture(Background2);
			 drawTexturedModalRect(guiX2 + 10, guiY2 + 10, 0, 0, guiWidth2, guiHeight2);
			 fontRendererObj.drawString("Your Gold is:" + PlayerData.get(Player).getMana(), guiX + 50, guiY - 10, 0x8b0000);
			 fontRendererObj.drawString("I can train troops for you." , guiX - 120, guiY + 10, 0xffffff);
			 fontRendererObj.drawString("(Left Click to Purchase)", guiX - 120 , guiY +20, 0xffffff);

			 fontRendererObj.drawString("Warrior Troops: " + PlayerData.get(Player).getWarriorTroops(), guiX - 120 , guiY + 40, 0xffffff);
			 fontRendererObj.drawString("Hunter Troops: " +  PlayerData.get(Player).getHunterTroops(), guiX - 120 , guiY + 50, 0xffffff);
			 fontRendererObj.drawString("Mage Troops: " +  PlayerData.get(Player).getMageTroops(), guiX - 120 , guiY + 60, 0xffffff);
			 fontRendererObj.drawString("Shadow Warrior Troops: " +  PlayerData.get(Player).getShadowWarriorTroops(), guiX - 120 , guiY + 70, 0xffffff);
			
		 }if(QuestScreen){
			 mc.renderEngine.bindTexture(Background2);
			 drawTexturedModalRect(guiX2 + 10, guiY2 + 10, 0, 0, guiWidth2, guiHeight2);
			 fontRendererObj.drawString("Your Gold is: " + PlayerData.get(Player).getMana(), guiX + 50, guiY - 10, 0x8b0000);
			 fontRendererObj.drawString("I can train troops for you." , guiX - 120, guiY + 10, 0xffffff);
			 fontRendererObj.drawString("(Left Click to Purchase)", guiX - 120 , guiY +20, 0xffffff);
			 
			 fontRendererObj.drawString("Warrior Troops: " + PlayerData.get(Player).getWarriorTroops(), guiX - 120 , guiY + 40, 0xffffff);
			 fontRendererObj.drawString("Hunter Troops: " +  PlayerData.get(Player).getHunterTroops(), guiX - 120 , guiY + 50, 0xffffff);
			 fontRendererObj.drawString("Mage Troops: " +  PlayerData.get(Player).getMageTroops(), guiX - 120 , guiY + 60, 0xffffff);
			 fontRendererObj.drawString("Shadow Warrior Troops: " +  PlayerData.get(Player).getShadowWarriorTroops(), guiX - 120 , guiY + 70, 0xffffff);
			
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
			buttonList.add(ShopButton = new GuiButton(0, guiX + 30, guiY + 140, 80, 20, "Train Troop"));
			buttonList.add(TalkButton = new GuiButton(1, guiX + 30, guiY + 170, 80, 20, "Control Troops"));
			buttonList.add(NevermindButton = new GuiButton(2, guiX + 30, guiY + 200, 80, 20, "Nevermind"));
		}if(ShopScreen){
			guiX = (width - 173)/2;
			guiY = (height - 183)/2;
	//		buttonList.add(itemOne = new GuiButton(3, guiX + 25, guiY + 20, 150, 20, "Train Warrior = 7000"));
			buttonList.add(itemOne = new GuiButton(9, guiX + 25, guiY + 50, 150, 20, "Train Hunter = 10000"));
			buttonList.add(itemOne = new GuiButton(3, guiX + 25, guiY + 80, 150, 20, "Train Shadow Warrior = 15000"));
			buttonList.add(itemOne = new GuiButton(11, guiX + 25, guiY + 130, 150, 20, "Train Mage = 20000"));
			
		}
		if(QuestScreen){
			guiX = (width - 173)/2;
			guiY = (height - 183)/2;
		//	buttonList.add(itemTwo = new GuiButton(4, guiX + 25, guiY + 20, 150, 20, "Withdraw Warrior"));
			buttonList.add(itemTwo = new GuiButton(6, guiX + 25, guiY + 50, 150, 20, "Withdraw Hunter"));
			buttonList.add(itemTwo = new GuiButton(4, guiX + 25, guiY + 80, 150, 20, "Withdraw Shadow Warrior"));
			buttonList.add(itemTwo = new GuiButton(8, guiX + 25, guiY + 110, 150, 20, "Withdraw Mage"));
			
		//	buttonList.add(itemThree = new GuiButton(5, guiX + 190, guiY + 20, 80, 20, "Deposit 1 Warrior"));
			buttonList.add(itemThree = new GuiButton(12, guiX + 190, guiY + 50, 80, 20, "Deposit 1 Hunter"));
			buttonList.add(itemThree = new GuiButton(5, guiX + 190, guiY + 80, 120, 20, "Deposit 1 Shadow Warrior"));
			buttonList.add(itemThree = new GuiButton(14, guiX + 190, guiY + 110, 80, 20, "Deposit 1 Mage"));
			buttonList.add(itemThree = new GuiButton(15, guiX + 190, guiY + 140, 80, 20, "Deposit All"));
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
				
				if(PlayerData.get(Player).getMana() >= 15000){
					PlayerData.get(Player).increaseMana(-15000);
					PlayerData.get(Player).increaseWarriorTroops(1);
					PacketHandler.INSTANCE.sendTo(new UpdateGold(PlayerData.get(Player).getMana()), (EntityPlayerMP) Player);
				}
		//		KOTRTcore.network.sendToServer(new Packeterino("TrainTroop"));
				break;
			case 4:
				PacketHandler.INSTANCE.sendToServer(new SpawnWarrior(0, true ,(int)this.Player.posX, (int)this.Player.posY,(int) this.Player.posZ));
				
			//	KOTRTcore.network.sendToServer(new Packeterino("WithdrawTroop"));
				break;
			case 5:
				
				PacketHandler.INSTANCE.sendToServer(new SpawnWarrior(0, false ,(int)this.Player.posX, (int)this.Player.posY,(int) this.Player.posZ));
			//	KOTRTcore.network.sendToServer(new Packeterino("DepositTroop"));
				break;
			case 6:
				PacketHandler.INSTANCE.sendToServer(new SpawnWarrior(1, true ,(int)this.Player.posX, (int)this.Player.posY,(int) this.Player.posZ));
				break;
			case 7:
				break;
			case 8:
				PacketHandler.INSTANCE.sendToServer(new SpawnWarrior(3, true ,(int)this.Player.posX, (int)this.Player.posY,(int) this.Player.posZ));
				break;
			case 9:
				if(PlayerData.get(Player).getMana() >= 10000){
					PlayerData.get(Player).increaseMana(-10000);
					PlayerData.get(Player).increaseHunterTroops(1);
					PacketHandler.INSTANCE.sendTo(new UpdateGold(PlayerData.get(Player).getMana()), (EntityPlayerMP) Player);
				}
				break;
			case 10:
				break;
			case 11:
				if(PlayerData.get(Player).getMana() >= 20000){
					PlayerData.get(Player).increaseMana(-20000);
					PlayerData.get(Player).increaseMageTroops(1);
					PacketHandler.INSTANCE.sendTo(new UpdateGold(PlayerData.get(Player).getMana()), (EntityPlayerMP) Player);
				}
				break;
			case 12:
				PacketHandler.INSTANCE.sendToServer(new SpawnWarrior(1, false ,(int)this.Player.posX, (int)this.Player.posY,(int) this.Player.posZ));
				break;
			case 14:
				PacketHandler.INSTANCE.sendToServer(new SpawnWarrior(3, false ,(int)this.Player.posX, (int)this.Player.posY,(int) this.Player.posZ));
				break;
			case 15:
				PacketHandler.INSTANCE.sendToServer(new SpawnWarrior(5, false ,(int)this.Player.posX, (int)this.Player.posY,(int) this.Player.posZ));
				break;
			default:
		}
		
		super.actionPerformed(Button);
	}

}