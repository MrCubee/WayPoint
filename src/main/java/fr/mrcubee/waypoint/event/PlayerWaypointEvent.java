package fr.mrcubee.waypoint.event;

import fr.mrcubee.waypoint.WayPoint;
import org.bukkit.entity.Player;

public abstract class PlayerWaypointEvent extends WayPointEvent {

    private final Player player;

    public PlayerWaypointEvent(final Player player, final WayPoint wayPoint) {
        super(wayPoint);
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

}
