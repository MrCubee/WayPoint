package fr.mrcubee.waypoint.event;

import fr.mrcubee.waypoint.WayPoint;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class PlayerRemoveWaypointEvent extends PlayerWaypointEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private boolean cancelled;

    public PlayerRemoveWaypointEvent(final Player player, final WayPoint wayPoint) {
        super(player, wayPoint);
        this.cancelled = false;
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
