package fr.mrcubee.waypoint.skript;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.classes.Serializer;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.yggdrasil.Fields;
import fr.mrcubee.waypoint.GPS;
import fr.mrcubee.waypoint.WayPoint;
import fr.mrcubee.waypoint.WayPointPlugin;
import fr.mrcubee.waypoint.skript.effect.WaypointSkriptEffectRegister;
import fr.mrcubee.waypoint.skript.event.WaypointSkriptEventRegister;
import fr.mrcubee.waypoint.skript.expression.WaypointSkriptExpressionRegister;
import fr.mrcubee.waypoint.util.ClassUtil;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.NotSerializableException;
import java.io.StreamCorruptedException;

public class WaypointSkriptRegister {

    public static void register(final WayPointPlugin plugin) {
        final ClassInfo<WayPoint> wayPointClassInfo = new ClassInfo<WayPoint>(WayPoint.class, "waypoint");
        final ClassInfo<GPS.TargetType> gpsTypeClassInfo = new ClassInfo<GPS.TargetType>(GPS.TargetType.class, "gpstargettype");

        Skript.registerAddon(plugin);
        wayPointClassInfo.user("waypoints?")
                .name("Waypoint")
                .since("1.2")
                .parser(new Parser<WayPoint>() {
                    @Override
                    public String toString(final WayPoint wayPoint, final int flags) {
                        final World world = wayPoint.getWorld();
                        final String worldName = world != null ? world.getName() : null;
                        return "[name: " + wayPoint.getName() + ", x: " + wayPoint.getBlockX() + ", y: " + wayPoint.getBlockY() + ", z: " + wayPoint.getBlockZ() + ", world: " + worldName + "]";
                    }

                    @Override
                    public String toVariableNameString(WayPoint wayPoint) {
                        return wayPoint.getName();
                    }

                    @Override
                    public boolean canParse(ParseContext context) {
                        return false;
                    }
                })
                .serializer(new Serializer<WayPoint>() {
                    @Override
                    public Fields serialize(WayPoint wayPoint) throws NotSerializableException {
                        final Fields fields = new Fields();

                        fields.putPrimitive("name", wayPoint.getName());
                        fields.putObject("location", wayPoint.cloneLocation());
                        return fields;
                    }

                    @Override
                    public void deserialize(WayPoint wayPoint, Fields fields) throws StreamCorruptedException, NotSerializableException {
                        throw new NotSerializableException();
                    }

                    @Override
                    protected WayPoint deserialize(Fields fields) throws StreamCorruptedException, NotSerializableException {
                        final String name = fields.getPrimitive("name", String.class);
                        final Location location = fields.getObject("location", Location.class);

                        if (name == null || location == null)
                            throw new NotSerializableException();
                        return new WayPoint(name, location);
                    }

                    @Override
                    public boolean mustSyncDeserialization() {
                        return true;
                    }

                    @Override
                    protected boolean canBeInstantiated() {
                        return false;
                    }
                });
        if (ClassUtil.isExist("ch.njol.skript.classes.Cloner") && ClassUtil.isMethodExist(ClassInfo.class, "cloner", ClassUtil.getClass("ch.njol.skript.classes.Cloner")))
            wayPointClassInfo.cloner(WayPoint::clone);
        gpsTypeClassInfo.user("gpstargettypes?")
                .name("Gps Target Type")
                .since("1.2")
                .parser(new Parser<GPS.TargetType>() {
                    @Override
                    public String toString(final GPS.TargetType targetType, final int flags) {
                        return targetType.name();
                    }

                    @Override
                    public String toVariableNameString(GPS.TargetType targetType) {
                        return targetType.name();
                    }

                    @Override
                    public boolean canParse(final ParseContext context) {
                        return false;
                    }
                })
                .serializer(new Serializer<GPS.TargetType>() {
                    @Override
                    public Fields serialize(GPS.TargetType targetType) throws NotSerializableException {
                        final Fields fields = new Fields();

                        fields.putPrimitive("ordinal", targetType.ordinal());
                        return fields;
                    }

                    @Override
                    public void deserialize(GPS.TargetType targetType, Fields fields) throws StreamCorruptedException, NotSerializableException {
                        throw new NotSerializableException();
                    }

                    @Override
                    protected GPS.TargetType deserialize(Fields fields) throws StreamCorruptedException, NotSerializableException {
                        final int ordinal = fields.getPrimitive("ordinal", int.class);
                        final GPS.TargetType[] targetTypes = GPS.TargetType.values();

                        if (ordinal < 0 || ordinal >= targetTypes.length)
                            throw new NotSerializableException();
                        return targetTypes[ordinal];
                    }

                    @Override
                    public boolean mustSyncDeserialization() {
                        return false;
                    }

                    @Override
                    protected boolean canBeInstantiated() {
                        return true;
                    }

                });
        Classes.registerClass(wayPointClassInfo);
        Classes.registerClass(gpsTypeClassInfo);
        WaypointSkriptEventRegister.register();
        WaypointSkriptExpressionRegister.register();
        WaypointSkriptEffectRegister.register();
    }

}
