package fr.mrcubee.waypoint.command;

import fr.mrcubee.waypoint.GPS;
import fr.mrcubee.waypoint.listeners.AsyncPlayerChatListener;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GpsCommand implements CommandExecutor {

    private Integer[] convertToInteger(String... args) {
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

    private Location getLocation(final Player player, final String[] args) {
        final Integer[] integers;
        World world;

        if (args.length < 2)
            return null;
        integers = convertToInteger(args);
        if (integers[0] == null || integers[1] == null)
            return null;
        if (args.length < 3) {
            world = player.getWorld();
            return new Location(world, integers[0], world.getHighestBlockYAt(integers[0], integers[1]), integers[1]);
        } else if (args.length < 4) {
            if (integers[2] != null) {
                world = player.getWorld();
                return new Location(world, integers[0], integers[1], integers[2]);
            }
            world = AsyncPlayerChatListener.getWorld(args[2]);
            if (world == null)
                return null;
            return new Location(world, integers[0], world.getHighestBlockYAt(integers[0], integers[1]), integers[1]);
        }
        world = AsyncPlayerChatListener.getWorld(args[3]);
        if (world == null)
            return null;
        return new Location(world, integers[0], integers[1], integers[2]);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        final Player player;
        final Location location;

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to run this command.");
            return true;
        }
        player = (Player) sender;
        if (args.length == 0) {
            GPS.removeLocation(player);
            player.sendMessage(ChatColor.GRAY + "The GPS is stopped.");
            return true;
        }
        location = getLocation(player, args);
        if (location == null)
            return false;
        GPS.setLocation(player, location);
        player.sendMessage(ChatColor.GRAY + "GPS is on.");
        return true;
    }

}
