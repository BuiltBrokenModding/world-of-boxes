package com.builtbroken.worldofboxes;

import com.builtbroken.worldofboxes.reg.WBBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 4/3/2018.
 */
@Mod.EventBusSubscriber
public class EventHandler
{
    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        Entity entity = event.getEntity();
        if (entity instanceof EntityPlayer && !(entity instanceof FakePlayer))
        {
            if (!entity.getEntityData().hasKey(WorldOfBoxes.PREFIX + "hasBox"))
            {
                entity.getEntityData().setBoolean(WorldOfBoxes.PREFIX + "hasBox", true);
                ((EntityPlayer) entity).inventory.addItemStackToInventory(new ItemStack(WBBlocks.BOX));
            }
        }
    }
}
