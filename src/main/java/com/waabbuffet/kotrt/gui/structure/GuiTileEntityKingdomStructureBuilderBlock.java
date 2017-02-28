package com.waabbuffet.kotrt.gui.structure;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import com.waabbuffet.kotrt.gui.GuiButtonCheckBox;
import com.waabbuffet.kotrt.inventory.ContainerTileEntityKingdomStructureBlock;
import com.waabbuffet.kotrt.inventory.ContainerTileEntityKingdomStructureBuilderBlock;
import com.waabbuffet.kotrt.packet.PacketHandler;
import com.waabbuffet.kotrt.packet.structure.FinishStructure;
import com.waabbuffet.kotrt.packet.structure.KingdomData;
import com.waabbuffet.kotrt.packet.structure.UpdateClientTileInv;
import com.waabbuffet.kotrt.packet.structure.UpdateClientTileInv1;
import com.waabbuffet.kotrt.packet.structure.UpdateMaterialList;
import com.waabbuffet.kotrt.proxy.ClientProxy;
import com.waabbuffet.kotrt.schematic.Structure;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBlock;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBuilderBlock;
import com.waabbuffet.kotrt.util.BlockList;
import com.waabbuffet.kotrt.util.GeometryMasks;
import com.waabbuffet.kotrt.util.GeometryTessellator;
import com.waabbuffet.kotrt.util.PlayerData;

public class GuiTileEntityKingdomStructureBuilderBlock  extends GuiContainer {

	private IInventory playerInv;
	private EntityPlayer ThePlayer;
	private TileEntityKingdomStructureBuilderBlock te;
	
	public String ChooseBuilding, BuildingDirection;
	public int StartingX = 0, StartingY = 0, StartingZ = 0;
	private int BuildingID;
	
	private GuiButton Construction;
	private GuiButtonCheckBox b, b1;
	
	
	private final static RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
	boolean SelectBuilding, RegularBuilding, StructureInfo, tierOne;

	private int CurrentTier;
	
	private String[] BuildingOptions = new String[6];
	private String[] BuildingQualities = new String[6];
	private int[] TopBlocks = new int[4];
	
    public GuiTileEntityKingdomStructureBuilderBlock(IInventory playerInv, TileEntityKingdomStructureBuilderBlock te, EntityPlayer player) {
        super(new ContainerTileEntityKingdomStructureBuilderBlock(playerInv, te));

        
        this.ThePlayer = player;
        this.playerInv = playerInv;
        this.te = te;
      
        this.CurrentTier = 0;
        this.RegularBuilding = true;
        this.BuildingDirection = "None";
        
       
      
        if(this.te.FileName != null)
        {
        	this.ChooseBuilding = te.FileName;
        }
        
        
       
        if(this.te.BuildingDirection != null)
        {
        	
        	this.BuildingDirection = te.BuildingDirection;
        }
        
        
        
        
        this.xSize = 248;
        this.ySize = 256;
      
	   if(te.TileBlockPosX == 0){
	        this.StartingX = te.getPos().getX();
	        this.StartingY = te.getPos().getY();
	        this.StartingZ = te.getPos().getZ();
	   }else{
		    this.StartingX = te.TileBlockPosX;
	        this.StartingY = te.TileBlockPosY;
	        this.StartingZ = te.TileBlockPosZ;
	   }
    }

    
    @Override
    public void onGuiClosed() 
    {
    	
    	this.te.setBuildingCoordinates(StartingX, StartingY, StartingZ);
    	this.BuildingID = 0;
    	this.SelectBuilding = false;
    	
    	super.onGuiClosed();
    }
    
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    	
	    	GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
	        this.mc.getTextureManager().bindTexture(new ResourceLocation("kotrt:textures/gui/container/kingdom_structureblock.png"));
	        this.drawTexturedModalRect(- 8, 0, 0, 0, this.xSize, this.ySize);
	      
