package fr.mrcubee.waypoint.skript.effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import fr.mrcubee.waypoint.GPS;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class SkSetPlayerGPSEffect extends SkEffect {

    private Expression<Player> playersExpression;
    private Expression<Location> locationExpression;

    @Override
    public boolean init(Expression<?>[] expressions, int matched, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        super.init(expressions, matched, kleenean, parseResult);
        if (expressions == null || expressions.length < 2)
            return false;
        this.playersExpression = (Expression<Player>) expressions[0];
        this.locationExpression = (Expression<Location>) expressions[1];
        return true;
    }

    @Override
    protected void execute(Event event) {
        final Player[] players = this.playersExpression.getArray(event);
        final Location location = this.locationExpression.getSingle(event);

        if (location == null)
            return;
        for (Player player : players)
            GPS.setLocationTarget(player, location);
    }

    @Override
    public String toString(Event event, boolean b) {
        return null;
    }
    
}
