package fr.mrcubee.waypoint.event;

import fr.mrcubee.waypoint.WayPoint;
import org.bukkit.entity.Player;

public abstract class PlayerEditableWaypointEvent extends PlayerWaypointEvent {

    private WayPoint newWayPoint;

    public PlayerEditableWaypointEvent(final Player player, final WayPoint wayPoint) {
        super(player, wayPoint);
        this.newWayPoint = null;
    }

    public void setNewWayPoint(WayPoint newWayPoint) {
        this.newWayPoint = newWayPoint;
    }

    public WayPoint getNewWayPoint() {
        return this.newWayPoint;
    }

}
