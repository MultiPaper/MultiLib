package com.github.puregero.multilib.bukkit;

import com.github.puregero.multilib.MultiLibImpl;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class BukkitImpl implements MultiLibImpl {

    private final HashMap<Player, StoredData> data = new HashMap<>();
    private final ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);

    public BukkitImpl() {
        Runtime.getRuntime().addShutdownHook(new Thread("MultiLib data save") {
            @Override
            public void run() {
                data.forEach((key, value) -> value.save());
            }
        });
    }

    @Override
    public boolean isChunkExternal(World world, int cx, int cz) {
        return false;
    }

    @Override
    public boolean isChunkExternal(Location location) {
        return false;
    }

    @Override
    public boolean isChunkExternal(Entity entity) {
        return false;
    }

    @Override
    public boolean isChunkExternal(Block block) {
        return false;
    }

    @Override
    public boolean isChunkExternal(Chunk chunk) {
        return false;
    }

    @Override
    public boolean isChunkLocal(World world, int cx, int cz) {
        return true;
    }

    @Override
    public boolean isChunkLocal(Location location) {
        return true;
    }

    @Override
    public boolean isChunkLocal(Entity entity) {
        return true;
    }

    @Override
    public boolean isChunkLocal(Block block) {
        return true;
    }

    @Override
    public boolean isChunkLocal(Chunk chunk) {
        return true;
    }

    @Override
    public boolean isExternalPlayer(Player player) {
        return false;
    }

    @Override
    public boolean isLocalPlayer(Player player) {
        return true;
    }

    @Override
    public String getData(Player player, String key) {
        return data.containsKey(player) ? data.get(player).getData(player, key) : null;
    }

    @Override
    public void setData(Player player, String key, String value) {
        data.computeIfAbsent(player, playerKey -> new StoredData(playerKey, scheduler)).setData(player, key, value);
    }

    @Override
    public String getPersistentData(Player player, String key) {
        return data.containsKey(player) ? data.get(player).getPersistentData(key) : null;
    }

    @Override
    public void setPersistentData(Player player, String key, String value) {
        data.computeIfAbsent(player, playerKey -> new StoredData(playerKey, scheduler)).setPersistentData(key, value);
    }
}
