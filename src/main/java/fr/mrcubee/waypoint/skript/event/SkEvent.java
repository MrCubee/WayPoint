package fr.mrcubee.waypoint.skript.event;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import fr.mrcubee.waypoint.WayPointPlugin;
import org.bukkit.event.Event;

public abstract class SkEvent extends SkriptEvent {

    @Override
    public boolean init(Literal<?>[] literals, int i, SkriptParser.ParseResult parseResult) {
        WayPointPlugin.skriptAddonUsed();
        return true;
    }

    @Override
    public boolean check(Event event) {
        return true;
    }

}
