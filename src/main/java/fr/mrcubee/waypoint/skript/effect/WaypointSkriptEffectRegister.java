package fr.mrcubee.waypoint.skript.effect;

import ch.njol.skript.Skript;

public class WaypointSkriptEffectRegister {

    public static void register() {
        Skript.registerEffect(SkStopPlayerGPSEffect.class, "(gsp stop|stop gps) [of] %player%", "stop %player%['s] gps");
        Skript.registerEffect(SkSetPlayerGPSEffect.class, "(set|setup|start|turn on|guide) %player%'s gps to %location%");

        Skript.registerEffect(SkAddPlayerWaypointEffect.class, "[add|set] %waypoint% to %player%['s] waypoint");
        Skript.registerEffect(SkRemovePlayerWaypointFromNameEffect.class, "(remove|delete) %string% from %player%");
        Skript.registerEffect(SkRemovePlayerWaypointEffect.class, "(remove|delete) %waypoint% from %player%");
    }

}
