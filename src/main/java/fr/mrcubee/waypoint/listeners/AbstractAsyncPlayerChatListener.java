package fr.mrcubee.waypoint.listeners;

import fr.mrcubee.waypoint.WayPoint;
import fr.mrcubee.waypoint.WayPointStorage;
import fr.mrcubee.waypoint.tools.ChatPattern;
import fr.mrcubee.waypoint.util.ClassUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.function.Consumer;
import java.util.regex.Matcher;

/**
 * @author MrCubee
 * @since 1.0
 * @version 1.0
 */
public abstract class AbstractAsyncPlayerChatListener implements Listener {

    protected String replaceWaypointVariable(final Player player, final String message) {
        final StringBuilder stringBuilder;
        final Matcher matcher;
        WayPoint wayPoint;
        int lastIndex;

        if (player == null || message == null)
            return null;
        matcher = ChatPattern.WAYPOINT_PATTERN.matcher(message);
        if (matcher == null)
            return null;
        stringBuilder = new StringBuilder();
        lastIndex = 0;
        while (matcher.find()) {
            if (matcher.groupCount() > 0) {
                wayPoint = WayPointStorage.getPlayerWayPoint(player, matcher.group(1));
                if (wayPoint != null) {
                    stringBuilder.append(message, lastIndex, matcher.start());
                    stringBuilder.append(wayPoint);
                    lastIndex = matcher.end();
                }
            }
        }
        stringBuilder.append(message, lastIndex, message.length());
        return stringBuilder.toString();
    }

    protected abstract BaseComponent[] buildInteractiveMessage(final Player player, final String message);

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void event(final AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        final String chatCompleteMessage;
        final BaseComponent[] newMessage;

        event.setMessage(replaceWaypointVariable(player, event.getMessage()));
        chatCompleteMessage = String.format(event.getFormat(), player.getName(), event.getMessage());
        newMessage = buildInteractiveMessage(player, chatCompleteMessage);
        if (newMessage == null)
            return;
        for (Player recipient : event.getRecipients())
            recipient.spigot().sendMessage(newMessage);
        event.getRecipients().clear();
    }

    public static AbstractAsyncPlayerChatListener newInstance() {
        if (ClassUtil.isExist("net.md_5.bungee.api.chat.hover.content.Text"))
            return new fr.mrcubee.waypoint.listeners.v1_16_R2.AsyncPlayerChatListener();
        if (ClassUtil.isExist("net.md_5.bungee.api.chat.TextComponent"))
            return new fr.mrcubee.waypoint.listeners.v1_8_R3.AsyncPlayerChatListener();
        return null;
    }

    public static void newInstance(final Consumer<AbstractAsyncPlayerChatListener> instanceConsumer) {
        final AbstractAsyncPlayerChatListener instance;

        if (instanceConsumer == null)
            return;
        instance = newInstance();
        if (instance != null)
            instanceConsumer.accept(instance);
    }

}
