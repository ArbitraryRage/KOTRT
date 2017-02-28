package com.waabbuffet.kotrt.gui;

import com.waabbuffet.kotrt.util.PlayerData;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiGoldCurrency extends GuiScreen {

	
	
	public GuiGoldCurrency() {
	
		
		
		
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void Gui(RenderGameOverlayEvent.Post event) {
		
		if(event.getType() == ElementType.EXPERIENCE)
		{
			EntityPlayer Player = Minecraft.getMinecraft().thePlayer;
			ScaledResolution Resolution = event.getResolution();
			
			int guiX = (Resolution.getScaledWidth() + 8)/2;
			int guiY = (Resolution.getScaledHeight() + 8)/2;
			
			GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
			
		
			
			Minecraft.getMinecraft().fontRendererObj.drawString("" +PlayerData.get(Player).getMana(), guiX - 167, guiY + 113, 0xffffff); 
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("kotrt:textures/items/goldCoin.png"));	
			this.drawModalRectWithCustomSizedTexture(guiX - 180, guiY + 112, 4, 5, 12, 10, 20, 20);
		
		}
		
		super.initGui();
	}
	
	
	
	
	
	
	
}
