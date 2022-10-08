package fr.mrcubee.waypoint.tools;

import fr.mrcubee.langlib.Lang;
import fr.mrcubee.waypoint.GPS;
import org.bukkit.entity.Player;

/**
 * @author MrCubee
 * @since 1.2.4
 * @version 1.0
 */
public class ActionBar {

    public static void send(final Player player, final String message) {
        GPS.removeTarget(player);
        if (player.hasPermission("waypoint.admin"))
            player.sendMessage(Lang.getMessage(player, "core.action_bar.wrong_version", "&cLANG ERROR: core.action_bar.wrong_version", true));
    }

}
