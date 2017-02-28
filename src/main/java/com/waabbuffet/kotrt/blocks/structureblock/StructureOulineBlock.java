package com.waabbuffet.kotrt.blocks.structureblock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StructureOulineBlock extends Block{

	private boolean DespawnBlock = false;
	
	public StructureOulineBlock(Material materialIn) {
		super(materialIn);
		
		
		// TODO Auto-generated constructor stub
	}
	
	
	
	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		
		DespawnBlock = true;
		
		worldIn.setBlockToAir(pos);
		
		super.onBlockClicked(worldIn, pos, playerIn);
	}
	
	
	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
		if(this.DespawnBlock){
			
			this.DespawnBlock = false;
			worldIn.setBlockToAir(pos);
		}
		super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
	}

}
