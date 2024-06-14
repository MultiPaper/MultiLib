package com.github.puregero.multilib.regionized.paper;

import com.github.puregero.multilib.regionized.RegionizedScheduler;
import com.github.puregero.multilib.regionized.RegionizedTask;
import io.papermc.paper.threadedregions.scheduler.RegionScheduler;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class PaperRegionizedSchedulerImpl implements RegionizedScheduler {

    private final RegionScheduler scheduler;

    public PaperRegionizedSchedulerImpl(RegionScheduler scheduler) {
        this.scheduler = scheduler;
    }


    @Override
    public void execute(@NotNull Plugin plugin, @NotNull World world, int chunkX, int chunkZ, @NotNull Runnable run) {
        scheduler.execute(plugin, world, chunkX, chunkZ, run);
    }

    @Override
    public void execute(@NotNull Plugin plugin, @NotNull Location location, @NotNull Runnable run) {
        scheduler.execute(plugin, location, run);
    }

    @Override
    public @NotNull RegionizedTask run(@NotNull Plugin plugin, @NotNull World world, int chunkX, int chunkZ, @NotNull Consumer<RegionizedTask> task) {
        return new PaperRegionizedTaskWrapper(scheduler.run(plugin, world, chunkX, chunkZ, scheduledTask -> task.accept(new PaperRegionizedTaskWrapper(scheduledTask))));
    }

    @Override
    public @NotNull RegionizedTask run(@NotNull Plugin plugin, @NotNull Location location, @NotNull Consumer<RegionizedTask> task) {
        return new PaperRegionizedTaskWrapper(scheduler.run(plugin, location, scheduledTask -> task.accept(new PaperRegionizedTaskWrapper(scheduledTask))));
    }

    @Override
    public @NotNull RegionizedTask runDelayed(@NotNull Plugin plugin, @NotNull World world, int chunkX, int chunkZ, @NotNull Consumer<RegionizedTask> task, long delayTicks) {
        return new PaperRegionizedTaskWrapper(scheduler.runDelayed(plugin, world, chunkX, chunkZ, scheduledTask -> task.accept(new PaperRegionizedTaskWrapper(scheduledTask)), delayTicks));
    }

    @Override
    public @NotNull RegionizedTask runDelayed(@NotNull Plugin plugin, @NotNull Location location, @NotNull Consumer<RegionizedTask> task, long delayTicks) {
        return new PaperRegionizedTaskWrapper(scheduler.runDelayed(plugin, location, scheduledTask -> task.accept(new PaperRegionizedTaskWrapper(scheduledTask)), delayTicks));
    }

    @Override
    public @NotNull RegionizedTask runAtFixedRate(@NotNull Plugin plugin, @NotNull World world, int chunkX, int chunkZ, @NotNull Consumer<RegionizedTask> task, long initialDelayTicks, long periodTicks) {
        return new PaperRegionizedTaskWrapper(scheduler.runAtFixedRate(plugin, world, chunkX, chunkZ, scheduledTask -> task.accept(new PaperRegionizedTaskWrapper(scheduledTask)), initialDelayTicks, periodTicks));
    }

    @Override
    public @NotNull RegionizedTask runAtFixedRate(@NotNull Plugin plugin, @NotNull Location location, @NotNull Consumer<RegionizedTask> task, long initialDelayTicks, long periodTicks) {
        return new PaperRegionizedTaskWrapper(scheduler.runAtFixedRate(plugin, location, scheduledTask -> task.accept(new PaperRegionizedTaskWrapper(scheduledTask)), initialDelayTicks, periodTicks));
    }
}
