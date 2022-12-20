package com.github.puregero.multilib.bukkit;

import com.github.puregero.multilib.DataStorageImpl;
import com.github.puregero.multilib.MultiLibImpl;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.PluginClassLoader;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BukkitImpl implements MultiLibImpl {

    private final Map<UUID, StoredData> data = new HashMap<>();
    private final ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
    private final BukkitDataStorageImpl dataStorage = new BukkitDataStorageImpl();

    public BukkitImpl() {
        JavaPlugin plugin = JavaPlugin.getProvidingPlugin(getClass());
        plugin.getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onDisable(PluginDisableEvent event) {
                if (event.getPlugin() == plugin) {
                    data.forEach((key, value) -> value.save());
                }
            }
        }, plugin);
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
    public @NotNull String getLocalServerName() {
        return "bukkit";
    }

    @Override
    public String getExternalServerName(Player player) {
        return null;
    }

    @Override
    public String getData(Player player, String key) {
        return data.containsKey(player.getUniqueId()) ? data.get(player.getUniqueId()).getData(player, key) : null;
    }

    @Override
    public void setData(Player player, String key, String value) {
        data.computeIfAbsent(player.getUniqueId(), playerKey -> new StoredData(
                player.getUniqueId(), player.getLastLogin(), scheduler)).setData(player, key, value);
    }

    @Override
    public String getPersistentData(Player player, String key) {
        return data.containsKey(player.getUniqueId()) ? data.get(player.getUniqueId()).getPersistentData(key) : null;
    }

    @Override
    public void setPersistentData(Player player, String key, String value) {
        data.computeIfAbsent(player.getUniqueId(), playerKey -> new StoredData(
                player.getUniqueId(), player.getLastLogin(), scheduler)).setPersistentData(key, value);
    }

    @Override
    public void on(Plugin plugin, String channel, Consumer<byte[]> callback) {
        // Do nothing, no other servers exist
    }

    @Override
    public void on(Plugin plugin, String channel, BiConsumer<byte[], BiConsumer<String, byte[]>> callbackWithReply) {
        // Do nothing, no other servers exist
    }

    @Override
    public void notify(String channel, byte[] data) {
        // Do nothing, no other servers exist
    }

    @Override
    public void notify(Chunk chunk, String channel, byte[] data) {
        // Do nothing, no other servers exist
    }

    @Override
    public void chatOnOtherServers(Player player, String message) {
        // Do nothing, no other servers exist
    }

    @Override
    public Collection<? extends Player> getAllOnlinePlayers() {
        return Bukkit.getOnlinePlayers();
    }

    @Override
    public DataStorageImpl getDataStorage() {
        return dataStorage;
    }
}
