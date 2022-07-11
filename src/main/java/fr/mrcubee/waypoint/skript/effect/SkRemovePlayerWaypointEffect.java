package fr.mrcubee.waypoint.skript.effect;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import fr.mrcubee.waypoint.WayPoint;
import fr.mrcubee.waypoint.WayPointStorage;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class SkRemovePlayerWaypointEffect extends Effect {

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

        for (Player player : players) {
            storage = WayPointStorage.getStorage(player);
            if (storage == null)
                continue;
            for (WayPoint waypoint : wayPoints)
                storage.removeFromName(waypoint.getName());
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return null;
    }
}
