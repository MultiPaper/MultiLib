package com.github.puregero.multilib.multipaper;

import com.github.puregero.multilib.MultiLibImpl;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class MultiPaperImpl implements MultiLibImpl {

    public MultiPaperImpl() {
        try {
            Player.class.getMethod("isExternalPlayer");
        } catch (NoSuchMethodException e) {
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
}
