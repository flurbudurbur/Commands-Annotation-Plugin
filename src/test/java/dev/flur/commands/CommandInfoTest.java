package dev.flur.commands;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommandInfoTest {

    @Test
    void testAnnotationPresence() {
        // Verify that the annotation is present on the test class
        assertTrue(TestCommand.class.isAnnotationPresent(CommandInfo.class),
                "TestCommand should be annotated with @CommandInfo");
    }

    @Test
    void testRequiredNameAttribute() {
        // Get the annotation from the test class
        CommandInfo annotation = TestCommand.class.getAnnotation(CommandInfo.class);
        
        // Verify the required name attribute
        assertNotNull(annotation, "CommandInfo annotation should be present");
        assertEquals("test", annotation.name(), "name attribute should match");
    }

    @Test
    void testOptionalAttributes() {
        // Get the annotation from the test class
        CommandInfo annotation = TestCommand.class.getAnnotation(CommandInfo.class);
        
        // Verify optional attributes
        assertNotNull(annotation, "CommandInfo annotation should be present");
        assertEquals("Test command", annotation.description(), "description attribute should match");
        assertEquals("test.permission", annotation.permission(), "permission attribute should match");
    }

    @Test
    void testDefaultValues() {
        // Get the annotation from the minimal test class
        CommandInfo annotation = MinimalTestCommand.class.getAnnotation(CommandInfo.class);
        
        // Verify default values for optional attributes
        assertNotNull(annotation, "CommandInfo annotation should be present");
        assertEquals("minimal", annotation.name(), "name attribute should match");
        assertEquals("", annotation.description(), "description should default to empty string");
        assertEquals("", annotation.permission(), "permission should default to empty string");
    }

    // Test class with all annotation attributes
    @CommandInfo(
            name = "test",
            description = "Test command",
            permission = "test.permission"
    )
    private static class TestCommand {
    }

    // Test class with only required attribute
    @CommandInfo(name = "minimal")
    private static class MinimalTestCommand {
    }
}