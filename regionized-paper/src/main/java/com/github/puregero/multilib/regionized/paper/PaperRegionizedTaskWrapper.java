package com.github.puregero.multilib.regionized.paper;

import com.github.puregero.multilib.regionized.RegionizedTask;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class PaperRegionizedTaskWrapper implements RegionizedTask {

    private final ScheduledTask scheduledTask;

    public PaperRegionizedTaskWrapper(ScheduledTask scheduledTask) {
        this.scheduledTask = scheduledTask;
    }

    @Override
    public @NotNull Plugin getOwningPlugin() {
        return this.scheduledTask.getOwningPlugin();
    }

    @Override
    public boolean isRepeatingTask() {
        return this.scheduledTask.isRepeatingTask();
    }

    @Override
    public @NotNull RegionizedTask.CancelledState cancel() {
        return from(this.scheduledTask.cancel());
    }

    @Override
    public @NotNull RegionizedTask.ExecutionState getExecutionState() {
        return from(this.scheduledTask.getExecutionState());
    }

    @Override
    public boolean isCancelled() {
        return this.scheduledTask.isCancelled();
    }

    private static RegionizedTask.CancelledState from(ScheduledTask.CancelledState state) {
        return switch (state) {
            case CANCELLED_BY_CALLER -> CancelledState.CANCELLED_BY_CALLER;
            case CANCELLED_ALREADY -> CancelledState.CANCELLED_ALREADY;
            case RUNNING -> CancelledState.RUNNING;
            case ALREADY_EXECUTED -> CancelledState.ALREADY_EXECUTED;
            case NEXT_RUNS_CANCELLED -> CancelledState.NEXT_RUNS_CANCELLED;
            case NEXT_RUNS_CANCELLED_ALREADY -> CancelledState.NEXT_RUNS_CANCELLED_ALREADY;
        };
    }

    private static RegionizedTask.ExecutionState from(ScheduledTask.ExecutionState state) {
        return switch (state) {
            case IDLE -> ExecutionState.IDLE;
            case RUNNING -> ExecutionState.RUNNING;
            case FINISHED -> ExecutionState.FINISHED;
            case CANCELLED -> ExecutionState.CANCELLED;
            case CANCELLED_RUNNING -> ExecutionState.CANCELLED_RUNNING;
        };
    }
}
