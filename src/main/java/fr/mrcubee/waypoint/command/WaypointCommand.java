package fr.mrcubee.waypoint.command;

import fr.mrcubee.langlib.Lang;
import fr.mrcubee.waypoint.WayPointStorage;
import fr.mrcubee.waypoint.tools.LocationTools;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class WaypointCommand implements CommandExecutor, TabExecutor {

    private boolean createWayPoint(final Player player, final String[] args) {
        final Location location;

        if (args.length < 1)
            return false;
        if (args.length > 3)
            location = LocationTools.getLocationFromArguments(player, args[1], args[2], args[3]);
        else if (args.length > 2)
            location = LocationTools.getLocationFromArguments(player, args[1], args[2]);
        else
            location = player.getLocation();
        if (location == null)
            return false;
        if (WayPointStorage.addPlayerWaypoint(player, args[0], location))
            player.sendMessage(Lang.getMessage(player, "waypoint.command.created", "&cLANG ERROR: waypoint.command.created", true, args[0]));
        else
            player.sendMessage(Lang.getMessage(player, "waypoint.command.already_exist", "&cLANG ERROR: waypoint.command.already_exist", true));
        return true;
    }

    private boolean removeWayPoint(final Player player, final String[] args) {
        if (args.length < 1)
            return false;
        if (WayPointStorage.removePlayerWaypoint(player, args[0]) != null)
            player.sendMessage(Lang.getMessage(player, "waypoint.command.removed", "&cLANG ERROR: waypoint.command.removed", true, args[0]));
        else
            player.sendMessage(Lang.getMessage(player, "waypoint.command.not_exist", "&cLANG ERROR: waypoint.command.not_exist", true));
        return true;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player player;
        final String[] subCommandArgs;

        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.getMessage("command.must_be_player", "&cLANG ERROR: command.must_be_player", true));
            return true;
        }
        player = (Player) sender;
        if (args.length < 2)
            return false;
        subCommandArgs = Arrays.copyOfRange(args, 1, args.length);
        switch (args[0].toLowerCase()) {
            case "create":
                return createWayPoint(player, subCommandArgs);
            case "remove":
                return removeWayPoint(player, subCommandArgs);
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Set<String> wayPointNames;
        if (!(sender instanceof Player))
            return null;
        if (args.length == 1)
            return Arrays.asList("create", "remove");
        if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
            wayPointNames = WayPointStorage.getPlayerWaypointsName((Player) sender);
            if (wayPointNames == null)
                return new ArrayList<String>();
            return new ArrayList<String>(wayPointNames);
        }
        return new ArrayList<String>();
    }
}
