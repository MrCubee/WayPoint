package fr.mrcubee.waypoint.skript.expression;

import fr.mrcubee.waypoint.WayPoint;

public class SkGetWaypointNameExpression extends SkPropertyExpression<WayPoint, String> {

    @Override
    protected String getPropertyName() {
        return "name";
    }

    @Override
    public String convert(final WayPoint wayPoint) {
        return wayPoint.getName();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

}
