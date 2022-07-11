package fr.mrcubee.waypoint.command;

import fr.mrcubee.langlib.Lang;
import fr.mrcubee.waypoint.GPS;
import fr.mrcubee.waypoint.WayPoint;
import fr.mrcubee.waypoint.WayPointStorage;
import fr.mrcubee.waypoint.event.Events;
import fr.mrcubee.waypoint.event.PlayerStartGPSEvent;
import fr.mrcubee.waypoint.tools.LocationTools;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author MrCubee
 * @since 1.0
 * @version 1.0
 */
public class GpsCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player player;
        final Location location;
        final WayPoint wayPoint;
        final Player targetPlayer;

        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.getMessage("command.must_be_player", "&cLANG ERROR: command.must_be_player", true));
            return true;
        }
        player = (Player) sender;
        if (args.length < 1)
            return false;
        if (args[0].equalsIgnoreCase("stop")) {
            GPS.removeLocation(player);
            player.sendMessage(Lang.getMessage(player, "gps.command.stop", "&cLANG ERROR: gps.command.stop", true));
            return true;
        }
        if (args.length < 2)
            return false;
        switch (args[0].toLowerCase()) {
            case "waypoint":
                wayPoint = WayPointStorage.getPlayerWayPoint(player, args[1]);
                if (wayPoint == null) {
                    player.sendMessage(Lang.getMessage(player, "gps.command.waypoint.not_exist", "&cLANG ERROR: gps.command.waypoint.not_exist", true));
                    return true;
                }
                if (!Events.call(new PlayerStartGPSEvent(player, wayPoint)))
                    return true;
                 GPS.setLocation(player, wayPoint);
                return true;
            case "player":
                targetPlayer = Bukkit.getPlayerExact(args[1]);
                if (targetPlayer == null) {
                    player.sendMessage(Lang.getMessage(player, "gps.command.player.not_exist", "&cLANG ERROR: gps.command.player.not_exist", true));
                    return true;
                }
                if (!Events.call(new PlayerStartGPSEvent(player, targetPlayer)))
                    return true;
                GPS.setLocation(player, targetPlayer);
                return true;
            default:
                location = LocationTools.getLocationFromArguments(player, args);
                if (location == null)
                    return false;
                if (!Events.call(new PlayerStartGPSEvent(player, location)))
                    return true;
                GPS.setLocation(player, location);
                player.sendMessage(Lang.getMessage(player, "gps.command.start", "&cLANG ERROR: gps.command.start", true));
                return true;
        }
    }

    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Set<String> wayPointsName;

        if (!(sender instanceof Player))
            return null;
        if (args.length == 1)
            return Arrays.asList("stop", "waypoint", "player");
        if (args.length == 2 && args[0].equalsIgnoreCase("waypoint")) {
            wayPointsName = WayPointStorage.getPlayerWaypointsName((Player) sender);
            if (wayPointsName == null)
                return new ArrayList<String>();
            return new ArrayList<String>(wayPointsName);
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("player"))
            return null;
        return new ArrayList<String>();
    }
}
