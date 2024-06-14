package com.github.puregero.multilib.multipaper;

import com.github.puregero.multilib.DataStorageImpl;
import com.github.puregero.multilib.MultiLibImpl;
import org.bukkit.Bukkit;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class MultiPaperDataStorageImpl implements DataStorageImpl {
    private final MultiPaperImpl multilib;

    public MultiPaperDataStorageImpl(MultiPaperImpl multilib) {
        this.multilib = multilib;
    }

    @Override
    public CompletableFuture<String> get(String key) {
        return Bukkit.getMultiPaperDataStorage().get(key);
    }

    @Override
    public CompletableFuture<String> get(String key, String def) {
        return Bukkit.getMultiPaperDataStorage().get(key, def);
    }

    @Override
    public CompletableFuture<Long> getLong(String key) {
        return Bukkit.getMultiPaperDataStorage().getLong(key);
    }

    @Override
    public CompletableFuture<Long> getLong(String key, long def) {
        return Bukkit.getMultiPaperDataStorage().getLong(key, def);
    }

    @Override
    public CompletableFuture<Integer> getInt(String key) {
        return Bukkit.getMultiPaperDataStorage().getInt(key);
    }

    @Override
    public CompletableFuture<Integer> getInt(String key, int def) {
        return Bukkit.getMultiPaperDataStorage().getInt(key, def);
    }

    @Override
    public CompletableFuture<Double> getDouble(String key) {
        return Bukkit.getMultiPaperDataStorage().getDouble(key);
    }

    @Override
    public CompletableFuture<Double> getDouble(String key, double def) {
        return Bukkit.getMultiPaperDataStorage().getDouble(key, def);
    }

    @Override
    public CompletableFuture<Map<String, String>> list() {
        return Bukkit.getMultiPaperDataStorage().list();
    }

    @Override
    public CompletableFuture<Map<String, String>> list(String keyPrefix) {
        return Bukkit.getMultiPaperDataStorage().list(keyPrefix);
    }

    @Override
    public CompletableFuture<String> set(String key, String value) {
        return Bukkit.getMultiPaperDataStorage().set(key, value);
    }

    @Override
    public CompletableFuture<Long> set(String key, long value) {
        return Bukkit.getMultiPaperDataStorage().set(key, value);
    }

    @Override
    public CompletableFuture<Integer> set(String key, int value) {
        return Bukkit.getMultiPaperDataStorage().set(key, value);
    }

    @Override
    public CompletableFuture<Double> set(String key, double value) {
        return Bukkit.getMultiPaperDataStorage().set(key, value);
    }

    @Override
    public CompletableFuture<String> add(String key, String increment) {
        return Bukkit.getMultiPaperDataStorage().add(key, increment);
    }

    @Override
    public CompletableFuture<Long> add(String key, long increment) {
        return Bukkit.getMultiPaperDataStorage().add(key, increment);
    }

    @Override
    public CompletableFuture<Integer> add(String key, int increment) {
        return Bukkit.getMultiPaperDataStorage().add(key, increment);
    }

    @Override
    public CompletableFuture<Double> add(String key, double increment) {
        return Bukkit.getMultiPaperDataStorage().add(key, increment);
    }

    @Override
    public MultiLibImpl getMultiLib() {
        return multilib;
    }
}
