package com.github.puregero.multilib.regionized;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public interface AsyncScheduler {

    /**
     * Schedules the specified task to be executed asynchronously immediately.
     * @param plugin Plugin which owns the specified task.
     * @param task Specified task.
     * @return The {@link RegionizedTask} that represents the scheduled task.
     */
    @NotNull RegionizedTask runNow(@NotNull Plugin plugin, @NotNull Consumer<RegionizedTask> task);

    /**
     * Schedules the specified task to be executed asynchronously after the time delay has passed.
     * @param plugin Plugin which owns the specified task.
     * @param task Specified task.
     * @param delayTicks The delay, in ticks.
     * @return The {@link RegionizedTask} that represents the scheduled task.
     */
    @NotNull RegionizedTask runDelayed(@NotNull Plugin plugin, @NotNull Consumer<RegionizedTask> task, long delayTicks);

    /**
     * Schedules the specified task to be executed asynchronously after the initial delay has passed,
     * and then periodically executed with the specified period.
     * @param plugin Plugin which owns the specified task.
     * @param task Specified task.
     * @param initialDelayTicks The initial delay, in ticks.
     * @param periodTicks The period, in ticks.
     * @return The {@link RegionizedTask} that represents the scheduled task.
     */
    @NotNull RegionizedTask runAtFixedRate(@NotNull Plugin plugin, @NotNull Consumer<RegionizedTask> task,
                                          long initialDelayTicks, long periodTicks);

}
