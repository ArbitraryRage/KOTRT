package com.waabbuffet.kotrt.entities.render;

import com.waabbuffet.kotrt.entities.Kingdom.EntityFarmer;
import com.waabbuffet.kotrt.references.SimpleReferences;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderFarmer extends RenderBiped{
	public static final ResourceLocation texture = new ResourceLocation(SimpleReferences.Mod_ID + ":" + "textures/entities/ManReg3.png");
	protected ModelBiped modelEntity;
	

	public RenderFarmer(ModelBiped p_i1262_1_, float p_i1262_2_) {
		super(Minecraft.getMinecraft().getRenderManager(), p_i1262_1_, p_i1262_2_);
		modelEntity = ((ModelBiped) mainModel);
	}
	public void renderMainDude(EntityFarmer entity, double x, double y, double z, float u, float v){
		super.doRender(entity, x, y, z, u, v);
	}
	
	public void doRenderLiving(EntityLiving entityLiving, double x, double y, double z, float u, float v){
		renderMainDude((EntityFarmer)entityLiving, x, y, z, u , v);
	}
	public void doRender(Entity entity, double x, double y, double z, float u, float v){
		renderMainDude((EntityFarmer)entity, x, y, z, u , v);
	}

	@Override
	public void bindTexture(ResourceLocation location) {
		
		  this.renderManager.renderEngine.bindTexture(location);
	}
	
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return texture;
	}

}