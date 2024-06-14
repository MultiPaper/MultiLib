package com.github.puregero.multilib.regionized;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface EntityScheduler {
    /**
     * Schedules a task with the given delay. If the task failed to schedule because the scheduler is retired (entity
     * removed), then returns {@code false}. Otherwise, either the run callback will be invoked after the specified delay,
     * or the retired callback will be invoked if the scheduler is retired.
     * Note that the retired callback is invoked in critical code, so it should not attempt to remove the entity, remove
     * other entities, load chunks, load worlds, modify ticket levels, etc.
     *
     * <p>
     * It is guaranteed that the run and retired callback are invoked on the region which owns the entity.
     * </p>
     * @param run The callback to run after the specified delay, may not be null.
     * @param retired Retire callback to run if the entity is retired before the run callback can be invoked, may be null.
     * @param delay The delay in ticks before the run callback is invoked. Any value less-than 1 is treated as 1.
     * @return {@code true} if the task was scheduled, which means that either the run function or the retired function
     *         will be invoked (but never both), or {@code false} indicating neither the run nor retired function will be invoked
     *         since the scheduler has been retired.
     */
    boolean execute(@NotNull Plugin plugin, @NotNull Runnable run, @Nullable Runnable retired, long delay);

    /**
     * Schedules a task to execute on the next tick. If the task failed to schedule because the scheduler is retired (entity
     * removed), then returns {@code null}. Otherwise, either the task callback will be invoked after the specified delay,
     * or the retired callback will be invoked if the scheduler is retired.
     * Note that the retired callback is invoked in critical code, so it should not attempt to remove the entity, remove
     * other entities, load chunks, load worlds, modify ticket levels, etc.
     *
     * <p>
     * It is guaranteed that the task and retired callback are invoked on the region which owns the entity.
     * </p>
     * @param plugin The plugin that owns the task
     * @param task The task to execute
     * @param retired Retire callback to run if the entity is retired before the run callback can be invoked, may be null.
     * @return The {@link RegionizedTask} that represents the scheduled task, or {@code null} if the entity has been removed.
     */
    @Nullable RegionizedTask run(@NotNull Plugin plugin, @NotNull Consumer<RegionizedTask> task,
                                @Nullable Runnable retired);

    /**
     * Schedules a task with the given delay. If the task failed to schedule because the scheduler is retired (entity
     * removed), then returns {@code null}. Otherwise, either the task callback will be invoked after the specified delay,
     * or the retired callback will be invoked if the scheduler is retired.
     * Note that the retired callback is invoked in critical code, so it should not attempt to remove the entity, remove
     * other entities, load chunks, load worlds, modify ticket levels, etc.
     *
     * <p>
     * It is guaranteed that the task and retired callback are invoked on the region which owns the entity.
     * </p>
     * @param plugin The plugin that owns the task
     * @param task The task to execute
     * @param retired Retire callback to run if the entity is retired before the run callback can be invoked, may be null.
     * @param delayTicks The delay, in ticks.
     * @return The {@link RegionizedTask} that represents the scheduled task, or {@code null} if the entity has been removed.
     */
    @Nullable RegionizedTask runDelayed(@NotNull Plugin plugin, @NotNull Consumer<RegionizedTask> task,
                                       @Nullable Runnable retired, long delayTicks);

    /**
     * Schedules a repeating task with the given delay and period. If the task failed to schedule because the scheduler
     * is retired (entity removed), then returns {@code null}. Otherwise, either the task callback will be invoked after
     * the specified delay, or the retired callback will be invoked if the scheduler is retired.
     * Note that the retired callback is invoked in critical code, so it should not attempt to remove the entity, remove
     * other entities, load chunks, load worlds, modify ticket levels, etc.
     *
     * <p>
     * It is guaranteed that the task and retired callback are invoked on the region which owns the entity.
     * </p>
     * @param plugin The plugin that owns the task
     * @param task The task to execute
     * @param retired Retire callback to run if the entity is retired before the run callback can be invoked, may be null.
     * @param initialDelayTicks The initial delay, in ticks.
     * @param periodTicks The period, in ticks.
     * @return The {@link RegionizedTask} that represents the scheduled task, or {@code null} if the entity has been removed.
     */
    @Nullable RegionizedTask runAtFixedRate(@NotNull Plugin plugin, @NotNull Consumer<RegionizedTask> task,
                                           @Nullable Runnable retired, long initialDelayTicks, long periodTicks);

}
