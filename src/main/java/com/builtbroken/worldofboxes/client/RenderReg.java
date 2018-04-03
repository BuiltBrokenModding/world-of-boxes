package com.builtbroken.worldofboxes.client;

import com.builtbroken.worldofboxes.WorldOfBoxes;
import com.builtbroken.worldofboxes.reg.WBBlocks;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 4/2/2018.
 */
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = WorldOfBoxes.DOMAIN)
public final class RenderReg
{
    @SubscribeEvent
    public static void registerBlockColors(ColorHandlerEvent.Block event)
    {
        event.getBlockColors().registerBlockColorHandler(new IBlockColor()
        {
            Random random = new Random();

            @Override
            public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
            {
                int color = worldIn != null && pos != null
                        ? BiomeColorHelper.getGrassColorAtPos(worldIn, pos)
                        : ColorizerGrass.getGrassColor(0.5D, 1.0D);
                return color + random.nextInt(100);
            }
        }, WBBlocks.GRASS);

        event.getBlockColors().registerBlockColorHandler(new IBlockColor()
        {
            Random random = new Random();

            @Override
            public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
            {
                BlockPlanks.EnumType blockplanks$enumtype = (BlockPlanks.EnumType) state.getValue(BlockOldLeaf.VARIANT);

                if (blockplanks$enumtype == BlockPlanks.EnumType.SPRUCE)
                {
                    return ColorizerFoliage.getFoliageColorPine();
                }
                else if (blockplanks$enumtype == BlockPlanks.EnumType.BIRCH)
                {
                    return ColorizerFoliage.getFoliageColorBirch();
                }
                else
                {
                    int color = worldIn != null && pos != null
                            ? BiomeColorHelper.getFoliageColorAtPos(worldIn, pos)
                            : ColorizerFoliage.getFoliageColorBasic();
                    return color + random.nextInt(100);
                }
            }
        }, WBBlocks.LEAF);
    }
}
