package dev.flur.commands.processor;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.Compiler;
import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.Test;

import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.nio.charset.StandardCharsets;

import static com.google.testing.compile.CompilationSubject.assertThat;

class CommandAnnotationProcessorTest {

    @Test
    void testBasicCommandProcessing() {
        // Load the test class with @CommandInfo annotation from resources
        JavaFileObject testClass = JavaFileObjects.forResource("FullCommand.java");

        // Compile the test class with our processor
        Compilation compilation = Compiler.javac()
                .withProcessors(new CommandAnnotationProcessor())
                .compile(testClass);

        // Verify compilation was successful
        assertThat(compilation).succeeded();

        // Verify the commands.yml file was generated
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml");

        // Verify the content of the generated file
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("# Auto-generated command definitions");

        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("commands:");

        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("  fullcmd:");

        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    description: \"A command with all attributes specified\"");

        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    permission: \"test.fullcommand\"");

        // Verify permission-message is in the output
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    permission-message: \"You don't have permission to use this command\"");

        // Verify custom usage is in the output
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    usage: \"/fullcmd [player] [action]\"");

        // Verify aliases are in the output
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    aliases: [\"fc\", \"full\"]");
    }

    @Test
    void testMultipleCommands() {
        // Load the test file with multiple @CommandInfo annotations from resources
        JavaFileObject testClass = JavaFileObjects.forResource("MultipleCommands.java");

        // Compile the test class with our processor
        Compilation compilation = Compiler.javac()
                .withProcessors(new CommandAnnotationProcessor())
                .compile(testClass);

        // Verify compilation was successful
        assertThat(compilation).succeeded();

        // Verify the commands.yml file was generated with all commands
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml");

        // Verify content for first command
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("  first:");

        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    description: \"First command in multiple commands file\"");

        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    usage: \"/first [arg]\"");

        // Verify content for second command
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("  second:");

        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    description: \"Second command in multiple commands file\"");

        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    permission: \"test.second\"");

        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    aliases: [\"s\", \"sec\"]");

        // Verify content for third command
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("  third:");

        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    description: \"\"");

        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    usage: \"/third\"");
    }

    @Test
    void testDefaultValues() {
        // Load the test class with minimal @CommandInfo annotation from resources
        JavaFileObject testClass = JavaFileObjects.forResource("MinimalCommand.java");

        // Compile the test class with our processor
        Compilation compilation = Compiler.javac()
                .withProcessors(new CommandAnnotationProcessor())
                .compile(testClass);

        // Verify compilation was successful
        assertThat(compilation).succeeded();

        // Verify the commands.yml file was generated with default values
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml");

        // Should contain the command with empty description
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("  minimal:");

        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    description: \"\"");

        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    usage: \"/minimal\"");

        // Should not contain permission line since it's empty
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .doesNotContain("    permission: \"\"");

        // Should not contain permission-message line since it's empty
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .doesNotContain("    permission-message: \"\"");

        // Should not contain aliases line since it's empty
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .doesNotContain("    aliases: []");
    }

    @Test
    void testAliasCommand() {
        // Load the test class with multiple aliases from resources
        JavaFileObject testClass = JavaFileObjects.forResource("AliasCommand.java");

        // Compile the test class with our processor
        Compilation compilation = Compiler.javac()
                .withProcessors(new CommandAnnotationProcessor())
                .compile(testClass);

        // Verify compilation was successful
        assertThat(compilation).succeeded();

        // Verify the commands.yml file was generated
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml");

        // Verify command name and description
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("  aliascmd:");

        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    description: \"A command with multiple aliases\"");

        // Verify multiple aliases are in the output
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    aliases: [\"ac\", \"alias\", \"a\", \"alt\"]");
    }

    @Test
    void testPermissionCommand() {
        // Load the test class with permission settings from resources
        JavaFileObject testClass = JavaFileObjects.forResource("PermissionCommand.java");

        // Compile the test class with our processor
        Compilation compilation = Compiler.javac()
                .withProcessors(new CommandAnnotationProcessor())
                .compile(testClass);

        // Verify compilation was successful
        assertThat(compilation).succeeded();

        // Verify the commands.yml file was generated
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml");

        // Verify command name and description
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("  permcmd:");

        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    description: \"A command with permission settings\"");

        // Verify permission settings are in the output
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    permission: \"test.permission.use\"");

        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    permission-message: \"You need the test.permission.use permission to use this command\"");
    }

    @Test
    void testUsageCommand() {
        // Load the test class with custom usage from resources
        JavaFileObject testClass = JavaFileObjects.forResource("UsageCommand.java");

        // Compile the test class with our processor
        Compilation compilation = Compiler.javac()
                .withProcessors(new CommandAnnotationProcessor())
                .compile(testClass);

        // Verify compilation was successful
        assertThat(compilation).succeeded();

        // Verify the commands.yml file was generated
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml");

        // Verify command name and description
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("  usagecmd:");

        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    description: \"A command with custom usage\"");

        // Verify custom usage is in the output
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    usage: \"/usagecmd <required> [optional] [--flag]\"");
    }

    @Test
    void testSpecialCharCommand() {
        // Load the test class with special characters from resources
        JavaFileObject testClass = JavaFileObjects.forResource("SpecialCharCommand.java");

        // Compile the test class with our processor
        Compilation compilation = Compiler.javac()
                .withProcessors(new CommandAnnotationProcessor())
                .compile(testClass);

        // Verify compilation was successful
        assertThat(compilation).succeeded();

        // Verify the commands.yml file was generated
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml");

        // Verify command name and description with special characters
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("  specialcmd:");

        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    description: \"A command with \"special\" characters & symbols\"");

        // Verify permission with special characters
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    permission: \"test.special.\"quotes\"\"");

        // Verify permission message with special characters
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    permission-message: \"You need the \"special\" permission to use this command\"");

        // Verify usage with special characters
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    usage: \"/specialcmd [player] \"quoted text\" & symbols\"");

        // Verify aliases with special characters
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    aliases: [\"special\", \"sp\"q\"\"]");
    }

    @Test
    void testSubcommandExample() {
        // Load the test class with subcommands from resources
        JavaFileObject testClass = JavaFileObjects.forResource("SubcommandExample.java");

        // Compile the test class with our processor
        Compilation compilation = Compiler.javac()
                .withProcessors(new CommandAnnotationProcessor())
                .compile(testClass);

        // Verify compilation was successful
        assertThat(compilation).succeeded();

        // Verify the commands.yml file was generated
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml");

        // Verify parent command
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("  parent:");

        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    description: \"Parent command with subcommands\"");

        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    usage: \"/parent <subcommand> [args]\"");

        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    aliases: [\"p\"]");

        // Verify create subcommand
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("  parent.create:");

        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    description: \"Create subcommand of parent\"");

        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    permission: \"parent.create\"");

        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    usage: \"/parent create <name> [type]\"");

        // Verify delete subcommand
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("  parent.delete:");

        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    aliases: [\"remove\", \"rm\"]");

        // Verify list subcommand
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("  parent.list:");
    }
}
