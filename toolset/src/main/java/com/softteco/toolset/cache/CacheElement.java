package com.softteco.toolset.cache;

class CacheElement<V> {
    private final V value;
    private final long lifetimeInMillis;
    private final long createdAt;
    private long calls = 0;

    public CacheElement(final V value, final int expirationInHours) {
        this.value = value;
        createdAt = System.currentTimeMillis();
        if (value != null) {
            lifetimeInMillis = expirationInHours * 1000 * 60 * 60;
        } else {
            lifetimeInMillis = 1000 * 60 * 30;
        }
    }

    public V getValue() {
        calls++;
        return value;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - createdAt > lifetimeInMillis;
    }

    public Long getCalls() {
        return calls;
    }
}
