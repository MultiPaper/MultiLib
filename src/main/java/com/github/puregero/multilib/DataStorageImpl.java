package com.github.puregero.multilib;

import com.github.puregero.multilib.util.DataStorageCache;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface DataStorageImpl {
    CompletableFuture<String> get(String key);

    default CompletableFuture<String> get(String key, String def) {
        return get(key).thenApply(value -> value != null ? value : def);
    }

    default CompletableFuture<Long> getLong(String key) {
        return get(key).thenApply(Long::parseLong);
    }

    default CompletableFuture<Long> getLong(String key, long def) {
        return getLong(key).exceptionally(throwable -> def);
    }

    default CompletableFuture<Integer> getInt(String key) {
        return get(key).thenApply(Integer::parseInt);
    }

    default CompletableFuture<Integer> getInt(String key, int def) {
        return getInt(key).exceptionally(throwable -> def);
    }

    default CompletableFuture<Double> getDouble(String key) {
        return get(key).thenApply(Double::parseDouble);
    }

    default CompletableFuture<Double> getDouble(String key, double def) {
        return getDouble(key).exceptionally(throwable -> def);
    }

    default CompletableFuture<Map<String, String>> list() {
        return list(null);
    }

    CompletableFuture<Map<String, String>> list(String keyPrefix);

    CompletableFuture<String> set(String key, String value);

    default CompletableFuture<Long> set(String key, long value) {
        return set(key, Long.toString(value)).thenApply(Long::parseLong);
    }

    default CompletableFuture<Integer> set(String key, int value) {
        return set(key, Integer.toString(value)).thenApply(Integer::parseInt);
    }

    default CompletableFuture<Double> set(String key, double value) {
        return set(key, Double.toString(value)).thenApply(Double::parseDouble);
    }

    CompletableFuture<String> add(String key, String increment);

    default CompletableFuture<Long> add(String key, long increment) {
        return add(key, Long.toString(increment)).thenApply(Long::parseLong);
    }

    default CompletableFuture<Integer> add(String key, int increment) {
        return add(key, Integer.toString(increment)).thenApply(Integer::parseInt);
    }

    default CompletableFuture<Double> add(String key, double increment) {
        return add(key, Double.toString(increment)).thenApply(Double::parseDouble);
    }

    /**
     * Creates a cache for the specified key prefix. All keys with the
     * specified prefix will be downloaded and cached on the server. When a
     * key is modified via this key cache, all other caches on other servers
     * will be updated with the modification.
     *
     * Warning: Any modifications made to keys that are performed outside this
     * cache will not be reflected on the cache.
     *
     * Warning: There must only be one cache per prefix per server. Other
     * caches on the same server with the same key prefix will not be updated
     * when a key is modified. If you have a cache with the prefix
     * "myplugin.subkey", you can not have another cache with the prefix
     * "myplugin" as they will conflict.
     *
     * @param plugin The plugin to register the cache modification listeners
     * @param keyPrefix The prefix for keys that should be cached. Eg supply
     *                  "myplugin" if you use keys such as "myplugin.mykey"
     *                  and "myplugin.myotherkey". There must only be one cache
     *                  per prefix per server. An empty string may be supplied
     *                  to cache all keys, but it will conflict with any other
     *                  caches no matter their prefix.
     * @return The cache for the specified key prefix
     */
    default DataStorageCache createCache(Plugin plugin, String keyPrefix) {
        return new DataStorageCache(plugin, keyPrefix, this);
    }
}
