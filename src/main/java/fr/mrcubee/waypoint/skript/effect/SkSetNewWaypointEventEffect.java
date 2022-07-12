package fr.mrcubee.waypoint.skript.effect;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.skript.util.Utils;
import ch.njol.util.Kleenean;
import fr.mrcubee.waypoint.WayPoint;
import fr.mrcubee.waypoint.WayPointStorage;
import fr.mrcubee.waypoint.event.PlayerEditableWaypointEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class SkSetNewWaypointEventEffect extends SkEffect {

    private Expression<WayPoint> wayPointExpression;

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        final Class<? extends Event>[] events;

        super.init(expressions, matchedPattern, kleenean, parseResult);
        if (expressions == null || expressions.length < 1)
            return false;
        events = getParser().getCurrentEvents();
        if (events == null || events.length < 1 || !PlayerEditableWaypointEvent.class.isAssignableFrom(events[0])) {
            Skript.error(Utils.A(getParser().getCurrentEventName()) + " is not an event containing an editable waypoint.", ErrorQuality.SEMANTIC_ERROR);
            return false;
        }
        this.wayPointExpression = (Expression<WayPoint>) expressions[0];
        return true;
    }

    @Override
    protected void execute(Event event) {
        if (event instanceof PlayerEditableWaypointEvent)
            ((PlayerEditableWaypointEvent) event).setNewWayPoint(this.wayPointExpression.getSingle(event));
    }

    @Override
    public String toString(Event event, boolean b) {
        return null;
    }

}
