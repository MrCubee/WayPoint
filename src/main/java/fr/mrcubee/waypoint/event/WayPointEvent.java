package fr.mrcubee.waypoint.event;

import fr.mrcubee.waypoint.WayPoint;
import org.bukkit.event.Event;

public abstract class WayPointEvent extends Event {

    private final WayPoint wayPoint;

    public WayPointEvent(final WayPoint wayPoint) {
        this.wayPoint = wayPoint;
    }

    public WayPoint getWayPoint() {
        return this.wayPoint;
    }
    
}
