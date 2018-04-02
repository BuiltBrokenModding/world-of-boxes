package com.builtbroken.worldofboxes.reg;

import com.builtbroken.worldofboxes.WorldOfBoxes;
import com.builtbroken.worldofboxes.content.block.BlockCBGrass;
import com.builtbroken.worldofboxes.content.block.BlockCBTallGrass;
import com.builtbroken.worldofboxes.content.block.box.BlockDimBox;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

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

    @GameRegistry.ObjectHolder("leaves")
    public static final Block LEAF = null;

    @GameRegistry.ObjectHolder("grass")
    public static final Block GRASS = null;

    @GameRegistry.ObjectHolder("tallgrass")
    public static final Block TALL_GRASS = null;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().register(new BlockDimBox());
        event.getRegistry().register(new BlockCBGrass());
        event.getRegistry().register(new BlockOldLog().setUnlocalizedName(WorldOfBoxes.PREFIX + "log").setRegistryName(WorldOfBoxes.DOMAIN, "log"));
        event.getRegistry().register(new BlockOldLeaf().setUnlocalizedName(WorldOfBoxes.PREFIX + "leaves").setRegistryName(WorldOfBoxes.DOMAIN, "leaves"));
        event.getRegistry().register(new BlockCBTallGrass());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(new ItemBlock(BOX).setRegistryName(BOX.getRegistryName()));
        event.getRegistry().register(new ItemBlock(LOG).setRegistryName(LOG.getRegistryName()));
        event.getRegistry().register(new ItemBlock(LEAF).setRegistryName(LEAF.getRegistryName()));
        event.getRegistry().register(new ItemBlock(GRASS).setRegistryName(GRASS.getRegistryName()));
        event.getRegistry().register(new ItemBlock(TALL_GRASS).setRegistryName(TALL_GRASS.getRegistryName()));
    }
}
