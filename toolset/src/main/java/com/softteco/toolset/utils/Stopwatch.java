package com.softteco.toolset.utils;

/**
 *
 * @author serge
 */
public final class Stopwatch {
    private static final int ONE_SEC = 1000;

    private final long started = System.currentTimeMillis();
    private long lapStarted = -1;

    public long lapInMillis() {
        final long currentLapStarted = this.lapStarted > 0 ? this.lapStarted : this.started;
        this.lapStarted = System.currentTimeMillis();
        return this.lapStarted - currentLapStarted;
    }

    public long lapInSeconds() {
        return (long) (lapInMillis() / ONE_SEC);
    }

    public long getTimeInMillis() {
        return System.currentTimeMillis() - started;
    }

    public long getTimeInSeconds() {
        return (long) (getTimeInMillis() / ONE_SEC);
    }
}
