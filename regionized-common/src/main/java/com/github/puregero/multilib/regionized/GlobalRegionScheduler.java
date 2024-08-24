package com.github.puregero.multilib.regionized;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface GlobalRegionScheduler {

    /**
     * Schedules a task to be executed on the global region.
     * @param plugin The plugin that owns the task
     * @param run The task to execute
     */
    void execute(@NotNull Plugin plugin, @NotNull Runnable run);

    /**
     * Schedules a task to be executed on the global region on the next tick.
     * @param plugin The plugin that owns the task
     * @param task The task to execute
     * @return The {@link RegionizedTask} that represents the scheduled task.
     */
    @NotNull RegionizedTask run(@NotNull Plugin plugin, @NotNull Consumer<RegionizedTask> task);

    /**
     * Schedules a task to be executed on the global region after the specified delay in ticks.
     * @param plugin The plugin that owns the task
     * @param task The task to execute
     * @param delayTicks The delay, in ticks.
     * @return The {@link RegionizedTask} that represents the scheduled task.
     */
    @NotNull RegionizedTask runDelayed(@NotNull Plugin plugin, @NotNull Consumer<RegionizedTask> task, long delayTicks);

    /**
     * Schedules a repeating task to be executed on the global region after the initial delay with the
     * specified period.
     * @param plugin The plugin that owns the task
     * @param task The task to execute
     * @param initialDelayTicks The initial delay, in ticks.
     * @param periodTicks The period, in ticks.
     * @return The {@link RegionizedTask} that represents the scheduled task.
     */
    @NotNull RegionizedTask runAtFixedRate(@NotNull Plugin plugin, @NotNull Consumer<RegionizedTask> task,
                                          long initialDelayTicks, long periodTicks);

}
