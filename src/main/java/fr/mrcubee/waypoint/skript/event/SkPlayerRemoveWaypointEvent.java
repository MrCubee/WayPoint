package fr.mrcubee.waypoint.skript.event;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import org.bukkit.event.Event;

public class SkPlayerRemoveWaypointEvent extends SkEvent {

    @Override
    public String toString(Event event, boolean b) {
        return "player remove waypoint";
    }

}
