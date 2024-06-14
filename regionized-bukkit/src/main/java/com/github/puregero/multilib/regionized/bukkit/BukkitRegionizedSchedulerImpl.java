package com.github.puregero.multilib.regionized.bukkit;

import com.github.puregero.multilib.regionized.RegionizedScheduler;
import com.github.puregero.multilib.regionized.RegionizedTask;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class BukkitRegionizedSchedulerImpl implements RegionizedScheduler {

    @Override
    public void execute(@NotNull Plugin plugin, @NotNull World world, int chunkX, int chunkZ, @NotNull Runnable run) {
        Bukkit.getScheduler().runTask(plugin, run);
    }

    @Override
    public @NotNull RegionizedTask run(@NotNull Plugin plugin, @NotNull World world, int chunkX, int chunkZ, @NotNull Consumer<RegionizedTask> task) {
        return new BukkitRegionizedTaskWrapper(plugin, task).schedule();
    }

    @Override
    public @NotNull RegionizedTask runDelayed(@NotNull Plugin plugin, @NotNull World world, int chunkX, int chunkZ, @NotNull Consumer<RegionizedTask> task, long delayTicks) {
        return new BukkitRegionizedTaskWrapper(plugin, task, delayTicks).schedule();
    }

    @Override
    public @NotNull RegionizedTask runAtFixedRate(@NotNull Plugin plugin, @NotNull World world, int chunkX, int chunkZ, @NotNull Consumer<RegionizedTask> task, long initialDelayTicks, long periodTicks) {
        return new BukkitRegionizedTaskWrapper(plugin, task, initialDelayTicks, periodTicks).schedule();
    }
}
