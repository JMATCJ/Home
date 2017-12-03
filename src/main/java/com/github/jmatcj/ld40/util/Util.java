package com.github.jmatcj.ld40.util;

public class Util {

    public static long timeInNS(int sec) {
        return sec * 1000000000L;
    }

    /**
     * Calculates if a specific amount of time has passed.
     * @param startNS The start time in nanoseconds.
     * @param curNS The current time in nanoseconds.
     * @param sec The number of seconds you want to pass for this to be true.
     * @return true if the specified number of seconds have passed, false otherwise.
     */
    public static boolean hasTimeElapsed(long startNS, long curNS, int sec) {
        return startNS + Util.timeInNS(sec) <= curNS;
    }
}
