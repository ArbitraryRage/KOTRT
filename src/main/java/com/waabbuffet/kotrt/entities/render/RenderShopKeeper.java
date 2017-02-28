package com.waabbuffet.kotrt.entities.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;



import com.waabbuffet.kotrt.entities.Kingdom.EntityShopKeeper;
import com.waabbuffet.kotrt.references.SimpleReferences;

public class RenderShopKeeper extends RenderBiped {
	public static final ResourceLocation texture = new ResourceLocation(SimpleReferences.Mod_ID + ":" + "textures/entities/GirlReg1.png");
	protected ModelBiped modelEntity;
	

	public RenderShopKeeper(ModelBiped p_i1262_1_, float p_i1262_2_) {
		super(Minecraft.getMinecraft().getRenderManager(), p_i1262_1_, p_i1262_2_);
		modelEntity = ((ModelBiped) mainModel);
	}
	public void renderMainDude(EntityShopKeeper entity, double x, double y, double z, float u, float v){
		super.doRender(entity, x, y, z, u, v);
	}
	
	public void doRenderLiving(EntityLiving entityLiving, double x, double y, double z, float u, float v){
		renderMainDude((EntityShopKeeper)entityLiving, x, y, z, u , v);
	}
	public void doRender(Entity entity, double x, double y, double z, float u, float v){
		renderMainDude((EntityShopKeeper)entity, x, y, z, u , v);
	}

	@Override
	public void bindTexture(ResourceLocation location) {
		
		  this.renderManager.renderEngine.bindTexture(location);
	}
	
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return texture;
	}

}