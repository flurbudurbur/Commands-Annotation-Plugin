package test;

import dev.flur.commands.CommandInfo;

/**
 * Test command with multiple aliases.
 * This tests that aliases are properly processed and included in the output.
 */
@CommandInfo(
    name = "aliascmd",
    description = "A command with multiple aliases",
    aliases = {"ac", "alias", "a", "alt"}
)
public class AliasCommand {
    // Command implementation would go here in a real command
}