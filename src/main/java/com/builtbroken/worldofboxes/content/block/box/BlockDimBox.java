package com.builtbroken.worldofboxes.content.block.box;

import com.builtbroken.worldofboxes.WorldOfBoxes;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
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
    public static final PropertyDirection FACING = PropertyDirection.create("facing");

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
        if (!worldIn.isRemote && playerIn instanceof EntityPlayerMP)
        {
            if (!playerIn.isSneaking())
            {
                //Check that above the box is clear
                RayTraceResult result = worldIn.rayTraceBlocks(
                        new Vec3d(pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5),
                        new Vec3d(pos.getX() + 0.5, pos.getY() + 3.9, pos.getZ() + 0.5));

                boolean isAboveClear = result == null || result.typeOfHit == RayTraceResult.Type.MISS;

                if (isAboveClear)
                {

                    TileEntity tile = worldIn.getTileEntity(pos);
                    if (tile instanceof TileEntityDimBox)
                    {
                        //Setup box
                        if (!((TileEntityDimBox) tile).isSetup())
                        {
                            //TODO play animation
                            //TODO use GUI for randomization
                            //playerIn.openGui(WorldOfBoxes.INSTANCE, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
                            ((TileEntityDimBox) tile).randomizeDim();
                        }

                        //Teleport player
                        //TODO play animation
                        ((TileEntityDimBox) tile).teleport((EntityPlayerMP) playerIn);
                    }
                }
                else
                {
                    playerIn.sendMessage(new TextComponentString("Zoidberg: The box says no..."));
                    playerIn.sendMessage(new TextComponentString("TheBox: Clear 3 blocks above to open \\0/"));

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

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getIndex();
    }
}
