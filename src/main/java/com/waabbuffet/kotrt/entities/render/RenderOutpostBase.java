package com.waabbuffet.kotrt.entities.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;



import com.waabbuffet.kotrt.entities.Outpost.EntityOutpostBase;
import com.waabbuffet.kotrt.references.SimpleReferences;

public class RenderOutpostBase extends RenderBiped {
	public static final ResourceLocation texture = new ResourceLocation(SimpleReferences.Mod_ID + ":" + "textures/entities/GirlReg.png");
	public static final ResourceLocation texture1 = new ResourceLocation(SimpleReferences.Mod_ID + ":" + "textures/entities/GirlReg1.png");
	public static final ResourceLocation texture2 = new ResourceLocation(SimpleReferences.Mod_ID + ":" + "textures/entities/GirlReg2.png");
	public static final ResourceLocation texture3 = new ResourceLocation(SimpleReferences.Mod_ID + ":" + "textures/entities/GirlReg3.png");
	public static final ResourceLocation texture4 = new ResourceLocation(SimpleReferences.Mod_ID + ":" + "textures/entities/GirlReg4.png");
	public static final ResourceLocation texture5 = new ResourceLocation(SimpleReferences.Mod_ID + ":" + "textures/entities/GirlReg5.png");
	public static final ResourceLocation texture6 = new ResourceLocation(SimpleReferences.Mod_ID + ":" + "textures/entities/ManRegular.png");
	public static final ResourceLocation texture7 = new ResourceLocation(SimpleReferences.Mod_ID + ":" + "textures/entities/ManReg1.png");
	public static final ResourceLocation texture8 = new ResourceLocation(SimpleReferences.Mod_ID + ":" + "textures/entities/ManReg2.png");
	public static final ResourceLocation texture9 = new ResourceLocation(SimpleReferences.Mod_ID + ":" + "textures/entities/ManReg4.png");
	public static final ResourceLocation texture10 = new ResourceLocation(SimpleReferences.Mod_ID + ":" + "textures/entities/ShopKeeperOne.png");
	public static final ResourceLocation texture11 = new ResourceLocation(SimpleReferences.Mod_ID + ":" + "textures/entities/ShopKeeperFour.png");
	public static final ResourceLocation texture12 = new ResourceLocation(SimpleReferences.Mod_ID + ":" + "textures/entities/ShopKeeperFive.png");
	public static final ResourceLocation texture13 = new ResourceLocation(SimpleReferences.Mod_ID + ":" + "textures/entities/WarriorGirl.png");
	public static final ResourceLocation texture14 = new ResourceLocation(SimpleReferences.Mod_ID + ":" + "textures/entities/ManGuard.png");
	
	protected ModelBiped modelEntity;
	

	public RenderOutpostBase(ModelBiped p_i1262_1_, float p_i1262_2_) {
		super(Minecraft.getMinecraft().getRenderManager(), p_i1262_1_, p_i1262_2_);
		modelEntity = ((ModelBiped) mainModel);
	}
	public void renderMainDude(EntityOutpostBase entity, double x, double y, double z, float u, float v){
		super.doRender(entity, x, y, z, u, v);
	}
	
	public void doRenderLiving(EntityLiving entityLiving, double x, double y, double z, float u, float v){
		renderMainDude((EntityOutpostBase)entityLiving, x, y, z, u , v);
	}
	public void doRender(Entity entity, double x, double y, double z, float u, float v){
		renderMainDude((EntityOutpostBase)entity, x, y, z, u , v);
	}

	@Override
	public void bindTexture(ResourceLocation location) {
		
		  this.renderManager.renderEngine.bindTexture(location);
	}
	
	protected ResourceLocation getEntityTexture(Entity entity) {
		
		EntityOutpostBase entity1 = (EntityOutpostBase)entity;
		
		
		switch(entity1.getVillagerSkinID()){
		case 1:
			return texture;
		case 2:
			return texture1;
		case 3:
			return texture2;
		case 4:
			return texture3;
		case 5:
			return texture4;
		case 6:
			return texture5;	
		case 7:
			return texture6;
		case 8:
			return texture7;
		case 9:
			return texture8;
		case 10:
			return texture9;
		case 11:
			return texture10;
		case 12:
			return texture11;
		case 13:
			return texture12;
		case 14:
			return texture13; // guard
		case 15:
			return texture14; // guard
		default:
			return texture;
		}
		
		
	}

}