package dev.flur.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@code CommandInfo} annotation is used to define command metadata for automatic command registration
 * and configuration generation. When applied to a class, it marks that class as a command handler and
 * provides all necessary information for the command system.
 * <p>
 * During compilation, classes annotated with {@code @CommandInfo} are processed by the
 * {@code CommandAnnotationProcessor}, which generates a {@code commands.yml} file containing
 * all command definitions in the proper format.
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * @CommandInfo(
 *     name = "teleport",
 *     description = "Teleport to another player or location",
 *     permission = "myserver.teleport",
 *     permissionMessage = "You don't have permission to teleport",
 *     usage = "/teleport <player> [destination]",
 *     aliases = {"tp", "tele"}
 * )
 * public class TeleportCommand {
 *     // Command implementation
 * }
 * }
 * </pre>
 * <p>
 * Only the {@code name} attribute is required; all other attributes have default values.
 *
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandInfo {
    /**
     * The name of the command, without any leading slash.
     * <p>
     * This is a required attribute and must be unique across all commands.
     * The name will be used as the primary identifier for the command in the
     * generated configuration.
     * 
     * @return The command name
     */
    String name();

    /**
     * The permission node required to use this command.
     * <p>
     * If specified, users must have this permission to execute the command.
     * If left empty, no permission check will be performed.
     * 
     * @return The permission node, or an empty string if no permission is required
     */
    String permission() default "";

    /**
     * A brief description of what the command does.
     * <p>
     * This description will be shown in help menus and command listings.
     * It should be concise but informative.
     * 
     * @return The command description, or an empty string if no description is provided
     */
    String description() default "";

    /**
     * The usage message for the command, showing the correct syntax.
     * <p>
     * This will be displayed to users when they use the command incorrectly.
     * If not specified, a default usage message in the format "/{command name}"
     * will be generated.
     * 
     * @return The usage message, or an empty string to use the default format
     */
    String usage() default "";

    /**
     * Alternative names for the command.
     * <p>
     * These aliases can be used instead of the primary command name.
     * For example, if the command name is "teleport" and aliases include "tp",
     * users can use either "/teleport" or "/tp".
     * 
     * @return An array of command aliases, or an empty array if no aliases are defined
     */
    String[] aliases() default {};

    /**
     * The message shown to users who attempt to use the command without
     * having the required permission.
     * <p>
     * This message will only be used if a permission is specified.
     * 
     * @return The permission denied message, or an empty string to use the system default
     */
    String permissionMessage() default "";
}
