package com.github.puregero.multilib.regionized.paper;

import com.github.puregero.multilib.regionized.AsyncScheduler;
import com.github.puregero.multilib.regionized.RegionizedTask;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class PaperAsyncSchedulerImpl implements AsyncScheduler {

    private final io.papermc.paper.threadedregions.scheduler.AsyncScheduler scheduler;

    public PaperAsyncSchedulerImpl(io.papermc.paper.threadedregions.scheduler.AsyncScheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public @NotNull RegionizedTask runNow(@NotNull Plugin plugin, @NotNull Consumer<RegionizedTask> task) {
        return new PaperRegionizedTaskWrapper(scheduler.runNow(plugin, scheduledTask -> task.accept(new PaperRegionizedTaskWrapper(scheduledTask))));
    }

    @Override
    public @NotNull RegionizedTask runDelayed(@NotNull Plugin plugin, @NotNull Consumer<RegionizedTask> task, long delayTicks) {
        return new PaperRegionizedTaskWrapper(scheduler.runDelayed(plugin, scheduledTask -> task.accept(new PaperRegionizedTaskWrapper(scheduledTask)), delayTicks * 50, TimeUnit.MILLISECONDS));
    }

    @Override
    public @NotNull RegionizedTask runAtFixedRate(@NotNull Plugin plugin, @NotNull Consumer<RegionizedTask> task, long initialDelayTicks, long periodTicks) {
        return new PaperRegionizedTaskWrapper(scheduler.runAtFixedRate(plugin, scheduledTask -> task.accept(new PaperRegionizedTaskWrapper(scheduledTask)), initialDelayTicks * 50, periodTicks * 50, TimeUnit.MILLISECONDS));
    }
}
