package com.builtbroken.worldofboxes.client;

import com.builtbroken.worldofboxes.WorldOfBoxes;
import com.builtbroken.worldofboxes.client.world.SkyBoxRenderBox;
import com.builtbroken.worldofboxes.content.block.BlockCBTallGrass;
import com.builtbroken.worldofboxes.content.block.box.TESRBox;
import com.builtbroken.worldofboxes.content.block.box.TileEntityDimBox;
import com.builtbroken.worldofboxes.reg.WBBlocks;
import com.builtbroken.worldofboxes.world.BoxWorldProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 4/2/2018.
 */
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = WorldOfBoxes.DOMAIN)
public final class RenderReg
{
    public static final Random random = new Random();

    @SubscribeEvent
    public static void registerBlockColors(ColorHandlerEvent.Block event)
    {
        event.getBlockColors().registerBlockColorHandler(new IBlockColor()
        {
            @Override
            public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
            {
                int color = worldIn != null && pos != null
                        ? BiomeColorHelper.getGrassColorAtPos(worldIn, pos)
                        : ColorizerGrass.getGrassColor(0.5D, 1.0D);
                return color + (worldIn != null ? random.nextInt(100) : 0);
            }
        }, WBBlocks.GRASS);

        event.getBlockColors().registerBlockColorHandler(new IBlockColor()
        {
            @Override
            public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
            {
                BlockPlanks.EnumType type = (BlockPlanks.EnumType) state.getValue(BlockOldLeaf.VARIANT);

                int color;

                if (type == BlockPlanks.EnumType.SPRUCE)
                {
                    color = ColorizerFoliage.getFoliageColorPine();
                }
                else if (type == BlockPlanks.EnumType.BIRCH)
                {
                    color = ColorizerFoliage.getFoliageColorBirch();
                }
                else
                {
                    color = worldIn != null && pos != null
                            ? BiomeColorHelper.getFoliageColorAtPos(worldIn, pos)
                            : ColorizerFoliage.getFoliageColorBasic();

                }
                return color + (worldIn != null ? random.nextInt(100) : 0);
            }
        }, WBBlocks.LEAF);
        event.getBlockColors().registerBlockColorHandler(new IBlockColor()
        {
            public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
            {
                return worldIn != null && pos != null ? BiomeColorHelper.getFoliageColorAtPos(worldIn, pos) : ColorizerFoliage.getFoliageColorBasic();
            }
        }, WBBlocks.LEAF2);
        event.getBlockColors().registerBlockColorHandler(new IBlockColor()
        {
            public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
            {
                int color;
                if (worldIn != null && pos != null)
                {
                    color = BiomeColorHelper.getGrassColorAtPos(worldIn, pos);
                }
                else
                {
                    color = state.getValue(BlockCBTallGrass.TYPE) == BlockCBTallGrass.EnumType.DEAD_BUSH ? 16777215 : ColorizerGrass.getGrassColor(0.5D, 1.0D);
                }
                return color + (worldIn != null ? random.nextInt(100) : 0);
            }
        }, WBBlocks.TALL_GRASS);
    }

    @SubscribeEvent
    public static void registerItemColors(ColorHandlerEvent.Item event)
    {
        event.getItemColors().registerItemColorHandler(new IItemColor()
        {
            public int colorMultiplier(ItemStack stack, int tintIndex)
            {
                IBlockState iblockstate = ((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());
                return event.getBlockColors().colorMultiplier(iblockstate, (IBlockAccess) null, (BlockPos) null, tintIndex);
            }
        }, WBBlocks.LEAF, WBBlocks.LEAF2, WBBlocks.TALL_GRASS, WBBlocks.GRASS);
    }

    @SubscribeEvent
    public static void registerAllModels(ModelRegistryEvent event)
    {
        addItemRender(WBBlocks.BOX);
        addItemSubtypesRender(WBBlocks.LOG, 0, 3);
        addItemSubtypesRender(WBBlocks.LOG2, 0, 1);
        addItemSubtypesRender(WBBlocks.LEAF, 0, 3);
        addItemSubtypesRender(WBBlocks.LEAF2, 0, 1);
        addItemRender(WBBlocks.GRASS);
        addItemSubtypesRender(WBBlocks.TALL_GRASS, 0, 2);

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDimBox.class, new TESRBox());
    }

    private static void addItemRender(Block block)
    {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }

    private static void addItemSubtypesRender(Block block, int min, int max)
    {
        Item item = Item.getItemFromBlock(block);
        ResourceLocation name = block.getRegistryName();
        ModelLoader.setCustomStateMapper(block, new DefaultStateMapper()
        {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state)
            {
                return new ModelResourceLocation(name, getPropertyString(state.getProperties()));
            }
        });
        for (int i = min; i <= max; i++)
        {
            ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(name, getPropertyString(block.getStateFromMeta(i).getProperties())));
        }
    }

    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event)
    {
        if (event.getWorld().provider instanceof BoxWorldProvider)
        {
            event.getWorld().provider.setSkyRenderer(new SkyBoxRenderBox());
        }
    }

    public static String getPropertyString(Map<IProperty<?>, Comparable<?>> values, String... extrasArgs)
    {
        StringBuilder stringbuilder = new StringBuilder();

        for (Map.Entry<IProperty<?>, Comparable<?>> entry : values.entrySet())
        {
            if (stringbuilder.length() != 0)
            {
                stringbuilder.append(",");
            }

            IProperty<?> iproperty = entry.getKey();
            stringbuilder.append(iproperty.getName());
            stringbuilder.append("=");
            stringbuilder.append(getPropertyName(iproperty, entry.getValue()));
        }


        if (stringbuilder.length() == 0)
        {
            stringbuilder.append("inventory");
        }

        for (String args : extrasArgs)
        {
            if (stringbuilder.length() != 0)
            {
                stringbuilder.append(",");
            }
            stringbuilder.append(args);
        }

        return stringbuilder.toString();
    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> String getPropertyName(IProperty<T> property, Comparable<?> comparable)
    {
        return property.getName((T) comparable);
    }
}
