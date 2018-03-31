package com.builtbroken.worldofboxes.content.block.board;

import com.builtbroken.worldofboxes.WorldOfBoxes;
import net.minecraft.block.BlockEmptyDrops;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

/**
 * Used to act as bedrock in seperating out pocket dimensions
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 3/31/2018.
 */
public class BlockGridEdge extends BlockEmptyDrops
{
    public BlockGridEdge()
    {
        super(Material.ROCK);
        setRegistryName(WorldOfBoxes.DOMAIN, "boxwall");
        setBlockUnbreakable();
        setResistance(6000000.0F);
        setSoundType(SoundType.STONE);
        setUnlocalizedName("box.wall");
        disableStats();
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    //TODO add block states to change texture based on rotation? Maybe randomize a little?
}
