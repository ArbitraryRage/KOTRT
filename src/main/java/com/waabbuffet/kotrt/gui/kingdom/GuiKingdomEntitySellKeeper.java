package com.waabbuffet.kotrt.gui.kingdom;


import java.io.IOException;

import com.waabbuffet.kotrt.entities.Kingdom.EntityShopKeeper;
import com.waabbuffet.kotrt.entities.Outpost.EntitySellShopKeeper;
import com.waabbuffet.kotrt.packet.PacketHandler;
import com.waabbuffet.kotrt.packet.structure.GiveItemToPlayer;
import com.waabbuffet.kotrt.packet.structure.UpdateGold;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBlock;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBuilderBlock;
import com.waabbuffet.kotrt.util.PlayerData;
import com.waabbuffet.kotrt.util.ShopKeeperItemFormat;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiKingdomEntitySellKeeper extends GuiScreen{

	

	EntitySellShopKeeper ShopKeeper;
	EntityPlayer Player;
	World World;
	
	ShopKeeperItemFormat SaleItems[];
	int WhichScreen, xSize, ySize;
	
	GuiButton Purchase;
	
	public static final ResourceLocation Background1 = new ResourceLocation("kotrt:textures/gui/container/SellShopKeeperGUI.png");
	public static final ResourceLocation Background2 = new ResourceLocation("kotrt:textures/items/goldCoin.png");


	public GuiKingdomEntitySellKeeper(EntitySellShopKeeper shopKeeper, EntityPlayer player, World world, ShopKeeperItemFormat saleItems[]) {
		
		this.xSize = 256;
	    this.ySize = 256;
		
		ShopKeeper = shopKeeper;
		Player = player;
		World = world;
		
		SaleItems = saleItems;
		
		this.WhichScreen = 0;
	}


	@Override
	public void initGui() {
		int guiX = (width - xSize) / 2;
		int guiY = (height - ySize) / 2;
		
		buttonList.clear();
		
		buttonList.add(Purchase = new GuiButton(0, guiX - 45, guiY + 122, 15, 20, "<-"));
		
		buttonList.add(Purchase = new GuiButton(1, guiX + 125, guiY + 122, 15, 20, "->"));
		
		for(int i = 0; i < 5; i ++)
		{
			if(i != 1 && i != 3)
			buttonList.add(Purchase = new GuiButton(2 + i, guiX - 21 + i * 30, guiY + 100, 16, 10, "+"));
		}
		
		for(int i = 0; i < 5; i ++)
		{
			if(i != 1 && i != 3)
			buttonList.add(Purchase = new GuiButton(7 + i, guiX - 21 + i * 30, guiY + 142, 16, 10, "+"));
		}
		
		for(int i = 0; i < 5; i ++)
		{
			if(i != 1 && i != 3)
			buttonList.add(Purchase = new GuiButton(12 + i, guiX - 21 + i * 30, guiY + 185, 16, 10, "+"));
		}
	//	buttonList.add(Purchase = new GuiButton(2, guiX + 9, guiY + 100, 16, 10, "+")); //30
		
		super.initGui();
	}
	
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		int guiX = (width - xSize) / 2;
		int guiY = (height - ySize) / 2;
		
		
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		
        this.mc.getTextureManager().bindTexture(Background1);
        this.drawModalRectWithCustomSizedTexture(guiX - 80, guiY, 0, 0, 256, 230, 256, 256);
		
        this.mc.getTextureManager().bindTexture(Background2); // 4, 5 - > 12, 10
     
       
        RenderHelper.enableGUIStandardItemLighting();
   
        if(!this.World.isRemote)
        {
        //Gold on top of items
        for(int j = 0; j < 5; j ++){
        	
        	if(j != 1 && j != 3)
        		this.drawModalRectWithCustomSizedTexture(guiX - 12 + 30 * j, guiY + 68, 4, 5, 12, 10, 20, 20);
        }
        
        for(int j = 0; j < 5; j ++){
        	if(j != 1 && j != 3)
        		this.drawModalRectWithCustomSizedTexture(guiX - 12 + 30 * j, guiY + 111, 4, 5, 12, 10, 20, 20);
        }
        
        for(int j = 0; j < 5; j ++){
        	if(j != 1 && j != 3)
        	this.drawModalRectWithCustomSizedTexture(guiX - 12 + 30 * j, guiY + 154, 4, 5, 12, 10, 20, 20);
        }
        
        
     for(int j = 0; j < 5; j ++){
        	if(this.SaleItems[j + 15*this.WhichScreen] != null)
        	itemRender.renderItemAndEffectIntoGUI(this.SaleItems[j + 15*this.WhichScreen].getItem(), guiX - 20 + 30*j, guiY + 82);
     }    
       
     for(int j = 0; j < 5; j ++){
     	
     	if(this.SaleItems[j + 5 + 15*this.WhichScreen] != null)
     		itemRender.renderItemAndEffectIntoGUI(this.SaleItems[j + 5 + 15*this.WhichScreen].getItem(), guiX - 21 + 30*j, guiY + 123);
     	
     }
     
     for(int j = 0; j < 5; j ++){
     	if(this.SaleItems[j + 10 + 15*this.WhichScreen] != null)
     		itemRender.renderItemAndEffectIntoGUI(this.SaleItems[j + 10 + 15*this.WhichScreen].getItem(), guiX - 20 + 30*j, guiY + 166);
     	
     }   
       
       
        
        this.fontRendererObj.drawString("Shopkeeper Shop: Page #" + this.WhichScreen, guiX - 18, guiY + 42, 0x000000); 
        this.fontRendererObj.drawString("Total Gold: " + PlayerData.get(Player).getMana(), guiX, guiY + 22, 0xffffff); 
        
        
        
       
        
        this.fontRendererObj.drawString("Welcome to my Shop! Here I buy various", guiX + 155, guiY + 35, 0xffffff); 
        this.fontRendererObj.drawString("goods at different prices.", guiX + 155, guiY + 45, 0xffffff); 
        this.fontRendererObj.drawString("Holding Shift will sell 16 items", guiX + 155, guiY + 65, 0xffffff); 
        this.fontRendererObj.drawString("Holding Control will sell 64 items ", guiX + 155, guiY + 75, 0xffffff); 
     
        //Cost on top of items //x moves 30, Y moves 43
        for(int i = 0; i < 5; i ++)
        {
        	if(this.SaleItems[i + 15*this.WhichScreen] != null)
        		this.fontRendererObj.drawString("" +this.SaleItems[i + 15*this.WhichScreen].getCost(), guiX - 26 + 30 * i, guiY + 70, 0xFF6A00); 
        }
        
        for(int i = 0; i < 5; i ++)
        {
        	if(this.SaleItems[i + 5 + 15*this.WhichScreen] != null)
        		this.fontRendererObj.drawString("" +this.SaleItems[i + 5 + 15*this.WhichScreen].getCost(), guiX - 26 + 30 * i, guiY + 113, 0xFF6A00); 
        }
        
        for(int i = 0; i < 5; i ++)
        {
        	if(this.SaleItems[i + 10 + 15*this.WhichScreen] != null)
        		this.fontRendererObj.drawString("" +this.SaleItems[i + 10 + 15*this.WhichScreen].getCost(), guiX - 26 + 30 * i, guiY + 156, 0xFF6A00); 
        }
        
		super.drawScreen(mouseX, mouseY, partialTicks);
        }
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
	
		if(button.id == 0)
		{
			//Go Forward >
			if(this.WhichScreen > 0)
			{
				
				this.WhichScreen --;
				initGui();
			}
		}else if(button.id == 1)
		{
			//Go Backward <
			
			if(this.WhichScreen < 3)
			{
				this.WhichScreen ++;
				initGui();
			}
			
			
		}else if(button.id >= 2)
		{
			int buttonID = button.id - 2 + this.WhichScreen* 15;
			
			if(!this.isCtrlKeyDown() && !this.isShiftKeyDown())
				PacketHandler.INSTANCE.sendToServer(new GiveItemToPlayer(Item.getIdFromItem(this.SaleItems[buttonID].getItem().getItem()), 1,this.SaleItems[buttonID].getCost(), this.SaleItems[buttonID].getItem().getMetadata(), true));
				
			
			
			if(this.isCtrlKeyDown())
			{
				PacketHandler.INSTANCE.sendToServer(new GiveItemToPlayer(Item.getIdFromItem(this.SaleItems[buttonID].getItem().getItem()), 64,this.SaleItems[buttonID].getCost(), this.SaleItems[buttonID].getItem().getMetadata(), true));
			}
			
			if(this.isShiftKeyDown())
			{
				PacketHandler.INSTANCE.sendToServer(new GiveItemToPlayer(Item.getIdFromItem(this.SaleItems[buttonID].getItem().getItem()), 16,this.SaleItems[buttonID].getCost(), this.SaleItems[buttonID].getItem().getMetadata(), true));
			}
			
		
			
		}
		
		
		super.actionPerformed(button);
	}


	

}
