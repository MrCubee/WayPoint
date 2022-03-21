package fr.mrcubee.waypoint.listeners;

import fr.mrcubee.waypoint.WayPointStorage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;

public class PlayerJoinQuitListener implements Listener {

    @EventHandler
    public void eventJoin(final PlayerJoinEvent event) {
        WayPointStorage.loadPlayerWaypoints(event.getPlayer());
    }

    @EventHandler
    public void eventQuit(final PlayerQuitEvent event) {
        try {
            WayPointStorage.savePlayerWaypoints(event.getPlayer());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
