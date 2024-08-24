package com.github.puregero.multilib.regionized.paper;

import com.github.puregero.multilib.regionized.GlobalRegionScheduler;
import com.github.puregero.multilib.regionized.RegionizedTask;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class PaperGlobalRegionSchedulerImpl implements GlobalRegionScheduler {

    private final io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler scheduler;

    public PaperGlobalRegionSchedulerImpl(io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void execute(@NotNull Plugin plugin, @NotNull Runnable run) {
        scheduler.execute(plugin, run);
    }

    @Override
    public @NotNull RegionizedTask run(@NotNull Plugin plugin, @NotNull Consumer<RegionizedTask> task) {
        return new PaperRegionizedTaskWrapper(scheduler.run(plugin, scheduledTask -> task.accept(new PaperRegionizedTaskWrapper(scheduledTask))));
    }

    @Override
    public @NotNull RegionizedTask runDelayed(@NotNull Plugin plugin, @NotNull Consumer<RegionizedTask> task, long delayTicks) {
        return new PaperRegionizedTaskWrapper(scheduler.runDelayed(plugin, scheduledTask -> task.accept(new PaperRegionizedTaskWrapper(scheduledTask)), delayTicks));
    }

    @Override
    public @NotNull RegionizedTask runAtFixedRate(@NotNull Plugin plugin, @NotNull Consumer<RegionizedTask> task, long initialDelayTicks, long periodTicks) {
        return new PaperRegionizedTaskWrapper(scheduler.runAtFixedRate(plugin, scheduledTask -> task.accept(new PaperRegionizedTaskWrapper(scheduledTask)), initialDelayTicks, periodTicks));
    }
}
