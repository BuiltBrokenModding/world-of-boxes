package com.builtbroken.worldofboxes;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 3/30/2018.
 */
@Mod(modid = WorldOfBoxes.DOMAIN, name = "World of Boxes", version = WorldOfBoxes.VERSION)
@Mod.EventBusSubscriber
public class WorldOfBoxes
{
    //ID stuff
    public static final String DOMAIN = "worldofboxes";
    public static final String PREFIX = DOMAIN + ":";

    //Version stuff
    public static final String MAJOR_VERSION = "@MAJOR@";
    public static final String MINOR_VERSION = "@MINOR@";
    public static final String REVISION_VERSION = "@REVIS@";
    public static final String BUILD_VERSION = "@BUILD@";
    public static final String MC_VERSION = "@MC@";
    public static final String VERSION = MC_VERSION + "-" + MAJOR_VERSION + "." + MINOR_VERSION + "." + REVISION_VERSION + "." + BUILD_VERSION;


    @Mod.Instance(DOMAIN)
    public static WorldOfBoxes INSTANCE;

    public static final int ENTITY_ID_PREFIX = 50;
    private static int nextEntityID = ENTITY_ID_PREFIX;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
    }

    @SubscribeEvent
    public static void registerEntity(RegistryEvent.Register<EntityEntry> event)
    {

    }
}
