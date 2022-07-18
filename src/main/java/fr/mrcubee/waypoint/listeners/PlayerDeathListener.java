package fr.mrcubee.waypoint.listeners;

import fr.mrcubee.langlib.Lang;
import fr.mrcubee.waypoint.GPS;
import fr.mrcubee.waypoint.WayPoint;
import fr.mrcubee.waypoint.WayPointStorage;
import fr.mrcubee.waypoint.event.Events;
import fr.mrcubee.waypoint.event.PlayerDeathWaypointEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.Date;

public class PlayerDeathListener implements Listener {

    private String formatNumber(final int value) {
        if (value < 10)
            return "0" + value;
        return Integer.toString(value);
    }

    @EventHandler
    public void event(final PlayerRespawnEvent event) {
        final Player player = event.getPlayer();
        final Location playerLocation;
        final Date date;
        final String waypointName;
        final WayPoint wayPoint;
        final PlayerDeathWaypointEvent deathEvent;
        final WayPoint newWaypoint;

        if (!player.hasPermission("waypoint.death-waypoint"))
            return;
        playerLocation = player.getLocation();
        date = new Date();
        waypointName = Lang.getMessage(player, "waypoint.death.name", "LANG ERROR", false,
                formatNumber(date.getDate()),
                formatNumber(date.getHours()),
                formatNumber(date.getMinutes()),
                formatNumber(date.getSeconds()));
        wayPoint = new WayPoint(waypointName, playerLocation);
        deathEvent = new PlayerDeathWaypointEvent(player, wayPoint);
        if (!Events.call(deathEvent))
            return;
        newWaypoint = deathEvent.getNewWayPoint();
        if (newWaypoint != null) {
            WayPointStorage.addPlayerWaypoint(player, newWaypoint.getName(), newWaypoint.cloneLocation());
            GPS.setLocationTarget(event.getPlayer(), newWaypoint);
        } else {
            WayPointStorage.addPlayerWaypoint(player, waypointName, playerLocation);
            GPS.setLocationTarget(event.getPlayer(), wayPoint);
        }
    }

}
