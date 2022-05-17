package com.github.puregero.multilib.util;

import com.github.puregero.multilib.DataStorageImpl;
import com.github.puregero.multilib.MultiLib;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class DataStorageCache {
    private static final String CHANNEL = "multilib:datastoragecacheupdate";

    private final String keyPrefix;
    private final DataStorageImpl dataStorage;
    private final Map<String, String> cache = new HashMap<>();

    public DataStorageCache(Plugin plugin, String keyPrefix, DataStorageImpl dataStorage) {
        this.keyPrefix = keyPrefix;
        this.dataStorage = dataStorage;

        MultiLib.onString(plugin, CHANNEL, key -> {
            if (key.startsWith(keyPrefix)) {
                dataStorage.get(key).thenAccept(value -> {
                    if (value == null) {
                        cache.remove(key);
                    } else {
                        cache.put(key, value);
                    }
                });
            }
        });

        dataStorage.list(keyPrefix).thenAccept(cache::putAll).join();
    }

    private void checkKey(String key) throws IllegalArgumentException {
        if (!key.startsWith(keyPrefix)) {
            throw new IllegalArgumentException(key + " does not have the prefix " + keyPrefix);
        }
    }

    public String get(String key) {
        checkKey(key);
        return cache.get(key);
    }

    public String get(String key, String def) {
        String value = get(key);
        return value != null ? value : def;
    }

    public long getLong(String key) throws NumberFormatException {
        return Long.parseLong(get(key));
    }

    public long getLong(String key, long def) {
        try {
            return getLong(key);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    public int getInt(String key) throws NumberFormatException {
        return Integer.parseInt(get(key));
    }

    public int getInt(String key, int def) {
        try {
            return getInt(key);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    public double getDouble(String key) throws NumberFormatException, NullPointerException {
        return Double.parseDouble(get(key));
    }

    public double getDouble(String key, double def) {
        try {
            return getDouble(key);
        } catch (NumberFormatException | NullPointerException e) {
            return def;
        }
    }

    public Map<String, String> list(String keyPrefix) {
        checkKey(keyPrefix);
        Map<String, String> list = new HashMap<>();
        for (Map.Entry<String, String> entry : cache.entrySet()) {
            if (entry.getKey() != null && entry.getKey().startsWith(keyPrefix)) {
                list.put(entry.getKey(), entry.getValue());
            }
        }
        return list;
    }

    public void set(String key, String value) {
        cache.put(key, value);
        dataStorage.set(key, value)
                .thenAccept(newValue -> cache.put(key, newValue))
                .thenRun(() -> MultiLib.notify(CHANNEL, key));
    }

    public void set(String key, long value) {
        set(key, Long.toString(value));
    }

    public void set(String key, int value) {
        set(key, Integer.toString(value));
    }

    public void set(String key, double value) {
        set(key, Double.toString(value));
    }

    public void add(String key, String increment) {
        cache.put(key, StringAddition.add(key, increment));
        dataStorage.add(key, increment)
                .thenAccept(newValue -> cache.put(key, newValue))
                .thenRun(() -> MultiLib.notify(CHANNEL, key));
    }

    public void add(String key, long increment) {
        add(key, Long.toString(increment));
    }

    public void add(String key, int increment) {
        add(key, Integer.toString(increment));
    }

    public void add(String key, double increment) {
        add(key, Double.toString(increment));
    }
}
