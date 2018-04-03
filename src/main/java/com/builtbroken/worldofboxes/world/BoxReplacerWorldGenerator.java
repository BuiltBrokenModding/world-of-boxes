package com.builtbroken.worldofboxes.world;

import com.builtbroken.worldofboxes.WorldOfBoxes;
import com.builtbroken.worldofboxes.reg.WBBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 3/31/2018.
 */
public class BoxReplacerWorldGenerator implements IWorldGenerator
{
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        if (world.provider.getDimensionType() == WorldOfBoxes.dimensionType)
        {
            Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
            for (int x = -16; x < 32; x++)
            {
                for (int z = -16; z < 32; z++)
                {
                    for (int y = 0; y < 255; y++)
                    {
                        BlockPos pos = new BlockPos((chunkX * 16) + x, y, (chunkZ * 16) + z);
                        if (world.isBlockLoaded(pos, false))
                        {
                            IBlockState state = chunk.getBlockState(pos);
                            Block block = state.getBlock();
                            if (block == Blocks.GRASS)
                            {
                                chunk.setBlockState(pos, WBBlocks.GRASS.getDefaultState());
                            }
                            else if (block == Blocks.TALLGRASS)
                            {
                                chunk.setBlockState(pos, WBBlocks.TALL_GRASS.getStateFromMeta(block.getMetaFromState(state)));
                            }
                            else if (block == Blocks.LOG || block == Blocks.LOG2)
                            {
                                chunk.setBlockState(pos, (block == Blocks.LOG ? WBBlocks.LOG : WBBlocks.LOG2).getStateFromMeta(block.getMetaFromState(state))); //lazy way, meta should be 1:1 anyways
                            }
                            else if (block == Blocks.LEAVES || block == Blocks.LEAVES2)
                            {
                                chunk.setBlockState(pos, (block == Blocks.LEAVES ? WBBlocks.LEAF : WBBlocks.LEAF2).getStateFromMeta(block.getMetaFromState(state)));
                            }
                        }
                    }
                }
            }
        }
    }
}
