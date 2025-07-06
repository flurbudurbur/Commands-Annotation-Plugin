package test;

import dev.flur.commands.CommandInfo;

/**
 * Test command with special characters.
 * This tests that special characters are properly escaped in the output.
 */
@CommandInfo(
    name = "specialcmd",
    description = "A command with \"special\" characters & symbols",
    permission = "test.special.\"quotes\"",
    permissionMessage = "You need the \"special\" permission to use this command",
    usage = "/specialcmd [player] \"quoted text\" & symbols",
    aliases = {"special", "sp\"q\""}
)
public class SpecialCharCommand {
    // Command implementation would go here in a real command
}