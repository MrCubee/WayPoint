package fr.mrcubee.waypoint.skript.expression;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import fr.mrcubee.waypoint.WayPoint;
import fr.mrcubee.waypoint.WayPointStorage;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Set;

public class SkGetPlayerWaypointExpression extends SkExpression<WayPoint> {

    private Expression<String> waypointNameExpression;
    private Expression<Player> targetPlayerExpression;

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        super.init(expressions, matchedPattern, kleenean, parseResult);
        if (expressions == null || expressions.length < 1)
            return false;
        if (matchedPattern > 0) {
            this.targetPlayerExpression = (Expression<Player>) expressions[0];
            return true;
        }
        this.targetPlayerExpression = (Expression<Player>) expressions[1];
        if (expressions.length < 2)
            return false;
        this.waypointNameExpression = (Expression<String>) expressions[0];
        return true;
    }

    @Override
    protected WayPoint[] get(Event event) {
        final Set<WayPoint> wayPoints;
        final WayPoint wayPoint;

        if (this.waypointNameExpression == null) {
            wayPoints = WayPointStorage.getPlayerWaypoints(targetPlayerExpression.getSingle(event));
            if (wayPoints == null)
                return new WayPoint[0];
            return wayPoints.toArray(new WayPoint[wayPoints.size()]);
        }
        wayPoint = WayPointStorage.getPlayerWayPoint(targetPlayerExpression.getSingle(event), waypointNameExpression.getSingle(event));
        if (wayPoint == null)
            return new WayPoint[0];
        return new WayPoint[] {wayPoint};
    }

    @Override
    public boolean isSingle() {
        return this.waypointNameExpression == null;
    }

    @Override
    public Class<? extends WayPoint> getReturnType() {
        return WayPoint.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return null;
    }

}
