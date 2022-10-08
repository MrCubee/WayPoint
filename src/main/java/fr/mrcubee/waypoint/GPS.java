package fr.mrcubee.waypoint;

import fr.mrcubee.langlib.Lang;
import fr.mrcubee.reflect.ClassChecker;
import fr.mrcubee.waypoint.tools.ActionBar;
import fr.mrcubee.waypoint.tools.Direction;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author MrCubee
 * @since 1.0
 * @version 1.1
 */
public class GPS extends BukkitRunnable {

    private static final Map<Player, Object> LOCATIONS = new ConcurrentHashMap<Player, Object>();

    private final ActionBarSender actionBarSender;

    public GPS() {
        if (ClassChecker.checkMethod(Player.Spigot.class, "sendMessage", ChatMessageType.class, BaseComponent[].class))
            this.actionBarSender = fr.mrcubee.waypoint.tools.v1_16_R2.ActionBar::send;
        else if (ClassChecker.checkClass("net.minecraft.server.v1_8_R3.PacketPlayOutChat")) {
            this.actionBarSender = fr.mrcubee.waypoint.tools.v1_8_R3.ActionBar::send;
        } else {
            this.actionBarSender = ActionBar::send;
        }
    }
    
    private void sendActionBar(final Player player, final Location targetLocation, final String messageId, final String name) {
        final World targetWorld = targetLocation.getWorld();
        final String direction;
        final int distance;
        final String message;

        if (!player.getLocation().getWorld().equals(targetWorld)) {
            message = Lang.getMessage(player, "gps.action_bar.change_world", "&cLANG ERROR: gps.action_bar.change_world", true, targetWorld.getName());
            this.actionBarSender.send(player, message);
            return;
        }
        direction = Direction.getDirectionArrow(player, targetLocation);
        distance = (int) Math.round(player.getLocation().distance(targetLocation));
        message = Lang.getMessage(player, messageId, "&cLANG ERROR: " + messageId, true,
                direction,
                targetLocation.getBlockX(),
                targetLocation.getBlockY(),
                targetLocation.getBlockZ(),
                distance,
                name);
        this.actionBarSender.send(player, message);
    }

    private boolean sendLocationDirection(final Player player, final Object target) {
        final Player targetPlayer;
        final Location targetLocation;

        if (player == null || target == null)
            return false;
        if (target instanceof Player) {
            targetPlayer = (Player) target;
            if (!targetPlayer.isOnline())
                return false;
            sendActionBar(player, targetPlayer.getLocation(), "gps.action_bar.player", targetPlayer.getName());
            return true;
        }
        if (target instanceof Location) {
            targetLocation = (Location) target;
            if (player.getWorld().equals(targetLocation.getWorld()) && targetLocation.distance(player.getLocation()) < 2) {
                this.actionBarSender.send(player, "");
                return false;
            }
            if (targetLocation instanceof WayPoint)
                sendActionBar(player, targetLocation, "gps.action_bar.waypoint", ((WayPoint) targetLocation).getName());
            else
                sendActionBar(player, targetLocation, "gps.action_bar.location", null);
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        Player key;

        for (Map.Entry<Player, Object> entry : getEntries()) {
            key = entry.getKey();
            if (!sendLocationDirection(key, entry.getValue()))
                LOCATIONS.remove(key);
        }
    }

    public static void setLocationTarget(final Player player, final Location location) {
        if (player == null || location == null || location.getWorld() == null)
            return;
        LOCATIONS.put(player, location.clone());
    }

    public static void setTarget(final Player player, final Player target) {
        if (player == null || target == null)
            return;
        LOCATIONS.put(player, target);
    }

    public static void removeTarget(final Player player) {
        if (player == null)
            return;
        LOCATIONS.remove(player);
    }

    public static TargetType getTargetType(final Player player) {
        final Object target;

        if (player == null)
            return null;
        target = LOCATIONS.get(player);
        if (target == null)
            return null;
        if (target instanceof Player)
            return TargetType.PLAYER;
        if (target instanceof WayPoint)
            return TargetType.WAYPOINT;
        if (target instanceof Location)
            return TargetType.LOCATION;
        return TargetType.UNKNOWN;
    }

    public static Object getTarget(final Player player) {
        if (player == null)
            return null;
        return LOCATIONS.get(player);
    }

    public static <T> T getTarget(final Player player, final Class<T> classTarget) {
        final Object target;

        if (player == null)
            return null;
        target = LOCATIONS.get(player);
        if (classTarget.isInstance(target))
            return classTarget.cast(target);;
        return null;
    }

    public static WayPoint getTargetAsWaypoint(final Player player) {
        return getTarget(player, WayPoint.class);
    }

    public static Player getTargetAsPlayer(final Player player) {
        return getTarget(player, Player.class);
    }

    public static Location getTargetLocation(final Player player) {
        final Object object;
        final Player targetPlayer;

        if (player == null)
            return null;
        object = LOCATIONS.get(player);
        if (object instanceof Player) {
            targetPlayer = (Player) object;
            if (!targetPlayer.isOnline()) {
                LOCATIONS.remove(player);
                return null;
            }
            return targetPlayer.getLocation();
        } else if (object instanceof Location)
            return ((Location) object).clone();
        LOCATIONS.remove(player);
        return null;
    }

    public static Set<Map.Entry<Player, Object>> getEntries() {
        return Collections.unmodifiableSet(LOCATIONS.entrySet());
    }

    public static enum TargetType {
        LOCATION,
        WAYPOINT,
        PLAYER,
        UNKNOWN;
    }

    @FunctionalInterface
    public static interface ActionBarSender {

        public void send(final Player recipient, final String message);

    }

}
