package fr.mrcubee.waypoint;

import org.bukkit.Location;
import org.bukkit.World;

public class WayPoint extends Location {

    private final String name;

    public WayPoint(final String name, final World world, final double x, final double y, final double z) {
        super(world, x, y, z);
        this.name = name;
    }

    public WayPoint(final String name, final Location location) {
        super(location.getWorld(), location.getX(), location.getY(), location.getZ());
        this.name = name;
    }

    @Override
    public Location clone() {
        return new WayPoint(this.name, this);
    }

    public Location cloneLocation() {
        return super.clone();
    }

    public String getName() {
        return this.name;
    }

}
