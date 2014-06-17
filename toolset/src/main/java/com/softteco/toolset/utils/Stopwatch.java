package com.softteco.toolset.utils;

/**
 *
 * @author serge
 */
public class Stopwatch {

    private final long started = System.currentTimeMillis();
    private long lapStarted = -1;

    public long lapInMillis() {
        final long currentLapStarted = this.lapStarted > 0 ? this.lapStarted : this.started;
        this.lapStarted = System.currentTimeMillis();
        return this.lapStarted - currentLapStarted;
    }

    public long lapInSeconds() {
        return (long) (lapInMillis() / 1000);
    }

    public long getTimeInMillis() {
        return System.currentTimeMillis() - started;
    }

    public long getTimeInSeconds() {
        return (long) (getTimeInMillis() / 1000);
    }
}
