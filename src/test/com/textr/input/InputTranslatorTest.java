package com.textr.input;

import com.textr.terminal.MockTerminalService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public final class InputTranslatorTest {

    private final MockTerminalService terminal = new MockTerminalService();
    private final InputTranslator translator = new InputTranslator(terminal);

    @Test
    public void testTranslateAllAsciiCharacters(){
        String ascii_chars =
                " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
        for (int i = 32; i < 127; i++) {
            terminal.addInput((byte) i);
        }
        for (int i = 0; i < ascii_chars.length(); i++) {
            char ascii = ascii_chars.charAt(i);
            Input newInput = translator.getNextInput();
            assertEquals(InputType.CHARACTER, newInput.getType());
            assertEquals(ascii, newInput.getCharacter());
        }
        assertTrue(terminal.hasNoMoreInput(), "Some characters were not tested");
    }

    @Test
    public void testCtrlD(){
        terminal.addInput((byte) 4);
        assertEquals(Input.createSpecialInput(InputType.CTRL_D), translator.getNextInput());
    }

    @Test
    public void testCtrlG(){
        terminal.addInput((byte) 7);
        assertEquals(Input.createSpecialInput(InputType.CTRL_G), translator.getNextInput());
    }

    @Test
    public void testEnter(){
        terminal.addInput((byte) 13);
        assertEquals(Input.createSpecialInput(InputType.ENTER), translator.getNextInput());
    }

    @Test
    public void testCtrlN(){
        terminal.addInput((byte) 14);
        assertEquals(Input.createSpecialInput(InputType.CTRL_N), translator.getNextInput());
    }

    @Test
    public void testCtrlP(){
        terminal.addInput((byte) 16);
        assertEquals(Input.createSpecialInput(InputType.CTRL_P), translator.getNextInput());
    }

    @Test
    public void testCtrlR(){
        terminal.addInput((byte) 18);
        assertEquals(Input.createSpecialInput(InputType.CTRL_R), translator.getNextInput());
    }

    @Test
    public void testCtrlS(){
        terminal.addInput((byte) 19);
        assertEquals(Input.createSpecialInput(InputType.CTRL_S), translator.getNextInput());
    }

    @Test
    public void testCtrlT(){
        terminal.addInput((byte) 20);
        assertEquals(Input.createSpecialInput(InputType.CTRL_T), translator.getNextInput());
    }

    @Test
    public void testCtrlU(){
        terminal.addInput((byte) 21);
        assertEquals(Input.createSpecialInput(InputType.CTRL_U), translator.getNextInput());
    }

    @Test
    public void testCtrlZ(){
        terminal.addInput((byte) -1);
        assertEquals(Input.createSpecialInput(InputType.CTRL_Z), translator.getNextInput());
    }

    @Test
    public void testTick(){
        terminal.addInput((byte) -2);
        assertEquals(Input.createSpecialInput(InputType.TICK), translator.getNextInput());
    }

    @Test
    public void testBackSpace(){
        terminal.addInput((byte) 127);
        assertEquals(Input.createSpecialInput(InputType.BACKSPACE), translator.getNextInput());
    }

    @Test
    public void testSingleByteNotMapped(){
        terminal.addInput((byte) 500);
        assertEquals(Input.createSpecialInput(InputType.NOT_MAPPED), translator.getNextInput());
    }

    @Test
    public void testEscapeByteSequenceOf2NotMapped(){
        terminal.addInput((byte) 27, (byte) 600);
        assertEquals(Input.createSpecialInput(InputType.NOT_MAPPED), translator.getNextInput());
    }

    @Test
    public void testArrowUp(){
        terminal.addInput((byte) 27, (byte) 91, (byte) 65);
        assertEquals(Input.createSpecialInput(InputType.ARROW_UP), translator.getNextInput());
    }

    @Test
    public void testArrowRight(){
        terminal.addInput((byte) 27, (byte) 91, (byte) 67);
        assertEquals(Input.createSpecialInput(InputType.ARROW_RIGHT), translator.getNextInput());
    }

    @Test
    public void testArrowDown(){
        terminal.addInput((byte) 27, (byte) 91, (byte) 66);
        assertEquals(Input.createSpecialInput(InputType.ARROW_DOWN), translator.getNextInput());
    }

    @Test
    public void testArrowLeft(){
        terminal.addInput((byte) 27, (byte) 91, (byte) 68);
        assertEquals(Input.createSpecialInput(InputType.ARROW_LEFT), translator.getNextInput());
    }

    @Test
    public void testEscapeSequenceOf3NotMapped(){
        terminal.addInput((byte) 27, (byte) '[', (byte) 870);
        assertEquals(Input.createSpecialInput(InputType.NOT_MAPPED), translator.getNextInput());
    }

    @Test
    public void testDelete(){
        terminal.addInput((byte) 27, (byte) 91, (byte) 51, (byte) 126);
        assertEquals(Input.createSpecialInput(InputType.DELETE), translator.getNextInput());
    }

    @Test
    public void testF4(){
        terminal.addInput((byte) 27, (byte) 79, (byte) 83);
        assertEquals(Input.createSpecialInput(InputType.F4), translator.getNextInput());
    }

    @Test
    public void testMoreUnmapped(){
        terminal.addInput((byte) 27, (byte) 'O', (byte) 450);
        assertEquals(Input.createSpecialInput(InputType.NOT_MAPPED), translator.getNextInput());
    }

    @Test
    public void testEscapeSequenceOf4NotMapped(){
        terminal.addInput((byte) 27, (byte) '[', (byte) '3', (byte) 450);
        assertEquals(Input.createSpecialInput(InputType.NOT_MAPPED), translator.getNextInput());
    }
}