package com.builtbroken.worldofboxes.client.entity;

import com.builtbroken.worldofboxes.WorldOfBoxes;
import com.builtbroken.worldofboxes.content.entity.EntitySpawnHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 4/3/2018.
 */
public class RenderCBZombie extends RenderZombie
{
    public static ResourceLocation ZOMBIE_TEXTURES = new ResourceLocation(WorldOfBoxes.DOMAIN, "textures/entity/zombie.png");

    public RenderCBZombie(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
    }

    @Override
    protected void renderModel(EntityZombie entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
    {
        boolean isVisible = this.isVisible(entitylivingbaseIn);
        boolean flag1 = !isVisible && !entitylivingbaseIn.isInvisibleToPlayer(Minecraft.getMinecraft().player);

        if (isVisible || flag1)
        {
            if (!this.bindEntityTexture(entitylivingbaseIn))
            {
                return;
            }

            if (flag1)
            {
                GlStateManager.enableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
            }

            if (entitylivingbaseIn.getEntityData().hasKey(EntitySpawnHandler.COLOR_TAG))
            {
                int color = entitylivingbaseIn.getEntityData().getInteger(EntitySpawnHandler.COLOR_TAG);
                int blue = color & 255;
                int green = (color >> 8) & 255;
                int red = (color >> 16) & 255;
                GlStateManager.color(red / 255f, green / 255f, blue / 255f, 1f);
            }
            this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            GlStateManager.resetColor();

            if (flag1)
            {
                GlStateManager.disableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
            }
        }
    }


    @Override
    protected ResourceLocation getEntityTexture(EntityZombie entity)
    {
        return ZOMBIE_TEXTURES;
    }
}
