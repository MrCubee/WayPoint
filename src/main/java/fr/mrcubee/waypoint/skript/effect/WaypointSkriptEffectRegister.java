package fr.mrcubee.waypoint.skript.effect;

import ch.njol.skript.Skript;

public class WaypointSkriptEffectRegister {

    public static void register() {
        Skript.registerEffect(SkStopPlayerGPSEffect.class, "(gsp stop|stop gps) [of] %player%", "(stop|turn off) %player%['s] gps");
        Skript.registerEffect(SkSetPlayerGPSEffect.class, "(set|setup|start|turn on|guide) %player%['s] gps to %location%");

        Skript.registerEffect(SkAddPlayerWaypointEffect.class, "add %waypoint% to %player%", "add %waypoint% to %player%['s] waypoint[s]");
        Skript.registerEffect(SkRemovePlayerWaypointFromNameEffect.class, "(remove|delete) %string% from %player%");
        Skript.registerEffect(SkRemovePlayerWaypointEffect.class, "(remove|delete) %waypoint% from %player%");
        Skript.registerEffect(SkSetNewWaypointEventEffect.class, "set new waypoint [to] %waypoint%");
    }

}
