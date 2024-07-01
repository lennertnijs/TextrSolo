package com.textr.input;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public final class InputTest {

    private final Input characterInput = Input.createCharacterInput('c');
    private final Input specialInput = Input.createSpecialInput(InputType.CTRL_T);

    @Test
    public void testCreateSpecialInputWithNull(){
        assertThrows(NullPointerException.class,
                () -> Input.createSpecialInput(null));
    }

    @Test
    public void testCreateSpecialInputWithCharacter(){
        assertThrows(IllegalArgumentException.class,
                () -> Input.createSpecialInput(InputType.CHARACTER));
    }

    @Test
    public void testGetCharacter(){
        assertEquals('c', characterInput.getCharacter());
    }

    @Test
    public void testGetType(){
        assertEquals(InputType.CTRL_T, specialInput.getType());
    }

    @Test
    public void testGetCharacterFromSpecialType(){
        assertThrows(IllegalStateException.class,
                specialInput::getCharacter);
    }

    @Test
    public void testEquals(){
        Input input1 = Input.createCharacterInput('c');
        Input input2 = Input.createCharacterInput('c');
        Input input3 = Input.createCharacterInput('c');
        // reflexive
        assertEquals(input1, input1);
        // symmetrical
        assertEquals(input1, input2);
        assertEquals(input2, input1);
        // transitive
        assertEquals(input1, input2);
        assertEquals(input2, input3);
        assertEquals(input1, input3);

        // not equals
        Input diffChar = Input.createCharacterInput('l');
        Input diffType = Input.createSpecialInput(InputType.CTRL_G);
        assertNotEquals(input1, diffChar);
        assertNotEquals(input1, diffType);
        assertNotEquals(input1, new Object());
        assertNotEquals(input1, null);
    }

    @Test
    public void testHashCode(){
        Input input1 = Input.createCharacterInput('c');
        Input input2 = Input.createCharacterInput('c');
        Input input3 = Input.createCharacterInput('c');
        // reflexive
        assertEquals(input1.hashCode(), input1.hashCode());
        // symmetrical
        assertEquals(input1.hashCode(), input2.hashCode());
        assertEquals(input2.hashCode(), input1.hashCode());
        // transitive
        assertEquals(input1.hashCode(), input2.hashCode());
        assertEquals(input2.hashCode(), input3.hashCode());
        assertEquals(input1.hashCode(), input3.hashCode());

        // not equals
        Input diffChar = Input.createCharacterInput('l');
        Input diffType = Input.createSpecialInput(InputType.CTRL_G);
        assertNotEquals(input1.hashCode(), diffChar.hashCode());
        assertNotEquals(input1.hashCode(), diffType.hashCode());
    }

    @Test
    public void testToString(){
        String expected = "Input[" +
                "type=CHARACTER, " +
                "characterByte=99" +
                "]";
        assertEquals(expected, characterInput.toString());
    }
}
