package com.textr.input;

import com.textr.terminal.MockTerminalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputTranslatorTest {

    MockTerminalService terminal;
    InputTranslator translator;

    @BeforeEach
    void setUp() {
        // New translator
        this.terminal = new MockTerminalService();
        this.translator = new InputTranslator(terminal);
    }

    @Test
    void getNextInput() {
        // Character input
        String ascii_chars =
                " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
        for (int i = 32; i < 127; i++) {
            this.terminal.addInput((byte) i);
        }
        for (int i = 0; i < ascii_chars.length(); i++) {
            char ascii = ascii_chars.charAt(i);
            Input newInput = translator.getNextInput();
            assertEquals(InputType.CHARACTER, newInput.getType(), String.format(
                    "Character not recognised: %c | byte: %d", ascii, i + 32));
            assertEquals(ascii, newInput.getCharacter(), String.format(
                    "Character mismatch: %c | byte: %d | received: %c", ascii, i + 32, newInput.getCharacter()));
        }
        assertTrue(terminal.hasNoMoreInput(), "Some characters were not tested");

        // Special input
        // CTRL+...
        terminal.addInput((byte) 4);
        assertEquals(Input.createSpecialInput(InputType.CTRL_D), translator.getNextInput());
        assertTrue(terminal.hasNoMoreInput(), "Input wasn't read successfully");
        terminal.addInput((byte) 7);
        assertEquals(Input.createSpecialInput(InputType.CTRL_G), translator.getNextInput());
        assertTrue(terminal.hasNoMoreInput(), "Input wasn't read successfully");
        terminal.addInput((byte) 14);
        assertEquals(Input.createSpecialInput(InputType.CTRL_N), translator.getNextInput());
        assertTrue(terminal.hasNoMoreInput(), "Input wasn't read successfully");
        terminal.addInput((byte) 16);
        assertEquals(Input.createSpecialInput(InputType.CTRL_P), translator.getNextInput());
        assertTrue(terminal.hasNoMoreInput(), "Input wasn't read successfully");
        terminal.addInput((byte) 18);
        assertEquals(Input.createSpecialInput(InputType.CTRL_R), translator.getNextInput());
        assertTrue(terminal.hasNoMoreInput(), "Input wasn't read successfully");
        terminal.addInput((byte) 19);
        assertEquals(Input.createSpecialInput(InputType.CTRL_S), translator.getNextInput());
        assertTrue(terminal.hasNoMoreInput(), "Input wasn't read successfully");
        terminal.addInput((byte) 20);
        assertEquals(Input.createSpecialInput(InputType.CTRL_T), translator.getNextInput());
        assertTrue(terminal.hasNoMoreInput(), "Input wasn't read successfully");
        terminal.addInput((byte) 21);
        assertEquals(Input.createSpecialInput(InputType.CTRL_U), translator.getNextInput());
        assertTrue(terminal.hasNoMoreInput(), "Input wasn't read successfully");
        terminal.addInput((byte) -1);
        assertEquals(Input.createSpecialInput(InputType.CTRL_Z), translator.getNextInput());
        assertTrue(terminal.hasNoMoreInput(), "Input wasn't read successfully");
        // BACKSPACE
        terminal.addInput((byte) 127);
        assertEquals(Input.createSpecialInput(InputType.BACKSPACE), translator.getNextInput());
        assertTrue(terminal.hasNoMoreInput(), "Input wasn't read successfully");
        // TICK
        terminal.addInput((byte) -2);
        assertEquals(Input.createSpecialInput(InputType.TICK), translator.getNextInput());
        assertTrue(terminal.hasNoMoreInput(), "Input wasn't read successfully");

        // Sequences:
        // ARROWS
        terminal.addInput((byte) 27, (byte) 91, (byte) 65);
        assertEquals(Input.createSpecialInput(InputType.ARROW_UP), translator.getNextInput());
        assertTrue(terminal.hasNoMoreInput(), "Input wasn't read successfully");
        terminal.addInput((byte) 27, (byte) 91, (byte) 66);
        assertEquals(Input.createSpecialInput(InputType.ARROW_DOWN), translator.getNextInput());
        assertTrue(terminal.hasNoMoreInput(), "Input wasn't read successfully");
        terminal.addInput((byte) 27, (byte) 91, (byte) 67);
        assertEquals(Input.createSpecialInput(InputType.ARROW_RIGHT), translator.getNextInput());
        assertTrue(terminal.hasNoMoreInput(), "Input wasn't read successfully");
        terminal.addInput((byte) 27, (byte) 91, (byte) 68);
        assertEquals(Input.createSpecialInput(InputType.ARROW_LEFT), translator.getNextInput());
        assertTrue(terminal.hasNoMoreInput(), "Input wasn't read successfully");
        // DELETE
        terminal.addInput((byte) 27, (byte) 91, (byte) 51, (byte) 126);
        assertEquals(Input.createSpecialInput(InputType.DELETE), translator.getNextInput());
        assertTrue(terminal.hasNoMoreInput(), "Input wasn't read successfully");
        // F4
        terminal.addInput((byte) 27, (byte) 79, (byte) 83);
        assertEquals(Input.createSpecialInput(InputType.F4), translator.getNextInput());
        assertTrue(terminal.hasNoMoreInput(), "Input wasn't read successfully");
    }
}