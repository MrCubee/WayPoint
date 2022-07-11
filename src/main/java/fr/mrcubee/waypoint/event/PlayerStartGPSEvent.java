package fr.mrcubee.waypoint.event;

import fr.mrcubee.waypoint.WayPoint;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class PlayerStartGPSEvent extends PlayerGPSEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private boolean cancelled;

    public PlayerStartGPSEvent(final Player player, final Location targetLocation) {
        super(player, targetLocation);
        this.cancelled = false;
    }

    public PlayerStartGPSEvent(final Player player, final WayPoint targetWayPoint) {
        super(player, targetWayPoint);
        this.cancelled = false;
    }

    public PlayerStartGPSEvent(final Player player, final Player targetPlayer) {
        super(player, targetPlayer);
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
