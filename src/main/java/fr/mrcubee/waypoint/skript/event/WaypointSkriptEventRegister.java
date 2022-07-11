package fr.mrcubee.waypoint.skript.event;

import ch.njol.skript.Skript;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import fr.mrcubee.waypoint.WayPoint;
import fr.mrcubee.waypoint.event.*;
import org.bukkit.entity.Player;

public class WaypointSkriptEventRegister {


    public static void register() {

        /* GPS Events */
        Skript.registerEvent("player gps start", SkPlayerStartGPSEvent.class, PlayerStartGPSEvent.class, "player start gps")
                .description("Call when the player turns on their GPS.")
                .since("1.1.3");
        Skript.registerEvent("player gps stop", SkPlayerStopGPSEvent.class, PlayerStopGPSEvent.class, "player stop gps")
                .description("Call when the player turns off their GPS.")
                .since("1.1.3");
        EventValues.registerEventValue(GPSEvent.class, GPSEvent.TargetType.class, new Getter<GPSEvent.TargetType, GPSEvent>() {
            @Override
            public GPSEvent.TargetType get(final GPSEvent event) {
                return event.getTargetType();
            }
        }, 0);
        EventValues.registerEventValue(PlayerGPSEvent.class, Player.class, new Getter<Player, PlayerGPSEvent>() {
            @Override
            public Player get(final PlayerGPSEvent event) {
                return event.getPlayer();
            }
        }, 0);

        /* Waypoint Events */
        Skript.registerEvent("player waypoint create", SkPlayerCreateWaypointEvent.class, PlayerCreateWaypointEvent.class, "player create waypoint")
                .description("Call when the player creates a waypoint.")
                .since("1.2");
        Skript.registerEvent("player remove waypoint", SkPlayerRemoveWaypointEvent.class, PlayerRemoveWaypointEvent.class, "player remove waypoint")
                .description("Call when the player deletes a waypoint.")
                .since("1.2");
        EventValues.registerEventValue(WayPointEvent.class, WayPoint.class, new Getter<WayPoint, WayPointEvent>() {
            @Override
            public WayPoint get(final WayPointEvent event) {
                return event.getWayPoint();
            }
        }, 0);
        EventValues.registerEventValue(PlayerWaypointEvent.class, Player.class, new Getter<Player, PlayerWaypointEvent>() {
            @Override
            public Player get(final PlayerWaypointEvent event) {
                return event.getPlayer();
            }
        }, 0);
    }

}
