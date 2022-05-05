package com.github.puregero.multilib;

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
}
