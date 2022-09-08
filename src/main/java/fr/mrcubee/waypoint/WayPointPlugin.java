package fr.mrcubee.waypoint;

import fr.mrcubee.langlib.Lang;
import fr.mrcubee.waypoint.command.GpsCommand;
import fr.mrcubee.waypoint.command.WaypointCommand;
import fr.mrcubee.waypoint.listeners.AsyncPlayerChatListener;
import fr.mrcubee.waypoint.listeners.PlayerDeathListener;
import fr.mrcubee.waypoint.listeners.PlayerJoinQuitListener;
import fr.mrcubee.waypoint.skript.WaypointSkriptRegister;
import fr.mrcubee.waypoint.stats.bstats.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
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
    private static boolean skriptAddonUsed;

    private Metrics bstatsMetrics;
    private GPS gps;

    @Override
    public void onLoad() {
        instance = this;
        skriptAddonUsed = false;
    }

    @Override
    public void onEnable() {
        final PluginManager pluginManager = getServer().getPluginManager();

        this.bstatsMetrics = new Metrics(this, 15441);
        this.bstatsMetrics.addCustomChart(new Metrics.SingleLineChart("waypoints", WayPointStorage::countTotalWaypoint));
        this.bstatsMetrics.addCustomChart(new Metrics.SimplePie("skript_installed", () -> {
            if (WayPointPlugin.isSkriptInstalled())
                return "true";
            WayPointPlugin.skriptAddonUsed = false;
            return "false";
        }));
        this.bstatsMetrics.addCustomChart(new Metrics.SimplePie("skript_used", () -> WayPointPlugin.skriptAddonUsed ? "true" : "false"));
        saveDefaultConfig();
        Lang.setDefaultLang(getConfig().getString("default_lang"));
        this.gps = new GPS();
        this.gps.runTaskTimerAsynchronously(this, 0L, 5L);
        pluginManager.registerEvents(new AsyncPlayerChatListener(), this);
        pluginManager.registerEvents(new PlayerJoinQuitListener(), this);
        pluginManager.registerEvents(new PlayerDeathListener(), this);
        if (isSkriptInstalled()) {
            getLogger().info(Lang.getMessage("core.skript.load", "&cLANG ERROR: core.skript.load",true));
            WaypointSkriptRegister.register(this);
        } else
            getLogger().warning(Lang.getMessage("core.skript.compatibility", "&cLANG ERROR: core.skript.compatibility", true));
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

    public static boolean isSkriptInstalled() {
        final Plugin plugin = Bukkit.getPluginManager().getPlugin("Skript");

        return plugin != null;
    }

    public static void skriptAddonUsed() {
        skriptAddonUsed = true;
    }

    public GPS getGps() {
        return this.gps;
    }

    public static WayPointPlugin getInstance() {
        return instance;
    }
}
