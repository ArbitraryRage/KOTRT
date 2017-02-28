package com.waabbuffet.kotrt.blocks.structureblock;

import java.util.Random;

import com.waabbuffet.kotrt.KnightsOfTheRoundTable;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBlock;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

public class StructureBlock extends Block implements ITileEntityProvider {

	public StructureBlock(Material materialIn) {
			super(materialIn);
			
			
			
			this.isBlockContainer = true;
			this.setBlockUnbreakable();
			
	}
   
	
		
	@Override
	public boolean isOpaqueCube() {
	
		return false;
	}
	
	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		
		return EnumWorldBlockLayer.CUTOUT;
	}
	
	
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		// TODO Auto-generated method stub
		return new TileEntityKingdomStructureBlock();
	}

	
	@Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
		
		try{
			TileEntityKingdomStructureBlock SB = (TileEntityKingdomStructureBlock) world.getTileEntity(pos);
			InventoryHelper.dropInventoryItems(world, pos, SB);
		}catch(NullPointerException b){
			
		}
	    
	    super.breakBlock(world, pos, state);
    }

    @Override
    public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam) {
        super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);
       
        
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity == null ? false : tileentity.receiveClientEvent(eventID, eventParam);
    }
	
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    	
        if (stack.hasDisplayName()) {
            ((TileEntityKingdomStructureBlock) worldIn.getTileEntity(pos)).setCustomName(stack.getDisplayName());
        }
    }
    
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
       
    	
    	if (!world.isRemote) {
            player.openGui(KnightsOfTheRoundTable.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }
	
}
