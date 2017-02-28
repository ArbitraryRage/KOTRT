package com.waabbuffet.kotrt.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiButtonCheckBox  extends GuiButton {

	public boolean enabled = false;
	

	public GuiButtonCheckBox(int par1, int par2, int par3) {
		super(par1, par2, par3, 16, 16, "");
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void check() {
		enabled = !enabled;
	}
	

	@Override
	public void drawButton(Minecraft par1Minecraft, int mouseX, int mouseY) {
        par1Minecraft.renderEngine.bindTexture(new ResourceLocation("kotrt:textures/gui/container/extra_utils.png"));
       
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
        int i = this.getHoverState(this.hovered);
        drawTexturedModalRect(xPosition, yPosition, this.enabled  ? 0 : 9, !enabled ? 49 : 49, 8, 8);
	}
}