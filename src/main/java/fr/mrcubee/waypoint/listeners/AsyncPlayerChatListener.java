package fr.mrcubee.waypoint.listeners;

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
import java.util.regex.Pattern;

public class AsyncPlayerChatListener implements Listener {

    public static final String COORDINATE_PATTERN_STR = "(?:x:[ ]*)?(-?[0-9]+)(?:(?:[ ]+|[ ]*y:[ ]*)(-?[0-9]+))?[ ]*(?:[ ]+|z:[ ]*)(-?[0-9]+)(?:[ ]*world:[ ]*?([a-zA-Z0-9_]+))?";
    private static final Pattern COORDINATE_PATTERN = Pattern.compile(COORDINATE_PATTERN_STR, Pattern.CASE_INSENSITIVE);

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void event(final AsyncPlayerChatEvent event) {
        final String message = String.format(event.getFormat(), event.getPlayer().getName(), event.getMessage());
        final Matcher matcher = COORDINATE_PATTERN.matcher(message);
        final List<BaseComponent> messageList = new LinkedList<BaseComponent>();
        final BaseComponent[] newMessage;
        Location location;
        TextComponent textComponent;
        int last = 0;

        while (matcher.find()) {
            location = extractLocation(event.getPlayer(), matcher);
            messageList.addAll(Arrays.asList(TextComponent.fromLegacyText(message.substring(last, matcher.start()))));
            last = matcher.end();
            textComponent = new TextComponent(message.substring(matcher.start(), matcher.end()));
            textComponent.setColor(net.md_5.bungee.api.ChatColor.AQUA);
            textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, locationToCommand(location)));
            textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(locationToString(location))));
            messageList.add(textComponent);
        }
        if (messageList.size() <= 0)
            return;
        messageList.addAll(Arrays.asList(TextComponent.fromLegacyText(message.substring(last))));
        newMessage = messageList.toArray(new BaseComponent[messageList.size()]);
        for (Player recipient : event.getRecipients())
            recipient.spigot().sendMessage(newMessage);
        event.getRecipients().clear();
    }

    private static String locationToCommand(final Location location) {
        final World world = location.getWorld();
        final String command = String.format("/gps %d %d %d", location.getBlockX(), location.getBlockY(), location.getBlockZ());

        if (world == null)
            return command;
        return command + " " + world.getName();
    }

    private static String locationToString(final Location location) {
        final World world = location.getWorld();

        return ChatColor.GOLD + "x: " + ChatColor.AQUA + location.getBlockX()
                + ChatColor.GOLD + "\ny: " + ChatColor.AQUA + location.getBlockY()
                + ChatColor.GOLD + "\nz: " + ChatColor.AQUA + location.getBlockZ()
                + ChatColor.GOLD + "\nworld: " + (world == null ? ChatColor.RED + "unknown" : ChatColor.AQUA + world.getName());
    }

    private static Location extractLocation(final Player player, final Matcher matcher) {
        final int coordX = Integer.parseInt(matcher.group(1));
        final String strCoordY = matcher.group(2);
        final int coordY;
        final int coordZ = Integer.parseInt(matcher.group(3));
        final String strWorld = matcher.group(4);
        final World world = strWorld == null ? player.getWorld() : getWorld(strWorld);

        if (strCoordY == null)
            coordY = world != null ?  world.getHighestBlockYAt(coordX, coordZ, HeightMap.WORLD_SURFACE) : 0;
        else
            coordY = Integer.parseInt(strCoordY);
        return new Location(world, coordX, coordY, coordZ);
    }

    public static World getWorld(String worldName) {
        final List<World> worlds;
        int index = -1;

        if (worldName == null)
            return null;
        worlds = Bukkit.getWorlds();
        try {
            index = Integer.parseInt(worldName);
            if (index > 0 && index < worlds.size())
                return worlds.get(index);
            return null;
        } catch (NumberFormatException ignored) {};
        return Bukkit.getWorld(worldName);
    }

}
