package fr.mrcubee.waypoint.skript.effect;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import fr.mrcubee.waypoint.WayPoint;
import fr.mrcubee.waypoint.WayPointStorage;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class SkAddPlayerWaypointEffect extends Effect {

    private Expression<WayPoint> wayPointExpression;
    private Expression<Player> playerExpression;

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if (expressions == null || expressions.length < 2)
            return false;
        this.wayPointExpression = (Expression<WayPoint>) expressions[0];
        this.playerExpression = (Expression<Player>) expressions[1];
        return true;
    }

    @Override
    protected void execute(Event event) {
        final WayPoint[] wayPoints = this.wayPointExpression.getArray(event);
        final Player[] players = this.playerExpression.getArray(event);
        WayPointStorage storage;
        WayPoint wayPoint;

        for (Player player : players) {
            wayPoint = wayPoints[0];
            WayPointStorage.addPlayerWaypoint(player, wayPoint.getName(), wayPoint);
            storage = WayPointStorage.getStorage(player);
            if (storage == null)
                continue;
            for (int i = 1; i < wayPoints.length; i++)
                storage.registerWayPoint(wayPoints[i]);
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return null;
    }
}
