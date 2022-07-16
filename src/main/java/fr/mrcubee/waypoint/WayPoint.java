package fr.mrcubee.waypoint;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.Objects;

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

    public WayPoint clone() {
        return new WayPoint(this.name, this);
    }

    public Location cloneLocation() {
        return new Location(getWorld(), getX(), getY(), getZ(), getYaw(), getPitch());
    }

    public String getName() {
        return this.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, super.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof WayPoint)
            return obj.hashCode() == hashCode();
        return super.equals(obj);
    }

}
