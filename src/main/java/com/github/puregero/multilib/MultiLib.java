package com.github.puregero.multilib;

import com.github.puregero.multilib.bukkit.BukkitImpl;
import com.github.puregero.multilib.multipaper.MultiPaperImpl;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

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

    public static boolean isMultiPaper() {
        return !(get() instanceof BukkitImpl);
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
    public static boolean isChunkLocal(World world, int cx, int cz) {
        return get().isChunkLocal(world, cx, cz);
    }

    /**
     * Returns whether the chunk is running on this server or not.
     *
     * @return True if the chunk is a local chunk, or false if the chunk
     * is running on an external server or if it's unloaded.
     */
    public static boolean isChunkLocal(Location location) {
        return get().isChunkLocal(location);
    }

    /**
     * Returns whether the chunk is running on this server or not.
     *
     * @return True if the chunk is a local chunk, or false if the chunk
     * is running on an external server or if it's unloaded.
     */
    public static boolean isChunkLocal(Entity entity) {
        return get().isChunkLocal(entity);
    }

    /**
     * Returns whether the chunk is running on this server or not.
     *
     * @return True if the chunk is a local chunk, or false if the chunk
     * is running on an external server or if it's unloaded.
     */
    public static boolean isChunkLocal(Block block) {
        return get().isChunkLocal(block);
    }

    /**
     * Returns whether the chunk is running on this server or not.
     *
     * @return True if the chunk is a local chunk, or false if the chunk
     * is running on an external server or if it's unloaded.
     */
    public static boolean isChunkLocal(Chunk chunk) {
        return get().isChunkLocal(chunk);
    }

    /**
     * Returns whether the player is on an external server or not.
     *
     * @return True if the player is on an external server.
     */
    public static boolean isExternalPlayer(Player player) {
        return get().isExternalPlayer(player);
    }

    /**
     * Returns whether the player is on this server or not.
     *
     * @return True if the player is on this server.
     */
    public static boolean isLocalPlayer(Player player) {
        return get().isLocalPlayer(player);
    }

    /**
     * Get the bungeecord name of this server.
     *
     * @return the bungeecord name of this server
     */
    @NotNull
    public static String getLocalServerName() {
        return get().getLocalServerName();
    }

    /**
     * Get the bungeecord name of the server that this player is on.
     *
     * @return The bungeecord name of the server the player is on for external
     *         players, or null for local players.
     */
    @Nullable
    public static String getExternalServerName(Player player) {
        return get().getExternalServerName(player);
    }

    /**
     * Returns cross-server data that is stored under the specified key. Note
     * that all plugins share the same set of keys. This data is
     * non-persistent, it will be lost when the player disconnects.
     *
     * @param key The key the data is stored under.
     * @return The data stored under the key, or null if the key isn't set.
     */
    public static String getData(Player player, String key) {
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
    public static void setData(Player player, String key, String value) {
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
    public static String getPersistentData(Player player, String key) {
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
    public static void setPersistentData(Player player, String key, String value) {
        get().setPersistentData(player, key, value);
    }

    /**
     * Listen to notifications sent by other servers.
     *
     * @param plugin The plugin listening to these notifications
     * @param channel The notification channel to listen to
     * @param callback A handler for any data received
     */
    public static void on(Plugin plugin, String channel, Consumer<byte[]> callback) {
        get().on(plugin, channel, callback);
    }

    /**
     * Listen to notifications sent by other servers.
     *
     * @param plugin The plugin listening to these notifications
     * @param channel The notification channel to listen to
     * @param callback A handler for any data received
     */
    public static void onString(Plugin plugin, String channel, Consumer<String> callback) {
        get().onString(plugin, channel, callback);
    }

    /**
     * Listen to notifications sent by other servers.
     *
     * @param plugin The plugin listening to these notifications
     * @param channel The notification channel to listen to
     * @param callbackWithReply A handler for any data received, and a method to reply to the server on a specified channel
     */
    public static void on(Plugin plugin, String channel, BiConsumer<byte[], BiConsumer<String, byte[]>> callbackWithReply) {
        get().on(plugin, channel, callbackWithReply);
    }

    /**
     * Listen to notifications sent by other servers.
     *
     * @param plugin The plugin listening to these notifications
     * @param channel The notification channel to listen to
     * @param callbackWithReply A handler for any data received, and a method to reply to the server on a specified channel
     */
    public static void onString(Plugin plugin, String channel, BiConsumer<String, BiConsumer<String, String>> callbackWithReply) {
        get().onString(plugin, channel, callbackWithReply);
    }

    /**
     * Notify all other servers.
     *
     * @param channel The notification channel to notify on
     * @param data The data to notify other servers with
     */
    public static void notify(String channel, byte[] data) {
        get().notify(channel, data);
    }

    /**
     * Notify all other servers.
     *
     * @param channel The notification channel to notify on
     * @param data The data to notify other servers with
     */
    public static void notify(String channel, String data) {
        get().notify(channel, data);
    }

    /**
     * Notify other servers with the specified chunk loaded
     *
     * @param chunk The chunk that's loaded
     * @param channel The notification channel to notify on
     * @param data The data to notify other servers with
     */
    public static void notify(Chunk chunk, String channel, byte[] data) {
        get().notify(chunk, channel, data);
    }

    /**
     * Notify other servers with the specified chunk loaded
     *
     * @param chunk The chunk that's loaded
     * @param channel The notification channel to notify on
     * @param data The data to notify other servers with
     */
    public static void notify(Chunk chunk, String channel, String data) {
        get().notify(chunk, channel, data);
    }

    /**
     * Notify the owning server of the specified chunk.
     * This chunk must be loaded on this server.
     * This will notify this server if this server is the owning server.
     *
     * @param chunk The loaded chunk with an owning server
     * @param channel The notification channel to notify on
     * @param data The data to notify other servers with
     */
    public static void notifyOwningServer(Chunk chunk, String channel, byte[] data) {
        get().notifyOwningServer(chunk, channel, data);
    }

    /**
     * Notify the owning server of the specified chunk.
     * This chunk must be loaded on this server.
     * This will notify this server if this server is the owning server.
     *
     * @param chunk The loaded chunk with an owning server
     * @param channel The notification channel to notify on
     * @param data The data to notify other servers with
     */
    public static void notifyOwningServer(Chunk chunk, String channel, String data) {
        get().notifyOwningServer(chunk, channel, data);
    }

    /**
     * Notify the owning server of the specified player.
     * This will notify this server if this server is the owning server.
     *
     * @param player The player with an owning server
     * @param channel The notification channel to notify on
     * @param data The data to notify other servers with
     */
    public static void notifyOwningServer(Player player, String channel, byte[] data) {
        get().notifyOwningServer(player, channel, data);
    }

    /**
     * Notify the owning server of the specified player.
     * This will notify this server if this server is the owning server.
     *
     * @param player The player with an owning server
     * @param channel The notification channel to notify on
     * @param data The data to notify other servers with
     */
    public static void notifyOwningServer(Player player, String channel, String data) {
        get().notifyOwningServer(player, channel, data);
    }

    /**
     * Says a message (or runs a command) on other servers excluding this one.
     *
     * @param message The chat message to say
     */
    public static void chatOnOtherServers(Player player, String message) {
        get().chatOnOtherServers(player, message);
    }

    /**
     * Returns all online players across all server instances.
     *
     * @return a view of all online players
     */
    public static Collection<? extends Player> getAllOnlinePlayers() {
        return get().getAllOnlinePlayers();
    }

    /**
     * Returns players logged into your single local server instance.
     *
     * @return a view of players online on your local instance
     */
    public static Collection<? extends Player> getLocalOnlinePlayers() {
        return get().getLocalOnlinePlayers();
    }

    /**
     * Gets the multipaper key-value data storage. Accessing this data is
     * asynchronous. This storage medium is hosted on the Master instance,
     * or a yaml file when using Bukkit.
     *
     * @return the multipaper data storage
     */
    public static DataStorageImpl getDataStorage() {
        return get().getDataStorage();
    }
}
