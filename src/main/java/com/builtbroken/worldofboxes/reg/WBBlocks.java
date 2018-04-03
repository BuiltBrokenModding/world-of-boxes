package com.builtbroken.worldofboxes.reg;

import com.builtbroken.worldofboxes.WorldOfBoxes;
import com.builtbroken.worldofboxes.content.block.BlockCBGrass;
import com.builtbroken.worldofboxes.content.block.BlockCBTallGrass;
import com.builtbroken.worldofboxes.content.block.box.BlockDimBox;
import com.builtbroken.worldofboxes.content.block.box.TileEntityDimBox;
import net.minecraft.block.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 4/1/2018.
 */
@GameRegistry.ObjectHolder(WorldOfBoxes.DOMAIN)
@Mod.EventBusSubscriber
public final class WBBlocks
{
    @GameRegistry.ObjectHolder("box")
    public static final Block BOX = null;

    @GameRegistry.ObjectHolder("log")
    public static final Block LOG = null;

    @GameRegistry.ObjectHolder("log2")
    public static final Block LOG2 = null;

    @GameRegistry.ObjectHolder("leaves")
    public static final Block LEAF = null;

    @GameRegistry.ObjectHolder("leaves2")
    public static final Block LEAF2 = null;

    @GameRegistry.ObjectHolder("grass")
    public static final Block GRASS = null;

    @GameRegistry.ObjectHolder("tallgrass")
    public static final Block TALL_GRASS = null;

    public static CreativeTabs creativeTab;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        creativeTab = new CreativeTabs(WorldOfBoxes.DOMAIN)
        {
            @SideOnly(Side.CLIENT)
            public ItemStack getTabIconItem()
            {
                return new ItemStack(BOX);
            }
        };

        event.getRegistry().register(new BlockDimBox().setCreativeTab(creativeTab));
        event.getRegistry().register(new BlockCBGrass().setCreativeTab(creativeTab));
        event.getRegistry().register(new BlockOldLog().setUnlocalizedName(WorldOfBoxes.PREFIX + "log").setRegistryName(WorldOfBoxes.DOMAIN, "log").setCreativeTab(creativeTab));
        event.getRegistry().register(new BlockNewLog().setUnlocalizedName(WorldOfBoxes.PREFIX + "log2").setRegistryName(WorldOfBoxes.DOMAIN, "log2").setCreativeTab(creativeTab));
        event.getRegistry().register(new BlockOldLeaf().setUnlocalizedName(WorldOfBoxes.PREFIX + "leaves").setRegistryName(WorldOfBoxes.DOMAIN, "leaves").setCreativeTab(creativeTab));
        event.getRegistry().register(new BlockNewLeaf().setUnlocalizedName(WorldOfBoxes.PREFIX + "leaves2").setRegistryName(WorldOfBoxes.DOMAIN, "leaves2").setCreativeTab(creativeTab));
        event.getRegistry().register(new BlockCBTallGrass().setCreativeTab(creativeTab));

        GameRegistry.registerTileEntity(TileEntityDimBox.class, WorldOfBoxes.PREFIX + "box");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(new ItemBlock(BOX).setRegistryName(BOX.getRegistryName()));
        event.getRegistry().register(new ItemBlock(LOG).setRegistryName(LOG.getRegistryName()));
        event.getRegistry().register(new ItemBlock(LOG2).setRegistryName(LOG2.getRegistryName()));
        event.getRegistry().register(new ItemBlock(LEAF).setRegistryName(LEAF.getRegistryName()));
        event.getRegistry().register(new ItemBlock(LEAF2).setRegistryName(LEAF2.getRegistryName()));
        event.getRegistry().register(new ItemBlock(GRASS).setRegistryName(GRASS.getRegistryName()));
        event.getRegistry().register(new ItemBlock(TALL_GRASS).setRegistryName(TALL_GRASS.getRegistryName()));


    }
}
