package test;

import dev.flur.commands.CommandInfo;

/**
 * Test command with custom usage.
 * This tests that usage formatting is properly processed and included in the output.
 */
@CommandInfo(
    name = "usagecmd",
    description = "A command with custom usage",
    usage = "/usagecmd <required> [optional] [--flag]"
)
public class UsageCommand {
    // Command implementation would go here in a real command
}