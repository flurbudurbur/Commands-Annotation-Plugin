package dev.flur.commands.processor;

import dev.flur.commands.CommandInfo;
import org.jetbrains.annotations.NotNull;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * An annotation processor that handles {@link CommandInfo} annotations.
 * <p>
 * This processor scans for classes annotated with {@code @CommandInfo} and generates
 * a {@code commands.yml} file containing command definitions in the format required
 * by Bukkit plugins. The generated file includes all command metadata such as name,
 * description, permission, usage, and aliases.
 * <p>
 * The processor runs during the compilation phase and outputs the generated YAML
 * to the class output directory, where it can be included in the final JAR file.
 *
 * @since 1.0.0
 * @see CommandInfo
 */
@SupportedAnnotationTypes("dev.flur.commands.CommandInfo")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public final class CommandAnnotationProcessor extends AbstractProcessor {

    /**
     * Constructs a new CommandAnnotationProcessor.
     * <p>
     * Initializes the processor with an empty command map. Commands will be
     * collected during the annotation processing rounds.
     */
    public CommandAnnotationProcessor() {
        // Default constructor
    }

    private final Map<String, CommandData> commands = new HashMap<>();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, @NotNull RoundEnvironment roundEnv) {
        // Collect all commands
        for (Element element : roundEnv.getElementsAnnotatedWith(CommandInfo.class)) {
            if (element instanceof TypeElement typeElement) {
                CommandInfo commandInfo = element.getAnnotation(CommandInfo.class);

                CommandData data = new CommandData(
                        commandInfo.name(),
                        commandInfo.description(),
                        commandInfo.permission(),
                        commandInfo.permissionMessage(),
                        commandInfo.usage(),
                        commandInfo.aliases()
                );

                commands.put(commandInfo.name(), data);

                processingEnv.getMessager().printMessage(
                        Diagnostic.Kind.NOTE,
                        "Found command: " + commandInfo.name() + " in class " + typeElement.getSimpleName()
                );
            }
        }

        // Generate plugin.yml fragment in the last round
        if (roundEnv.processingOver() && !commands.isEmpty()) {
            generatePluginYmlFragment();
        }

        return true;
    }

    private void generatePluginYmlFragment() {
        try {
            FileObject resource = processingEnv.getFiler().createResource(
                    StandardLocation.CLASS_OUTPUT,
                    "",
                    "commands.yml"
            );

            try (Writer writer = resource.openWriter()) {
                writer.write("# Auto-generated command definitions\n");
                writer.write("commands:\n");

                for (CommandData command : commands.values()) {
                    writer.write("  " + command.name() + ":\n");
                    writer.write("    description: \"" + command.description() + "\"\n");

                    if (!command.usage().isEmpty()) {
                        writer.write("    usage: \"" + command.usage() + "\"\n");
                    } else {
                        writer.write("    usage: \"/" + command.name() + "\"\n");
                    }

                    if (!command.permission().isEmpty()) {
                        writer.write("    permission: \"" + command.permission() + "\"\n");
                    }

                    if (!command.permissionMessage().isEmpty()) {
                        writer.write("    permission-message: \"" + command.permissionMessage() + "\"\n");
                    }

                    if (command.aliases().length > 0) {
                        writer.write("    aliases: [");
                        for (int i = 0; i < command.aliases().length; i++) {
                            writer.write("\"" + command.aliases()[i] + "\"");
                            if (i < command.aliases().length - 1) {
                                writer.write(", ");
                            }
                        }
                        writer.write("]\n");
                    }

                    writer.write("\n");
                }
            }

            processingEnv.getMessager().printMessage(
                    Diagnostic.Kind.NOTE,
                    "Generated commands.yml with " + commands.size() + " commands"
            );

        } catch (IOException e) {
            processingEnv.getMessager().printMessage(
                    Diagnostic.Kind.ERROR,
                    "Failed to generate commands.yml: " + e.getMessage()
            );
        }
    }

    private record CommandData(
            String name, 
            String description, 
            String permission,
            String permissionMessage,
            String usage,
            String[] aliases) {
    }
}
