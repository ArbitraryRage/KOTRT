package com.waabbuffet.kotrt.gui.kingdom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import com.waabbuffet.kotrt.entities.Kingdom.EntityFarmer;
import com.waabbuffet.kotrt.gui.GuiButtonCheckBox;
import com.waabbuffet.kotrt.packet.PacketHandler;
import com.waabbuffet.kotrt.packet.structure.ChangeKingdomVillagerInformation;
import com.waabbuffet.kotrt.packet.structure.UpdateClientTileInv;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBlock;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBuilderBlock;
import com.waabbuffet.kotrt.util.CraftingAIFormat;

public class GuiKingdomEntityBase extends GuiScreen {

	//Base Will Have option to set Workhome, Start working, and another ting
	
	GuiButton Working;
	GuiButtonCheckBox b, b1;
	EntityFarmer Farmer;
	EntityPlayer Player;
	
	World World;
	String JobName;
	
	List<TileEntityKingdomStructureBlock> WorkLocation;
	List<TileEntityKingdomStructureBuilderBlock> WorkLocation1;
	List<CraftingAIFormat> CraftingItems = new ArrayList();
	TileEntityKingdomStructureBlock SpecificWorkLocation;
	
	boolean ChooseJob, insideJob, ChooseWorkLocation, insideWorkLocation, ExtraStuff, MailFirstTE, MailSecTE, MailThirdTE, MailFourthTE, MailFifthTE, SourceLocation, Workwork;
	public static final ResourceLocation Background1 = new ResourceLocation("kotrt:textures/gui/container/FirstGUI1.png");
	
	int xSize, ySize, WorkLocationIndex;
	
	public GuiKingdomEntityBase(EntityPlayer player, EntityFarmer farmer, World world) {
		// TODO Auto-generated constructor stub
		
		Player = player;
		this.Farmer = farmer;
		this.World = world;
		
		this.xSize = 256;
	    this.ySize = 256;
	  
	    this.ChooseJob = false;
	    this.insideJob = false;
	    this.ChooseWorkLocation = false;
	    this.insideWorkLocation = false;
	    this.ExtraStuff = false;
	    this.WorkLocationIndex = 0;
	}
		
