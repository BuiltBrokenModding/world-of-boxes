package com.builtbroken.worldofboxes.content.block.box;

import com.builtbroken.worldofboxes.config.ConfigDim;
import com.builtbroken.worldofboxes.network.PacketHandler;
import com.builtbroken.worldofboxes.network.PacketSyncBox;
import com.builtbroken.worldofboxes.reg.WBBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 3/30/2018.
 */
public class TileEntityDimBox extends TileEntity implements ITickable
{
    public final static String NBT_TARGET_DIM = "targetDim";
    public final static String NBT_TARGET = "target";

    public final static int ANIMATION_TIMER = 300;

    private static Method copyFromOldField; //TODO replace with AT

    public BlockPos teleportTarget;
    public int teleportDim;
    public boolean isSetup = false;

    public boolean doingSetupAnimation = false;
    public int animationTimer = 0;
    public float animationStep = 0;


    private byte packetTimer = 0;
    private byte updateCheck = 0;
    private BlockDimBox.State prevRenderState;

    public boolean isSetup()
    {
        return isSetup;
    }

    @Override
    public void update()
    {
        if (doingSetupAnimation && animationTimer-- <= 0)
        {
            doingSetupAnimation = false;
            if (!world.isRemote)
            {
                randomizeDim();
            }
        }

        if (!world.isRemote)
        {
            if (packetTimer++ >= 0)
            {
                packetTimer = 0;
                PacketHandler.sendToAllAround(new PacketSyncBox(this), this);
            }
        }
        else if (updateCheck++ >= 5)
        {
            IBlockState state = world.getBlockState(getPos());
            if (state.getProperties().containsKey(BlockDimBox.STATE_PROPERTY_ENUM))
            {
                BlockDimBox.State renderState = state.getValue(BlockDimBox.STATE_PROPERTY_ENUM);
                if (renderState != prevRenderState)
                {
                    prevRenderState = renderState;
                    world.markBlockRangeForRenderUpdate(getPos(), getPos());
                }
            }
        }
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

    public void startSetupAnimation()
    {
        doingSetupAnimation = true;
        animationTimer = ANIMATION_TIMER;
    }

    public void randomizeDim()
    {
        //Get random point to teleport user
        final int x = world.rand.nextInt(10000) - world.rand.nextInt(10000);
        final int z = world.rand.nextInt(10000) - world.rand.nextInt(10000);

        //Init target point for ray trace
        int y = 100;
        teleportTarget = new BlockPos(x, y, z);

        //Get world
        final int targetDim = world.provider.getDimension() == ConfigDim.dimID ? 0 : ConfigDim.dimID;
        final World targetWorld = DimensionManager.getWorld(targetDim);

        //Load chunk
        targetWorld.getBlockState(teleportTarget);

        //Find spot to start looking for placments
        RayTraceResult result = targetWorld.rayTraceBlocks(new Vec3d(x, 255, z), new Vec3d(x, 0, z), false, true, true);
        if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK)
        {
            //Find spot to place box
            y = result.getBlockPos().getY() + 1;
            BlockPos pos = new BlockPos(x, y, z);
            while (!targetWorld.getBlockState(pos).getBlock().isReplaceable(targetWorld, pos))
            {
                y++;
                pos = new BlockPos(x, y, z);
            }

            //Block box and set data, so player can return
            targetWorld.setBlockState(pos, WBBlocks.BOX.getDefaultState());
            TileEntity tile = targetWorld.getTileEntity(pos);
            if (tile instanceof TileEntityDimBox)
            {
                ((TileEntityDimBox) tile).setTarget(world.provider.getDimension(), getPos().up());
            }

            teleportTarget = pos.up();

            //TODO start dim animation
            //TODO randomize world grid
            setTarget(targetDim, teleportTarget);
        }
    }

    public void setTarget(int dim, BlockPos teleportTarget)
    {
        this.teleportDim = dim;
        this.teleportTarget = teleportTarget;
        this.isSetup = true;
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
        if (isSetup())
        {
            //TODO create short distance teleporter if same dim
            //TODO spawn above box
            //TODO push player in an ark off the box

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

    public boolean isDoingSetupAnimation()
    {
        return doingSetupAnimation;
    }
}
