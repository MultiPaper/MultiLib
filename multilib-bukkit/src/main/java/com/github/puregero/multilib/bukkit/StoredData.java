package com.github.puregero.multilib.bukkit;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class StoredData {
    private long lastPlayed;
    private final UUID uuid;
    private final ScheduledThreadPoolExecutor scheduler;
    private final Map<String, String> data = new HashMap<>();
    private final Map<String, String> persistentData = new HashMap<>();
    private boolean needsSaving = false;
    private ScheduledFuture<?> saveFuture = null;

    public StoredData(UUID uuid, long lastPlayed, ScheduledThreadPoolExecutor scheduler) {
        this.uuid = uuid;
        this.lastPlayed = lastPlayed;
        this.scheduler = scheduler;

        load();
    }

    private File getFile() {
        return new File("multilib-data/" + uuid + ".json");
    }

    private void load() {
        if (getFile().isFile()) {
            try {
                YamlConfiguration configuration = new YamlConfiguration();

                configuration.load(getFile());

                for (String key : configuration.getKeys(true)) {
                    persistentData.put(key, configuration.getString(key));
                }
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }
    }

    public void save() {
        if (needsSaving) {
            YamlConfiguration configuration = new YamlConfiguration();

            persistentData.forEach(configuration::set);

            try {
                configuration.save(getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }

            needsSaving = false;
        }
    }

    private void checkIfPlayerChanged(Player player) {
        // If the player has changed, that means they disconnected so we need to reset their non-persistent data
        if (lastPlayed != player.getLastPlayed()) {
            lastPlayed = player.getLastPlayed();
            data.clear();
        }
    }

    public String getData(Player player, String key) {
        checkIfPlayerChanged(player);

        return data.get(key);
    }

    public void setData(Player player, String key, String value) {
        checkIfPlayerChanged(player);

        data.put(key, value);
    }

    public String getPersistentData(String key) {
        return persistentData.get(key);
    }

    public void setPersistentData(String key, String value) {
        persistentData.put(key, value);

        needsSaving = true;
        if (saveFuture == null || saveFuture.isDone() || saveFuture.isCancelled()) {
            saveFuture = scheduler.schedule(this::save, 60, TimeUnit.SECONDS);
        }
    }
}
