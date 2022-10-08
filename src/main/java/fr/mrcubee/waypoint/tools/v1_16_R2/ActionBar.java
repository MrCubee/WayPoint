package fr.mrcubee.waypoint.tools.v1_16_R2;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

/**
 * @author MrCubee
 * @since 1.2.4
 * @version 1.0
 */
public class ActionBar {

    public static void send(final Player player, final String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

}
