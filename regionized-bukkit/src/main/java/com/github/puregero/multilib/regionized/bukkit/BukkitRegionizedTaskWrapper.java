package com.github.puregero.multilib.regionized.bukkit;

import com.github.puregero.multilib.regionized.RegionizedTask;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class BukkitRegionizedTaskWrapper implements RegionizedTask, Runnable {

    private final Plugin plugin;
    private final Consumer<RegionizedTask> task;
    private final long initialDelayTicks;
    private final long periodTicks;
    private BukkitTask bukkitTask;

    private final AtomicReference<ExecutionState> executionState = new AtomicReference<>(ExecutionState.IDLE);

    public BukkitRegionizedTaskWrapper(Plugin plugin, Consumer<RegionizedTask> task) {
        this(plugin, task, 0);
    }

    public BukkitRegionizedTaskWrapper(Plugin plugin, Consumer<RegionizedTask> task, long delayTicks) {
        this(plugin, task, delayTicks, -1);
    }

    public BukkitRegionizedTaskWrapper(Plugin plugin, Consumer<RegionizedTask> task, long initialDelayTicks, long periodTicks) {
        this.plugin = plugin;
        this.task = task;
        this.initialDelayTicks = initialDelayTicks;
        this.periodTicks = periodTicks;
    }

    @Override
    public void run() {
        if (!this.executionState.compareAndSet(ExecutionState.IDLE, ExecutionState.RUNNING)) {
            return;
        }

        try {
            this.task.accept(this);
        } finally {
            if (this.isRepeatingTask()) {
                this.executionState.compareAndSet(ExecutionState.RUNNING, ExecutionState.IDLE);
            } else {
                this.executionState.compareAndSet(ExecutionState.RUNNING, ExecutionState.FINISHED);
            }
            this.executionState.compareAndSet(ExecutionState.CANCELLED_RUNNING, ExecutionState.CANCELLED);
        }
    }

    public BukkitRegionizedTaskWrapper schedule() {
        if (this.isRepeatingTask()) {
            this.bukkitTask = this.plugin.getServer().getScheduler().runTaskTimer(this.plugin, this, this.initialDelayTicks, this.periodTicks);
        } else {
            this.bukkitTask = this.plugin.getServer().getScheduler().runTaskLater(this.plugin, this, this.initialDelayTicks);
        }

        return this;
    }

    @Override
    public @NotNull Plugin getOwningPlugin() {
        return this.plugin;
    }

    @Override
    public boolean isRepeatingTask() {
        return this.periodTicks > 0;
    }

    @Override
    public @NotNull RegionizedTask.CancelledState cancel() {
        if (executionState.compareAndSet(ExecutionState.IDLE, ExecutionState.CANCELLED)) {
            this.bukkitTask.cancel();
            return CancelledState.CANCELLED_BY_CALLER;
        }
        if (executionState.compareAndSet(ExecutionState.RUNNING, ExecutionState.CANCELLED_RUNNING)) {
            this.bukkitTask.cancel();
            if (isRepeatingTask()) {
                return CancelledState.NEXT_RUNS_CANCELLED;
            } else {
                return CancelledState.RUNNING;
            }
        }
        switch (executionState.get()) {
            case IDLE:
            case RUNNING:
                this.bukkitTask.cancel();
                executionState.set(ExecutionState.CANCELLED);
                return CancelledState.CANCELLED_BY_CALLER;
            case CANCELLED:
                return CancelledState.CANCELLED_ALREADY;
            case CANCELLED_RUNNING:
                return CancelledState.NEXT_RUNS_CANCELLED_ALREADY;
            case FINISHED:
                return CancelledState.ALREADY_EXECUTED;
            default:
                throw new IllegalStateException("Unknown execution state: " + executionState.get());
        }
    }

    @Override
    public @NotNull RegionizedTask.ExecutionState getExecutionState() {
        return this.executionState.get();
    }

    @Override
    public boolean isCancelled() {
        return this.getExecutionState() == ExecutionState.CANCELLED || this.getExecutionState() == ExecutionState.CANCELLED_RUNNING;
    }
}
