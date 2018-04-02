package com.builtbroken.worldofboxes.world;

import com.builtbroken.worldofboxes.WorldOfBoxes;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;

/**
 * Provider for the basic box world
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 3/30/2018.
 */
public class BoxWorldProvider extends WorldProvider
{
    @Override
    public DimensionType getDimensionType()
    {
        return WorldOfBoxes.dimensionType;
    }

    @Override
    public String getSaveFolder()
    {
        return "worldofboxes";
    }

    @Override
    public IChunkGenerator createChunkGenerator()
    {
        return new ChunkGeneratorBoxworld(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled(), world.getWorldInfo().getGeneratorOptions());
    }
}