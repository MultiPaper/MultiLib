# MultiLib
MultiLib is a plugin library for interfacing with MultiPaper specific APIs (such as external player detection), with graceful fallbacks maintaining compatibility with both the Bukkit and Spigot API's.

## API
All API calls can be found as static util methods in the `MultiLib` class.

### Chunks
```java
boolean isChunkExternal(World world, int cx, int cz);
boolean isChunkExternal(Location location);
boolean isChunkExternal(Entity entity);
boolean isChunkExternal(Block block);
boolean isChunkExternal(Chunk chunk);
boolean isChunkLocal(World world, int cx, int cz);
boolean isChunkLocal(Location location);
boolean isChunkLocal(Entity entity);
boolean isChunkLocal(Block block);
boolean isChunkLocal(Chunk chunk);
```
On MultiPaper, an external chunk is one being ticked on an external server, with this server simply containing a copy of the chunk.
On Bukkit, all chunks are local.

### Players
```java
boolean isExternalPlayer(Player player);
boolean isLocalPlayer(Player player);
String getExternalServerName(Player player);
String getLocalServerName();
```
On MultiPaper, an external player is a player connected to another server.
Even if the player is an external player, it may still be in local chunks.
The same can be said for local players, they can be in external chunks.
On Bukkit, all players are local, and the server's local name will always be
"bukkit".

```java
@Nullable String getExternalServerName(Player player);
```
On MultiPaper, an external player will return the name of the server that the
player is on. A local player has no external server, and will return null.
On Bukkit, all players are local, and will always return null.

```java
Collection<? extends Player> getAllOnlinePlayers();
Collection<? extends Player> getLocalOnlinePlayers();
```
On MultiPaper, all online players will return all players across all MultiPaper
instances. Local online players will return the players on your single local
MultiPaper instance.
On Bukkit, both these methods return the same collection of players.

### Sharing data
```java
String getData(Player player, String key);
void setData(Player player, String key, String value);
String getPersistentData(Player player, String key);
void setPersistentData(Player player, String key, String value);
```
On MultiPaper, data stored with these methods will be available on all servers.
Persistent data will be saved to the disk while non-persistent data will be lost when the player disconnects.
On Bukkit, this data will only be available on the one server, and persistent data will be stored in a yaml file under `multilib-data`.

### Notifying other servers
```java
void on(Plugin plugin, String channel, Consumer<byte[]> callback);
void onString(Plugin plugin, String channel, Consumer<String> callback);
void on(Plugin plugin, String channel, BiConsumer<byte[], BiConsumer<String, byte[]>> callbackWithReply);
void onString(Plugin plugin, String channel, BiConsumer<String, BiConsumer<String, String>> callbackWithReply);
void notify(String channel, byte[] data);
void notify(String channel, String data);
void notify(Chunk chunk, String channel, byte[] data);
void notify(Chunk chunk, String channel, String data);
```
On MultiPaper, plugins running on other servers can be notified with a data payload.
You can also notify only certain server with a specified chunk loaded.
On Bukkit, these methods will do nothing as there are no other servers.

### Running commands on other servers
```java
void chatOnOtherServers(Player player, String message);
```
On MultiPaper, the specified player will say a chat message (or run a command)
on all other servers. On Bukkit, this method will do nothing as there are no
other servers.

### Simple key-value data storage
```java
MultiLib.getDataStorage();
CompletableFuture<String> get(String key);
CompletableFuture<Map<String, String>> list(String keyPrefix);
CompletableFuture<String> set(String key, String value);
CompletableFuture<Integer> add(String key, int increment);
CompletableFuture<Double> add(String key, double increment);
MultiLib.getDataStorage().createCache(Plugin plugin, String keyPrefix);
```
An async key-value data storage. This should not be used for large data as the
backing database is in-memory and utilises an inefficient file storage format.
Adding is an atomic operation. Any changes on one server will instantly be
reflected across all other servers.

## Example Plugin

```java
import com.github.puregero.multilib.MultiLib;

public class MyPlugin extends JavaPlugin {
    public void onEnable() {
        MultiLib.onString(this, "myplugin:testchannel", (data, reply) -> {
            getLogger().info("Received " + data);
            reply.accept("myplugin:testchannelreply", "Replying to " + data);
        });

        MultiLib.onString(this, "myplugin:testchannelreply", data -> {
            getLogger().info("Received the reply " + data);
        });

        MultiLib.notify("myplugin:testchannel", "Hello!");
    }

    public void doSomething(Player player) {
        if (MultiLib.isExternalPlayer(player)) {
            getLogger().info("Not our player! Let's not actually do this!");
        } else {
            actuallyDoTheSomething(player);
        }
    }
}
```

## Build Script Setup
Add the Clojars repository and the MultiLib dependency, then shade and relocate it to your own package.
Relocation helps avoid version conflicts with other plugins using MultiLib. 

### Gradle

Repo:
```groovy
repositories {
  maven {
    url "https://repo.clojars.org/"
  }
}
```

Dependency:
```groovy
dependencies {
    implementation "com.github.puregero:multilib:1.1.8"
}
```

Shadow Jar and Relocate (Groovy Syntax):
```groovy
plugins {
  id "com.github.johnrengelman.shadow" version "7.1.2"
  // Make sure to always use the latest version (https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow)
}
shadowJar {
   relocate "com.github.puregero.multilib", "[YOUR PLUGIN PACKAGE].multilib"
}
```

### Maven
Repo:
```xml
<repositories>
    <repository>
        <id>clojars</id>
        <url>https://repo.clojars.org</url>
    </repository>
</repositories>
```
Dependency:
```xml
<dependencies>
    <dependency>
        <groupId>com.github.puregero</groupId>
        <artifactId>multilib</artifactId>
        <version>1.1.8</version>
        <scope>compile</scope>
     </dependency>
 </dependencies>
 ```
 
Shade & Relocate:
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-shade-plugin</artifactId>
            <version>3.4.0</version> <!-- Make sure to always use the latest version (https://maven.apache.org/plugins/maven-shade-plugin/) -->
            <configuration>
                <dependencyReducedPomLocation>${project.build.directory}/dependency-reduced-pom.xml</dependencyReducedPomLocation>
                <relocations>
                    <relocation>
                        <pattern>com.github.puregero.multilib</pattern>
                        <shadedPattern>[YOUR PLUGIN PACKAGE].multilib</shadedPattern> <!-- Replace this -->
                    </relocation>
                </relocations>
            </configuration>
            <executions>
                <execution>
                    <phase>package</phase>
                    <goals>
                        <goal>shade</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

## Compiling
MultiPaper is compiled using maven:
```
mvn
```

## License
MultiLib is licensed under the [MIT license](LICENSE)

## PaperLib
MultiLib is inspired by PaperMC's [PaperLib](https://github.com/PaperMC/PaperLib)
