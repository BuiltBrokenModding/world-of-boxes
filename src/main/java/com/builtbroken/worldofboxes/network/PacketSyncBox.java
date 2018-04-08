package com.builtbroken.worldofboxes.network;

import com.builtbroken.worldofboxes.WorldOfBoxes;
import com.builtbroken.worldofboxes.content.block.box.TileEntityDimBox;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 4/3/2018.
 */
public class PacketSyncBox implements IMessage
{
    BlockPos blockPos;
    boolean animation;
    boolean isSetup;
    int animationTimer;

    public PacketSyncBox()
    {
    }

    public PacketSyncBox(TileEntityDimBox box)
    {
        blockPos = box.getPos();
        animation = box.isDoingSetupAnimation();
        isSetup = box.isSetup();
        animationTimer = box.animationTimer;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        blockPos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        animationTimer = buf.readInt();
        animation = buf.readBoolean();
        isSetup = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(blockPos.getX());
        buf.writeInt(blockPos.getY());
        buf.writeInt(blockPos.getZ());
        buf.writeInt(animationTimer);
        buf.writeBoolean(animation);
        buf.writeBoolean(isSetup);
    }

    public static class Handler implements IMessageHandler<PacketSyncBox, IMessage>
    {
        @Override
        public IMessage onMessage(PacketSyncBox message, MessageContext ctx)
        {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketSyncBox message, MessageContext ctx)
        {
            EntityPlayer player = WorldOfBoxes.proxy.getPlayerForSide(ctx);
            World world = player.getEntityWorld();

            if (world.isBlockLoaded(message.blockPos))
            {
                TileEntity tile = world.getTileEntity(message.blockPos);
                if(tile instanceof TileEntityDimBox)
                {
                    ((TileEntityDimBox) tile).isSetup = message.isSetup;
                    ((TileEntityDimBox) tile).doingSetupAnimation = message.animation;
                    ((TileEntityDimBox) tile).animationTimer = message.animationTimer;
                }
            }
        }
    }
}
