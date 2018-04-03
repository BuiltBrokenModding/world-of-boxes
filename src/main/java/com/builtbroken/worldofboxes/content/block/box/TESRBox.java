package com.builtbroken.worldofboxes.content.block.box;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 4/3/2018.
 */
public class TESRBox extends TileEntitySpecialRenderer<TileEntityDimBox>
{
    @Override
    public void render(TileEntityDimBox te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();



        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }
}
