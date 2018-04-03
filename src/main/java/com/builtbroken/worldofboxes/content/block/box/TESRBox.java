package com.builtbroken.worldofboxes.content.block.box;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;

import java.awt.*;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 4/3/2018.
 */
public class TESRBox extends TileEntitySpecialRenderer<TileEntityDimBox>
{
    private static final ResourceLocation END_SKY_TEXTURE = new ResourceLocation("textures/environment/end_sky.png");
    private static final ResourceLocation END_PORTAL_TEXTURE = new ResourceLocation("textures/entity/end_portal.png");

    private static final Random RANDOM = new Random(31100L);
    private static final FloatBuffer MODELVIEW = GLAllocation.createDirectFloatBuffer(16);
    private static final FloatBuffer PROJECTION = GLAllocation.createDirectFloatBuffer(16);
    private final FloatBuffer buffer = GLAllocation.createDirectFloatBuffer(16);

    public static List<Color> randomColorsForBeams = new ArrayList();

    public Color colorIn = new Color(16777215);

    @Override
    public void render(TileEntityDimBox te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        if (te.isSetup())
        {
            te.animationStep = 0;
            renderPortal(te, x, y, z, partialTicks, destroyStage, alpha);
        }
        else if (te.isDoingSetupAnimation())
        {
            float step = -partialTicks * 0.01f;

            //Down
            if (te.animationTimer > (TileEntityDimBox.ANIMATION_TIMER / 2))
            {
                step = -step * 0.5f;
            }
            te.animationStep += step;
            te.animationStep = Math.max(te.animationStep, 0);
            te.animationStep = Math.min(te.animationStep, 1f);

            //Renders
            renderBeams(te, x + 0.5, y + te.animationStep + 0.5, z + 0.5, 0.02f, te.animationStep);
            renderSphere(te, x + 0.5, y + te.animationStep + 0.5, z + 0.5, 0.1f);
        }
        else
        {
            te.animationStep = 0;
        }
    }

    float lerp(float point1, float point2, float alpha)
    {
        return point1 + alpha * (point2 - point1);
    }

