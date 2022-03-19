package fr.mrcubee.waypoint;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class GPS extends BukkitRunnable {

    private static final String MESSAGE_FORMAT = ChatColor.translateAlternateColorCodes('&', "&6%s &b[x:%d, y: %d, z: %d] &7in &6%s &7%s");

    private static final Map<Player, Object> LOCATIONS = new WeakHashMap<Player, Object>();

    private void sendDirection(final Player player, final Location location) {
        String direction = Direction.getDirectionArrow(player, location);
        int distance = (int) Math.round(player.getLocation().distance(location));
        final String message = String.format(MESSAGE_FORMAT,
                direction,
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ(),
                distance, distance > 1 ? "blocks" : "block");
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    @Override
    public void run() {
        Player key;
        Object object;
        Player targetPlayer;

        for (Map.Entry<Player, Object> entry : getEntries()) {
            key = entry.getKey();
            object = entry.getValue();
            if (object instanceof Player) {
                targetPlayer = (Player) object;
                if (targetPlayer.isOnline())
                    sendDirection(key, targetPlayer.getLocation());
                else
                    LOCATIONS.remove(key);
            } else if (object instanceof Location)
                sendDirection(key, (Location) object);
            else
                LOCATIONS.remove(key);
        }
    }

    public static void setLocation(final Player player, final Location location) {
        if (player == null || location == null || location.getWorld() == null)
            return;
        LOCATIONS.put(player, location);
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
            return (Location) object;
        LOCATIONS.remove(player);
        return null;
    }

    public static Set<Map.Entry<Player, Object>> getEntries() {
        return Collections.unmodifiableSet(LOCATIONS.entrySet());
    }

}
