package com.textr.filebuffer;

import com.textr.Settings;
import com.textr.util.Direction;
import com.textr.util.Point;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class FileBufferTest {

    static String directoryPath = "test-resources/file-buffer";
    static java.io.File directory = new java.io.File(directoryPath);
    static String fileName = "test-file.txt";
    static String[] linesInFile = new String[]{"Example text", "Line 2"};
    static String textInFile = String.join(System.lineSeparator(), linesInFile);
    static java.io.File fileIO = new java.io.File(directory, fileName);
    FileBuffer buffer;

    @BeforeAll
    static void initialiseDirectory(){
        /*
         * Directory setup
         */
        if (!directory.exists()) {
            assertTrue(directory.mkdirs(), "Failed to make test directory.");
        }
        // Set line separator
        Settings.defaultLineSeparator = "\n";

        /*
         * File setup
         */
        try {
            // Using OutputStreamWriter to force write string as is (without changing line separator)
            var writer = new OutputStreamWriter(new FileOutputStream(fileIO), StandardCharsets.UTF_8);
            writer.write(textInFile);
            writer.close();
        } catch (IOException e) {
            assertDoesNotThrow(() -> {throw e;}, "Failed setting up test file " + fileName);
        }
    }

    @BeforeEach
    void initialiseBuffer() {
        buffer = FileBuffer.createFromFilePath(fileIO.getPath());
    }

    @Test
    void createFromFilePath() {
        /*
         * Test if createFromFilePath creates a correct new FileBuffer
         */
        var buffer = FileBuffer.createFromFilePath(fileIO.getPath());
        assertArrayEquals(linesInFile, buffer.getText().getLines(),
                "Buffer content did not load correctly");
        assertEquals(fileIO, buffer.getFile(),
                "Buffer's File reference does not link to given file path");
        assertEquals(Point.create(0, 0), buffer.getCursor(),
                "Buffer's cursor is not set to start of text");
        assertEquals(BufferState.CLEAN, buffer.getState(),
                "Buffer's initial state is not CLEAN, despite just having been loaded in");

        /*
         * If file path is null, throw error:
         */
        assertThrows(IllegalArgumentException.class, () -> FileBuffer.createFromFilePath(null));
    }

    @Test
    void getFile() {
        assertEquals(fileIO, buffer.getFile(),
                "File reference returned is not equal to file reference used to create buffer");
    }

    @Test
    void getText() {
        assertEquals(Text.create(textInFile), buffer.getText());
        // Is Text object same as saved?
        var returnedText = buffer.getText();
        returnedText.splitLineAtColumn(0, 0);
        assertEquals(returnedText, buffer.getText());
    }

    @Test
    void getCursor() {
        assertEquals(Point.create(0, 0), buffer.getCursor());
        // Is cursor as saved?
        var returnedCursor = buffer.getCursor();
        returnedCursor.setY(1);
        assertEquals(returnedCursor, buffer.getCursor());
    }

    @Test
    void getState() {
        assertEquals(BufferState.CLEAN, buffer.getState());
    }

    @Test
    void moveCursor() {
        // It is assumed that this uses CursorMover
        buffer.getCursor().setX(0);
        buffer.getCursor().setY(1);
        buffer.moveCursor(Direction.LEFT);
        assertEquals(linesInFile[0].length(), buffer.getCursor().getX(),
                "Cursor did not move to end of line on moving left at start of line");
        assertEquals(0, buffer.getCursor().getY(),
                "Cursor did not move to previous line on moving left at start of line");

        // For the other cases, see CursorMover
    }

    @Test
    void setState() {
        buffer.setState(BufferState.DIRTY); // What if state was already DIRTY? See below V
        assertEquals(BufferState.DIRTY, buffer.getState());
        buffer.setState(BufferState.CLEAN);
        assertEquals(BufferState.CLEAN, buffer.getState());
        buffer.setState(BufferState.DIRTY); // Makes sure the state changed from CLEAN to DIRTY
        assertEquals(BufferState.DIRTY, buffer.getState());
    }

    @Test
    void insertCharacter() {
        final var textCopy = buffer.getText().copy();
        final var cursorCopy = buffer.getCursor().copy();
        assertDoesNotThrow(() -> buffer.insertCharacter('a'), "insertCharacter threw on valid input");
        textCopy.insertCharacter('a', cursorCopy.getY(), cursorCopy.getX());
        CursorMover.move(cursorCopy, Direction.RIGHT, textCopy);
        assertEquals(textCopy, buffer.getText(), "Text object after character insertion is not as expected");
        assertEquals(cursorCopy, buffer.getCursor(), "Cursor object after character insertion is not as expected");
        assertEquals(BufferState.DIRTY, buffer.getState(), "Buffer state is not DIRTY despite having been modified");
    }

    @Test
    void removeCharacterBefore() {
        // Start of text deletion (no effect)
        var textCopy = buffer.getText().copy();
        var cursorCopy = buffer.getCursor().copy();
        assertDoesNotThrow(() -> buffer.removeCharacterBefore(), "removeCharacterBefore threw on valid input");
        assertEquals(textCopy, buffer.getText(), "Text object after no deletion is not as expected");
        assertEquals(cursorCopy, buffer.getCursor(), "Cursor object after no deletion is not as expected");
        // BufferState may be DIRTY or CLEAN

        buffer.setState(BufferState.CLEAN);

        // Set cursor to next line in text
        buffer.getCursor().setY(1);
        buffer.getCursor().setX(1);

        // Standard deletion
        cursorCopy = buffer.getCursor().copy();

        assertDoesNotThrow(() -> buffer.removeCharacterBefore(), "removeCharacterBefore threw on valid input");
        CursorMover.move(cursorCopy, Direction.LEFT, textCopy);
        textCopy.removeCharacter(cursorCopy.getY(), cursorCopy.getX());
        assertEquals(textCopy, buffer.getText(), "Text object after character deletion is not as expected");
        assertEquals(cursorCopy, buffer.getCursor(), "Cursor object after character deletion is not as expected");
        assertEquals(BufferState.DIRTY, buffer.getState(), "Buffer state is not DIRTY despite having been modified");

        buffer.setState(BufferState.CLEAN);

        // Newline deletion
        assertDoesNotThrow(() -> buffer.removeCharacterBefore(), "removeCharacterBefore threw on valid input");
        var cursorCopyCopy = cursorCopy.copy();
        CursorMover.move(cursorCopy, Direction.LEFT, textCopy);
        textCopy.removeCharacter(cursorCopyCopy.getY(), cursorCopyCopy.getX() - 1);
        assertEquals(textCopy, buffer.getText(), "Text object after character insertion is not as expected");
        assertEquals(cursorCopy, buffer.getCursor(), "Cursor object after character insertion is not as expected");
        assertEquals(BufferState.DIRTY, buffer.getState(), "Buffer state is not DIRTY despite having been modified");
    }

    @Test
    void removeCharacterAfter() {
        // Set cursor close to end of line in text
        buffer.getCursor().setX(11);

        // Standard deletion
        var textCopy = buffer.getText().copy();
        var cursorCopy = buffer.getCursor().copy();

        assertDoesNotThrow(() -> buffer.removeCharacterAfter(), "removeCharacterAfter threw on valid input");
        textCopy.removeCharacter(cursorCopy.getY(), cursorCopy.getX());
        assertEquals(textCopy, buffer.getText(), "Text object after character deletion is not as expected");
        assertEquals(cursorCopy, buffer.getCursor(), "Cursor object after character deletion is not as expected");
        assertEquals(BufferState.DIRTY, buffer.getState(), "Buffer state is not DIRTY despite having been modified");

        buffer.setState(BufferState.CLEAN);

        // Newline deletion
        assertDoesNotThrow(() -> buffer.removeCharacterAfter(), "removeCharacterAfter threw on valid input");
        textCopy.removeCharacter(cursorCopy.getY(), cursorCopy.getX());
        assertEquals(textCopy, buffer.getText(), "Text object after character deletion is not as expected");
        assertEquals(cursorCopy, buffer.getCursor(), "Cursor object after character deletion is not as expected");
        assertEquals(BufferState.DIRTY, buffer.getState(), "Buffer state is not DIRTY despite having been modified");

        buffer.setState(BufferState.CLEAN);

        // End of text deletion (no effect)
        buffer.getCursor().setY(buffer.getText().getAmountOfLines() - 1);
        buffer.getCursor().setX(buffer.getText().getLineLength(buffer.getCursor().getY()));

        cursorCopy = buffer.getCursor().copy();
        textCopy = buffer.getText().copy();

        assertDoesNotThrow(() -> buffer.removeCharacterAfter(), "removeCharacterAfter threw on valid input");
        assertEquals(cursorCopy, buffer.getCursor(), "Cursor changed after deletion at end of text");
        assertEquals(textCopy, buffer.getText(), "Text changed after deletion at end of text");
        // BufferState may be DIRTY or CLEAN

        buffer.setState(BufferState.CLEAN);
    }

    @Test
    void createNewLine() {
        buffer.getCursor().setX(1);
        buffer.getCursor().setY(1);
        var textCopy = buffer.getText().copy();
        assertDoesNotThrow(() -> buffer.createNewLine(), "createNewLine threw on valid input");
        textCopy.splitLineAtColumn(1, 1);
        assertEquals(2, buffer.getCursor().getY(), "Buffer cursor did not move to newly created line");
        assertEquals(0, buffer.getCursor().getX(), "Buffer cursor did not move to start of new line");
        assertEquals(textCopy, buffer.getText(), "Text object after newline injection is not as expected");
        assertEquals(BufferState.DIRTY, buffer.getState(), "Buffer state is not DIRTY despite having been modified");

        buffer.setState(BufferState.CLEAN);
    }

    @Test
    void testEqualsAndHashCode() {
        assertEquals(buffer, buffer, "Buffer object is not equal to itself");
        assertEquals(buffer.hashCode(), buffer.hashCode(), "Buffer hash code differs on different calls");

        var buffer2 = FileBuffer.createFromFilePath(fileIO.getPath()); // Identical copy
        assertEquals(buffer2, buffer, "Buffer object is not equal to identically instantiated buffer");
        assertEquals(buffer2.hashCode(), buffer.hashCode(), "Hash codes of equal buffers is different");

        buffer2.getText().removeCharacter(1, 1);
        assertNotEquals(buffer2, buffer, "Buffer object is equal to buffer with unequal text");
        assertNotEquals(buffer2.hashCode(), buffer.hashCode(),
                "Buffer objects with unequal text have equal hash code");
        buffer2.getText().insertCharacter('i', 1, 1);
        assertEquals(buffer2, buffer, "Fault in testing: buffer text reset resulted in unequal buffers");

        buffer2.getCursor().setY(1);
        assertNotEquals(buffer2, buffer, "Buffer object is equal to buffer with unequal cursor");
        assertNotEquals(buffer2.hashCode(), buffer.hashCode(),
                "Buffer objects with unequal cursor have equal hash code");
        buffer2.getCursor().setY(0);
        assertEquals(buffer2, buffer, "Fault in testing: buffer cursor reset resulted in unequal buffers");

        // Cannot yet change File instance in buffer. Cannot (easily) test for this.

        buffer2.setState(BufferState.DIRTY);
        assertNotEquals(buffer2, buffer, "Buffer object is equal to buffer with unequal states");
        assertNotEquals(buffer2.hashCode(), buffer.hashCode(),
                "Buffer objects with unequal state have equal hash code");
        buffer2.setState(BufferState.CLEAN);
        assertEquals(buffer2, buffer, "Fault in testing: buffer state reset resulted in unequal buffers");
    }

    @Test
    void testToString() {
        String bufferFile = buffer.getFile().toString();
        String bufferText = buffer.getText().toString();
        String bufferCursor = buffer.getCursor().toString();
        String bufferState = buffer.getState().toString();
        String expected = String.format("FileBuffer[fileId = %s, text = %s, cursor = %s, state = %s]",
                bufferFile,
                bufferText,
                bufferCursor,
                bufferState);
        assertEquals(expected, buffer.toString());
    }

    @Test
    void copy() {
        var bufferCopy = buffer.copy();
        assertEquals(buffer, bufferCopy, "Copy of buffer is not equal to original");
        assertNotSame(buffer, bufferCopy, "Copy of buffer is same instance as original");
    }
}