	@Override
	public void initGui() {
		// TODO buttons
	
		int guiX = (width - xSize) / 2;
		int guiY = (height - ySize) / 2;
		
		buttonList.clear();
		
		buttonList.add(b = new GuiButtonCheckBox(-1, guiX + 150, guiY +  9));
		buttonList.add(b1 = new GuiButtonCheckBox(-2, guiX + 240, guiY + 9));
		
		
		
		if(!this.ChooseJob && !this.ChooseWorkLocation && !this.ExtraStuff)
		{
			buttonList.add(Working = new GuiButton(0, guiX - 20, guiY + 130, 85, 20, "Choose Job"));
			buttonList.add(Working = new GuiButton(1, guiX - 20, guiY + 160, 85, 20, "Set Work Home"));
			if(!this.Farmer.getStartJob())
			{
				if(this.Farmer.getJob() != null && this.Farmer.getWorkPlace() != null)
					buttonList.add(Working = new GuiButton(2, guiX - 20, guiY + 190, 85, 20, "Start Job"));
			}else{
				buttonList.add(Working = new GuiButton(-6, guiX + 80, guiY + 160, 85, 20, "Configure Job"));
				buttonList.add(Working = new GuiButton(-5, guiX - 20, guiY + 190, 85, 20, "Cancel Job"));
			}
		}
		
		if(this.ChooseJob)
		{
			buttonList.add(Working = new GuiButton(3, guiX - 20, guiY + 130, 85, 20, "<-- Back"));
			
			if(!this.insideJob)
			{
				buttonList.add(Working = new GuiButton(4, guiX + 160, guiY + 70, 95, 20, "Miner"));
				buttonList.add(Working = new GuiButton(5, guiX + 260, guiY + 70, 95, 20, "Tree Cutter"));
				buttonList.add(Working = new GuiButton(6, guiX + 160, guiY + 100, 95, 20, "Wheat Farmer"));
				buttonList.add(Working = new GuiButton(7, guiX + 260, guiY + 100, 95, 20, "Melon/Reed Farmer"));
				buttonList.add(Working = new GuiButton(8, guiX + 160, guiY + 130, 95, 20, "Mail Man"));
				buttonList.add(Working = new GuiButton(9, guiX + 260, guiY + 130, 95, 20, "Carpenter"));
				buttonList.add(Working = new GuiButton(10, guiX + 160, guiY + 160, 95, 20, "Builder"));
				buttonList.add(Working = new GuiButton(11, guiX + 260, guiY + 160, 95, 20, "Storage Keeper"));
				buttonList.add(Working = new GuiButton(18, guiX + 160, guiY + 190, 95, 20, "Blacksmith"));
				
				
				//add blacksmith, and forge and tier three stuff
			}
			if(this.insideJob)
			{
				buttonList.add(Working = new GuiButton(12, guiX + 210, guiY + 200, 95, 20, "Select Job"));
			}
			
			
		}else if(this.ChooseWorkLocation)
		{
			
			
		if(!this.insideWorkLocation)
		{
			if(!this.WorkLocation.isEmpty())
			{
				buttonList.add(Working = new GuiButton(-3 , guiX - 20, guiY + 130, 20, 20, " " + "<---"));
				

				if(this.WorkLocationIndex * 6 + 6 < this.WorkLocation.size())
				{
					
					buttonList.add(Working = new GuiButton(-4 , guiX - 20, guiY + 160, 20, 20, " " + "--->"));
					for(int i = 0; i < 6; i ++)
					{
						
						buttonList.add(Working = new GuiButton(50 + i + this.WorkLocationIndex * 6 , guiX + 160, guiY + 30 + i * 30, 80, 20,  "Pos:" + this.WorkLocation.get(i + this.WorkLocationIndex*6).getPos().toString().substring(8)));
						//the way we will get which index it is, is by taking worklocation index 
					}
				}else{
					
					for(int i = this.WorkLocationIndex * 6; i < this.WorkLocation.size(); i ++)
					{
						buttonList.add(Working = new GuiButton(50 + i + this.WorkLocationIndex * 6 , guiX + 160, guiY + 30 + (this.WorkLocation.size() -i) * 30, 80, 20,  "Pos:" +  this.WorkLocation.get(i).getPos().toString().substring(8)));
						//the way we will get which index it is, is by taking worklocation index 
					}
				}
			}
			
				
			}else if (this.insideWorkLocation){
				buttonList.add(Working = new GuiButton(13, guiX + 210, guiY + 200, 95, 20, "Select Location"));
			}
		
		
			
		}
		
		if(this.ExtraStuff)
		{
		
			if(this.Farmer.getJob().contains("Carpenter") ||this.Farmer.getJob().contains("Blacksmith"))
			{
				
				
				buttonList.add(Working = new GuiButton(-8 , guiX - 20, guiY + 130, 20, 20, " " + "<---"));// change
			
				if(this.WorkLocationIndex * 6 + 6 < CraftingItems.size())
				{
					buttonList.add(Working = new GuiButton(-7 , guiX - 20, guiY + 160, 20, 20, " " + "--->")); // change
					
					for(int i = 0; i < 6; i ++)
					{
						
						buttonList.add(Working = new GuiButton(50 + i + this.WorkLocationIndex * 6 , guiX + 160, guiY + 30 + i * 30, 80, 20, "" + CraftingItems.get(i + this.WorkLocationIndex*6).getProduced().getDisplayName()));
						//the way we will get which index it is, is by taking worklocation index 
					}
				}else{
					
					for(int i = this.WorkLocationIndex * 6; i < CraftingItems.size(); i ++)
					{
						buttonList.add(Working = new GuiButton(50 + i + this.WorkLocationIndex * 6 , guiX + 160, guiY + 30 + (CraftingItems.size() -i) * 30, 80, 20,  "" + CraftingItems.get(i).getProduced().getDisplayName()));
						//the way we will get which index it is, is by taking worklocation index 
					}
				}
			}else if(this.Farmer.getJob().contains("Messanger Guild"))
			{
				buttonList.add(Working = new GuiButton(14, guiX - 20, guiY + 110, 100, 20, "Choose First Location"));
				buttonList.add(Working = new GuiButton(15, guiX - 20, guiY + 140, 100, 20, "Choose Second Location"));
				buttonList.add(Working = new GuiButton(16, guiX - 20, guiY + 170, 100, 20, "Choose Third Location"));
				buttonList.add(Working = new GuiButton(17, guiX - 20, guiY + 200, 100, 20, "Choose Fourth Location"));
				
				buttonList.add(Working = new GuiButton(21, guiX - 20, guiY + 230, 100, 20, "Choose DropOff Location"));
				
				if(this.MailFirstTE || this.MailSecTE || this.MailThirdTE || this.MailFourthTE || this.MailFifthTE)
				{
					buttonList.add(Working = new GuiButton(-3 , guiX + 100, guiY + 130, 20, 20, " " + "<---"));
					

					if(this.WorkLocationIndex * 6 + 6 < this.WorkLocation.size())
					{
						
						buttonList.add(Working = new GuiButton(-4 , guiX + 100, guiY + 160, 20, 20, " " + "--->"));
						for(int i = 0; i < 6; i ++)
						{
							
							buttonList.add(Working = new GuiButton(50 + i + this.WorkLocationIndex * 6 , guiX + 160, guiY + 30 + i * 30, 180, 20,  "Pos: " + this.WorkLocation.get(i + this.WorkLocationIndex*6).getPos().toString().substring(8)));
							//the way we will get which index it is, is by taking worklocation index 
						}
					}else{
						
						for(int i = this.WorkLocationIndex * 6; i < this.WorkLocation.size(); i ++)
						{
							buttonList.add(Working = new GuiButton(50 + i + this.WorkLocationIndex * 6 , guiX + 160, guiY + 30 + (this.WorkLocation.size() -i) * 30, 180, 20,  "Pos: " +  this.WorkLocation.get(i).getPos().toString().substring(8)));
							//the way we will get which index it is, is by taking worklocation index 
						}
					}
				}
				
			}else if(this.Farmer.getJob().contains("Builders Guild"))
			{
				buttonList.add(Working = new GuiButton(19, guiX - 20, guiY + 130, 130, 20, "Choose Builder Location"));
				buttonList.add(Working = new GuiButton(20, guiX - 20, guiY + 160, 130, 20, "Choose Source Location"));
				
				
				
				if(this.Workwork)
				{
					buttonList.add(Working = new GuiButton(-3 , guiX + 120, guiY + 130, 20, 20, " " + "<---"));
					
	
					if(this.WorkLocationIndex * 6 + 6 < this.WorkLocation1.size())
					{
						
						buttonList.add(Working = new GuiButton(-4 , guiX + 120, guiY + 160, 20, 20, " " + "--->"));
						for(int i = 0; i < 6; i ++)
						{
							
							buttonList.add(Working = new GuiButton(50 + i + this.WorkLocationIndex * 6 , guiX + 160, guiY + 30 + i * 30, 180, 20,  "Pos: " + this.WorkLocation1.get(i + this.WorkLocationIndex*6).getPos().toString().substring(8)));
							//the way we will get which index it is, is by taking worklocation index 
						}
					}else{
						
						for(int i = this.WorkLocationIndex * 6; i < this.WorkLocation1.size(); i ++)
						{
							buttonList.add(Working = new GuiButton(50 + i + this.WorkLocationIndex * 6 , guiX + 180, guiY + 30 + (this.WorkLocation1.size() -i) * 30, 180, 20,  "Pos " +  this.WorkLocation1.get(i).getPos().toString().substring(8)));
							//the way we will get which index it is, is by taking worklocation index 
						}
					}
				}else if(this.SourceLocation)
				{
					buttonList.add(Working = new GuiButton(-3 , guiX + 120, guiY + 130, 20, 20, " " + "<---"));
					

					if(this.WorkLocationIndex * 6 + 6 < this.WorkLocation.size())
					{
						
						buttonList.add(Working = new GuiButton(-4 , guiX + 120, guiY + 160, 20, 20, " " + "--->"));
						for(int i = 0; i < 6; i ++)
						{
							
							buttonList.add(Working = new GuiButton(50 + i + this.WorkLocationIndex * 6 , guiX + 160, guiY + 30 + i * 30, 180, 20,  "Pos: " + this.WorkLocation.get(i + this.WorkLocationIndex*6).getPos().toString().substring(8)));
							//the way we will get which index it is, is by taking worklocation index 
						}
					}else{
						
						for(int i = this.WorkLocationIndex * 6; i < this.WorkLocation.size(); i ++)
						{
							buttonList.add(Working = new GuiButton(50 + i + this.WorkLocationIndex * 6 , guiX + 160, guiY + 30 + (this.WorkLocation.size() -i) * 30, 180, 20,  "Pos: " +  this.WorkLocation.get(i).getPos().toString().substring(8)));
							//the way we will get which index it is, is by taking worklocation index 
						}
					}
				}
			
			}
		}
		
		super.initGui();
	}
	
