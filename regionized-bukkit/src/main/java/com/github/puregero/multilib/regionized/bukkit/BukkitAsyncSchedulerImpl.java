package com.github.puregero.multilib.regionized.bukkit;

import com.github.puregero.multilib.regionized.AsyncScheduler;
import com.github.puregero.multilib.regionized.RegionizedTask;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class BukkitAsyncSchedulerImpl implements AsyncScheduler {

    @Override
    public @NotNull RegionizedTask runNow(@NotNull Plugin plugin, @NotNull Consumer<RegionizedTask> task) {
        return new BukkitRegionizedTaskWrapper(plugin, task).scheduleAsync();
    }

    @Override
    public @NotNull RegionizedTask runDelayed(@NotNull Plugin plugin, @NotNull Consumer<RegionizedTask> task, long delayTicks) {
        return new BukkitRegionizedTaskWrapper(plugin, task, delayTicks).scheduleAsync();
    }

    @Override
    public @NotNull RegionizedTask runAtFixedRate(@NotNull Plugin plugin, @NotNull Consumer<RegionizedTask> task, long initialDelayTicks, long periodTicks) {
        return new BukkitRegionizedTaskWrapper(plugin, task, initialDelayTicks, periodTicks).scheduleAsync();
    }

}
