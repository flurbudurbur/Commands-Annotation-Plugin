package test;

import dev.flur.commands.CommandInfo;

/**
 * File containing multiple command classes to test processing multiple commands.
 */

/**
 * First command in the file.
 */
@CommandInfo(
    name = "first",
    description = "First command in multiple commands file",
    usage = "/first [arg]"
)
class FirstCommand {
    // Command implementation would go here in a real command
}

/**
 * Second command in the file.
 */
@CommandInfo(
    name = "second",
    description = "Second command in multiple commands file",
    permission = "test.second",
    aliases = {"s", "sec"}
)
class SecondCommand {
    // Command implementation would go here in a real command
}

/**
 * Third command in the file with minimal attributes.
 */
@CommandInfo(name = "third")
class ThirdCommand {
    // Command implementation would go here in a real command
}