	@Override
	public void onGuiClosed() {
		// TODO Auto-generated method stub
		if(!this.CraftingItems.isEmpty())
			this.CraftingItems.clear();
		super.onGuiClosed();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if(!this.World.isRemote)
		{	
		int guiX = (width - xSize) / 2;
		int guiY = (height - ySize) / 2;
		
			GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		
	        this.mc.getTextureManager().bindTexture(Background1);
	        this.drawModalRectWithCustomSizedTexture(guiX - 60, guiY + 5, 0, 0, 200, 230, 200, 230);
	        
	        this.fontRendererObj.drawString("Kingdom Villager ", guiX - 5, guiY + 40, 0x000000); 
	        this.fontRendererObj.drawString("Work Location ", guiX - 30, guiY + 55, 0x000000); 
	        
	        this.fontRendererObj.drawString("Job: " + Farmer.getJob(), guiX - 30, guiY + 80, 0x000000); 
	        this.fontRendererObj.drawString("Currently Working: " + Farmer.getStartJob(), guiX - 30, guiY + 90, 0x000000);
	        
	        if(Farmer.getRequestedItem() != null && (this.Farmer.getJob().contains("Carpenter") || this.Farmer.getJob().contains("Blacksmith")))
	        {
	        	this.fontRendererObj.drawString("Requested Item: " + Farmer.getRequestedItem().getDisplayName(), guiX - 30, guiY + 100, 0x000000);
	        }
	        
	        
	        if(Farmer.getWhereAM()[0] != null && this.Farmer.getJob().contains("Messanger"))
	        {
	        	this.fontRendererObj.drawString("Te 1: " + Farmer.getWhereAM()[0], guiX +160, guiY + 30, 0xffffff);
	        	
	        }
	        
	        if(Farmer.getWhereAM()[1] != null && this.Farmer.getJob().contains("Messanger"))
	        {
	        	
	        	this.fontRendererObj.drawString("Te 2: " + Farmer.getWhereAM()[1], guiX + 160, guiY + 40, 0xffffff);
	        	
	        }
	        if(Farmer.getWhereAM()[2] != null && this.Farmer.getJob().contains("Messanger"))
	        {
	        
	        	this.fontRendererObj.drawString("Te 3: " + Farmer.getWhereAM()[2], guiX + 160, guiY + 50, 0xffffff);
	        	
	        }
	        if(Farmer.getWhereAM()[3] != null && this.Farmer.getJob().contains("Messanger"))
	        {
	        	
	        	this.fontRendererObj.drawString("Te 4: " + Farmer.getWhereAM()[3], guiX + 160, guiY + 60, 0xffffff);
	        }
	        if(Farmer.getStorageLocation()!= null && this.Farmer.getJob().contains("Messanger"))
	        {
	        	
	        	this.fontRendererObj.drawString("Source: " +  Farmer.getStorageLocation(), guiX + 160, guiY + 70, 0xffffff);
	        }
	        
	        
	        if(Farmer.getStorageLocation() != null && Farmer.getStorageLocation().getY() != 0 && this.Farmer.getJob().contains("Builder"))
	        {
	        	this.fontRendererObj.drawString("Source:" + Farmer.getStorageLocation().toString().substring(8), guiX - 30, guiY + 100, 0x000000);
	        
	        }
	        
	        this.fontRendererObj.drawString("Choose Job" , guiX + 160, guiY +  10, 0xFFFFFF);
	        this.fontRendererObj.drawString("Choose Work Location" , guiX + 250, guiY + 10, 0xFFFFFF);
	        
	        
	    if(this.Farmer.getWorkPlace() != null)
	    {
	    	
	        this.fontRendererObj.drawString("X: " + this.Farmer.getWorkPlace().getX(), guiX - 30, guiY + 65, 0x000000); 
	        this.fontRendererObj.drawString("Y: " + this.Farmer.getWorkPlace().getY(), guiX + 10, guiY + 65,0x000000); 
	        this.fontRendererObj.drawString("Z: " + this.Farmer.getWorkPlace().getZ(), guiX + 50, guiY + 65, 0x000000); 
	    }   
	       
		
	        
		if(this.ChooseJob)
		{
			 this.fontRendererObj.drawString("Job Selection Page!", guiX + 220, guiY + 40, 0xFF6A00); 
		     this.fontRendererObj.drawString("Click on a job to see more information! ", guiX + 170, guiY + 55, 0xFF6A00); 
		  
		    if(this.insideJob)
			{
		    	if(this.JobName.contains("Miners Guild"))
		    	{
		    		this.fontRendererObj.drawString("This job will allow the Villager to be able to ", guiX + 160, guiY + 80, 0xffffff); 
		    		this.fontRendererObj.drawString("mine resources from the ground", guiX + 160, guiY + 90, 0xffffff); 
		    		//mine resources from the ground"
		    		this.fontRendererObj.drawString("Common produce: Cobblestone", guiX + 160, guiY + 110, 0xffffff);   
		    		this.fontRendererObj.drawString("Rare produce: Iron ore/coal", guiX + 160, guiY + 120, 0xffffff); 
		    		
		    		this.fontRendererObj.drawString("Required Buildings: Miner's Guild", guiX + 160, guiY + 140, 0xffffff);   
		    		
		    	}else if(this.JobName.contains("Lumberjack Guild"))
		    	{
		    		this.fontRendererObj.drawString("This job will allow the Villager to be able to ", guiX + 160, guiY + 80, 0xffffff); 
		    		this.fontRendererObj.drawString("plant and harvest oak trees", guiX + 160, guiY + 90, 0xffffff); 
		    		//mine resources from the ground"
		    		this.fontRendererObj.drawString("Common produce: Wood", guiX + 160, guiY + 110, 0xffffff);   
		    		this.fontRendererObj.drawString("Rare produce: Sapling/Apple", guiX + 160, guiY + 120, 0xffffff); 
		    		
		    		this.fontRendererObj.drawString("Required Buildings: Lumberjack's Guild", guiX + 160, guiY + 140, 0xffffff);   
		    	}else if(this.JobName.contains("Wheat Farmer Guild"))
		    	{
		    		this.fontRendererObj.drawString("This job will allow the Villager to be able to ", guiX + 160, guiY + 80, 0xffffff); 
		    		this.fontRendererObj.drawString("plant and harvest wheat crops only!", guiX + 160, guiY + 90, 0xffffff); 
		    		//mine resources from the ground"
		    		this.fontRendererObj.drawString("Common produce: Wheat", guiX + 160, guiY + 110, 0xffffff);   
		    		this.fontRendererObj.drawString("Rare produce: Seeds", guiX + 160, guiY + 120, 0xffffff); 
		    		
		    		this.fontRendererObj.drawString("Required Buildings: Farmer's Guild", guiX + 160, guiY + 140, 0xffffff);   
		    	}else if(this.JobName.contains("Exotic Farmers guild"))
		    	{
		    		this.fontRendererObj.drawString("This job will allow the Villager to be able to ", guiX + 160, guiY + 80, 0xffffff); 
		    		this.fontRendererObj.drawString("plant and harvest sugar cane, and Melon crops only!", guiX + 160, guiY + 90, 0xffffff); 
		    		//mine resources from the ground"
		    		this.fontRendererObj.drawString("Common produce: Sugar cane, melon", guiX + 160, guiY + 110, 0xffffff);   
		    		this.fontRendererObj.drawString("Rare produce: Melon Seeds", guiX + 160, guiY + 120, 0xffffff); 
		    		
		    		this.fontRendererObj.drawString("Required Buildings: Special Farmer's Guild", guiX + 160, guiY + 140, 0xffffff);   
		    	}else if(this.JobName.contains("Messanger Guild"))
		    	{
		    		this.fontRendererObj.drawString("This job will allow the Villager to be able to ", guiX + 160, guiY + 80, 0xffffff); 
		    		this.fontRendererObj.drawString("walk to four buildings and collect the produce", guiX + 160, guiY + 90, 0xffffff); 
		    		//mine resources from the ground"
		    		this.fontRendererObj.drawString("Common produce: N/A", guiX + 160, guiY + 110, 0xffffff);   
		    		this.fontRendererObj.drawString("Rare produce: N/A", guiX + 160, guiY + 120, 0xffffff); 
		    		
		    		this.fontRendererObj.drawString("Required Buildings: Messagner's Guild", guiX + 160, guiY + 140, 0xffffff);   
		    	}else if(this.JobName.contains("Carpenter"))
		    	{
		    		this.fontRendererObj.drawString("This job will allow the Villager to be able to ", guiX + 160, guiY + 80, 0xffffff); 
		    		this.fontRendererObj.drawString("craft certain items (based on the building)", guiX + 160, guiY + 90, 0xffffff); 
		    		//mine resources from the ground"
		    		this.fontRendererObj.drawString("Common produce: N/A", guiX + 160, guiY + 110, 0xffffff);   
		    		this.fontRendererObj.drawString("Rare produce: N/A", guiX + 160, guiY + 120, 0xffffff); 
		    		
		    		this.fontRendererObj.drawString("Required Buildings: Blacksmith, Carpenter, Botanist", guiX + 160, guiY + 140, 0xffffff);   
		    	}else if(this.JobName.contains("Builders Guild"))
		    	{
		    		this.fontRendererObj.drawString("This job will allow the Villager to be able to ", guiX + 160, guiY + 80, 0xffffff); 
		    		this.fontRendererObj.drawString("gather and give all the requested items ", guiX + 160, guiY + 90, 0xffffff); 
		    		this.fontRendererObj.drawString("needed for a building. The items are ", guiX + 160, guiY + 100, 0xffffff); 
		    		this.fontRendererObj.drawString("taken from a specific storage location", guiX + 160, guiY + 110, 0xffffff); 
		    		
		    		
		    		
		    		//mine resources from the ground"
		    		this.fontRendererObj.drawString("Common produce: N/A", guiX + 160, guiY + 130, 0xffffff);   
		    		this.fontRendererObj.drawString("Rare produce: N/A", guiX + 160, guiY + 140, 0xffffff); 
		    		
		    		this.fontRendererObj.drawString("Requires: Storage Location, and Builder's Guild", guiX + 140, guiY + 150, 0xffffff);   
		    	}else if(this.JobName.contains("Storage Hut"))
		    	{
		    		this.fontRendererObj.drawString("This job will allow the Villager to be able to", guiX + 160, guiY + 80, 0xffffff); 
		    		this.fontRendererObj.drawString("store all items in nearby chests", guiX + 160, guiY + 90, 0xffffff); 
		   
		    		
		    		//mine resources from the ground"
		    		this.fontRendererObj.drawString("Common produce: N/A", guiX + 160, guiY + 110, 0xffffff);   
		    		this.fontRendererObj.drawString("Rare produce: N/A", guiX + 160, guiY + 120, 0xffffff); 
		    		
		    		this.fontRendererObj.drawString("Required Buildings: Storage Building", guiX + 160, guiY + 140, 0xffffff);   
		    	}	
			}
		     
		}else if(this.ChooseWorkLocation)
		{
			if(this.insideWorkLocation)
			{
				
				
				this.fontRendererObj.drawString("Structure's Name: " + this.SpecificWorkLocation.structure.getName(), guiX + 160, guiY + 80, 0xffffff); 
				
				
				this.fontRendererObj.drawString("Number of Workers:  " + this.SpecificWorkLocation.structure.getCurrentWorkers(), guiX + 160, guiY + 90, 0xffffff); 
				
	    		
	    		this.fontRendererObj.drawString("Coordinates:  ", guiX + 160, guiY + 100, 0xffffff); 
	    		this.fontRendererObj.drawString("X: " + this.SpecificWorkLocation.getPos().getX() + " Y: " + this.SpecificWorkLocation.getPos().getY() + " Z:" + this.SpecificWorkLocation.getPos().getZ(), guiX + 160, guiY + 110, 0xffffff); 
	    		
	    		
	    		
	    		//mine resources from the ground"
	    		  
			}
		}
		}     
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	
	
	@Override
	public boolean doesGuiPauseGame() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public List<TileEntityKingdomStructureBlock> DetermineWorkLocations()
	{
		List<TileEntityKingdomStructureBlock> B = new ArrayList();
		
		for(int i =0; i < World.loadedTileEntityList.size(); i ++)
		{
			if(World.loadedTileEntityList.get(i) instanceof TileEntityKingdomStructureBlock)
			{
				if(((TileEntityKingdomStructureBlock)World.loadedTileEntityList.get(i)).structure.getName() != null)
				{
					if(Farmer.getJob() != null)
					{
						if(!Farmer.getJob().contains("Builder"))
						{
							if(((TileEntityKingdomStructureBlock)World.loadedTileEntityList.get(i)).structure.getName().contains(Farmer.getJob()) || this.MailFirstTE || this.MailSecTE || this.MailThirdTE || this.MailFourthTE || this.MailFifthTE)
								B.add((TileEntityKingdomStructureBlock) World.loadedTileEntityList.get(i));
						}else{
							B.add((TileEntityKingdomStructureBlock) World.loadedTileEntityList.get(i));
						}
					}
				}
			}
		}
		return B;
	}
	
	public List<TileEntityKingdomStructureBuilderBlock> DetermineBuilderWorkLocations()
	{
		List<TileEntityKingdomStructureBuilderBlock> B = new ArrayList();
		
		for(int i =0; i < World.loadedTileEntityList.size(); i ++)
		{
			if(World.loadedTileEntityList.get(i) instanceof TileEntityKingdomStructureBuilderBlock)
			{
				
				if(Farmer.getJob() != null)
				{
						B.add((TileEntityKingdomStructureBuilderBlock) World.loadedTileEntityList.get(i));
				}
				
			}
		}
		return B;
	}

	
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		
		if(button.id == -6)
		{
			this.ChooseJob=false;
			this.ChooseWorkLocation= false;
			this.ExtraStuff = true;
			this.WorkLocationIndex = 0;
			
			if(!this.World.isRemote && this.Farmer.getJob().contains("Carpenter")){
				CraftingItems = this.Farmer.FillCarpenterObjects();
			
			}else 
				if(!this.World.isRemote && this.Farmer.getJob().contains("Blacksmith")){
					CraftingItems = this.Farmer.FillBlacksmithObjects();
				
				}
			initGui();
			
		}else if(button.id == -7)
		{
	
			//choose job
			
			this.WorkLocationIndex++;	
		
			this.initGui();
		}else if(button.id == -8)
		{
			//choose job
		
			if(this.WorkLocationIndex > 0)
				this.WorkLocationIndex--;
			
		this.initGui();
		}else if(button.id == -5)
		{
			
				
				
			
			PacketHandler.INSTANCE.sendToAllAround(new ChangeKingdomVillagerInformation(false, Farmer.getEntityId()),  new TargetPoint(this.Player.dimension, this.Farmer.posX,this.Farmer.posY, this.Farmer.posZ, 40));
			this.Farmer.setStartJob(false);
			this.Farmer.setWorkPlace(0, 0, 0);
			this.Farmer.RemoveTasks();
			initGui();
			
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
		}else if(button.id == 0)
		{
			//choose job
			this.ChooseJob = true;
			this.initGui();
			
	
		}else if(button.id == 1)
		{
			//set Work local
			this.WorkLocation = new ArrayList();
			this.WorkLocation = this.DetermineWorkLocations();
			this.ChooseJob = false;
			this.ChooseWorkLocation = true;
			this.initGui();
			
		}else if(button.id == 2)
		{
			//start job
			if(this.Farmer.getWorkPlace().getX() != 0 && this.Farmer.getWorkPlace().getY() != 0 && this.Farmer.getJob() != null)
			{
				
				this.Farmer.setStartJob(true);
				this.Farmer.RemoveTasks();
				this.Farmer.DetermineTasks(World);
				
				PacketHandler.INSTANCE.sendToAllAround(new ChangeKingdomVillagerInformation(true, Farmer.getEntityId()),  new TargetPoint(this.Player.dimension, this.Farmer.posX,this.Farmer.posY, this.Farmer.posZ, 40));
			}	
		}else if(button.id == 3)
		{
			this.ChooseJob = false;
			this.insideJob = false;
			initGui();
			
		}else if(button.id == 4)
		{
			this.JobName = "Miners Guild";
			this.insideJob = true;
			initGui();
			
		}else if(button.id == 5)
		{
			this.JobName = "Lumberjack Guild";
			this.insideJob = true;
			initGui();
			
		}else if(button.id == 6)
		{
			this.JobName = "Wheat Farmer Guild";
			this.insideJob = true;
			initGui();
			
		}else if(button.id == 7)
		{
			this.JobName = "Exotic Farmers guild";
			this.insideJob = true;
			initGui();
			
		}else if(button.id == 8)
		{
			this.JobName = "Messanger Guild";
			this.insideJob = true;
			initGui();
			
		}else if(button.id == 9)
		{
			this.JobName = "Carpenter";
			this.insideJob = true;
			initGui();
			
		}else if(button.id == 10)
		{
			this.JobName = "Builders Guild";
			this.insideJob = true;
			initGui();
			
		}else if(button.id == 11)
		{
			this.JobName = "Storage Hut";
			this.insideJob = true;
			initGui();
			
		}else if(button.id == 18)
		{
			this.JobName = "Blacksmith";
			this.insideJob = true;
			initGui();
			
		}else if(button.id == 12)
		{
			this.ChooseJob = false;
			this.insideJob = false;
			// send packet
			
			this.b.check();

			this.Farmer.setJob(JobName);
			PacketHandler.INSTANCE.sendToAllAround(new ChangeKingdomVillagerInformation(this.JobName, Farmer.getEntityId()),  new TargetPoint(this.Player.dimension, this.Farmer.posX,this.Farmer.posY, this.Farmer.posZ, 40));
			
			initGui();
		}else if(button.id == 13)
		{
			this.ChooseWorkLocation = false;
			this.insideWorkLocation = false;
			
			
			
			BlockPos b = this.SpecificWorkLocation.GetWorkPlaceLocationsBasedOnName(this.SpecificWorkLocation.structure.getName(), this.SpecificWorkLocation.structure.getStartX(), this.SpecificWorkLocation.structure.getStartY(), this.SpecificWorkLocation.structure.getStartZ(), this.SpecificWorkLocation.structure.getCurrentWorkers(), this.SpecificWorkLocation.structure.getDirection());
			
			
		if(b != null)
		{
			
				this.Farmer.setWorkPlace(b.getX(),b.getY() + 1, b.getZ());
				PacketHandler.INSTANCE.sendToAllAround((new ChangeKingdomVillagerInformation("Dont", b.getX(),b.getY() + 1, b.getZ(), Farmer.getEntityId())), new TargetPoint(this.Player.dimension, this.Farmer.posX,this.Farmer.posY, this.Farmer.posZ, 40));
				this.SpecificWorkLocation.structure.AddOneToWorker();
				this.SpecificWorkLocation = null;
		}		
			
			initGui();
		}else if(button.id == 14)
		{
			this.MailFirstTE = true;
			this.MailSecTE = false;
			this.MailThirdTE = false;
			this.MailFourthTE = false;
			
			this.WorkLocation = this.DetermineWorkLocations();
			
			initGui();
		}else if(button.id == 15)
		{
			this.MailFirstTE = false;
			this.MailSecTE = true;
			this.MailThirdTE = false;
			this.MailFourthTE = false;
			
			this.WorkLocation = this.DetermineWorkLocations();
			
			initGui();
		}else if(button.id == 16)
		{
			this.MailFirstTE = false;
			this.MailSecTE = false;
			this.MailThirdTE = true;
			this.MailFourthTE = false;
			
			this.WorkLocation = this.DetermineWorkLocations();
			
			initGui();
		}else if(button.id == 17)
		{
			this.MailFirstTE = false;
			this.MailSecTE = false;
			this.MailThirdTE = false;
			this.MailFourthTE = true;
			
			this.WorkLocation = this.DetermineWorkLocations();
			
			initGui();
		}else if(button.id == 21)
		{
			this.MailFirstTE = false;
			this.MailSecTE = false;
			this.MailThirdTE = false;
			this.MailFourthTE = false;
			this.MailFifthTE = true;
			
			this.WorkLocation = this.DetermineWorkLocations();
			
			initGui();
		}else if(button.id == 19)
		{
			//Work Location
			
			this.Workwork = true;
			this.SourceLocation = false;
			
			this.WorkLocation1 = this.DetermineBuilderWorkLocations();
			
			
			initGui();
		}else if(button.id == 20)
		{
			//Work Location
			
			this.Workwork = false;
			this.SourceLocation = true;
			
			this.WorkLocation = this.DetermineWorkLocations();
			
			
			initGui();
		}else if(button.id >= 50)
		{
			if(!this.ExtraStuff)
			{
				
				int TEID = button.id - this.WorkLocationIndex*6 - 50;
				this.SpecificWorkLocation = this.WorkLocation.get(TEID);
				
				this.insideWorkLocation = true;
				this.ChooseJob = false;
				
			}else if(this.ExtraStuff && !this.MailFirstTE && !this.MailSecTE && !this.MailThirdTE && !this.MailFourthTE && !this.MailFifthTE)
			{
				
				int ItemID = button.id - this.WorkLocationIndex*6 - 50;
				this.Farmer.setRequestedItem(this.CraftingItems.get(ItemID).getProduced());
				
			}
			
			if(this.Workwork)
			{
				int ItemID = button.id - this.WorkLocationIndex*6 - 50;
				
				
				
				this.Farmer.setWorkPlace(this.WorkLocation1.get(ItemID).getPos().getX(), this.WorkLocation1.get(ItemID).getPos().getY(), this.WorkLocation1.get(ItemID).getPos().getZ());
			}
			if(this.SourceLocation)
			{
				int ItemID = button.id - this.WorkLocationIndex*6 - 50;
				this.Farmer.setStorageLocation(this.WorkLocation.get(ItemID).getPos());
			}
			
			if(this.MailFirstTE)
			{
				
				int ItemID = button.id - this.WorkLocationIndex*6 - 50;
				this.Farmer.setWhereAM1(this.WorkLocation.get(ItemID).getPos());
			}
			if(this.MailSecTE)
			{
				int ItemID = button.id - this.WorkLocationIndex*6 - 50;
				this.Farmer.setWhereAM2(this.WorkLocation.get(ItemID).getPos());
			}
			if(this.MailThirdTE)
			{
				int ItemID = button.id - this.WorkLocationIndex*6 - 50;
				this.Farmer.setWhereAM3(this.WorkLocation.get(ItemID).getPos());
			}
			if(this.MailFourthTE)
			{
				int ItemID = button.id - this.WorkLocationIndex*6 - 50;
				this.Farmer.setWhereAM4(this.WorkLocation.get(ItemID).getPos());
			}
			
			if(this.MailFifthTE)
			{
				int ItemID = button.id - this.WorkLocationIndex*6 - 50;
				this.Farmer.setStorageLocation(this.WorkLocation.get(ItemID).getPos());
				
			}
			initGui();
			
		}
		
		super.actionPerformed(button);
	}
}
