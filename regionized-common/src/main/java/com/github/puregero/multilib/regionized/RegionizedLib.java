package com.github.puregero.multilib.regionized;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public interface RegionizedLib {

    AsyncScheduler getAsyncScheduler();

    GlobalRegionScheduler getGlobalRegionScheduler();

    RegionizedScheduler getRegionScheduler();
    
    EntityScheduler getEntityScheduler(Entity entity);

    boolean isOwnedByCurrentRegion(World world, int chunkX, int chunkZ);

    default boolean isOwnedByCurrentRegion(Location location) {
        return isOwnedByCurrentRegion(location.getWorld(), location.getBlockX() >> 4, location.getBlockZ() >> 4);
    }

    default boolean isOwnedByCurrentRegion(Entity entity) {
        return isOwnedByCurrentRegion(entity.getLocation());
    }

    @NotNull CompletableFuture<Chunk> getChunkAtAsync(World world, int x, int z, boolean gen, boolean urgent);

    default @NotNull CompletableFuture<Chunk> getChunkAtAsync(World world, int x, int z, boolean gen) {
        return getChunkAtAsync(world, x, z, gen, false);
    }

    default @NotNull CompletableFuture<Chunk> getChunkAtAsync(World world, int x, int z) {
        return getChunkAtAsync(world, x, z, true);
    }

    default @NotNull CompletableFuture<Chunk> getChunkAtAsync(Location location) {
        return getChunkAtAsync(location.getWorld(), location.getBlockX() >> 4, location.getBlockZ() >> 4);
    }

    default @NotNull CompletableFuture<Boolean> teleportAsync(Entity entity, Location location) {
        return teleportAsync(entity, location, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    @NotNull CompletableFuture<Boolean> teleportAsync(Entity entity, Location location, PlayerTeleportEvent.TeleportCause cause);
    
}
