package com.builtbroken.worldofboxes.client.entity;

import com.builtbroken.worldofboxes.WorldOfBoxes;
import com.builtbroken.worldofboxes.content.entity.EntitySpawnHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Handles replacing renders for vanilla mobs with different textures
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 3/30/2018.
 */
@Mod.EventBusSubscriber(modid = WorldOfBoxes.DOMAIN, value = Side.CLIENT)
public final class EntityRenderOverrides
{
    private static final ResourceLocation ZOMBIE_TEXTURES = new ResourceLocation("textures/entity/zombie/zombie.png");

    private static RenderCBZombie renderCBZombie;

    @SubscribeEvent
    public static void onRender(RenderLivingEvent.Pre<EntityZombie> event)
    {
        EntityLivingBase entity = event.getEntity();
        if (entity instanceof EntityZombie
                && entity.getEntityData().hasKey(EntitySpawnHandler.COLOR_TAG)
                && !(event.getRenderer() instanceof RenderCBZombie))
        {
            if (renderCBZombie == null)
            {
                renderCBZombie = new RenderCBZombie(event.getRenderer().getRenderManager());
            }

            renderCBZombie.doRender((EntityZombie) event.getEntity(), event.getX(), event.getY(), event.getZ(), event.getEntity().rotationYaw, event.getPartialRenderTick());
            event.setCanceled(true);
        }
    }
}
