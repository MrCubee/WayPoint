package fr.mrcubee.waypoint;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public enum Direction {

    FORWARD,
    FORWARD_RIGHT,
    FORWARD_LEFT,
    RIGHT,
    LEFT,
    BACKWARD,
    BACKWARD_RIGHT,
    BACKWARD_LEFT;

    public static Direction getDirection(final Player player, final Location locationTarget) {
        final Vector vector;
        final Vector playerDirection;
        final Vector cross;
        double angle;

        if (player == null || locationTarget == null)
            return null;
        vector = new Vector(locationTarget.getX() - player.getLocation().getX(), 0, locationTarget.getZ() - player.getLocation().getZ());
        playerDirection = new Vector(Math.cos(Math.toRadians(player.getLocation().getYaw())), 0, Math.sin(Math.toRadians(player.getLocation().getYaw())));
        angle = Math.acos(vector.clone().normalize().dot(playerDirection.clone().normalize()));
        cross = vector.clone().getCrossProduct(playerDirection);
        if (cross.dot(new Vector(0, 1, 0)) < 0)
            angle = -angle;
        angle = Math.toDegrees(Math.round(angle * 100.0) / 100.0);

        if (angle <= -170 || angle >= 170)
            return RIGHT;
        else if (angle > 100 && angle < 170)
            return FORWARD_RIGHT;
        else if (angle <= 100 && angle >= 80)
            return FORWARD;
        else if (angle > 0.0 && angle < 80)
            return FORWARD_LEFT;
        else if (angle <= 10 && angle >= -10)
            return LEFT;
        else if (angle > -80 && angle < -10)
            return BACKWARD_LEFT;
        else if (angle <= -80 && angle >= -100)
            return BACKWARD;
        else if (angle < -100 && angle > -170)
            return BACKWARD_RIGHT;
        return null;
    }

    public static String getDirectionArrow(final Player player, final Location location) {
        final Direction direction = getDirection(player, location);

        switch (direction) {
            case FORWARD:
                return "⬆";
            case FORWARD_RIGHT:
                return "↗";
            case FORWARD_LEFT:
                return "↖";
            case RIGHT:
                return "➡";
            case LEFT:
                return "⬅";
            case BACKWARD:
                return "⬇";
            case BACKWARD_RIGHT:
                return "↘";
            case BACKWARD_LEFT:
                return "↙";
        }
        return "Error";
    }

}
