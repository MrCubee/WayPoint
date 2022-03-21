package fr.mrcubee.waypoint.listeners;

import fr.mrcubee.langlib.Lang;
import fr.mrcubee.waypoint.GPS;
import fr.mrcubee.waypoint.WayPoint;
import fr.mrcubee.waypoint.WayPointStorage;
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
        final Location playerLocation = player.getLocation();
        final Date date = new Date();
        final String waypointName = Lang.getMessage(player, "waypoint.death.format", "ERROR", false,
                formatNumber(date.getDate()),
                formatNumber(date.getHours()),
                formatNumber(date.getMinutes()),
                formatNumber(date.getSeconds()));
        final WayPoint wayPoint = new WayPoint(waypointName, playerLocation);

        WayPointStorage.addPlayerWaypoint(player, waypointName, playerLocation);
        GPS.setLocation(event.getPlayer(), wayPoint);
    }

}
