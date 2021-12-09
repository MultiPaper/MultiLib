package com.github.puregero.multilib;

import com.github.puregero.multilib.bukkit.BukkitImpl;
import com.github.puregero.multilib.multipaper.MultiPaperImpl;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class MultiLib {

    private static MultiLibImpl multiLib;

    private static MultiLibImpl get() {
        if (multiLib != null) {
            return multiLib;
        }

        try {
            return multiLib = new MultiPaperImpl();
        } catch (Exception ignored) {
            // Ignored, not MultiPaper environment, fallback to Bukkit
        }

        return multiLib = new BukkitImpl();
    }

    /**
     * Returns whether the chunk is running on an external server or not.
     *
     * @return True if the chunk is an external chunk, or false if the chunk
     * is running on this server or if it's unloaded.
     */
    public static boolean isChunkExternal(World world, int cx, int cz) {
        return get().isChunkExternal(world, cx, cz);
    }

    /**
     * Returns whether the chunk is running on an external server or not.
     *
     * @return True if the chunk is an external chunk, or false if the chunk
     * is running on this server or if it's unloaded.
     */
    public static boolean isChunkExternal(Location location) {
        return get().isChunkExternal(location);
    }

    /**
     * Returns whether the chunk is running on an external server or not.
     *
     * @return True if the chunk is an external chunk, or false if the chunk
     * is running on this server or if it's unloaded.
     */
    public static boolean isChunkExternal(Entity entity) {
        return get().isChunkExternal(entity);
    }

    /**
     * Returns whether the chunk is running on an external server or not.
     *
     * @return True if the chunk is an external chunk, or false if the chunk
     * is running on this server or if it's unloaded.
     */
    public static boolean isChunkExternal(Block block) {
        return get().isChunkExternal(block);
    }

    /**
     * Returns whether the chunk is running on an external server or not.
     *
     * @return True if the chunk is an external chunk, or false if the chunk
     * is running on this server or if it's unloaded.
     */
    public static boolean isChunkExternal(Chunk chunk) {
        return get().isChunkExternal(chunk);
    }

    /**
     * Returns whether the chunk is running on this server or not.
     *
     * @return True if the chunk is a local chunk, or false if the chunk
     * is running on an external server or if it's unloaded.
     */
    public boolean isChunkLocal(World world, int cx, int cz) {
        return get().isChunkLocal(world, cx, cz);
    }

    /**
     * Returns whether the chunk is running on this server or not.
     *
     * @return True if the chunk is a local chunk, or false if the chunk
     * is running on an external server or if it's unloaded.
     */
    public boolean isChunkLocal(Location location) {
        return get().isChunkLocal(location);
    }

    /**
     * Returns whether the chunk is running on this server or not.
     *
     * @return True if the chunk is a local chunk, or false if the chunk
     * is running on an external server or if it's unloaded.
     */
    public boolean isChunkLocal(Entity entity) {
        return get().isChunkLocal(entity);
    }

    /**
     * Returns whether the chunk is running on this server or not.
     *
     * @return True if the chunk is a local chunk, or false if the chunk
     * is running on an external server or if it's unloaded.
     */
    public boolean isChunkLocal(Block block) {
        return get().isChunkLocal(block);
    }

    /**
     * Returns whether the chunk is running on this server or not.
     *
     * @return True if the chunk is a local chunk, or false if the chunk
     * is running on an external server or if it's unloaded.
     */
    public boolean isChunkLocal(Chunk chunk) {
        return get().isChunkLocal(chunk);
    }

    /**
     * Returns whether the player is on an external server or not.
     *
     * @return True if the player is on an external server.
     */
    public boolean isExternalPlayer(Player player) {
        return get().isExternalPlayer(player);
    }

    /**
     * Returns whether the player is on this server or not.
     *
     * @return True if the player is on this server.
     */
    public boolean isLocalPlayer(Player player) {
        return get().isLocalPlayer(player);
    }

    /**
     * Returns cross-server data that is stored under the specified key. Note
     * that all plugins share the same set of keys. This data is
     * non-persistent, it will be lost when the player disconnects.
     *
     * @param key The key the data is stored under.
     * @return The data stored under the key, or null if the key isn't set.
     */
    public String getData(Player player, String key) {
        return get().getData(player, key);
    }

    /**
     * Store cross-server data under the specified key. Note that all plugins
     * share the same set of keys. This data is non-persistent, it will be
     * lost when the player disconnects.
     *
     * @param key The key to store the data under.
     * @param value The data to store under the key.
     */
    public void setData(Player player, String key, String value) {
        get().setData(player, key, value);
    }

    /**
     * Returns cross-server data that is stored under the specified key. Note
     * that all plugins share the same set of keys. This data is persistent,
     * it will be saved even if the player disconnects. This persistent data is
     * saved onto the player's .dat file.
     *
     * @param key The key the data is stored under.
     * @return The data stored under the key, or null if the key isn't set.
     */
    public String getPersistentData(Player player, String key) {
        return get().getPersistentData(player, key);
    }

    /**
     * Store cross-server data under the specified key. Note that all plugins
     * share the same set of keys. This data is persistent, it will be saved
     * even if the player disconnects. This persistent data is saved onto the
     * player's .dat file.
     *
     * @param key The key to store the data under.
     * @param value The data to store under the key.
     */
    public void setPersistentData(Player player, String key, String value) {
        get().setPersistentData(player, key, value);
    }
}
