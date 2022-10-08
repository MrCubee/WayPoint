package fr.mrcubee.waypoint.tools.v1_8_R3;

import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * @author MrCubee
 * @since 1.2.4
 * @version 1.0
 */
public class ActionBar {

    public static void send(final Player player, final String message) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(new ChatComponentText(message), (byte) 2));
    }

}
