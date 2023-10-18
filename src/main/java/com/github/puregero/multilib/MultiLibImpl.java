package com.github.puregero.multilib;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface MultiLibImpl {

    boolean isChunkExternal(World world, int cx, int cz);

    boolean isChunkExternal(Location location);

    boolean isChunkExternal(Entity entity);

    boolean isChunkExternal(Block block);

    boolean isChunkExternal(Chunk chunk);

    boolean isChunkLocal(World world, int cx, int cz);

    boolean isChunkLocal(Location location);

    boolean isChunkLocal(Entity entity);

    boolean isChunkLocal(Block block);

    boolean isChunkLocal(Chunk chunk);

    boolean isExternalPlayer(Player player);

    boolean isLocalPlayer(Player player);

    @NotNull String getLocalServerName();

    @Nullable String getExternalServerName(Player player);

    String getData(Player player, String key);

    void setData(Player player, String key, String value);

    String getPersistentData(Player player, String key);

    void setPersistentData(Player player, String key, String value);

    void on(Plugin plugin, String channel, Consumer<byte[]> callback);

    default void onString(Plugin plugin, String channel, Consumer<String> callback) {
        on(plugin, channel, bytes -> callback.accept(new String(bytes, StandardCharsets.UTF_8)));
    }

    void on(Plugin plugin, String channel, BiConsumer<byte[], BiConsumer<String, byte[]>> callbackWithReply);

    default void onString(Plugin plugin, String channel, BiConsumer<String, BiConsumer<String, String>> callbackWithReply) {
        on(plugin, channel, (bytes, reply) -> callbackWithReply.accept(
                new String(bytes, StandardCharsets.UTF_8),
                (replyChannel, string) -> reply.accept(replyChannel, string.getBytes(StandardCharsets.UTF_8)))
        );
    }

    void notify(String channel, byte[] data);

    default void notify(String channel, String data) {
        notify(channel, data.getBytes(StandardCharsets.UTF_8));
    }

    void notify(Chunk chunk, String channel, byte[] data);

    default void notify(Chunk chunk, String channel, String data) {
        notify(chunk, channel, data.getBytes(StandardCharsets.UTF_8));
    }

    void notifyOwningServer(Chunk chunk, String channel, byte[] data);

    default void notifyOwningServer(Chunk chunk, String channel, String data) {
        notifyOwningServer(chunk, channel, data.getBytes(StandardCharsets.UTF_8));
    }

    void notifyOwningServer(Player player, String channel, byte[] data);

    default void notifyOwningServer(Player player, String channel, String data) {
        notifyOwningServer(player, channel, data.getBytes(StandardCharsets.UTF_8));
    }

    void chatOnOtherServers(Player player, String message);

    Collection<? extends Player> getAllOnlinePlayers();

    default Collection<? extends Player> getLocalOnlinePlayers() {
        return getAllOnlinePlayers();
    }

    DataStorageImpl getDataStorage();
}
