package fr.mrcubee.waypoint.listeners;

import fr.mrcubee.reflect.ClassChecker;
import fr.mrcubee.waypoint.WayPoint;
import fr.mrcubee.waypoint.WayPointStorage;
import fr.mrcubee.waypoint.tools.ChatPattern;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;

/**
 * @author MrCubee
 * @since 1.0
 * @version 1.0
 */
public class AsyncPlayerChatListener implements Listener {

    private final InteractiveMessageBuilder interactiveMessageBuilder;
    private final PlayerChatMessageSender playerChatMessageSender;

    public AsyncPlayerChatListener() {
        if (ClassChecker.checkClass("net.md_5.bungee.api.chat.hover.content.Text")) {
            interactiveMessageBuilder = fr.mrcubee.waypoint.listeners.v1_16_R2.AsyncPlayerChatListener::buildInteractiveMessage;
        } else if (ClassChecker.checkClass("net.md_5.bungee.api.chat.TextComponent"))
            interactiveMessageBuilder = fr.mrcubee.waypoint.listeners.v1_8_R3.AsyncPlayerChatListener::buildInteractiveMessage;
        else
            interactiveMessageBuilder = (player, message) -> null;

        if (ClassChecker.checkMethod(Player.Spigot.class, "sendMessage", UUID.class, BaseComponent[].class)) {
            playerChatMessageSender = (sender, message, rawMessage, recipients) -> {
                final UUID senderUniqueId = sender.getUniqueId();

                for (Player player : recipients)
                    player.spigot().sendMessage(senderUniqueId, message);
            };
        } else if (ClassChecker.checkMethod(Player.Spigot.class, "sendMessage", BaseComponent[].class)) {
            playerChatMessageSender = (sender, message, rawMessage, recipients) -> {
                for (Player player : recipients)
                    player.spigot().sendMessage(message);
            };
        } else {
            playerChatMessageSender = (sender, message, rawMessage, recipients) -> {
                for (Player player : recipients)
                    player.sendMessage(rawMessage);
            };
        }
    }

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

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void event(final AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        final String chatCompleteMessage;
        final BaseComponent[] newMessage;

        event.setMessage(replaceWaypointVariable(player, event.getMessage()));
        chatCompleteMessage = String.format(event.getFormat(), player.getName(), event.getMessage());
        newMessage = interactiveMessageBuilder.build(player, chatCompleteMessage);
        if (newMessage == null)
            return;
        this.playerChatMessageSender.send(player, newMessage, chatCompleteMessage, event.getRecipients());
        event.getRecipients().clear();
    }

    @FunctionalInterface
    public static interface InteractiveMessageBuilder {

        public BaseComponent[] build(final Player player, final String message);

    }

    @FunctionalInterface
    public static interface PlayerChatMessageSender {

        public void send(final Player sender, final BaseComponent[] message, final String rawMessage, final Set<Player> recipients);

    }

}
