package fr.mrcubee.waypoint.util;

public class ClassUtil {

    public static Class<?> getClass(final String className) {
        if (className == null)
            return null;
        try {
            return Class.forName(className);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static boolean isExist(final String className) {
        if (className == null)
            return false;
        try {
            Class.forName(className);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public static boolean isMethodExist(final Class<?> clazz, final String methodName, final Class<?>... parameterTypes) {
        if (clazz == null || methodName == null)
            return false;
        try {
            clazz.getMethod(methodName, parameterTypes);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

}
