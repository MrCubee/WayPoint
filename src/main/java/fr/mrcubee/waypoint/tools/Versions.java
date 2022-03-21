package fr.mrcubee.waypoint.tools;

import org.bukkit.Bukkit;

/**
 * @author MrCubee
 * @since 1.0
 * @version 1.0
 */
public enum Versions {

    v1_7_R4(5),
    v1_8_R1(47),
    v1_8_R2(47),
    v1_8_R3(47),
    v1_9_R1(107),
    v1_9_R2(110),
    v1_10_R1(210),
    v1_11_R1(316),
    v1_12_R1(340),
    v1_13_R1(393),
    v1_13_R2(404),
    v1_14_R1(498),
    v1_15_R1(573),
    v1_16_R1(736),
    v1_16_R2(753),
    v1_16_R3(754),
    v1_17_R1(756),
    v1_18_R1(757),
    v1_18_R2(758);

    private final int number;

    Versions(int number) {
        this.number = number;
    }

    /** Gives the version number of the protocol.
     * @since 1.0
     * @return Returns the version protocol number.
     */
    public int getNumber() {
        return this.number;
    }

    /** Get the corresponding enumeration from the string.
     * @since 1.0
     * @param str The targeted string.
     * @return Returns the corresponding enumeration if it exists otherwise returns null.
     */
    public static Versions fromString(String str) {
        Versions[] versions;

        if (str == null)
            return null;
        versions = Versions.values();
        for (Versions version : versions) {
            if (version.toString().equalsIgnoreCase(str))
                return version;
        }
        return null;
    }

    /**
     * Returns the enumeration value corresponding to the current version.
     * @since 1.0
     * @return Returns the enumeration value corresponding to the current version.
     */
    public static Versions getCurrent() {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();

        return fromString(packageName.substring(packageName.lastIndexOf('.') + 1));
    }
}