    protected void renderPortal(TileEntityDimBox te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        GlStateManager.disableLighting();
        RANDOM.setSeed(31100L);
        GlStateManager.getFloat(2982, MODELVIEW);
        GlStateManager.getFloat(2983, PROJECTION);
        double d0 = x * x + y * y + z * z;
        int i = this.getPasses(d0);
        float f = this.getOffset();
        boolean flag = false;

        for (int j = 0; j < i; ++j)
        {
            GlStateManager.pushMatrix();
            float f1 = 2.0F / (float) (18 - j);

            if (j == 0)
            {
                this.bindTexture(END_SKY_TEXTURE);
                f1 = 0.15F;
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            }

            if (j >= 1)
            {
                this.bindTexture(END_PORTAL_TEXTURE);
                flag = true;
                Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            }

            if (j == 1)
            {
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            }

            GlStateManager.texGen(GlStateManager.TexGen.S, 9216);
            GlStateManager.texGen(GlStateManager.TexGen.T, 9216);
            GlStateManager.texGen(GlStateManager.TexGen.R, 9216);
            GlStateManager.texGen(GlStateManager.TexGen.S, 9474, this.getBuffer(1.0F, 0.0F, 0.0F, 0.0F));
            GlStateManager.texGen(GlStateManager.TexGen.T, 9474, this.getBuffer(0.0F, 1.0F, 0.0F, 0.0F));
            GlStateManager.texGen(GlStateManager.TexGen.R, 9474, this.getBuffer(0.0F, 0.0F, 1.0F, 0.0F));
            GlStateManager.enableTexGenCoord(GlStateManager.TexGen.S);
            GlStateManager.enableTexGenCoord(GlStateManager.TexGen.T);
            GlStateManager.enableTexGenCoord(GlStateManager.TexGen.R);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();
            GlStateManager.translate(0.5F, 0.5F, 0.0F);
            GlStateManager.scale(0.5F, 0.5F, 1.0F);
            float f2 = (float) (j + 1);
            GlStateManager.translate(17.0F / f2, (2.0F + f2 / 1.5F) * ((float) Minecraft.getSystemTime() % 800000.0F / 800000.0F), 0.0F);
            GlStateManager.rotate((f2 * f2 * 4321.0F + f2 * 9.0F) * 2.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.scale(4.5F - f2 / 4.0F, 4.5F - f2 / 4.0F, 1.0F);
            GlStateManager.multMatrix(PROJECTION);
            GlStateManager.multMatrix(MODELVIEW);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
            float f3 = (RANDOM.nextFloat() * 0.5F + 0.1F) * f1;
            float f4 = (RANDOM.nextFloat() * 0.5F + 0.4F) * f1;
            float f5 = (RANDOM.nextFloat() * 0.5F + 0.5F) * f1;


            bufferbuilder.pos(x, y + (double) f, z + 1.0D).color(f3, f4, f5, 1.0F).endVertex();
            bufferbuilder.pos(x + 1.0D, y + (double) f, z + 1.0D).color(f3, f4, f5, 1.0F).endVertex();
            bufferbuilder.pos(x + 1.0D, y + (double) f, z).color(f3, f4, f5, 1.0F).endVertex();
            bufferbuilder.pos(x, y + (double) f, z).color(f3, f4, f5, 1.0F).endVertex();

            tessellator.draw();
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
            this.bindTexture(END_SKY_TEXTURE);
        }

        GlStateManager.disableBlend();
        GlStateManager.disableTexGenCoord(GlStateManager.TexGen.S);
        GlStateManager.disableTexGenCoord(GlStateManager.TexGen.T);
        GlStateManager.disableTexGenCoord(GlStateManager.TexGen.R);
        GlStateManager.enableLighting();

        if (flag)
        {
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
        }
    }

    public void renderBeams(TileEntityDimBox te, double x, double y, double z, float sizeScale, float timeScale)
    {
        //Get buffer
        final BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
        RenderHelper.disableStandardItemLighting();

        //Start
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x, (float) y, (float) z);

        //Setup
        GlStateManager.disableTexture2D();
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GlStateManager.disableAlpha();
        GlStateManager.enableCull();
        GlStateManager.disableDepth();

        Random redmatterBeamRandom = new Random(432L);

        //0 - 61 for scale of 1
        final int beamCount = (int) ((timeScale + timeScale * timeScale) / 2.0F * 60.0F);
        for (int beamIndex = 0; beamIndex < beamCount; ++beamIndex)
        {
            //Start
            GlStateManager.pushMatrix();

            //Calculate size
            float beamLength = (redmatterBeamRandom.nextFloat() * 20.0F + 5.0F) * sizeScale;
            float beamWidth = (redmatterBeamRandom.nextFloat() * 2.0F + 1.0F) * sizeScale;

            //Random rotations TODO see if we need to rotate so much
            GlStateManager.rotate(redmatterBeamRandom.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(redmatterBeamRandom.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(redmatterBeamRandom.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(redmatterBeamRandom.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(redmatterBeamRandom.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(redmatterBeamRandom.nextFloat() * 360.0F + timeScale * 90.0F, 0.0F, 0.0F, 1.0F);

            //Get color based on state
            Color colorOut;
            Color colorIn = this.colorIn;

            if (beamIndex < randomColorsForBeams.size())
            {
                colorOut = randomColorsForBeams.get(beamIndex);
            }
            else
            {
                colorOut = new Color(redmatterBeamRandom.nextFloat(), redmatterBeamRandom.nextFloat(), redmatterBeamRandom.nextFloat());
                randomColorsForBeams.add(colorOut);
            }

            //Draw spike shape
            bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);

            //center vertex
            bufferbuilder.pos(0.0D, 0.0D, 0.0D)
                    .color(colorIn.getRed(), colorIn.getGreen(), colorIn.getBlue(), colorIn.getAlpha())
                    .endVertex();

            //Outside vertex
            bufferbuilder.pos(-0.866D * beamWidth, beamLength, -0.5F * beamWidth)
                    .color(colorOut.getRed(), colorOut.getGreen(), colorOut.getBlue(), colorOut.getAlpha())
                    .endVertex();
            bufferbuilder.pos(0.866D * beamWidth, beamLength, -0.5F * beamWidth)
                    .color(colorOut.getRed(), colorOut.getGreen(), colorOut.getBlue(), colorOut.getAlpha())
                    .endVertex();
            bufferbuilder.pos(0.0D, beamLength, 1.0F * beamWidth)
                    .color(colorOut.getRed(), colorOut.getGreen(), colorOut.getBlue(), colorOut.getAlpha())
                    .endVertex();
            bufferbuilder.pos(-0.866D * beamWidth, beamLength, -0.5F * beamWidth)
                    .color(colorOut.getRed(), colorOut.getGreen(), colorOut.getBlue(), colorOut.getAlpha())
                    .endVertex();

            //draw
            Tessellator.getInstance().draw();

            //end
            GlStateManager.popMatrix();
        }

        //Cleanup
        GlStateManager.enableDepth();
        GlStateManager.disableCull();
        GlStateManager.disableBlend();
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.enableTexture2D();
        GlStateManager.enableAlpha();
        RenderHelper.enableStandardItemLighting();

        //End
        GlStateManager.popMatrix();
    }

    public void renderSphere(TileEntityDimBox te, double x, double y, double z, float radius)
    {

        //--------------------------------------------------
        //First Sphere, inside
        //Setup
        GlStateManager.pushMatrix();

        //Translate
        GlStateManager.translate((float) x, (float) y, (float) z);

        //Assign texture
        bindTexture(END_SKY_TEXTURE);

        //Assign color
        GlStateManager.color(0.0F, 0.0F, 0.0F, 1);

        //Render outer sphere
        new Sphere().draw(radius * 0.8f, 32, 32);

        //Reset
        GlStateManager.popMatrix();


        //--------------------------------------------------
        //Second Sphere, outside

        float ticks = te.animationTimer % 40;

        //Setup
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();

        //Translate
        GlStateManager.translate((float) x, (float) y, (float) z);

        //Assign texture
        bindTexture(END_PORTAL_TEXTURE);

        //Assign color
        GlStateManager.color(0.0F, 0.0F, 0.2F, 0.8f);

        //Render outer sphere
        final float scaleSize = 0.0005f;
        final float fullSize = radius * scaleSize * 20;
        float scaleDelta;
        if (ticks > 20)
        {
            scaleDelta = fullSize - (radius * scaleSize * (ticks - 20));
        }
        else
        {
            scaleDelta = radius * scaleSize * ticks;
        }
        new Sphere().draw(radius + scaleDelta, 32, 32);

        //Reset
        GlStateManager.enableDepth();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        RenderHelper.enableStandardItemLighting();
    }

    protected int getPasses(double p_191286_1_)
    {
        int i;

        if (p_191286_1_ > 36864.0D)
        {
            i = 1;
        }
        else if (p_191286_1_ > 25600.0D)
        {
            i = 3;
        }
        else if (p_191286_1_ > 16384.0D)
        {
            i = 5;
        }
        else if (p_191286_1_ > 9216.0D)
        {
            i = 7;
        }
        else if (p_191286_1_ > 4096.0D)
        {
            i = 9;
        }
        else if (p_191286_1_ > 1024.0D)
        {
            i = 11;
        }
        else if (p_191286_1_ > 576.0D)
        {
            i = 13;
        }
        else if (p_191286_1_ > 256.0D)
        {
            i = 14;
        }
        else
        {
            i = 15;
        }

        return i;
    }

    protected float getOffset()
    {
        return 0.75F;
    }

    private FloatBuffer getBuffer(float p_147525_1_, float p_147525_2_, float p_147525_3_, float p_147525_4_)
    {
        this.buffer.clear();
        this.buffer.put(p_147525_1_).put(p_147525_2_).put(p_147525_3_).put(p_147525_4_);
        this.buffer.flip();
        return this.buffer;
    }
}
