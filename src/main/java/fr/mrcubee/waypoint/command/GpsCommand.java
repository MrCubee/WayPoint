package fr.mrcubee.waypoint.command;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import fr.mrcubee.langlib.Lang;
import fr.mrcubee.waypoint.GPS;
import fr.mrcubee.waypoint.WayPoint;
import fr.mrcubee.waypoint.WayPointStorage;
import fr.mrcubee.waypoint.event.Events;
import fr.mrcubee.waypoint.event.PlayerStartGPSEvent;
import fr.mrcubee.waypoint.event.PlayerStopGPSEvent;
import fr.mrcubee.waypoint.tools.LocationTools;

/**
 * @author MrCubee
 * @since 1.0
 * @version 1.0
 */
public class GpsCommand implements CommandExecutor, TabCompleter {

	private static final Collection<String> SUB_COMMANDS = Arrays.asList("stop", "waypoint", "player");

	private boolean callPlayerGPSStopEvent(final Player player, final GPS.TargetType targetType) {
		final PlayerStopGPSEvent playerStopGPSEvent;

		switch (targetType) {
			case PLAYER:
				playerStopGPSEvent = new PlayerStopGPSEvent(player, GPS.getTargetAsPlayer(player));
				break;
			case WAYPOINT:
				playerStopGPSEvent = new PlayerStopGPSEvent(player, GPS.getTargetAsWaypoint(player));
				break;
			default:
				playerStopGPSEvent = new PlayerStopGPSEvent(player, GPS.getTargetLocation(player));
				break;
		}
		return Events.call(playerStopGPSEvent);
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		final Player player;
		final Location location;
		final WayPoint wayPoint;
		final Player targetPlayer;
		final GPS.TargetType targetType;

		if (!(sender instanceof Player)) {
			sender.sendMessage(Lang.getMessage("command.must_be_player", "&cLANG ERROR: command.must_be_player", true));
			return true;
		}
		player = (Player) sender;
		if (args.length < 1)
			return false;
		if (args[0].equalsIgnoreCase("stop")) {
			targetType = GPS.getTargetType(player);
			if (targetType == null || !callPlayerGPSStopEvent(player, targetType))
				return true;
			GPS.removeTarget(player);
			player.sendMessage(Lang.getMessage(player, "gps.command.stop", "&cLANG ERROR: gps.command.stop", true));
			return true;
		}
		if (args.length < 2)
			return false;
		switch (args[0].toLowerCase()) {
			case "waypoint":
				if (!player.hasPermission("waypoint.gps.use.waypoint")) {
					player.sendMessage(Lang.getMessage(player, "gps.command.waypoint.permission", "&cLANG ERROR: gps.command.waypoint.permission", true));
					return true;
				}
				wayPoint = WayPointStorage.getPlayerWayPoint(player, args[1]);
				if (wayPoint == null) {
					player.sendMessage(Lang.getMessage(player, "gps.command.waypoint.not_exist", "&cLANG ERROR: gps.command.waypoint.not_exist", true));
					return true;
				}
				if (!Events.call(new PlayerStartGPSEvent(player, wayPoint)))
					return true;
				GPS.setLocationTarget(player, wayPoint);
				return true;
			case "player":
				if (!player.hasPermission("waypoint.gps.use.player")) {
					player.sendMessage(Lang.getMessage(player, "gps.command.player.permission", "&cLANG ERROR: gps.command.player.permission", true));
					return true;
				}
				targetPlayer = Bukkit.getPlayerExact(args[1]);
				if (targetPlayer == null) {
					player.sendMessage(Lang.getMessage(player, "gps.command.player.not_exist", "&cLANG ERROR: gps.command.player.not_exist", true));
					return true;
				}
				if (!Events.call(new PlayerStartGPSEvent(player, targetPlayer)))
					return true;
				GPS.setTarget(player, targetPlayer);
				return true;
			default:
				location = LocationTools.getLocationFromArguments(player, args);
				if (location == null)
					return false;
				if (!Events.call(new PlayerStartGPSEvent(player, location)))
					return true;
				GPS.setLocationTarget(player, location);
				player.sendMessage(Lang.getMessage(player, "gps.command.start", "&cLANG ERROR: gps.command.start", true));
				return true;
		}
	}

	@Override
	public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
		final ArrayList<String> result;
		final String subCommand;
		final String current;

		if (!(sender instanceof Player))
			return null;
		current = args[args.length - 1].toLowerCase();
		if (args.length < 2) {
			result = new ArrayList<String>(SUB_COMMANDS);
		} else if (args.length == 2){
			subCommand = args[0].toLowerCase();
			switch (subCommand) {
				case "waypoint":
					result = new ArrayList<String>(WayPointStorage.getPlayerWaypointsName((Player) sender));
					break;
				case "player":
					result = null;
					break;
				default:
					result = new ArrayList<String>();
					break;
			}
		} else
			result = new ArrayList<String>();
		if (result != null)
			result.removeIf(element -> !element.toLowerCase().startsWith(current));
		return result;
	}

}
