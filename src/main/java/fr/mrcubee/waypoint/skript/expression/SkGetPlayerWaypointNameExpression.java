package fr.mrcubee.waypoint.skript.expression;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import fr.mrcubee.waypoint.WayPointStorage;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Set;

public class SkGetPlayerWaypointNameExpression extends SkExpression<String> {

    private Expression<Player> targetPlayerExpression;

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        super.init(expressions, matchedPattern, kleenean, parseResult);
        if (expressions == null || expressions.length < 1)
            return false;
        this.targetPlayerExpression = (Expression<Player>) expressions[0];
        return true;
    }

    @Override
    protected String[] get(Event event) {
        final Set<String> names = WayPointStorage.getPlayerWaypointsName(this.targetPlayerExpression.getSingle(event));

        if (names == null)
            return new String[0];
        return names.toArray(new String[names.size()]);
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return null;
    }

}
