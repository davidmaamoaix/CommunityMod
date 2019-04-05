package com.mcmoddev.communitymod.davidm.extrarandomness.common.block;

import java.util.List;

import com.mcmoddev.communitymod.davidm.extrarandomness.common.tileentity.TileEntityCapacitor;
import com.mcmoddev.communitymod.davidm.extrarandomness.core.EnumCapacitor;
import com.mcmoddev.communitymod.davidm.extrarandomness.core.helper.NBTHelper;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMemeCapacitor extends Block {

	private final EnumCapacitor enumCapacitor;
	
	public BlockMemeCapacitor(EnumCapacitor enumCapacitor) {
		super(enumCapacitor.getMaterial());
		this.setHardness(enumCapacitor.getHardness());
		this.setHarvestLevel("pickaxe", 2);
		this.setSoundType(SoundType.METAL);
		this.setResistance(150);
		
		this.enumCapacitor = enumCapacitor;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity instanceof TileEntityCapacitor) {
			((TileEntityCapacitor) tileEntity).onRightClick(player, facing);
		}
		return true;
	}
	
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(world, pos, state, placer, stack);
		
		TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity instanceof TileEntityCapacitor) {
			((TileEntityCapacitor) tileEntity).setCapacitorData(this.enumCapacitor);
		}
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityCapacitor();
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
		int currentPower =  NBTHelper.getOrCreateCompound(stack).getInteger("power");
		int totalPower = this.enumCapacitor.getPower();
		tooltip.add(TextFormatting.AQUA + I18n.format("tooltip.community_mod.meme_capacitor",currentPower, totalPower));
	}
	
	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public int getLightOpacity(IBlockState state) {
		return 0;
	}
	
	public EnumCapacitor getEnumCapacitor() {
		return this.enumCapacitor;
	}
}