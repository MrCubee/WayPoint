package fr.mrcubee.waypoint.skript.expression;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import fr.mrcubee.waypoint.WayPoint;
import org.bukkit.Location;
import org.bukkit.event.Event;

public class SkCreateWaypointExpression extends SkExpression<WayPoint> {

    private Expression<String> nameExpression;
    private Expression<Location> locationExpression;

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        super.init(expressions, matchedPattern, kleenean, parseResult);
        if (expressions == null || expressions.length < 2)
            return false;
        this.nameExpression = (Expression<String>) expressions[0];
        this.locationExpression = (Expression<Location>) expressions[1];
        return true;
    }

    @Override
    protected WayPoint[] get(final Event event) {
        final String name = this.nameExpression.getSingle(event);
        final Location location = this.locationExpression.getSingle(event);

        if (name == null || location == null || location.getWorld() == null)
            return new WayPoint[0];
        return new WayPoint[] {new WayPoint(name, location)};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends WayPoint> getReturnType() {
        return WayPoint.class;
    }

    @Override
    public String toString(final Event event, final boolean b) {
        return null;
    }

}
