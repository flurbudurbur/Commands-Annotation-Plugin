# Commands Annotation Processor

An annotation processor for Minecraft plugin commands that automatically generates command definitions for Bukkit/Spigot plugins.

## Features

- Processes `@CommandInfo` annotations to extract command metadata
- Automatically generates `commands.yml` fragments for Bukkit/Spigot plugins
- Simplifies command registration in Minecraft plugins

## Installation

Add the JitPack repository and dependency to your project:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.flurbudurbur</groupId>
        <artifactId>commands-annotation-processor</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

## Usage

1. Annotate your command classes with `@CommandInfo`:

```java
import dev.flur.commands.CommandInfo;

@CommandInfo(
    name = "example",
    description = "An example command",
    permission = "example.use",
    aliases = {"e", "exm"},
    permissionMessage = "No permission for /example",
    usage = "/example use"
)
public class ExampleCommand {
    // Command implementation
}
```

2. The annotation processor will automatically generate a `commands.yml` file during compilation that can be used in your plugin.

## Continuous Integration and Deployment

This project uses GitHub Actions for continuous integration and deployment:

- When changes are pushed to the `main` branch, the CI/CD pipeline automatically runs
- The pipeline extracts the version from `pom.xml` and compares it with the latest GitHub release
- If the version has changed, it:
  1. Builds the project with Maven
  2. Creates a new GitHub release with the JAR file attached
  3. Triggers JitPack to build the new version

### How to Release a New Version

1. Update the version number in `pom.xml`:
   ```xml
   <version>NEW_VERSION</version>
   ```
2. Commit and push your changes to the `main` branch
3. The CI/CD pipeline will automatically create a new release and publish it to JitPack

### Manual Trigger

You can also manually trigger the workflow from the GitHub Actions tab if needed.

## Development Requirements

- JDK 21
- Maven 3.8+

## License

[MIT License](LICENSE)
