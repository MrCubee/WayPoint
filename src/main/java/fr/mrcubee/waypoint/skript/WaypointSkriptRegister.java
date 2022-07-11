package fr.mrcubee.waypoint.skript;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.registrations.Classes;
import fr.mrcubee.waypoint.WayPoint;
import fr.mrcubee.waypoint.event.GPSEvent;
import fr.mrcubee.waypoint.skript.event.WaypointSkriptEventRegister;

public class WaypointSkriptRegister {

    public static void register() {
        Classes.registerClass(new ClassInfo<WayPoint>(WayPoint.class, "waypoint")
                .user("waypoints?")
                .name("Waypoint"));
        Classes.registerClass(new ClassInfo<GPSEvent.TargetType>(GPSEvent.TargetType.class, "gpstargettype")
                .user("gpstargettypes?")
                .name("Gps Target Type"));
        WaypointSkriptEventRegister.register();
    }

}
