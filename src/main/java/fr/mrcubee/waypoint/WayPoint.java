package fr.mrcubee.waypoint;

import fr.mrcubee.waypoint.command.GpsCommand;
import fr.mrcubee.waypoint.listeners.AsyncPlayerChatListener;
import org.bukkit.plugin.java.JavaPlugin;

public class WayPoint extends JavaPlugin {

    private GPS gps;

    @Override
    public void onEnable() {
        this.gps = new GPS();
        this.gps.runTaskTimerAsynchronously(this, 0L, 5L);
        getServer().getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);
        getCommand("gps").setExecutor(new GpsCommand());
    }

    public GPS getGps() {
        return this.gps;
    }
}
