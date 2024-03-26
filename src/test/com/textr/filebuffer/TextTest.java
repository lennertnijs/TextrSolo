package com.textr.filebuffer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TextTest {

    final private String text1 = String.join(System.lineSeparator(), new String[]{"TEXT1", "TEXT 1", "Text 1"});
    final private String[] text2 = new String[]{"TEXT2", "TEXT 2", "Text 2", "Extra line"};
    private Text firstText;
    private Text secText;

    @BeforeEach
    void initialise(){
        firstText = Text.create(text1);
        secText = Text.create(text2);
    }

    @Test
    void testEquals(){
        Text text = Text.create(String.join(System.lineSeparator(), new String[]{"TEXT1", "TEXT 1", "Text 1"}));
        assertEquals(text, text, "Text object is not equal to itself");
        assertEquals(text, firstText, "Text object is not equal to an equally instantiated Text object");
        assertNotEquals(text, secText, "Text object is equal to differently instantiated Text object");
        assertNotEquals(text, new Object(), "Text object is equal to generic Object");
    }

    @Test
    void testHashCode(){
        Text text = Text.create(String.join(System.lineSeparator(), new String[]{"TEXT1", "TEXT 1", "Text 1"}));
        assertEquals(text.hashCode(), firstText.hashCode(), "Hash codes of equal Text objects is different");
        assertEquals(text.hashCode(), text.hashCode(), "Hash code of Text object is different on different calls");
        assertNotEquals(text.hashCode(), secText.hashCode(), "Hash codes of unequal Text objects are equal");
    }

    @Test
    public void testToString(){
        String expected = "Text[Lines: TEXT1\r\nTEXT 1\r\nText 1]";
        assertEquals(expected, firstText.toString());
    }


    @Test
    void create() {
        // Bad input
        assertThrows(IllegalArgumentException.class, () -> Text.create((String[]) null));
        assertThrows(IllegalArgumentException.class, () -> Text.create((String) null));
        assertThrows(IllegalArgumentException.class, () -> Text.create(new String[]{"1", null, "3"}));
    }

    @Test
    void getLines() {
        assertArrayEquals(firstText.getLines(), text1.split(System.lineSeparator()));
        assertArrayEquals(secText.getLines(), text2);
    }

    @Test
    void getText() {
        assertEquals(firstText.getText(), text1);
        assertEquals(secText.getText(), String.join(System.lineSeparator(), text2));
    }

    @Test
    void getLineLength() {
        // Standard input
        assertEquals(5, secText.getLineLength(0), "getLineLength returned incorrect length.");
        assertEquals(6, secText.getLineLength(1), "getLineLength returned incorrect length.");
        assertEquals(6, secText.getLineLength(2), "getLineLength returned incorrect length.");
        assertEquals(10, secText.getLineLength(3), "getLineLength returned incorrect length.");

        // Bad input
        assertThrows(IllegalArgumentException.class, () -> secText.getLineLength(secText.getAmountOfLines()),
                "getLineLength should throws when index is out of bounds.");
        assertThrows(IllegalArgumentException.class, () -> secText.getLineLength(-1),
                "getLineLength should throws when index is negative.");

        // 0-length test
        Text emptyText = Text.create("");
        assertEquals(0, emptyText.getLineLength(0), "getLineLength returned incorrect length.");
    }

    @Test
    void getAmountOfLines() {
        assertEquals(firstText.getAmountOfLines(), 3);
        assertEquals(secText.getAmountOfLines(), 4);
    }

    @Test
    void getAmountOfChars() {
        assertEquals(firstText.getAmountOfChars(), 17);
        assertEquals(secText.getAmountOfChars(), 27);
    }

    @Test
    void insertCharacter() {
        Text shouldBeText1 = Text.create(String.join(System.lineSeparator(), new String[]{"TEXT1","TEXT 19","Text 1"}));
        Text shouldBeText2 = Text.create(new String[]{"9TEXT2", "TEXT 2", "Text 2", "Extra line"});
        assertDoesNotThrow(() -> firstText.insertCharacter('9', 1, 6), "insertCharacter threw on valid input");
        assertEquals(shouldBeText1, firstText, "Text objects aren't equals after character insertion.");
        assertDoesNotThrow(() -> secText.insertCharacter('9', 0, 0), "insertCharacter threw on valid input");
        assertEquals(shouldBeText2, secText, "Text objects aren't equals after character insertion.");

        // Bad input
        assertThrows(IllegalArgumentException.class, () -> firstText.insertCharacter('a', 0, -1),
                "insertCharacter does not throw when given negative column index.");
        assertThrows(IllegalArgumentException.class, () -> firstText.insertCharacter('a', -1, 0),
                "insertCharacter does not throw when given negative row index.");
        assertThrows(IllegalArgumentException.class, () -> firstText.insertCharacter('a', 0, 6),
                "insertCharacter does not throw when given out of bounds column index.");
        assertThrows(IllegalArgumentException.class, () -> firstText.insertCharacter('a', 3, 0),
                "insertCharacter does not throw when given out of bounds row index.");
    }

    @Test
    void removeCharacter() {
        // Standard behavior
        Text shouldBeText1 = Text.create(String.join(System.lineSeparator(), new String[]{"TEXT1","TEXT ","Text 1"}));
        Text shouldBeText2 = Text.create(new String[]{"EXT2", "TEXT 2", "Text 2", "Extra line"});
        assertDoesNotThrow(() -> firstText.removeCharacter(1, 5), "removeCharacter threw on valid input.");
        assertEquals(shouldBeText1, firstText, "Text objects aren't equals after character deletion.");
        assertDoesNotThrow(() -> secText.removeCharacter(0, 0), "removeCharacter threw on valid input.");
        assertEquals(shouldBeText2, secText, "Text objects aren't equals after character deletion.");

        // Newline deletion
        Text beforeDel1 = Text.create(new String[]{"Line 1", "Line 2"});
        Text afterDel = Text.create(new String[]{"Line 1Line 2"});
        assertDoesNotThrow(() -> beforeDel1.removeCharacter(0, 6), "removeCharacter threw on newline location.");
        assertEquals(afterDel, beforeDel1, "Newline at end of line wasn't deleted correctly.");

        Text beforeDel2 = Text.create(new String[]{"Line 1", "Line 2"});
        assertDoesNotThrow(() -> beforeDel2.removeCharacter(1, -1), "removeCharacter threw on newline location.");
        assertEquals(afterDel, beforeDel2, "Newline at negative column index wasn't deleted correctly.");

        // Endpoint of files deletion (no effect)
        Text beforeDel3 = Text.create(new String[]{"Line 1", "Line 2"});
        afterDel = Text.create(new String[]{"Line 1", "Line 2"});
        assertDoesNotThrow(() -> beforeDel3.removeCharacter(0, -1), "removeCharacter threw on endpoint input.");
        assertEquals(afterDel, beforeDel3, "Deletion before start of file resulted in unequal object.");
        assertDoesNotThrow(() -> beforeDel3.removeCharacter(1, 6), "removeCharacter threw on endpoint input.");
        assertEquals(afterDel, beforeDel3, "Deletion after end of file resulted in unequal object.");

        // Bad input
        assertThrows(IllegalArgumentException.class, () -> firstText.removeCharacter(0, -2),
                "removeCharacter does not throw when given column index < -1.");
        assertThrows(IllegalArgumentException.class, () -> firstText.removeCharacter(-1, 0),
                "removeCharacter does not throw when given negative row index.");
        assertThrows(IllegalArgumentException.class, () -> firstText.removeCharacter(0, 6),
                "removeCharacter does not throw when given out of bounds column index.");
        assertThrows(IllegalArgumentException.class, () -> firstText.removeCharacter(3, 0),
                "removeCharacter does not throw when given out of bounds row index.");
    }

    @Test
    void splitLineAtColumn() {
        // Standard behavior
        Text afterSplit = Text.create(
                String.join(System.lineSeparator(), new String[]{"TE", "XT1", "TEXT 1", "Text 1"})
        );
        assertDoesNotThrow(() -> firstText.splitLineAtColumn(0, 2), "splitLineAtColumn threw with valid input.");
        assertEquals(afterSplit, firstText, "Lines did not split correctly.");

        // End of text split
        afterSplit = Text.create(
                String.join(System.lineSeparator(), new String[]{"TE", "XT1", "TEXT 1", "Text 1", ""})
        );
        assertDoesNotThrow(() -> firstText.splitLineAtColumn(3, 6), "splitLineAtColumn threw at end of text input.");
        assertEquals(afterSplit.getText(), firstText.getText(), "Lines did not split correctly at end of text.");

        // Bad input
        Text badInputText = Text.create(new String[]{"Line 1", "Line 2"});
        assertThrows(IllegalArgumentException.class, () -> badInputText.splitLineAtColumn(0, -1),
                "splitLineAtColumn does not throw when given negative column index.");
        assertThrows(IllegalArgumentException.class, () -> badInputText.splitLineAtColumn(-1, 0),
                "splitLineAtColumn does not throw when given negative row index.");
        assertThrows(IllegalArgumentException.class, () -> badInputText.splitLineAtColumn(0, 7),
                "splitLineAtColumn does not throw when given out of bounds column index.");
        assertThrows(IllegalArgumentException.class, () -> badInputText.splitLineAtColumn(2, 0),
                "splitLineAtColumn does not throw when given out of bounds row index.");
    }

    @Test
    void copy() {
        Text firstTextCopy = firstText.copy();
        assertEquals(firstText, firstTextCopy, "Copy is not equal to original.");
        assertNotSame(firstText, firstTextCopy, "Copy is not a different object from original.");
    }
}
