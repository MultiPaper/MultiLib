package com.github.puregero.multilib.regionized.bukkit;

import com.github.puregero.multilib.regionized.EntityScheduler;
import com.github.puregero.multilib.regionized.RegionizedTask;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class BukkitEntitySchedulerImpl implements EntityScheduler {

    private final Entity entity;

    public BukkitEntitySchedulerImpl(Entity entity) {
        this.entity = entity;
    }

    private Entity getEntity() {
        return this.entity;
    }

    private void handle(RegionizedTask regionizedTask, Consumer<RegionizedTask> runnable, Runnable retired) {
        if (getEntity() == null || getEntity().isDead() || !getEntity().isValid()) {
            regionizedTask.cancel();
            if (retired != null) {
                retired.run();
            }
            return;
        }

        runnable.accept(regionizedTask);
    }

    @Override
    public boolean execute(@NotNull Plugin plugin, @NotNull Runnable run, @Nullable Runnable retired, long delay) {
        new BukkitRegionizedTaskWrapper(plugin, regionizedTask -> handle(regionizedTask, t -> run.run(), retired), delay).schedule();
        return true;
    }

    @Override
    public @Nullable RegionizedTask run(@NotNull Plugin plugin, @NotNull Consumer<RegionizedTask> task, @Nullable Runnable retired) {
        return new BukkitRegionizedTaskWrapper(plugin, regionizedTask -> handle(regionizedTask, task, retired)).schedule();
    }

    @Override
    public @Nullable RegionizedTask runDelayed(@NotNull Plugin plugin, @NotNull Consumer<RegionizedTask> task, @Nullable Runnable retired, long delayTicks) {
        return new BukkitRegionizedTaskWrapper(plugin, regionizedTask -> handle(regionizedTask, task, retired), delayTicks).schedule();
    }

    @Override
    public @Nullable RegionizedTask runAtFixedRate(@NotNull Plugin plugin, @NotNull Consumer<RegionizedTask> task, @Nullable Runnable retired, long initialDelayTicks, long periodTicks) {
        return new BukkitRegionizedTaskWrapper(plugin, regionizedTask -> handle(regionizedTask, task, retired), initialDelayTicks, periodTicks).schedule();
    }
}
