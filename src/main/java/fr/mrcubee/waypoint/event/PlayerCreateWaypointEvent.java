package fr.mrcubee.waypoint.event;

import fr.mrcubee.waypoint.WayPoint;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class PlayerCreateWaypointEvent extends PlayerWaypointEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private WayPoint newWayPoint;
    private boolean cancelled;

    public PlayerCreateWaypointEvent(final Player player, final WayPoint wayPoint) {
        super(player, wayPoint);
        this.newWayPoint = null;
        this.cancelled = false;
    }

    public void setNewWayPoint(final WayPoint newWayPoint) {
        this.newWayPoint = newWayPoint;
    }

    public WayPoint getNewWayPoint() {
        return this.newWayPoint;
    }

    @Override
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}
