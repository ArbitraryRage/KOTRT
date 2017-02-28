package com.waabbuffet.kotrt.entities.render;

import com.waabbuffet.kotrt.entities.Kingdom.EntityFarmer;
import com.waabbuffet.kotrt.entities.Kingdom.EntityGold;
import com.waabbuffet.kotrt.references.SimpleReferences;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderGold extends Render<EntityGold>{
	
	public RenderGold(RenderManager renderManager) {
		
		super(renderManager);
		this.shadowSize = 0.15F;
        this.shadowOpaque = 0.75F;
       
	}

	public static final ResourceLocation texture = new ResourceLocation(SimpleReferences.Mod_ID + ":" + "textures/items/goldCoin.png");
	
	

	@Override
	public void doRender(EntityGold entity, double x, double y, double z,
			float entityYaw, float partialTicks) {
		
		    GlStateManager.pushMatrix();
	        GlStateManager.translate((float)x, (float)y, (float)z);
	     
	        this.bindEntityTexture(entity);
	        int i = 10;
	        // 4 - 12, 5 - 10
	        float f = 0.0F;
	        float f1 = 1.2F;
	       
	        float f2 = 0.2F;
	        float f3 = 1.2F;
	        
	        float f4 = 1.5F; 
	        float f5 = 0.65F; 
	        float f6 = 0.5F; 
	        
	        int j = entity.getBrightnessForRender(0.0F);
	        int k = j % 65536;
	        int l = j / 65536;
	        
	        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)k / 1.0F, (float)l / 1.0F);
	        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	        float f8 = 255.0F;
	        float f9 = ((float)0.1) / 2.0F;
	        l = (int)((MathHelper.sin(f9 + 0.0F) + 1.0F) * 0.5F * 255.0F);
	        int i1 = 255;
	        int j1 = (int)((MathHelper.sin(f9 + 4.1887903F) + 1.0F) * 0.1F * 255.0F);
	        GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
	        GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
	        float f7 = 0.3F;
	        GlStateManager.scale(0.4F, 0.6F, 0.4F);
	        Tessellator tessellator = Tessellator.getInstance();
	        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
	        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
	        worldrenderer.pos((double)(0.0F - f5), (double)(0.0F - f6), 0.0D).tex((double)f, (double)f3).color(200, 255, 200, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
	        worldrenderer.pos((double)(f4 - f5), (double)(0.0F - f6), 0.0D).tex((double)f1, (double)f3).color(200, 255, 200, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
	        worldrenderer.pos((double)(f4 - f5), (double)(1.0F - f6), 0.0D).tex((double)f1, (double)f2).color(200, 255, 200, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
	        worldrenderer.pos((double)(0.0F - f5), (double)(1.0F - f6), 0.0D).tex((double)f, (double)f2).color(200, 255, 200, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
	        tessellator.draw();
	        GlStateManager.disableBlend();
	        GlStateManager.disableRescaleNormal();
	        GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	public void bindTexture(ResourceLocation location) {
		
		  this.renderManager.renderEngine.bindTexture(location);
	}
	
	

	@Override
	protected ResourceLocation getEntityTexture(EntityGold entity) {
		// TODO Auto-generated method stub
		return this.texture;
	}
}