package com.softteco.toolset.cache;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleInMemoryCache<K, V> implements InMemoryCache<K, V> {
    private final Map<K, CacheElement<V>> map;
    private final int size;
    private final int cleanupSize;
    private final int expirationInHours;

    public static void main(String[] a) {
        InMemoryCache<Long, Date> cache = new SimpleInMemoryCache<>();
        for (long i = 0; i < 1000; i++) {
            cache.put(i, new Date());
            for (int j = 0; j < Math.random() * 100; j++) {
                cache.get(i);
            }
        }

        System.out.println(cache.size());
    }

    public SimpleInMemoryCache() {
        this(100, 1);
    }

    public SimpleInMemoryCache(final int size) {
        this(size, 1);
    }

    public SimpleInMemoryCache(final int size, final int expirationInHours) {
        this.size = size;
        this.cleanupSize = size / 10;
        this.expirationInHours = expirationInHours;
        map = new ConcurrentHashMap<>(size);
    }

    @Override
    public boolean containsKey(final K key) {
        if (!map.containsKey(key)) {
            return false;
        }
        final CacheElement<V> element = map.get(key);
        if (element.isExpired()) {
            remove(key);
            return false;
        }
        return true;
    }

    @Override
    public void put(final K key, final V value) {
        if (map.size() == size) {
            cleanup();
        }
        map.put(key, new CacheElement<>(value, expirationInHours));
    }

    private synchronized void cleanup() {
        final Set<K> keysToRemove = new HashSet<>();
        final List<Long> calls = new ArrayList<>();
        for (K key : map.keySet()) {
            final CacheElement<V> element = map.get(key);
            if (element.isExpired()) {
                keysToRemove.add(key);
                continue;
            }

            calls.add(element.getCalls());
        }

        if (keysToRemove.size() < cleanupSize) {
            Collections.sort(calls);

            long callsLimit = calls.get(Math.min(calls.size() - 1, cleanupSize - keysToRemove.size()));
            for (K key : map.keySet()) {
                final CacheElement<V> element = map.get(key);
                if (element.getCalls() <= callsLimit) {
                    keysToRemove.add(key);
                }
            }
        }

        for (K key : keysToRemove) {
            remove(key);
        }
    }

    @Override
    public V get(final K key) {
        final CacheElement<V> element = map.get(key);
        if (element == null) {
            return null;
        }
        if (element.isExpired()) {
            remove(key);
        }
        return element.getValue();
    }

    @Override
    public void remove(final K key) {
        map.remove(key);
    }

    @Override
    public int size() {
        return map.size();
    }
}
