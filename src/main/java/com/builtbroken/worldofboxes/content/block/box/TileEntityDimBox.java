package com.builtbroken.worldofboxes.content.block.box;

import com.builtbroken.worldofboxes.config.ConfigDim;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 3/30/2018.
 */
public class TileEntityDimBox extends TileEntity
{
    public final static String NBT_TARGET_DIM = "targetDim";
    public final static String NBT_TARGET = "target";

    private static Method copyFromOldField; //TODO replace with AT

    BlockPos teleportTarget;
    int teleportDim;
    boolean isSetup = false;

    public boolean isSetup()
    {
        return isSetup;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        if (compound.hasKey(NBT_TARGET_DIM))
        {
            isSetup = true;
            teleportDim = compound.getInteger(NBT_TARGET_DIM);
            if (compound.hasKey(NBT_TARGET))
            {
                int[] array = compound.getIntArray(NBT_TARGET);
                teleportTarget = new BlockPos(array[0], array[1], array[2]);
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        if (isSetup)
        {
            compound.setInteger(NBT_TARGET_DIM, teleportDim);
            if (teleportTarget != null)
            {
                compound.setIntArray(NBT_TARGET, new int[]{teleportTarget.getX(), teleportTarget.getY(), teleportTarget.getZ()});
            }
        }
        return super.writeToNBT(compound);
    }

    public void randomizeDim()
    {
        teleportTarget = new BlockPos(0, 255, 0);
        teleportDim = ConfigDim.dimID;
        isSetup = true;
        //TODO start dim animation
        //TODO create world
    }

    public BlockPos getTeleportTarget()
    {
        return teleportTarget;
    }

    /**
     * Sends the player to the target dim
     *
     * @param player
     */
    public void teleport(EntityPlayerMP player)
    {
        //TODO create short distance teleporter if same dim

        //Get old dim
        int oldDimension = player.getEntityWorld().provider.getDimension();

        //Get server
        MinecraftServer server = player.getEntityWorld().getMinecraftServer();

        //Get target dim world
        WorldServer worldServer = server.getWorld(teleportDim);

        //Verify target dim exists
        if (worldServer != null && worldServer.getMinecraftServer() != null)
        {
            //Reset XP to prevent issues
            player.addExperienceLevel(0);

            //Transfer player
            worldServer.getMinecraftServer().getPlayerList().transferPlayerToDimension(player, teleportDim, new TeleporterSetPos(worldServer, getTeleportTarget()));

            //Update player
            player.setPositionAndUpdate(getTeleportTarget().getX(), getTeleportTarget().getY(), getTeleportTarget().getZ());

            //Edge case for the end
            if (oldDimension == 1)
            {
                player.setPositionAndUpdate(getTeleportTarget().getX(), getTeleportTarget().getY(), getTeleportTarget().getZ());
                worldServer.spawnEntity(player);
                worldServer.updateEntityWithOptionalForce(player, false);
            }
        }
        else
        {
            player.sendMessage(new TextComponentString("Error: Failed to locate dimension [" + teleportDim + "] to send you to!!!")); //TODO make red
        }
    }

    private void copyDateFromOld(Entity entity, Entity oldEntity)
    {
        if (copyFromOldField == null)
        {
            try
            {
                copyFromOldField = Entity.class.getDeclaredMethod("copyDataFromOld", Entity.class); //TODO get MC version
                copyFromOldField.setAccessible(true);
            }
            catch (NoSuchMethodException e)
            {
                e.printStackTrace();
            }
        }

        if (copyFromOldField != null)
        {
            try
            {
                copyFromOldField.invoke(entity, oldEntity);
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
            catch (InvocationTargetException e)
            {
                e.printStackTrace();
            }
        }
    }
}
