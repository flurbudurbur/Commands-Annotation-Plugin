package test;

import dev.flur.commands.CommandInfo;

/**
 * Test command with permission settings.
 * This tests that permissions and permission messages are properly processed and included in the output.
 */
@CommandInfo(
    name = "permcmd",
    description = "A command with permission settings",
    permission = "test.permission.use",
    permissionMessage = "You need the test.permission.use permission to use this command"
)
public class PermissionCommand {
    // Command implementation would go here in a real command
}