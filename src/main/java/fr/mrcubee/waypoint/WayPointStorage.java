package fr.mrcubee.waypoint;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class WayPointStorage implements ConfigurationSerializable {

    private final static Map<Player, WayPointStorage> PLAYER_WAYPOINTS_STORAGES = new WeakHashMap<Player, WayPointStorage>();

    private final Map<String, Location> waypoints;

    public WayPointStorage() {
        this.waypoints = new HashMap<String, Location>();
    }

    private WayPointStorage(final Map<String, Location> waypoints) {
        this.waypoints = waypoints;
    }

    public boolean registerWayPoint(final String name, final Location location) {
        if (name == null || location == null || location.getWorld() == null || this.waypoints.containsKey(name))
            return false;
        this.waypoints.put(name, location.clone());
        return true;
    }

    public boolean registerWayPoint(final WayPoint wayPoint) {
        if (wayPoint == null || wayPoint.getName() == null || wayPoint.getWorld() == null || this.waypoints.containsKey(wayPoint.getName()))
            return false;
        this.waypoints.put(wayPoint.getName(), wayPoint.cloneLocation());
        return true;
    }

    public Set<String> getWaypointsName() {
        return new HashSet<String>(this.waypoints.keySet());
    }

    public Set<WayPoint> getWaypoints() {
        final Set<WayPoint> wayPoints = new HashSet<WayPoint>(this.waypoints.size());

        for (final Map.Entry<String, Location> entry : this.waypoints.entrySet())
            wayPoints.add(new WayPoint(entry.getKey(), entry.getValue()));
        return wayPoints;
    }

    public WayPoint getFromName(final String name) {
        final Location location;

        if (name == null)
            return null;
        location = this.waypoints.get(name);
        if (location == null)
            return null;
        if (location.getWorld() == null) {
            this.waypoints.remove(name);
            return null;
        }
        return new WayPoint(name, location);
    }

    public WayPoint removeFromName(final String name) {
        final Location location;

        if (name == null)
            return null;
        location = this.waypoints.remove(name);
        if (location == null)
            return null;
        return new WayPoint(name, location);
    }

    public int count() {
        return this.waypoints.size();
    }

    public void clear() {
        this.waypoints.clear();
    }

    @Override
    public Map<String, Object> serialize() {
        final Map<String, Object> result = new HashMap<String, Object>();

        for (final Map.Entry<String, Location> entry : this.waypoints.entrySet())
            result.put(entry.getKey(), entry.getValue());
        return result;
    }

    public static WayPointStorage deserialize(final Map<String, Object> args) {
        final Map<String, Location> wayPoints = new HashMap<String, Location>();
        String name;
        Location location;

        for (final Map.Entry<String, Object> entry : args.entrySet()) {
            name = entry.getKey();
            if (name != null && !name.equals("==")) {
                location = (Location) entry.getValue();

                if (location != null && location.getWorld() != null)
                    wayPoints.put(name, location);
            }
        }
        if (wayPoints.size() < 1)
            return null;
        return new WayPointStorage(wayPoints);
    }

    public static WayPointStorage getStorage(final Player player) {
        if (player == null)
            return null;
        return PLAYER_WAYPOINTS_STORAGES.get(player);
    }

    public static Set<String> getPlayerWaypointsName(final Player player) {
        final WayPointStorage wayPointStorage;

        if (player == null)
            return null;
        wayPointStorage = PLAYER_WAYPOINTS_STORAGES.get(player);
        if (wayPointStorage == null)
            return null;
        return wayPointStorage.getWaypointsName();
    }

    public static Set<WayPoint> getPlayerWaypoints(final Player player) {
        final WayPointStorage wayPointStorage;

        if (player == null)
            return null;
        wayPointStorage = PLAYER_WAYPOINTS_STORAGES.get(player);
        if (wayPointStorage == null)
            return null;
        return wayPointStorage.getWaypoints();
    }

    public static WayPoint getPlayerWayPoint(final Player player, final String wayPointName) {
        final WayPointStorage wayPointStorage;

        if (player == null)
            return null;
        wayPointStorage = PLAYER_WAYPOINTS_STORAGES.get(player);
        if (wayPointStorage == null)
            return null;
        return wayPointStorage.getFromName(wayPointName);
    }

    public static boolean addPlayerWaypoint(final Player player, final String wayPointName, final Location location) {
        WayPointStorage wayPointStorage;

        if (player == null || wayPointName == null || location == null || location.getWorld() == null)
            return false;
        wayPointStorage = PLAYER_WAYPOINTS_STORAGES.get(player);
        if (wayPointStorage == null) {
            wayPointStorage = new WayPointStorage();
            PLAYER_WAYPOINTS_STORAGES.put(player, wayPointStorage);
        }
        return wayPointStorage.registerWayPoint(wayPointName, location);
    }

    public static WayPoint removePlayerWaypoint(final Player player, final String wayPointName) {
        final WayPointStorage wayPointStorage;
        final WayPoint wayPoint;

        if (player == null)
            return null;
        wayPointStorage = PLAYER_WAYPOINTS_STORAGES.get(player);
        if (wayPointStorage == null)
            return null;
        wayPoint = wayPointStorage.removeFromName(wayPointName);
        if (wayPointStorage.count() < 1)
            PLAYER_WAYPOINTS_STORAGES.remove(player);
        return wayPoint;
    }

    public static void savePlayerWaypoints(final Player player) throws IOException {
        final WayPointStorage wayPointStorage;
        final Plugin plugin;
        final File playersWaypointFile;
        final FileConfiguration fileConfiguration;

        if (player == null)
            return;
        plugin = WayPointPlugin.getInstance();
        playersWaypointFile = new File(plugin.getDataFolder(), "waypoint/" + player.getUniqueId() + ".yml");
        wayPointStorage = PLAYER_WAYPOINTS_STORAGES.get(player);
        if (wayPointStorage == null || wayPointStorage.waypoints.size() < 1) {
            if (playersWaypointFile.exists())
                playersWaypointFile.delete();
            return;
        }
        fileConfiguration = new YamlConfiguration();
        fileConfiguration.set("storage", wayPointStorage);
        fileConfiguration.save(playersWaypointFile);
    }

    public static void loadPlayerWaypoints(final Player player) {
        final Plugin plugin;
        final File playersWaypointFile;
        final FileConfiguration fileConfiguration;
        final WayPointStorage wayPointStorage;

        if (player == null)
            return;
        plugin = WayPointPlugin.getInstance();
        playersWaypointFile = new File(plugin.getDataFolder(), "waypoint/" + player.getUniqueId() + ".yml");
        if (!playersWaypointFile.exists())
            return;
        fileConfiguration = YamlConfiguration.loadConfiguration(playersWaypointFile);
        if (fileConfiguration == null)
            return;
        wayPointStorage = (WayPointStorage) fileConfiguration.get("storage");
        PLAYER_WAYPOINTS_STORAGES.put(player, wayPointStorage);
    }

    public static int countTotalWaypoint() {
        int total = 0;

        for (final WayPointStorage wayPointStorage : PLAYER_WAYPOINTS_STORAGES.values())
            total += wayPointStorage.count();
        return total;
    }

}