	      if(!this.SelectBuilding){
	        this.mc.getTextureManager().bindTexture(new ResourceLocation("kotrt:textures/gui/container/extra_utils.png"));
	        this.drawTexturedModalRect(this.width - 167, this.height - 170, 0, 0, 25, 18);
	        this.drawTexturedModalRect(this.width - 130, this.height - 170, 0, 0, 25, 18);
	        this.drawTexturedModalRect(this.width - 93, this.height - 170, 0, 0, 25, 18);
	      }
        
        	
	     // itemRender.renderItemAndEffectIntoGUI(new ItemStack(Blocks.log),  100, 100);
    }

 
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

        String s = this.te.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(s, -55, 45, 4210752);            //#404040
        this.fontRendererObj.drawString("Player's " + this.playerInv.getDisplayName().getUnformattedText(), -55, 115, 4210752);      //#404040
       
        
        this.fontRendererObj.drawString("Choose Structure" , this.width - 335, this.height - 247, 0xFFFFFF);
        this.fontRendererObj.drawString("Choose Direction" , this.width - 220, this.height - 247, 0xFFFFFF);
        if(!this.SelectBuilding)
        {     
        	
	        if(this.ChooseBuilding != null){
	        	this.fontRendererObj.drawString("Current Building:  " + this.ChooseBuilding, this.width - 290, this.height - 210, 0xFFFFFF);   
	        }else{
	        	this.fontRendererObj.drawString("Current Building: None ", this.width - 290, this.height - 210, 0xFFFFFF);   
	        }
	        
	     
	        this.fontRendererObj.drawString("Current Direction: " + this.BuildingDirection, this.width - 290, this.height - 200, 0xFFFFFF);   
	        
	        
	        this.fontRendererObj.drawString("X: ", this.width - 273, this.height - 180, 0xFFFFFF);  
	        this.fontRendererObj.drawString("Y: ", this.width - 237, this.height - 180, 0xFFFFFF);  
	        this.fontRendererObj.drawString("Z: ", this.width - 200, this.height - 180, 0xFFFFFF);  
	        this.fontRendererObj.drawString("" + this.StartingX, this.width - 281, this.height - 160, 0xFFFFFF);  
	        this.fontRendererObj.drawString("" + this.StartingY, this.width - 244, this.height - 160, 0xFFFFFF); 
	        this.fontRendererObj.drawString("" + this.StartingZ, this.width - 207, this.height - 160, 0xFFFFFF); 
	        
		     if(this.ChooseBuilding != null && this.te.StartConstruction){ 
		        this.fontRendererObj.drawString("Blocks" , this.width - 593, this.height - 215, 0xFFFFFF);
		        this.fontRendererObj.drawString("Needed" , this.width - 593, this.height - 205, 0xFFFFFF);
		        this.fontRendererObj.drawString("______" , this.width - 593, this.height - 200, 0xFFFFFF);
		      
		     
		       te.SortList();
		     
		    
			  if(this.te.MaterialList1.size() != 0) {
				  RenderHelper.enableGUIStandardItemLighting();
				
				
				  for(int i = 0; te.MaterialList1.size() > 16 ? i < 16 : i < te.MaterialList1.size(); i++){
					  
					  if(i < 8)
					  {
							 if(!this.te.MaterialList1.get(i).Blockerino.toString().contains("water") && !this.te.MaterialList1.get(i).Blockerino.toString().contains("lava")){
							
							
								 itemRender.renderItemAndEffectIntoGUI(new ItemStack(this.te.MaterialList1.get(i).Blockerino.getBlock()),  -115, 60 + 20*i);
								 
							 }else {
								 if(this.te.MaterialList1.get(i).Blockerino.toString().contains("water"))
									 itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.water_bucket),  -115, 60 + 20*i);
								 else{
									 itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.lava_bucket),  -115, 60 + 20*i);
								 }
							 }
							 
							  this.fontRendererObj.drawString("*" + te.MaterialList1.get(i).TotalNumberOfBlock, -95, 65 + 20*i, 0xFFFFFF); 
					  }else {
						  if(!this.te.MaterialList1.get(i).Blockerino.toString().contains("water") && !this.te.MaterialList1.get(i).Blockerino.toString().contains("lava")){
								
								
								 itemRender.renderItemAndEffectIntoGUI(new ItemStack(this.te.MaterialList1.get(i).Blockerino.getBlock()),  +95, 60 + 20* (i - 8));
								 
							 }else {
								 if(this.te.MaterialList1.get(i).Blockerino.toString().contains("water"))
									 itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.water_bucket),  +95, 60 + 20* (i - 8));
								 else{
									 itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.lava_bucket),  +95, 60 + 20* (i - 8));
								 }
							 }
							 
							  this.fontRendererObj.drawString("*" + te.MaterialList1.get(i).TotalNumberOfBlock, + 115, 65 + 20* (i - 8), 0xFFFFFF); 
					  }
					  
				  }
				  
				  
			
			}
		   
		     
		     
		     
		     }
		      
	        
        }else{
        	
        	
        if(BuildingQualities[4] != null){
        	this.fontRendererObj.drawString("Building Selection:  " + BuildingQualities[4], this.width - 290, this.height - 210, 0xFFFFFF);   
        }else{
        	this.fontRendererObj.drawString("Building Selection:  ", this.width - 290, this.height - 210, 0xFFFFFF);   
        }
        
        this.fontRendererObj.drawString("Current Tier: " + this.CurrentTier , this.width - 290, this.height - 200, 0xFFFFFF);   
        	
        }
        
        if(!this.StructureInfo)
        {
        	if(this.tierOne)
        	{
        		this.fontRendererObj.drawString("" + BuildingQualities[0], this.width - 370, this.height - 180, 0xFFFFFF);  
      	        this.fontRendererObj.drawString("" + BuildingQualities[1], this.width - 370, this.height - 170, 0xFFFFFF);  
      	        this.fontRendererObj.drawString("" + BuildingQualities[2], this.width - 370, this.height - 160, 0xFFFFFF);  
      	        
      	      //  this.fontRendererObj.drawString("Top Four Blocks used: ", this.width - 385, this.height - 140, 0xFFFFFF);  
      	        
      	        this.fontRendererObj.drawString("Building Difficulty: ", this.width - 230, this.height - 140, 0xFFFFFF);  
      	        this.fontRendererObj.drawString("-" + this.BuildingQualities[3], this.width - 220, this.height - 130, 0xFFFFFF); 
      	        
      	        this.fontRendererObj.drawString("Building Dimensions: ", this.width - 230, this.height - 110, 0xFFFFFF);  
      	        if(this.te.FullBlocks != null)
      	        	this.fontRendererObj.drawString("X: " + this.te.FullBlocks.length +"  Y: " + this.te.FullBlocks[0].length + "   Z: " + this.te.FullBlocks[0][0].length, this.width - 230, this.height - 100, 0xFFFFFF);  
     	        
      	       
    	        
      	   
 
        	}
        }
   }
    
    @Override
	public boolean doesGuiPauseGame()
	   {
	       return false;
	   }
    
    @Override
    public void initGui() {
    	
    	int guiX = (width - xSize) / 2;
		int guiY = (height - ySize) / 2;
		
    	
		buttonList.clear();
		
		buttonList.add(b = new GuiButtonCheckBox(6, this.width - 230, this.height - 249));
		buttonList.add(b1 = new GuiButtonCheckBox(7, this.width - 115, this.height - 249));
		
		if(this.RegularBuilding)
        {
			if(!this.te.StartConstruction)
			{
				buttonList.add(Construction = new GuiButton(1, this.width - 215, this.height - 100, 100, 20, "Select Building"));
			//	buttonList.add(Construction = new GuiButton(0, this.width - 215, this.height - 75, 100, 20, "Preview Building"));
				
				buttonList.add(Construction = new GuiButton(4, this.width - 110, this.height - 100, 100, 20, "Change Direction"));
				buttonList.add(Construction = new GuiButton(3, this.width - 110, this.height - 75, 100, 20, "Reset Coordinates"));
				buttonList.add(Construction = new GuiButton(8, this.width - 167, this.height - 151, 10, 10, "+"));
				buttonList.add(Construction = new GuiButton(9, this.width - 152, this.height - 151, 10, 10, "-"));
				
				buttonList.add(Construction = new GuiButton(10, this.width - 130, this.height - 151, 10, 10, "+"));
				buttonList.add(Construction = new GuiButton(11, this.width - 114, this.height - 151, 10, 10, "-"));
				
				buttonList.add(Construction = new GuiButton(12, this.width - 94, this.height - 151, 10, 10, "+"));
				buttonList.add(Construction = new GuiButton(13, this.width - 78, this.height - 151, 10, 10, "-"));
			
				buttonList.add(Construction = new GuiButton(5, this.width - 157, this.height - 40, 110, 20, "Start Construction"));
			}else{
				
				buttonList.add(Construction = new GuiButton(-5, this.width - 110, this.height - 100, 100, 20, "Stop Construction"));
				buttonList.add(Construction = new GuiButton(-1, this.width - 215, this.height - 100, 100, 20, "Update Material List"));
				buttonList.add(Construction = new GuiButton(24, this.width - 110, this.height - 130, 100, 20, "Full Material List"));
				
			
				if(this.te.Completetion){
					buttonList.add(Construction = new GuiButton(25, this.width - 215, this.height - 130, 100, 20, "Finish Structure"));
				}
					
			}
			
	    	
			
        }else if(this.SelectBuilding){
      
        	if(this.CurrentTier >= 0 && this.StructureInfo)
        	{
        		buttonList.add(Construction = new GuiButton(16, this.width - 225, this.height - 150, 100, 20, this.BuildingOptions[0]));
        		buttonList.add(Construction = new GuiButton(17, this.width - 225, this.height - 175, 100, 20, this.BuildingOptions[1]));
        		
        		if(this.CurrentTier != 2)
        		{
	        		buttonList.add(Construction = new GuiButton(18, this.width - 225, this.height - 125, 100, 20, this.BuildingOptions[2]));
	        		buttonList.add(Construction = new GuiButton(19, this.width - 120, this.height - 150, 100, 20, this.BuildingOptions[3]));
	        		buttonList.add(Construction = new GuiButton(20, this.width - 120, this.height - 175, 100, 20, this.BuildingOptions[4]));
	        		buttonList.add(Construction = new GuiButton(21, this.width - 120, this.height - 125, 100, 20, this.BuildingOptions[5]));
        		}
        		
        		//inside the town hall there will be....A guide who will teach you things, a builder/recruiter
        		//builder will allow 1 builder block, recruiter will recruit villagers for you on a 24 hr cd, each will have a rating d d d d
        		// to compensate, you will want a miner/tree cutter and a house and a storage place
        		// add a level system to each house
        	
        		
        	}else if(!this.StructureInfo){
        		
        		if(this.tierOne)
    			{
        			buttonList.add(Construction = new GuiButton(22, this.width - 110, this.height - 40, 100, 20, "Confirm Building"));
            		buttonList.add(Construction = new GuiButton(23, this.width - 220, this.height - 65, 80, 20, "<-- Previous"));
    			}
        		
        		
        		
        	}
        	if(!this.tierOne){
        		buttonList.add(Construction = new GuiButton(14, this.width - 90, this.height - 203, 10, 10, "+"));
        		buttonList.add(Construction = new GuiButton(15, this.width - 78, this.height - 203, 10, 10, "-"));
        	}
        	
        	buttonList.add(Construction = new GuiButton(2, this.width - 220, this.height - 40, 80, 20, "<-- Start Menu"));
        }
    	super.initGui();
    }
    
    
    public void renderStuff() 
    {
    
    	final GeometryTessellator tessellator = GeometryTessellator.getInstance();
    	tessellator.setTranslation(-this.StartingX, -this.StartingY, -this.StartingZ);
        
  
    }

    
   

  
    
    public void FillBuildingOption(boolean FillTierOrNaw, int WhatBuilding)
    {
	    if(FillTierOrNaw){
	    	if(this.CurrentTier == 0)
	    	{
	    		this.BuildingOptions[0] = "Town Hall";// Done (Good to go!! except chests)
	    		this.BuildingOptions[1] = "Miners Guild";// Done (Good to go!!)
	    		this.BuildingOptions[2] = "Lumberjack Guild"; //(Done) 1. 4man and need 1 smaller one (good to go!!)
	    		this.BuildingOptions[3] = "Small House"; // (have like 50) (Good to go!)
	    		this.BuildingOptions[4] = "Shopkeeper Guild"; //(have) (Good to go!)
	    		this.BuildingOptions[5] = "Wheat Farmer Guild"; // (Done) (Good to go!)
	    		
	    	}else
	    	if(this.CurrentTier == 1)
	    	{
	    		this.BuildingOptions[0] = "Carpenter"; //(Done) (Good to go)
	    		this.BuildingOptions[1] = "Blacksmith"; // (Done) (the forge) (Needs major improvements)
	    		this.BuildingOptions[2] = "Storage Hut"; // (Done) (in 66) (Not done) (Missing building)
	    		this.BuildingOptions[3] = "Messanger Guild"; // (Done) in final (Good to go)
	    		this.BuildingOptions[4] = "Exotic Farmers guild"; //(Done) (Good to go)
	    		this.BuildingOptions[5] = "Builders Guild"; // in main world not final (Good to go!!)
	    	}if(this.CurrentTier == 2)
	    	{
	    		this.BuildingOptions[0] = "Cathedral";
	    		this.BuildingOptions[1] = "Barracks"; 
	    	
	    	}
	    	/*
	    	 * else
	    	if(this.CurrentTier == 2)
	    	{
	    		this.BuildingOptions[0] = "Animal Farm";
	    		this.BuildingOptions[1] = "Destoryer's Guild"; // clears a specific area and gains the resources
	    		this.BuildingOptions[2] = "Train Guild"; // 
	    		this.BuildingOptions[3] = "Messanger Guild"; //rest should be crafting
	    		this.BuildingOptions[4] = "Exotic Farmer's guild";
	    		this.BuildingOptions[5] = "Builder's Guild"; // 
	    	}
	    	 */
	    }else
	    {
	    	
		    if(this.CurrentTier == 0 && WhatBuilding == 1){
		    	
		    	this.BuildingQualities[0] = "This building can auto craft various wood items";
		    	this.BuildingQualities[1] = "";
		    	this.BuildingQualities[2] = "";
		    	this.BuildingQualities[3] = "Easy";
		    	this.BuildingQualities[4] = "Carpenter";
		    }else if(this.CurrentTier == 0 && WhatBuilding == 2){
		    	
		    	this.BuildingQualities[0] = "This building can auto craft various metal related items";
		    	this.BuildingQualities[1] = "";
		    	this.BuildingQualities[2] = "";
		    	this.BuildingQualities[3] = "Easy";
		    	this.BuildingQualities[4] = "Blacksmith";
		    }else if(this.CurrentTier == 0 && WhatBuilding == 3){
		    	
		    	this.BuildingQualities[0] = "This building can auto store items";
		    	this.BuildingQualities[1] = "";
		    	this.BuildingQualities[2] = "";
		    	this.BuildingQualities[3] = "Easy";
		    	this.BuildingQualities[4] = "Storage Hut";
		    }else if(this.CurrentTier == 0 && WhatBuilding == 4){
		    	
		    	this.BuildingQualities[0] = "This building can provide shelter";
		    	this.BuildingQualities[1] = "to up to three villagers";
		    	
		    	this.BuildingQualities[2] = "";
		    	this.BuildingQualities[3] = "Easy";
		    	this.BuildingQualities[4] = "Small Quarry";
		    }else if(this.CurrentTier == 0 && WhatBuilding == 5){
		    	
		    	this.BuildingQualities[0] = "This building can sell basic goods";
		    	this.BuildingQualities[1] = "";
		    	this.BuildingQualities[2] = "";
		    	this.BuildingQualities[3] = "Easy";
		    	this.BuildingQualities[4] = "Small Shop";
		    }else if(this.CurrentTier == 0 && WhatBuilding == 6){
		    	
		    	this.BuildingQualities[0] = "This building will produce wheat";
		    	this.BuildingQualities[1] = "";
		    	this.BuildingQualities[2] = "";
		    	this.BuildingQualities[3] = "Easy";
		    	this.BuildingQualities[4] = "Small Farm";
		    }else if(this.CurrentTier == 1 && WhatBuilding == 1){
		    	
		    	this.BuildingQualities[0] = "This building contains various npcs that will help";
		    	this.BuildingQualities[1] = "you start your kingdom... some npcs are: ";
		    	this.BuildingQualities[2] = "Recruiter, Quest Giver, Builder, and a Guide";
		    	this.BuildingQualities[3] = "Medium";
		    	this.BuildingQualities[4] = "Town Hall";
		    }else if(this.CurrentTier == 1 && WhatBuilding == 2){
		    	
		    	this.BuildingQualities[0] = "This building can produce stone and various ores";
		    	this.BuildingQualities[1] = "";
		    	this.BuildingQualities[2] = "";
		    	this.BuildingQualities[3] = "Easy";
		    	this.BuildingQualities[4] = "Small Quarry";
		    }else if(this.CurrentTier == 1 && WhatBuilding == 3){
		    	
		    	this.BuildingQualities[0] = "This building can produce logs and planks";
		    	this.BuildingQualities[1] = "";
		    	this.BuildingQualities[2] = "";
		    	this.BuildingQualities[3] = "Easy";
		    	this.BuildingQualities[4] = "Small Lumbermill";
		    }else if(this.CurrentTier == 1 && WhatBuilding == 4){
		    	
		    	this.BuildingQualities[0] = "This building can provide shelter";
		    	this.BuildingQualities[1] = "to up to three villagers";
		    	
		    	this.BuildingQualities[2] = "";
		    	this.BuildingQualities[3] = "Easy";
		    	this.BuildingQualities[4] = "Small Quarry";
		    }else if(this.CurrentTier == 1 && WhatBuilding == 5){
		    	
		    	this.BuildingQualities[0] = "This building can sell basic goods";
		    	this.BuildingQualities[1] = "";
		    	this.BuildingQualities[2] = "";
		    	this.BuildingQualities[3] = "Easy";
		    	this.BuildingQualities[4] = "Small Shop";
		    }else if(this.CurrentTier == 1 && WhatBuilding == 6){
		    	
		    	this.BuildingQualities[0] = "This building will produce wheat";
		    	this.BuildingQualities[1] = "";
		    	this.BuildingQualities[2] = "";
		    	this.BuildingQualities[3] = "Easy";
		    	this.BuildingQualities[4] = "Small Farm";
		    }
	    }
    }
    
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) {
       
    	
    	if(guiButton.id == 1)
    	{
    		this.SelectBuilding = true;
    		this.RegularBuilding = false;
    		this.StructureInfo = true;
    		this.tierOne = false;
    	
    		this.FillBuildingOption(true, 0);
    		initGui();
    	}else if(guiButton.id == -1)
    	{
    		
    		PacketHandler.INSTANCE.sendToAll(new UpdateMaterialList(this.ChooseBuilding, this.BuildingDirection, this.te.getPos().getX(), this.te.getPos().getY(), this.te.getPos().getZ(), StartingX, StartingY, StartingZ));
    	

    		initGui();
      	}else if(guiButton.id == 2)
    	{
    		this.SelectBuilding = false;
    		this.RegularBuilding = true;
    		this.tierOne = false;
    	

    		initGui();
      	}else if(guiButton.id == 0)
    	{
      		
    	

    		initGui();
      	}else if(guiButton.id == 3)
    	{
      		this.StartingX = te.getPos().getX();
	        this.StartingY = te.getPos().getY();
	        this.StartingZ = te.getPos().getZ();
   
    		initGui();
    	}else if(guiButton.id == 4)
    	{
    		
    		
    		if(this.BuildingDirection.contains("East"))
    		{
    			this.BuildingDirection = "South";
    		}
    		else if(this.BuildingDirection.contains("South"))
    		{
    			this.BuildingDirection = "West";
    		}
    		else if(this.BuildingDirection.contains("West"))
    		{
    			this.BuildingDirection = "North";
    		}else if(this.BuildingDirection.contains("North"))
    		{
    			this.BuildingDirection = "East";
    		}
    		
    		if(this.BuildingDirection.contains("None"))
    		{
    			this.BuildingDirection = "East";
    		}
   
   
    		initGui();
    	}else
    	if(guiButton.id == 5)
    	{
    		
    		if(this.ChooseBuilding != null && this.BuildingDirection != null)
    		{
    			
    			if(!this.BuildingDirection.contains("None"))
    			{
    				this.te.StartCreationProcess(this.ChooseBuilding, this.BuildingDirection, StartingX, StartingY, StartingZ);
    				PacketHandler.INSTANCE.sendToServer(new UpdateClientTileInv(this.ChooseBuilding, this.BuildingDirection, StartingX, StartingY, StartingZ, this.te.getPos().getX(), this.te.getPos().getY(), this.te.getPos().getZ()));
    			}
    		}
    		initGui();
    	}else if(guiButton.id == -5)
    	{
    	
    		te.FileName = null;
    		te.BuildingDirection = null;
    		te.StartConstruction = false;
    	
    		
    		initGui();
    	}
    	else
    	if(guiButton.id == 8)
    	{
    		this.StartingX++;
    		
    	}
    	else
    	if(guiButton.id == 9)
    	{
    		this.StartingX--;
    	}
    	else
    	if(guiButton.id == 10)
    	{
    		this.StartingY++;
    	}
    	else
    	if(guiButton.id == 11)
    	{
    		this.StartingY--;
    	}
    	else
    	if(guiButton.id == 12)
    	{
    		this.StartingZ++;
    	}
    	else
    	if(guiButton.id == 13)
    	{
    		this.StartingZ--;
    	}
    	else
    	if(guiButton.id == 14)
    	{
    		
    			this.CurrentTier++;
    			this.FillBuildingOption(true, 0);
    			initGui();
    			
    	}
    	else
    	if(guiButton.id == 15)
    	{
    		if(this.CurrentTier > 0)
    		{
    			this.CurrentTier--;
    			this.FillBuildingOption(true, 0);
    			initGui();
    		}
    	}else if(guiButton.id == 16)
    	{
    		
    		FillBuildingOption(false, 1);
    	
    		
    		this.tierOne = true;
    		this.StructureInfo = false;
    		this.BuildingID = 0;
    		initGui();
    		
    	}else if(guiButton.id == 17)
    	{
    		FillBuildingOption(false, 2);
    		this.tierOne = true;
    		this.StructureInfo = false;
    		this.BuildingID = 1;
    		initGui();
    		
    	}else if(guiButton.id == 18)
    	{
    		FillBuildingOption(false, 3);
    		this.tierOne = true;
    		this.StructureInfo = false;
    		this.BuildingID = 2;
    		initGui();
    		
    	}else if(guiButton.id == 19)
    	{
    		FillBuildingOption(false, 4);
    		this.tierOne = true;
    		this.StructureInfo = false;
    		this.BuildingID = 3;
    		initGui();
    		
    	}else if(guiButton.id == 20)
    	{
    		FillBuildingOption(false, 5);
    		this.tierOne = true;
    		this.StructureInfo = false;
    		this.BuildingID = 4;
    		initGui();
    		
    	}else if(guiButton.id == 21)
    	{
    		FillBuildingOption(false, 6);
    		this.tierOne = true;
    		this.StructureInfo = false;
    		this.BuildingID = 5;
    		initGui();
    		
    	}else if(guiButton.id == 22)
    		{	
    		
	    		this.ChooseBuilding = this.BuildingOptions[this.BuildingID];
    		//	this.ChooseBuilding = "TestCreation.schematic";
    		
	    		this.tierOne = false;
	    		this.StructureInfo = false;
	    		this.SelectBuilding = false;
	    		this.RegularBuilding = true;
	    		initGui();
	    		
    	}else if(guiButton.id == 23){
    			this.StructureInfo = true;
	    		this.tierOne = false;
	    
	    		initGui();
    	}else if(guiButton.id == 24){
			
    		for(int i =0; i < this.te.MaterialList1.size(); i ++)
    		{
    			System.out.println("" + this.te.MaterialList1.get(i).Blockerino.getBlock());
    		}
    	
    	}else if(guiButton.id == 25)
    	{
    		PacketHandler.INSTANCE.sendToServer(new FinishStructure(StartingX, StartingY, StartingZ, this.te.getPos().getX(), this.te.getPos().getY(), this.te.getPos().getZ(), this.BuildingDirection, this.ChooseBuilding ));
    		this.te.StartConstruction = false;
    		this.te.FileName = "";
    		
    		initGui();
    	}
    	
    	
    }
    
    
}