package fr.mrcubee.waypoint.skript.effect;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import fr.mrcubee.waypoint.WayPointPlugin;

public abstract class SkEffect extends Effect {

    @Override
    public boolean init(Expression<?>[] expressions, int matched, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        WayPointPlugin.skriptAddonUsed();
        return true;
    }
    
}
