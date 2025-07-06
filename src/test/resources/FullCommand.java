package test;

import dev.flur.commands.CommandInfo;

/**
 * Test command with all attributes specified.
 * This tests that all attributes are properly processed and included in the output.
 */
@CommandInfo(
    name = "fullcmd",
    description = "A command with all attributes specified",
    permission = "test.fullcommand",
    permissionMessage = "You don't have permission to use this command",
    usage = "/fullcmd [player] [action]",
    aliases = {"fc", "full"}
)
public class FullCommand {
    // Command implementation would go here in a real command
}