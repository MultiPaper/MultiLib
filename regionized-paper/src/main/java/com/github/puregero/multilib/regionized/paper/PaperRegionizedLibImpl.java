package com.github.puregero.multilib.regionized.paper;

import com.github.puregero.multilib.regionized.AsyncScheduler;
import com.github.puregero.multilib.regionized.EntityScheduler;
import com.github.puregero.multilib.regionized.GlobalRegionScheduler;
import com.github.puregero.multilib.regionized.RegionizedLib;
import com.github.puregero.multilib.regionized.RegionizedScheduler;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class PaperRegionizedLibImpl implements RegionizedLib {

    private final PaperAsyncSchedulerImpl asyncScheduler = new PaperAsyncSchedulerImpl(Bukkit.getAsyncScheduler());
    private final PaperGlobalRegionSchedulerImpl globalRegionScheduler = new PaperGlobalRegionSchedulerImpl(Bukkit.getGlobalRegionScheduler());
    private final PaperRegionizedSchedulerImpl regionScheduler = new PaperRegionizedSchedulerImpl(Bukkit.getRegionScheduler());

    public PaperRegionizedLibImpl() {
        try {
            Entity.class.getMethod("getScheduler");
            Server.class.getMethod("getRegionScheduler");
            Server.class.getMethod("getGlobalRegionScheduler");
            Server.class.getMethod("getAsyncScheduler");
        } catch (NoSuchMethodException | NoSuchMethodError e) {
            throw new IllegalStateException("Not a Folia-compatible Paper environment", e);
        }
    }

    @Override
    public AsyncScheduler getAsyncScheduler() {
        return asyncScheduler;
    }

    @Override
    public GlobalRegionScheduler getGlobalRegionScheduler() {
        return globalRegionScheduler;
    }

    @Override
    public RegionizedScheduler getRegionScheduler() {
        return regionScheduler;
    }

    @Override
    public EntityScheduler getEntityScheduler(Entity entity) {
        return new PaperEntitySchedulerImpl(entity.getScheduler());
    }

    @Override
    public boolean isOwnedByCurrentRegion(World world, int chunkX, int chunkZ) {
        return Bukkit.isOwnedByCurrentRegion(world, chunkX, chunkZ);
    }

    @Override
    public boolean isOwnedByCurrentRegion(Location location) {
        return Bukkit.isOwnedByCurrentRegion(location);
    }

    @Override
    public boolean isOwnedByCurrentRegion(Entity entity) {
        return Bukkit.isOwnedByCurrentRegion(entity);
    }

    @Override
    public @NotNull CompletableFuture<Chunk> getChunkAtAsync(World world, int x, int z, boolean gen, boolean urgent) {
        return world.getChunkAtAsync(x, z, gen, urgent);
    }

    @Override
    public @NotNull CompletableFuture<Chunk> getChunkAtAsync(World world, int x, int z, boolean gen) {
        return world.getChunkAtAsync(x, z, gen);
    }

    @Override
    public @NotNull CompletableFuture<Chunk> getChunkAtAsync(World world, int x, int z) {
        return world.getChunkAtAsync(x, z);
    }

    @Override
    public @NotNull CompletableFuture<Chunk> getChunkAtAsync(Location location) {
        return location.getWorld().getChunkAtAsync(location);
    }

    @Override
    public @NotNull CompletableFuture<Boolean> teleportAsync(Entity entity, Location location, PlayerTeleportEvent.TeleportCause cause) {
        return entity.teleportAsync(location, cause);
    }
}
