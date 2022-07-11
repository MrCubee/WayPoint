package fr.mrcubee.waypoint.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public class Events {

    public static boolean call(final Event event) {
        if (event == null)
            return true;
        Bukkit.getPluginManager().callEvent(event);
        if (event instanceof Cancellable)
            return !((Cancellable) event).isCancelled();
        return true;
    }

}
