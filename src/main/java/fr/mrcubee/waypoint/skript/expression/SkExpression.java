package fr.mrcubee.waypoint.skript.expression;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import fr.mrcubee.waypoint.WayPointPlugin;

public abstract class SkExpression<T> extends SimpleExpression<T> {

    @Override
    public boolean init(Expression<?>[] expressions, int matched, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        WayPointPlugin.skriptAddonUsed();
        return true;
    }
}
