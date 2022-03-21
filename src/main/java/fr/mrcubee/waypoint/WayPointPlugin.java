package fr.mrcubee.waypoint;

import fr.mrcubee.langlib.Lang;
import fr.mrcubee.waypoint.command.GpsCommand;
import fr.mrcubee.waypoint.command.WaypointCommand;
import fr.mrcubee.waypoint.listeners.AsyncPlayerChatListener;
import fr.mrcubee.waypoint.listeners.PlayerJoinQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

/**
 * @author MrCubee
 * @since 1.0
 * @version 1.0
 */
public class WayPointPlugin extends JavaPlugin {

    private static WayPointPlugin instance;

    private GPS gps;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        final PluginManager pluginManager = getServer().getPluginManager();

        saveDefaultConfig();
        Lang.setDefaultLang(getConfig().getString("default_lang"));
        this.gps = new GPS();
        this.gps.runTaskTimerAsynchronously(this, 0L, 5L);
        pluginManager.registerEvents(new AsyncPlayerChatListener(), this);
        pluginManager.registerEvents(new PlayerJoinQuitListener(), this);
        getCommand("gps").setExecutor(new GpsCommand());
        getCommand("waypoint").setExecutor(new WaypointCommand());
        for (Player player : Bukkit.getOnlinePlayers())
            WayPointStorage.loadPlayerWaypoints(player);
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            try {
                WayPointStorage.savePlayerWaypoints(player);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    public GPS getGps() {
        return this.gps;
    }

    public static WayPointPlugin getInstance() {
        return instance;
    }
}
