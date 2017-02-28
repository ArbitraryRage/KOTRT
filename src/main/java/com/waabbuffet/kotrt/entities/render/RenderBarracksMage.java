package com.waabbuffet.kotrt.entities.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

import com.waabbuffet.kotrt.entities.Kingdom.barracks.EntityBarracksHunter;
import com.waabbuffet.kotrt.entities.Kingdom.barracks.EntityBarracksMage;
import com.waabbuffet.kotrt.references.SimpleReferences;

public class RenderBarracksMage extends RenderBiped<EntityBarracksMage> {
	
	public static final ResourceLocation texture = new ResourceLocation(SimpleReferences.Mod_ID + ":" + "textures/entities/mage.png");
	protected ModelBiped modelEntity;
	

	
	
	public RenderBarracksMage(ModelBiped p_i1262_1_, float p_i1262_2_) {
		super(Minecraft.getMinecraft().getRenderManager(), p_i1262_1_, p_i1262_2_);
		modelEntity = ((ModelBiped) mainModel);
		
		this.addLayer(new LayerBipedArmor(this));
		 
		
	}
	public void renderMainDude(EntityBarracksMage entity, double x, double y, double z, float u, float v){
		super.doRender(entity, x, y, z, u, v);
	}
	
	public void doRenderLiving(EntityLiving entityLiving, double x, double y, double z, float u, float v){
		renderMainDude((EntityBarracksMage)entityLiving, x, y, z, u , v);
	}
	public void doRender(EntityBarracksMage entity, double x, double y, double z, float u, float v){
		renderMainDude((EntityBarracksMage)entity, x, y, z, u , v);
	}

	@Override
	public void bindTexture(ResourceLocation location) {
		
		  this.renderManager.renderEngine.bindTexture(location);
	}
	
	protected ResourceLocation getEntityTexture(EntityBarracksMage p_110775_1_) {
		return texture;
	}

}