package fr.mrcubee.waypoint.skript.expression;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import fr.mrcubee.waypoint.WayPointPlugin;

public abstract class SkPropertyExpression<F, T> extends SimplePropertyExpression<F, T> {

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        WayPointPlugin.skriptAddonUsed();
        return super.init(expressions, matchedPattern, isDelayed, parseResult);
    }

}
