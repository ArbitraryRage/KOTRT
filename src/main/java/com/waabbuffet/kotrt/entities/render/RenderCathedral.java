package com.waabbuffet.kotrt.entities.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

import com.waabbuffet.kotrt.entities.Kingdom.EntityCathedral;
import com.waabbuffet.kotrt.references.SimpleReferences;

public class RenderCathedral extends RenderBiped<EntityCathedral> {
	
	public static final ResourceLocation texture = new ResourceLocation(SimpleReferences.Mod_ID + ":" + "textures/entities/mage.png");
	protected ModelBiped modelEntity;
	

	
	
	public RenderCathedral(ModelBiped p_i1262_1_, float p_i1262_2_) {
		super(Minecraft.getMinecraft().getRenderManager(), p_i1262_1_, p_i1262_2_);
		modelEntity = ((ModelBiped) mainModel);
		
		this.addLayer(new LayerBipedArmor(this));
		 
		
	}
	public void renderMainDude(EntityCathedral entity, double x, double y, double z, float u, float v){
		super.doRender(entity, x, y, z, u, v);
	}
	
	public void doRenderLiving(EntityLiving entityLiving, double x, double y, double z, float u, float v){
		renderMainDude((EntityCathedral)entityLiving, x, y, z, u , v);
	}
	public void doRender(EntityCathedral entity, double x, double y, double z, float u, float v){
		renderMainDude((EntityCathedral)entity, x, y, z, u , v);
	}

	@Override
	public void bindTexture(ResourceLocation location) {
		
		  this.renderManager.renderEngine.bindTexture(location);
	}
	
	protected ResourceLocation getEntityTexture(EntityCathedral p_110775_1_) {
		return texture;
	}

}