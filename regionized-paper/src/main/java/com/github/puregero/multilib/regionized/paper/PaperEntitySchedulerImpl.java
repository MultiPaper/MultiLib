package com.github.puregero.multilib.regionized.paper;

import com.github.puregero.multilib.regionized.EntityScheduler;
import com.github.puregero.multilib.regionized.RegionizedTask;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class PaperEntitySchedulerImpl implements EntityScheduler {

    private final io.papermc.paper.threadedregions.scheduler.EntityScheduler scheduler;

    public PaperEntitySchedulerImpl(io.papermc.paper.threadedregions.scheduler.EntityScheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public boolean execute(@NotNull Plugin plugin, @NotNull Runnable run, @Nullable Runnable retired, long delay) {
        return scheduler.execute(plugin, run, retired, delay);
    }

    @Override
    public @Nullable RegionizedTask run(@NotNull Plugin plugin, @NotNull Consumer<RegionizedTask> task, @Nullable Runnable retired) {
        return new PaperRegionizedTaskWrapper(scheduler.run(plugin, scheduledTask -> task.accept(new PaperRegionizedTaskWrapper(scheduledTask)), retired));
    }

    @Override
    public @Nullable RegionizedTask runDelayed(@NotNull Plugin plugin, @NotNull Consumer<RegionizedTask> task, @Nullable Runnable retired, long delayTicks) {
        return new PaperRegionizedTaskWrapper(scheduler.runDelayed(plugin, scheduledTask -> task.accept(new PaperRegionizedTaskWrapper(scheduledTask)), retired, delayTicks));
    }

    @Override
    public @Nullable RegionizedTask runAtFixedRate(@NotNull Plugin plugin, @NotNull Consumer<RegionizedTask> task, @Nullable Runnable retired, long initialDelayTicks, long periodTicks) {
        return new PaperRegionizedTaskWrapper(scheduler.runAtFixedRate(plugin, scheduledTask -> task.accept(new PaperRegionizedTaskWrapper(scheduledTask)), retired, initialDelayTicks, periodTicks));
    }
}
