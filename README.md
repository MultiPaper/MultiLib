# MultiLib
MultiLib is a plugin library for interfacing with MultiPaper specific APIs (such as external player detection), with graceful fallbacks maintaining compatibility with both the Bukkit and Spigot API's.

## API
All API calls can be found as static util methods in the `MultiPaper` class.

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
```
On MultiPaper, an external player is a player connected to another server.
Even if the player is an external player, it may still be in local chunks.
The same can be said for local players, they can be in external chunks.
On Bukkit, all players are local.

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

## Example Plugin
```java
public class MyPlugin extends JavaPlugin {
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
    implementation "com.github.puregero:multilib:1.0.0-SNAPSHOT"
}
```

Shadow Jar and Relocate (Groovy Syntax):
```groovy
plugins {
  id "com.github.johnrengelman.shadow" version "7.1.0"
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
        <version>1.0.0-SNAPSHOT</version>
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
            <version>3.2.4</version> <!-- Make sure to always use the latest version (https://maven.apache.org/plugins/maven-shade-plugin/) -->
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
