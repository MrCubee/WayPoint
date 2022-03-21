package fr.mrcubee.waypoint;

import fr.mrcubee.langlib.Lang;
import fr.mrcubee.waypoint.tools.Direction;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * @author MrCubee
 * @since 1.0
 * @version 1.0
 */
public class GPS extends BukkitRunnable {

    private static final Map<Player, Object> LOCATIONS = new WeakHashMap<Player, Object>();

    private void sendLocationDirection(final Player player, final Location targetLocation, final String messageId, final String name) {
        final String direction = Direction.getDirectionArrow(player, targetLocation);
        final int distance = (int) Math.round(player.getLocation().distance(targetLocation));
        final String message = Lang.getMessage(player, messageId, "&cLANG ERROR: " + messageId, true,
                direction,
                targetLocation.getBlockX(),
                targetLocation.getBlockY(),
                targetLocation.getBlockZ(),
                distance,
                name);

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    @Override
    public void run() {
        Player key;
        Object object;
        Player targetPlayer;
        Location targetLocation;

        for (Map.Entry<Player, Object> entry : getEntries()) {
            key = entry.getKey();
            object = entry.getValue();
            if (object instanceof Player) {
                targetPlayer = (Player) object;
                if (targetPlayer.isOnline())
                    sendLocationDirection(key, targetPlayer.getLocation(), "gps.action_bar.player", targetPlayer.getName());
                else
                    LOCATIONS.remove(key);
            } else if (object instanceof WayPoint) {
                targetLocation = (Location) object;
                sendLocationDirection(key, targetLocation, "gps.action_bar.waypoint", ((WayPoint) targetLocation).getName());
                if (targetLocation.distance(key.getLocation()) < 1)
                    LOCATIONS.remove(key);
            } else if (object instanceof Location) {
                targetLocation = (Location) object;
                sendLocationDirection(key, targetLocation, "gps.action_bar.location", null);
                if (targetLocation.distance(key.getLocation()) < 1)
                    LOCATIONS.remove(key);
            }
            else
                LOCATIONS.remove(key);
        }
    }

    public static void setLocation(final Player player, final Location location) {
        if (player == null || location == null || location.getWorld() == null)
            return;
        LOCATIONS.put(player, location.clone());
    }

    public static void setLocation(final Player player, final Player target) {
        if (player == null || target == null)
            return;
        LOCATIONS.put(player, target);
    }

    public static void removeLocation(final Player player) {
        if (player == null)
            return;
        LOCATIONS.remove(player);
    }

    public static Location getLocation(final Player player) {
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

}
