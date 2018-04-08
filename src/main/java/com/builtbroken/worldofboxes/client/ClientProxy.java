package com.builtbroken.worldofboxes.client;

import com.builtbroken.worldofboxes.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 3/30/2018.
 */
public class ClientProxy extends CommonProxy
{
    public EntityPlayer getPlayerClient(MessageContext ctx)
    {
        return Minecraft.getMinecraft().player;
    }
}
