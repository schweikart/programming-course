package edu.kit.informatik.uxnvp.cardgame.util;

import java.util.HashMap;
import java.util.Map;

/**
 * A builder class for maps.
 * @param <K> the key type of the map
 * @param <V> the value type of the map.
 * @author Max Schweikart
 * @version 1.0
 */
public final class MapBuilder<K, V> {
    private final Map<K, V> map;

    /**
     * Constructs an empty map builder.
     */
    public MapBuilder() {
        map = new HashMap<>();
    }

    /**
     * Puts a key-value pair into the map. This will overwrite previous entries with the given key.
     * @param key the key for this entry.
     * @param value the value for this entry.
     * @return this map builder instance.
     */
    public MapBuilder<K, V> put(K key, V value) {
        map.put(key, value);
        return this;
    }

    /**
     * Returns a new map with all entries added through {@link #put(Object, Object)}.
     * @return a new map with all entries added through {@link #put(Object, Object)}.
     */
    public Map<K, V> getMap() {
        return new HashMap<>(map);
    }
}
