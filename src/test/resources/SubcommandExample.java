package test;

import dev.flur.commands.CommandInfo;

/**
 * Example of a parent command with subcommands.
 * This demonstrates how to structure commands in a hierarchical way.
 */
@CommandInfo(
    name = "parent",
    description = "Parent command with subcommands",
    usage = "/parent <subcommand> [args]",
    aliases = {"p"}
)
public class SubcommandExample {
    // Command implementation would go here in a real command
}

/**
 * First subcommand of the parent command.
 */
@CommandInfo(
    name = "parent.create",
    description = "Create subcommand of parent",
    permission = "parent.create",
    usage = "/parent create <name> [type]"
)
class CreateSubcommand {
    // Subcommand implementation would go here in a real command
}

/**
 * Second subcommand of the parent command.
 */
@CommandInfo(
    name = "parent.delete",
    description = "Delete subcommand of parent",
    permission = "parent.delete",
    usage = "/parent delete <name>",
    aliases = {"remove", "rm"}
)
class DeleteSubcommand {
    // Subcommand implementation would go here in a real command
}

/**
 * Third subcommand of the parent command.
 */
@CommandInfo(
    name = "parent.list",
    description = "List subcommand of parent",
    usage = "/parent list [filter]"
)
class ListSubcommand {
    // Subcommand implementation would go here in a real command
}