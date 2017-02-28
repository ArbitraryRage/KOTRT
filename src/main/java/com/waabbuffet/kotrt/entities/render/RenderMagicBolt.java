package com.waabbuffet.kotrt.entities.render;

import java.util.Random;

import com.waabbuffet.kotrt.entities.Kingdom.barracks.EntityMagicBolt;
import com.waabbuffet.kotrt.references.SimpleReferences;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderMagicBolt extends Render<EntityMagicBolt>
{
	public static final ResourceLocation texture = new ResourceLocation(SimpleReferences.Mod_ID + ":" + "textures/items/BoltParticle.png");
	
	
	
    public RenderMagicBolt(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity>) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doe
     */
    
    
   
    public void doRender(EntityMagicBolt entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
    	
    	 GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y, (float)z);
         Random b = new Random();
         
         
         GlStateManager.disableTexture2D();
         GlStateManager.disableLighting();
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(770, 1);
         this.bindEntityTexture(entity);
         int i = 10;
         
         // 1 - 14, 14 - 1
         float f = 0.0F;
         float f1 = 1.0F;
        
         float f2 = 0.0F;
         float f3 = 1.0F;
         
         float f4 = 1.3F; 
         float f5 = 0.65F; 
         float f6 = 0.0F; 
         
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
       
         
         float f7 = 0.3F;
         GlStateManager.scale(0.2F, 0.2F, 0.2F);
         Tessellator tessellator = Tessellator.getInstance();
         WorldRenderer worldrenderer = tessellator.getWorldRenderer();
         
         
         worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
  
         worldrenderer.pos((double)2.0D, (double)0.0F, 0.0D).tex((double)2.0, (double)1.0).color(200 , 255, 200, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
         worldrenderer.pos((double)2.0D, (double)2.0F, 0.0D).tex((double)2.0, (double)0.0).color(200 , 255, 200, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
         worldrenderer.pos((double)0.0D, (double)2.0F, 0.0D).tex((double)0.0, (double)0.0).color(200 , 255, 200, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
         worldrenderer.pos((double)0.0D, (double)0.0F, 0.0D).tex((double)0.0, (double)1.0).color(200 , 255, 200, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
       
         
         worldrenderer.pos((double)0.0D, (double)0.0F, 0.0D).tex((double)0.0, (double)1.0).color(200 , 255, 200, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
         worldrenderer.pos((double)0.0D, (double)2.0F, 0.0D).tex((double)0.0, (double)0.0).color(200 , 255, 200, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
         worldrenderer.pos((double)2.0D, (double)2.0F, 0.0D).tex((double)2.0, (double)0.0).color(200 , 255, 200, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
         worldrenderer.pos((double)2.0D, (double)0.0F, 0.0D).tex((double)2.0, (double)1.0).color(200 , 255, 200, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
         //238 4 284 
         //D = 0,0
         
      //   worldrenderer.pos((double)(0.0F - f5), (double)(0.0F - f6), 0.0D).tex((double)f, (double)f3).color(200 , 255, 200, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
      //   worldrenderer.pos((double)(f4 - f5), (double)(0.0F - f6), 0.0D).tex((double)f1, (double)f3).color(200, 255, 200, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
      //   worldrenderer.pos((double)(f4 - f5), (double)(1.0F - f6), 0.0D).tex((double)f1, (double)f2).color(200, 255, 200, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
       //  worldrenderer.pos((double)(0.0F - f5), (double)(1.0F - f6), 0.0D).tex((double)f, (double)f2).color(200, 255, 200, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
        
         

         GlStateManager.disableBlend();
         GlStateManager.enableLighting();
         GlStateManager.enableTexture2D();
         
         tessellator.draw();
         GlStateManager.disableBlend();
         GlStateManager.disableRescaleNormal();
         GlStateManager.popMatrix();
    	
    	
    	
    	
        
/*
        for (int i = 7; i >= 0; --i)
        {
            adouble[i] = d0;
            adouble1[i] = d1;
            d0 += (double)(random.nextInt(11) - 5);
            d1 += (double)(random.nextInt(11) - 5);
        }
        
      
        

        for (int k1 = 0; k1 < 3; ++k1)
        {
            Random random1 = new Random(entity.boltVertex);

            for (int j = 0; j < 3; ++j)
            {
                int k = 7;
                int l = 0;

                if (j > 0)
                {
                    k = 7 - j;
                }

                if (j > 0)
                {
                    l = k - 2;
                }

                
                double d2 = adouble[k] - d0;
                double d3 = adouble1[k] - d1;

                for (int i1 = k; i1 >= l; --i1)
                {
                    double d4 = d2;
                    double d5 = d3;

                    if (j == 0)
                    {
                        d2 += (double)(random1.nextInt(11) - 5);
                        d3 += (double)(random1.nextInt(11) - 5);
                    }
                    else
                    {
                        d2 += (double)(random1.nextInt(31) - 15);
                        d3 += (double)(random1.nextInt(31) - 15);
                    }

                    worldrenderer.begin(5, DefaultVertexFormats.POSITION_COLOR);
                    float f = 0.5F;
                    float f1 = 0.45F;
                    float f2 = 0.45F;
                    float f3 = 0.5F;
                    double d6 = 0.1D + (double)k1 * 0.2D;

                    if (j == 0)
                    {
                        d6 *= (double)i1 * 0.1D + 1.0D;
                    }

                    double d7 = 0.1D + (double)k1 * 0.2D;

                    if (j == 0)
                    {
                        d7 *= (double)(i1 - 1) * 0.1D + 1.0D;
                    }

                    for (int j1 = 0; j1 < 2; ++j1)
                    {
                        double d8 = x + 0.5D - d6;
                        double d9 = z + 0.5D - d6;

                        if (j1 == 1 || j1 == 2)
                        {
                            d8 += d6 * 2.0D;
                        }

                        if (j1 == 2 || j1 == 3)
                        {
                            d9 += d6 * 2.0D;
                        }

                        double d10 = x + 0.5D - d7;
                        double d11 = z + 0.5D - d7;

                        if (j1 == 1 || j1 == 2)
                        {
                            d10 += d7 * 2.0D;
                        }

                        if (j1 == 2 || j1 == 3)
                        {
                            d11 += d7 * 2.0D;
                        }

                    /*X1:
                     * [22:49:46] [Client thread/INFO] [STDOUT]: [com.waabbuffet.kotrt.entities.render.RenderMagicBolt:doRender:154]: X: -20.21604120829527
				       [22:49:46] [Client thread/INFO] [STDOUT]: [com.waabbuffet.kotrt.entities.render.RenderMagicBolt:doRender:155]: Y: 112.0
				       [22:49:46] [Client thread/INFO] [STDOUT]: [com.waabbuffet.kotrt.entities.render.RenderMagicBolt:doRender:156]: Z: 10.91815399868376 
                     * 
                     */
                
                      /*[23:01:15] [Client thread/INFO] [STDOUT]: [com.waabbuffet.kotrt.entities.render.RenderMagicBolt:doRender:-1]: X2: -12.270847991818618
                        [23:01:15] [Client thread/INFO] [STDOUT]: [com.waabbuffet.kotrt.entities.render.RenderMagicBolt:doRender:-1]: Y2: 128.0
                        [23:01:15] [Client thread/INFO] [STDOUT]: [com.waabbuffet.kotrt.entities.render.RenderMagicBolt:doRender:-1]: Z2: 0.8698819585328805
                       * 
                       */
                    /*    
                        worldrenderer.pos(-8.91, 0, 0.268).color(0.45F, 0.45F, 0.5F, 0.3F).endVertex();
                        worldrenderer.pos(-1.92, 16, 3.22).color(0.45F, 0.45F, 0.5F, 0.3F).endVertex();
                       
                        
                        worldrenderer.pos(d10 + d2, y + (double)(i1 * 16), d11 + d3).color(0.45F, 0.45F, 0.5F, 0.3F).endVertex();
                        worldrenderer.pos(d8 + d4, y + (double)((i1 + 1) * 16), d9 + d5).color(0.45F, 0.45F, 0.5F, 0.3F).endVertex();
                        
                    }

                    tessellator.draw();
                }
            }
        }
        */

      
      
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	/*
	    GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        Random b = new Random();
        
        
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 1);
        this.bindEntityTexture(entity);
        int i = 10;
        
        // 1 - 14, 14 - 1
        float f = 0.0F;
        float f1 = 1.0F;
       
        float f2 = 0.0F;
        float f3 = 1.0F;
        
        float f4 = 1.3F; 
        float f5 = 0.65F; 
        float f6 = 0.0F; 
        
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
        
        GlStateManager.rotate(entity.Ticks * 5, 0.5F, 0.5F, 0.5F);
        
        float f7 = 0.3F;
        
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        
        
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
    
    
        
   
        worldrenderer.pos((double)1.0D, (double)0.0F, 0.0D).tex((double)1.0, (double)1.0).color(200 , 255, 200, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
        worldrenderer.pos((double)1.0D, (double)1.0F, 0.0D).tex((double)1.0, (double)0.0).color(200 , 255, 200, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
        worldrenderer.pos((double)0.0D, (double)1.0F, 0.0D).tex((double)0.0, (double)0.0).color(200 , 255, 200, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
        worldrenderer.pos((double)0.0D, (double)0.0F, 0.0D).tex((double)0.0, (double)1.0).color(200 , 255, 200, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
      
        //238 4 284 
        //D = 0,0
        
     //   worldrenderer.pos((double)(0.0F - f5), (double)(0.0F - f6), 0.0D).tex((double)f, (double)f3).color(200 , 255, 200, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
     //   worldrenderer.pos((double)(f4 - f5), (double)(0.0F - f6), 0.0D).tex((double)f1, (double)f3).color(200, 255, 200, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
     //   worldrenderer.pos((double)(f4 - f5), (double)(1.0F - f6), 0.0D).tex((double)f1, (double)f2).color(200, 255, 200, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
      //  worldrenderer.pos((double)(0.0F - f5), (double)(1.0F - f6), 0.0D).tex((double)f, (double)f2).color(200, 255, 200, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
       
        

        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        
        tessellator.draw();
        GlStateManager.disableBlend();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        */
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityMagicBolt entity)
    {
        return texture;
    }
}
