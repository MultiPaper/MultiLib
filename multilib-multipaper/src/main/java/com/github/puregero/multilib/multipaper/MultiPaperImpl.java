package com.github.puregero.multilib.multipaper;

import com.github.puregero.multilib.DataStorageImpl;
import com.github.puregero.multilib.MultiLibImpl;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MultiPaperImpl implements MultiLibImpl {

    private final MultiPaperDataStorageImpl dataStorage = new MultiPaperDataStorageImpl(this);

    public MultiPaperImpl() {
        try {
            Player.class.getMethod("isExternalPlayer");
        } catch (NoSuchMethodException | NoSuchMethodError e) {
            throw new IllegalStateException("Not a MultiPaper environment", e);
        }
    }

    @Override
    public boolean isChunkExternal(World world, int cx, int cz) {
        return world.isChunkExternal(cx, cz);
    }

    @Override
    public boolean isChunkExternal(Location location) {
        return location.isChunkExternal();
    }

    @Override
    public boolean isChunkExternal(Entity entity) {
        return entity.isInExternalChunk();
    }

    @Override
    public boolean isChunkExternal(Block block) {
        return block.isInExternalChunk();
    }

    @Override
    public boolean isChunkExternal(Chunk chunk) {
        return chunk.isExternalChunk();
    }

    @Override
    public boolean isChunkLocal(World world, int cx, int cz) {
        return world.isChunkLocal(cx, cz);
    }

    @Override
    public boolean isChunkLocal(Location location) {
        return location.isChunkLocal();
    }

    @Override
    public boolean isChunkLocal(Entity entity) {
        return entity.isInLocalChunk();
    }

    @Override
    public boolean isChunkLocal(Block block) {
        return block.isInLocalChunk();
    }

    @Override
    public boolean isChunkLocal(Chunk chunk) {
        return chunk.isLocalChunk();
    }

    @Override
    public boolean isExternalPlayer(Player player) {
        return player.isExternalPlayer();
    }

    @Override
    public boolean isLocalPlayer(Player player) {
        return player.isLocalPlayer();
    }

    @Override
    public @NotNull String getLocalServerName() {
        return Bukkit.getLocalServerName();
    }

    @Override
    public String getExternalServerName(Player player) {
        return player.getExternalServerName();
    }

    @Override
    public String getData(Player player, String key) {
        return player.getData(key);
    }

    @Override
    public void setData(Player player, String key, String value) {
        player.setData(key, value);
    }

    @Override
    public String getPersistentData(Player player, String key) {
        return player.getPersistentData(key);
    }

    @Override
    public void setPersistentData(Player player, String key, String value) {
        player.setPersistentData(key, value);
    }

    @Override
    public void on(Plugin plugin, String channel, Consumer<byte[]> callback) {
        Bukkit.getMultiPaperNotificationManager().on(plugin, channel, callback);
    }

    @Override
    public void onString(Plugin plugin, String channel, Consumer<String> callback) {
        Bukkit.getMultiPaperNotificationManager().onString(plugin, channel, callback);
    }

    @Override
    public void on(Plugin plugin, String channel, BiConsumer<byte[], BiConsumer<String, byte[]>> callbackWithReply) {
        Bukkit.getMultiPaperNotificationManager().on(plugin, channel, callbackWithReply);
    }

    @Override
    public void onString(Plugin plugin, String channel, BiConsumer<String, BiConsumer<String, String>> callbackWithReply) {
        Bukkit.getMultiPaperNotificationManager().onString(plugin, channel, callbackWithReply);
    }

    @Override
    public void notify(String channel, byte[] data) {
        Bukkit.getMultiPaperNotificationManager().notify(channel, data);
    }

    @Override
    public void notify(String channel, String data) {
        Bukkit.getMultiPaperNotificationManager().notify(channel, data);
    }

    @Override
    public void notify(Chunk chunk, String channel, byte[] data) {
        Bukkit.getMultiPaperNotificationManager().notify(chunk, channel, data);
    }

    @Override
    public void notify(Chunk chunk, String channel, String data) {
        Bukkit.getMultiPaperNotificationManager().notify(chunk, channel, data);
    }

    @Override
    public void notifyOwningServer(Chunk chunk, String channel, byte[] data) {
        Bukkit.getMultiPaperNotificationManager().notifyOwningServer(chunk, channel, data);
    }

    @Override
    public void notifyOwningServer(Chunk chunk, String channel, String data) {
        Bukkit.getMultiPaperNotificationManager().notifyOwningServer(chunk, channel, data);
    }

    @Override
    public void notifyOwningServer(Player player, String channel, byte[] data) {
        Bukkit.getMultiPaperNotificationManager().notifyOwningServer(player, channel, data);
    }

    @Override
    public void notifyOwningServer(Player player, String channel, String data) {
        Bukkit.getMultiPaperNotificationManager().notifyOwningServer(player, channel, data);
    }

    @Override
    public void chatOnOtherServers(Player player, String message) {
        player.chatOnOtherServers(message);
    }

    @Override
    public Collection<? extends Player> getAllOnlinePlayers() {
        return Bukkit.getAllOnlinePlayers();
    }

    @Override
    public Collection<? extends Player> getLocalOnlinePlayers() {
        return Bukkit.getLocalOnlinePlayers();
    }

    @Override
    public DataStorageImpl getDataStorage() {
        return dataStorage;
    }
}
