package org.binxml.util;


/**
 * @author andy
 * @creationDate on 20.07.2004
 */
public class DebugUtil {

    public static String formatDuration(long time) {
        long s = time/1000;
        long ms = time - s * 1000;
        return s + "," + ms;
    }
    
}
