package fr.mrcubee.waypoint.skript.effect;

import ch.njol.skript.Skript;

public class WaypointSkriptEffectRegister {

    public static void register() {
        Skript.registerEffect(SkAddPlayerWaypointEffect.class, "[add|set] %waypoint% to %player%");
        Skript.registerEffect(SkRemovePlayerWaypointFromNameEffect.class, "(remove|delete) %string% from %player%");
        Skript.registerEffect(SkRemovePlayerWaypointEffect.class, "(remove|delete) %waypoint% from %player%");
    }

}
