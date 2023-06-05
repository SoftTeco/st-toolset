package com.softteco.toolset.cache;

public interface Cache<K, V> {
    boolean containsKey(K key);

    void put(K key, V value);

    V get(K key);

    void remove(K key);

    int size();

    void clear();
}
