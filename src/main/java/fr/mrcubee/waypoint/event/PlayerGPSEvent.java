package fr.mrcubee.waypoint.event;

import fr.mrcubee.waypoint.WayPoint;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class PlayerGPSEvent extends GPSEvent {

    private final Player player;

    public PlayerGPSEvent(final Player player, final Location targetLocation) {
        super(targetLocation);
        this.player = player;
    }

    public PlayerGPSEvent(final Player player, final WayPoint targetWaypoint) {
        super(targetWaypoint);
        this.player = player;
    }

    public PlayerGPSEvent(final Player player, final Player targetPlayer) {
        super(targetPlayer);
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

}
