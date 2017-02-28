package com.waabbuffet.kotrt.gui.kingdom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.waabbuffet.kotrt.entities.Kingdom.EntityRecruiter;
import com.waabbuffet.kotrt.packet.PacketHandler;
import com.waabbuffet.kotrt.packet.structure.FinishStructure;
import com.waabbuffet.kotrt.packet.structure.UpdateGold;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBlock;
import com.waabbuffet.kotrt.util.PlayerData;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiRecruiter extends GuiScreen{

	
	EntityPlayer Player;
	EntityRecruiter Recruiter;
	World World;
	
	int xSize, ySize, WorkLocationIndex;
	boolean ChangeGoldScreen, SelectWorkPlace, insideWorkPlace;
	GuiButton Recuiter;
	List<TileEntityKingdomStructureBlock> WorkLocation;
	TileEntityKingdomStructureBlock SpecificWorkLocation;
	
	public static final ResourceLocation Background1 = new ResourceLocation("kotrt:textures/gui/container/GUIShopMaybe.png");
	public static final ResourceLocation Background2 = new ResourceLocation("kotrt:textures/items/goldCoin.png");
	
	public GuiRecruiter(EntityPlayer player, EntityRecruiter recruiter, World world) {
		
		this.xSize = 256;
	    this.ySize = 256;
		
		
		Player = player;
		Recruiter = recruiter;
		World = world;
		
		this.ChangeGoldScreen = false;
		
		
	}
	
	@Override
	public void initGui() {
		int guiX = (width - xSize) / 2;
		int guiY = (height - ySize) / 2;
		
		buttonList.clear();
		
		if(!this.ChangeGoldScreen && !this.SelectWorkPlace && !this.insideWorkPlace)
		{
			
			if(this.Recruiter.getRecruit())
				buttonList.add(Recuiter = new GuiButton(0, guiX - 11, guiY + 172, 80, 20, "Accept Offer"));
				
		
			if(!this.Recruiter.getRecruit()){
				
				buttonList.add(Recuiter = new GuiButton(1, guiX - 11, guiY + 142, 80, 20, "Change Bid"));
			}
			     buttonList.add(Recuiter = new GuiButton(6, guiX + 77, guiY + 142, 80, 20, "Cancel Bid"));
			
		}
		
		if(this.ChangeGoldScreen)
		{
			buttonList.add(Recuiter = new GuiButton(2, guiX - 11, guiY + 112, 15, 20, "+1"));
			buttonList.add(Recuiter = new GuiButton(3, guiX + 11, guiY + 112, 20, 20, "+10"));
			buttonList.add(Recuiter = new GuiButton(4, guiX + 41, guiY + 112, 25, 20, "+100"));
			buttonList.add(Recuiter = new GuiButton(5, guiX + 71, guiY + 112, 30, 20, "+1000"));
			
		}
		
	if(this.SelectWorkPlace)
	{
			if(!this.WorkLocation.isEmpty())
			{
				buttonList.add(Recuiter = new GuiButton(-3 , guiX - 11, guiY + 142, 20, 20, " " + "<---"));
				
	
				if(this.WorkLocationIndex * 6 + 6 < this.WorkLocation.size())
				{
					
					buttonList.add(Recuiter = new GuiButton(-4 , guiX - 11, guiY + 160, 20, 20, " " + "--->"));
					for(int i = 0; i < 6; i ++)
					{
						
						buttonList.add(Recuiter = new GuiButton(50 + i + this.WorkLocationIndex * 6 , guiX + 170, guiY + 60 + i * 30, 180, 20,  "Pos: " + this.WorkLocation.get(i + this.WorkLocationIndex*6).getPos().toString().substring(8)));
						//the way we will get which index it is, is by taking worklocation index 
					}
				}else{
					
					for(int i = this.WorkLocationIndex * 6; i < this.WorkLocation.size(); i ++)
					{
						buttonList.add(Recuiter = new GuiButton(50 + i + this.WorkLocationIndex * 6 , guiX + 170, guiY + 50 + (this.WorkLocation.size() -i) * 30, 180, 20,  "Pos: " +  this.WorkLocation.get(i).getPos().toString().substring(8)));
						//the way we will get which index it is, is by taking worklocation index 
					}
				}
				
				
			}
			
			
	}else if(this.insideWorkPlace)
	{
		buttonList.add(Recuiter = new GuiButton(13, guiX + 210, guiY + 200, 115, 20, "Select Location"));
	}
				
		
		super.initGui();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		// TODO Auto-generated method stub
	if(!this.World.isRemote)
	{
		int guiX = (width - xSize) / 2;
		int guiY = (height - ySize) / 2;
		
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		
        this.mc.getTextureManager().bindTexture(Background1);
        this.drawModalRectWithCustomSizedTexture(guiX - 80, guiY + 10, 0, 0, 300, 240, 300, 256);
        
        this.mc.getTextureManager().bindTexture(Background2);
        this.drawModalRectWithCustomSizedTexture(guiX + 88, guiY + 21, 4, 5, 12, 10, 20, 20);
        
        this.drawModalRectWithCustomSizedTexture(guiX + 105, guiY + 50, 4, 5, 12, 10, 20, 20);
        
        this.fontRendererObj.drawString("Current Gold: " +  PlayerData.get(Player).getMana(), guiX - 8, guiY + 22, 0xFF6A00); 
        this.fontRendererObj.drawString("Recruiter Signup:", guiX - 8, guiY + 42, 0x000000); 
        this.fontRendererObj.drawString("Current Offer: " + this.Recruiter.getGoldPutDown(), guiX - 8, guiY + 52, 0x000000); 
        this.fontRendererObj.drawString("Chance to Recruit: " + this.Recruiter.getChanceToRecruit() + " %", guiX - 8, guiY + 62, 0x000000); 
        this.fontRendererObj.drawString("Time Till Next Recruitment: " + ((1200 -this.Recruiter.getRecruitTime())/20) + " sec", guiX - 8, guiY + 72, 0x000000); 
        this.fontRendererObj.drawString("Found Someone! " + this.Recruiter.getRecruit(), guiX - 8, guiY + 82, 0x000000); 
        
       if(!this.insideWorkPlace)
       {
	        this.fontRendererObj.drawString("Note: You must have available ", guiX + 170, guiY + 42, 0xffffff); 
	        this.fontRendererObj.drawString("gold/room to accept a new ", guiX + 170, guiY + 52, 0xffffff); 
	        this.fontRendererObj.drawString("recruit into your kingdom", guiX + 170, guiY + 62, 0xffffff);
       }
        
        if(this.ChangeGoldScreen)
        {
        	this.fontRendererObj.drawString("Click the button to increase the bid", guiX + 170, guiY + 82, 0xffffff);
        }
        
        
        	if(this.insideWorkPlace)
        	{
        		this.fontRendererObj.drawString("Structure's Name: " + this.SpecificWorkLocation.structure.getName(), guiX + 170, guiY + 42, 0xffffff); 
			
				this.fontRendererObj.drawString("Number of Workers:  " + this.SpecificWorkLocation.structure.getCurrentWorkers(), guiX + 170, guiY + 52, 0xffffff); 
				
	    		
	    		this.fontRendererObj.drawString("Coordinates:  ", guiX + 170, guiY + 62, 0xffffff); 
	    		this.fontRendererObj.drawString("X: " + this.SpecificWorkLocation.getPos().getX() + " Y: " + this.SpecificWorkLocation.getPos().getY() + " Z:" + this.SpecificWorkLocation.getPos().getZ(), guiX + 170, guiY + 72, 0xffffff); 
	    		
	    		
        	}
    	
        
	}
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	public boolean DoesPersonHaveRoomAndGold()
	{
		boolean YeaOr = true;
		
		
		if( PlayerData.get(Player).getMana() < this.Recruiter.getGoldPutDown())
		{
			
			return false;
		}
		
		
		
		return YeaOr;
	}
	
	public List<TileEntityKingdomStructureBlock> DetermineWorkLocations()
	{
		List<TileEntityKingdomStructureBlock> B = new ArrayList();
		
		
		for(int i =0; i < World.loadedTileEntityList.size(); i ++)
		{
			System.out.println("" + World.loadedTileEntityList.get(i));
			if(World.loadedTileEntityList.get(i) instanceof TileEntityKingdomStructureBlock)
			{
				
				System.out.println(((TileEntityKingdomStructureBlock)World.loadedTileEntityList.get(i)).structure.getName());
				if(((TileEntityKingdomStructureBlock)World.loadedTileEntityList.get(i)).structure.getName() != null)
				{
					
					if(((TileEntityKingdomStructureBlock)World.loadedTileEntityList.get(i)).structure.getName().contains("Small House")){
						if(((TileEntityKingdomStructureBlock)World.loadedTileEntityList.get(i)).structure.getCurrentWorkers() < 4)
							B.add((TileEntityKingdomStructureBlock) World.loadedTileEntityList.get(i));
					}
					
				}
			}
		}
		return B;
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
			//Accept offer (Need to bring over the same way you assign a farmer a work place...this will tp the farmer over,) maybe add a check space button
			if(this.DoesPersonHaveRoomAndGold()){
			
				
				this.WorkLocation = this.DetermineWorkLocations();
				this.SelectWorkPlace = true;
				this.ChangeGoldScreen = false;
				this.initGui();
			}
			
			
		}else if(button.id == 1)
		{
			//Change Bid
			this.ChangeGoldScreen = true;
			this.initGui();
		}else if(button.id == -4)
		{
	
			//choose job
			if(!this.World.isRemote)
			{
				if(this.WorkLocationIndex < this.WorkLocation.size())
					this.WorkLocationIndex++;	
		
			}
			this.initGui();
		}else if(button.id == -3)
		{
			//choose job
		if(!this.World.isRemote)
		{
			if(this.WorkLocationIndex > 0)
				this.WorkLocationIndex--;
		}	
		this.initGui();
		}else if(button.id == 2)
		{
			
			this.Recruiter.setGoldPutDown(this.Recruiter.getGoldPutDown() + 1);
		}else if(button.id == 3)
		{
			this.Recruiter.setGoldPutDown(this.Recruiter.getGoldPutDown() + 10);
		}else if(button.id == 4)
		{
			this.Recruiter.setGoldPutDown(this.Recruiter.getGoldPutDown() + 100);
		}else if(button.id == 5)
		{
			this.Recruiter.setGoldPutDown(this.Recruiter.getGoldPutDown() + 1000);
		}else if(button.id == 13)
		{
			//sned packet to spawn dude
			PacketHandler.INSTANCE.sendToServer(new FinishStructure(this.SpecificWorkLocation.getPos().getX(),this.SpecificWorkLocation.getPos().getY(), this.SpecificWorkLocation.getPos().getZ() ));
			
			PlayerData.get(Player).increaseMana(-this.Recruiter.getGoldPutDown());
			PacketHandler.INSTANCE.sendTo(new UpdateGold(PlayerData.get(Player).getMana()), (EntityPlayerMP) Player);
			
			this.Recruiter.setGoldPutDown(0);
			this.Recruiter.setChanceToRecruit(0);
			this.Recruiter.setRecruit(false);
			this.SelectWorkPlace = false;
			this.insideWorkPlace = false;
			initGui();
			
		}else if(button.id == 6)
		{
			this.Recruiter.setGoldPutDown(0);
			this.Recruiter.setChanceToRecruit(0);
			this.Recruiter.setRecruit(false);
			initGui();
		}else if(button.id >= 50)
		{
			int TEID = button.id - this.WorkLocationIndex*6 - 50;
			this.SpecificWorkLocation = this.WorkLocation.get(TEID);

			this.SelectWorkPlace = false;
			this.insideWorkPlace = true;
			initGui();
		}
					
		
		
		
		super.actionPerformed(button);
	}
	
	
	
	
	
	
}
