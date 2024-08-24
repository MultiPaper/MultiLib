package com.github.puregero.multilib.regionized.bukkit;

import com.github.puregero.multilib.regionized.AsyncScheduler;
import com.github.puregero.multilib.regionized.EntityScheduler;
import com.github.puregero.multilib.regionized.GlobalRegionScheduler;
import com.github.puregero.multilib.regionized.RegionizedLib;
import com.github.puregero.multilib.regionized.RegionizedScheduler;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class BukkitRegionizedLibImpl implements RegionizedLib {

    private final BukkitAsyncSchedulerImpl asyncScheduler = new BukkitAsyncSchedulerImpl();
    private final BukkitGlobalRegionSchedulerImpl globalRegionScheduler = new BukkitGlobalRegionSchedulerImpl();
    private final BukkitRegionizedSchedulerImpl regionScheduler = new BukkitRegionizedSchedulerImpl();

    @Override
    public AsyncScheduler getAsyncScheduler() {
        return this.asyncScheduler;
    }

    @Override
    public GlobalRegionScheduler getGlobalRegionScheduler() {
        return this.globalRegionScheduler;
    }

    @Override
    public RegionizedScheduler getRegionScheduler() {
        return this.regionScheduler;
    }

    @Override
    public EntityScheduler getEntityScheduler(Entity entity) {
        return new BukkitEntitySchedulerImpl(entity);
    }

    @Override
    public boolean isOwnedByCurrentRegion(World world, int chunkX, int chunkZ) {
        return Bukkit.isPrimaryThread();
    }

    @Override
    public @NotNull CompletableFuture<Chunk> getChunkAtAsync(World world, int x, int z, boolean gen, boolean urgent) {
        return CompletableFuture.completedFuture(world.getChunkAt(x, z));
    }

    @Override
    public @NotNull CompletableFuture<Boolean> teleportAsync(Entity entity, Location location, PlayerTeleportEvent.TeleportCause cause) {
        return CompletableFuture.completedFuture(entity.teleport(location, cause));
    }
}
