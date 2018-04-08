package com.builtbroken.worldofboxes.config;

import com.builtbroken.worldofboxes.WorldOfBoxes;
import net.minecraftforge.common.config.Config;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 3/30/2018.
 */
@Config(modid = WorldOfBoxes.DOMAIN, name = "worldofboxes/dimension")
@Config.LangKey("config.worldofboxes:dimension.title")
public class ConfigDim
{
    @Config.Name("dimension_id")
    @Config.Comment("Index number used to register and access the cardboard box world. Use this to fix overlap issues with other mods.")
    @Config.RangeInt(min = 2)
    public static int dimID = 0;
}
