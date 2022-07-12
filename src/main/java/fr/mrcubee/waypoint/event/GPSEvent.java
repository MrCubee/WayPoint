package fr.mrcubee.waypoint.event;

import fr.mrcubee.waypoint.GPS;
import fr.mrcubee.waypoint.WayPoint;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public abstract class GPSEvent extends Event {

    private final GPS.TargetType targetType;
    private final Player targetPlayer;
    private final Location target;

    public GPSEvent(final Player targetPlayer) {
        this.targetPlayer = targetPlayer;
        this.target = null;
        this.targetType = GPS.TargetType.PLAYER;
    }

    public GPSEvent(final Location targetLocation) {
        this.target = targetLocation;
        this.targetPlayer = null;
        if (this.target instanceof WayPoint)
            this.targetType = GPS.TargetType.WAYPOINT;
        else
            this.targetType = GPS.TargetType.LOCATION;
    }

    public GPSEvent(final WayPoint targetWaypoint) {
        this.target = targetWaypoint;
        this.targetPlayer = null;
        this.targetType = GPS.TargetType.WAYPOINT;
    }

    public GPS.TargetType getTargetType() {
        return this.targetType;
    }

    public Location getTargetLocation() {
        if (this.targetType == GPS.TargetType.PLAYER) {
            if (this.targetPlayer != null)
                return this.targetPlayer.getLocation();
            return null;
        }
        return this.target;
    }

    public Location getLocationTarget() {
        if (this.targetType != GPS.TargetType.LOCATION)
            return null;
        return this.target;
    }

    public WayPoint getWayPointTarget() {
        if (this.targetType != GPS.TargetType.WAYPOINT)
            return null;
        return (WayPoint) this.target;
    }

    public Player getPlayerTarget() {
        if (this.targetType != GPS.TargetType.PLAYER)
            return null;
        return this.targetPlayer;
    }

}
