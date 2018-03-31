package com.builtbroken.worldofboxes.content.block.box;

import com.builtbroken.worldofboxes.WorldOfBoxes;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Gateway to another dim
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 3/30/2018.
 */
public class BlockDimBox extends Block implements ITileEntityProvider
{
    public BlockDimBox()
    {
        super(Material.WOOD);
        setRegistryName(WorldOfBoxes.DOMAIN, "box");
        setUnlocalizedName(WorldOfBoxes.PREFIX + "box");
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        setBlockUnbreakable();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(playerIn instanceof EntityPlayerMP)
        {
            if (!playerIn.isSneaking())
            {
                TileEntity tile = worldIn.getTileEntity(pos);
                if (tile instanceof TileEntityDimBox)
                {
                    if (((TileEntityDimBox) tile).isSetup())
                    {
                        ((TileEntityDimBox) tile).teleport((EntityPlayerMP) playerIn);
                    }
                    else
                    {
                        //TODO use GUI for randomization
                        //playerIn.openGui(WorldOfBoxes.INSTANCE, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
                        ((TileEntityDimBox) tile).randomizeDim();
                    }
                }
            }
            else
            {
                //TODO pick up box
            }
        }
        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityDimBox();
    }
}
