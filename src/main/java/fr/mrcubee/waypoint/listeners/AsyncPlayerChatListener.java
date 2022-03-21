package fr.mrcubee.waypoint.listeners;

import fr.mrcubee.waypoint.tools.LocationTools;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * @author MrCubee
 * @since 1.0
 * @version 1.0
 */
public class AsyncPlayerChatListener implements Listener {

    private BaseComponent[] buildInteractiveMessage(final Player player, final String message, final Matcher matcher) {
        final List<BaseComponent> messageList;
        int last;
        Location location;
        TextComponent textComponent;

        if (player == null || message == null || matcher == null)
            return null;
        last = 0;
        messageList = new LinkedList<BaseComponent>();
        while (matcher.find()) {
            location = LocationTools.extractLocationFromMatcher(player, matcher);
            messageList.addAll(Arrays.asList(TextComponent.fromLegacyText(message.substring(last, matcher.start()))));
            last = matcher.end();
            textComponent = new TextComponent(message.substring(matcher.start(), matcher.end()));
            textComponent.setColor(net.md_5.bungee.api.ChatColor.AQUA);
            textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, LocationTools.locationToCommand(location)));
            textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(LocationTools.locationToHoverText(location))));
            messageList.add(textComponent);
        }
        if (messageList.size() <= 0)
            return null;
        messageList.addAll(Arrays.asList(TextComponent.fromLegacyText(message.substring(last))));
        return messageList.toArray(new BaseComponent[messageList.size()]);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void event(final AsyncPlayerChatEvent event) {
        final String message = String.format(event.getFormat(), event.getPlayer().getName(), event.getMessage());
        final Matcher matcher = LocationTools.LOCATION_PATTERN.matcher(message);
        final BaseComponent[] newMessage = buildInteractiveMessage(event.getPlayer(), message, matcher);

        if (newMessage == null)
            return;
        for (Player recipient : event.getRecipients())
            recipient.spigot().sendMessage(newMessage);
        event.getRecipients().clear();
    }

}
