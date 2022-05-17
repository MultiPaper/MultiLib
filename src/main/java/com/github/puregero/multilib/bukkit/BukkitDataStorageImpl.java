package com.github.puregero.multilib.bukkit;

import com.github.puregero.multilib.DataStorageImpl;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class BukkitDataStorageImpl implements DataStorageImpl {
    private CompletableFuture<Map<String, Object>> yaml;
    private CompletableFuture<Void> saveFuture;

    private String add0(String A, String B) {
        if (A == null) {
            return B;
        }

        try {
            long a = Long.parseLong(A);
            long b = Long.parseLong(B);
            return Long.toString(a + b);
        } catch (NumberFormatException ignored) {}

        try {
            double a = Double.parseDouble(A);
            double b = Double.parseDouble(B);
            return Double.toString(a + b);
        } catch (NumberFormatException ignored) {}

        return B;
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
                        return new Yaml().load(in);
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
        Runtime.getRuntime().addShutdownHook(new Thread("datastorage-saver") {
            @Override
            public void run() {
                if (saveFuture != null && !saveFuture.isDone()) {
                    System.out.println("Saving unsaved datastorage.yaml...");
                    saveYaml();
                }
            }
        });
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
            String result = add0((String) yaml.get(key), increment);
            yaml.put(key, result);
            scheduleSave();
            return result;
        });
    }
}
