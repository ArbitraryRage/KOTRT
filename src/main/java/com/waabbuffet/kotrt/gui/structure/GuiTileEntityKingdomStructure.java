package com.waabbuffet.kotrt.gui.structure;

import com.waabbuffet.kotrt.inventory.ContainerTileEntityKingdomStructureBlock;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBlock;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiTileEntityKingdomStructure extends GuiContainer {

	private IInventory playerInv;
	private TileEntityKingdomStructureBlock te;
	
    public GuiTileEntityKingdomStructure(IInventory playerInv, TileEntityKingdomStructureBlock te) {
        super(new ContainerTileEntityKingdomStructureBlock(playerInv, te));

     
    
        this.playerInv = playerInv;
        this.te = te;
        
        this.xSize = 256;
        this.ySize = 256;
    }

    
    
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    	GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(new ResourceLocation("kotrt:textures/gui/container/kingdom_structureblock.png"));
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = this.te.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(s, 50, 30, 4210752);            //#404040
        this.fontRendererObj.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, 72, 4210752);      //#404040
    }
}
