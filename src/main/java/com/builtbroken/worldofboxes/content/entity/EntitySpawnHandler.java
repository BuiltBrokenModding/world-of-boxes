package com.builtbroken.worldofboxes.content.entity;

import com.builtbroken.worldofboxes.WorldOfBoxes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 4/3/2018.
 */
@Mod.EventBusSubscriber(modid = WorldOfBoxes.DOMAIN)
public class EntitySpawnHandler
{
    public static final String COLOR_TAG = WorldOfBoxes.PREFIX + "boxcolor";
    public static final List<Class<? extends Entity>> supportedEntities = new ArrayList();

    static
    {
        supportedEntities.add(EntityZombie.class);
        supportedEntities.add(EntityPig.class);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onSpawn(EntityJoinWorldEvent event)
    {
        final Entity entity = event.getEntity();
        final World world = entity.getEntityWorld();
        if (world.provider.getDimensionType() == WorldOfBoxes.dimensionType
                && supportedEntities.contains(entity.getClass()))
        {
            Color color = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
            entity.getEntityData().setInteger(COLOR_TAG, color.getRGB());
        }
    }
}
