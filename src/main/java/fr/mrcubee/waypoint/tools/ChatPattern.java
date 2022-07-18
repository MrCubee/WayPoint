package fr.mrcubee.waypoint.tools;

import java.util.regex.Pattern;

public class ChatPattern {

    private static final String LOCATION_PATTERN_STR = "(?:x:[ ]*)?(-?[0-9]+)(?:(?:[ ]+|[ ]*y:[ ]*)(-?[0-9]+))?[ ]*(?:[ ]+|z:[ ]*)(-?[0-9]+)(?:[ ]*world:[ ]*?([a-zA-Z0-9_]+))?";
    public static final Pattern LOCATION_PATTERN = Pattern.compile(LOCATION_PATTERN_STR, Pattern.CASE_INSENSITIVE);

    private static final String WAYPOINT_PATTERN_STR = "\\$([^ ]*)\\$";
    public static final Pattern WAYPOINT_PATTERN = Pattern.compile(WAYPOINT_PATTERN_STR);

}
