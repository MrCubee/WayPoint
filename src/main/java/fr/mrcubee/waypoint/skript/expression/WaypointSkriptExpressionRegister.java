package fr.mrcubee.waypoint.skript.expression;

import ch.njol.skript.Skript;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.ExpressionType;
import fr.mrcubee.waypoint.WayPoint;

public class WaypointSkriptExpressionRegister {

    public static void register() {
        Skript.registerExpression(SkGetPlayerWaypointExpression.class, WayPoint.class, ExpressionType.COMBINED, "get %player%['s] waypoint[s]");
        Skript.registerExpression(SkGetPlayerWaypointNameExpression.class, String.class, ExpressionType.COMBINED, "get %player% waypoint['s|s'|s's] name[s]");
        Skript.registerExpression(SkCreateWaypointExpression.class, WayPoint.class, ExpressionType.COMBINED, "(new|create) %string% waypoint (at|from) %location%");
        SimplePropertyExpression.register(SkGetWaypointNameExpression.class, String.class, "name", "waypoint");
    }

}
