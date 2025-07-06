package dev.flur.commands.processor;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.Compiler;
import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.Test;

import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.google.testing.compile.CompilationSubject.assertThat;

class CommandAnnotationProcessorTest {

    @Test
    void testBasicCommandProcessing() {
        // Create a test class with @CommandInfo annotation
        JavaFileObject testClass = JavaFileObjects.forSourceLines(
                "test.TestCommand",
                "package test;",
                "",
                "import dev.flur.commands.CommandInfo;",
                "",
                "@CommandInfo(name = \"testcmd\", description = \"A test command\", permission = \"test.permission\")",
                "public class TestCommand {",
                "}"
        );

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
                .contains("  testcmd:");

        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    description: \"A test command\"");

        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    usage: \"/testcmd\"");

        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    permission: \"test.permission\"");
    }

    @Test
    void testMultipleCommands() {
        // Create test classes with @CommandInfo annotations
        JavaFileObject testClass1 = JavaFileObjects.forSourceLines(
                "test.TestCommand1",
                "package test;",
                "",
                "import dev.flur.commands.CommandInfo;",
                "",
                "@CommandInfo(name = \"cmd1\", description = \"Command 1\")",
                "public class TestCommand1 {",
                "}"
        );

        JavaFileObject testClass2 = JavaFileObjects.forSourceLines(
                "test.TestCommand2",
                "package test;",
                "",
                "import dev.flur.commands.CommandInfo;",
                "",
                "@CommandInfo(name = \"cmd2\", description = \"Command 2\", permission = \"cmd.use\")",
                "public class TestCommand2 {",
                "}"
        );

        // Compile the test classes with our processor
        Compilation compilation = Compiler.javac()
                .withProcessors(new CommandAnnotationProcessor())
                .compile(testClass1, testClass2);

        // Verify compilation was successful
        assertThat(compilation).succeeded();

        // Verify the commands.yml file was generated with both commands
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml");

        // Verify content for first command
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("  cmd1:");

        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    description: \"Command 1\"");

        // Verify content for second command
        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("  cmd2:");

        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    description: \"Command 2\"");

        assertThat(compilation)
                .generatedFile(StandardLocation.CLASS_OUTPUT, "commands.yml")
                .contentsAsString(StandardCharsets.UTF_8)
                .contains("    permission: \"cmd.use\"");
    }

    @Test
    void testDefaultValues() {
        // Create a test class with minimal @CommandInfo annotation (only required name)
        JavaFileObject testClass = JavaFileObjects.forSourceLines(
                "test.MinimalCommand",
                "package test;",
                "",
                "import dev.flur.commands.CommandInfo;",
                "",
                "@CommandInfo(name = \"minimal\")",
                "public class MinimalCommand {",
                "}"
        );

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
    }
}