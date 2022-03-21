package fr.mrcubee.waypoint.listeners;

import fr.mrcubee.langlib.Lang;
import fr.mrcubee.waypoint.GPS;
import fr.mrcubee.waypoint.WayPoint;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void event(final PlayerRespawnEvent event) {
        final String waypointName = Lang.getMessage(event.getPlayer(), "waypoint.death.name", "&cLANG ERROR", true);

        GPS.setLocation(event.getPlayer(), new WayPoint(waypointName, event.getPlayer().getLocation()));
    }

}
