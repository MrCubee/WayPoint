package fr.mrcubee.waypoint.tools;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * @author MrCubee
 * @since 1.0
 * @version 1.0
 */
public enum Direction {

    FORWARD,
    FORWARD_RIGHT,
    FORWARD_LEFT,
    RIGHT,
    LEFT,
    BACKWARD,
    BACKWARD_RIGHT,
    BACKWARD_LEFT,
    DOWN,
    UP,
    HERE;

    public static Direction getDirection(final Player player, final Location locationTarget) {
        final Location playerLocation;
        final int height;
        final Vector vector;
        final Vector playerDirection;
        final Vector cross;
        double angle;

        if (player == null || locationTarget == null || locationTarget.getWorld() == null || !locationTarget.getWorld().equals(player.getWorld()))
            return null;
        playerLocation = player.getLocation();
        if ((playerLocation.getBlockX() - locationTarget.getBlockX()) + (playerLocation.getBlockZ() - locationTarget.getBlockZ()) == 0) {
            height = locationTarget.getBlockY() - playerLocation.getBlockY();
            if (height > 0)
                return UP;
            else if (height < 0)
                return DOWN;
            return HERE;
        }
        vector = new Vector(locationTarget.getX() - playerLocation.getX(), 0, locationTarget.getZ() - playerLocation.getZ());
        playerDirection = new Vector(Math.cos(Math.toRadians(playerLocation.getYaw())), 0, Math.sin(Math.toRadians(playerLocation.getYaw())));
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
            case UP:
                return "⤴";
            case DOWN:
                return "⤵";
            case HERE:
                return "⏺";
        }
        return "Error";
    }

}
