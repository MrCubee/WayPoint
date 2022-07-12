package fr.mrcubee.waypoint.skript.effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import fr.mrcubee.waypoint.GPS;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class SkStopPlayerGPSEffect extends SkEffect {

    private Expression<Player> playersExpression;

    @Override
    public boolean init(Expression<?>[] expressions, int matched, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        super.init(expressions, matched, kleenean, parseResult);
        if (expressions == null || expressions.length < 1)
            return false;
        this.playersExpression = (Expression<Player>) expressions[0];
        return true;
    }

    @Override
    protected void execute(Event event) {
        final Player[] players = this.playersExpression.getArray(event);

        for (Player player : players)
            GPS.removeTarget(player);
    }

    @Override
    public String toString(Event event, boolean b) {
        return null;
    }
}
