package com.builtbroken.worldofboxes.client.world;

import com.builtbroken.worldofboxes.WorldOfBoxes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IRenderHandler;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 4/3/2018.
 */
public class SkyBoxRenderBox extends IRenderHandler
{
    public static ResourceLocation TEXTURE = new ResourceLocation(WorldOfBoxes.DOMAIN, "textures/blocks/dirt.png");

    float rotationYaw = 0;
    float rotationPitch = 0;


    public boolean renderMotionRotation = false;

    @Override
    public void render(float partialTicks, WorldClient world, Minecraft mc)
    {
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();

        if (renderMotionRotation)
        {
            rotationYaw += 1 * partialTicks;
            rotationYaw %= 360;

            rotationPitch += 1 * partialTicks;
            rotationPitch %= 360;

            GlStateManager.rotate(rotationYaw, 1, 0, 0);
            GlStateManager.rotate(rotationPitch, 0, 1, 0);
        }

        float size = 100;
        float renderY = 50;

        mc.getRenderManager().renderEngine.bindTexture(TEXTURE);

        GlStateManager.pushMatrix();
        GlStateManager.rotate(180, 1, 0, 0);

        for(int x = -5; x <= 5; x++)
        {
            for(int z = -5; z <= 5; z++)
            {
                bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
                bufferbuilder.pos(-size + size * 2 * x, -renderY, -size + size * 2 * z).tex(0, 0).endVertex();
                bufferbuilder.pos(-size + size * 2 * x, -renderY, +size + size * 2 * z).tex(0, 1).endVertex();
                bufferbuilder.pos(+size + size * 2 * x, -renderY, +size + size * 2 * z).tex(1, 1).endVertex();
                bufferbuilder.pos(+size + size * 2 * x, -renderY, -size + size * 2 * z).tex(1, 0).endVertex();
                Tessellator.getInstance().draw();
            }
        }

        GlStateManager.popMatrix();
    }
}
