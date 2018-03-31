package com.builtbroken.worldofboxes.content.block.box;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

import javax.annotation.Nonnull;

/**
 * Simple override for teleporter to handle sending the player to the correct spot
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 3/31/2018.
 */
public class TeleporterSetPos extends Teleporter
{
    public final BlockPos pos;

    public TeleporterSetPos(WorldServer world, BlockPos pos)
    {
        super(world);
        this.pos = pos;
    }

    @Override
    public void placeInPortal(@Nonnull Entity entity, float yaw)
    {
        //Trick the chunk into loading
        this.world.getBlockState(pos);

        //Set entity position
        entity.setPosition(pos.getX() + 0.5, pos.getY() + 0.01, pos.getZ() + 0.5);

        //Cancel motion TODO maybe don't?
        entity.motionX = 0.0f;
        entity.motionY = 0.0f;
        entity.motionZ = 0.0f;
    }
}