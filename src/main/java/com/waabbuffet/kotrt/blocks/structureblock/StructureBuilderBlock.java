package com.waabbuffet.kotrt.blocks.structureblock;

import com.waabbuffet.kotrt.KnightsOfTheRoundTable;
import com.waabbuffet.kotrt.tileEntities.structure.TileEntityKingdomStructureBuilderBlock;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class StructureBuilderBlock extends Block implements ITileEntityProvider {


	
	public StructureBuilderBlock(Material materialIn) {
			super(materialIn);
			
			this.setHardness(1.0F);
			this.isBlockContainer = true;
			
	}
   
	@Override
	public boolean isOpaqueCube(IBlockState state) {
	
		return false;
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		
		
		return new TileEntityKingdomStructureBuilderBlock();
	}

	
	@Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntityKingdomStructureBuilderBlock SB = (TileEntityKingdomStructureBuilderBlock) world.getTileEntity(pos);
	    InventoryHelper.dropInventoryItems(world, pos, SB);
	    super.breakBlock(world, pos, state);
    }

    @Override
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int eventID, int eventParam) {
        super.eventReceived(state,worldIn,pos, eventID, eventParam);
       
        
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity == null ? false : tileentity.receiveClientEvent(eventID, eventParam);
    }
	
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    	
        if (stack.hasDisplayName()) {
            ((TileEntityKingdomStructureBuilderBlock) worldIn.getTileEntity(pos)).setCustomName(stack.getDisplayName());
        }
        
       
    }
    
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
       
    	
    	if (!world.isRemote) {
    		player.openGui(KnightsOfTheRoundTable.instance, 1, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }
	
}

