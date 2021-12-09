package com.github.puregero.multilib;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

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

    String getData(Player player, String key);

    void setData(Player player, String key, String value);

    String getPersistentData(Player player, String key);

    void setPersistentData(Player player, String key, String value);
}
