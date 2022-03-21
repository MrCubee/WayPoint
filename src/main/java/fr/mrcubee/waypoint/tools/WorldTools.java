package fr.mrcubee.waypoint.tools;

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.List;

/**
 * @author MrCubee
 * @since 1.0
 * @version 1.0
 */
public class WorldTools {

    public static World getWorld(String worldName) {
        final List<World> worlds;
        int index = -1;

        if (worldName == null)
            return null;
        worlds = Bukkit.getWorlds();
        try {
            index = Integer.parseInt(worldName);
            if (index > 0 && index < worlds.size())
                return worlds.get(index);
            return null;
        } catch (NumberFormatException ignored) {};
        return Bukkit.getWorld(worldName);
    }

}
