package fr.mrcubee.waypoint.tools;

import org.bukkit.ChatColor;
import org.bukkit.HeightMap;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;

/**
 * @author MrCubee
 * @since 1.0
 * @version 1.0
 */
public class LocationTools {

    private static Integer[] convertToInteger(String... args) {
        final Integer[] results = new Integer[args.length];
        final int max = Math.min(3, args.length);

        for (int i = 0; i < max; i++) {
            try {
                results[i] = Integer.parseInt(args[i]);
            } catch (NumberFormatException ignored) {
                results[i] = null;
            }
        }
        return results;
    }

    public static Location getLocationFromArguments(final Player player, final String... args) {
        final Integer[] integers;
        World world;

        if (player == null || args == null || args.length < 2)
            return null;
        integers = convertToInteger(args);
        if (integers[0] == null || integers[1] == null)
            return null;
        if (args.length < 3) {
            world = player.getWorld();
            return new Location(world, integers[0], world.getHighestBlockYAt(integers[0], integers[1]) + 1, integers[1]);
        } else if (args.length < 4) {
            if (integers[2] != null) {
                world = player.getWorld();
                return new Location(world, integers[0], integers[1], integers[2]);
            }
            world = WorldTools.getWorld(args[2]);
            if (world == null)
                return null;
            return new Location(world, integers[0], world.getHighestBlockYAt(integers[0], integers[1]) + 1, integers[1]);
        }
        world = WorldTools.getWorld(args[3]);
        if (world == null)
            return null;
        return new Location(world, integers[0], integers[1], integers[2]);
    }

    public static String locationToCommand(final Location location) {
        final World world;
        final String command;

        if (location == null)
            return null;
        world = location.getWorld();
        command = String.format("/gps %d %d %d", location.getBlockX(), location.getBlockY(), location.getBlockZ());

        if (world == null)
            return command;
        return command + " " + world.getName();
    }

    public static String locationToHoverText(final Location location) {
        final World world;

        if (location == null)
            return null;
        world = location.getWorld();
        return ChatColor.GOLD + "x: " + ChatColor.AQUA + location.getBlockX()
                + ChatColor.GOLD + "\ny: " + ChatColor.AQUA + location.getBlockY()
                + ChatColor.GOLD + "\nz: " + ChatColor.AQUA + location.getBlockZ()
                + ChatColor.GOLD + "\nworld: " + (world == null ? ChatColor.RED + "unknown" : ChatColor.AQUA + world.getName());
    }

    public static Location extractLocationFromMatcher(final Player player, final Matcher matcher) {
        final int coordX;
        final String strCoordY;
        final int coordY;
        final int coordZ;
        final String strWorld;
        final World world;

        if (player == null || matcher == null)
            return null;
        coordX = Integer.parseInt(matcher.group(1));
        strCoordY = matcher.group(2);
        coordZ = Integer.parseInt(matcher.group(3));
        strWorld = matcher.group(4);
        world = strWorld == null ? player.getWorld() : WorldTools.getWorld(strWorld);
        if (strCoordY == null)
            coordY = world != null ?  world.getHighestBlockYAt(coordX, coordZ, HeightMap.WORLD_SURFACE) + 1 : 0;
        else
            coordY = Integer.parseInt(strCoordY);
        return new Location(world, coordX, coordY, coordZ);
    }

}
