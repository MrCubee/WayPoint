package fr.mrcubee.waypoint.listeners.v1_8_R3;

import fr.mrcubee.waypoint.listeners.AbstractAsyncPlayerChatListener;
import fr.mrcubee.waypoint.tools.ChatPattern;
import fr.mrcubee.waypoint.tools.LocationTools;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * @author MrCubee
 * @since 1.0
 * @version 1.0
 */
public class AsyncPlayerChatListener extends AbstractAsyncPlayerChatListener {

    @Override
    protected BaseComponent[] buildInteractiveMessage(final Player player, final String message) {
        final Matcher matcher;
        final List<BaseComponent> messageList;
        int last;
        Location location;
        TextComponent textComponent;

        if (player == null || message == null)
            return null;
        matcher = ChatPattern.LOCATION_PATTERN.matcher(message);
        if (matcher == null)
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
            TextComponent.fromLegacyText(LocationTools.locationToHoverText(location));
            textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(LocationTools.locationToHoverText(location))));
            messageList.add(textComponent);
        }
        if (messageList.size() <= 0)
            return null;
        messageList.addAll(Arrays.asList(TextComponent.fromLegacyText(message.substring(last))));
        return messageList.toArray(new BaseComponent[messageList.size()]);
    }

}
