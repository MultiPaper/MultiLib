package com.github.puregero.multilib.bukkit;

import com.github.puregero.multilib.DataStorageImpl;
import com.github.puregero.multilib.MultiLibImpl;
import com.github.puregero.multilib.util.StringAddition;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class BukkitDataStorageImpl implements DataStorageImpl {
    private final BukkitImpl multilib;
    private CompletableFuture<Map<String, Object>> yaml;
    private CompletableFuture<Void> saveFuture;

    public BukkitDataStorageImpl(BukkitImpl multilib) {
        this.multilib = multilib;
    }

    private synchronized void scheduleSave() {
        if (saveFuture == null || saveFuture.isDone()) {
            saveFuture = CompletableFuture.runAsync(this::saveYaml, CompletableFuture.delayedExecutor(15, TimeUnit.SECONDS));
        }
    }

    private File getFile() {
        // Use package name in file to ensure multiple shaded MultiLibs aren't conflicting with eachother
        return new File((getClass().getPackageName() + ".").replace("\tcom.github.puregero.multilib.bukkit.".substring(1), "") + "datastorage.yml");
    }

    private synchronized void saveYaml() {
        File file = getFile();
        try (FileWriter out = new FileWriter(file)) {
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            options.setPrettyFlow(true);
            new Yaml(options).dump(yaml.join(), out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized CompletableFuture<Map<String, Object>> loadYaml() {
        if (yaml == null) {
            yaml = CompletableFuture.supplyAsync(() -> {
                registerShutdownHook();
                File file = getFile();
                if (file.isFile()) {
                    try (FileInputStream in = new FileInputStream(file)) {
                        return new Yaml(new SafeConstructor(new LoaderOptions())).load(in);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return new HashMap<>();
            });
        }

        return yaml;
    }

    private void registerShutdownHook() {
        JavaPlugin plugin = JavaPlugin.getProvidingPlugin(getClass());
        plugin.getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onDisable(PluginDisableEvent event) {
                if (event.getPlugin() == plugin) {
                    if (saveFuture != null && !saveFuture.isDone()) {
                        System.out.println("Saving unsaved datastorage.yaml...");
                        saveYaml();
                    }
                }
            }
        }, plugin);
    }

    @Override
    public CompletableFuture<String> get(String key) {
        return loadYaml().thenApply(yaml -> (String) yaml.get(key));
    }

    @Override
    public CompletableFuture<Map<String, String>> list(String keyPrefix) {
        return loadYaml().thenApply(yaml -> {
            Map<String, String> list = new HashMap<>();
            for (Map.Entry<String, Object> entry : yaml.entrySet()) {
                if (entry.getKey() != null && entry.getKey().startsWith(keyPrefix) && entry.getValue() instanceof String string) {
                    list.put(entry.getKey(), string);
                }
            }
            return list;
        });
    }

    @Override
    public CompletableFuture<String> set(String key, String value) {
        return loadYaml().thenApply(yaml -> {
            if (value == null) {
                yaml.remove(key);
            } else {
                yaml.put(key, value);
            }
            scheduleSave();
            return value;
        });
    }

    @Override
    public CompletableFuture<String> add(String key, String increment) {
        return loadYaml().thenApply(yaml -> {
            String result = StringAddition.add((String) yaml.get(key), increment);
            yaml.put(key, result);
            scheduleSave();
            return result;
        });
    }

    @Override
    public MultiLibImpl getMultiLib() {
        return multilib;
    }
}
