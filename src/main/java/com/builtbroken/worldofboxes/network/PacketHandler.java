package com.builtbroken.worldofboxes.network;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler
{
    public static SimpleNetworkWrapper INSTANCE = null;

    private static int nextPacketID = 0;

    private static int nextID()
    {
        return nextPacketID++;
    }

    public static void registerMessages(String channelName)
    {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
        registerMessages();
    }

    public static void registerMessages()
    {
        INSTANCE.registerMessage(PacketSyncBox.Handler.class, PacketSyncBox.class, nextID(), Side.CLIENT);
    }

    public static void sendToAllAround(IMessage packetSyncBox, TileEntity tile)
    {
        INSTANCE.sendToAllAround(packetSyncBox, new NetworkRegistry.TargetPoint(tile.getWorld().provider.getDimension(),
                tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ(), 64));
    }
}